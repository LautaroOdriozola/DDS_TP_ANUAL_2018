package com.tpdds;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class DataLoader {

	private static DataLoader instance;
	private Gson gson;

	private DataLoader() {
		gson = new Gson();
	}

	public static DataLoader getInstance() {
		if (instance == null) instance = new DataLoader();
		return instance;
	}

	// TODO: fileutils
	// TODO: tests
	public <T> List<T> cargarData(String archivo, Class<T> t) {
		try {
		    Class<?> tArray = Array.newInstance(t, 0).getClass();

			JsonReader reader = new JsonReader(new FileReader(archivo));
			return Arrays.asList(gson.fromJson(reader, tArray));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
