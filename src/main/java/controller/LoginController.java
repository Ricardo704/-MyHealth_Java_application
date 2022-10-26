package controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

/**
 * Controller for Login page
 */
public class LoginController {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;

	@FXML
	private Button login;
	@FXML
	private Button signup;

	private Model model;
	private Stage stage;
	
	public LoginController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}
	
	@FXML
	public void initialize() {
		login.setOnAction(event -> {
			if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
				User user;
				try {
					user = model.getUserDao().getUser(username.getText(), password.getText());
					if (user != null) {
						model.setCurrentUser(user);
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));

							// Customize controller instance
							HomeController homeController = new HomeController(stage, model);
							loader.setController(homeController);
							VBox root = loader.load();
							stage.close();
							homeController.showStage(root);

						}catch (IOException e) {
							message.setText(e.getMessage());
						}
					} else {
						message.setText("Wrong username or password");
						message.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					message.setText(e.getMessage());
					message.setTextFill(Color.RED);
				}
				
			} else {
				message.setText("Empty username or password");
				message.setTextFill(Color.RED);
			}
			username.clear();
			password.clear();
		});
		
		signup.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignupView.fxml"));
				SignupController signupController =  new SignupController(stage, model);
				loader.setController(signupController);
				VBox root = loader.load();
				signupController.showStage(root);
				message.setText("");
				username.clear();
				password.clear();
				stage.close();
			} catch (IOException e) {
				message.setText(e.getMessage());
			}});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 407, 374);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.getIcons().add(new Image("/images/icon.png"));
		stage.setTitle("Welcome");
		stage.show();
	}
}

