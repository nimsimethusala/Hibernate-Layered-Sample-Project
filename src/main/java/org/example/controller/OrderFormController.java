package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import jakarta.persistence.criteria.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.*;
import org.example.dto.CustomerDTO;
import org.example.dto.ItemDTO;
import org.example.dto.OrderDTO;
import org.example.entity.Customer;
import org.example.entity.Item;
import org.example.entity.OrderDetail;
import org.example.entity.Orders;
import org.example.tm.OrderTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class OrderFormController {
    @FXML
    public TableColumn colCustomerID;

    @FXML
    public TableColumn colItemID;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private AnchorPane rootOrder;

    @FXML
    private TableView<OrderTm> tblOrder;

    @FXML
    private TextField txtQty;

    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAIL);

    public void initialize() {
        generateNextOrderId();
        getAllCustomerId();
        getAllItemId();
        setCellValueFactory();
    }

    private void generateNextOrderId() {
        String nextId = orderBO.generateNextOrderId();
        lblOrderId.setText(nextId);
    }

    private void setCellValueFactory(){
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    void getAllCustomerId() {
        try {
            ArrayList<CustomerDTO> customerDTOS = (ArrayList<CustomerDTO>) customerBO.getAllCustomers();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (CustomerDTO customerDTO : customerDTOS){
                obList.add(customerDTO.getCustomerId());
            }

            cmbCustomerId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void getAllItemId() {
        try {
            ArrayList<ItemDTO> itemDTOS = (ArrayList<ItemDTO>) itemBO.getAllItems();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (ItemDTO itemDTO : itemDTOS){
                obList.add(itemDTO.getItemId());
            }

            cmbItemId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddCartOnAction(ActionEvent event) {
        String cusId = cmbCustomerId.getValue();
        String itemId = cmbItemId.getValue();
        double price = Double.parseDouble(lblPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = Double.parseDouble(lblTotal.getText());

        OrderTm orderTm = new OrderTm(cusId, itemId, price, qty, total);
        obList.add(orderTm);
        tblOrder.setItems(obList);

        double totalAmount = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            OrderTm orderTm1 = tblOrder.getItems().get(i);
            totalAmount += orderTm1.getTotal();
        }

        lblTotalAmount.setText(String.valueOf(totalAmount));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/org/example/view/mainForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootOrder.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        setCustomerName();
    }

    private void setCustomerName(){
        String cusId = (String) cmbCustomerId.getValue();

        String customerName = customerBO.customerName(cusId);
        lblCustomerName.setText(customerName);
    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        setItemName();
        setPrice();
    }

    private void setPrice() {
        String itemId = (String) cmbItemId.getValue();
        double price = itemBO.getPrice(itemId);
        lblPrice.setText(String.valueOf(price));
    }

    private void setItemName(){
        String itemId = (String) cmbItemId.getValue();

        String price = itemBO.getItemName(itemId);
        lblItemName.setText(price);
    }

    @FXML
    void txtTotalOnAction(ActionEvent event) {
        double price = Double.parseDouble(lblPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        double total = price * qty;
        lblTotal.setText(String.valueOf(total));
    }

    @FXML
    void imgHomeOnAction(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/org/example/view/mainForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootOrder.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPlacedOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String orderId = lblOrderId.getText();
        double totalAmount = Double.parseDouble(lblTotalAmount.getText());
        String customerId = cmbCustomerId.getValue();

        CustomerDTO customerDTO = customerBO.searchByCustomerId(customerId);
        Customer customer = new Customer(customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact());

        OrderDTO orderDTO = new OrderDTO(orderId, totalAmount, customer);

        List<OrderTm> orderTmList = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            OrderTm orderTm = tblOrder.getItems().get(i);
            orderTmList.add(orderTm);
        }

        boolean isOrderPlaced = placeOrderBO.placeOrder(orderTmList, orderDTO);
    }
}