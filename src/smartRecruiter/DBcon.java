package smartRecruiter;


import java.sql.*;
public class DBcon {

	public static Connection connect() throws ClassNotFoundException, SQLException{
	
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://cloudrecruiter.ck0g1lzestbc.us-west-2.rds.amazonaws.com:3306/cloudrecruiter";
		String username= "admin"; 
		String password= "password";
		Connection conn = null;
		conn = DriverManager.getConnection(url,username,password);
		return conn;
	}
}