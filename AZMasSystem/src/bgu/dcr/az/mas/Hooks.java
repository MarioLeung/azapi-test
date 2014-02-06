/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas;

import bgu.dcr.az.api.Agent;
import bgu.dcr.az.api.Message;
import bgu.dcr.az.api.exen.ExecutionResult;
import bgu.dcr.az.mas.Execution;
import bgu.dcr.az.mas.cp.CPSolution;

/**
 * this collection of interfaces that the simple agent supports hooking via
 *
 * @author bennyl
 */
public class Hooks {

    /**
     * describe hook definition with automatically hooking functinality
     */
    public static interface HookDefinition {

        void hookInto(Execution ex);
    }

    /**
     * callback that will get called before message sent - can be attached to
     * simple agent.
     */
    public static abstract class BeforeMessageSentHook implements HookDefinition {

        public abstract void hook(int senderId, int recepientId, Message msg);

        @Override
        public void hookInto(Execution ex) {
            ex.hook(BeforeMessageSentHook.class, this);
        }
    }

    /**
     * callback that will get called before message processed by the attached
     * agent - can be attachd to simple agent.
     */
    public static abstract class BeforeMessageProcessingHook implements HookDefinition {

        /**
         * callback implementation
         *
         * @param msg
         */
        public abstract void hook(Agent a, Message msg);

        @Override
        public void hookInto(Execution ex) {
            ex.hook(BeforeMessageProcessingHook.class, this);
        }
    }

    public static abstract class AfterMessageProcessingHook implements HookDefinition {

        /**
         * callback implementation
         *
         * @param msg
         */
        public abstract void hook(Agent a, Message msg);

        @Override
        public void hookInto(Execution ex) {
            ex.hook(AfterMessageProcessingHook.class, this);
        }
    }

    public static abstract class BeforeCallingFinishHook implements HookDefinition {

        public abstract void hook(Agent a);

        @Override
        public void hookInto(Execution ex) {
            ex.hook(BeforeCallingFinishHook.class, this);
        }
    }

    public static abstract class ReportHook implements HookDefinition {

        String reportName;

        public ReportHook(String reportName) {
            this.reportName = reportName;
        }

        public String getReportName() {
            return reportName;
        }

        public abstract void hook(Agent a, Object[] args);

        @Override
        public void hookInto(Execution ex) {
            ex.hook(ReportHook.class, this);
        }
    }

    public static abstract class TickHook implements HookDefinition {

        public abstract void hook(int tickNumner);

        @Override
        public void hookInto(Execution ex) {
            ex.hook(TickHook.class, this);
        }
    }

    public static abstract class TerminationHook implements HookDefinition {

        public abstract void hook(ExecutionResult result);

        @Override
        public void hookInto(Execution ex) {
            ex.hook(TerminationHook.class, this);
        }
    }
}