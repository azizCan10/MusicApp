package muzikDosyam;

import java.sql.*;
import java.util.ArrayList;

import muzikDosyam.db.Album;
import muzikDosyam.db.Base;
import muzikDosyam.getData.AlbumGetData;
import muzikDosyam.ui.FrmKullaniciSecme;

public class Main {

	public static void main(String[] args) throws SQLException {
		new FrmKullaniciSecme().setVisible(true);
	}
}