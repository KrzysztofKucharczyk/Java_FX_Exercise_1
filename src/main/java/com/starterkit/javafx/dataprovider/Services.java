package com.starterkit.javafx.dataprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.starterkit.javafx.dataprovider.data.BookVO;
import com.starterkit.javafx.dataprovider.impl.DataProviderImpl;

// REV: nazwa klasy nic nie mowi
public class Services {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	private JSONObject json;

	public Services(JSONObject json) {
		this.json = json;
	}

	public void doPost() {
		// REV: to nie jest dobre miejsce na startowanie watku, to wywolujacy ta operacje powinien moc zdecydowac czy operacja wykona sie w osobnym watku
		new Thread(new Runnable() {
			@Override
			public void run() {
				// REV: ten obiekt powinien byc utworzony tylko raz jako zmienna klasy
				HttpClient httpClient = HttpClientBuilder.create().build();
				try {
					// REV: adres powinien byc pobrany z konfiguracji
					HttpPost request = new HttpPost("http://localhost:8080/webstore/rbooks/");

					StringEntity params = new StringEntity(json.toString());
					request.addHeader("content-type", "application/json");
					request.setEntity(params);
					httpClient.execute(request);
					// REV: powinienes sprawdzic co zwrocil serwer

				} catch (Exception ex) {
					// REV: obsluga wyjatkow
					ex.printStackTrace();
				}
			}
		}).start();
	}

	// REV: nieuzywany kod poiwnien byc usuniety
	public void doPut() {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// HttpClient httpClient = HttpClientBuilder.create().build();
		// try {
		// HttpPut request = new
		// HttpPut("http://localhost:8080/webstore/rbooks/");
		//
		// StringEntity params = new StringEntity(json.toString());
		// request.addHeader("content-type", "application/json");
		// request.setEntity(params);
		// httpClient.execute(request);
		//
		// } catch (Exception ex) {
		// }
		// }
		// }).start();
	}

	public static List<BookVO> searchBooks(String title, String authors) {
		List<BookVO> books = new ArrayList<>();
		LOG.debug("Searching books from db");

		if (title == null)
			title = "";

		if (authors == null)
			authors = "";

		final String searchTitle = title;
		final String searchAuthors = authors;

		StringBuffer response = new StringBuffer();
		// REV: j.w.
		String url = "http://localhost:8080/webstore/rbooks/search?title=" + searchTitle + "&authors=" + searchAuthors;

		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		} catch (MalformedURLException e) {
			// REV: obsluga wyjatkow
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONArray json = new JSONArray();
		try {
			json = (JSONArray) new JSONParser().parse(response.toString());
		} catch (ParseException e) {
			// REV: j.w.
			e.printStackTrace();
		}

		for (int i = 0; i < json.size(); i++) {
			JSONObject j = (JSONObject) json.get(i);
			// REV: ta konwersje powinna zalatwic biblioteka do obslugi JSONow
			BookVO q = new BookVO(j.get("title").toString(), j.get("authors").toString(), j.get("status").toString(),
					j.get("genre").toString(), j.get("year").toString());

			books.add(q);

		}

		// REV: nie ma potrzeby przechowywania wynikow, wystarczy je zwrocic
		BooksRepository.setBooks(books);
		return books;
	}

}
