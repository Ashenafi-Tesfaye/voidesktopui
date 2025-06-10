/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.voi.voidesktopui;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Ashuka
 */
public class MakeModelView extends VBox {
    public MakeModelView() {
        setPadding(new Insets(24));
        setSpacing(16);

        Label h = new Label("Makes & Models");
        h.setFont(Theme.h2());

        HBox bar = new HBox(8);
        TextField filter = new TextField(); filter.setPromptText("Filter makes…");
        Button newMake  = new Button("+ New Make");
        Button newModel = new Button("+ New Model");
        bar.getChildren().addAll(filter, newMake, newModel);
        HBox.setHgrow(filter, Priority.ALWAYS);

        TableView<Make> tv = new TableView<>();
        TableColumn<Make,String> makeCol = new TableColumn<>("Make");
        makeCol.setCellValueFactory(data -> data.getValue().name);
        TableColumn<Make,String> modelsCol = new TableColumn<>("Models");
        modelsCol.setCellValueFactory(data -> data.getValue().models);
        tv.getColumns().addAll(makeCol, modelsCol);
        tv.setItems(FXCollections.observableArrayList(
            new Make("Honda","Accord, Civic, CR-V")
        ));
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        newMake.setOnAction(e -> dialog("Add Make", "Make Name*", s ->
            tv.getItems().add(new Make(s,""))));

        newModel.setOnAction(e -> dialog("Add Model", "Model Name*", s -> {
            Make selected = tv.getSelectionModel().getSelectedItem();
            if (selected != null) selected.models.set(selected.models.get() + ", " + s);
        }));

        getChildren().addAll(h, bar, tv);
    }

    private void dialog(String title, String label, java.util.function.Consumer<String> onSave){
        Stage dlg=new Stage(); dlg.initModality(Modality.APPLICATION_MODAL); dlg.setTitle(title);
        TextField tf=new TextField();
        VBox root=new VBox(12,new Label(label),tf);
        root.setPadding(new Insets(20));
        Button save=new Button("Save");
        save.setDefaultButton(true);
        save.setOnAction(e->{ onSave.accept(tf.getText()); dlg.close(); });
        root.getChildren().add(save);
        dlg.setScene(new Scene(root)); dlg.showAndWait();
    }

    private static class Make{
        final javafx.beans.property.SimpleStringProperty name  = new javafx.beans.property.SimpleStringProperty();
        final javafx.beans.property.SimpleStringProperty models= new javafx.beans.property.SimpleStringProperty();
        Make(String n,String m){ name.set(n); models.set(m); }
    }
}

