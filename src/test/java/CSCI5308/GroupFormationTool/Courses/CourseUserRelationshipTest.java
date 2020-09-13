package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@SuppressWarnings("deprecation")
class CourseUserRelationshipTest {

	static ICourseUserRelationshipPersistence dbMock;
	static ICourse course1, course2, course3, course4;
	static IUser user1, guest, admin;

	@BeforeAll
	static void SetUp() {
		dbMock = new CourseUserRelationshipDbMock();
		user1 = new User();
		user1.setId(4);
		user1.setBannerId("B00001");

		admin = new User();
		admin.setBannerId("B00002");
		admin.setId(1);

		guest = new User();
		guest.setId(2);
		guest.setBannerId("B00003");

		course1 = new Course();
		course1.setId(5408L);

		course2 = new Course();
		course2.setId(5409L);

		course3 = new Course();
		course3.setId(5417L);

		course4 = new Course();
		course4.setId(5418L);
	}

	@AfterAll
	static void TearDown() {
		dbMock = null;
		course1 = course2 = course3 = course4 = null;
		user1 = guest = admin = null;
	}

	@Test
	void userHasRoleInCourse() {
		Assert.isTrue(!dbMock.loadUserRolesForCourse(course1, guest).isEmpty());
	}

	@Test
	void loadAllRoluesForUserInCourse() {
		Assert.isTrue(!dbMock.loadUserRolesForCourse(course2, user1).isEmpty());
	}

	@Test
	void enrollUserInCourse() {
		Assert.isTrue(dbMock.enrollUser(course4, user1, Role.GUEST));
	}
}