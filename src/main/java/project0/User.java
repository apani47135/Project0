package project0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class User {
	private static final Logger LOGGER = LogManager.getLogger("com.revature.project0");

	private int userId;
	private String username;
	private String password;
	private String role;
	public User() {
		super();
	}
	public User(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String[] login(Connection c) throws SQLException {
		try {
		PreparedStatement getPassword = c.prepareStatement("select password, role from bankusers where username=?");
		String pass = null;
		String role = null;
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Username: ");
		String username = scan.nextLine();

		System.out.print("\nEnter Password: ");
		String password = scan.nextLine();
		getPassword.setString(1, username);
		ResultSet rs = getPassword.executeQuery();
		while (rs.next()) {
			pass = rs.getString("password");
			role = rs.getString("role");
		}
		rs.close();
		getPassword.close();
		if (password.equals(pass)) {
			System.out.println("Login Successful!\n\n");
			return new String[] { "true", username, role };
		} else
			System.out.println("Login Failed\n\n");
		return new String[] { "false" };
	}catch(Exception e) {
		System.out.println("Input Error.");
		LOGGER.error("Input Error on Main Screen");
		return null;
	}
	}

	public void createAccount(Connection c) throws SQLException {

		Scanner sc = new Scanner(System.in);
		PreparedStatement getUsers = c.prepareStatement("select username from bankusers");
		ResultSet rs = getUsers.executeQuery();
		ArrayList users = new ArrayList();
		while (rs.next()) {
			String u = rs.getString(1);
			users.add(u.toLowerCase());
		}

		System.out.println("Create your account");
		System.out.println("Enter a username: ");
		String user = sc.next();
		String testing = user.toLowerCase();
		while (users.contains(testing.toLowerCase()) == true) {
			System.out.print("Username taken. Try again: ");
			user = sc.next();
			testing = user.toLowerCase();
		}
		System.out.println("Enter a password: ");
		String password = sc.next();

		getUsers = c.prepareStatement("INSERT INTO public.bankusers(username,password,role) VALUES (?,?,'Customer');");
		getUsers.setString(1, user);
		getUsers.setString(2, password);

		int test = getUsers.executeUpdate();
		c.commit();

		System.out.println("Your account has been created!");
	}
}
