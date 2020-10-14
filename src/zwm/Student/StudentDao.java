package zwm.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import zwm.Database.DBOperation;
import zwm.Login.String.StringUtil;
import zwm.page.Page;

public class StudentDao extends DBOperation{
	
	public Student login(String name,String password) {
		String sql ="select * from student where name='"+name+"' and password='"+password+"'";
		ResultSet resultset = Query(sql);
		try {
			//有内容返回一个对象
			if(resultset.next()) {
				Student student = new Student();//创建一个学生对象
				student.setId(resultset.getInt("id"));
				student.setSno(resultset.getString("sno"));//学号
				student.setName(resultset.getString("name"));//姓名
				student.setPassword(resultset.getString("password"));//密码
				student.setSex(resultset.getString("sex"));//性别
				student.setAge(resultset.getInt("age"));//年龄
				student.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student.setBirthday(change(date));//生日
				student.setHome(resultset.getString("home"));//家庭住址
				student.setPhone(resultset.getString("phone"));//联系电话
				return student;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//没有内容就返回NULL
		return null;
	}	
	//是否存在该学生
	public boolean getStudent(String sno) {
		String sql ="select * from student where sno='"+sno+"'";
		ResultSet resultset = Query(sql);
		try {
			//有内容返回一个对象
			if(resultset.next()) {
				Student student = new Student();//创建一个学生对象
				student.setId(resultset.getInt("id"));
				student.setSno(resultset.getString("sno"));//学号
				student.setName(resultset.getString("name"));//姓名
				student.setPassword(resultset.getString("password"));//密码
				student.setSex(resultset.getString("sex"));//性别
				student.setAge(resultset.getInt("age"));//年龄
				student.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student.setBirthday(change(date));//生日
				student.setHome(resultset.getString("home"));//家庭住址
				student.setPhone(resultset.getString("phone"));//联系电话
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//获取学生
	public Student getStudent1(String sno) {
		Student student = new Student();
		String sql ="select photo from student where sno='"+sno+"'";
		ResultSet resultset = Query(sql);
		try {
			//有内容返回一个对象
			if(resultset.next()) {
				student.setPhoto(resultset.getBinaryStream("photo"));
				return student;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}
	
	//增加学生
	public boolean addStudent(Student student){
		//先查询班级是否存在
		String sql0 = "select clazzid from clazz where clazzid = '"+student.getClazzid()+"'";
		ResultSet rs = Query(sql0);
		try {
			if(rs.next()) {
			}else {
				return false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//查询id最大值
		boolean kkk = true;
		String ss = "select id from student";
		int max1 = 0;
		int[] flag = new int[99999999];
		for(int i=0;i<99999999;i++) {
			flag[i] = 0;
		}
		ResultSet resultSet = Query(ss);
		try {
			while(resultSet.next()){
				flag[resultSet.getInt("id")] = 1;
				if(resultSet.getInt("id")>max1) {
					max1 = resultSet.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//如果中间有一个漏的后面的补上
		for(int i=1;i<=max1;i++) {
			if(flag[i]!=1) {
				for(int j=i+1;j<=max1;j++) {
					String s0 = "update student set id=id-1 where id="+j;
					Update(s0);
				}
				kkk = false;
				break;
			}
		}
		if(max1 == 0 || kkk)
			max1++;
		student.setId(max1);
		String clazzid = student.getClazzid();
		String sno = getS(clazzid);
		//获取学号
		student.setSno(sno);
		if(student.getSno().charAt(3)=='6'||student.getSno().charAt(3)=='7'||student.getSno().charAt(3)=='8'||student.getSno().charAt(3)=='9') {
			student.setGratuated("否");
		}else {
			student.setGratuated("是");
		}
		//学号最后两位
		String sql2 = "select sno from student where sno like '%" +student.getSno()+ "%'";
		ResultSet resultSet1 = Query(sql2);
		try {
			int max = 0;
			while(resultSet1.next()){
				String s = resultSet1.getString("sno");				
				if(s.charAt(10)=='0') {
					s = s.charAt(11)+"";
				}else {
					String a = s.charAt(10)+"";
					s = a + s.charAt(11);
				}
				if(Integer.parseInt(s)>max) {
					max = Integer.parseInt(s);
				}
			}
			max++;
			Integer integer = new Integer(max);
			String m = integer.toString();
			if(m.length()==1) {
				String x = m;
				m="0";
				m+=x;
			}
			student.setSno((student.getSno()+m));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql1 = "insert into student values('"+student.getId()+"','"+student.getSno()+"','"+student.getName()+"','"+student.getPassword()+"','"+student.getSex()+"','"+student.getAge()+"','"+student.getBirthday()+"','"+student.getHome()+"','"+student.getPhone()+"','"+student.getClazzid()+"','"+student.getGratuated()+"','')";
		return Update(sql1);
	}
	//修改
	public boolean editStudent(Student student) {
		String sql = "update student set sno='"+student.getSno()+"',name='"+student.getName()+"',password='"+student.getPassword()+"',sex='"+student.getSex()+"',age = '"+student.getAge()+"',birthday='"+student.getBirthday()+"',home='"+student.getHome()+"',phone='"+student.getPhone()+"',clazzid='"+student.getClazzid()+"',gratuated = '"+student.getGratuated()+"' where id="+student.getId();
		return Update(sql);
	}	
	
	//获取学号
	public String getS(String clazzid) {
		String sno = "2019";
		if(clazzid.charAt(0)=='1') {
			sno="2019";
		}else if(clazzid.charAt(0)=='2') {
			sno="2018";
		}else if(clazzid.charAt(0)=='3') {
			sno="2017";
		}else if(clazzid.charAt(0)=='4') {
			sno="2016";
		}
		sno+="28";
		sno+=(clazzid.charAt(1));
		sno+=(clazzid.charAt(2));
		sno+=(clazzid.charAt(3));
		sno+=(clazzid.charAt(4));
		return sno;
	}
	//升级学生(暂定)
	public boolean UpStudent() {
		//有则做，并且需要获取原先学号
		String sql = "update student set clazzid='无' where clazzid like '4%'";
		Update(sql);
		sql = "update student set clazzid='无' where clazzid like '4%'";
		Update(sql);
		sql = "update student set clazzid='无' where clazzid like '4%'";
		Update(sql);
		sql = "update student set clazzid='无' where clazzid like '4%'";
		Update(sql);
		return true;
	}
	//删除
	public boolean deleteStudent(String snos,String ids) {
		
		//查询学生是否有选课
		String sql0 = "select * from selectedcourse where sno in("+snos+")";
		//如果学生有选课记录则删除学生的选课记录
		ResultSet rs0 = Query(sql0);
		try {
			if(rs0.next()) {
				sql0 = "delete from selectedcourse where sno in("+snos+")";
				Update(sql0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//查询学生是否有成绩
		String sql1 = "select * from score where sno in("+snos+")";
		//删除学生的选课成绩
		ResultSet rs1 = Query(sql1);
		try {
			if(rs1.next()) {
				sql1 = "delete from score where sno in("+snos+")";
				Update(sql1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//删除学生表里的学生
		String sql = "delete from student where id in("+ids+")";
		return Update(sql);
	}	

	//查找所有学生
	public List<Student> getAllStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student";
		if(!StringUtil.isEmpty(student.getSno())) {
			sql = "select * from student where sno like '%" +student.getSno()+ "%'";
		}
		if(!StringUtil.isEmpty(student.getClazzid())) {
			sql = "select * from student where clazzid like '%" +student.getClazzid()+ "%'";
		}
		if((!StringUtil.isEmpty(student.getSno()))&&(!StringUtil.isEmpty(student.getClazzid()))){
			sql = "select * from student where name like '%" + student.getSno() + "%' and academic like '%" + student.getClazzid() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultset = Query(sql.replaceFirst(" and", "where"));
		try {
			while(resultset.next()){
				Student student1 = new Student();//创建一个学生对象
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//学号
				student1.setName(resultset.getString("name"));//姓名
				student1.setPassword(resultset.getString("password"));//密码
				student1.setSex(resultset.getString("sex"));//性别
				student1.setAge(resultset.getInt("age"));//年龄
				student1.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//生日
				student1.setHome(resultset.getString("home"));//家庭住址
				student1.setPhone(resultset.getString("phone"));//联系电话
				student1.setGratuated(resultset.getString("gratuated"));
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	//查找在校学生
	public List<Student> getInStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where gratuated='否'";
		if(!StringUtil.isEmpty(student.getSno())) {
			sql = "select * from student where sno like '%" +student.getSno()+ "%'";
		}
		if(!StringUtil.isEmpty(student.getClazzid())) {
			sql = "select * from student where  like '%" +student.getClazzid()+ "%'";
		}
		if((!StringUtil.isEmpty(student.getSno()))&&(!StringUtil.isEmpty(student.getClazzid()))){
			sql = "select * from clazz where name like '%" + student.getSno() + "%' and academic like '%" + student.getClazzid() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultset = Query(sql.replaceFirst(" and", "where"));
		try {
			while(resultset.next()){
				Student student1 = new Student();//创建一个学生对象
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//学号
				student1.setName(resultset.getString("name"));//姓名
				student1.setPassword(resultset.getString("password"));//密码
				student1.setSex(resultset.getString("sex"));//性别
				student1.setAge(resultset.getInt("age"));//年龄
				student1.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//生日
				student1.setHome(resultset.getString("home"));//家庭住址
				student1.setPhone(resultset.getString("phone"));//联系电话
				student1.setGratuated(resultset.getString("gratuated"));//是否毕业
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}		
	
	//查找大一学生
	public List<Student> getOneStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where clazzid like '1%'";
		if(!StringUtil.isEmpty(student.getSno())) {
			sql = "select * from student where sno like '%" +student.getSno()+ "%'";
		}
		if(!StringUtil.isEmpty(student.getClazzid())) {
			sql = "select * from student where  like '%" +student.getClazzid()+ "%'";
		}
		if((!StringUtil.isEmpty(student.getSno()))&&(!StringUtil.isEmpty(student.getClazzid()))){
			sql = "select * from clazz where name like '%" + student.getSno() + "%' and academic like '%" + student.getClazzid() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultset = Query(sql.replaceFirst(" and", "where"));
		try {
			while(resultset.next()){
				Student student1 = new Student();//创建一个学生对象
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//学号
				student1.setName(resultset.getString("name"));//姓名
				student1.setPassword(resultset.getString("password"));//密码
				student1.setSex(resultset.getString("sex"));//性别
				student1.setAge(resultset.getInt("age"));//年龄
				student1.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//生日
				student1.setHome(resultset.getString("home"));//家庭住址
				student1.setPhone(resultset.getString("phone"));//联系电话
				student1.setGratuated(resultset.getString("gratuated"));//是否毕业
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	

	//查找大二学生
	public List<Student> getTwoStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where clazzid like '2%'";
		if(!StringUtil.isEmpty(student.getSno())) {
			sql = "select * from student where sno like '%" +student.getSno()+ "%'";
		}
		if(!StringUtil.isEmpty(student.getClazzid())) {
			sql = "select * from student where  like '%" +student.getClazzid()+ "%'";
		}
		if((!StringUtil.isEmpty(student.getSno()))&&(!StringUtil.isEmpty(student.getClazzid()))){
			sql = "select * from clazz where name like '%" + student.getSno() + "%' and academic like '%" + student.getClazzid() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultset = Query(sql.replaceFirst(" and", "where"));
		try {
			while(resultset.next()){
				Student student1 = new Student();//创建一个学生对象
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//学号
				student1.setName(resultset.getString("name"));//姓名
				student1.setPassword(resultset.getString("password"));//密码
				student1.setSex(resultset.getString("sex"));//性别
				student1.setAge(resultset.getInt("age"));//年龄
				student1.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//生日
				student1.setHome(resultset.getString("home"));//家庭住址
				student1.setPhone(resultset.getString("phone"));//联系电话
				student1.setGratuated(resultset.getString("gratuated"));//是否毕业
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}		
	
	//查找大三学生
	public List<Student> getThreeStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where clazzid like '3%'";
		if(!StringUtil.isEmpty(student.getSno())) {
			sql = "select * from student where sno like '%" +student.getSno()+ "%'";
		}
		if(!StringUtil.isEmpty(student.getClazzid())) {
			sql = "select * from student where  like '%" +student.getClazzid()+ "%'";
		}
		if((!StringUtil.isEmpty(student.getSno()))&&(!StringUtil.isEmpty(student.getClazzid()))){
			sql = "select * from clazz where name like '%" + student.getSno() + "%' and academic like '%" + student.getClazzid() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultset = Query(sql.replaceFirst(" and", "where"));
		try {
			while(resultset.next()){
				Student student1 = new Student();//创建一个学生对象
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//学号
				student1.setName(resultset.getString("name"));//姓名
				student1.setPassword(resultset.getString("password"));//密码
				student1.setSex(resultset.getString("sex"));//性别
				student1.setAge(resultset.getInt("age"));//年龄
				student1.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//生日
				student1.setHome(resultset.getString("home"));//家庭住址
				student1.setPhone(resultset.getString("phone"));//联系电话
				student1.setGratuated(resultset.getString("gratuated"));//是否毕业
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	
	
	//查找大四学生
	public List<Student> getFourStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where clazzid like '4%'";
		if(!StringUtil.isEmpty(student.getSno())) {
			sql = "select * from student where sno like '%" +student.getSno()+ "%'";
		}
		if(!StringUtil.isEmpty(student.getClazzid())) {
			sql = "select * from student where  like '%" +student.getClazzid()+ "%'";
		}
		if((!StringUtil.isEmpty(student.getSno()))&&(!StringUtil.isEmpty(student.getClazzid()))){
			sql = "select * from clazz where name like '%" + student.getSno() + "%' and academic like '%" + student.getClazzid() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultset = Query(sql.replaceFirst(" and", "where"));
		try {
			while(resultset.next()){
				Student student1 = new Student();//创建一个学生对象
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//学号
				student1.setName(resultset.getString("name"));//姓名
				student1.setPassword(resultset.getString("password"));//密码
				student1.setSex(resultset.getString("sex"));//性别
				student1.setAge(resultset.getInt("age"));//年龄
				student1.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//生日
				student1.setHome(resultset.getString("home"));//家庭住址
				student1.setPhone(resultset.getString("phone"));//联系电话
				student1.setGratuated(resultset.getString("gratuated"));//是否毕业
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	
	
	//查找毕业学生
	public List<Student> getOutStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where gratuated='是'";
		if(!StringUtil.isEmpty(student.getSno())) {
			sql = "select * from student where sno like '%" +student.getSno()+ "%'";
		}
		if(!StringUtil.isEmpty(student.getClazzid())) {
			sql = "select * from student where  like '%" +student.getClazzid()+ "%'";
		}
		if((!StringUtil.isEmpty(student.getSno()))&&(!StringUtil.isEmpty(student.getClazzid()))){
			sql = "select * from clazz where name like '%" + student.getSno() + "%' and academic like '%" + student.getClazzid() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultset = Query(sql.replaceFirst(" and", "where"));
		try {
			while(resultset.next()){
				Student student1 = new Student();//创建一个学生对象
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//学号
				student1.setName(resultset.getString("name"));//姓名
				student1.setPassword(resultset.getString("password"));//密码
				student1.setSex(resultset.getString("sex"));//性别
				student1.setAge(resultset.getInt("age"));//年龄
				student1.setClazzid(resultset.getString("clazzid"));//班号
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//生日
				student1.setHome(resultset.getString("home"));//家庭住址
				student1.setPhone(resultset.getString("phone"));//联系电话
				student1.setGratuated(resultset.getString("gratuated"));//是否毕业
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}		
	
	//设置图像
	public boolean setStudentPhoto(Student student) {
		String sql = "update student set photo = ? where sno = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setBinaryStream(1,student.getPhoto());
			prepareStatement.setString(2,student.getSno());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Update(sql);
	}
	//获取所有学生记录总条数
		public int getAllStudentListTotal(Student student){
			int total = 0;
			String sql = "select count(*) as total from student";
			if(!StringUtil.isEmpty(student.getName())){
				sql += " and name like '%" + student.getName() + "%'";
			}
			if(!StringUtil.isEmpty(student.getClazzid())){
				sql += " and clazzid like '%" + student.getName() + "%'";
			}
			if(student.getId() != 0){
				sql += " and id = " + student.getId();
			}
			ResultSet resultSet = Query(sql.replaceFirst(" and", "where"));
			try {
				while(resultSet.next()){
					total = resultSet.getInt("total");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return total;
		}
		//获取在校生记录总条数
				public int getInStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where gratuated='否'";
					if(!StringUtil.isEmpty(student.getName())){
						sql += " and name like '%" + student.getName() + "%'";
					}
					if(!StringUtil.isEmpty(student.getClazzid())){
						sql += " and clazzid like '%" + student.getName() + "%'";
					}
					if(student.getId() != 0){
						sql += " and id = " + student.getId();
					}
					ResultSet resultSet = Query(sql.replaceFirst(" and", "where"));
					try {
						while(resultSet.next()){
							total = resultSet.getInt("total");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return total;
				}
				//获取大一学生记录总条数
				public int getOneStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where clazzid like '1%'";
					if(!StringUtil.isEmpty(student.getName())){
						sql += " and name like '%" + student.getName() + "%'";
					}
					if(!StringUtil.isEmpty(student.getClazzid())){
						sql += " and clazzid like '%" + student.getName() + "%'";
					}
					if(student.getId() != 0){
						sql += " and id = " + student.getId();
					}
					ResultSet resultSet = Query(sql.replaceFirst(" and", "where"));
					try {
						while(resultSet.next()){
							total = resultSet.getInt("total");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return total;
				}
				//获取大二学生记录总条数
				public int getTwoStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where clazzid like '2%'";
					if(!StringUtil.isEmpty(student.getName())){
						sql += " and name like '%" + student.getName() + "%'";
					}
					if(!StringUtil.isEmpty(student.getClazzid())){
						sql += " and clazzid like '%" + student.getName() + "%'";
					}
					if(student.getId() != 0){
						sql += " and id = " + student.getId();
					}
					ResultSet resultSet = Query(sql.replaceFirst(" and", "where"));
					try {
						while(resultSet.next()){
							total = resultSet.getInt("total");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return total;
				}
				//获取大三学生记录总条数
				public int getThreeStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where clazzid like '3%'";
					if(!StringUtil.isEmpty(student.getName())){
						sql += " and name like '%" + student.getName() + "%'";
					}
					if(!StringUtil.isEmpty(student.getClazzid())){
						sql += " and clazzid like '%" + student.getName() + "%'";
					}
					if(student.getId() != 0){
						sql += " and id = " + student.getId();
					}
					ResultSet resultSet = Query(sql.replaceFirst(" and", "where"));
					try {
						while(resultSet.next()){
							total = resultSet.getInt("total");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return total;
				}
				//获取大四学生记录总条数
				public int getFourStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where clazzid like '4%'";
					if(!StringUtil.isEmpty(student.getName())){
						sql += " and name like '%" + student.getName() + "%'";
					}
					if(!StringUtil.isEmpty(student.getClazzid())){
						sql += " and clazzid like '%" + student.getName() + "%'";
					}
					if(student.getId() != 0){
						sql += " and id = " + student.getId();
					}
					ResultSet resultSet = Query(sql.replaceFirst(" and", "where"));
					try {
						while(resultSet.next()){
							total = resultSet.getInt("total");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return total;
				}
				//获取毕业生记录总条数
				public int getOutStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where gratuated='是'";
					if(!StringUtil.isEmpty(student.getName())){
						sql += " and name like '%" + student.getName() + "%'";
					}
					if(!StringUtil.isEmpty(student.getClazzid())){
						sql += " and clazzid like '%" + student.getName() + "%'";
					}
					if(student.getId() != 0){
						sql += " and id = " + student.getId();
					}
					ResultSet resultSet = Query(sql.replaceFirst(" and", "where"));
					try {
						while(resultSet.next()){
							total = resultSet.getInt("total");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return total;
				}
		
	//Date类型转化为String类型
	public String change(Date date) {
		String dt;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dt = sdf.format(date);
		return dt;
	}

	//修改密码
	public boolean editPassword(Student student, String newPassword) {
		String sql = "update student set password = '"+newPassword+"' where id = " + student.getId();
		return Update(sql);
	}
	//查询学生
	public List<Student> FindStudentList(String sno, String grade, String academic, String major, String clazz,String name, String sex, Page page) {
			List<Student> ret = new ArrayList<Student>();
			String sql = "select * from student";
			if(!(StringUtil.isEmpty(sno))) {
				sql += " and sno='"+sno+"'";
			}
			if(!StringUtil.isEmpty(grade)){
				if(grade.equals("大一")) {
					sql += " and clazzid like '1%'";
				}else if(grade.equals("大二")) {
					sql += " and clazzid like '2%'";
				}else if(grade.equals("大三")) {
					sql += " and clazzid like '3%'";
				}else if(grade.equals("大四")) {
					sql += " and clazzid like '4%'";
				}
			}
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("经济与管理学院")) {
					sql += " and substr(clazzid,2,2)='01'";
				}else if(academic.equals("数学与统计学院")) {
					sql += " and substr(clazzid,2,2)='02'";
				}else if(academic.equals("物理与电子工程学院")) {
					sql += " and substr(clazzid,2,2)='03'";
				}else if(academic.equals("计算机科学与技术学院")) {
					sql += " and substr(clazzid,2,2)='04'";
				}else if(academic.equals("化学与制药工程学院")) {
					sql += " and substr(clazzid,2,2)='05'";
				}else if(academic.equals("环境科学与旅游学院")) {
					sql += " and substr(clazzid,2,2)='06'";
				}else if(academic.equals("教育科学学院")) {
					sql += " and substr(clazzid,2,2)='07'";
				}else if(academic.equals("历史文化学院")) {
					sql += " and substr(clazzid,2,2)='08'";
				}else if(academic.equals("生命科学与技术学院")) {
					sql += " and substr(clazzid,2,2)='09'";
				}else if(academic.equals("外国语学院")) {
					sql += " and substr(clazzid,2,2)='10'";
				}else if(academic.equals("政治与公共管理学院")) {
					sql += " and substr(clazzid,2,2)='11'";
				}else if(academic.equals("国际教育学院")) {
					sql += " and substr(clazzid,2,2)='12'";
				}else if(academic.equals("音乐学院")) {
					sql += " and substr(clazzid,2,2)='13'";
				}else if(academic.equals("体育学院")) {
					sql += " and substr(clazzid,2,2)='14'";
				}else if(academic.equals("文学院")) {
					sql += " and substr(clazzid,2,2)='15'";
				}else if(academic.equals("法学院")) {
					sql += " and substr(clazzid,2,2)='16'";
				}else if(academic.equals("美术与艺术设计学院")) {
					sql += " and substr(clazzid,2,2)='17'";
				}else if(academic.equals("土木建筑工程学院")) {
					sql += " and substr(clazzid,2,2)='18'";
				}else if(academic.equals("新闻与传播学院")) {
					sql += " and substr(clazzid,2,2)='19'";
				}
			}
			if(!StringUtil.isEmpty(major)){
				//班名 采用正则匹配
				if(major.equals("工商管理专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("市场营销专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("人力资源专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("财务管理专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("会计学专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("国际经济与贸易专业")) {
					sql += " and substr(clazzid,4,1)='6'";
				}else if(major.equals("金融学专业")) {
					sql += " and substr(clazzid,4,1)='7'";
				}else if(major.equals("保险专业")) {
					sql += " and substr(clazzid,4,1)='8'";
				}else if(major.equals("财政学专业")) {
					sql += " and substr(clazzid,4,1)='9'";
				}else if(major.equals("数学与应用数学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("统计学专业 ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("金融工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("金融科学专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("信息与计算科学专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("物理学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("电子信息科学与技术专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("自动化专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("光电信息科学专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("计算机科学与技术专业 ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("大数据工程专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("物联网工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("软件工程专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("网络工程专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("化学工程与工艺专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("能源化学工程专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("制药工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("药物制剂专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("药学专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("地理科学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("地理信息科学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("测绘工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("教育学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("心理学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("学前教育学专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("文化产业管理专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("人文教育专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("生物技术专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("园林专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("英语专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("商务英语专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("日语专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("政治学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("哲学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("汉语国际教育专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("钢琴专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("声乐专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("体育教育专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("社会体育专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("中国文学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("语言学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("法学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("侦查学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("工业设计专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("平面设计专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("土木工程专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("建筑环境与能源应用工程专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("广告学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("新闻学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("传播学专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}
		   }
			if(!(StringUtil.isEmpty(clazz))) {
				if(clazz.equals("1班")) {
					sql += " and substr(clazzid,5,1)='1'";
				}else if(clazz.equals("2班")) {
					sql += " and substr(clazzid,5,1)='2'";
				}else if(clazz.equals("3班")) {
					sql += " and substr(clazzid,5,1)='3'";
				}else if(clazz.equals("4班")) {
					sql += " and substr(clazzid,5,1)='4'";
				}
			}
			if(!(StringUtil.isEmpty(name))) {
					sql += " and name='"+name+"'";
			}
			if(!(StringUtil.isEmpty(sex))) {
				sql += " and sex='"+sex+"'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();
			ResultSet resultset = Query(sql.replaceFirst("and", "where"));
			try {
				while(resultset.next()){
					Student student1 = new Student();//创建一个学生对象
					student1.setId(resultset.getInt("id"));
					student1.setSno(resultset.getString("sno"));//学号
					student1.setName(resultset.getString("name"));//姓名
					student1.setPassword(resultset.getString("password"));//密码
					student1.setSex(resultset.getString("sex"));//性别
					student1.setAge(resultset.getInt("age"));//年龄
					student1.setClazzid(resultset.getString("clazzid"));//班号
					Date date = resultset.getDate("birthday");
					student1.setBirthday(change(date));//生日
					student1.setHome(resultset.getString("home"));//家庭住址
					student1.setPhone(resultset.getString("phone"));//联系电话
					student1.setGratuated(resultset.getString("gratuated"));//是否毕业
					ret.add(student1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
	}
	//返回查询条数
	public int getFindStudentListTotal(String sno, String grade, String academic, String major, String clazz,String name, String sex) {
		int total = 0;
		String sql = "select count(*) as total from student";
		if(!(StringUtil.isEmpty(sno))) {
			sql += " and sno='"+sno+"'";
		}
		if(!StringUtil.isEmpty(grade)){
			if(grade.equals("大一")) {
				sql += " and clazzid like '1%'";
			}else if(grade.equals("大二")) {
				sql += " and clazzid like '2%'";
			}else if(grade.equals("大三")) {
				sql += " and clazzid like '3%'";
			}else if(grade.equals("大四")) {
				sql += " and clazzid like '4%'";
			}
		}
		if(!StringUtil.isEmpty(academic)){
			if(academic.equals("经济与管理学院")) {
				sql += " and substr(clazzid,2,2)='01'";
			}else if(academic.equals("数学与统计学院")) {
				sql += " and substr(clazzid,2,2)='02'";
			}else if(academic.equals("物理与电子工程学院")) {
				sql += " and substr(clazzid,2,2)='03'";
			}else if(academic.equals("计算机科学与技术学院")) {
				sql += " and substr(clazzid,2,2)='04'";
			}else if(academic.equals("化学与制药工程学院")) {
				sql += " and substr(clazzid,2,2)='05'";
			}else if(academic.equals("环境科学与旅游学院")) {
				sql += " and substr(clazzid,2,2)='06'";
			}else if(academic.equals("教育科学学院")) {
				sql += " and substr(clazzid,2,2)='07'";
			}else if(academic.equals("历史文化学院")) {
				sql += " and substr(clazzid,2,2)='08'";
			}else if(academic.equals("生命科学与技术学院")) {
				sql += " and substr(clazzid,2,2)='09'";
			}else if(academic.equals("外国语学院")) {
				sql += " and substr(clazzid,2,2)='10'";
			}else if(academic.equals("政治与公共管理学院")) {
				sql += " and substr(clazzid,2,2)='11'";
			}else if(academic.equals("国际教育学院")) {
				sql += " and substr(clazzid,2,2)='12'";
			}else if(academic.equals("音乐学院")) {
				sql += " and substr(clazzid,2,2)='13'";
			}else if(academic.equals("体育学院")) {
				sql += " and substr(clazzid,2,2)='14'";
			}else if(academic.equals("文学院")) {
				sql += " and substr(clazzid,2,2)='15'";
			}else if(academic.equals("法学院")) {
				sql += " and substr(clazzid,2,2)='16'";
			}else if(academic.equals("美术与艺术设计学院")) {
				sql += " and substr(clazzid,2,2)='17'";
			}else if(academic.equals("土木建筑工程学院")) {
				sql += " and substr(clazzid,2,2)='18'";
			}else if(academic.equals("新闻与传播学院")) {
				sql += " and substr(clazzid,2,2)='19'";
			}
		}
		if(!StringUtil.isEmpty(major)){
			//班名 采用正则匹配
			if(major.equals("工商管理专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("市场营销专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("人力资源专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("财务管理专业")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("会计学专业")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("国际经济与贸易专业")) {
				sql += " and substr(clazzid,4,1)='6'";
			}else if(major.equals("金融学专业")) {
				sql += " and substr(clazzid,4,1)='7'";
			}else if(major.equals("保险专业")) {
				sql += " and substr(clazzid,4,1)='8'";
			}else if(major.equals("财政学专业")) {
				sql += " and substr(clazzid,4,1)='9'";
			}else if(major.equals("数学与应用数学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("统计学专业 ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("金融工程专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("金融科学专业")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("信息与计算科学专业")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("物理学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("电子信息科学与技术专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("自动化专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("光电信息科学专业")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("计算机科学与技术专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("大数据工程专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("物联网工程专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("软件工程专业")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("网络工程专业")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("化学工程与工艺专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("能源化学工程专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("制药工程专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("药物制剂专业")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("药学专业")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("地理科学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("地理信息科学专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("测绘工程专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("教育学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("心理学专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("学前教育学专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("文化产业管理专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("人文教育专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("生物技术专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("园林专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("英语专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("商务英语专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("日语专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("政治学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("哲学专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("汉语国际教育专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("钢琴专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("声乐专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("体育教育专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("社会体育专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("中国文学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("语言学专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("法学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("侦查学专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("工业设计专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("平面设计专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("土木工程专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("建筑环境与能源应用工程专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("广告学专业")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("新闻学专业")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("传播学专业")) {
				sql += " and substr(clazzid,4,1)='3'";
			}
	   }
		if(!(StringUtil.isEmpty(clazz))) {
			if(clazz.equals("1班")) {
				sql += " and substr(clazzid,5,1)='1'";
			}else if(clazz.equals("2班")) {
				sql += " and substr(clazzid,5,1)='2'";
			}else if(clazz.equals("3班")) {
				sql += " and substr(clazzid,5,1)='3'";
			}else if(clazz.equals("4班")) {
				sql += " and substr(clazzid,5,1)='4'";
			}
		}
		if(!(StringUtil.isEmpty(name))) {
				sql += " and name='"+name+"'";
		}
		if(!(StringUtil.isEmpty(sex))) {
			sql += " and sex='"+sex+"'";
		}
		ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
}
