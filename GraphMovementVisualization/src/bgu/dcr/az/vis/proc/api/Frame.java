/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.vis.proc.api;

/**
 *
 * @author Zovadi
 */
public interface Frame {
    
    Frame addAction(Action action);
    
    void initialize(Player player);
    
    void update();
}
