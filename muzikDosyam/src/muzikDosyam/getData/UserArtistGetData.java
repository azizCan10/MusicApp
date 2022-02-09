package muzikDosyam.getData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import muzikDosyam.db.DbHelper;
import muzikDosyam.db.UserArtist;
import muzikDosyam.ui.FrmLogin;

public class UserArtistGetData {
	public ArrayList<UserArtist> getData() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<UserArtist> userArtists = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from user_artist where user_id = " + FrmLogin.userId + "");
			userArtists = new ArrayList<UserArtist>();
			
			while (resultSet.next()) {
				userArtists.add(new UserArtist(
						resultSet.getInt("artist_id"),
						resultSet.getInt("user_id")
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
		return userArtists;
	}
}
