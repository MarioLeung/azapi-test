package bgu.dcr.az.pivot.ui.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import bgu.dcr.az.pivot.ui.PivotDataTableViewController;
import bgu.dcr.az.pivot.ui.PivotViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class PivotView extends Application {

    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        PivotView.stage = stage;
//        FXMLLoader loader = new FXMLLoader(PivotViewController.class.getResource("PivotViewFXML.fxml"));
//        Parent root = (Parent)loader.load();
//        PivotViewController controller = (PivotViewController)loader.getController();
//        controller.setModel(PivotTestUtils.generateSimplePivot1());
        
        FXMLLoader loader = new FXMLLoader(PivotDataTableViewController.class.getResource("PivotDataTableView.fxml"));
        Parent root = (Parent)loader.load();
        PivotDataTableViewController controller = (PivotDataTableViewController)loader.getController();
        controller.setModel(PivotTestUtils.generateSimplePivot1().getPivotedData());
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}