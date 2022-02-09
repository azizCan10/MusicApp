package muzikDosyam.db;

public class Music extends Base {
	private String date;
	private double time;
	private int dinlenme_sayisi;
	private String genre;
	private int artist_id;
	private int album_id;
	
	public Music(int id, String name, String date, double time, int dinlenme_sayisi, String genre, int artist_id,
			int album_id) {
		super(id, name);
		this.date = date;
		this.time = time;
		this.dinlenme_sayisi = dinlenme_sayisi;
		this.genre = genre;
		this.artist_id = artist_id;
		this.album_id = album_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getDinlenme_sayisi() {
		return dinlenme_sayisi;
	}

	public void setDinlenme_sayisi(int dinlenme_sayisi) {
		this.dinlenme_sayisi = dinlenme_sayisi;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}

	public int getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(int album_id) {
		this.album_id = album_id;
	}
}