package com.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.javafx.dataprovider.data.BookVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Data displayed on the person search screen.
 *
 * @author Leszek
 */
public class BookSearch {

	private final StringProperty title = new SimpleStringProperty();
	private final StringProperty authors = new SimpleStringProperty();
	private final ListProperty<BookVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final String getTitle() {
		return title.get();
	}

	public final void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public final String getAuthors() {
		return authors.get();
	}

	public final void setAuthors(String value) {
		authors.set(value);
	}

	public StringProperty authorsProperty() {
		return authors;
	}

	public final List<BookVO> getResult() {
		return result.get();
	}

	public final void setResult(List<BookVO> value) {
		result.setAll(value);
	}

	public ListProperty<BookVO> resultProperty() {
		return result;
	}

	@Override
	public String toString() {
		return "PersonSearch [name=" + title  + ", result=" + result + "]";
	}

}
