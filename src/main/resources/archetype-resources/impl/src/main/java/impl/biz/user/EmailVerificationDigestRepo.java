package com.github.chenjianjx.srb4jfullsample.impl.biz.user;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */


@Repository
public interface EmailVerificationDigestRepo {

	/**
	 * save new digest
	 * 
	 * @param digest
	 */
	@Insert("insert into EmailVerificationDigest(digestStr, userId, expiresAt, createdBy) "
			+ "values (#{digestStr},  #{userId}, #{expiresAt}, #{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	long saveNewDigest(EmailVerificationDigest digest);

	@Select("select * from EmailVerificationDigest where  userId = #{userId}")
	EmailVerificationDigest getByUserId(long userId);

	@Delete("delete from EmailVerificationDigest where  userId = #{userId}")
	void deleteByUserId(long userId);

	@Delete("delete from EmailVerificationDigest where  expiresAt < #{timestamp}")
	int deleteDigestsExpiresBefore(Timestamp timestamp);

	@Select("select * from EmailVerificationDigest where  digestStr = #{digestStr}")
	EmailVerificationDigest getByDigestStr(String digestStr);
}
