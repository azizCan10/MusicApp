package muzikDosyam.getData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import muzikDosyam.db.DbHelper;
import muzikDosyam.db.User;

public class UserGetData {

	public ArrayList<User> getData() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<User> users = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from user");
			users = new ArrayList<User>();
			
			while (resultSet.next()) {
				users.add(new User(
						resultSet.getInt("user_id"),
						resultSet.getString("username"), 
						resultSet.getString("email"),
						resultSet.getString("password"),
						resultSet.getBoolean("is_premium"),
						resultSet.getString("country")
				)); 
			}
		} 
		catch (SQLException e) {
			dbHelper.showErrorMessage(e);
		}
		finally {
			statement.close();
			connection.close();
		}
		return users;
	}
}
