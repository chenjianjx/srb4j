package com.github.chenjianjx.srb4jfullsample.impl.biz.user;

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
			+ "values (#{principal}, #{password}, #{source}, #{email}, #{emailVerified}, #{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public long saveNewUser(User user);

	@Select("select * from User where  principal = #{principal}")
	public User getUserByPrincipal(String principal);

	@Select("select * from User where  email = #{email}")
	public User getUserByEmail(String email);

	@Select("select * from User where  id = #{id}")
	public User getUserById(long id);

	@Update("update User set password = #{password}, emailVerified = #{emailVerified}, updatedBy = #{updatedBy}  where id = #{id}")
	public void updateUser(User newUser);

	@Select("select * from User order by id")
	List<User> getAllUsers();
}
