package CSCI5308.GroupFormationTool;

import CSCI5308.GroupFormationTool.Security.*;
import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.Database.*;
import CSCI5308.GroupFormationTool.Courses.*;
import CSCI5308.GroupFormationTool.QuestionManager.*;
import CSCI5308.GroupFormationTool.EditCourseSurvey.*;

public class SystemConfig {
	private static SystemConfig uniqueInstance = null;

	private IPasswordEncryption passwordEncryption;
	private IUserPersistence userDb;
	private IDatabaseConfiguration databaseConfiguration;
	private ICoursePersistence courseDb;
	private ICourseUserRelationshipPersistence courseUserRelationshipDb;
	private IUserNotifications userNotifications;
	private IQuestionDb questionDb;
	private IEditCourseSurveyDb surveyDb;

	private SystemConfig() {

		passwordEncryption = new BCryptPasswordEncryption();
		userDb = new UserDb();
		databaseConfiguration = new DefaultDatabaseConfiguration();
		courseDb = new CourseDb();
		courseUserRelationshipDb = new CourseUserRelationshipDb();
		userNotifications = new SendNotification();
		questionDb = new QuestionDb();
		surveyDb = new EditCourseSurveyDb();
	}

	public static SystemConfig instance() {

		if (null == uniqueInstance) {
			uniqueInstance = new SystemConfig();
		}
		return uniqueInstance;
	}

	public IPasswordEncryption getPasswordEncryption() {
		return passwordEncryption;
	}

	public void setPasswordEncryption(IPasswordEncryption passwordEncryption) {
		this.passwordEncryption = passwordEncryption;
	}

	public IUserPersistence getUserDb() {
		return userDb;
	}

	public void setUserDb(IUserPersistence userDb) {
		this.userDb = userDb;
	}

	public IDatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	public void setDatabaseConfiguration(IDatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	public void setCourseDb(ICoursePersistence courseDb) {
		this.courseDb = courseDb;
	}

	public ICoursePersistence getCourseDb() {
		return courseDb;
	}

	public void setCourseUserRelationshipDb(ICourseUserRelationshipPersistence courseUserRelationshipDb) {
		this.courseUserRelationshipDb = courseUserRelationshipDb;
	}

	public ICourseUserRelationshipPersistence getCourseUserRelationshipDb() {
		return courseUserRelationshipDb;
	}

	public IUserNotifications getUserNotifications() {
		return userNotifications;
	}

	public void setUserNotifications(IUserNotifications userNotifications) {
		this.userNotifications = userNotifications;
	}

	public IQuestionDb getQuestionDb() {
		return questionDb;
	}

	public void setQuestionDb(IQuestionDb questionDb) {
		this.questionDb = questionDb;
	}

	public IEditCourseSurveyDb getSurveyDb() {
		return surveyDb;
	}

	public void setSurveyDb(IEditCourseSurveyDb surveyDb) {
		this.surveyDb = surveyDb;
	}
}
