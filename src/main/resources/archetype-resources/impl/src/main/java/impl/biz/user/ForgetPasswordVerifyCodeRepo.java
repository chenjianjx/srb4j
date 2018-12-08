#set($hashtag = '#')
package ${package}.impl.biz.user;

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
public interface ForgetPasswordVerifyCodeRepo {

	/**
	 * save new code
	 * 
	 * @param code
	 */
	@Insert("insert into ForgetPasswordVerifyCode(codeStr, userId, expiresAt, createdBy) "
			+ "values (${hashtag}{codeStr},  ${hashtag}{userId}, ${hashtag}{expiresAt}, ${hashtag}{createdBy})")
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
	public long saveNewCode(ForgetPasswordVerifyCode code);

	@Select("select * from ForgetPasswordVerifyCode where  userId = ${hashtag}{userId}")
	public ForgetPasswordVerifyCode getByUserId(long userId);

	@Delete("delete from ForgetPasswordVerifyCode where  userId = ${hashtag}{userId}")
	public void deleteByUserId(long userId);

	@Delete("delete from ForgetPasswordVerifyCode where  expiresAt < ${hashtag}{timestamp}")
	public int deleteCodesExpiresBefore(Timestamp timestamp);

}
