package com.starterkit.javafx.dataprovider;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.javafx.dataprovider.data.BookVO;

public class BooksRepository {

	private static List<BookVO> books = new ArrayList<>();
	
	public static List<BookVO> getBooks() {
		return books;
	}

	public static void setBooks(List<BookVO> newBooks) {
		books = newBooks;
	}	
}
