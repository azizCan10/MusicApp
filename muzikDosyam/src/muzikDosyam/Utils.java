package muzikDosyam;

import java.awt.Cursor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;

import muzikDosyam.db.DbHelper;

public class Utils {
	private static Cursor imlec;
	
	public static void setHandCursor(JButton button) {		//imleci el haline getirme
		imlec = new Cursor(Cursor.HAND_CURSOR);
		button.setCursor(imlec);
	}
	
	public static void setHandCursor(JLabel label) {		//imleci el haline getirme
		imlec = new Cursor(Cursor.HAND_CURSOR);
		label.setCursor(imlec);
	}
}
