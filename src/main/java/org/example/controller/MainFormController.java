package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.HelloApplication;

import java.io.IOException;

public class MainFormController {

    @FXML
    private AnchorPane rootMain;

    @FXML
    void imgCustomerOnAction(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/org/example/view/customerForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootMain.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void imgItemOnAction(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/org/example/view/itemForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootMain.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void imgOrderOnAction(MouseEvent event) {

    }

}
