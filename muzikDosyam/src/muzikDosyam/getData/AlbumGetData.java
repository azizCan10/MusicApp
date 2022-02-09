package muzikDosyam.getData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import muzikDosyam.db.Album;
import muzikDosyam.db.DbHelper;

public class AlbumGetData {
	
	public ArrayList<Album> getData() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<Album> albums = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from album");
			albums = new ArrayList<Album>();
			
			while (resultSet.next()) {
				albums.add(new Album(
						resultSet.getInt("album_id"),
						resultSet.getString("album_name"), 
						resultSet.getInt("artist_id"), 
						resultSet.getString("date"), 
						resultSet.getString("genre")
				));
			}
		} 
		catch (SQLException e) 	{
			dbHelper.showErrorMessage(e);
		}
		finally {
			statement.close();
			connection.close();
		}
		return albums;
	}
}
