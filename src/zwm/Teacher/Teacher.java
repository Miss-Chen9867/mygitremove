package zwm.Teacher;

import java.io.InputStream;

public class Teacher {
	private int id;
	private String tno;//教师工号
	private String name;
	private String password;
	private String sex="男";
	private String phone;
	private String rztime;//入职时间
	private String major;//职务
	private String academic;//所属院系
	private InputStream photo;//头像
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRztime() {
		return rztime;
	}
	public void setRztime(String rztime) {
		this.rztime = rztime;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getAcademic() {
		return academic;
	}
	public void setAcademic(String academic) {
		this.academic = academic;
	}
	public InputStream getPhoto() {
		return photo;
	}
	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}
}
