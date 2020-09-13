package CSCI5308.GroupFormationTool.Database;

public class DefaultDatabaseConfiguration implements IDatabaseConfiguration {
	private static final String URL = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_4_TEST?useSSL=false";
	private static final String USER = "CSCI5308_4_TEST_USER";
	private static final String PASSWORD = "CSCI5308_4_TEST_4395";

	public String getDatabaseUserName() {
		return USER;
	}

	public String getDatabasePassword() {
		return PASSWORD;
	}

	public String getDatabaseUrl() {
		return URL;
	}
}
