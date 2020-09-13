package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseUserRelationshipDb implements ICourseUserRelationshipPersistence {
	private Logger logger = Logger.getLogger(this.getClass());

	public List<IUser> findAllUsersWithoutCourseRole(Role role, long courseId) {
		List<IUser> users = new ArrayList<IUser>();
		CallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spFindUsersWithoutCourseRole(?, ?)");
			proc.setParameter(1, role.toString());
			proc.setParameter(2, courseId);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long userId = results.getLong(1);
					String bannerId = results.getString(2);
					String firstName = results.getString(3);
					String lastName = results.getString(4);
					IUser u = new User();
					u.setId(userId);
					u.setBannerId(bannerId);
					u.setFirstName(firstName);
					u.setLastName(lastName);
					users.add(u);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return users;
	}

	public List<IUser> findAllUsersWithCourseRole(Role role, long courseId) {
		List<IUser> users = new ArrayList<IUser>();
		CallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spFindUsersWithCourseRole(?, ?)");
			proc.setParameter(1, role.toString());
			proc.setParameter(2, courseId);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long userId = results.getLong(1);
					IUser u = new User();
					u.setId(userId);
					users.add(u);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return users;
	}

	public boolean enrollUser(ICourse course, IUser user, Role role) {
		CallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spEnrollUser(?, ?, ?)");
			proc.setParameter(1, course.getId());
			proc.setParameter(2, user.getId());
			proc.setParameter(3, role.toString());
			proc.execute();
		} catch (SQLException e) {
			logger.error(e);
			return false;
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;
	}

	public List<Role> loadUserRolesForCourse(ICourse course, IUser user) {
		List<Role> roles = new ArrayList<Role>();
		CallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spLoadUserRolesForCourse(?, ?)");
			proc.setParameter(1, course.getId());
			proc.setParameter(2, user.getId());
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					Role role = Role.valueOf(results.getString(1).toUpperCase());
					roles.add(role);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return roles;
	}
}
