package muzikDosyam.db;

public class User extends Base {
	private String email;
	private String password;
	private boolean is_premium;
	private String country;
	
	public User(int id, String name, String email, String password, boolean is_premium, String country) {
		super(id, name);
		this.email = email;
		this.password = password;
		this.is_premium = is_premium;
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIs_premium() {
		return is_premium;
	}

	public void setIs_premium(boolean is_premium) {
		this.is_premium = is_premium;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}