package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDb implements ICoursePersistence {
	private Logger logger = Logger.getLogger(this.getClass());

	public List<ICourse> loadAllCourses() {
		List<ICourse> courses = new ArrayList<ICourse>();
		CallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spLoadAllCourses()");
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long id = results.getLong(1);
					String title = results.getString(2);
					ICourse c = new Course();
					c.setId(id);
					c.setTitle(title);
					courses.add(c);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception genericException) {
			logger.error(genericException);
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return courses;
	}

	public void loadCourseById(long id, ICourse course) {
		CallStoredProcedure proc = null;
		try {
			long courseId = -1;
			String title = null;
			proc = new CallStoredProcedure("spFindCourseByID(?)");
			proc.setParameter(1, id);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					courseId = results.getLong(1);
					title = results.getString(2);
					course.setId(courseId);
					course.setTitle(title);
				}
			} else {
				course.setId(courseId);
			}
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception genericException) {
			logger.error(genericException);
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
	}

	public boolean createCourse(ICourse course) {
		CallStoredProcedure proc = null;
		try {
			loadCourseByTitle(course.getTitle(), course);
			if (course.getId() > -1) {
				return false;
			} else {
				proc = new CallStoredProcedure("spCreateCourse(?, ?)");
				proc.setParameter(1, course.getTitle());
				proc.registerOutputParameterLong(2);
				proc.execute();
			}
		} catch (SQLException e) {
			logger.error(e);
			return false;
		} catch (Exception genericException) {
			logger.error(genericException);
			return false;
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;
	}

	public boolean deleteCourse(long id) {
		CallStoredProcedure proc = null;
		try {
			ICourse course = new Course();
			loadCourseById(id, course);
			if (course.getId() == -1) {
				return false;
			} else {
				proc = new CallStoredProcedure("spDeleteCourse(?)");
				proc.setParameter(1, id);
				proc.execute();
			}
		} catch (SQLException e) {
			logger.error(e);
			return false;
		} catch (Exception genericException) {
			logger.error(genericException);
			return false;
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;
	}

	@Override
	public void loadCourseByTitle(String courseTitle, ICourse course) {
		CallStoredProcedure proc = null;
		try {
			long courseId = -1;
			String title = null;
			proc = new CallStoredProcedure("spFindCourseByTitle(?)");
			proc.setParameter(1, courseTitle);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					courseId = results.getLong(1);
					title = results.getString(2);
					course.setId(courseId);
					course.setTitle(title);
				}
			} else {
				course.setId(courseId);
			}
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception genericException) {
			logger.error(genericException);
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
	}
}
