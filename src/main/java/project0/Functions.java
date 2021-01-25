package project0;

import java.sql.*;
import java.util.*;
import java.util.logging.FileHandler;


//public class Functions {

	// LOGIN METHOD


//	public static String[] login(Connection c) throws SQLException {
//		PreparedStatement getPassword = c.prepareStatement("select password, role from bankusers where username=?");
//		String pass = null;
//		String role = null;
//		Scanner scan = new Scanner(System.in);
//		System.out.print("Enter Username: ");
//		String username = scan.nextLine();
//
//		System.out.print("\nEnter Password: ");
//		String password = scan.nextLine();
//		getPassword.setString(1, username);
//		ResultSet rs = getPassword.executeQuery();
//		while (rs.next()) {
//			pass = rs.getString("password");
//			role = rs.getString("role");
//		}
//		rs.close();
//		getPassword.close();
//		if (password.equals(pass)) {
//			System.out.println("Login Successful!\n\n");
//			return new String[] { "true", username, role };
//		} else
//			System.out.println("Login Failed\n\n");
//		return new String[] { "false" };
//	}

	// GET CURRENT BALANCE METHOD

//	public static void currentBalance(Connection c, String username) throws SQLException {
//		Statement stmt = null;
//		Scanner sc = new Scanner(System.in);
//		PreparedStatement statement = c.prepareStatement("select userid from bankusers where username=?");
//		statement.setString(1, username);
//		int user = 0;
//		stmt = c.createStatement();
//		ResultSet rs = statement.executeQuery();
//
//		while (rs.next()) {
//			user = rs.getInt("userid");
//
//		}
//		ArrayList accountNums = new ArrayList();
//
//		PreparedStatement getBalances = c.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
//		getBalances.setInt(1, user);
//		rs = getBalances.executeQuery();
//		while (rs.next()) {
//			int AccountNum = rs.getInt("AccountNum");
//			String type = rs.getString("type");
//			accountNums.add(AccountNum);
//			System.out.println(type+" Account: " + AccountNum);
//		}
//		if (accountNums.isEmpty()) {
//			System.out.println("You do not have an account open");
//		} else {
//			System.out.println("What accout would you like to check?");
//			int acc = sc.nextInt();
//			while (accountNums.contains(acc) == false) {
//				System.out.print("Not a valid account. Try again: ");
//				acc = sc.nextInt();
//			}
//			CallableStatement cstmt = c.prepareCall("CALL get_balance(?,?)");
//			cstmt.setInt(1, acc);
//			cstmt.setFloat(2, 0);
//			cstmt.registerOutParameter(2, java.sql.Types.DOUBLE);
//			cstmt.execute();
//
//			double amount = cstmt.getDouble(2);
//			System.out.println("\nAccount: " + acc + " has $" + amount);
//			System.out.print("\n\n");
//
//			rs.close();
//			statement.close();
//			getBalances.close();
//		}
//
//	}

	// DEPOSIT METHOD
//	public static void depositFunds(Connection c, String username) throws SQLException {
//		Statement stmt = null;
//		float currentAmount = 0;
//		Scanner sc = new Scanner(System.in);
//		PreparedStatement statement = c.prepareStatement("select userid from bankusers where username=?");
//		statement.setString(1, username);
//		int user = 0;
//		stmt = c.createStatement();
//		ResultSet rs = statement.executeQuery();
//
//		while (rs.next()) {
//			user = rs.getInt("userid");
//
//		}
//
//		PreparedStatement getBalances = c.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
//		ArrayList accountNums = new ArrayList();
//		getBalances.setInt(1, user);
//		rs = getBalances.executeQuery();
//		while (rs.next()) {
//			int AccountNum = rs.getInt("AccountNum");
//			String type = rs.getString("type");
//			accountNums.add(AccountNum);
//			System.out.println(type+" Account: " + AccountNum);
//
//		}
//		if (accountNums.isEmpty()) {
//			System.out.println("You do not have an open account");
//		} else {
//			System.out.println("\n\nWhat account would you like to deposit funds into?");
//
//			int account = sc.nextInt();
//			while (accountNums.contains(account) == false) {
//				System.out.print("Not a valid account. Try again: ");
//				account = sc.nextInt();
//			}
//			System.out.print("\nHow much would you like to deposit? ");
//			float amount = sc.nextFloat();
//			while (amount < 0) {
//				System.out.print("Amount cannot be less than 0. Please enter valid amount: ");
//				amount = sc.nextFloat();
//			}
//
//			PreparedStatement getCurrentAmount = c.prepareStatement("select amount from accounts where accountnum=?");
//			getCurrentAmount.setInt(1, account);
//			rs = getCurrentAmount.executeQuery();
//			while (rs.next()) {
//				currentAmount = rs.getFloat(1);
//			}
//			currentAmount += amount;
//			stmt = c.createStatement();
//
//			stmt.executeUpdate("update accounts SET amount=" + currentAmount + " where accountnum=" + account + ";");
//			c.commit();
//			stmt.close();
//		}

//		  BasicConfigurator.configure();
//		  FileHandler fh = new FileHandler("BankLog.log");  
//	      logger.add
//		  logger.info("User: "+user+" deposited $"+amount+" into account #"+account);  

	

	// WITHDRAW METHOD

//	public static void withdrawFund(Connection c, String username) throws SQLException {
//
//		Statement stmt = null;
//		float currentAmount = 0;
//		Scanner sc = new Scanner(System.in);
//		PreparedStatement statement = c.prepareStatement("select userid from bankusers where username=?");
//		statement.setString(1, username);
//		int user = 0;
//		stmt = c.createStatement();
//		ResultSet rs = statement.executeQuery();
//
//		while (rs.next()) {
//			user = rs.getInt("userid");
//
//		}
//
//		PreparedStatement getBalances = c.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
//		ArrayList accountNums = new ArrayList();
//		getBalances.setInt(1, user);
//		rs = getBalances.executeQuery();
//		while (rs.next()) {
//			int AccountNum = rs.getInt("AccountNum");
//			String type = rs.getString("type");
//			accountNums.add(AccountNum);
//			System.out.println(type+ " Account: " + AccountNum);
//
//		}
//
//		if (accountNums.isEmpty()) {
//			System.out.println("You do not have an open account");
//		} else {
//			System.out.println("What account would you like to withdraw funds from?");
//
//			int account = sc.nextInt();
//			while (accountNums.contains(account) == false) {
//				System.out.print("Not a valid account. Try again: ");
//				account = sc.nextInt();
//			}
//			PreparedStatement getCurrentAmount = c.prepareStatement("select amount from accounts where accountnum=?");
//			getCurrentAmount.setInt(1, account);
//			rs = getCurrentAmount.executeQuery();
//			while (rs.next()) {
//				currentAmount = rs.getFloat(1);
//			}
//			System.out.print("How much would you like to withdraw? ");
//			float amount = sc.nextFloat();
//			while (amount < 0 || amount > currentAmount) {
//				if (amount < 0) {
//					System.out.print("Amount cannot be less than 0. Please enter a valid amount: ");
//					amount = sc.nextFloat();
//				} else {
//					System.out.print("You do not have sufficient funds. Please enter a value amount: ");
//					amount = sc.nextFloat();
//				}
//			}
//
//			currentAmount -= amount;
//			stmt = c.createStatement();
//
//			stmt.executeUpdate("update accounts SET amount=" + currentAmount + " where accountnum=" + account + ";");
//			c.commit();
//			stmt.close();
//		}
//	}

	// GET ALL TRANSACTION HISTORY
//	public static void getHistory(Connection c) throws SQLException {
//		PreparedStatement stmt = c
//				.prepareStatement("select transaction_type, transaction_user, account, amount from transactions");
//
//		ResultSet rs = stmt.executeQuery();
//		System.out.println();
//		while (rs.next()) {
//			String type = rs.getString(1);
//			int user = rs.getInt(2);
//			int acc = rs.getInt(3);
//			float amount = rs.getFloat(4);
//
//			System.out.println("User " + user + " did a " + type + " for $" + amount + " on account " + acc);
//		}
//		System.out.println("\n");
//
//	}

	// CREATE AN ACCOUNT
//	public static void createAccount(Connection c) throws SQLException {
//
//		Scanner sc = new Scanner(System.in);
//		PreparedStatement getUsers = c.prepareStatement("select username from bankusers");
//		ResultSet rs = getUsers.executeQuery();
//		ArrayList users = new ArrayList();
//		while (rs.next()) {
//			String u = rs.getString(1);
//			users.add(u);
//		}
//
//		System.out.println("Create your account");
//		System.out.println("Enter a username: ");
//		String user = sc.next();
//		while (users.contains(user) == true) {
//			System.out.print("Username taken. Try again: ");
//			user = sc.next();
//		}
//		System.out.println("Enter a password: ");
//		String password = sc.next();
//
//		getUsers = c.prepareStatement("INSERT INTO public.bankusers(username,password,role) VALUES (?,?,'Customer');");
//		getUsers.setString(1, user);
//		getUsers.setString(2, password);
//
//		int test = getUsers.executeUpdate();
//		c.commit();
//
//		System.out.println("Your account has been created!");
//	}

	//APPLY FOR AN ACCOUNT
//	public static void apply(Connection c, String username) throws SQLException{
//		Statement stmt = null;
//		Scanner sc = new Scanner(System.in);
//		PreparedStatement statement = c.prepareStatement("select userid from bankusers where username=?");
//		statement.setString(1, username);
//		int user = 0;
//		stmt = c.createStatement();
//		ResultSet rs = statement.executeQuery();
//
//		while (rs.next()) {
//			user = rs.getInt("userid");
//
//		}
//		System.out.println("What kind of account would you like?\n1)Checkings\n2)Savings\n");
//		int acc = sc.nextInt();
//		while(acc!=1 && acc!=2) {
//			System.out.println("Not a valid choice. Try again: ");
//			acc = sc.nextInt();
//		}
//		System.out.println("How much is your starting balance?");
//		float balance = sc.nextFloat();
//		PreparedStatement applyAccount = c.prepareStatement("insert into accounts(type,amount,user_id) values (?,?,?)");
//		if (acc==1) {
//			applyAccount.setString(1, "Checkings");
//		}
//		else {
//			applyAccount.setString(1, "Savings");
//		}
//		applyAccount.setFloat(2, balance);
//		applyAccount.setInt(3, user);
//		applyAccount.executeUpdate();
//		c.commit();
//		System.out.println("Account submitted for approval");
//	}
	
	//APPROVE ACCOUNT
//	public static void approveAccount(Connection c) throws SQLException{
//
//		Scanner sc = new Scanner(System.in);
//		PreparedStatement statement = c.prepareStatement("select accountnum, user_id from accounts where approved=0");
//		ResultSet rs = statement.executeQuery();
//		ArrayList accountNums = new ArrayList();
//
//		while(rs.next()) {
//			int acc = rs.getInt(1);
//			int user = rs.getInt(2);
//			accountNums.add(acc);
//			System.out.println("Account "+acc+" for user: "+user);
//		}
//		System.out.println("What account would you like to approve: ");
//		int approve = sc.nextInt();
//		while(accountNums.contains(approve)==false) {
//			System.out.print("Account not found. Try again: ");
//			approve = sc.nextInt();
//		}
//		statement = c.prepareStatement("update accounts set approved=1 where accountnum=?;");
//		statement.setInt(1, approve);
//		statement.executeUpdate();
//		c.commit();
//		System.out.println("Account approved!");
//
//	}
	
	//TRANSFER REQUEST
//	public static void requestTransfer(Connection c, String username) throws SQLException {
//		Statement stmt = null;
//		Scanner sc = new Scanner(System.in);
//		int user = getUserId(c,username);
//
//		ArrayList accountNums = new ArrayList();
//
//		PreparedStatement getBalances = c.prepareStatement("select accountnum, type from accounts where user_id=? and approved=1");
//		getBalances.setInt(1, user);
//		rs = getBalances.executeQuery();
//		while (rs.next()) {
//			int AccountNum = rs.getInt("AccountNum");
//			String type = rs.getString("type");
//			accountNums.add(AccountNum);
//			System.out.println(type + " Account: " + AccountNum);
//		}
//		if (accountNums.isEmpty()) {
//			System.out.println("You do not have an account open");
//		} else {
//			System.out.println("What accout would you like to transfer from?");
//			int acc = sc.nextInt();
//			while (accountNums.contains(acc) == false) {
//				System.out.print("Not a valid account. Try again: ");
//				acc = sc.nextInt();
//			}
//			PreparedStatement getCurrentAmount = c.prepareStatement("select amount from accounts where accountnum=?");
//			getCurrentAmount.setInt(1, acc);
//			rs = getCurrentAmount.executeQuery();
//			while (rs.next()) {
//				currentAmount = rs.getFloat(1);
//			}
//			System.out.print("How much would you like to withdraw? ");
//			float amount = sc.nextFloat();
//			while (amount < 0 || amount > currentAmount) {
//				if (amount < 0) {
//					System.out.print("Amount cannot be less than 0. Please enter a valid amount: ");
//					amount = sc.nextFloat();
//				} else {
//					System.out.print("You do not have sufficient funds. Please enter a value amount: ");
//					amount = sc.nextFloat();
//				}
//			}
//			System.out.println("What account would you like to transfer to?");
//
//			ResultSet newResults = stmt.executeQuery("select * from accounts where approved=1");
//			ArrayList validAccounts = null;
//			while(newResults.next()) {
//				int account = rs.getInt("accountnum");
//				String type = rs.getString("type");
//				if(account==acc) {
//					continue;
//				}
//				else {
//					validAccounts.add(account);
//					System.out.println(type+" Account: "+account);
//				}
//
//			}
//			int transferTo = sc.nextInt();
//			if(validAccounts.contains(transferTo)==false) {
//				System.out.print("Not a valid account. Try again: ");
//				transferTo = sc.nextInt();
//			}
//			CallableStatement cstmt = c.prepareCall("CALL transfer(?,?,?)");
//			cstmt.setInt(1, user);
//			cstmt.setInt(2, transferTo);
//			cstmt.setFloat(3, amount);
//			cstmt.execute();
//			c.commit();
//			
//			
//		}
//
//	}
//}
	
	
