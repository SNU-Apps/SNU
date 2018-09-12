package com.androidapp.snu.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonBuilder {
	public static String toJsonString(final Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> T fromJsonString(final String json, Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(json, clazz);
		} catch (IOException e) {
			return null;
		}
	}
}
