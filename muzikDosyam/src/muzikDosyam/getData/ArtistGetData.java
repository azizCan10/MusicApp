package muzikDosyam.getData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import muzikDosyam.db.Artist;
import muzikDosyam.db.DbHelper;

public class ArtistGetData {
	
	public ArrayList<Artist> getData() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<Artist> artists = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from artist");
			artists = new ArrayList<Artist>();
			
			while (resultSet.next()) {
				artists.add(new Artist(
						resultSet.getInt("artist_id"),
						resultSet.getString("artist_name"), 
						resultSet.getString("artist_country")
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
		return artists;
	}
}
