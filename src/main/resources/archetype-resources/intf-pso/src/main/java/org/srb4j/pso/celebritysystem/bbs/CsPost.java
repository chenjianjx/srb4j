package org.srb4j.pso.celebritysystem.bbs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.srb4j.pso.common.PsoEntityBase;

/**
 * post for celebrity system. (An exemplary pso bean)
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class CsPost extends PsoEntityBase  implements Serializable{

 
	private static final long serialVersionUID = 771086654014396291L;

	private Map<String, Integer> celebrityOccurenceMap = new HashMap<String, Integer>();

	private String content;

	/**
	 * key = celebrity's name, value = num of times this name exists in the post
	 * @return
	 */
	public Map<String, Integer> getCelebrityOccurenceMap() {
		return celebrityOccurenceMap;
	}

	public void setCelebrityOccurenceMap(
			Map<String, Integer> celebrityOccurenceMap) {
		this.celebrityOccurenceMap = celebrityOccurenceMap;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
