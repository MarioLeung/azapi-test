/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bc.swing.models;

import bc.dsl.JavaDSL;

/**
 *
 * @author bennyl
 */
public abstract class DataExtractor<T> {

    String[] supported;

    public DataExtractor(String... supported) {
        this.supported = supported;
    }

    public abstract Object getData(String dataName, T from);

    public String[] getSupportedDataNames() {
        return supported;
    }

    public static class BeanDataExtractor<T> extends DataExtractor<T> {

        public BeanDataExtractor(String... supported) {
            super(supported);
        }

        
        @Override
        public Object getData(String dataName, T from) {
            try {
                return from.getClass().getMethod("get" + JavaDSL.camelCase(dataName)).invoke(from);
            } catch (Exception ex) {
                return "N/A";
            } 
        }
        
    }
}
