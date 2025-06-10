/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.voi.voidesktopui;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
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
public class ReasonsView extends VBox {
    private final FilteredList<Reason> model =
        new FilteredList<>(FXCollections.observableArrayList(
            new Reason("01", "SilverAlert", "Missing elderly person")
        ), p -> true);

    public ReasonsView() {
        setPadding(new Insets(24));
        setSpacing(16);

        // header
        Label h = new Label("Reason Catalog");
        h.setFont(Theme.h2());

        // filter & new button
        HBox bar = new HBox(8);
        TextField filter = new TextField();
        filter.setPromptText("Filter reasons…");
        filter.textProperty().addListener((obs, ov, nv) ->
                model.setPredicate(r -> nv.isBlank()
                        || r.code().toLowerCase().contains(nv.toLowerCase())
                        || r.desc().toLowerCase().contains(nv.toLowerCase())));
        Button add = new Button("+ New Reason");
        add.setOnAction(e -> openDialog(null));
        bar.getChildren().addAll(filter, add);
        HBox.setHgrow(filter, Priority.ALWAYS);

        // table
        TableView<Reason> tv = new TableView<>(model);
        tv.getColumns().addAll(
            col("ID", Reason::code, 80),
            col("Reason", Reason::codeReadable, 140),
            col("Description", Reason::desc, 300),
            actionCol(tv)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getChildren().addAll(h, bar, tv);
    }

    /* helper to build a normal table column */
    private <S> TableColumn<Reason, S> col(String title,
                                           javafx.util.Callback<Reason, S> getter,
                                           int minWidth) {
        TableColumn<Reason, S> c = new TableColumn<>(title);
        c.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(getter.call(cell.getValue())));
        c.setMinWidth(minWidth);
        return c;
    }

    /* column with Edit / Delete buttons */
    private TableColumn<Reason, Void> actionCol(TableView<Reason> tv) {
        TableColumn<Reason, Void> col = new TableColumn<>();
        col.setSortable(false);
        col.setMinWidth(180);
        col.setCellFactory(__ -> new TableCell<>() {
            final Button edit = new Button("Edit");
            final Button del  = new Button("Delete");
            { edit.setOnAction(e -> openDialog(getTableView().getItems().get(getIndex())));
              del.setOnAction(e -> model.remove(getTableView().getItems().get(getIndex()))); }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null); else setGraphic(new HBox(6, edit, del));
            }
        });
        return col;
    }

    private void openDialog(Reason existing) {
        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle(existing == null ? "Add Reason" : "Edit Reason");

        TextField code = new TextField();
        TextField desc = new TextField();
        if (existing != null) {
            code.setText(existing.code());
            desc.setText(existing.desc());
        }

        VBox content = new VBox(12,
            new Label("Reason Code*"), code,
            new Label("Description"), desc);
        content.setPadding(new Insets(20));

        Button save = new Button("Save");
        save.setDefaultButton(true);
        save.setOnAction(e -> {
            if (existing == null)
                model.add(new Reason(code.getText(), code.getText(), desc.getText()));
            else {
                existing.setCode(code.getText());
                existing.setDesc(desc.getText());
                tvRefresh();
            }
            dlg.close();
        });
        content.getChildren().add(save);

        dlg.setScene(new Scene(content));
        dlg.showAndWait();
    }

    private void tvRefresh() { model.setPredicate(model.getPredicate()); }

    /** tiny record */
    private static class Reason {
        private String code, desc;
        Reason(String id, String code, String desc) { this.code = code; this.desc = desc; }
        String code() { return code; }
        String codeReadable() { return code; }
        String desc() { return desc; }
        void setCode(String c){ code = c; }
        void setDesc(String d){ desc = d; }
    }
}
