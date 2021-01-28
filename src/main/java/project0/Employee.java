package project0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Employee extends User {
	private static final Logger LOGGER = LogManager.getLogger("com.revature.project0");

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void getHistory(Connection c) throws SQLException {
		LOGGER.info("Employee getting transaction history");
		PreparedStatement stmt = c
				.prepareStatement("select transaction_type, transaction_user, account, amount from transactions");

		ResultSet rs = stmt.executeQuery();
		System.out.println();
		while (rs.next()) {
			String type = rs.getString(1);
			int user = rs.getInt(2);
			int acc = rs.getInt(3);
			double amount = rs.getDouble(4);
			String formatted = String.format("%.2f", amount);

			System.out.println("User " + user + " did a " + type + " for $" + formatted + " on account " + acc);
		}
		System.out.println("\n");

	}

	public void approveAccount(Connection c) throws SQLException {
		try {
			Scanner sc = new Scanner(System.in);
			PreparedStatement statement = c
					.prepareStatement("select accountnum, user_id from accounts where approved=0");
			ResultSet rs = statement.executeQuery();
			ArrayList accountNums = new ArrayList();

			while (rs.next()) {
				int acc = rs.getInt(1);
				int user = rs.getInt(2);
				accountNums.add(acc);
				System.out.println("Account " + acc + " for user: " + user);
			}
			if (accountNums.isEmpty() == true) {
				System.out.println("\nNo Accounts to approve.");
			} else {
				System.out.print("\nWhat account would you like to approve: ");
				int approve = sc.nextInt();
				while (accountNums.contains(approve) == false) {
					System.out.print("Account not found. Try again: ");
					approve = sc.nextInt();
				}
				statement = c.prepareStatement("update accounts set approved=1 where accountnum=?;");
				statement.setInt(1, approve);
				statement.executeUpdate();
				c.commit();
				System.out.println("Account approved!");
			}

		} catch (Exception e) {
			System.out.println("Input error. Aborting process.");
			LOGGER.info("Employee Approving/Denying Accounts");
		}
	}

	public void checkAccounts(Connection c) throws SQLException {
		try {
		Scanner sc = new Scanner(System.in);
		PreparedStatement stmt = c.prepareStatement("select userid from bankusers where role='Customer'");
		ResultSet rs = stmt.executeQuery();
		ArrayList users = new ArrayList();
		System.out.println("\nHere is a list of all User ID's: ");
		while(rs.next()) {
			int user = rs.getInt("userid");
			users.add(user);
			System.out.println("User "+user);
		}
		System.out.print("Selet what User you would like to view: ");
		int choice = sc.nextInt();
		while(users.contains(choice)==false) {
			System.out.println("User does not exist. Try again: ");
			choice = sc.nextInt();
		}
		stmt = c.prepareStatement("select accountnum, type, amount from accounts where user_id=? and approved=1");
		stmt.setInt(1, choice);
		rs = stmt.executeQuery();
		ArrayList check = new ArrayList();
		System.out.println();
		while(rs.next()) {
			int num = rs.getInt(1);
			String type = rs.getString(2);
			double amount = rs.getDouble(3);
			String formatted = String.format("%.2f", amount);

			check.add(num);
			System.out.println(type+ " Account: "+num+" has $"+formatted);

		}
		if(check.isEmpty()) {
			System.out.println("This user has no active accounts.\n");
		}
		LOGGER.info("Employee checking accounts for user "+choice);
	}catch(Exception e) {
		System.out.println("Invalid Input. Terminating Process");
		LOGGER.error("Invalid Input. Terminating Process");
	}
	}
}
