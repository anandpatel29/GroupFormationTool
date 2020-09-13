package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

public interface ICoursePersistence {
	public List<ICourse> loadAllCourses();

	public void loadCourseById(long id, ICourse course);

	public boolean createCourse(ICourse course);

	public boolean deleteCourse(long id);

	public void loadCourseByTitle(String courseTitle, ICourse course);
}
