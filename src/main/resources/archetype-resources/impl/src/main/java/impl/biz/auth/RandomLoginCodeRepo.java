package com.github.chenjianjx.srb4jfullsample.impl.biz.auth;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */


@Repository
public interface RandomLoginCodeRepo {

	/**
	 * save new code
	 * 
	 * @param code
	 */
	@Insert("insert into RandomLoginCode(codeStr, userId, expiresAt, createdBy) "
			+ "values (#{codeStr},  #{userId}, #{expiresAt}, #{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public long saveNewCode(RandomLoginCode code);

	@Select("select * from RandomLoginCode where  userId = #{userId}")
	public RandomLoginCode getByUserId(long userId);

	@Delete("delete from RandomLoginCode where  userId = #{userId}")
	public void deleteByUserId(long userId);

	@Delete("delete from RandomLoginCode where  expiresAt < #{timestamp}")
	public int deleteCodesExpiresBefore(Timestamp timestamp);

}
