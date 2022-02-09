package muzikDosyam.getData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import muzikDosyam.db.DbHelper;
import muzikDosyam.db.MusicCalmaListesi;
import muzikDosyam.ui.FrmLogin;
import muzikDosyam.ui.user.FrmUserKitaplik;

public class MusicCalmaListesiGetData {
	public ArrayList<MusicCalmaListesi> getData() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<MusicCalmaListesi> musicCalmaListesis = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from music_calma_listesi");
			musicCalmaListesis = new ArrayList<MusicCalmaListesi>();
			// where calma_listesi_id = " + Integer.valueOf(FrmUserKitaplik.txtCalmaListesi.getText()) + "
			while (resultSet.next()) {
				musicCalmaListesis.add(new MusicCalmaListesi(
						resultSet.getInt("music_id"),
						resultSet.getInt("calma_listesi_id")
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
		return musicCalmaListesis;
	}
}
