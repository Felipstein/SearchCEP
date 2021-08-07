package br.felipstein.searchcep;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertException extends Exception {
	
	private static final long serialVersionUID = -1673218252813169334L;
	
	private String title, header;
	
	public AlertException(String title, String header, String context) {
		super(context);
		this.title = title;
		this.header = header;
	}
	
	public Alert buildAlert() {
		return buildAlert(AlertType.ERROR);
	}
	
	public Alert buildAlert(AlertType type) {
		Alert alert = new Alert(type);
		alert.initOwner(SearchCEP.getInstance().getStage());
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(getMessage());
		return alert;
	}
	
	public Optional<ButtonType> showDialog() {
		return buildAlert().showAndWait();
	}
	
}