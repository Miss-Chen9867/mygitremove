package zwm.Student;

import java.io.InputStream;

public class Student {
	private int id;
	private String sno;//学号
	private String name;//姓名
	private String password;//密码
	private String sex="男";//性别
	private int age;//年龄
	private String home;//家庭住址
	private String phone;//联系电话
	private String birthday;//生日
	private String clazzid;//班号
	private InputStream photo;//头像
	private String gratuated;
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getClazzid() {
		return clazzid;
	}
	public void setClazzid(String clazzid) {
		this.clazzid = clazzid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String username) {
		this.name = username;
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
	public InputStream getPhoto() {
		return photo;
	}
	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}
	public String getGratuated() {
		return gratuated;
	}
	public void setGratuated(String gratuated) {
		this.gratuated = gratuated;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}