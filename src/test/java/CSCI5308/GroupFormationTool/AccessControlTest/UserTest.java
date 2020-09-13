package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@SuppressWarnings("deprecation")
public class UserTest {
	@Test
	public void ConstructorTests() {
		IUser u = new User();
		Assert.isTrue(u.getBannerId().isEmpty());
		Assert.isTrue(u.getFirstName().isEmpty());
		Assert.isTrue(u.getLastName().isEmpty());
		Assert.isTrue(u.getEmail().isEmpty());

		IUserPersistence userDBMock = new UserDbMock();
		u = new User(1, userDBMock);
		Assert.isTrue(u.getBannerId().equals("B00000000"));

		u = new User("B00000000", userDBMock);
		Assert.isTrue(u.getBannerId().equals("B00000000"));
	}

	@Test
	public void setIDTest() {
		IUser u = new User();
		u.setId(10);
		Assert.isTrue(10 == u.getId());
	}

	@Test
	public void getIDTest() {
		IUser u = new User();
		u.setId(10);
		Assert.isTrue(10 == u.getId());
	}

	@Test
	public void setBannerIDTest() {
		IUser u = new User();
		u.setBannerId("B00000000");
		Assert.isTrue(u.getBannerId().equals("B00000000"));
	}

	@Test
	public void getBannerIDTest() {
		IUser u = new User();
		u.setBannerId("B00000000");
		Assert.isTrue(u.getBannerId().equals("B00000000"));
	}

	@Test
	public void setFirstNameTest() {
		IUser u = new User();
		u.setFirstName("Rob");
		Assert.isTrue(u.getFirstName().equals("Rob"));
	}

	@Test
	public void getFirstNameTest() {
		IUser u = new User();
		u.setFirstName("Rob");
		Assert.isTrue(u.getFirstName().equals("Rob"));
	}

	@Test
	public void setLastNameTest() {
		User u = new User();
		u.setLastName("Hawkey");
		Assert.isTrue(u.getLastName().equals("Hawkey"));
	}

	@Test
	public void getLastNameTest() {
		IUser u = new User();
		u.setLastName("Hawkey");
		Assert.isTrue(u.getLastName().equals("Hawkey"));
	}

	@Test
	public void setEmailTest() {
		IUser u = new User();
		u.setEmail("rhawkey@dal.ca");
		Assert.isTrue(u.getEmail().equals("rhawkey@dal.ca"));
	}

	@Test
	public void getEmailTest() {
		User u = new User();
		u.setEmail("rhawkey@dal.ca");
		Assert.isTrue(u.getEmail().equals("rhawkey@dal.ca"));
	}

	@Test
	public void isBannerIDValidTest() {
		Assert.isTrue(User.isBannerIDValid("B000000000"));
		Assert.isTrue(!User.isBannerIDValid(null));
		Assert.isTrue(!User.isBannerIDValid(""));
	}

	@Test
	public void isFirstNameValidTest() {
		Assert.isTrue(User.isFirstNameValid("rob"));
		Assert.isTrue(!User.isFirstNameValid(null));
		Assert.isTrue(!User.isFirstNameValid(""));
	}

	@Test
	public void isLastNameValidTest() {
		Assert.isTrue(User.isLastNameValid("hawkey"));
		Assert.isTrue(!User.isLastNameValid(null));
		Assert.isTrue(!User.isLastNameValid(""));
	}

	@Test
	public void isEmailValidTest() {
		Assert.isTrue(User.isEmailValid("rhawkey@dal.ca"));
		Assert.isTrue(!User.isEmailValid(null));
		Assert.isTrue(!User.isEmailValid(""));
		Assert.isTrue(!User.isEmailValid("@dal.ca"));
		Assert.isTrue(!User.isEmailValid("rhawkey@"));
	}

}
