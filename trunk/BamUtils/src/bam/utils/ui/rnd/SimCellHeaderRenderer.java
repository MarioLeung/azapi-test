/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SimCellHeaderRendere.java
 *
 * Created on 17/01/2011, 18:55:27
 */

package bam.utils.ui.rnd;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author bennyl
 */
public class SimCellHeaderRenderer extends javax.swing.JPanel implements TableCellRenderer{

    /** Creates new form SimCellHeaderRendere */
    public SimCellHeaderRenderer() {
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

        inLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(46, 137, 255));

        inLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        inLabel.setForeground(new java.awt.Color(255, 255, 255));
        inLabel.setText("jLabel1");
        add(inLabel);
    }// </editor-fold>//GEN-END:initComponents

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        inLabel.setText(value.toString());
        return this;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel inLabel;
    // End of variables declaration//GEN-END:variables

}