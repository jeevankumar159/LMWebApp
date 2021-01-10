package Model;

public class User {
	public String emailid;
	public String password;
	
	public User(String email,String password){
		this.emailid = email;
		this.password = password;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
