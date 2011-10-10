/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TablePV2.java
 *
 * Created on Sep 11, 2011, 7:26:17 PM
 */
package bc.swing.pfrm.views;

import bc.swing.comp.JActionView;
import bc.swing.models.DataExtractor;
import bc.swing.models.DataInserter;
import bc.swing.models.GenericTableModel;
import bc.swing.pfrm.Action;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.ParamView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author BLutati
 */
public class TablePV extends javax.swing.JPanel implements ParamView {

    JPopupMenu pmenu;
    private GenericTableModel innerModel;

    /** Creates new form TablePV2 */
    public TablePV() {
        initComponents();
        pmenu = new JPopupMenu();
    }

    public DataExtractor getDataExtractor(final BaseParamModel model) {
        DataExtractor extractort = model.getDataExtractor();
        return extractort;
    }

    public DataInserter getDataInserter(final BaseParamModel model) {
        DataInserter inserter = model.getDataInserter();
        return inserter;
    }

    public List getItemList(final BaseParamModel model) {
        return (model.getValue() != null ? (List) model.getValue() : new LinkedList());
    }

    private void configureFirstColumnWidth(final BaseParamModel model) {
        final int firstColumnWidth = model.getViewHints().firstColumnWidth();

        if (firstColumnWidth >= 0) {
            final TableColumn col0 = table.getColumnModel().getColumn(0);
            col0.setPreferredWidth(firstColumnWidth);
            col0.setMaxWidth(firstColumnWidth);
            col0.setMinWidth(firstColumnWidth);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        globalActionsPan = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 245, 245)));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setFillsViewportHeight(true);
        table.setGridColor(new java.awt.Color(240, 245, 255));
        table.setRowHeight(20);
        table.setShowVerticalLines(false);
        jScrollPane1.setViewportView(table);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        globalActionsPan.setBackground(new java.awt.Color(255, 255, 255));
        globalActionsPan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        add(globalActionsPan, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel globalActionsPan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    protected void hideTableHeader() {
        table.setTableHeader(null);
    }

    public void setParam(final BaseParamModel model) {
        configurePopupMenu(model);
        configureGlobalActions(model);
        DataExtractor extractort = getDataExtractor(model);
        innerModel = new GenericTableModel(extractort);
        innerModel.changeInnerList(getItemList(model));

        DataInserter inserter = getDataInserter(model);
        if (inserter != null) {
            innerModel.setInserter(inserter);
        }

        table.setModel(innerModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    final Object newSelection = innerModel.getInnerData().get(table.getSelectedRow());
                    model.fireSelectionChanged(newSelection);
                } else {
                    model.fireSelectionChanged(null);
                }

            }
        });
        configureFirstColumnWidth(model);
        table.setRowSelectionAllowed(model.getViewHints().allowSelection());
    }

    public void reflectChangesToParam(BaseParamModel to) {
    }

    public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
        innerModel.changeInnerList(getItemList(source));
        configureFirstColumnWidth(source);
        validate();
        repaint();
    }

    private void configurePopupMenu(BaseParamModel model) {
        pmenu.removeAll();
        for (final Action a : model.getActions()) {
            if (a.isItemAction()) {
                pmenu.add(new AbstractAction(a.getName(), a.getIcon()) {

                    public void actionPerformed(ActionEvent e) {
                        a.execute();
                    }
                });
            }
        }

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    return;
                }
                int p = table.rowAtPoint(e.getPoint());
                if (p >= 0) {
                    table.getSelectionModel().setSelectionInterval(p, p);
                    pmenu.show(table, e.getX(), e.getY());
                }
            }
        });

    }

    private void configureGlobalActions(BaseParamModel model) {
        globalActionsPan.setVisible(false);
        for (Action a : model.getActions()) {
            if (!a.isItemAction()) {
                globalActionsPan.setVisible(true);
                globalActionsPan.add(new JActionView(a, Color.GRAY));
            }
        }
    }
}