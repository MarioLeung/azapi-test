/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.dcr.api.problems;

import bgu.dcr.az.dcr.api.Assignment;
import bgu.dcr.az.dcr.util.ImmutableSet;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author bennyl
 */
public interface ImmutableProblem {
    
    /**
     * @return the problem type.
     */
    ProblemType type();

    /**
     *
     * @param var1
     * @param val1
     * @param var2
     * @param val2
     * @return the cost of assigning var1=val1 when var2=val2
     */
    int getConstraintCost(int var1, int val1, int var2, int val2);

    /**
     *
     * @param var1
     * @param val1
     * @return the cost of assigning var1=val1
     */
    int getConstraintCost(int var1, int val1);

    /**
     * return the cost of the k-ary constraint represented by the given assignment
     * @param ass
     * @return 
     */
    int getConstraintCost(Assignment ass);
    
    /**
     * return the domain of the given variable
     * @param var
     * @return
     */
    ImmutableSet<Integer> getDomainOf(int var);

    /**
     * return the domain size of the variable var
     * @param var
     * @return
     */
    int getDomainSize(int var);

    /**
     * @return this problem metadata
     */
    HashMap<String, Object> getMetadata();

    /**
     * @param var
     * @return all the variables that constrained with the given variable
     */
    Set<Integer> getNeighbors(int var);

    /**
     * @return the number of variables defined in this problem
     */
    int getNumberOfVariables();

    /**
     * @param var1
     * @param val1
     * @param var2
     * @param val2
     * @return true if var1=val1 consistent with var2=val2
     */
    boolean isConsistent(int var1, int val1, int var2, int val2);

    
    /**
     * @param var1
     * @param var2
     * @return true if there is a constraint between var1 and var2
     * operation cost: o(d^2)cc
     */
    boolean isConstrained(int var1, int var2);
    
    /**
     * return the cost of the given assignment (taking into consideration all the constraints that apply to it)
     * @param a
     * @return 
     */
    int calculateCost(Assignment a);
    
}
