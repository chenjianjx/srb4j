package ${groupId}.impl.biz.auth;

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

#set($hashtag = '#')

@Repository
public interface RandomLoginCodeRepo {

	/**
	 * save new code; if the user has already the code, overwrite it
	 * 
	 * @param code
	 */
	@Insert("insert into RandomLoginCode(codeStr, userId, expiresAt,createdAt, createdBy) "
			+ "values (${hashtag}{codeStr},  ${hashtag}{userId}, ${hashtag}{expiresAt}, ${hashtag}{createdAt}, ${hashtag}{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public void saveNewCode(RandomLoginCode code);

	@Select("select * from RandomLoginCode where  userId = ${hashtag}{userId}")
	public RandomLoginCode getByUserId(long userId);

	@Delete("delete from RandomLoginCode where  userId = ${hashtag}{userId}")
	public void deleteByUserId(long userId);

	@Delete("delete from RandomLoginCode where  expiresAt < ${hashtag}{timestamp}")
	public int deleteCodesExpiresBefore(Timestamp timestamp);

}
