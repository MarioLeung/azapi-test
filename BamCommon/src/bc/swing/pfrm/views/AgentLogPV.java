/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AgentLogPV.java
 *
 * Created on 04/10/2011, 10:24:36
 */
package bc.swing.pfrm.views;

import bc.swing.models.AgentLogConsoleModel;
import bc.swing.models.ConsoleModel;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.Page;
import bc.swing.pfrm.PageView;
import bc.swing.pfrm.ParamView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author bennyl
 */
public class AgentLogPV extends javax.swing.JPanel implements ParamView, ConsoleModel.Listener {

    private AgentLogConsoleModel alm;

    /** Creates new form AgentLogPV */
    public AgentLogPV() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        editor = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        editor.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(editor);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane editor;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void setParam(BaseParamModel param) {
        alm = (AgentLogConsoleModel) param.getValue();
        editor.setEditorKit(new StyledEditorKit());
        editor.setDocument(alm);
        editor.setEditable(false);

        final Timer t = new Timer(500, new ActionListener() {
            int lastLength = 0;
            
            public void actionPerformed(ActionEvent e) {
                int newLength = alm.getLength();
                if (!editor.hasFocus() && lastLength != newLength) {
                    editor.setCaretPosition(newLength);
                    lastLength = newLength;
                }
            }
        });
        
        t.setRepeats(true);
        t.start();
        
        param.getPage().addToDisposeList(new PageView() {

            public void setPage(Page page) {
            }

            public void onDispose() {
                t.stop();
            }
        });
    }

    public void reflectChangesToParam(BaseParamModel to) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onTextAppended(ConsoleModel source, String append) {
    }
}