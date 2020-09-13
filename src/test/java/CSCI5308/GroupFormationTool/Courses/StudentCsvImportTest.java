package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.AccessControlTest.UserDbMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@SuppressWarnings("deprecation")
class StudentCsvImportTest {

	static List<String> lines = new ArrayList<>();
	static IUserPersistence userDbMock;
	static ICoursePersistence courseDbMock;

	@BeforeAll
	static void SetUp() {
		userDbMock = new UserDbMock();
		courseDbMock = new CourseDbMock();
		lines.add("B0034034, Akshay, Singh, akki007.singh@gmail.com");
		lines.add("B0034035, Deepak, Aggarwal, deepak.singh@gmail.com");
		lines.add("B0034036, Rahul, Yadav, rahul.singh@gmail.com");
		lines.add("B0034038, Amit, Shah, amit.singh@gmail.com");
		lines.add("B0038034, Vishnu, Vellampalli, vishnu.singh@gmail.com");
		lines.add("B0096760, Mayank, Bagla, mayank.singh@gmail.com");
		lines.add("B0034756, Anand, Bhadania, anand.singh@gmail.com");
		lines.add("B0034945, Ron, Lee, ron.lee@gmail.com");
		lines.add("B0032763, Shasha, Mcarthy, mc.shasha@gmail.com");
		lines.add("B0034030, Yuan, Ran, tuan.yuan@gmail.com");
		lines.add("B0039034, Long, Ying, lee.007@gmail.com");
	}

	@Test
	void parseCsvFile() {
		for (String line : lines) {
			String[] record = line.split(",");
			Assert.isTrue(enrollStudentFromRecord(record));
		}
	}

	private boolean enrollStudentFromRecord(String[] record) {
		// Banner ID, first name, last name, email
		if (record.length != 4) {
			return false;
		}
		String bannerId = record[0];
		String firstName = record[1];
		String lastName = record[2];
		String email = record[3];
		IUser u = new User();
		userDbMock.loadUserByBannerId(bannerId, u);

		if (!u.isValidUser()) {
			u.setBannerId(bannerId);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);

		}

		return true;
	}
}