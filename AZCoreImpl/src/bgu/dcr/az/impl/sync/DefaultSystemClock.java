/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.impl.sync;

import bgu.dcr.az.api.Hooks.TickHook;
import bgu.dcr.az.api.infra.Execution;
import bgu.dcr.az.api.SystemClock;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 *
 * @author bennyl
 */
public class DefaultSystemClock implements SystemClock {

    private CyclicBarrier barrier;
    private long time;
    private volatile long closed = -2;
    private volatile boolean ticked = false;
    private volatile long notified = -1;
    private List<TickHook> tickHooks = new LinkedList<TickHook>();
    private Semaphore tickHookLock = new Semaphore(1);

    public DefaultSystemClock() {
        this.time = 0;
    }

    public void setExcution(Execution exc) {
        System.out.println("DefaultSystemClock: Barrier Set to Be: " + exc.getNumberOfAgentRunners());
        this.barrier = new CyclicBarrier(exc.getNumberOfAgentRunners());
    }

    @Override
    public void tick() throws InterruptedException {
        try {
            ticked = true;
            long nextTime = time + 1;

            barrier.await();

//            System.out.println(Thread.currentThread().getName() + " out of berrier");
            if (closed == nextTime - 1) {
//                System.out.println(Thread.currentThread().getName() + " found closed clock - out!");
                return;
            }
            /**
             * visibility problem should not occure here as every thread must pass through the following line
             * thus its local cpu cache will get updated.
             */
            time = nextTime;

            tickHookLock.acquire();
            try {
                if (notified < time) {
                    notified = time;
//                    System.out.println(Thread.currentThread().getName() + " notifing tick " + time);

                    for (TickHook t : tickHooks) {
                        t.hook(this);
                    }
                }
            } finally {
                tickHookLock.release();
            }

            ticked = false;

        } catch (BrokenBarrierException ex) {
            if (closed == time) {
                return;
            }
            System.err.println("got BrokenBarrierException, translating it to InterruptedException (DefaultSystemClock)");
            throw new InterruptedException(ex.getMessage());
        }
    }

    @Override
    public long time() {
        return time;
    }

    @Override
    public void close() {
        if (closed == -2) {
            closed = time + 1;
        }
    }

    public boolean isClosed() {
        return closed == time;
    }

    @Override
    public boolean isTicked() {
        return ticked;
    }

    @Override
    public void hookIn(TickHook hook) {
        tickHooks.add(hook);
    }
}
