/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import graphData.Graph;
import java.util.ArrayDeque;
import graphData.GraphData;
import java.util.HashMap;
import java.util.HashSet;
import services.searchAlgoritms.PathGenerator;
import services.searchAlgoritms.Dijkstra;
import services.searchAlgoritms.RandomDFS;
import statistics.Utility;

/**
 *
 * @author Eran
 */
public class RoadService implements Service {

    private static final String tag_hangout = "amenity";
    private static final String[] hangout = {"cafe", "restaurant", "fast_food", "pub", "bycicle_rental"};

    private static final String tag_shops = "shop";
    private static final String[] shops = {"cinema", "hairdresser", "supermarket", "clothes", "kiosk", "convenience", "bakery", "laundry"};

    private final Graph graph;
    private final PathGenerator pGen;
    private HashMap<String, TrafficLight> trafficLights;
    private HashMap<String, HashSet<String>> nearbyParkingLots; //associates a collection of nearby parking lots with a vertex  
    private HashMap<String, String> taggedToPL; //maps each tagged PL to the actual PL it belongs to.
    private HashMap<String, ParkingLot<Integer>> parkingLots; //maps each actual PL ID to an PL instance.
    private String[] entertainment;

    public RoadService() {
        System.out.print("Parsing graph ... ");
        GraphData parsed = GraphData.parseGraph("graph_tlv.txt");
        System.out.println("SUCCESS!");

        System.out.print("Converting GraphData to Graph ... ");
        this.graph = Graph.convertGraph(parsed, Utility.CAR_LENGTH);
        System.out.println("SUCCESS!");

        this.pGen = new RandomDFS(this.graph);
        this.trafficLights = new HashMap<>();
        this.parkingLots = new HashMap<>();
        this.taggedToPL = new HashMap<>();
        this.nearbyParkingLots = new HashMap<>();
    }

    @Override
    public void init() {
        initPointsOfInterest();
        initTrafficLights();
    }

    @Override
    public void tick() {
        // meanwhile, there nothing that should be done
    }

    private void initTrafficLights() {
        for (String vertex : this.graph.getConnectedVertexSet()) {
            if ("traffic_signals".equals(graph.getVertexAttributes(vertex).get("highway"))) {
//                if (this.graph.getIncomingVertices(vertex).isEmpty()) {
//                    System.out.println(vertex);
//                }
                trafficLights.put(vertex, new TrafficLight(Utility.calculate_TLInterval(), this.graph.getIncomingVertices(vertex)));
            }
        }
    }

    /**
     * Initialize parking-lots, shops, restaurants, etc.
     */
    private void initPointsOfInterest() {
        HashSet<String> init = new HashSet<>();
        HashSet<String> entertain = new HashSet<>();
        int c = 0;
        //get initial group of parking lots.
        for (String v : this.graph.getEntireVertexSet()) {
            if (taggedAsPL(v)) {
                init.add(v);
                taggedToPL.put(v, v);
                parkingLots.put(v, new ParkingLot<>(Utility.calculateLotCapacity()));
            } else if (taggedAsEntertainment(v)) {
                entertain.add(v);
            }
        }
        
        this.entertainment = new String[entertain.size()];
        entertain.toArray(this.entertainment);
        
        //tag other vertices as parking-lots according to the tagging radius.
        for (String v : this.graph.getConnectedVertexSet()) {
            for (String u : init) {
                boolean not_tagged = !taggedAsPL(v);
                boolean inside_tagging_radius = this.graph.distance(v, u) < Utility.LOT_TAGGING_RADIUS;
                if (not_tagged && inside_tagging_radius) {
                    taggedToPL.put(v, u);
                }
            }
        }

        //attach a collection of nearby parking-lots to each vertex in the graph.
        for (String v : this.graph.getEntireVertexSet()) {
            for (String lot : taggedToPL.keySet()) {
                if (this.graph.distance(lot, v) <= Utility.LOT_SEARCH_RADIUS) {
                    nearbyParkingLots.putIfAbsent(v, new HashSet<>());
                    nearbyParkingLots.get(v).add(lot);
                }
            }
        }
    }

    /**
     * Checks whether a vertex is tagged as a parking-lot.
     *
     * @param v
     * @return
     */
    public boolean taggedAsPL(String v) {
        boolean tag_parking = "parking".equals(this.graph.getVertexAttributes(v).get("amenity"));
        boolean tag_parking_entrance = "parking_entrance".equals(this.graph.getVertexAttributes(v).get("amenity"));
        return tag_parking || tag_parking_entrance;
    }

    public boolean taggedAsEntertainment(String v) {
        return taggedAsShop(v) || taggedAsHangout(v);
    }

    public boolean taggedAsHangout(String v) {
        HashMap<String, String> tags = this.graph.getVertexAttributes(v);
        if (tags.get(tag_hangout) == null) {
            return false;
        } else {
            for (String tag : hangout) {
                if (tag.equals(tags.get(tag_hangout))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean taggedAsShop(String v){
        HashMap<String, String> tags = this.graph.getVertexAttributes(v);
        if (tags.get(tag_shops) == null) {
            return false;
        } else {
            for (String tag : shops) {
                if (tag.equals(tags.get(tag_shops))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * generate a path between the two given vertices
     *
     * @param from
     * @param to
     * @return
     */
    public ArrayDeque<String> getPath(String from, String to) {
        return pGen.generate(from, to);
    }

    public double getEdgeLength(String from, String to) {
        return this.graph.calcEdgeLength(from, to);
    }

    /**
     * return the length (#km's) of the given path
     *
     * @param path
     * @return
     */
    public double getPathLengths(ArrayDeque<String> path) {
        double acc = 0;

        for (double len : this.graph.calcPathLength(path)) {
            acc += len;
        }
        return acc;
    }

    /**
     * return true if there's a slot available in the road (v1,v2).
     *
     * @param v1
     * @param v2
     * @return
     */
    public boolean segmentNotFull(String v1, String v2) {
        return !graph.reachedMaxCapacity(v1, v2);
    }

    /**
     * insert the car named id into the road (from, to)
     *
     * @param id - car's id
     * @param from - edge's source
     * @param to - edge's target
     */
    public void enterSegment(int id, String from, String to) {
        graph.insertCarToEdge(id, from, to);
    }

    /**
     * draw out the car named id from the road segment
     *
     * @param id - car's id
     */
    public void exitSegment(int id) {
        graph.removeCarFromEdge(id);
    }

    public String[] getCurrSegment(int id) {
        return null;
    }

    /**
     * Finds the closest car ahead of the car named 'id', and return its place
     * in the segment (in percentage). If there's no car ahead, 100% is
     * returned.
     *
     * @param id - car's id
     * @return
     */
    public double getPositionOfNextCar(int id) {
        return graph.carAhead(id);
    }

    public double getPositionOfNextCar(int id, ArrayDeque<String> path) {
        return getPositionOfNextCar(id) + ((getPositionOfNextCar(id) < 100) ? 0 : graph.lastCarInPath(path));
    }

    /**
     * get the position of car 'id' in the relevant segment (returned data
     * between 0 to 100).
     *
     * @param id - car's id
     * @return
     */
    public double getPercantage(int id) {
        return this.graph.getPercantage(id);
    }

    /**
     * set the position of car 'id' in the relevant segment to be 'per'
     *
     * @param id - car's id
     * @param per - between 0 to 100.
     */
    public void setPercantage(int id, double per) {
        this.graph.setPercantage(id, per);
    }

    /**
     * return two vertices - home and work addresses. Also ensure that it's
     * possible to reach from one vertex to the other.
     *
     * @return
     */
    public String[] generateWorkerInfo() {
        String[] vs = new String[2];
        ArrayDeque<String> homeToWork, workToHome;

        do {
            vs[0] = this.graph.getRandomVertex();
            vs[1] = this.graph.getRandomVertex();
            homeToWork = this.pGen.generate(vs[0], vs[1]);
            workToHome = this.pGen.generate(vs[1], vs[0]);
            if (this.graph.sameVertex(vs[0], vs[1]) || homeToWork.isEmpty() || workToHome.isEmpty()) {
                System.out.print("Trying again - ");
                if (homeToWork.isEmpty()) {
                    System.out.println("path (v1->v2) is empty!");
                } else if (workToHome.isEmpty()) {
                    System.out.println("path (v2->v1) is empty!");
                } else {
                    System.out.printf("same vertex: %s,%s\n", vs[0], vs[1]);
                }
            }
        } while (this.graph.sameVertex(vs[0], vs[1]) || homeToWork.isEmpty() || workToHome.isEmpty());
        return vs;
    }

    /**
     * Checks whether vertex src is the only vertex heading to dest.
     *
     * @param src
     * @param dest
     * @return
     */
    public boolean isOnlyIncomingRoad(String src, String dest) {
        return graph.isPartialRoad(src);
    }

    /**
     * Checks if the traffic-light on the edge (src, dest) is green in a
     * specific tick, or there's no traffic-light on that junction.
     *
     * @param src
     * @param dest
     * @param tick
     * @return
     */
    public boolean greenLight(String src, String dest, int tick) {
        if (this.trafficLights.get(dest) == null) {
            return true;
        } else {
            return src.equals(this.trafficLights.get(dest).getCurrent(tick));
        }
    }

    /**
     * Find a non-empty parking-lot nearby the destination, return null if
     * nothing was found.
     *
     * @param carID
     * @param src
     * @param dest
     * @return
     */
    public String findClosePL(int carID, String src, String dest) {
        if (this.nearbyParkingLots.get(dest) == null) {
            return null;
        }

        for (String taggedPL : this.nearbyParkingLots.get(dest)) {
            if (!accessible(src, taggedPL)) {
                continue;
            }
            String actualPL_name = this.taggedToPL.get(taggedPL);
            if (parkingLots.get(actualPL_name).addCar(carID)) {
//                System.out.println("FOUND PL!!");
                return taggedPL;
            }
        }
        return null;
    }

    public boolean accessible(String src, String taggedPL) {
        return !this.pGen.generate(src, taggedPL).isEmpty() && !this.pGen.generate(taggedPL, src).isEmpty();
    }

    /**
     * Removes a car from the parking-lot.
     *
     * @param carID
     * @param taggedPL
     */
    public void exitFromPL(int carID, String taggedPL) {
        String actualPL_name = this.taggedToPL.get(taggedPL);
        parkingLots.get(actualPL_name).removeCar(carID);
    }

    /**
     * Return the attributes of the given edge.
     *
     * @param src
     * @param dest
     * @return
     */
    public HashMap<String, String> getEdgeAttributes(String src, String dest) {
        return this.graph.getEdgeAttributes(src, dest);
    }

    public int waitingToEnterJunction(String junction) {
        HashSet<String> sources = graph.getIncomingVertices(junction);
        int count = 0;
        for (String src : sources) {
            if (this.graph.getMostFarPos(src, junction) == 100) {
                count++;
            }
        }
        return count;
    }

    public String getRandomHotSpot() {
        int indx = Utility.rand.nextInt(this.entertainment.length);
        return this.entertainment[indx];
    }
}