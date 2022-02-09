package muzikDosyam.getData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import muzikDosyam.db.DbHelper;
import muzikDosyam.db.Music;

public class MusicGetData {

	public ArrayList<Music> getData() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<Music> musics = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from music");
			musics = new ArrayList<Music>();
			
			while (resultSet.next()) {
				musics.add(new Music(
						resultSet.getInt("music_id"),
						resultSet.getString("music_name"), 
						resultSet.getString("date"),
						resultSet.getDouble("time"),
						resultSet.getInt("dinlenme_sayisi"),
						resultSet.getString("genre"),
						resultSet.getInt("artist_id"),
						resultSet.getInt("album_id")
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
		return musics;
	}
}
