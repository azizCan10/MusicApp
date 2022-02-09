package muzikDosyam.db;

public class MusicCalmaListesi {
	private int music_id;
	private int calma_listesi_id;
	
	public MusicCalmaListesi(int music_id, int calma_listesi_id) {
		this.music_id = music_id;
		this.calma_listesi_id = calma_listesi_id;
	}

	public int getMusic_id() {
		return music_id;
	}

	public void setMusic_id(int music_id) {
		this.music_id = music_id;
	}

	public int getCalma_listesi_id() {
		return calma_listesi_id;
	}

	public void setCalma_listesi_id(int calma_listesi_id) {
		this.calma_listesi_id = calma_listesi_id;
	}
}
