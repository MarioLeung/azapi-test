/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.conf.api;

import bgu.dcr.az.conf.FromStringPropertyValue;
import java.util.Collection;

/**
 * class that represent a configuration of a specific class this class allow you
 * to examine the class needed properties set their values, using the
 * ConfigurationUtils you can also save and load those settings to and from xml
 * and finally generate an object based on this configuration
 *
 * this class can also be generated with a visual data - this may allow a UI to
 * show and modify this configuration with good visual feedback to the user
 *
 * @author Benny Lutati
 */
public interface Configuration extends Iterable<Property>, Documented {

    String registeredName();

    Collection<Property> properties();

    Class configuredType();

    <T> T create() throws ConfigurationException;

    void configure(Object o) throws ConfigurationException;

    void configureProperty(Object o, String propertyName, PropertyValue pval) throws ConfigurationException;

    Property get(String name);

    Configuration loadFrom(Object o) throws ConfigurationException;

    default void configureProperty(Object o, String propertyName, String pval) throws ConfigurationException {
        configureProperty(o, propertyName, new FromStringPropertyValue(pval));
    }

}
