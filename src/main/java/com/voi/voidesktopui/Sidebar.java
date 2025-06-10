/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.voi.voidesktopui;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.function.Consumer;
/**
 *
 * @author Ashuka
 */
public class Sidebar extends VBox {
    public Sidebar(Consumer<String> onNavigate) {
        setPrefWidth(240);
        setSpacing(8);
        setPadding(new Insets(16));
        setBackground(new Background(new BackgroundFill(Theme.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        Label logo = new Label("VOI HQ");
        logo.setTextFill(Color.WHITE);
        logo.setFont(Theme.h2());
        getChildren().add(logo);

        getChildren().addAll(
            link("Dashboard"    , "dashboard", onNavigate),
            link("Reasons"      , "reasons"  , onNavigate),
            link("Vehicles"     , "vehicles" , onNavigate),
            link("Make & Model" , "mm"       , onNavigate)
        );

        Pane spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);

        Button logout = new Button("Logout");
        logout.getStyleClass().add("flat-link");
        logout.setTextFill(Color.WHITE);
        logout.setOnAction(e -> System.exit(0));
        getChildren().add(logout);
    }

    private Node link(String text, String target, Consumer<String> nav) {
        Label lbl = new Label(text);
        lbl.setTextFill(Color.WHITE);
        lbl.setPadding(new Insets(6, 0, 6, 0));
        lbl.setCursor(Cursor.HAND);
        lbl.setOnMouseClicked(e -> nav.accept(target));
     lbl.hoverProperty().addListener((obs, oldVal, isHover) -> {
    lbl.setBackground(new Background(new BackgroundFill(
            isHover ? Color.rgb(255,255,255,0.15) : Color.TRANSPARENT,
            CornerRadii.EMPTY, Insets.EMPTY)));
});
        return lbl;
    }
}
