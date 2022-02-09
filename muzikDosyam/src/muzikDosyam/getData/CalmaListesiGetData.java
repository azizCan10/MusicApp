package muzikDosyam.getData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import muzikDosyam.db.CalmaListesi;
import muzikDosyam.db.DbHelper;
import muzikDosyam.ui.FrmLogin;

public class CalmaListesiGetData {
	
	public ArrayList<CalmaListesi> getData() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<CalmaListesi> calmaListesis = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select calma_listesi_id, calma_listesi_name, calma_listesi.user_id from calma_listesi, user where calma_listesi.user_id = user.user_id and user.is_premium = true");
			calmaListesis = new ArrayList<CalmaListesi>();
			
			while (resultSet.next()) {
				calmaListesis.add(new CalmaListesi(
						resultSet.getInt("calma_listesi_id"),
						resultSet.getString("calma_listesi_name"), 
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
		return calmaListesis;
	}
	
	public ArrayList<CalmaListesi> getData2() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<CalmaListesi> calmaListesii = null;
		
		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from calma_listesi where calma_listesi.user_id = " + FrmLogin.userId + "");
			calmaListesii = new ArrayList<CalmaListesi>();
			
			while (resultSet.next()) {
				calmaListesii.add(new CalmaListesi(
						resultSet.getInt("calma_listesi_id"),
						resultSet.getString("calma_listesi_name"), 
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
		return calmaListesii;
	}
}
