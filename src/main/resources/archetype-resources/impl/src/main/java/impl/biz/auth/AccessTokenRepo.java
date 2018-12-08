package com.github.chenjianjx.srb4jfullsample.impl.biz.auth;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Delete;
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


@Repository
public interface AccessTokenRepo {

	@Insert("insert into AccessToken(tokenStr, lifespan,  userId, expiresAt,refreshTokenStr, createdBy) "
			+ "values (#{tokenStr}, #{lifespan}, #{userId}, #{expiresAt}, #{refreshTokenStr}, #{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public long saveNewToken(AccessToken accessToken);

	@Select("select * from AccessToken where  tokenStr = #{tokenStr}")
	public AccessToken getByTokenStr(String tokenStr);

	@Select("select * from AccessToken where  refreshTokenStr = #{refreshTokenStr}")
	public AccessToken getByRefreshTokenStr(String refreshTokenStr);

	@Delete("delete from AccessToken where  tokenStr = #{tokenStr}")
	public void deleteByTokenStr(String tokenStr);

	@Delete("delete from AccessToken where  expiresAt < #{timestamp}")
	public int deleteTokensExpiresBefore(Timestamp timestamp);

	@Update("update AccessToken set tokenStr = #{tokenStr}, lifespan=#{lifespan}, expiresAt = #{expiresAt}, refreshTokenStr = #{refreshTokenStr}, updatedBy = #{updatedBy} where id = #{id}")
	public void updateAccessToken(AccessToken newToken);

}
