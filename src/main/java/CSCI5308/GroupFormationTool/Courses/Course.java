package CSCI5308.GroupFormationTool.Courses;

import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.ErrorHandling.CourseException;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorMessage;
import org.apache.log4j.Logger;

public class Course implements ICourse {
	private Logger logger = Logger.getLogger(this.getClass());
	private long id;
	private String title;
	private ICourseUserRelationship userRoleDecider;
	private List<String> failureResults = new ArrayList<>();

	public Course() {
		setDefaults();
	}

	public Course(long id, ICoursePersistence courseDb) {
		setDefaults();
		courseDb.loadCourseById(id, this);
	}

	private void setDefaults() {
		id = -1;
		title = "";
		userRoleDecider = new CourseUserRelationship();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getFailureResults() {
		return failureResults;
	}

	public void setFailureResults(List<String> failureResults) {
		this.failureResults = failureResults;
	}

	public boolean delete(ICoursePersistence courseDb) {
		boolean isCourseExists = true;
		boolean isCourseDeleted = false;
		try {
			courseDb.loadCourseById(this.getId(), this);
			isCourseExists = this.getId() > -1;
			if (!isCourseExists) {
				String errorMessage = String.format(ErrorMessage.COURSE_NOT_EXISTS_ERROR, this.getTitle());
				failureResults.add(errorMessage);
				throw new CourseException(errorMessage);
			} else {
				isCourseDeleted = courseDb.deleteCourse(id);
			}
		} catch (CourseException duplicateCourseException) {
			logger.error(duplicateCourseException);
			isCourseDeleted = false;
		} catch (Exception genericException) {
			isCourseDeleted = false;
			logger.error(genericException);
		}
		return isCourseDeleted;
	}

	public boolean createCourse(ICoursePersistence courseDb) {
		boolean isCourseExists = true;
		boolean isCourseCreated = false;
		try {
			courseDb.loadCourseByTitle(this.getTitle(), this);
			isCourseExists = this.getId() > -1;
			if (isCourseExists) {
				String errorMessage = String.format(ErrorMessage.COURSE_ALREADY_EXISTS_ERROR, this.getTitle());
				failureResults.add(errorMessage);
				throw new CourseException(errorMessage);
			} else {
				isCourseCreated = courseDb.createCourse(this);
			}
		} catch (CourseException duplicateCourseException) {
			logger.error(duplicateCourseException);
			isCourseCreated = false;
		} catch (Exception genericException) {
			isCourseCreated = false;
			logger.error(genericException);
		}
		return isCourseCreated;
	}

	public boolean enrollUserInCourse(Role role, IUser user) {
		return userRoleDecider.enrollUserInCourse(user, this, role);
	}

	public boolean isCurrentUserEnrolledAsRoleInCourse(Role role) {
		return userRoleDecider.userHasRoleInCourse(CurrentUser.instance().getCurrentAuthenticatedUser(), role, this);
	}

	public boolean isCurrentUserEnrolledAsRoleInCourse(String role) {
		Role r = Role.valueOf(role.toUpperCase());
		return isCurrentUserEnrolledAsRoleInCourse(r);
	}

	public List<Role> getAllRolesForCurrentUserInCourse() {
		return userRoleDecider.loadAllRoluesForUserInCourse(CurrentUser.instance().getCurrentAuthenticatedUser(), this);
	}
}
