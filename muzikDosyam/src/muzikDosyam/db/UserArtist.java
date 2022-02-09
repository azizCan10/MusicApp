package muzikDosyam.db;

public class UserArtist {
	private int artist_id;
	private int user_id;
	
	public UserArtist(int artist_id, int user_id) {
		this.artist_id = artist_id;
		this.user_id = user_id;
	}

	public int getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
