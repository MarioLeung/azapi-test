/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PageFrame.java
 *
 * Created on 13/09/2011, 05:59:24
 */
package bc.swing.pfrm.layouts;

import bc.swing.pfrm.Page;
import bc.swing.pfrm.PageView;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author bennyl
 */
public class PageFrame extends javax.swing.JFrame implements PageView{

    /** Creates new form PageFrame */
    public PageFrame() {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PageFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void setPage(Page model) {
        getContentPane().add(model.getView(), BorderLayout.CENTER);
        validate();
        pack();
        repaint();
        model.addToDisposeList(this);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
    }

    public void onDispose() {
        setVisible(false);
    }
    
    public static void show(Page p){
        PageFrame pf = new  PageFrame();
        pf.setTitle(p.getName());
        pf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pf.setPage(p);
        pf.setVisible(true);
    }
}
