package ${groupId}.intf.bo.bbs;

import ${groupId}.intf.fo.bbs.FoPost;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * an exemplary bo entity
 * 
 * @author chenjianjx@gmail.com
 *
 */

public class BoPost extends FoPost {

	private String userPrincipal;

	public String getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(String userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	@Override
	public String toString() {
		try {
			return toJson(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}

	private String toJson(Object response) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(response);
		return json;
	}
}
