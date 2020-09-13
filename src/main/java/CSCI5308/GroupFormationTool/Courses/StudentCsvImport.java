package CSCI5308.GroupFormationTool.Courses;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import CSCI5308.GroupFormationTool.*;
import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.Security.*;

public class StudentCsvImport implements IStudentCsvImport {
	private Logger logger = Logger.getLogger(this.getClass());
	private List<String> successResults;
	private List<String> failureResults;
	private ICourse course;
	private MultipartFile uploadedFile;
	private IUserPersistence userDb;
	private IPasswordEncryption passwordEncryption;
	private IUserNotifications notifyUser;

	public StudentCsvImport(ICourse course, MultipartFile file) {
		this.course = course;
		this.uploadedFile = file;
		successResults = new ArrayList<String>();
		failureResults = new ArrayList<String>();
		userDb = SystemConfig.instance().getUserDb();
		passwordEncryption = SystemConfig.instance().getPasswordEncryption();
		notifyUser = SystemConfig.instance().getUserNotifications();
	}

	public void parseCsvFile() {

		try {
			Reader reader = new InputStreamReader(uploadedFile.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> records = csvReader.readAll();
			Iterator<String[]> iter = records.iterator();
			while (iter.hasNext()) {
				String[] record = iter.next();
				enrollStudentFromRecord(record);
			}
		} catch (IOException e) {
			String errorMessage = String.format("Failure reading uploaded file: %s", e.getMessage());
			failureResults.add(errorMessage);
			logger.error(errorMessage);
		} catch (Exception e) {
			String errorMessage = String.format("Failure parsing CSV file: %s", e.getMessage());
			failureResults.add(errorMessage);
			logger.error(errorMessage);
		}
	}

	private void enrollStudentFromRecord(String[] record) {
		if (record.length != 4) {
			String errorMessage = "Faulty row: " + Arrays.toString(record);
			failureResults.add(errorMessage);
			logger.error(errorMessage);
			return;
		}
		String bannerId = record[0];
		String firstName = record[1];
		String lastName = record[2];
		String email = record[3];
		IUser u = new User();
		userDb.loadUserByBannerId(bannerId, u);

		if (!u.isValidUser()) {
			u.setBannerId(bannerId);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);
			if (u.createUser(userDb, passwordEncryption, null)) {
				String s1 = UUID.randomUUID().toString();
				String rawPassword = s1.substring(0, 20);
				notifyUser.sendUserLoginCredentials(u, rawPassword);
				String successMessage = "Created: " + Arrays.toString(record);
				successResults.add(successMessage);
				logger.info(successMessage);
			} else {
				String errorMessage = "Unable to save this user to DB: " + Arrays.toString(record);
				failureResults.add(errorMessage);
				logger.error(errorMessage);
				return;
			}
		}
		if (course.enrollUserInCourse(Role.STUDENT, u)) {
			String errorMessage = "Unable to enroll user in course: " + Arrays.toString(record);
			failureResults.add(errorMessage);
			logger.error(errorMessage);
		}
	}

	public List<String> getSuccessResults() {
		return successResults;
	}

	public List<String> getFailureResults() {
		return failureResults;
	}
}
