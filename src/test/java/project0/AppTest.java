package project0;


import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppTest {
	@Test
	void testLogin(Connection c) throws SQLException {
		System.out.println("======TEST ONE EXECUTED=======");
		Assertions.assertEquals(4, User.login(c));
	}


//	@BeforeEach
//	void setupThis() {
//		System.out.println("@BeforeEach executed");
//	}

	@BeforeAll
	static void setup() throws SQLException {
		System.out.println("@BeforeAll executed");
		Connection c = null;
		c = DriverManager.getConnection("jdbc:postgresql://localhost/BankDatabase", "postgres", "Alex441011");

	}

//	@AfterAll
//	static void tear() {
//		System.out.println("@AfterAll executed");
//	}
}
