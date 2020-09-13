package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.ErrorHandling.ErrorMessage;
import CSCI5308.GroupFormationTool.ErrorHandling.UserException;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements IUser {
	private Logger logger = Logger.getLogger(this.getClass());
	private List<String> failureResults = new ArrayList<>();
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	private static final long ID = -1;
	private long id;
	private String password;
	private String bannerId;
	private String firstName;
	private String lastName;
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBannerId() {
		return bannerId;
	}

	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User() {
		setDefaults();
	}

	public List<String> getFailureResults() {
		return failureResults;
	}

	public void setFailureResults(List<String> failureResults) {
		this.failureResults = failureResults;
	}

	public User(long id, IUserPersistence persistence) {
		setDefaults();
		persistence.loadUserById(id, this);
	}

	public User(String bannerId, IUserPersistence persistence) {
		setDefaults();
		persistence.loadUserByBannerId(bannerId, this);
	}

	private void setDefaults() {
		id = ID;
		password = "";
		bannerId = "";
		firstName = "";
		lastName = "";
		email = "";
	}

	public boolean isValidUser() {
		return id != -1;
	}

	public boolean createUser(IUserPersistence userDb, IPasswordEncryption passwordEncryption,
			IUserNotifications notification) {
		boolean success = false;
		String rawPassword = password;
		this.password = passwordEncryption.encryptPassword(this.password);
		try {
			success = userDb.createUser(this);
			if (success && (null != notification)) {
				notification.sendUserLoginCredentials(this, rawPassword);
			} else {
				boolean isUserExists = true;
				userDb.loadUserByBannerId(this.getBannerId(), this);
				isUserExists = (this.getId() > -1);
				if (isUserExists) {
					String errorMessage = String.format(ErrorMessage.USER_ALREADY_EXISTS_ERROR, this.getBannerId());
					failureResults.add(errorMessage);
					throw new UserException(errorMessage);
				}
			}
		} catch (UserException duplicateUserException) {
			logger.error(duplicateUserException);
			return success;
		} catch (Exception genericException) {
			logger.error(genericException);
			return success;
		}
		return success;
	}

	private static boolean isStringNullOrEmpty(String s) {
		if (null == s) {
			return true;
		}
		return s.isEmpty();
	}

	public static boolean isBannerIDValid(String bannerId) {
		return !isStringNullOrEmpty(bannerId);
	}

	public static boolean isFirstNameValid(String name) {
		return !isStringNullOrEmpty(name);
	}

	public static boolean isLastNameValid(String name) {
		return !isStringNullOrEmpty(name);
	}

	public static boolean isEmailValid(String email) {
		if (isStringNullOrEmpty(email)) {
			return false;
		}

		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}