package com.starterkit.javafx.dataprovider;

import java.util.ArrayList;
import java.util.Collection;

import com.starterkit.javafx.dataprovider.data.BookVO;

public class BooksRepository {

	private static Collection<BookVO> persons = new ArrayList<>();
	
	public static Collection<BookVO> getPersons() {
		return persons;
	}

	public static void setPersons(Collection<BookVO> newPersons) {
		persons = newPersons;
	}
		
}
