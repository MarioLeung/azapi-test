/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.api.tools;

import bgu.csp.az.api.Agent;
import bgu.csp.az.api.Message;
import bgu.csp.az.api.agt.SimpleAgent;
import bgu.csp.az.api.Hooks.BeforeMessageProcessingHook;
import bgu.csp.az.api.Hooks.BeforeMessageSentHook;
import bgu.csp.az.api.agt.SimpleMessage;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Mostly taken from: 
 * Asynchronous Forward Bounding for Distributed COPs
 * by:
 * Amir Gershman AMIRGER@CS.BGU.AC.IL
 * Amnon Meisels AM@CS.BGU.AC.IL
 * Roie Zivan ZIVANR@CS.BGU.AC.IL
 * Department of Computer Science,
 * Ben-Gurion University of the Negev,
 * Beer-Sheva, 84-105, Israel
 * 
 * http://www.bgu.ac.il/~zivanr/files/AFB_JAIR09.pdf
 * 
 * The Time-Stamp Mechanism (Nguyen et al., 2004; Meisels & Zivan, 2007)
 * ---------------------------------------------------------------------
 * The requirements from this mechanism are
 * that given two messages with two different partial assignments, it must determine which one of
 * them is obsolete. An obsolete partial assignment is one that was abandoned by the search process
 * because one of the assigned agen
 * ts has changed its assignment. This requirement is accomplished by
 * the time-stamping mechanism in the following way. Each agent keeps a local running-assignment
 * counter. Whenever it performs an assignment it increments its local counter. Whenever it sends
 * a message containing its assignment, the agent copies its current counter onto the message. Each
 * message holds a vector containing the counters of the agents it passed through. The i-th element
 * of the vector corresponds to Agent i’s counter. This vector is in fact the time-stamp. A lexicographical
 * comparison of two such vectors will reveal which time-stamp is more up-to-date.
 * Each agent saves a copy of what it knows to be the most up-to-date time-stamp. When receiving
 * a new message with a newer time-stamp, the agent updates its local saved “latest” time-stamp.
 * Suppose agent Ai receives a message with a time-stamp that is lexicographically smaller than the
 * locally saved “latest”, by comparing the ﬁrst i − 1 elements of the vector. This means that the
 * message was based on a combination of assignments which was already abandoned and thismessage
 * is discarded. Only when the message’s time-stamp in the ﬁrst i − 1 elemental is equal or greater
 * than the locally saved ”best” time-stamp is the message processed further.
 * The vector’s counters might appear to require a lot of space, as the number of assignments can
 * grow exponentially in the number of agents. However, if the agent (Ai) resets its local counter to
 * zero each time the assignments of higher priority agents are altered, the counters will remain small
 * (log of the size of the value domain), and the mechanism will remain correct.
 * 
 * Limitations:
 * ------------
 * this time stamp mechanism is vulnerable to dynamic variable ordering
 * because of the lexicographic comparison, in vulnerable i means it will not work!
 * dont use it if this is one of the algorithm requirements!
 * 
 * @author bennyl
 */
public class TimeStamp implements Comparable<TimeStamp>, Serializable {

    private int[] id;
    private int usingAgentId;

    public TimeStamp(Agent a) {
        this.id = new int[a.getNumberOfVariables()];
        this.usingAgentId = a.getId();
    }

    public void register(final SimpleAgent a, final OldMessageHandlingPolicy policy) {
        a.hookIn(new BeforeMessageSentHook() {

            @Override
            public void hook(SimpleMessage msg) {
                msg.getMetadata().put("TimeStamp", TimeStamp.this);
            }
        });

        a.hookIn(new BeforeMessageProcessingHook() {

            @Override
            public void hook(SimpleMessage msg) {
                final TimeStamp ts = (TimeStamp) msg.getMetadata().get("TimeStamp");
                final TimeStamp timestamp = TimeStamp.this;
                if (timestamp.compareTo(ts) > 0
                        && !msg.getName().equals("NEW_SOLUTION")) {
                    a.log("Dumping Message - timestamp older then mine");
                    msg.flag(policy.flag);
                }

                if (timestamp.compareTo(ts) < 0) {
                    timestamp.set(ts);
                }

            }
        });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeStamp) {
            TimeStamp other = (TimeStamp) obj;
            return Arrays.equals(this.id, other.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Arrays.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : id) {
            sb.append(i).append('.');
        }

        return sb.toString();
    }

    @Override
    public int compareTo(TimeStamp o) {
        for (int i = 0; i < this.id.length; i++) {
            if (this.id[i] > o.id[i]) {
                return 1;
            }
            if (this.id[i] < o.id[i]) {
                return -1;
            }
        }
        return 0;
    }

    public int getLocalTime() {
        return id[usingAgentId];
    }

    public void setLocalTime(int localTime) {
        id[usingAgentId] = localTime;
    }

    public void incLocalTime() {
        id[usingAgentId]++;
    }

    public void set(TimeStamp t) {
        for (int i = 0; i < t.id.length; i++) {
            if (i == usingAgentId) {
                continue;
            }
            this.id[i] = t.id[i];
        }
    }

    public static enum OldMessageHandlingPolicy {

        DISCARD_OLD_MESSAGES(Message.DISCARDED),
        FLAG_OLD_MESSAGES_AS_OLD(Message.OLD);
        
        int flag;

        private OldMessageHandlingPolicy(int flag) {
            this.flag = flag;
        }

        public int getFlag() {
            return flag;
        }
        
    }
}
