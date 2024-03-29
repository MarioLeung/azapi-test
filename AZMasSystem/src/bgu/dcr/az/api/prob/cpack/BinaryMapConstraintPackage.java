/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.api.prob.cpack;

import bgu.dcr.az.api.Agt0DSL;
import bgu.dcr.az.api.prob.ConstraintCheckResult;
import bgu.dcr.az.api.prob.KAryConstraint;
import bgu.dcr.az.api.tools.Assignment;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author bennyl
 */
public class BinaryMapConstraintPackage extends AbstractConstraintPackage {

    private Object[] map;
    private int biggestDomainSize = 0;
    private final int numvars;

//    private SymetricCPACostCache cache;
//    boolean useCache = false;
    public BinaryMapConstraintPackage(int numvar, int maxDomainSize) {
        super(numvar);

        this.numvars = numvar;
        this.biggestDomainSize = maxDomainSize;
        this.map = new Object[numvars * numvars];

        //      cache = new SymetricCPACostCache(this);
    }

    protected int calcId(int i, int j) {
        return i * numvars + j;
    }

    @Override
    public void setConstraintCost(int owner, int var1, int val1, int var2, int val2, int cost) {

        if (owner == var2) {
            int t = var1;
            var1 = var2;
            var2 = t;
            t = val1;
            val1 = val2;
            val2 = t;
        }

        if (owner != var1) {
            Agt0DSL.panic("Binary Problem cannot support constraint owners that are not part of the constraints, if you need such a feature use the K-Ary version.");
        }

        int id = calcId(var1, var2);
        if (var1 != var2) {
            addNeighbor(var1, var2);
        }

        createMap(id);
        ((int[][]) map[id])[val1][val2] = cost;
    }

    private void createMap(int id) {
        int[][] mapId = (int[][]) map[id];
        if (mapId == null) {
            mapId = new int[biggestDomainSize][biggestDomainSize];
            map[id] = mapId;
        }
    }

    @Override
    public void setConstraintCost(int owner, int x1, int v1, int cost) {
        setConstraintCost(owner, x1, v1, x1, v1, cost);
    }

    @Override
    public void getConstraintCost(int owner, int x1, int v1, ConstraintCheckResult result) {
        getConstraintCost(owner, x1, v1, x1, v1, result);
    }

    @Override
    public void getConstraintCost(int owner, int var1, int val1, int var2, int val2, ConstraintCheckResult result) {
        if (owner == var2) {
            int t = var1;
            var1 = var2;
            var2 = t;
            t = val1;
            val1 = val2;
            val2 = t;
        }

        if (owner != var1) {
            Agt0DSL.panic("Binary Problem cannot support constraint owners that are not part of the constraints, if you need such a feature use the K-Ary version.");
        }

        int id = calcId(var1, var2);
        if (map[id] == null) {
            result.set(0, 1);
        } else {
            result.set(((int[][]) map[id])[val1][val2], 1);
        }
    }

    @Override
    public void getConstraintCost(int owner, Assignment k, ConstraintCheckResult result) {
        throw new UnsupportedOperationException("Not supported - only Binary and Unary Constraints supported in this problem type.");
    }

    @Override
    public void setConstraintCost(int owner, KAryConstraint constraint) {
        throw new UnsupportedOperationException("Not supported - only Binary and Unary Constraints supported in this problem type.");
    }

    @Override
    public void calculateCost(int owner, Assignment assignment, ConstraintCheckResult result) {
//        if (useCache) {
//            result.set(cache.calcCost(assignment.getAssignment(), owner), 0);
//            return;
//        }
        int c = 0;
        int cc = 0;
        ArrayList<Map.Entry<Integer, Integer>> past = new ArrayList<>(assignment.getAssignments());
        for (int i = 0; i< past.size(); i++ ) {
            Map.Entry<Integer, Integer> e = past.get(i);
            int var = e.getKey();
            int val = e.getValue();
            getConstraintCost(var, var, val, result);
            c += result.getCost();
            cc += result.getCheckCost();

            for (int j = 0; j<i; j++) {
                Map.Entry<Integer, Integer> pe = past.get(j);
                int pvar = pe.getKey();
                int pval = pe.getValue();

                getConstraintCost(pvar, pvar, pval, var, val, result);
                c += result.getCost();
                cc += result.getCheckCost();
            }
//            past.add(e);
        }

//        ArrayList<Map.Entry<Integer, Integer>> past = new ArrayList<>(assignment.getNumberOfAssignedVariables());
//        for (Map.Entry<Integer, Integer> e : assignment.getAssignments()) {
//            int var = e.getKey();
//            int val = e.getValue();
//            getConstraintCost(var, var, val, result);
//            c += result.getCost();
//            cc += result.getCheckCost();
//
//            for (Map.Entry<Integer, Integer> pe : past) {
//                int pvar = pe.getKey();
//                int pval = pe.getValue();
//
//                getConstraintCost(pvar, pvar, pval, var, val, result);
//                c += result.getCost();
//                cc += result.getCheckCost();
//            }
//            past.add(e);
//        }
        result.set(c, cc);
    }

    @Override
    public int calculateGlobalCost(Assignment assignment) {
        ConstraintCheckResult res = new ConstraintCheckResult();
        calculateCost(-1, assignment, res);
        return res.getCost();
    }

    @Override
    public void addConstraintCost(int owner, KAryConstraint constraint) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
