/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JIPAddress.java
 *
 * Created on 03/07/2011, 07:20:04
 */
package bc.swing.pfrm.params.views;

import bc.swing.pfrm.params.BaseParamModel;
import bc.swing.pfrm.params.ParamView;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import static bc.dsl.JavaDSL.*;

/**
 *
 * @author bennyl
 */
public class IPAddressPV extends javax.swing.JPanel implements ParamView {

    private static final Color UNFOCUSED_COLOR = new Color(235, 240, 255);
    private static final Color FOCUSED_COLOR = new Color(131, 182, 255);
    private JTextField[] fields;

    /** Creates new form JIPAddress */
    public IPAddressPV() {
        initComponents();

        fields = new JTextField[]{t1, t2, t3, t4};

        int i = 0;
        for (final JTextField field : fields) {
            final int pos = i++;
            field.setBackground(UNFOCUSED_COLOR);
            field.setForeground(Color.BLACK);
            field.addFocusListener(new FocusListener() {

                @Override
                public void focusGained(FocusEvent e) {
                    field.setBackground(FOCUSED_COLOR);
                    field.setForeground(Color.WHITE);
                    field.setSelectionStart(0);
                    field.setSelectionEnd(field.getText().length());
                }

                @Override
                public void focusLost(FocusEvent e) {
                    field.setBackground(UNFOCUSED_COLOR);
                    field.setForeground(Color.BLACK);

                    if (field.getText().isEmpty()) {
                        return;
                    }

                    if (integeric(field.getText())) {
                        Integer num = Integer.parseInt(field.getText());
                        if (!between(num, 0, 255)) {
                            field.setText("255");
                        }
                    } else {
                        field.setText("0");
                    }
                }
            });
            field.addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(KeyEvent e) {
                    if (eqor(e.getKeyChar(), '.')) {
                        e.consume();
                        fields[(pos + 1) % 4].requestFocus();
                    }
                }
            });
        }

    }

    public int[] getIp() {
        int[] ip = new int[4];
        for (int i = 0; i < 4; i++) {
            ip[i] = cint(fields[i].getText(), 0);
        }
        return ip;
    }

    public void setIp(int[] ip) {
        for (int i = 0; i < 4; i++) {
            fields[i].setText("" + ip[i]);
        }
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

        t1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        t2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        t3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        t4 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        setMinimumSize(new java.awt.Dimension(127, 23));
        setPreferredSize(new java.awt.Dimension(127, 23));
        setLayout(new java.awt.GridBagLayout());

        t1.setBackground(new java.awt.Color(235, 240, 255));
        t1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t1.setText("255");
        t1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 3, 0, 3);
        add(t1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText(".");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(jLabel1, gridBagConstraints);

        t2.setBackground(new java.awt.Color(131, 182, 255));
        t2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t2.setText("255");
        t2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 3);
        add(t2, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText(".");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(jLabel2, gridBagConstraints);

        t3.setBackground(new java.awt.Color(240, 248, 255));
        t3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t3.setText("255");
        t3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 3);
        add(t3, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText(".");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(jLabel3, gridBagConstraints);

        t4.setBackground(new java.awt.Color(240, 248, 255));
        t4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t4.setText("255");
        t4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        t4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 3);
        add(t4, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void t4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t4ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField t1;
    private javax.swing.JTextField t2;
    private javax.swing.JTextField t3;
    private javax.swing.JTextField t4;
    // End of variables declaration//GEN-END:variables

    public void setValueText(String text) {
        String[] parts = text.split("\\.");

        if (parts.length != 4) {
            parts = new String[]{"", "", "", ""};
        }

        for (int i = 0; i < 4; i++) {
            fields[i].setText(parts[i]);
        }
    }

    public String getValueText() {
        boolean allEmpty = true;
        for (JTextField f : fields) {
            if (!f.getText().isEmpty()) {
                allEmpty = false;
                break;
            }
        }

        if (allEmpty) {
            return "";
        }

        int[] val = getIp();
        return "" + val[0] + "." + val[1] + "." + val[2] + "." + val[3];
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (JTextField f : fields) {
            f.setEnabled(enabled);
            if (enabled){
                f.setBackground(UNFOCUSED_COLOR);
            }else{
                f.setBackground(new Color(240, 240, 240));
            }
        }
    }

//    @Override
//    public void addValueChangedListener(final ValueChangedListener l) {
//        for (JTextField f : fields){
//            f.getDocument().addDocumentListener(new DocumentListener() {
//
//                @Override
//                public void insertUpdate(DocumentEvent e) {
//                    l.onChange(IPAddressPV.this);
//                }
//
//                @Override
//                public void removeUpdate(DocumentEvent e) {
//                    l.onChange(IPAddressPV.this);
//                }
//
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    l.onChange(IPAddressPV.this);
//                }
//            });
//        }
//    }

    public void setParam(BaseParamModel model) {
        String val =  (String) model.getValue();
        setValueText(val);
    }

    public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
        setValueText(newValue.toString());
    }

    public void reflectChangesToParam(BaseParamModel to) {
       to.setValue(getValueText());
    }
}
