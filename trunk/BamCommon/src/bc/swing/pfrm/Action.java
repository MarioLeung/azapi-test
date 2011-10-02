/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bc.swing.pfrm;

import javax.swing.Icon;

/**
 *
 * @author BLutati
 */
public abstract class Action {

    String name;
    Icon icon;
    boolean defaultAction;

    public Action(String name, Icon icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }

    public boolean isDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(boolean defaultAction) {
        this.defaultAction = defaultAction;
    }
    
    public abstract void execute();

}
