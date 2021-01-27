package project0;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer extends User {
	private static final Logger LOGGER = LogManager.getLogger("com.revature.project0");

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId(Connection c, String username) throws SQLException {
		PreparedStatement statement = c.prepareStatement("select userid from bankusers where username=?");
		statement.setString(1, username);
		int user = 0;
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			user = rs.getInt("userid");

		}
		return user;
	}

	public void currentBalance(Connection c, String username) throws SQLException {
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Scanner sc = new Scanner(System.in);
			int user = getUserId(c, username);
			LOGGER.info("User: "+user+" is checking their balance");
			ArrayList accountNums = new ArrayList();

			PreparedStatement getBalances = c
					.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
			getBalances.setInt(1, user);
			rs = getBalances.executeQuery();
			while (rs.next()) {
				int AccountNum = rs.getInt("AccountNum");
				String type = rs.getString("type");
				accountNums.add(AccountNum);
				System.out.println(type + " Account: " + AccountNum);
			}
			if (accountNums.isEmpty()) {
				System.out.println("You do not have an account open");
			} else {
				System.out.print("What accout would you like to check: ");
				int acc = sc.nextInt();
				while (accountNums.contains(acc) == false) {
					System.out.print("Not a valid account. Try again: ");
					acc = sc.nextInt();
				}
				CallableStatement cstmt = c.prepareCall("CALL get_balance(?,?)");
				cstmt.setInt(1, acc);
				cstmt.setFloat(2, 0);
				cstmt.registerOutParameter(2, java.sql.Types.DOUBLE);
				cstmt.execute();

				double amount = cstmt.getDouble(2);
				System.out.println("\nAccount: " + acc + " has $" + amount);
				System.out.print("\n\n");

				rs.close();
				getBalances.close();
			}
		} catch (Exception e) {
			System.out.println("Input Error. Aborting process.");
			LOGGER.error("Input error. Cancelled Balance Check");

		}

	}

	public void depositFunds(Connection c, String username) throws SQLException {
		try {
			Statement stmt = null;
			ResultSet rs = null;
			float currentAmount = 0;
			Scanner sc = new Scanner(System.in);
			int user = getUserId(c, username);
			LOGGER.info("User: "+user+" is depositing funds");

			PreparedStatement getBalances = c
					.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
			ArrayList accountNums = new ArrayList();
			getBalances.setInt(1, user);
			rs = getBalances.executeQuery();
			while (rs.next()) {
				int AccountNum = rs.getInt("AccountNum");
				String type = rs.getString("type");
				accountNums.add(AccountNum);
				System.out.println(type + " Account: " + AccountNum);

			}
			if (accountNums.isEmpty()) {
				System.out.println("You do not have an open account");
			} else {
				System.out.print("\n\nWhat account would you like to deposit funds into: ");

				int account = sc.nextInt();
				while (accountNums.contains(account) == false) {
					System.out.print("Not a valid account. Try again: ");
					account = sc.nextInt();
				}
				System.out.print("\nHow much would you like to deposit: ");
				float amount = sc.nextFloat();
				while (amount < 0) {
					System.out.print("Amount cannot be less than 0. Please enter valid amount: ");
					amount = sc.nextFloat();
				}

				PreparedStatement getCurrentAmount = c
						.prepareStatement("select amount from accounts where accountnum=?");
				getCurrentAmount.setInt(1, account);
				rs = getCurrentAmount.executeQuery();
				while (rs.next()) {
					currentAmount = rs.getFloat(1);
				}
				currentAmount += amount;
				stmt = c.createStatement();

				stmt.executeUpdate(
						"update accounts SET amount=" + currentAmount + " where accountnum=" + account + ";");
				c.commit();
				stmt.close();
			}
		} catch (Exception e) {
			System.out.println("Input error. Aborting Process");
			LOGGER.error("Input error. Cancelled Deposit");

		}
	}

	public void withdrawFund(Connection c, String username) throws SQLException {
		try {
			Statement stmt = null;
			ResultSet rs = null;
			float currentAmount = 0;
			Scanner sc = new Scanner(System.in);
			int user = getUserId(c, username);
			LOGGER.info("User: "+user+" is withdrawing funds");

			PreparedStatement getBalances = c
					.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
			ArrayList accountNums = new ArrayList();
			getBalances.setInt(1, user);
			rs = getBalances.executeQuery();
			while (rs.next()) {
				int AccountNum = rs.getInt("AccountNum");
				String type = rs.getString("type");
				accountNums.add(AccountNum);
				System.out.println(type + " Account: " + AccountNum);

			}

			if (accountNums.isEmpty()) {
				System.out.println("You do not have an open account");
			} else {
				System.out.print("\nWhat account would you like to withdraw funds from: ");

				int account = sc.nextInt();
				while (accountNums.contains(account) == false) {
					System.out.print("Not a valid account. Try again: ");
					account = sc.nextInt();
				}
				PreparedStatement getCurrentAmount = c
						.prepareStatement("select amount from accounts where accountnum=?");
				getCurrentAmount.setInt(1, account);
				rs = getCurrentAmount.executeQuery();
				while (rs.next()) {
					currentAmount = rs.getFloat(1);
				}
				System.out.print("How much would you like to withdraw: ");
				float amount = sc.nextFloat();
				while (amount < 0 || amount > currentAmount) {
					if (amount < 0) {
						System.out.print("Amount cannot be less than 0. Please enter a valid amount: ");
						amount = sc.nextFloat();
					} else {
						System.out.print("You do not have sufficient funds. Please enter a value amount: ");
						amount = sc.nextFloat();
					}
				}

				currentAmount -= amount;
				stmt = c.createStatement();

				stmt.executeUpdate(
						"update accounts SET amount=" + currentAmount + " where accountnum=" + account + ";");
				c.commit();
				stmt.close();
			}
		} catch (Exception e) {
			System.out.println("Input Error. Aborting Process.");
			LOGGER.error("Input error. Cancelled withdraw");
		}
	}

	public void apply(Connection c, String username) throws SQLException {
		try {
			Statement stmt = null;
			Scanner sc = new Scanner(System.in);
			int user = getUserId(c, username);
			LOGGER.info("User: "+user+" is applying for a new bank account");

			System.out.println("What kind of account would you like?\n1)Checkings\n2)Savings\n");
			int acc = sc.nextInt();
			while (acc != 1 && acc != 2) {
				System.out.println("Not a valid choice. Try again: ");
				acc = sc.nextInt();
			}
			System.out.println("How much is your starting balance?");
			float balance = sc.nextFloat();
			PreparedStatement applyAccount = c
					.prepareStatement("insert into accounts(type,amount,user_id) values (?,?,?)");
			if (acc == 1) {
				applyAccount.setString(1, "Checkings");
			} else {
				applyAccount.setString(1, "Savings");
			}
			applyAccount.setFloat(2, balance);
			applyAccount.setInt(3, user);
			applyAccount.executeUpdate();
			c.commit();
			System.out.println("Account submitted for approval");

		} catch (Exception e) {
			System.out.println("Input error. Aborting Process.");
			LOGGER.error("User input error. Stopped account application.");
		}
	}

	public void requestTransfer(Connection c, String username) throws SQLException {
		try {
		Statement stmt = null;
		ResultSet rs = null;
		float currentAmount = 0;
		Scanner sc = new Scanner(System.in);
		int user = getUserId(c, username);

		ArrayList accountNums = new ArrayList();

		PreparedStatement getBalances = c.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
		getBalances.setInt(1, user);
		rs = getBalances.executeQuery();
		while (rs.next()) {
			int AccountNum = rs.getInt("AccountNum");
			String type = rs.getString("type");
			accountNums.add(AccountNum);
			System.out.println(type + " Account: " + AccountNum);
		}
		if (accountNums.isEmpty()) {
			System.out.println("You do not have an account open");
		} else {
			System.out.println("What account would you like to transfer from?");
			int acc = sc.nextInt();
			while (accountNums.contains(acc) == false) {
				System.out.print("Not a valid account. Try again: ");
				acc = sc.nextInt();
			}
			PreparedStatement getCurrentAmount = c.prepareStatement("select amount from accounts where accountnum=?");
			getCurrentAmount.setInt(1, acc);
			rs = getCurrentAmount.executeQuery();
			while (rs.next()) {
				currentAmount = rs.getFloat(1);
			}
			System.out.print("How much would you like to transfer? ");
			float amount = sc.nextFloat();
			while (amount < 0 || amount > currentAmount) {
				if (amount < 0) {
					System.out.print("Amount cannot be less than 0. Please enter a valid amount: ");
					amount = sc.nextFloat();
				} else {
					System.out.print("You do not have sufficient funds. Please enter a value amount: ");
					amount = sc.nextFloat();
				}
			}
			System.out.println("What account would you like to transfer to?");
			PreparedStatement getAccounts = c.prepareStatement("select accountnum, type from accounts where user_id!=? and approved=1");
			getAccounts.setInt(1, user);
			rs = getAccounts.executeQuery();
			ArrayList validAccounts = new ArrayList();
			while (rs.next()) {
				int account = rs.getInt("accountnum");
				String type = rs.getString("type");
				if (account == acc) {
					continue;
				} else {
					validAccounts.add(account);
					System.out.println(type + " Account: " + account);
				}

			}
			int transferTo = sc.nextInt();
			if (validAccounts.contains(transferTo) == false) {
				System.out.print("Not a valid account. Try again: ");
				transferTo = sc.nextInt();
			}
			PreparedStatement getUser = c.prepareStatement("select user_id from accounts where accountnum=?;");
			getUser.setInt(1, transferTo);
			rs = getUser.executeQuery();
			int transferToUser=0;
			while(rs.next()) {
				transferToUser=rs.getInt("user_id");
			}
			CallableStatement cstmt = c.prepareCall("CALL initiate_transfer(?,?,?,?)");
			cstmt.setInt(1, acc);
			cstmt.setInt(2, transferTo);
			cstmt.setDouble(3, amount);
			cstmt.setInt(4, transferToUser);
			cstmt.execute();
			c.commit();
			LOGGER.info("User: "+user+" initiated a money transfer.");

		}
		}catch(Exception e) {
			System.out.println("Invalid Input. Terminating Process");
			LOGGER.error("Invalid input stopped the money transfer initiation");
		}
	}

	public void acceptTransfer(Connection c, String username) throws SQLException {
		try {
			int user = getUserId(c, username);
			Scanner sc = new Scanner(System.in);
			PreparedStatement stmt = c.prepareStatement(
					"select transfer_id,from_account,amount from transfers where to_user=?;");
			stmt.setInt(1, user);
			ArrayList transfers = new ArrayList();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int transfer_id = rs.getInt("transfer_id");
				int from_account = rs.getInt("from_account");
				double amount = rs.getDouble("amount");
				transfers.add(transfer_id);
				System.out.println(transfer_id + ") From Account: " + from_account + " for the amount of $" + amount);
			}
			if (transfers.isEmpty() == true) {
				System.out.println("\nYou have no pending transfers.");
			} else {
				System.out.println("What transfer would you like to accept/decline? ");
				int choice = sc.nextInt();
				while (transfers.contains(choice) == false) {
					System.out.println("Transfer doesn't exist. Try again: ");
					choice = sc.nextInt();
				}
				System.out.println("1)Accept\n2)Decline\nEnter Choice: ");
				int UserSelection = sc.nextInt();
				while (UserSelection != 1 && UserSelection != 2) {
					System.out.println("Invalid Choice. Try again: ");
					UserSelection = sc.nextInt();
				}
				switch (UserSelection) {
				case 1:
					stmt = c.prepareStatement("select from_account,amount,to_account from transfers where transfer_id=?");
					stmt.setInt(1, choice);
					rs = stmt.executeQuery();
					int transfer_from=0; 
					int transfer_to=0;
					double amount=0;
					while(rs.next()) {
						transfer_from = rs.getInt("from_account");
						amount = rs.getDouble("amount");
						transfer_to = rs.getInt("to_account");
					}
					CallableStatement cstmt = c.prepareCall("CALL transfer(?,?,?)");
					cstmt.setInt(1, transfer_from);
					cstmt.setInt(2, transfer_to);
					cstmt.setDouble(3, amount);
					cstmt.execute();
					c.commit();
					LOGGER.info("User: "+user+" accepted a money transfer");

					
				case 2:
					stmt = c.prepareStatement("delete from transfers where transfer_id=?");
					stmt.setInt(1, choice);
					stmt.executeUpdate();
					c.commit();
					
					break;
				default:
					System.out.println("Not a valid choice. Terminating Process.");

				}

			}
		
		}catch(Exception e) {
			System.out.println("Not a valid choice. Terminating Process.");
			LOGGER.error("Input error stopped money transfer accept/reject");
		}
	}
	}

