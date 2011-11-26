/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionList.java
 *
 * Created on 24/11/2011, 17:55:10
 */
package bc.ui.swing.lists;

import bc.ui.swing.listeners.Listeners;
import bc.ui.swing.listeners.SelectionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;

/**
 *
 * @author bennyl
 */
public abstract class ComponentList extends javax.swing.JPanel {

    protected LinkedHashMap<Object, JComponent> items;
    
    /** Creates new form OptionList */
    public ComponentList() {
        initComponents();
        JViewport vp = scroll.getViewport();
        vp.setLayout(new BorderLayout());
        vp.removeAll();
        vp.add(jPanel1, BorderLayout.CENTER);
        vp.setOpaque(false);
        items = new LinkedHashMap<Object, JComponent>();
    }

    public void remove(Object item){
        JComponent box = items.remove(item);
        jPanel1.remove(box);
    }
    
    public void clear(){
        jPanel1.removeAll();
        items.clear();
    }
    
    public void add(final Object item) {
        
        final JComponent comp = createComponentFor(item);
        items.put(item, comp);
        
        GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        jPanel1.add(comp, gbc);
    }

    public abstract JComponent createComponentFor(Object item);
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        scroll.setBorder(null);
        scroll.setOpaque(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());
        scroll.setViewportView(jPanel1);

        add(scroll, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
    
}