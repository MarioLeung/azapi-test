/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bgu.dcr.az.abm.api;

/**
 *
 * @author Eran
 */
public interface WorldService {
    void init(World w);
    
    void tick(int tickNumber);
}