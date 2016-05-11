package com.starterkit.javafx.dataprovider.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.BooksRepository;
import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.Services;
import com.starterkit.javafx.dataprovider.data.BookVO;

public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	public DataProviderImpl() {
		BooksRepository.setPersons(Services.getBooks());
	}

	@Override
	public Collection<BookVO> findBooks(String title, String authors) {
		LOG.debug("Entering findPersons()");

		Collection<BookVO> result = new ArrayList<>();

		if (authors.trim().isEmpty())
			result = BooksRepository.getPersons().stream()
					.filter(p -> ((title == null || title.isEmpty())
							|| (title != null && !title.isEmpty() && p.getTitle().contains(title))))
					.collect(Collectors.toList());
		else if (title.trim().isEmpty()) {
			result = BooksRepository.getPersons().stream()
					.filter(p -> ((authors == null || authors.isEmpty())
							|| (authors != null && !authors.isEmpty() && p.getAuthors().contains(authors))))
					.collect(Collectors.toList());
		} else {
			result = BooksRepository.getPersons().stream()
					.filter(p -> ((title == null || title.isEmpty() || authors == null || authors.isEmpty())
							|| (title != null && !title.isEmpty() && p.getTitle().contains(title) && authors != null
									&& !authors.isEmpty() && p.getAuthors().contains(authors))))
					.collect(Collectors.toList());
		}
		LOG.debug("Leaving findPersons()");
		return result;
	}

	@Override
	public Collection<BookVO> getBooks() {
		Collection<BookVO> result = BooksRepository.getPersons();
		LOG.debug("Leaving findPersons()");
		return result;
	}
}
