package com.starterkit.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.BooksRepository;
import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.Services;
import com.starterkit.javafx.dataprovider.data.BookVO;
import com.starterkit.javafx.model.BookSearch;
import com.starterkit.javafx.texttospeech.Speaker;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BookSearchController {

	private static final Logger LOG = Logger.getLogger(BookSearchController.class);

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField titleField;

	@FXML
	private TextField authorsField;

	@FXML
	private Button searchButton;

	@FXML
	private Button showBooksButton;

	@FXML
	private Button addBooksButton;

	@FXML
	private TableView<BookVO> resultTable;

	@FXML
	private TableColumn<BookVO, String> titleColumn;

	@FXML
	private TableColumn<BookVO, String> authorsColumn;

	@FXML
	private TableColumn<BookVO, String> yearColumn;

	@FXML
	private TableColumn<BookVO, String> genreColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final Speaker speaker = Speaker.INSTANCE;

	private final BookSearch model = new BookSearch();

	public BookSearchController() {
		LOG.debug("Constructor: nameField = " + titleField);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize(): nameField = " + titleField);

		initializeResultTable();

		titleField.textProperty().bindBidirectional(model.titleProperty());
		authorsField.textProperty().bindBidirectional(model.authorsProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		titleField.setText("");
		authorsField.setText("");

		searchButton.disableProperty()
				.bind(titleField.textProperty().isEmpty().and(authorsField.textProperty().isEmpty()));
	}

	private void initializeResultTable() {

		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		authorsColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthors()));
		authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));

		yearColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getYear()));
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

		genreColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getGenre()));
		genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookVO>() {

			@Override
			public void changed(ObservableValue<? extends BookVO> observable, BookVO oldValue, BookVO newValue) {
				LOG.debug(newValue + " selected");

				if (newValue != null) {
					Task<Void> backgroundTask = new Task<Void>() {

						@Override
						protected Void call() throws Exception {
							speaker.say(newValue.getTitle());
							return null;
						}

						@Override
						protected void failed() {
							LOG.error("Could not say name: " + newValue.getTitle(), getException());
						}
					};
					new Thread(backgroundTask).start();
				}
			}
		});
	}

	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");
		searchButtonActionVersion();
	}

	@FXML
	private void showButtonAction(ActionEvent event) {
		LOG.debug("'Show' button clicked");
		showButtonActionVersion();
	}

	@FXML
	private void addButtonAction(ActionEvent event) {
		LOG.debug("'Show' button clicked");

		addButtonActionVersion();
	}

	private void searchButtonActionVersion() {

		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			@Override
			protected Collection<BookVO> call() throws Exception {
				LOG.debug("call() called");

				if (model.getAuthors().isEmpty())
					model.setAuthors(" ");
				Collection<BookVO> result = dataProvider.findBooks(model.getTitle(), model.getAuthors());

				return result;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");
				Collection<BookVO> result = getValue();
				model.setResult(new ArrayList<BookVO>(result));
				resultTable.getSortOrder().clear();
			}
		};

		new Thread(backgroundTask).start();
	}

	private void showButtonActionVersion() {

		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			@Override
			protected Collection<BookVO> call() throws Exception {
				LOG.debug("call() called");

				Collection<BookVO> result = dataProvider.getBooks();
				return result;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");
				Collection<BookVO> result = getValue();
				model.setResult(new ArrayList<BookVO>(result));
				resultTable.getSortOrder().clear();
			}
		};

		new Thread(backgroundTask).start();
	}

	private void addButtonActionVersion() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/com/starterkit/javafx/view/person-add.fxml"), resources);
			Stage stage = new Stage();
			stage.setTitle("My New Stage Title");
			stage.setScene(new Scene(root, 450, 450));
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			BooksRepository.setPersons(Services.getBooks());
		}

	}
}
