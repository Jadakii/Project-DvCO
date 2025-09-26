package model;

public class User {
	 private String UserID;
	 private String Username;
	 private String Email;
	 private String Password;
	 private int Age;
	 private String Gender;
	 private String Country;
	 private String PhoneNumber;
	 private String Role;
	 
	 
	public User(String userID, String username, String email, String password, int age, String gender, String country,
			String phoneNumber, String role) {
		super();
		UserID = userID;
		Username = username;
		Email = email;
		Password = password;
		Age = age;
		Gender = gender;
		Country = country;
		PhoneNumber = phoneNumber;
		Role = role;
	}


	public String getUserID() {
		return UserID;
	}


	public void setUserID(String userID) {
		UserID = userID;
	}


	public String getUsername() {
		return Username;
	}


	public void setUsername(String username) {
		Username = username;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public int getAge() {
		return Age;
	}


	public void setAge(int age) {
		Age = age;
	}


	public String getGender() {
		return Gender;
	}


	public void setGender(String gender) {
		Gender = gender;
	}


	public String getCountry() {
		return Country;
	}


	public void setCountry(String country) {
		Country = country;
	}


	public String getPhoneNumber() {
		return PhoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}


	public String getRole() {
		return Role;
	}


	public void setRole(String role) {
		Role = role;
	}


	public User() {
		// TODO Auto-generated constructor stub
	}

}
