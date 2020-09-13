package CSCI5308.GroupFormationTool.AccessControl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;

public interface IUser {

	public long getId();

	public void setId(long id);

	public String getPassword();

	public void setPassword(String password);

	public String getBannerId();

	public void setBannerId(String bannerId);

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);

	public String getEmail();

	public void setEmail(String email);

	public boolean isValidUser();

	public boolean createUser(IUserPersistence userDb, IPasswordEncryption passwordEncryption,
			IUserNotifications notification);

	public static boolean isStringNullOrEmpty(String s) {
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
		final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		if (isStringNullOrEmpty(email)) {
			return false;
		}

		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	public List<String> getFailureResults();

	public void setFailureResults(List<String> failureResults);

}
