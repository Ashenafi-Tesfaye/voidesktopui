/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.voi.voidesktopui;

/**
 *
 * @author Ashuka
 */
//public class VOIDesktopUI {
//
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }
//}


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VOIDesktopUI extends Application {
    private BorderPane root;           // sidebar on the left, page content in center

    @Override public void start(Stage stage) {
        root = new BorderPane();
        root.setLeft(new Sidebar(this::showPage));
        root.setStyle("-fx-font-family: 'Segoe UI'; -fx-background-color:" + Theme.SNOW);

        showPage("dashboard");         // initial view

        Scene scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.setTitle("VOI Desktop Client");
        stage.show();
    }

    private void showPage(String key) {
        switch (key) {
            case "reasons"  -> root.setCenter(new ReasonsView());
            case "vehicles" -> root.setCenter(new VehiclesView());
            case "mm"       -> root.setCenter(new MakeModelView());
            default         -> root.setCenter(new DashboardView());
        }
    }

    public static void main(String[] args) { launch(); }
}
