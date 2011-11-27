/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SingleVariableEditor.java
 *
 * Created on 26/11/2011, 23:49:25
 */
package bc.ui.swing.configurable;

import bc.dsl.ReflectionDSL;
import bgu.csp.az.api.infra.VariableMetadata;

/**
 *
 * @author bennyl
 */
public class SingleVariableEditor extends javax.swing.JPanel {

    VariableMetadata var;
    
    /** Creates new form SingleVariableEditor */
    public SingleVariableEditor() {
        initComponents();
    }
    
    public void setModel(VariableMetadata var){
        this.var = var;
        this.varDesc.setText(var.getDescription());
        this.varName.setText(var.getName());
        this.varVal.setText(var.getCurrentValue().toString());
    }

    public VariableMetadata getModel(){
        return var;
    }
    
    /**
     * can return null if the type not contain a valueOf function
     * @return 
     */
    public Object getValue(){
        String val = varVal.getText();
        Object ret = ReflectionDSL.valueOf(val, var.getType());
        return ret;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        varName = new javax.swing.JLabel();
        spacer = new javax.swing.JLabel();
        varVal = new javax.swing.JTextField();
        varDesc = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        varName.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        varName.setForeground(new java.awt.Color(51, 51, 51));
        varName.setText("p1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 3, 0);
        add(varName, gridBagConstraints);

        spacer.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        spacer.setForeground(new java.awt.Color(51, 51, 51));
        spacer.setText("=");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 3, 0);
        add(spacer, gridBagConstraints);

        varVal.setBackground(new java.awt.Color(153, 153, 153));
        varVal.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        varVal.setForeground(new java.awt.Color(51, 102, 255));
        varVal.setText("defaultValue");
        varVal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));
        varVal.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 3, 3);
        add(varVal, gridBagConstraints);

        varDesc.setForeground(new java.awt.Color(121, 141, 141));
        varDesc.setText("Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(varDesc, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel spacer;
    private javax.swing.JLabel varDesc;
    private javax.swing.JLabel varName;
    private javax.swing.JTextField varVal;
    // End of variables declaration//GEN-END:variables
}
