package muzikDosyam.db;

public class Album extends Base {
	private int artist_id;
	private String date;
	private String genre;
	
	public Album(int id, String name, int artist_id, String date, String genre) {
		super(id, name);
		this.artist_id = artist_id;
		this.date = date;
		this.genre = genre;
	}

	public int getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}