/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.map.api;

/**
 *
 * @author Zovadi
 */
public interface MapNode {
    long getId();
    
    MapNodeProperties properties();
}