/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.ui.confe;

import bgu.dcr.az.anop.conf.Configuration;
import bgu.dcr.az.anop.conf.Property;
import bgu.dcr.az.anop.utils.PropertyUtils;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Predicate;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Shl
 */
public class ConfigurationEditorInternal extends VBox {

    private final ConfigurationEditor parent;
    private final ConfigurationPropertyEditor parentEditor;
    private Configuration configuration;
    private boolean readOnly;

    private Predicate filter;

    public ConfigurationEditorInternal(ConfigurationEditor parent) {
        this(parent, null);
    }

    public ConfigurationEditorInternal(ConfigurationEditor parent, ConfigurationPropertyEditor parentEditor) {
        this.parent = parent;
        this.parentEditor = parentEditor;
        setSpacing(3);
        setPadding(new Insets(5));

        getStyleClass().add("conf-editor");
    }

    public void setModel(Configuration configuration, boolean readOnly, Predicate filter) {
        this.readOnly = readOnly;

        if (this.configuration == configuration) {
            return;
        }

        this.filter = filter;
        this.configuration = configuration;

        getChildren().clear();

        if (configuration == null) {
            return;
        }

        Collection<Property> properties = configuration.properties();

        generateTerminalPropertiesEditors(properties);
        generateConfigurationPropertiesEditors(properties);
        generateCollectionPropertiesEditors(properties);
    }

    private void generateCollectionPropertiesEditors(Collection<Property> properties) {
        for (Property property : properties) {
            if (PropertyUtils.isCollection(property)) {
                if (showableProperty(property)) {
                    CollectionPropertyEditor editor = new CollectionPropertyEditor(parent);
                    editor.setModel(property, readOnly, filter);
                    getChildren().add(editor);
                }
            }
        }
    }

    private void generateConfigurationPropertiesEditors(Collection<Property> properties) {
        for (Property property : properties) {
            if (!PropertyUtils.isPrimitive(property) && !PropertyUtils.isCollection(property)) {
                if (showableProperty(property)) {
                    ConfigurationPropertyEditor editor = new ConfigurationPropertyEditor(parent, null);
                    editor.setModel(property, readOnly, filter);
                    getChildren().add(editor);
                }
            }
        }
    }

    private void generateTerminalPropertiesEditors(Collection<Property> properties) {
        double max = 0;
        LinkedList<TerminalPropertyEditor> controllerList = new LinkedList<>();
        for (Property property : properties) {
            if (PropertyUtils.isPrimitive(property)) {
                if (showableProperty(property)) {
                    TerminalPropertyEditor controller = new TerminalPropertyEditor(parent, parentEditor);
                    controller.setModel(property, readOnly, filter);
                    double labelWidth = controller.getLabelWidth();
                    if (labelWidth > max) {
                        max = labelWidth;
                    }
                    controllerList.add(controller);
                    getChildren().add(controller);
                }
            }
        }
        for (TerminalPropertyEditor controller : controllerList) {
            controller.setLabelWidth(max);
        }
    }

    private boolean showableProperty(Property property) {
        if (property.doc() != null
                && "false".equals(property.doc().first("UIVisibility"))) {
            return false;
        }
        if (readOnly && property.get() == null) {
            return false;
        }
        
        return filter == null || filter.test(property);
    }
}
