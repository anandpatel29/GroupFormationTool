package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

import CSCI5308.GroupFormationTool.AccessControl.IUser;

public interface ICourse {
	public void setId(long id);

	public long getId();

	public void setTitle(String title);

	public String getTitle();

	public List<String> getFailureResults();

	public boolean delete(ICoursePersistence courseDb);

	public boolean createCourse(ICoursePersistence courseDb);

	public boolean enrollUserInCourse(Role role, IUser user);

	public boolean isCurrentUserEnrolledAsRoleInCourse(Role role);

	public boolean isCurrentUserEnrolledAsRoleInCourse(String role);

	public List<Role> getAllRolesForCurrentUserInCourse();
}
