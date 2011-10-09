/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DateAndTimePV.java
 *
 * Created on 07/10/2011, 04:01:12
 */
package bc.swing.pfrm.views;

import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.ParamView;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author bennyl
 */
public class DateAndTimePV extends javax.swing.JPanel implements ParamView{

    /** Creates new form DateAndTimePV */
    public DateAndTimePV() {
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

        dtp = new org.jdesktop.swingx.JXDatePicker();
        tp = new javax.swing.JSpinner();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        dtp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dtpActionPerformed(evt);
            }
        });
        add(dtp, new java.awt.GridBagConstraints());

        tp.setModel(new javax.swing.SpinnerDateModel());
        tp.setEditor(new javax.swing.JSpinner.DateEditor(tp, "HH:mm"));
        tp.setMinimumSize(new java.awt.Dimension(64, 20));
        tp.setPreferredSize(new java.awt.Dimension(64, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(tp, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void dtpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dtpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dtpActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker dtp;
    private javax.swing.JSpinner tp;
    // End of variables declaration//GEN-END:variables

    public void setParam(BaseParamModel param) {
        onChange(param, param.getValue(), null);
    }

    public void reflectChangesToParam(BaseParamModel to) {
        Calendar cd = Calendar.getInstance();
        Calendar d = Calendar.getInstance();
        d.setTime(dtp.getDate());
        Calendar t = Calendar.getInstance();
        t.setTime((Date) tp.getValue());
        
        cd.set(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DATE), t.get(Calendar.HOUR_OF_DAY), t.get(Calendar.MINUTE));
    }

    public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
        Calendar cd = (Calendar) newValue;
        dtp.setDate(cd.getTime());
        tp.setValue(cd.getTime());
    }
}
