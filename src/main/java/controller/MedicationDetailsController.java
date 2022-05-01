package controller;

import domain.entities.Medication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Services;

import java.io.IOException;
import java.net.URL;

/**
 * 
 */
public class MedicationDetailsController extends GenericController {

    private final Medication medication;
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

    public MedicationDetailsController(Services services, Stage stage, Medication medication) {
        super(services, stage);
        this.medication = medication;
    }

    /**
     * 
     */
    public void handleAddMedication() {
        // TODO implement here
    }

    @FXML
    public void handleCancel() {
        stage.close();
    }

    /**
     * 
     */
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

    /**
     * 
     */
    public void initiateAddMedicationProcedure() {
        // TODO implement here
    }

}