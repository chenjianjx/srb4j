#set($hashtag = '#')
package ${package}.impl.biz.staff;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author chenjianjx@gmail.com
 */


@Repository
public interface StaffUserRepo {

    @Insert("insert into StaffUser(username, password, lastLoginDate, createdBy) "
            + "values (${hashtag}{username}, ${hashtag}{password}, ${hashtag}{lastLoginDate}, ${hashtag}{createdBy})")
    @SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", keyColumn = "id", before = false, resultType = long.class)
    public long saveNewStaffUser(StaffUser staffUser);

    @Select("select * from StaffUser where  username = ${hashtag}{username}")
    public StaffUser getStaffUserByUsername(String username);

    @Select("select * from StaffUser where  id = ${hashtag}{id}")
    public StaffUser getStaffUserById(long id);

    @Update("update StaffUser set password = ${hashtag}{password}, lastLoginDate = ${hashtag}{lastLoginDate}, updatedBy = ${hashtag}{updatedBy} where id = ${hashtag}{id}")
    public void updateStaffUser(StaffUser newStaffUser);

}
