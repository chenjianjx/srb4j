package org.srb4j.intf.bo.bbs;

import java.util.List;

import org.srb4j.intf.fo.basic.FoResponse;

/**
 * an exemplary bo-intf manager
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface BoBbsManager {

	public FoResponse<List<BoPost>> getAllPostsForBbsAdmin(Long currentUserId);

}
