package CSCI5308.GroupFormationTool.AccessControl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import CSCI5308.GroupFormationTool.SystemConfig;

public class CurrentUser {
	private static CurrentUser uniqueInstance = null;

	private CurrentUser() {

	}

	public static CurrentUser instance() {
		if (null == uniqueInstance) {
			uniqueInstance = new CurrentUser();
		}
		return uniqueInstance;
	}

	public IUser getCurrentAuthenticatedUser() {
		IUserPersistence userDb = SystemConfig.instance().getUserDb();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated()) {
			String bannerID = authentication.getPrincipal().toString();
			IUser u = new User();
			userDb.loadUserByBannerId(bannerID, u);
			if (u.isValidUser()) {
				return u;
			}
		}
		return null;
	}
}
