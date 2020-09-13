package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CourseDbMock implements ICoursePersistence {

	private List<ICourse> courses = new ArrayList<ICourse>();
	private Map<ICourse, IUser> courseUserMap = new HashMap<>();
	private IUser user1, guest, admin;

	public CourseDbMock() {

		user1 = new User();
		user1.setId(4);
		user1.setBannerId("B00001");

		admin = new User();
		admin.setBannerId("B00002");
		admin.setId(1);

		guest = new User();
		guest.setId(2);
		guest.setBannerId("B00003");

		Course course1 = new Course();
		course1.setId(1);
		course1.setTitle("Data");
		courses.add(course1);

		Course course2 = new Course();
		course2.setId(2);
		course2.setTitle("Mobile");
		courses.add(course2);

		Course course3 = new Course();
		course3.setId(3);
		course3.setTitle("Networking");
		courses.add(course3);

		Course course4 = new Course();
		course4.setId(4);
		course4.setTitle("Software Comprehension");
		courses.add(course4);

		Course course5 = new Course();
		course5.setId(5);
		course5.setTitle("Software Development");
		courses.add(course5);

		courseUserMap.put(course1, user1);
		courseUserMap.put(course1, user1);
		courseUserMap.put(course1, admin);
		courseUserMap.put(course1, guest);
		courseUserMap.put(course1, user1);
	}

	public List<ICourse> loadAllCourses() {
		return courses;
	}

	public void loadCourseById(long id, ICourse course) {
		for (ICourse cs : courses) {
			if (cs.getId() == id) {
				course.setTitle(cs.getTitle());
				course.setId(cs.getId());
			}
		}
	}

	public boolean createCourse(ICourse course) {
		return courses.add(course);
	}

	public boolean deleteCourse(long id) {
		boolean success = false;
		for (ICourse c : courses) {
			if (c.getId() == id) {
				courses.remove(c);
				success = true;
				break;
			}
		}
		return success;
	}

	@Override
	public void loadCourseByTitle(String courseTitle, ICourse course) {
		for (ICourse c : courses) {
			if (c.getTitle().equals(courseTitle)) {
				course.setId(c.getId());
				course.setTitle(c.getTitle());
				break;
			}
		}
	}

}