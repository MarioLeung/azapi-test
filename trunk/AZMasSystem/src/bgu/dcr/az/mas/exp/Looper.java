/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas.exp;

import bgu.dcr.az.anop.conf.Configuration;
import java.util.Collection;

/**
 *
 * @author User
 */
public interface Looper {

    int count() throws ExperimentExecutionException;

    void configure(int i, Collection<Configuration> configurations) throws ExperimentExecutionException;
}