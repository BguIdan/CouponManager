package CouponManager;


import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;



public class Main {
	
	public static void main(String[] args) {

		Database db = new Database();
		db.createDatabase();
		db.run();
	}
	
	
}
