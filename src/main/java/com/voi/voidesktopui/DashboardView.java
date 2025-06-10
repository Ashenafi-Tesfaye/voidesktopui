/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.voi.voidesktopui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
/**
 *
 * @author Ashuka
 */
public class DashboardView extends VBox {
    public DashboardView() {
        setPadding(new Insets(0, 32, 32, 32));
        setSpacing(24);

        // header
        Label h = new Label("Headquarters Dashboard");
        h.setFont(Theme.h2());
        h.setTextFill(Theme.TEXT);
        h.setBackground(new Background(new BackgroundFill(Theme.SKY, CornerRadii.EMPTY, Insets.EMPTY)));
        h.setPadding(new Insets(16, 24, 16, 24));
        h.setMaxWidth(Double.MAX_VALUE);

        // KPI tiles
        HBox kpis = new HBox(16);
        kpis.getChildren().addAll(
            kpi("128", "Total VOIs Today"),
            kpi("2"  , "New Reasons"),
            kpi("6"  , "Vehicles Added")
        );

        // search box
        HBox search = new HBox(8);
        TextField tf = new TextField();
        tf.setPromptText("Search plate or VIN…");
        Button go = new Button("Search");
        go.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Searching: " + tf.getText());
            a.showAndWait();
        });
        search.getChildren().addAll(tf, go);
        HBox.setHgrow(tf, Priority.ALWAYS);

        // recent activity
        VBox activity = new VBox(8);
        activity.getChildren().addAll(
            new Label("Recent Activity"),
            new Label("08:42 AM – Vehicle 8AB123 marked STOLEN"),
            new Label("07:10 AM – Reason \"Silver Alert\" created")
        );

        getChildren().addAll(h, kpis, search, activity);
    }

    private VBox kpi(String value, String label) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(16));
        box.setSpacing(4);
        box.setBorder(new Border(new BorderStroke(Color.web("#dde6ef"),
            BorderStrokeStyle.SOLID, new CornerRadii(6), BorderWidths.DEFAULT)));
        box.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(6), Insets.EMPTY)));

        Label v = new Label(value);
        v.setFont(Theme.kpiFont());
        v.setTextFill(Theme.BLUE);

        Label l = new Label(label);
        l.setTextFill(Theme.TEXT);

        box.getChildren().addAll(v, l);
        return box;
    }
}

