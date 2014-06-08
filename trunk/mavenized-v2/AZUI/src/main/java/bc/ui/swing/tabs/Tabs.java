/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Tabs.java
 *
 * Created on 26/11/2011, 18:46:49
 */
package bc.ui.swing.tabs;

import bc.ui.swing.buttons.FlatToggleButton;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.Icon;
import javax.swing.JComponent;

/**
 *
 * @author bennyl
 */
public class Tabs extends javax.swing.JPanel {

    LinkedHashMap<String, JComponent> tabs = new LinkedHashMap<>();
    HashMap<String, FlatToggleButton> buttons = new HashMap<String, FlatToggleButton> ();
    /** Creates new form Tabs */
    public Tabs() {
        initComponents();
    }

    public void addTab(String name, Icon icon, JComponent content) {
        tabs.put(name, content);
        createTab(name, icon, content);
        //this is the first tab then select it
        if (tabs.size() == 1) {
            showTab(name);
        }
    }

    public void removeTab(String name) {
        JComponent c = tabs.remove(name);
        FlatToggleButton b = buttons.remove(name);
        header.remove(b);
        buttonGroup1.remove(b);
        content.remove(c);
        
        revalidate();
        repaint();
    }

    private void createTab(final String name, Icon icon, JComponent content) {
        //CREATE THE BUTTON
        FlatToggleButton button = new FlatToggleButton();
        button.setBackground(new java.awt.Color(51, 153, 255));
        button.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        buttonGroup1.add(button);
        button.setForeground(new java.awt.Color(255, 255, 255));
        button.setIcon(icon); // NOI18N
        button.setText(name);
        button.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        header.add(button);
        buttons.put(name, button);

        //ADD THE CARD
        this.content.add(content, name);

        //BIND 
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showTab(name);
            }
        });
    }

    public void showTab(String name) {
        ((CardLayout) Tabs.this.content.getLayout()).show(this.content, name);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        header = new javax.swing.JPanel();
        content = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        header.setBackground(new java.awt.Color(51, 51, 51));
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        header.setOpaque(false);
        header.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        add(header, java.awt.BorderLayout.NORTH);

        content.setOpaque(false);
        content.setLayout(new java.awt.CardLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());
        content.add(jPanel1, "card2");

        add(content, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel content;
    private javax.swing.JPanel header;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}