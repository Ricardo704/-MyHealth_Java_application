package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Model;
import model.Record;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller for Export Records page
 */
public class ExportRcdController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    @FXML
    private Button choosePath;
    @FXML
    private Button export;
    @FXML
    private Button cancel;
    @FXML
    private TextField writePath;
    @FXML
    private TextField name;
    @FXML
    private Label status;

    public ExportRcdController(Stage parentStage, Model model){
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    public void initialize() {

        //Enable user to choose directory easily
        choosePath.setOnAction(event->{
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(stage);
            if (directory != null){
                writePath.setText(directory.getAbsolutePath());
            }else {
                status.setText("No directory selected");
                status.setTextFill(Color.ORANGERED);
            }
        });

        export.setOnAction(event-> {
            Writer writer = null;
            ArrayList<Record> records;
            try {
                records = model.getUserDao().getRecords(model.getCurrentUser().getUsername());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (writePath.getText() != null && name.getText() != null) {
                    File file = new File(writePath.getText() + "\\" + name.getText() + ".csv");
                    writer = new BufferedWriter(new FileWriter(file));
                    for (Record record : records) {
                        String text = record.getRecordID() + "," + record.getUsername() + "," + record.getWeight() + "," + record.getTemperature() + ","
                                + record.getBloodPressure_low() + "," + record.getBloodPressure_high() + "," + record.getNotes() + "," + record.getDate() + "\n";
                        writer.write(text);
                        status.setText("Records exported");
                        status.setTextFill(Color.GREEN);
                    }
                }else {
                    status.setText("Empty file path or name");
                    status.setTextFill(Color.ORANGERED);
                }
            } catch (IOException e) {
                status.setText(e.getMessage());
                status.setTextFill(Color.RED);
            }
            finally {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    status.setText(e.getMessage());
                    status.setTextFill(Color.RED);
                }
            }
        });

        cancel.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 474, 281);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("Export records");
        stage.show();
    }


}
