package br.felipstein.searchcep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SearchCEP extends Application {
	
	private static SearchCEP instance;
	
	private Stage primaryStage;
	private Controller controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/Root.fxml"));
		Parent root = fxml.load();
		this.controller = fxml.getController();
		System.out.println("Controller: " + controller);
		primaryStage.setTitle("Busca Simples por CEP");
		primaryStage.getIcons().add(new Image("/cafe.png"));
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		this.primaryStage = primaryStage;
		instance = this;
		System.out.println("Controller: " + controller + ", nulo? " + (controller == null));
	}
	
	public Stage getStage() {
		return primaryStage;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static SearchCEP getInstance() {
		return instance;
	}
	
}
