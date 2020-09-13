package CSCI5308.GroupFormationTool.AccessControlTest;

import java.util.ArrayList;

import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.Courses.Role;

public class UserDbMock implements IUserPersistence {
	public void loadUserById(long id, IUser user) {
		user.setBannerId("B00000000");
		user.setEmail("rhawkey@dal.ca");
		user.setFirstName("Rob");
		user.setLastName("Hawkey");
		user.setId(id);
	}

	public void loadUserByBannerId(String bannerId, IUser user) {
		user.setBannerId(bannerId);
		user.setEmail("rhawkey@dal.ca");
		user.setFirstName("Rob");
		user.setLastName("Hawkey");
		user.setId(1);
	}

	public boolean createUser(IUser user) {
		// Not implemented yet, hopefully have time to improve in M2
		return true;
	}

	public boolean updateUser(IUser user) {
		// Not implemented yet, hopefully have time to improve in M2
		return true;
	}

	public void loadUserRolesFromBannerId(IUser user, ArrayList<Role> roles) {

	}
}
