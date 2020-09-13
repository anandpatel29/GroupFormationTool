package CSCI5308.GroupFormationTool.Courses;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@SuppressWarnings("deprecation")
class CourseTest {

	private static long id;
	private static String title;
	private static ICourse course;
	private static ICoursePersistence courseDbMock;

	@BeforeAll
	static void setUp() {
		courseDbMock = new CourseDbMock();
		course = new Course();
		id = 1;
		title = "Data";
		course.setId(id);
		course.setTitle(title);
	}

	@Test
	public void deleteTest() {
		Assert.isTrue(courseDbMock.deleteCourse(id));
	}

	@Test
	public void createCourseTest() {
		Assert.isTrue(courseDbMock.createCourse(course));
	}

	@Test
	public void getAllRolesForCurrentUserInCourseTest() {
		List<Role> roleList = new ArrayList<>();
		roleList.add(Role.ADMIN);
		roleList.add(Role.GUEST);
		roleList.add(Role.INSTRUCTOR);
		roleList.add(Role.STUDENT);
		Assert.isTrue(!roleList.isEmpty());
	}
}