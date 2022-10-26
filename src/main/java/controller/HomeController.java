package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.Record;
import model.User;

/**
 * Controller for Home page
 */
public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private Label homeMessage;
	@FXML
	private Label message;
	@FXML
	private MenuItem viewProfile;
	@FXML
	private MenuItem updateProfile;
	@FXML
	private MenuItem viewRecord;
	@FXML
	private MenuItem createRecord;
	@FXML
	private Button logOut;
	@FXML
	private MenuItem export;
	@FXML
	private MenuItem about;

	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	public void setHomeMessage(String firstName, String lastName) {
		this.homeMessage.setText("Hi " + firstName + " "  + lastName +" !");
	}

	@FXML
	public void initialize() {
		setHomeMessage(model.getCurrentUser().getFirstname(), model.getCurrentUser().getLastname());
		viewProfile.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
				ProfileController profileController = new ProfileController(stage, model);
				loader.setController(profileController);
				VBox root = loader.load();
				profileController.showStage(root);
			}catch (IOException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});

		updateProfile.setOnAction(event ->{
			try{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditProfileView.fxml"));
				EditProfileController editProfileController = new EditProfileController(stage,model);
				loader.setController(editProfileController);
				VBox root = loader.load();
				editProfileController.showStage(root);

				//Refresh firstname and lastname shown on home page after updating profile
				setHomeMessage(model.getCurrentUser().getFirstname(), model.getCurrentUser().getLastname());
			}catch (IOException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});

		viewRecord.setOnAction(event ->{
			try{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordView.fxml"));
				RecordController recordController = new RecordController(stage, model);
				loader.setController(recordController);
				VBox root = loader.load();
				recordController.showStage(root);
			}catch (IOException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});

		createRecord.setOnAction(event ->{
			try{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddRecordView.fxml"));
				AddRecordController addRecordController = new AddRecordController(stage, model);
				loader.setController(addRecordController);
				VBox root = loader.load();
				addRecordController.showStage(root);
				stage.close();
			}catch (IOException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});

		export.setOnAction(event-> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportRcdView.fxml"));
				ExportRcdController exportRcdController = new ExportRcdController(stage, model);
				loader.setController(exportRcdController);
				VBox root = loader.load();
				exportRcdController.showStage(root);
			}catch (IOException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});

		about.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AboutView.fxml"));
				AboutController aboutController = new AboutController(stage, model);
				loader.setController(aboutController);
				VBox root = loader.load();
				aboutController.showStage(root);
			}catch (IOException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});

		logOut.setOnAction(event -> {
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
				LoginController loginController = new LoginController(new Stage(), model);
				loader.setController(loginController);
				VBox root = loader.load();
				loginController.showStage(root);
				stage.close();
			}catch (IOException e){
				message.setText(e.getMessage());
				message.setTextFill(Color.RED);
			}
		});

	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 517, 450);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.getIcons().add(new Image("/images/icon.png"));
		stage.setTitle("Home");
		stage.showAndWait();
	}

}
