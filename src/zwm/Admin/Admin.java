package zwm.Admin;

/**
 * ��װ����Ա��
 * @author Administrator
 *
 */
public class Admin {
	private int id;
	private String name;
	private String password;
	private int flag = 1;	//����Ա�ı�־Ϊ1
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
