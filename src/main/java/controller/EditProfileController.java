package controller;

import javafx.fxml.FXML;
import java.sql.SQLException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

/**
 * Controller for Edit Profile page
 */
public class EditProfileController {
    private Model model;
    private Stage stage;
    private Stage parentStage;

    @FXML
    private TextField lastname;
    @FXML
    private TextField firstname;
    @FXML
    private Button close;
    @FXML
    private Button update;
    @FXML
    private Label message;
    @FXML
    private Label username;


    public EditProfileController(Stage parentStage, Model model){
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    @FXML
    public void initialize(){
        username.setText(model.getCurrentUser().getUsername());
        update.setOnAction(event -> {
            if (!firstname.getText().isEmpty() && !lastname.getText().isEmpty()){
                String newFirstName = firstname.getText();
                String newLastName = lastname.getText();
                User user = model.getCurrentUser();
                try{
                    boolean flag = model.getUserDao().updateUser(user.getUsername(), newFirstName, newLastName);
                    if (flag){
                        user.setFirstname(newFirstName);
                        user.setLastname(newLastName);

                        message.setText(user.getUsername() + " profile updated");
                        message.setTextFill(Color.GREEN);
                    }
                }catch (SQLException e){
                    message.setText(e.getMessage());
                    message.setTextFill(Color.RED);
                }
            } else {
                message.setText("Empty firstname or lastname");
                message.setTextFill(Color.RED);
            }
            firstname.clear();
            lastname.clear();
        });

        close.setOnAction(event -> {
            stage.close();
        });
    }


    public void showStage(Pane root) {
        Scene scene = new Scene(root, 455, 373);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("Update profile");
        stage.showAndWait();
    }

}
