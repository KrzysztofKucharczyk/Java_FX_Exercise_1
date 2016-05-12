package com.starterkit.javafx.dataprovider;

import java.util.Collection;

import com.starterkit.javafx.dataprovider.data.BookVO;
import com.starterkit.javafx.dataprovider.impl.DataProviderImpl;


public interface DataProvider {

	DataProvider INSTANCE = new DataProviderImpl();

	Collection<BookVO> getBooks();
}
