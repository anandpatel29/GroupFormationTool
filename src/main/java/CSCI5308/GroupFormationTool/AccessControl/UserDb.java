package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.Courses.Role;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.Database.ICallStoredProcedure;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDb implements IUserPersistence {
	private Logger logger = Logger.getLogger(this.getClass());

	public void loadUserById(long id, IUser user) {
		ICallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spLoadUser(?)");
			proc.setParameter(1, id);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long userId = results.getLong(1);
					String bannerId = results.getString(2);
					String password = results.getString(3);
					String firstName = results.getString(4);
					String lastName = results.getString(5);
					String email = results.getString(6);
					user.setId(userId);
					user.setBannerId(bannerId);
					user.setPassword(password);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
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
	}

	public void loadUserByBannerId(String bannerId, IUser user) {
		ICallStoredProcedure proc = null;
		long userId = -1;
		try {
			proc = new CallStoredProcedure("spFindUserByBannerID(?)");
			proc.setParameter(1, bannerId);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					userId = results.getLong(1);
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
		if (userId > -1) {
			loadUserById(userId, user);
		}
	}

	public boolean createUser(IUser user) {
		ICallStoredProcedure proc = null;

		try {
			loadUserByBannerId(user.getBannerId(), user);
			if (user.getId() > -1) {
				return false;
			} else {
				proc = new CallStoredProcedure("spCreateUser(?, ?, ?, ?, ?, ?)");
				proc.setParameter(1, user.getBannerId());
				proc.setParameter(2, user.getPassword());
				proc.setParameter(3, user.getFirstName());
				proc.setParameter(4, user.getLastName());
				proc.setParameter(5, user.getEmail());
				proc.registerOutputParameterLong(6);
				proc.execute();
			}
		} catch (SQLException sqlException) {
			String errorMessage = String.format("Sql exception occurred: %s", sqlException.getMessage());
			logger.error(errorMessage);
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

	public void loadUserRolesFromBannerId(IUser user, ArrayList<Role> roles) {
		ICallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spLoadAllUserRolesFromBannerId(?)");
			proc.setParameter(1, user.getBannerId());
			proc.registerOutputParameterLong(6);
			ResultSet rs = proc.executeWithResults();
			while (rs.next()) {
				if (Integer.parseInt(rs.getString(1)) == 2) {
					roles.add(Role.GUEST);
				} else if (Integer.parseInt(rs.getString(1)) == 3) {
					roles.add(Role.STUDENT);
				} else if (Integer.parseInt(rs.getString(1)) == 4) {
					roles.add(Role.INSTRUCTOR);
				} else if (Integer.parseInt(rs.getString(1)) == 2) {
					roles.add(Role.TA);
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

	}

}
