package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import java.util.List;

public class CourseUserRelationship implements ICourseUserRelationship {
	public boolean userHasRoleInCourse(IUser user, Role role, ICourse course) {
		if (null == user || !user.isValidUser()) {
			return false;
		}
		if (null == role) {
			return false;
		}
		if (null == course) {
			return false;
		}
		ICourseUserRelationshipPersistence userCourseRelationshipDb = SystemConfig.instance()
				.getCourseUserRelationshipDb();
		List<Role> roles = userCourseRelationshipDb.loadUserRolesForCourse(course, user);
		if (null != roles && roles.contains(role)) {
			return true;
		}
		return false;
	}

	public List<Role> loadAllRoluesForUserInCourse(IUser user, ICourse course) {
		ICourseUserRelationshipPersistence userCourseRelationshipDb = SystemConfig.instance()
				.getCourseUserRelationshipDb();
		List<Role> roles = userCourseRelationshipDb.loadUserRolesForCourse(course, user);
		return roles;
	}

	public boolean enrollUserInCourse(IUser user, ICourse course, Role role) {
		ICourseUserRelationshipPersistence userCourseRelationshipDb = SystemConfig.instance()
				.getCourseUserRelationshipDb();
		return userCourseRelationshipDb.enrollUser(course, user, role);
	}
}
