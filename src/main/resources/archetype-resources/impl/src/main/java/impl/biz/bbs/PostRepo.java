package ${groupId}.${rootArtifactId}.impl.biz.bbs;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

#set($hashtag = '#')

/**
 * 
 * An exemplary repository class
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Repository
public interface PostRepo {

	@Insert("insert into Post(userId, content, createdAt, createdBy) "
			+ "values (${hashtag}{userId}, ${hashtag}{content}, ${hashtag}{createdAt}, ${hashtag}{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public void saveNewPost(Post post);

	@Select("select * from Post where  id = ${hashtag}{id}")
	public Post getPostById(long id);

	@Select("select * from Post order by id desc")
	public List<Post> getAllPosts();

	@Update("update Post set content = ${hashtag}{content}, updatedBy = ${hashtag}{updatedBy}, updatedAt = ${hashtag}{updatedAt}  where Id = ${hashtag}{id}")
	public void updatePost(Post newPost);

	@Delete("delete from post where id = ${hashtag}{id}")
	public void deletePostById(long id);

}

