package ${package}.democlient.util;

import java.net.URLDecoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import ${package}.restclient.model.ErrorResult;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientUtils {

	public static Client createRestClient() {
		return ClientBuilder.newBuilder()
				.register(JacksonJaxbJsonProvider.class).build();
	}

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

	public static <T> T fromJson(String json, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
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
