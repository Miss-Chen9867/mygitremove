package zwm.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *        封装常用数据库操作
 *       连接数据库
 *       增删查改
 *       关闭数据库
 * @author Administrator
 *
 */

public class DBOperation {
	private DBConnection dbc = new DBConnection();
	
	//打开连接 连接数据库 
	public Connection getConnection() {
		return dbc.getConnection();
	}
	
	//查询
	public ResultSet Query(String sql) {
		try {
			//执行数据库操作
			PreparedStatement preparestatement = dbc.getConnection().prepareStatement(sql);
			return preparestatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//增删改均是Update完成
	public boolean Update(String sql) {
		try {
			//结果记录大于0表示成功
			return dbc.getConnection().prepareStatement(sql).executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void Close() {
		//关闭连接
		dbc.closeCon();
	}
	
}
