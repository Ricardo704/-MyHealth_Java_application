package controller;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.Record;

/**
 * Controller for Create Record page
 */
public class AddRecordController {
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
    private Button add;
    @FXML
    private Button close;
    @FXML
    private Label status;

    private Stage stage;
    private Stage parentStage;
    private Model model;

    public AddRecordController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    public void initialize(){
        add.setOnAction(event->{

            //Check if user fill at least one field
            if (!weight.getText().isEmpty() | !temperature.getText().isEmpty() |
                    (!bloodPressure_low.getText().isEmpty() && !bloodPressure_high.getText().isEmpty()) |
                     !notes.getText().isEmpty()){
                User user = model.getCurrentUser();
                Record record;
                try {
                    Integer newRecordID = model.getUserDao().recordIdMax(user.getUsername()).getRecordID() + 1;
                    record = model.getUserDao().createRecord(newRecordID,
                            parseDoubleFromTF(weight),
                            parseDoubleFromTF(temperature),
                            parseDoubleFromTF(bloodPressure_low),
                            parseDoubleFromTF(bloodPressure_high),
                            notes.getText(), user.getUsername());
                    if (record != null){

                        //Check if Blood pressure_low is lower than BP_high when user inputs BP fields.
                        if (!bloodPressure_low.getText().isEmpty() && !bloodPressure_high.getText().isEmpty()){
                            if (!(parseDoubleFromTF(bloodPressure_high) > parseDoubleFromTF(bloodPressure_low))){
                                status.setText("BP_Low should be lower than BP_High");
                                status.setTextFill(Color.ORANGERED);
                            }else {
                                record.setDate(record.getDate());
                                status.setText("Record created" );
                                status.setTextFill(Color.GREEN);
                            }
                        }else {
                            record.setDate(record.getDate());
                            status.setText("Record created" );
                            status.setTextFill(Color.GREEN);
                        }
                    }else {
                        status.setText("Cannot create record");
                        status.setTextFill(Color.ORANGERED);
                    }
                } catch (SQLException e) {
                    status.setText(e.getMessage());
                    status.setTextFill(Color.ORANGERED);
                }
            }else {
                status.setText("Please fill at least ONE field");
                status.setTextFill(Color.RED);
            }
        });

        close.setOnAction(event -> {
            stage.close();
            parentStage.show();
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
        stage.getIcons().add(new Image("/images/icon.png"));        stage.setTitle("Create a record");
        stage.show();
    }
}
