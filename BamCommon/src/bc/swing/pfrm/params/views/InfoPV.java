/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InfoView.java
 *
 * Created on 11/09/2011, 11:27:39
 */
package bc.swing.pfrm.params.views;

import bc.swing.pfrm.ano.ViewHints;
import bc.swing.pfrm.params.ParamModel;
import bc.swing.pfrm.params.ParamView;
import java.awt.GridBagConstraints;

/**
 *
 * @author bennyl
 */
public class InfoPV extends javax.swing.JPanel implements ParamView {

    /** Creates new form InfoView */
    public InfoPV() {
        initComponents();
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

        infoName = new javax.swing.JLabel();
        infoData = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        infoName.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(infoName, gridBagConstraints);

        infoData.setForeground(new java.awt.Color(102, 153, 255));
        infoData.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(infoData, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel infoData;
    private javax.swing.JLabel infoName;
    // End of variables declaration//GEN-END:variables

    public void setModel(ParamModel model) {
        ViewHints vh = model.getViewHints();
        GridBagConstraints gbc;
        if (vh != null) {
            if (vh.orianitation().equals(ViewHints.Orianitation.VERTICAL)) {
                remove(infoData);
                gbc = new java.awt.GridBagConstraints();
                gbc.weightx = 1.0;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                add(infoData, gbc);
            }
        }

        infoName.setText(model.getName() + ":");
        infoData.setText(model.getValue().toString());
        infoName.setIcon(model.getIcon());
        
        validate();
        repaint();
    }

    public void reflectChanges(ParamModel to) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onChange(ParamModel source, Object newValue, Object deltaHint) {
        infoData.setText(newValue.toString());
        validate();
        repaint();
    }
}
