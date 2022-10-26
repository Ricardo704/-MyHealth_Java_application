package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.Record;

/**
 * Controller for View Record page
 */
public class RecordController {

    private Model model;
    private Stage stage;
    private Stage parentStage;
    private ObservableList<Record> data;

    @FXML
    private Label weight;
    @FXML
    private Label temperature;
    @FXML
    private Label bloodPressure_low;
    @FXML
    private Label bloodPressure_high;
    @FXML
    private Label notes;
    @FXML
    private Label message;
    @FXML
    private Button close;
    @FXML
    private Button delete;
    @FXML
    private Button update;
    @FXML
    private TableView <Record> table;
    @FXML
    private TableColumn <Record, String> date;
    @FXML
    private TableColumn <Record, Double> weightCol;
    @FXML
    private TableColumn <Record, Double> tempCol;
    @FXML
    private TableColumn <Record, Double> bpLowCol;
    @FXML
    private TableColumn <Record, Double> bpHighCol;
    @FXML
    private TableColumn <Record, String> noteCol;

    public RecordController(Stage parentStage, Model model){
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    @FXML
    public void initialize() throws SQLException {

        /**
         * Import existing records from database to TableView
         */
        ObservableList<Record> data = FXCollections.observableArrayList();
        table.setPlaceholder(new Label("No record found"));
        User user = model.getCurrentUser();
        try {
            ArrayList<Record> records = model.getUserDao().getRecords(user.getUsername());
            weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
            tempCol.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            bpLowCol.setCellValueFactory(new PropertyValueFactory<>("bloodPressure_low"));
            bpHighCol.setCellValueFactory(new PropertyValueFactory<>("bloodPressure_high"));
            noteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            if (records != null){
                data.addAll(records);
                table.setItems(data);
            } else {
                message.setText("");
            }
        }catch (SQLException e) {
            message.setText(e.getMessage());
            message.setTextFill(Color.RED);
        }

        /**
         * Update a record
         */
        update.setOnAction(event->{
            Record selectRecord = table.getSelectionModel().getSelectedItem();
            model.setCurrentRecord(selectRecord);
            if (selectRecord != null){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateRecordView.fxml"));
                    UpdateRcdController updateRcdController = new UpdateRcdController(stage,model);
                    loader.setController(updateRcdController);
                    VBox root = loader.load();
                    updateRcdController.showStage(root);

                    //Refresh data in table after updating
                    ArrayList<Record> records = model.getUserDao().getRecords(user.getUsername());
                    data.clear();
                    data.addAll(records);
                    table.setItems(data);
                }catch (IOException | SQLException e) {
                    message.setText(e.getMessage());
                }
            }
        });

        /**
         * Delete a record
         */
        delete.setOnAction(event-> {
            Record selectRecord = table.getSelectionModel().getSelectedItem();
            model.setCurrentRecord(selectRecord);
            if (selectRecord != null){
                try {
                    boolean flag = model.getUserDao().deleteRecord(selectRecord.getRecordID(), selectRecord.getUsername());
                    if (flag){
                        message.setText("Record deleted");
                        message.setTextFill(Color.GREEN);
                        ArrayList<Record> records = model.getUserDao().getRecords(user.getUsername());
                        data.clear();
                        data.addAll(records);
                        table.setItems(data);
                    }else {
                        message.setText("Cannot delete this record");
                        message.setTextFill(Color.ORANGERED);
                    }
                } catch (SQLException e) {
                    message.setText(e.getMessage());
                    message.setTextFill(Color.RED);
                }
            }else {
                message.setText("Please select a valid record");
                message.setTextFill(Color.ORANGERED);
            }
        });

        close.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }


    public void showStage(Pane root) {
        Scene scene = new Scene(root, 720, 490);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("Record");
        stage.show();
    }

}
