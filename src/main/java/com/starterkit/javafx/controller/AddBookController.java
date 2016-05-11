package com.starterkit.javafx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.starterkit.javafx.dataprovider.BooksRepository;
import com.starterkit.javafx.dataprovider.Services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookController {

	private static final Logger LOG = Logger.getLogger(AddBookController.class);

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField newTitleField;

	@FXML
	private TextField newAuthorsField;

	@FXML
	private TextField newStatusField;

	@FXML
	private TextField newGenreField;

	@FXML
	private TextField newYearField;

	@FXML
	private Button closeWindowButton;

	@FXML
	private Button saveBookButton;

	public AddBookController() {

	}

	@FXML
	private void initialize() {

	}

	@FXML
	private void closeWindowButtonAction(ActionEvent event) {
		LOG.debug("'Close' button clicked");
		closeAddBookWindow();
	}

	@FXML
	private void saveButtonAction(ActionEvent event) {
		LOG.debug("'Save' button clicked");
		saveBookToDatabase();
	}

	private void closeAddBookWindow() {
		BooksRepository.setPersons(Services.getBooks());
		Stage stage = (Stage) newTitleField.getScene().getWindow();
		stage.close();
	}

	private void saveBookToDatabase() {
		Services service = new Services(generateJSONFromTextFields());
		service.doPost();
	}

	@SuppressWarnings("unchecked")
	private JSONObject generateJSONFromTextFields() {
		JSONObject json = new JSONObject();
		json.put("title", newTitleField.getText());
		json.put("authors", newAuthorsField.getText());
		json.put("status", newStatusField.getText());
		json.put("genre", newGenreField.getText());
		json.put("year", newYearField.getText());

		return json;
	}

}
