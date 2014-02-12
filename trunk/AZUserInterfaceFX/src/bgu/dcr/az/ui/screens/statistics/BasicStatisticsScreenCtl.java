/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.ui.screens.statistics;

import bgu.dcr.az.mas.exp.Experiment;
import bgu.dcr.az.mas.stat.StatisticCollector;
import bgu.dcr.az.ui.screens.dialogs.Notification;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author User
 */
public class BasicStatisticsScreenCtl implements Initializable {

    @FXML
    HBox header;

    @FXML
    TreeView testsTree;

    @FXML
    BorderPane resultsContainer;

    @FXML
    Button showChartButton;

    StatisticsPlotter plotter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        plotter = new StatisticsPlotter(resultsContainer);

        testsTree.setShowRoot(false);
        testsTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        testsTree.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            if (nv != null && !((TreeItem) nv).isLeaf()) {
                testsTree.getSelectionModel().select(testsTree.getRoot()); //fix bug of double clicking the title
                testsTree.getSelectionModel().clearSelection();
            }
        });

        testsTree.expandedItemCountProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number nv) -> {
            testsTree.getSelectionModel().clearSelection();
        });

        showChartButton.setOnAction(e -> plot());

    }

    private void plot() {
        TreeItem selection = (TreeItem) testsTree.getSelectionModel().getSelectedItem();
        if (selection != null) {
            StatisticCollector collector = (StatisticCollector) selection.getValue();
            ExperimentToStringWrapper experiment = (ExperimentToStringWrapper) selection.getParent().getValue();
            collector.plot(plotter, experiment.exp);
        } else {
            Notification.Notifier.INSTANCE.notifyWarning("Cannot Complete Operation", "you must select statistic first.");
        }
    }

    public void setModel(Experiment exp) {
        TreeItem testsRoot = new TreeItem("Tests");

        for (Experiment sub : exp) {
            TreeItem experimentRoot = new TreeItem(new ExperimentToStringWrapper(sub));
            testsRoot.getChildren().add(experimentRoot);
            for (StatisticCollector statistic : sub.getStatistics()) {
                experimentRoot.getChildren().add(new TreeItem(statistic));
            }

            experimentRoot.setExpanded(true);
        }

        Platform.runLater(() -> testsTree.setRoot(testsRoot));
    }

    private class ExperimentToStringWrapper {

        Experiment exp;

        public ExperimentToStringWrapper(Experiment exp) {
            this.exp = exp;
        }

        @Override
        public String toString() {
            return "Test " + exp.getName();
        }

    }

}
