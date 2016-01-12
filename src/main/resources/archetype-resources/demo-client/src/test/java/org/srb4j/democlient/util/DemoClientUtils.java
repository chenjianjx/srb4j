package org.srb4j.democlient.util;

import java.net.URLDecoder;

import org.srb4j.restclient.model.ErrorResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientUtils {

	public static String toJson(Object response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(response);
			return json;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String urlDecode(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (java.io.UnsupportedEncodingException wow) {
			throw new RuntimeException(wow);
		}
	}
	
	public static void printErrorResult(ErrorResult errorResult) {
		System.out.println("error code: " + errorResult.getError());
		System.out.println("error msg for developer: "
				+ errorResult.getErrorDescription());
		System.out.println("error msg for user: "
				+ errorResult.getErrorDescriptionForUser());
		System.out.println("exception id if any: "
				+ errorResult.getExceptionId());
	}
}
