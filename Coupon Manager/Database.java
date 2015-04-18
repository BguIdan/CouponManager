package CouponManager;

import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String DB_URL2 = "jdbc:mysql://localhost/couponmanager";
	static final String USER = "root";
	static final String PASS = "z6iVwfry";

	public Connection conn;

	public Database() {
		this.conn = null;
	}

	public void createDatabase() {
		Statement stmt = null;
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			//Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE couponmanager";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (stmt!=null)
					stmt.close();
			} catch (SQLException se2){
			}
			try {
				if (conn!=null)
					conn.close();
			} catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

	@Test
	public void run() {
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL2, USER, PASS);
			stmt = conn.createStatement();
			//CREATING TABLE CUSTOMERS
			String sql = "CREATE TABLE CUSTOMERS " +
					"( UserName VARCHAR(10), " + " Name VARCHAR(10), " + 
					"eMail VARCHAR(30), " + " Password VARCHAR(10), " + 
					" CurrentLocation VARCHAR(20), " + " PRIMARY KEY ( UserName ))";
			stmt.executeUpdate(sql);
			//CREATING TABLE COUPONS
			sql = "CREATE TABLE COUPONS " + "( Name VARCHAR(20), " + 
					"Description VARCHAR(30), " + " Category VARCHAR(10), " +
					"OriginalPrice INT, " + "PriceAfterDiscount INT, " + "Ranking INT, " +
					"ExpiresOn VARCHAR(10), " + " PRIMARY KEY ( Name ))";
			stmt.executeUpdate(sql);
			//CREATING TABLE BUSINESSES
			sql = "CREATE TABLE BUSINESSES " + "( Address VARCHAR(20), " + 
					"City VARCHAR(10), " + " Description VARCHAR(20), " +
					"Category VARCHAR(10), "  + " PRIMARY KEY ( Address ))";
			stmt.executeUpdate(sql);
			//CREATING TABLE SOCIAL_NETWORKS
			sql = "CREATE TABLE SOCIAL_NETWORKS " + "( Name VARCHAR(20), " +
					" PRIMARY KEY ( Name ))";
			stmt.executeUpdate(sql);
			//INSERTING RECORDS INTO CUSTOMERS
			sql = "INSERT INTO CUSTOMERS " +
					"VALUES ('rotemsin', 'Rotem', 'rotemsin@post.bgu.ac.il', 1234, 'Beer Sheva')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO CUSTOMERS " +
					"VALUES ('bogdanB', 'Bogdan', 'bogdanovic@post.bgu.ac.il', 4321, 'Beer Sheva')";
			stmt.executeUpdate(sql);
			//INSERTING RECORDS INTO COUPONS
			sql = "INSERT INTO COUPONS " +
					"VALUES ('PizzaDiscount', '20% discount on XXL Pizza', 'Food', 50, 40, 1, '31.12.15')";
			stmt.executeUpdate(sql);
			//INSERTING RECORDS INTO BUSINESSES
			sql = "INSERT INTO BUSINESSES " +
					"VALUES ('Rager St. 130', 'Beer Sheva', 'Pizzeria', 'Food')";
			stmt.executeUpdate(sql);
			//INSERTING RECORDS INTO SOCIAL_NETWORKS
			sql = "INSERT INTO SOCIAL_NETWORKS " +
					"VALUES ('Facebook')";
			stmt.executeUpdate(sql);
			//TEST 1
			sql = "SELECT Name FROM CUSTOMERS" + " WHERE Password = '1234' ";
			ResultSet rs = stmt.executeQuery(sql);
			String resultString = null;
			while (rs.next()) {
				resultString  = rs.getString("Name");
				assertEquals(resultString, "Rotem");
			}
			//TEST 2
			sql = "SELECT eMail FROM CUSTOMERS" + " WHERE Name = 'Idan' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("eMail");
				assertEquals(resultString, "bogdanovic@post.bgu.ac.il");
			}
			//TEST 3
			sql = "SELECT Category FROM COUPONS" + " WHERE OriginalPrice = 50 ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("Category");
				assertEquals(resultString, "Food");
			}
			//TEST 4
			sql = "SELECT ExpiresOn FROM COUPONS" + " WHERE Ranking = 1 ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("ExpiresOn");
				assertEquals(resultString, "31.12.15");
			}
			//TEST 5
			sql = "SELECT Address FROM BUSINESSES" + " WHERE Category = 'Pizzeria' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("Address");
				assertEquals(resultString, "Rager St. 130");
			}
			//TEST 6
			sql = "SELECT Address FROM BUSINESSES" + " WHERE Category = 'Pizzeria' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("Address");
				assertEquals(resultString, "Rager St. 130");
			}
			//TEST 7
			sql = "SELECT Name FROM SOCIAL_NETWORKS" + " WHERE Name = 'Facebook' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("Name");
				assertEquals(resultString, "Facebook");
			}
			//TEST 8
			sql = "SELECT PriceAfterDiscount FROM COUPONS" + " WHERE OriginalPrice = 50 ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int resultInt  = rs.getInt("PriceAfterDiscount");
				assertEquals(resultInt, 40);
			}
			//TEST 9
			sql = "SELECT UserName FROM CUSTOMERS" + " WHERE Name = 'Rotem' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("UserName");
				assertEquals(resultString, "rotemsin");
			}
			//TEST 10
			sql = "SELECT Description FROM COUPONS" + " WHERE Name = 'PizzaDiscount' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				resultString  = rs.getString("Description");
				assertEquals(resultString, "20% discount on XXL Pizza");
			}

		}
		catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch(SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}
}






























