package ${groupId}.${rootArtifactId}.intf.fo.bbs;

import java.util.List;

import ${groupId}.${rootArtifactId}.intf.fo.basic.FoResponse;

/**
 * an exemplary manager
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface FoBbsManager {

	public FoResponse<FoPost> newPost(Long currentUserId,
			FoNewPostRequest request);
	
	public FoResponse<FoPost> updatePost(Long currentUserId,
			FoUpdatePostRequest request);

	public FoResponse<List<FoPost>> getAllPosts(Long currentUserId);

	public FoResponse<Void> deletePostById(Long currentUserId, Long postId);

}
