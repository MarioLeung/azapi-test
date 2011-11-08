/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExecutionStatisticalView.java
 *
 * Created on 18/08/2011, 10:41:40
 */
package bgu.csp.az.dev.pui.sgrp;

import bc.dsl.PageDSL;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.FieldParamModel.ChangeListener;
import bc.swing.pfrm.Page;
import bc.swing.pfrm.PageLayout;
import java.awt.Color;
import org.jdesktop.swingx.painter.RectanglePainter;

/**
 *
 * @author bennyl
 */
public class StatisticsView extends javax.swing.JPanel implements PageLayout {

    public static final String GRAPHS_TREE_ROLE = "GRAPHS TREE ROLE";
    public static final String MASTER_GRAPH_ROLE = "MASTER GRAPH ROLE";

    /** Creates new form ExecutionStatisticalView */
    public StatisticsView() {
        initComponents();
        descriptionLabel.setBackgroundPainter(new RectanglePainter(new Color(245, 245, 245), null));
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

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        graphsContainetPan = new javax.swing.JPanel();
        graphPan = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        descriptionLabel = new org.jdesktop.swingx.JXLabel();
        chartsPanel = new javax.swing.JPanel();
        masterGraphPan = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        slaveGrapthPan1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        slaveGraphPan2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        graphsContainetPan.setBackground(new java.awt.Color(245, 245, 245));
        graphsContainetPan.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        graphsContainetPan.add(graphPan, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(graphsContainetPan, gridBagConstraints);

        jLabel2.setBackground(new java.awt.Color(245, 245, 245));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/information-frame.png"))); // NOI18N
        jLabel2.setText("Description");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(245, 245, 245)));
        jLabel2.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 24));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 15;
        jPanel1.add(jLabel2, gridBagConstraints);

        descriptionLabel.setBackground(new java.awt.Color(245, 245, 245));
        descriptionLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 10, 0, 5, new java.awt.Color(245, 245, 245)));
        descriptionLabel.setForeground(new java.awt.Color(51, 102, 255));
        descriptionLabel.setText("Description Here");
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setMaximumSize(new java.awt.Dimension(200, 0));
        descriptionLabel.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 5;
        jPanel1.add(descriptionLabel, gridBagConstraints);

        jSplitPane1.setLeftComponent(jPanel1);

        chartsPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartsPanel.setLayout(new java.awt.GridBagLayout());

        masterGraphPan.setBackground(new java.awt.Color(255, 255, 255));
        masterGraphPan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        masterGraphPan.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(245, 245, 245));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 3));

        jLabel1.setText("Master View");
        jPanel2.add(jLabel1);

        masterGraphPan.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        chartsPanel.add(masterGraphPan, gridBagConstraints);

        slaveGrapthPan1.setBackground(new java.awt.Color(255, 255, 255));
        slaveGrapthPan1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        slaveGrapthPan1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 3));

        jLabel3.setText("Slave View 1");
        jPanel3.add(jLabel3);

        slaveGrapthPan1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        chartsPanel.add(slaveGrapthPan1, gridBagConstraints);

        slaveGraphPan2.setBackground(new java.awt.Color(255, 255, 255));
        slaveGraphPan2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        slaveGraphPan2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 3));

        jLabel4.setText("Slave View 2");
        jPanel4.add(jLabel4);

        slaveGraphPan2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        chartsPanel.add(slaveGraphPan2, gridBagConstraints);

        jSplitPane1.setRightComponent(chartsPanel);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartsPanel;
    private org.jdesktop.swingx.JXLabel descriptionLabel;
    private javax.swing.JPanel graphPan;
    private javax.swing.JPanel graphsContainetPan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel masterGraphPan;
    private javax.swing.JPanel slaveGraphPan2;
    private javax.swing.JPanel slaveGrapthPan1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setPage(Page model) {
        PageDSL.fillByRole(model, graphPan, GRAPHS_TREE_ROLE);
        model.getParamsWithRole(GRAPHS_TREE_ROLE).get(0).addSelectionListner(new ChangeListener() {

            @Override
            public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
                descriptionLabel.setText(source.getPage().getModel().provideParamValueDescription(source.getName(), newValue));
            }
        });
        
        PageDSL.insertToCenterByRole(model, masterGraphPan, MASTER_GRAPH_ROLE);
    }

    @Override
    public void onDispose() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}