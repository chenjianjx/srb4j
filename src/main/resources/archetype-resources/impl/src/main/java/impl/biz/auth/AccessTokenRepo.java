#set($hashtag = '#')
package ${package}.impl.biz.auth;

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
			+ "values (${hashtag}{tokenStr}, ${hashtag}{lifespan}, ${hashtag}{userId}, ${hashtag}{expiresAt}, ${hashtag}{refreshTokenStr}, ${hashtag}{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public long saveNewToken(AccessToken accessToken);

	@Select("select * from AccessToken where  tokenStr = ${hashtag}{tokenStr}")
	public AccessToken getByTokenStr(String tokenStr);

	@Select("select * from AccessToken where  refreshTokenStr = ${hashtag}{refreshTokenStr}")
	public AccessToken getByRefreshTokenStr(String refreshTokenStr);

	@Delete("delete from AccessToken where  tokenStr = ${hashtag}{tokenStr}")
	public void deleteByTokenStr(String tokenStr);

	@Delete("delete from AccessToken where  expiresAt < ${hashtag}{timestamp}")
	public int deleteTokensExpiresBefore(Timestamp timestamp);

	@Update("update AccessToken set tokenStr = ${hashtag}{tokenStr}, lifespan=${hashtag}{lifespan}, expiresAt = ${hashtag}{expiresAt}, refreshTokenStr = ${hashtag}{refreshTokenStr}, updatedBy = ${hashtag}{updatedBy} where id = ${hashtag}{id}")
	public void updateAccessToken(AccessToken newToken);

}
