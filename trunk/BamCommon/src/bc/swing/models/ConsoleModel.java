/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bc.swing.models;

import bc.swing.pfrm.Model;
import bc.swing.pfrm.ano.PageDef;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author BLutati
 */
public class ConsoleModel{

    String consoleName;
    private Document doc;
    private int idealSize = 30000;
    private int maxExcess = 5000;
    private LinkedList<Listener> listeners = new LinkedList<Listener>();

    public ConsoleModel() {
        doc = new PlainDocument();
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public String getConsoleName() {
        return consoleName;
    }
    
    public void addListener(Listener l){
        listeners.add(l);
    }

    public void removeListener(Listener l){
        listeners.remove(l);
    }

    public Document getDocument() {
        return doc;
    }

    public void setIdealSize(int idealSize) {
        this.idealSize = idealSize;
    }

    public void setMaxExcess(int maxExcess) {
        this.maxExcess = maxExcess;
    }

    public int getIdealSize() {
        return idealSize;
    }

    public int getMaxExcess() {
        return maxExcess;
    }

    public synchronized void append(String data) {
        try {
            doc.insertString(doc.getLength(), data, null);
        } catch (BadLocationException e) {
        }

        fireTextAppended(data);

        int excess = doc.getLength() - idealSize;
        if (excess >= maxExcess) {
            int start = 0;
            int end = excess;
            try {
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument) doc).replace(start, end - start, "",
                            null);
                } else {
                    doc.remove(start, end - start);
                    doc.insertString(start, "", null);
                }

            } catch (BadLocationException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public OutputStream createConsoleWritingStream(){
        OutputStream ops = new OutputStream() {

            @Override
            public synchronized void  write(int b) throws IOException {
                append("" + (char)b);
            }
        };
        return ops;
    }
    
    public void fireTextAppended(String data) {
        for (Listener l : listeners) l.onTextAppended(this, data);
    }

    public static interface Listener{
        void onTextAppended(ConsoleModel source, String append);
    }
}
