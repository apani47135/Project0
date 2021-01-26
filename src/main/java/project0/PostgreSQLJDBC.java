package project0;

import java.sql.*;
import java.util.*;

public class PostgreSQLJDBC {
	public static void main(String args[]) {
		Connection c = null;
		Customer customer;
		Employee employee;
		User sessionUser = new User();
		Scanner sc = new Scanner(System.in);
		String[] logInInfo = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost/BankDatabase", "postgres", "Alex441011");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			System.out.println("Welcome to the bank!");
			boolean flag = true;
			boolean logged_in = true;
			while (flag) {
				System.out.println("1)Login\n2)Create Account\n3)Exit\nWhat would you like to do today?");

				int userSelection = sc.nextInt();

				switch (userSelection) {
				case 1:
					logInInfo = sessionUser.login(c);

					if (logInInfo[0].equals("true") && logInInfo[2].equals("Customer")) {
						logged_in=true;
						customer = new Customer();
						while (logged_in) {
							String user = logInInfo[1];
							System.out.print(
									"1)Check Balance\n2)Deposit\n3)Withdraw\n4)Transfer\n5)Apply for Bank Account\n6)View Transfer Request\n7)Log Out\n\nWhat would you like to do? ");
							int choiceSelect = sc.nextInt();
							switch (choiceSelect) {
							case 1:
								customer.currentBalance(c,user);
								break;
							case 2:
								customer.depositFunds(c, user);
								break;
							case 3:
								customer.withdrawFund(c, user);
								break;
							case 4:
								customer.requestTransfer(c, user);
								break;
							case 5:
								customer.apply(c, user);
								break;
							case 6:
								customer.acceptTransfer(c, user);
								break;
							case 7:
								logged_in = false;
								break;
							default:
								System.out.println("Invalid Choice");
								break;
							}
						}
					} else if(logInInfo[0].equals("true") && logInInfo[2].equals("Employee")) {
						logged_in = true;
						employee = new Employee();
						while (logged_in) {
							System.out.println("You are an Employee!");
							System.out.println("1)View Transaction History\n2)Approve Accounts\n5)Log Out\n\nWhat would you like to do? ");
							int choiceSelect = sc.nextInt();
							switch (choiceSelect) {
							case 1:
								employee.getHistory(c);
								break;
							case 2:
								employee.approveAccount(c);
								break;
							case 5:
								logged_in = false;
								break;
							default:
								System.out.println("Invalid Choice");
								break;
							}
						}
					}
					break;
				case 2:
					sessionUser.createAccount(c);
					flag=false;
					break;
				case 3:
					flag = false;
					break;
				default:
					System.out.println("Invalid Choice");
					break;
				}
			}
			c.close();
			sc.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
	}
}