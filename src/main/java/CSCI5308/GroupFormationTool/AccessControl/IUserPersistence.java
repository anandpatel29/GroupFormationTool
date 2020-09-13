package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.Courses.Role;

import java.util.ArrayList;

public interface IUserPersistence {
	public void loadUserById(long id, IUser user);

	public void loadUserByBannerId(String bannerid, IUser user);

	public boolean createUser(IUser user);

	public void loadUserRolesFromBannerId(IUser user, ArrayList<Role> roles);
}
