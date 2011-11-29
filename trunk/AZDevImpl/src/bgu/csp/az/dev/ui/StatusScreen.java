/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StatusScreen.java
 *
 * Created on 24/11/2011, 16:25:11
 */
package bgu.csp.az.dev.ui;

import bc.dsl.SwingDSL;
import bc.ui.swing.visuals.Visual;
import bgu.csp.az.api.infra.Execution;
import bgu.csp.az.api.infra.Experiment;
import bgu.csp.az.api.infra.Round;
import bgu.csp.az.dev.ExecutionUnit;
import com.sun.java.swing.plaf.motif.MotifProgressBarUI;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoundedRangeModel;
import javax.swing.Icon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author bennyl
 */
public class StatusScreen extends javax.swing.JPanel {

    private static final Icon ROUND_WAIT_ICON = SwingDSL.resIcon("round-wait");
    private static final Icon ROUND_DONE_ICON = SwingDSL.resIcon("round-done");
    private static final Icon ROUND_PLAY_ICON = SwingDSL.resIcon("round-play");

    
    private LinkedList<Visual> visualRounds;

    /** Creates new form StatusScreen */
    public StatusScreen() {
        initComponents();
        execProgress.setUI(new MotifProgressBarUI());
    }

    void setModel(Experiment experiment) {
        visualRounds = Visual.adapt(experiment.getRounds(), new Visual.VisualGen() {

            @Override
            public Visual gen(Object it) {
                Round r = (Round) it;
                return new Visual(it, r.getName(), "", ROUND_WAIT_ICON);
            }
        });
        roundList.setItems(visualRounds);

        roundList.addSelectionListner(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                List<Visual> items = roundList.getSelectedItems();
                if (items.isEmpty()) {
                    roundData.unSetData();
                } else {
                    roundView.setModel((Round) items.get(0).getItem());
                    roundData.setData(roundDataScroll);
                }
            }
        });
        // EXECUTION PROGRESS BAR
        final BoundedRangeModel mod = execProgress.getModel();
        mod.setMinimum(0);

        final int expLength = experiment.getLength();

        mod.setMaximum(expLength);
        progressLabel.setText("Execution 1 " + " of " + expLength);
        ExecutionUnit.UNIT.addExperimentListener(new Experiment.ExperimentListener() {

            @Override
            public void onExpirementStarted(Experiment source) {
            }

            @Override
            public void onExpirementEnded(Experiment source) {
                for (Visual v : visualRounds) {
                    v.setIcon(ROUND_DONE_ICON);
                }

                roundList.revalidate();
                roundList.repaint();
            }

            @Override
            public void onNewRoundStarted(Experiment source, Round round) {
                for (Visual v : visualRounds) {
                    if (v.getItem().equals(round)) {
                        v.setIcon(ROUND_PLAY_ICON);
                        break;
                    } else {
                        v.setIcon(ROUND_DONE_ICON);
                    }
                }

                roundList.revalidate();
                roundList.repaint();
            }

            @Override
            public void onNewExecutionStarted(Experiment source, Round round, Execution exec) {
            }

            @Override
            public void onExecutionEnded(Experiment source, Round round, Execution exec) {
                mod.setValue(mod.getValue() + 1);
                progressLabel.setText("Execution " + (mod.getValue()) + " of " + expLength);
            }
        });
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

        roundDataScroll = new javax.swing.JScrollPane();
        roundView = new bgu.csp.az.dev.ui.RoundView();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        progressLabel = new javax.swing.JLabel();
        execProgress = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        roundList = new bc.ui.swing.lists.StripeList();
        roundData = new bc.ui.swing.useful.DataPanel();

        roundDataScroll.setBorder(null);
        roundDataScroll.setViewportView(roundView);

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/monitor.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel1, gridBagConstraints);

        progressLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        progressLabel.setForeground(new java.awt.Color(255, 255, 255));
        progressLabel.setText("Execution 0 of 16");
        progressLabel.setDoubleBuffered(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel2.add(progressLabel, gridBagConstraints);

        execProgress.setBackground(null);
        execProgress.setForeground(new java.awt.Color(255, 255, 255));
        execProgress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(71, 71, 71), 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 0);
        jPanel2.add(execProgress, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(120, 120, 120));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel11.setBackground(new java.awt.Color(120, 120, 120));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 3));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Executing Rounds");
        jPanel11.add(jLabel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(jPanel11, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        roundList.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 3, new java.awt.Color(153, 153, 153)));
        roundList.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        roundList.setMinimumSize(new java.awt.Dimension(200, 24));
        roundList.setOddBackColor(new java.awt.Color(230, 230, 230));
        roundList.setOddForeColor(new java.awt.Color(61, 61, 61));
        roundList.setPreferredSize(new java.awt.Dimension(200, 194));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(roundList, gridBagConstraints);

        roundData.setNoDataForeColor(new java.awt.Color(255, 255, 255));
        roundData.setNoDataText("No Round Selected");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel3.add(roundData, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        jPanel1.add(jPanel3, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar execProgress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel progressLabel;
    private bc.ui.swing.useful.DataPanel roundData;
    private javax.swing.JScrollPane roundDataScroll;
    private bc.ui.swing.lists.StripeList roundList;
    private bgu.csp.az.dev.ui.RoundView roundView;
    // End of variables declaration//GEN-END:variables
}
