package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;

/**
 * Controller for About page
 */
public class AboutController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    @FXML
    private Label version;
    @FXML
    private Button close;

    public AboutController(Stage parentStage, Model model){
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    @FXML
    public void initialize(){

        version.setText(" 1.7.4");

        close.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });

    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 324, 505);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("About MyHealth");
        stage.show();
    }


}
