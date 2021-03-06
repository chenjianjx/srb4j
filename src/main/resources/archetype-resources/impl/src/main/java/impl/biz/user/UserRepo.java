#set($hashtag = '#')
package ${package}.impl.biz.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */


@Repository
public interface UserRepo {

	@Insert("insert into User(principal, password, source, email, emailVerified, createdBy) "
			+ "values (${hashtag}{principal}, ${hashtag}{password}, ${hashtag}{source}, ${hashtag}{email}, ${hashtag}{emailVerified}, ${hashtag}{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public long saveNewUser(User user);

	@Select("select * from User where  principal = ${hashtag}{principal}")
	public User getUserByPrincipal(String principal);

	@Select("select * from User where  email = ${hashtag}{email}")
	public User getUserByEmail(String email);

	@Select("select * from User where  id = ${hashtag}{id}")
	public User getUserById(long id);

	@Update("update User set password = ${hashtag}{password}, emailVerified = ${hashtag}{emailVerified}, updatedBy = ${hashtag}{updatedBy}  where id = ${hashtag}{id}")
	public void updateUser(User newUser);

	@Select("select * from User order by id")
	List<User> getAllUsers();
}
