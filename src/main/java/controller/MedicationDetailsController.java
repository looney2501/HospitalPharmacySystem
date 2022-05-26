package controller;

import domain.entities.Medication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Services;
import services.exceptions.ServicesException;

import java.io.IOException;
import java.net.URL;

/**
 * 
 */
public class MedicationDetailsController extends GenericController {

    private final Medication medication;
    private final GenericController parentController;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField producerTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    public MedicationDetailsController(Services services, Stage stage, Medication medication, GenericController parent) {
        super(services, stage);
        this.medication = medication;
        this.parentController = parent;
    }

    @FXML
    public void handleAddMedication() {
        String name = nameTextField.getText();
        String producer = producerTextField.getText();
        Integer stock;
        try {
            stock = Integer.valueOf(stockTextField.getText());
        }
        catch (NumberFormatException e) {
            stock = -1;
        }
        String description = descriptionTextArea.getText();
        try {
            services.addMedication(name, producer, stock, description);
            parentController.refresh();
            stage.close();
        }
        catch (ServicesException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handleModifyMedication() {
        Integer id = medication.getId();
        String name = nameTextField.getText();
        String producer = producerTextField.getText();
        Integer stock;
        try {
            stock = Integer.valueOf(stockTextField.getText());
        }
        catch (NumberFormatException e) {
            stock = -1;
        }
        String description = descriptionTextArea.getText();
        try {
            services.modifyMedication(id, name, producer, stock, description);
            parentController.refresh();
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Medication modified successfully!");
            stage.close();
        }
        catch (ServicesException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void handleCancel() {
        stage.close();
    }

    public void initiateViewMedicationDetailsProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/medication-details.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Medication Details");
        stage.show();

        loadTextFieldData();
        disableControls();
    }

    private void loadTextFieldData() {
        nameTextField.setText(medication.getName());
        producerTextField.setText(medication.getProducer());
        stockTextField.setText(medication.getStock().toString());
        descriptionTextArea.setText(medication.getDescription());
    }

    private void disableControls() {
        nameTextField.setEditable(false);
        producerTextField.setEditable(false);
        stockTextField.setEditable(false);
        descriptionTextArea.setEditable(false);
        saveButton.setVisible(false);
    }

    public void initiateAddMedicationProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/medication-details.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add medication");
        saveButton.setOnAction(x -> handleAddMedication());
        stage.show();
    }

    public void initiateModifyMedicationProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/medication-details.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add medication");
        saveButton.setOnAction(x -> handleModifyMedication());
        stage.show();

        loadTextFieldData();
    }
}