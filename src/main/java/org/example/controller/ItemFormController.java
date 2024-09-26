package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.ItemBO;
import org.example.dto.CustomerDTO;
import org.example.dto.ItemDTO;
import org.example.tm.ItemTm;

import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private TableColumn<?, ?> colCount;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private Label lblItemId;

    @FXML
    private AnchorPane rootItem;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtCount;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSearch;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() {
        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtCount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCount.requestFocus();
            }
        });

        txtPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPrice.requestFocus();
            }
        });

        generateNextItemId();
        setCellValueFactory();
        loadAllItem();
    }

    private void setCellValueFactory(){
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadAllItem(){
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        try {
            List<ItemDTO> itemList = itemBO.getAllItems();
            for (ItemDTO itemDTO : itemList){
                ItemTm itemTm = new ItemTm(itemDTO.getItemId(), itemDTO.getItemName(), itemDTO.getCount(), itemDTO.getPrice());
                obList.add(itemTm);
            }
            tblItem.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFeilds();
    }

    private void clearFeilds() {
        txtName.setText("");
        txtCount.setText("");
        txtPrice.setText("");
    }

    private void generateNextItemId() {
        String nextId = itemBO.generateNextItemId();
        lblItemId.setText(nextId);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String itemId = lblItemId.getText();

        try {
            boolean isDelete = itemBO.deleteItem(itemId);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION, "Item is Deleted...!").show();
                clearFeilds();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String itemId = lblItemId.getText();
        String itemName = txtName.getText();
        int count = Integer.parseInt(txtCount.getText());
        double price = Double.parseDouble(txtPrice.getText());

        ItemDTO itemDTO = new ItemDTO(itemId, itemName, count, price);

        try {
            boolean isItemSaved = itemBO.SaveItem(itemDTO);
            if (isItemSaved){
                new Alert(Alert.AlertType.INFORMATION, "New Item is Saved...!").show();
                clearFeilds();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String itemId = lblItemId.getText();
        String itemName = txtName.getText();
        int count = Integer.parseInt(txtCount.getText());
        double price = Double.parseDouble(txtPrice.getText());

        ItemDTO itemDTO = new ItemDTO(itemId, itemName, count, price);

        boolean isItemupdated = itemBO.updateItem(itemDTO);

        if (isItemupdated){
            new Alert(Alert.AlertType.CONFIRMATION, "Item is Updated...!").show();
            clearFeilds();
        }
    }

    @FXML
    void lblItemIdOnAction(MouseEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String itemiId = txtSearch.getText();

        try {
            ItemDTO itemDTO = itemBO.searchByItemId(itemiId);
            if (itemDTO != null) {
                lblItemId.setText(itemDTO.getItemId());
                txtName.setText(itemDTO.getItemName());
                txtCount.setText(String.valueOf(itemDTO.getCount()));
                txtPrice.setText(String.valueOf(itemDTO.getPrice()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
