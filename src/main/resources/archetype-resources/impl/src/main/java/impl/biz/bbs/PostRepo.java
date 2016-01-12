package ${groupId}.impl.biz.bbs;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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
			+ "values (#{userId}, #{content}, #{createdAt}, #{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public void saveNewPost(Post post);

	@Select("select * from Post where  id = #{id}")
	public Post getPostById(long id);

	@Select("select * from Post order by id desc")
	public List<Post> getAllPosts();

	@Update("update Post set content = #{content}, updatedBy = #{updatedBy}, updatedAt = #{updatedAt}  where Id = #{id}")
	public void updatePost(Post newPost);

	@Delete("delete from post where id = #{id}")
	public void deletePostById(long id);

}
