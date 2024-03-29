/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attributes.car;

import agents.Agent;
import attributes.Behavior;
import agentData.CarData;
import agentData.TankData;
import data.events.impl.MoveEvent;
import services.ClockService;
import services.RoadService;
import testsimulator.TestSimulator;

/**
 *
 * @author Eran
 */
public class DriveSegment extends Behavior {
    
    private final CarData carData;
    private final TankData tankData;
    private final RoadService roadService;
    private final ClockService clockService;
    
    public DriveSegment(Agent a) {
        super(a);
        try{
            this.carData = a.getData(CarData.class);
            this.tankData = a.getData(TankData.class);
            this.roadService = a.getService(RoadService.class);
            this.clockService = a.getService(ClockService.class);
        } catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Can't instantiate the behavior " + getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @Override
    public void behave(String currState) {
        double percentage = roadService.getPercantage(id), tmp = percentage;
//        int consume = clockService.getTicks();
//        System.out.println(String.format("Tick[%d]:: driving road segment ... %f --> %f", consume, percentage, Math.min(percentage + this.carData.getSpeed(), roadService.getPositionOfNextCar(id))));
        
        //try to proceed, considering the cars ahead.
        percentage = Math.min(percentage + this.carData.getSpeed(), roadService.getPositionOfNextCar(id));
        double edgeLen = this.roadService.getEdgeLength(carData.getCurrSource(), carData.getCurrDestination());
        
        double distance = (percentage - tmp)/100.0*edgeLen;
        this.tankData.consume(distance);
        
        MoveEvent moveEvent = new MoveEvent(id, carData.getCurrSource(), carData.getCurrDestination(), roadService.getPercantage(id), percentage);
//        if(carData.getCurrSource() == null || carData.getCurrDestination() == null)
        System.out.printf("EVENT[%d]:: (%s, %s, %f, %f)\n",id, carData.getCurrSource(), carData.getCurrDestination(), roadService.getPercantage(id), percentage);
        TestSimulator.eventWriter.writeEvent(TestSimulator.output, moveEvent);
        roadService.setPercantage(id, percentage);
    }
}
