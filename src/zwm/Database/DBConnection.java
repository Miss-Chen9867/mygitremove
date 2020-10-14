package zwm.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  �ڴ��������ݿ�
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
			//1�������������õ���������
			Class.forName(JDBCName);
			//2���������ݿ�
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			//System.out.println("�ɹ��������ݿ⣡");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//System.out.println("�������ݿ�������⣡");
		} catch (SQLException e) {
			//System.out.println("�������ݿ�������⣡");
			e.printStackTrace();
		}	catch (Exception e) {
			//System.out.println("�������ݿ�������⣡");
			e.printStackTrace();
		}
		return connection;	
	}
	
	public void closeCon() {
		if(connection != null) {
			try {
				connection.close();
				//System.out.println("�ѹر����ݿ���ţ�");
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
