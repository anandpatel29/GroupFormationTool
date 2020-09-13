package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorMessage;
import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupController {
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	private final String FIRST_NAME = "firstName";
	private final String LAST_NAME = "lastName";
	private final String EMAIL = "email";

	@GetMapping("/signup")
	public String displaySignup(Model model) {
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView processSignup(@RequestParam(name = USERNAME) String bannerId,
			@RequestParam(name = PASSWORD) String password,
			@RequestParam(name = PASSWORD_CONFIRMATION) String passwordConfirm,
			@RequestParam(name = FIRST_NAME) String firstName, @RequestParam(name = LAST_NAME) String lastName,
			@RequestParam(name = EMAIL) String email) {
		boolean success = false;
		IUserPersistence userDb = null;
		IPasswordEncryption passwordEncryption = null;
		IUser u = null;

		if (User.isBannerIDValid(bannerId) && User.isEmailValid(email) && User.isFirstNameValid(firstName)
				&& User.isLastNameValid(lastName) && password.equals(passwordConfirm)) {
			u = new User();
			u.setBannerId(bannerId);
			u.setPassword(password);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);
			userDb = SystemConfig.instance().getUserDb();
			passwordEncryption = SystemConfig.instance().getPasswordEncryption();
			success = u.createUser(userDb, passwordEncryption, null);
		}
		ModelAndView m;
		if (success) {
			m = new ModelAndView("login");
		} else {
			m = new ModelAndView("signup");
			m.addObject(ErrorMessage.ERROR_LABEL, u.getFailureResults());
		}
		return m;
	}
}