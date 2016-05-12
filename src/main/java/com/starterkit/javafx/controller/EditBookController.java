package com.starterkit.javafx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.starterkit.javafx.dataprovider.Services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBookController {

	private static final Logger LOG = Logger.getLogger(EditBookController.class);

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField editTitleField;

	@FXML
	private TextField editAuthorsField;

	@FXML
	private TextField editStatusField;

	@FXML
	private TextField editGenreField;

	@FXML
	private TextField editYearField;

	@FXML
	private Button closeWindowButton;

	@FXML
	private Button editBookButton;

	public EditBookController() {

	}

	@FXML
	private void initialize() {

	}

	@FXML
	private void closeWindowButtonAction(ActionEvent event) {
		LOG.debug("'Close' button clicked");
		closeEditBookWindow();
	}

	@FXML
	private void editButtonAction(ActionEvent event) {
		LOG.debug("'Save' button clicked");
		saveBookToDatabase();
	}

	private void closeEditBookWindow() {
		Stage stage = (Stage) editTitleField.getScene().getWindow();
		stage.close();
	}

	private void saveBookToDatabase() {
		Services service = new Services(generateJSONFromTextFields());
		service.doPut();
	}

	@SuppressWarnings("unchecked")
	private JSONObject generateJSONFromTextFields() {
		JSONObject json = new JSONObject();
		json.put("title", editTitleField.getText());
		json.put("authors", editAuthorsField.getText());
		json.put("status", editStatusField.getText());
		json.put("genre", editGenreField.getText());
		json.put("year", editYearField.getText());

		return json;
	}

}
