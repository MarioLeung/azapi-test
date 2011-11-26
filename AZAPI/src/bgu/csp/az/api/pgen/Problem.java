package bgu.csp.az.api.pgen;

import bgu.csp.az.api.ImmutableProblem;
import bgu.csp.az.api.ProblemType;
import bgu.csp.az.api.ds.ImmutableSet;
import bgu.csp.az.api.tools.Assignment;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * An abstract class for problems that should let you build any type of problem 
 * @author guyafe, edited by bennyl
 */
public abstract class Problem implements Serializable, ImmutableProblem {

    private HashMap<String, Object> metadata = new HashMap<String, Object>();
    protected int numvars;
    protected ImmutableSet<Integer> domain;
    protected HashMap<Integer, Set<Integer>> neighbores = new HashMap<Integer, Set<Integer>>();
    protected HashMap<Integer, Boolean> constraints = new HashMap<Integer, Boolean>();
    protected ProblemType type;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNumberOfVariables(); i++) {
            for (Integer di : getDomainOf(i)) {
                for (int j = 0; j < getNumberOfVariables(); j++) {
                    for (Integer dj : getDomainOf(j)) {
                        sb.append((int) getConstraintCost(i, di, j, dj)).append(" ");
                    }
                }

                sb.append("\n");
            }
        }

        return sb.toString();
    }

    protected int calcId(int i, int j) {
        return i * numvars + j;
    }

    /**
     * @param var1
     * @param var2
     * @return true if there is a constraint between var1 and var2
     * operation cost: o(d^2)cc
     */
    @Override
    public boolean isConstrained(int var1, int var2) {
        int id = calcId(var1, var2);
        Boolean ans = constraints.get(id);
        if (ans == null) {

            return false;
//            OUTER_FOR:
//            for (Integer d1 : getDomainOf(var1)) {
//                for (Integer d2 : getDomainOf(var2)) {
//                    if (getConstraintCost(var1, d1, var2, d2) != 0) {
//                        found = true;
//                        break OUTER_FOR;
//                    }
//                }
//            }

//            return found;
        } else {
            return ans;
        }
    }

    /**
     * @param var1
     * @param val1
     * @param var2
     * @param val2
     * @return true if var1=val1 consistent with var2=val2
     */
    @Override
    public boolean isConsistent(int var1, int val1, int var2, int val2) {
        return getConstraintCost(var1, val1, var2, val2) == 0;
    }

    /**
     * return the domain size of the variable var
     * @param var
     * @return
     */
    @Override
    public int getDomainSize(int var) {
        return getDomainOf(var).size();
    }

    /**
     * @return this problem metadata
     */
    @Override
    public HashMap<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * @param var
     * @return all the variables that costrainted with the given var 
     * operation cost: o(n*d^2)cc
     */
    @Override
    public Set<Integer> getNeighbors(int var) {
        Set<Integer> l = this.neighbores.get(var);
        return l;
    }

    @Override
    public double getConstraintCost(int var, int val, Assignment ass) {
        double sum = 0;
        for (Integer av : ass.assignedVariables()) {
            sum += getConstraintCost(var, val, av, ass.getAssignment(av));
        }

        return sum;
    }

    abstract public void setConstraintCost(int var1, int val1, int var2, int val2, double cost);

    public ImmutableSet<Integer> getDomain() {
        return domain;
    }

    @Override
    public int getNumberOfVariables() {
        return numvars;
    }

    public void initialize(ProblemType type, int numberOfVariables, Set<Integer> domain) {
        this.domain = new ImmutableSet<Integer>(domain);
        this.numvars = numberOfVariables;
        this.neighbores = new HashMap<Integer, Set<Integer>>();
        for (int i=0; i<numvars; i++){
            neighbores.put(i, new HashSet<Integer>());
        }
        this.type = type;
        _initialize();
    }

    public ProblemType type() {
        return type;
    }

    protected abstract void _initialize();

    /**
     * we are not yet supports seperated domains but when we do - this function should be useful
     */
    @Override
    public ImmutableSet<Integer> getDomainOf(int var) {
        return domain;
    }
}