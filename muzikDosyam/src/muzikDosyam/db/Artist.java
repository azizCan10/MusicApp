package muzikDosyam.db;

public class Artist extends Base {
	private String artist_country;

	public Artist(int id, String name, String artist_country) {
		super(id, name);
		this.artist_country = artist_country;
	}

	public String getArtist_country() {
		return artist_country;
	}

	public void setArtist_country(String artist_country) {
		this.artist_country = artist_country;
	}
}