package zwm.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *        ��װ�������ݿ����
 *       �������ݿ�
 *       ��ɾ���
 *       �ر����ݿ�
 * @author Administrator
 *
 */

public class DBOperation {
	private DBConnection dbc = new DBConnection();
	
	//������ �������ݿ� 
	public Connection getConnection() {
		return dbc.getConnection();
	}
	
	//��ѯ
	public ResultSet Query(String sql) {
		try {
			//ִ�����ݿ����
			PreparedStatement preparestatement = dbc.getConnection().prepareStatement(sql);
			return preparestatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ɾ�ľ���Update���
	public boolean Update(String sql) {
		try {
			//�����¼����0��ʾ�ɹ�
			return dbc.getConnection().prepareStatement(sql).executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void Close() {
		//�ر�����
		dbc.closeCon();
	}
	
}
