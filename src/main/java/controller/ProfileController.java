package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

/**
 * Controller for View Profile page
 */
public class ProfileController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    @FXML
    private Label firstname;
    @FXML
    private Label lastname;
    @FXML
    private Label message;
    @FXML
    private Button close;
    @FXML
    private Label username;


    public ProfileController(Stage parentStage, Model model){
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    @FXML
    public void initialize(){
        firstname.setText(model.getCurrentUser().getFirstname());
        lastname.setText(model.getCurrentUser().getLastname());
        username.setText(model.getCurrentUser().getUsername());
        close.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 440, 337);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("Profile");
        stage.show();
    }

}
