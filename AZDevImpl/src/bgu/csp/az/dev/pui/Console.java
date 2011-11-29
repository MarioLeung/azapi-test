/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Console.java
 *
 * Created on 27/10/2011, 12:44:24
 */
package bgu.csp.az.dev.pui;

//import bc.dsl.PageDSL;
import bc.swing.models.BatchDocument;
import bc.swing.pfrm.Parameter;
import bc.swing.pfrm.Model;
//import bc.swing.pfrm.ParamView;
import bc.swing.pfrm.ano.PageDef;
import bc.swing.pfrm.ano.Param;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.AbstractMap.SimpleEntry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author bennyl
 */
public class Console extends javax.swing.JPanel {
    
    private Scanner sc=null;
    private AgentLogDocument doc;
    private int offset=0;
    private String lastSearch ="";
    /** Creates new form Console */
    public Console() {
        initComponents();
        output.setEditorKit(new StyledEditorKit());
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                output.getHighlighter().removeAllHighlights();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                output.getHighlighter().removeAllHighlights();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                output.getHighlighter().removeAllHighlights();
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

        jPanel1 = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        nextMatch = new javax.swing.JButton();
        matchCaseCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextPane();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        setPreferredSize(new java.awt.Dimension(200, 150));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        searchLabel.setText("Search");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(searchLabel, gridBagConstraints);

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(searchTextField, gridBagConstraints);

        nextMatch.setText("Next");
        nextMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextMatchActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(nextMatch, gridBagConstraints);

        matchCaseCheckBox.setText("Match Case");
        matchCaseCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchCaseCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(matchCaseCheckBox, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(32, 50));

        output.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        output.setForeground(new java.awt.Color(245, 151, 151));
        output.setText("bla");
        jScrollPane1.setViewportView(output);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
    
    

}//GEN-LAST:event_searchTextFieldActionPerformed

private void nextMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextMatchActionPerformed
    if(!this.searchTextField.getText().equals(this.lastSearch)){
        output.getHighlighter().removeAllHighlights();
        
    }
    this.lastSearch=this.searchTextField.getText();
    offset=output.getCaretPosition();
    SimpleEntry<Integer,Integer> place=doc.search(this.searchTextField.getText(), true, offset);
    Highlighter highlighter = output.getHighlighter();
    highlighter.removeAllHighlights();
    if(place.getKey()==-1){
        output.setCaretPosition(0);
        return;
    }
    offset=output.getCaretPosition();
    
    Highlighter.HighlightPainter myHighlightPainter =   new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        try {
            highlighter.addHighlight(offset + place.getKey(),
                                       offset + place.getValue(),
                                       myHighlightPainter);
            offset=offset+ place.getValue();
            output.moveCaretPosition(offset);
        } catch (BadLocationException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    
}//GEN-LAST:event_nextMatchActionPerformed

private void matchCaseCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchCaseCheckBoxActionPerformed
    
}//GEN-LAST:event_matchCaseCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox matchCaseCheckBox;
    private javax.swing.JButton nextMatch;
    private javax.swing.JTextPane output;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables

//    @Override
    public void setParam(Parameter param) {
        doc = (AgentLogDocument) param.getValue();
        doc.addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                output.setCaretPosition(doc.getLength());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        output.setDocument(doc);
    }

//    @Override
    public void reflectChangesToParam(Parameter to) {
    }

//    @Override
    public void onChange(Parameter source, Object newValue, Object deltaHint) {
    }

    @PageDef
    public static class TestModel extends Model {
        BatchDocument b = new AgentLogDocument();
        
        public TestModel() {
            
        }
        
//        @Param(name = "console", customView = Console.class)
        public BatchDocument getBatchdoc() {
            
            return b;
        }
    }

    public static void main(String[] args) {
        
        TestModel tm = new TestModel();
//        PageDSL.showInFrame(tm);
        
    }
}
