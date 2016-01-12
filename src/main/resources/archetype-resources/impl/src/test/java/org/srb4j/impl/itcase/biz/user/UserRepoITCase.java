package ${groupId}.impl.itcase.biz.user;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import ${groupId}.impl.biz.user.User;
import ${groupId}.impl.biz.user.UserRepo;
import ${groupId}.impl.itcase.support.MySpringJunit4ClassRunner;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(MySpringJunit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class UserRepoITCase {

	@Resource
	UserRepo repo;

	@Test
	public void crudTest() throws Exception {
		User user = new User();
		user.setSource(User.SOURCE_LOCAL);
		user.setPassword("sdafhksldafds728926347");		
		user.setEmail(System.currentTimeMillis() + "@temp.com");		
		user.setCreatedBy("someone");		
	 
		
		//save
		repo.saveNewUser(user);
		Assert.assertNotNull(user.getId());
		Assert.assertNotNull(user.getCreatedAt());
		System.out.println(user);
		

		long userId = user.getId();
		String principal = user.getPrincipal();
		
		
		//query		
		user = repo.getUserById(userId);
		Assert.assertNotNull(user);
		
		user = repo.getUserByPrincipal(principal);
		Assert.assertNotNull(user);
		
		//update
		user.setPassword("newPassword");
		user.setUpdatedBy("newUpdatedBy");		
		repo.updateUser(user);
		Assert.assertNotNull(user.getUpdatedAt());
		user = repo.getUserById(userId);
		Assert.assertEquals("newPassword", user.getPassword());
		Assert.assertEquals("newUpdatedBy", user.getUpdatedBy());

	}
	
	
	 


}
