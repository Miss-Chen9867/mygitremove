package zwm.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  在此连接数据库
 * @author Administrator
 *
 */

public class DBConnection {
	private String JDBCName = "com.mysql.cj.jdbc.Driver";
	private String URL = "jdbc:mysql:///zwmstudent?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=UTC";
	private String USER = "root";
	private String PASSWORD = "1234";
	private Connection connection = null;
	public Connection getConnection() {
		try {
			//1、加载驱动，得到驱动包、
			Class.forName(JDBCName);
			//2、连接数据库
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			//System.out.println("成功连接数据库！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//System.out.println("连接数据库出现问题！");
		} catch (SQLException e) {
			//System.out.println("连接数据库出现问题！");
			e.printStackTrace();
		}	catch (Exception e) {
			//System.out.println("连接数据库出现问题！");
			e.printStackTrace();
		}
		return connection;	
	}
	
	public void closeCon() {
		if(connection != null) {
			try {
				connection.close();
				//System.out.println("已关闭数据库大门！");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DBConnection dbc = new DBConnection();
		dbc.getConnection();
	}
	
}
