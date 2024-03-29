/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.vis.presets.map;

import bgu.dcr.az.vis.controls.ui.PlayerControls;
import bgu.dcr.az.vis.newplayer.SimplePlayer;
import bgu.dcr.az.vis.presets.map.drawer.FPSDrawer;
import bgu.dcr.az.vis.presets.map.drawer.GroupDrawer;
import bgu.dcr.az.vis.presets.map.drawer.SimpleDrawer;
import data.events.impl.Tick;
import data.events.impl.test.EventsTester;
import data.map.impl.wersdfawer.groupbounding.GroupBoundingQuery;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Zovadi
 */
public class MapTester extends Application {

    private static MapVisualScene vs;
    private GroupBoundingQuery boundingQuery;
    private SimpleDrawer drawer;

    @Override
    public void start(Stage stage) throws Exception {

        boundingQuery = new GroupBoundingQuery();
        drawer = new SimpleDrawer(boundingQuery);

        boundingQuery.createGroup("*FPS*", "*FPS*", true);

        //change to beershevagraph.txt to get beersheva back
        //graph2_1.txt is telaviv
        //graph_b7.txt is new b7
        //"graph_try.txt";
        //"graph_manhattan.txt"
        //"graph_tlv.txt"
        //"graph_b7.txt"
        vs = new MapVisualScene(1000, "graph_tlv.txt", boundingQuery, drawer);

        SimplePlayer player = new SimplePlayer(boundingQuery, drawer, 1000, 0);

        boundingQuery.addMetaData("*FPS*", GroupDrawer.class, new FPSDrawer(drawer, player));

        FramesGenerator fg = new FramesGenerator(player, boundingQuery);

        BorderPane bp = new BorderPane();
        bp.setCenter(vs);
        bp.setBottom(new PlayerControls(player));

        Scene scene = new Scene(bp);

        stage.setScene(scene);
        stage.show();
        stage.addEventFilter(WindowEvent.WINDOW_HIDING, e -> fg.cancel());

        player.play();

        fg.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class FramesGenerator extends Thread {

        private boolean isStopped = false;
        private final EventsTester eventTester;
        private SimplePlayer player;
        private final GroupBoundingQuery boundingQuery;

        public FramesGenerator(SimplePlayer player, GroupBoundingQuery boundingQuery) {
            this.player = player;
            this.boundingQuery = boundingQuery;
            MapCanvasLayer layer = (MapCanvasLayer) vs.getLayer(MapCanvasLayer.class);
            eventTester = new EventsTester(layer.getGraphData());
//            eventTester.write();

        }

        @Override
        public void run() {            
            
            //adds a change listener to know when to play the next tick.
            player.addFrameFinishListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1 && !isStopped) {
                        Tick tick = eventTester.read();
                        eventTester.AddNewMovesFromTick(tick, player, boundingQuery);
                    }
                }
            });
            
            //plays the first tick
            Tick tick = eventTester.read();
            if (!isStopped) {
                eventTester.AddNewMovesFromTick(tick, player, boundingQuery);
            }

        }

        public void cancel() {
            isStopped = true;
            stop();
        }
    };
}
