package ${package}.pso.celebritysystem.bbs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ${package}.pso.common.PsoEntityBase;

#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")

/**
 * post for celebrity system. (An exemplary pso bean)
 * 
 * @author chenjianjx@gmail.com
 *
 */
	
#end	
public class CsPost extends PsoEntityBase  implements Serializable{

 
	private static final long serialVersionUID = 771086654014396291L;

#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")	
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

#end
}
