package controller;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

/**
 * Controller for Sign Up page
 */
public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private Button createUser;
	@FXML
	private Button close;
	@FXML
	private Label status;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;

	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
		createUser.setOnAction(event -> {
			if (!username.getText().isEmpty() && !password.getText().isEmpty() && !firstname.getText().isEmpty() && !lastname.getText().isEmpty()) {
				User user;
				try {

					//Check if the username is taken
					boolean flag = model.getUserDao().checkUsername(username.getText());
					if (flag){
						user = model.getUserDao().createUser(username.getText(), password.getText(), firstname.getText(), lastname.getText());
						if (user != null) {
							status.setText("Created " + user.getUsername());
							status.setTextFill(Color.GREEN);
						} else {
							status.setText("Cannot create user");
							status.setTextFill(Color.RED);
						}
					}else {
						status.setText("Username is taken");
						status.setTextFill(Color.RED);
					}

				} catch (SQLException e) {
					status.setText(e.getMessage());
					status.setTextFill(Color.RED);
				}
				
			} else {
				status.setText("Empty username or password");
				status.setTextFill(Color.RED);
			}
		});

		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 418, 530);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.getIcons().add(new Image("/images/icon.png"));
		stage.setTitle("Sign up");
		stage.show();
	}
}
