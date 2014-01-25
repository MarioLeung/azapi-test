/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas;

import bgu.dcr.az.api.Message;
import java.util.Queue;

/**
 * a message router holds the collection of all the agents controllers and is
 * responsible to distribute messages amongs them (it can support message delay
 * strategies maybe?)
 *
 * @author User
 */
public interface MessageRouter extends ExecutionService {

    void route(Message m, int agent);

    Queue<AZIPMessage> getMessageQueue(int controllerId);
}