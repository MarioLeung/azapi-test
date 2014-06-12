/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.execs.sim.nest;

/**
 *
 * @author bennyl
 */
public class ContinuationMediator {

    Continuation continuation;

    /**
     * provide a continuation to call to after the operation completed
     *
     * @param c
     */
    public void andWhenDoneDo(Continuation c) {
        this.continuation = c;
    }

    /**
     * execute the defined continuation
     */
    public void executeContinuation() {
        if (this.continuation != null) {
            this.continuation.doContinue();
        }
    }

}