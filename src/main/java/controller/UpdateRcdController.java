package controller;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.Record;

/**
 * Controller for Update Record page
 */
public class UpdateRcdController {
    @FXML
    private TextField weight;
    @FXML
    private TextField temperature;
    @FXML
    private TextField bloodPressure_low;
    @FXML
    private TextField bloodPressure_high;
    @FXML
    private TextArea notes;
    @FXML
    private Button update;
    @FXML
    private Button close;
    @FXML
    private Label status;

    private Stage stage;
    private Stage parentStage;
    private Model model;

    public UpdateRcdController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    public void initialize(){
        update.setOnAction(event->{
            Record record = model.getCurrentRecord();
            if (record != null) {

                //Check if user fill at least one field
                if (!weight.getText().isEmpty() | !temperature.getText().isEmpty() |
                        (!bloodPressure_low.getText().isEmpty() && !bloodPressure_high.getText().isEmpty()) | !notes.getText().isEmpty()) {

                    //Check if Blood pressure_low is lower than BP_high when user inputs BP fields.
                    if (!bloodPressure_low.getText().isEmpty() && !bloodPressure_high.getText().isEmpty()){
                        if (!(parseDoubleFromTF(bloodPressure_high) > parseDoubleFromTF(bloodPressure_low))) {
                            status.setText("BloodPressure_Low must be lower than BP_High");
                            status.setTextFill(Color.ORANGERED);
                        }else {
                            try {
                                boolean flag = model.getUserDao().updateRecord(
                                        parseDoubleFromTF(weight),
                                        parseDoubleFromTF(temperature),
                                        parseDoubleFromTF(bloodPressure_low),
                                        parseDoubleFromTF(bloodPressure_high),
                                        notes.getText(), record.getDate(), record.getUsername(), record.getRecordID());
                                if (flag) {
                                    record.setDate(record.getDate());
                                    status.setText("Record updated");
                                    status.setTextFill(Color.GREEN);

                                } else {
                                    status.setText("Cannot create record");
                                    status.setTextFill(Color.RED);
                                }
                            } catch (SQLException e) {
                                status.setText(e.getMessage());
                                status.setTextFill(Color.RED);
                            }
                        }
                    }else {
                        try {
                            boolean flag = model.getUserDao().updateRecord(
                                    parseDoubleFromTF(weight),
                                    parseDoubleFromTF(temperature),
                                    parseDoubleFromTF(bloodPressure_low),
                                    parseDoubleFromTF(bloodPressure_high),
                                    notes.getText(), record.getDate(), record.getUsername(), record.getRecordID());
                            if (flag) {
                                record.setDate(record.getDate());
                                status.setText("Record updated");
                                status.setTextFill(Color.GREEN);

                            } else {
                                status.setText("Cannot create record");
                                status.setTextFill(Color.RED);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                            }
                    }
                } else {
                    status.setText("Please enter at least ONE field");
                    status.setTextFill(Color.RED);
                }
            }else {
                status.setText("Can not update this record");
                status.setTextFill(Color.RED);
            }
        });


        close.setOnAction(event -> {
            stage.close();
//            parentStage.show();
        });
    }

    /**
     * The method to check if user's input is an integer.
     */
    private Double parseDoubleFromTF(TextField tf){
        //return tf.getText() != null ? Double.parseDouble(tf.getText()): 0;
        if (!tf.getText().isEmpty()){
            try {
                return Double.parseDouble(tf.getText());
            }catch (Exception e){
                status.setText("Your input should be an integer!");
                status.setTextFill(Color.RED);
            }
        }else {
            return 0.0;
        }
        return parseDoubleFromTF(tf);
    }


    public void showStage(Pane root) {
        Scene scene = new Scene(root, 500, 475);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("Update record");
        stage.showAndWait();
    }
}
