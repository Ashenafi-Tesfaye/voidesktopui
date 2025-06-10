/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.voi.voidesktopui;

import javafx.collections.ObservableList;
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
public class VehiclesView extends VBox {
   private final ObservableList<Vehicle> master =
            FXCollections.observableArrayList(
                    new Vehicle("8AB123","Honda",2015,"Gray",
                                "Stolen","John Smith","410-555-1234")
            );

      private final FilteredList<Vehicle> rows =
            new FilteredList<>(master, v -> true);
      
    public VehiclesView() {
        setPadding(new Insets(24));
        setSpacing(16);

        Label h = new Label("Vehicles of Interest");
        h.setFont(Theme.h2());

        // search/filter bar
        HBox bar = new HBox(8);
        TextField q = new TextField(); q.setPromptText("Plate / VIN");
        ComboBox<String> byReason = new ComboBox<>(FXCollections.observableArrayList("All Reasons","Stolen"));
        ComboBox<String> byMake   = new ComboBox<>(FXCollections.observableArrayList("All Makes","Honda"));
        Button search = new Button("Search");
        search.setOnAction(e -> rows.setPredicate(v ->
            (q.getText().isBlank() || v.plate.contains(q.getText()) || v.vin.contains(q.getText())) &&
            (byReason.getValue().equals("All Reasons") || v.reason.equals(byReason.getValue())) &&
            (byMake  .getValue().equals("All Makes")   || v.make  .equals(byMake  .getValue()))
        ));
        Button add = new Button("+ New Vehicle");
        add.setOnAction(e -> vehicleDialog(null));
        bar.getChildren().addAll(q, byReason, byMake, search, add);
        bar.getChildren().forEach(n -> HBox.setHgrow(n, n instanceof TextField ? Priority.ALWAYS : Priority.NEVER));

        // table
        TableView<Vehicle> tv = new TableView<>(rows);
        tv.getColumns().addAll(
            col("Plate" , v->v.plate ,80),
            col("Make"  , v->v.make  ,80),
            col("Yr"    , v->v.year  ,60),
            col("Color" , v->v.color ,80),
            col("Reason", v->v.reason,120),
            col("Owner" , v->v.owner ,140),
            col("Phone" , v->v.phone ,120),
            actionCol(tv)
        );
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getChildren().addAll(h, bar, tv);
    }

    private <S> TableColumn<Vehicle,S> col(String t, javafx.util.Callback<Vehicle,S> f, int w){
        TableColumn<Vehicle,S> c=new TableColumn<>(t);
        c.setCellValueFactory(cell->new javafx.beans.property.SimpleObjectProperty<>(f.call(cell.getValue())));
        c.setMinWidth(w);return c;
    }
    private TableColumn<Vehicle,Void> actionCol(TableView<Vehicle> tv){
        TableColumn<Vehicle,Void> c=new TableColumn<>();
        c.setCellFactory(__->new TableCell<>(){
            final Button edit=new Button("Edit");
            { edit.setOnAction(e->vehicleDialog(tv.getItems().get(getIndex()))); }
            @Override protected void updateItem(Void v,boolean empty){
                super.updateItem(v,empty);
                setGraphic(empty?null:edit);
            }
        });
        c.setMinWidth(100);return c;
    }

    private void vehicleDialog(Vehicle existing){
        Stage dlg=new Stage(); dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle(existing==null?"Add Vehicle":"Edit Vehicle");

        TextField plate=new TextField();
        TextField vin  =new TextField();
        TextField year =new TextField();
        TextField color=new TextField();
        ComboBox<String> make   =new ComboBox<>(FXCollections.observableArrayList("Honda"));
        ComboBox<String> model  =new ComboBox<>(FXCollections.observableArrayList("Accord"));
        ComboBox<String> reason =new ComboBox<>(FXCollections.observableArrayList("Stolen"));
        TextField owner =new TextField();
        TextField phone =new TextField();

        if(existing!=null){
            plate.setText(existing.plate);
            vin  .setText(existing.vin);
            year .setText(String.valueOf(existing.year));
            color.setText(existing.color);
            make .setValue(existing.make);
            reason.setValue(existing.reason);
            owner.setText(existing.owner);
            phone.setText(existing.phone);
        }

        GridPane gp=new GridPane(); gp.setVgap(8); gp.setHgap(12);
        gp.addRow(0,new Label("Plate*"),plate,new Label("VIN"),vin);
        gp.addRow(1,new Label("Make*"),make ,new Label("Model*"),model);
        gp.addRow(2,new Label("Color"),color,new Label("Year"),year);
        gp.addRow(3,new Label("Reason*"),reason,new Label("Owner"),owner);
        gp.addRow(4,new Label("Phone"),phone);

        Button save=new Button("Save");
        save.setDefaultButton(true);
      save.setOnAction(e -> {
    if (existing == null) {
        master.add(new Vehicle(
            plate.getText(), make.getValue(),
            Integer.parseInt(year.getText()), color.getText(),
            reason.getValue(), owner.getText(), phone.getText()
        ));
    } else {
        // … update fields in 'existing'
    }
    dlg.close();
});

        VBox box=new VBox(12,gp,save); box.setPadding(new Insets(20));
        dlg.setScene(new Scene(box));
        dlg.showAndWait();
    }

    private static class Vehicle{
        String plate,vin,make,color,reason,owner,phone; int year;
        Vehicle(String p,String m,int y,String c,String r,String o,String ph){
            plate=p;make=m;year=y;color=c;reason=r;owner=o;phone=ph;vin="";
        }
    }
}
