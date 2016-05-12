package com.starterkit.javafx.dataprovider.impl;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.BooksRepository;
import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.data.BookVO;

public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	public DataProviderImpl() {

	}

	@Override
	public Collection<BookVO> getBooks() {
		Collection<BookVO> result = BooksRepository.getBooks();
		LOG.debug("Leaving findPersons()");
		return result;
	}
}
