package controller;

import domain.entities.Medication;
import domain.entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import services.Services;

import java.io.IOException;
import java.net.URL;

/**
 * 
 */
public class AdminController extends GenericController {

    @FXML
    private TableView<Medication> medicationsTable;
    @FXML
    private TableColumn<Medication, String> medicationIDColumn;
    @FXML
    private TableColumn<Medication, String> medicationNameColumn;
    @FXML
    private TableColumn<Medication, String> medicationProducerColumn;
    @FXML
    private TableColumn<Medication, String> medicationStockColumn;
    private final ObservableList<Medication> medicationsModel = FXCollections.observableArrayList();
    @FXML
    private final Stage loginStage;

    public AdminController(User loggedUser, Services services, javafx.stage.Stage stage, Stage loginStage) {
        super(loggedUser, services, stage);
        this.loginStage = loginStage;
    }

    @FXML
    public void handleLogout() {
        stage.close();
        loginStage.show();
    }

    /**
     * 
     */
    public void initiateAddMedicationProcedure() {
        // TODO implement here
    }

    public void initialize() {
        updateMedicationsModel();
        initializeMedicationsTable();
    }

    /**
     * 
     */
    public void initiateViewMenuProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/admin-menu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Hospital Pharmacy System");
        stage.show();
    }

    private void initializeMedicationsTable() {
        updateMedicationsModel();
        medicationsTable.setItems(medicationsModel);
        medicationsTable.setRowFactory(tv -> {
            TableRow<Medication> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    handleViewMedicationDetails(row.getItem());
                }
            });
            return row;
        });
        medicationIDColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId().toString()));
        medicationNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        medicationProducerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProducer()));
        medicationStockColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStock().toString()));
    }

    /**
     *
     */
    private void handleViewMedicationDetails(Medication medication) {
        MedicationDetailsController medicationDetailsController = new MedicationDetailsController(services, new Stage(), medication);
        try {
            medicationDetailsController.initiateViewMedicationDetailsProcedure();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateMedicationsModel() {
        medicationsModel.setAll(services.getAllMedications());
    }
}