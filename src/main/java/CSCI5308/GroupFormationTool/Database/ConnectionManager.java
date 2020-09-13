package CSCI5308.GroupFormationTool.Database;

import CSCI5308.GroupFormationTool.SystemConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static ConnectionManager uniqueInstance = null;

	private String dbUrl;
	private String dbUserName;
	private String dbPassword;

	private ConnectionManager() {
		IDatabaseConfiguration config = SystemConfig.instance().getDatabaseConfiguration();
		dbUrl = config.getDatabaseUrl();
		dbUserName = config.getDatabaseUserName();
		dbPassword = config.getDatabasePassword();
	}

	public static ConnectionManager instance() {
		if (null == uniqueInstance) {
			uniqueInstance = new ConnectionManager();
		}
		return uniqueInstance;
	}

	public Connection getDBConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
	}
}
