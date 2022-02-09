package muzikDosyam.db;

public class CalmaListesi extends Base {
	private int user_id;

	public CalmaListesi(int id, String name, int user_id) {
		super(id, name);
		this.user_id = user_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
