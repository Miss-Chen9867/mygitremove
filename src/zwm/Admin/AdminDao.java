package zwm.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

import zwm.Database.DBOperation;

public class AdminDao extends DBOperation{
	
	public Admin login(String name,String password) {
		String sql = "select * from admin where name='"+name+"'and password='"+password+"'";
		ResultSet resultset = Query(sql);
		try {
			//Ŀ���Ƿ��ؽ���� ��ǰ���û���������Ƚ�
			if(resultset.next()) {
				Admin admin = new Admin();
				admin.setId(resultset.getInt("id"));
				admin.setName(resultset.getString("name"));
				admin.setPassword(resultset.getString("password"));
				admin.setFlag(resultset.getInt("flag"));
				return admin;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//û�����ݾͷ���null
		return null;
	}
	//�޸�����
	public boolean editPassword(Admin admin, String newPassword) {
		String sql = "update admin set password = '"+newPassword+"' where id = " + admin.getId();
		return Update(sql);
	}
}
