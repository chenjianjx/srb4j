package ${groupId}.impl.biz.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */

#set($hashtag = '#')

@Repository
public interface UserRepo {

	@Insert("insert into User(principal, password, source, email, createdAt, createdBy) "
			+ "values (${hashtag}{principal}, ${hashtag}{password}, ${hashtag}{source}, ${hashtag}{email}, ${hashtag}{createdAt}, ${hashtag}{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public void saveNewUser(User user);

	@Select("select * from User where  principal = ${hashtag}{principal}")
	public User getUserByPrincipal(String principal);

	@Select("select * from User where  id = ${hashtag}{id}")
	public User getUserById(long id);

	@Update("update User set password = ${hashtag}{password}, updatedBy = ${hashtag}{updatedBy}, updatedAt = ${hashtag}{updatedAt}  where id = ${hashtag}{id}")
	public void updateUser(User newUser);

}
