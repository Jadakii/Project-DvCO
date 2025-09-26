package Database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSingleton {
	private static DatabaseConnection dbConnection;
	
	
	public DatabaseSingleton() {
		// TODO Auto-generated constructor stub
	}
	
	public static DatabaseConnection getInstance() {
		if(dbConnection == null) {
			dbConnection = new DatabaseConnection();
		} 
		return dbConnection;
	}

}
