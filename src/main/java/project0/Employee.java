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

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void getHistory(Connection c) throws SQLException {
		PreparedStatement stmt = c
				.prepareStatement("select transaction_type, transaction_user, account, amount from transactions");

		ResultSet rs = stmt.executeQuery();
		System.out.println();
		while (rs.next()) {
			String type = rs.getString(1);
			int user = rs.getInt(2);
			int acc = rs.getInt(3);
			float amount = rs.getFloat(4);

			System.out.println("User " + user + " did a " + type + " for $" + amount + " on account " + acc);
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
				System.out.println("No Accounts to approve.");
			} else {
				System.out.println("What account would you like to approve: ");
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
		}
	}
}
