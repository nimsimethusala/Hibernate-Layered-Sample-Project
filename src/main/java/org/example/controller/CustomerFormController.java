package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.CustomerBO;
import org.example.dto.CustomerDTO;
import org.example.tm.CustomerTm;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    @FXML
    public TextField txtSearch;

    @FXML
    public TableColumn colName;

    @FXML
    private AnchorPane CustomerRoot;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TextField txtName;

     CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtContact.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });

        generateNextCustomerId();
        setCellValueFactory();
        loadAllCustomer();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void generateNextCustomerId() {
        String nextId = customerBO.generateNextCustomerId();
        System.out.println(nextId);
        lblCustomerId.setText(nextId);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String cusId = lblCustomerId.getText();

        try {
            boolean isDelete = customerBO.deleteCustomer(cusId);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is Deleted...!").show();
                clearFields();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String cusId = lblCustomerId.getText();
        String cusName = txtName.getText();
        String address = txtAddress.getText();
        int contact = Integer.parseInt(txtContact.getText());

        try {
            CustomerDTO customer = new CustomerDTO(cusId, cusName, address, contact);

            boolean isSaved = customerBO.saveCustomer(customer);

            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "New Customer is Saved...!").show();
                clearFields();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String cusId = lblCustomerId.getText();
        String cusName = txtName.getText();
        String address = txtAddress.getText();
        int contact = Integer.parseInt(txtContact.getText());

        CustomerDTO customerDTO = new CustomerDTO(cusId, cusName, address, contact);

        boolean isupdated = customerBO.updateCustomer(customerDTO);
    }

    public void lblCustomerOnAction(MouseEvent mouseEvent) {

    }

    private void clearFields() {
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllCustomer(){
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDTO> customerList = customerBO.getAllCustomers();
            for (CustomerDTO customer : customerList){
                CustomerTm customerTm = new CustomerTm(customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getContact());
                obList.add(customerTm);
            }
            tblCustomer.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String cusid = txtSearch.getText();

        try {
            CustomerDTO customer = customerBO.searchByCustomerId(cusid);
            if (customer != null) {
                lblCustomerId.setText(customer.getCustomerId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtContact.setText(String.valueOf(customer.getContact()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
