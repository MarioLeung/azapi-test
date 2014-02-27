/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.vis.player.api;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @author Zovadi
 */
public interface Player {

    DoubleProperty millisPerFrameProperty();
    
    IntegerProperty framesPerSecondProperty();
    
    VisualScene getScene();

    void play(FramesStream stream);
    
    void pause();
    
    void resume();
    
    void stop();
    
    boolean isPaused();
    
    boolean isStopped();
}
