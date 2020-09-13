package CSCI5308.GroupFormationTool.Courses;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@SuppressWarnings("deprecation")
class CourseDbTest {

	private static long id;
	private static String title;
	private static ICourse course;
	static ICoursePersistence courseDbMock;
	static List<ICourse> courses = new ArrayList<ICourse>();

	@BeforeAll
	static void setUp() {
		courseDbMock = new CourseDbMock();
		courses = courseDbMock.loadAllCourses();
		course = new Course();
		id = 1;
		title = "Data";
		course.setId(id);
		course.setTitle(title);
	}

	@Test
	public void testLoadAllCourses() {

		Assert.isTrue(!courses.isEmpty());
	}

	@Test
	public void testLoadCourseById() {
		Course courseRandom = new Course();
		courseDbMock.loadCourseById(id, courseRandom);
		Assert.isTrue(courseRandom.getId() == id);
	}

	@Test
	public void testCreateCourse() {

		Assert.isTrue(courseDbMock.createCourse(course));
	}

	@Test
	public void testDeleteCourse() {
		boolean success = courseDbMock.deleteCourse(id);
		Assert.isTrue(success);
	}
}