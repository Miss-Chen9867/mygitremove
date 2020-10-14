package zwm.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zwm.Database.DBOperation;
import zwm.Login.String.StringUtil;
import zwm.Student.Student;
import zwm.page.Page;

public class TeacherDao extends DBOperation {
	//教师登录
	public Teacher login(String name ,String password){
		String sql = "select * from teacher where name = '"+name+"' and password = '"+password+"'";
		ResultSet resultSet = Query(sql);
		try {
			if(resultSet.next()){
				Teacher teacher = new Teacher();
				teacher.setId(resultSet.getInt("id"));
				teacher.setTno(resultSet.getString("tno"));
				teacher.setName(resultSet.getString("name"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setSex(resultSet.getString("sex"));
				teacher.setPhone(resultSet.getString("phone"));
				teacher.setRztime(resultSet.getString("rztime"));
				teacher.setAcademic(resultSet.getString("academic"));
				teacher.setMajor(resultSet.getString("major"));
				return teacher;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//添加教师记录
	public boolean addTeacher(Teacher teacher){
		//查询id最大值
		boolean kkk = true;
		String ss = "select id from teacher";
		int max = 0;
		int[] flag = new int[99999999];
		for(int i=0;i<99999999;i++) {
			flag[i] = 0;
		}
		ResultSet resultSet = Query(ss);
		try {
			while(resultSet.next()){
				flag[resultSet.getInt("id")] = 1;
				if(resultSet.getInt("id")>max) {
					max = resultSet.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//如果中间有一个漏的后面的补上
		for(int i=1;i<=max;i++) {
			if(flag[i]!=1) {
				for(int j=i+1;j<=max;j++) {
					String s0 = "update teacher set id=id-1 where id="+j;
					Update(s0);
				}
				kkk = false;
				break;
			}
		}
		if(max == 0 || kkk)
			max++;
		teacher.setId(max);
		//获取教工号最后两位
		int max1 = 1;
		String two = teacher.getTno().charAt(0)+"";
		two += teacher.getTno().charAt(1);
		two += "%";
		String tql = "select tno from teacher where tno like '"+two+"'";
		ResultSet t = Query(tql);
		try {
			while(t.next()) {
				max1++;
			}
			if(max1>0&&max1<10) {
				String k = teacher.getTno();
				k += "0";
				k += Integer.toString(max1);
				teacher.setTno(k);
			}else {
				String k = teacher.getTno();
				k += Integer.toString(max1);
				teacher.setTno(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql1 = "insert into teacher values('"+teacher.getId()+"','"+teacher.getTno()+"','"+teacher.getName()+"','"+teacher.getPassword()+"','"+teacher.getSex()+"','"+teacher.getPhone()+"','"+teacher.getRztime()+"','"+teacher.getAcademic()+"','"+teacher.getMajor()+"','')";
		return Update(sql1);
	}	
	//编辑教师记录
	public boolean editTeacher(Teacher teacher) {
		//获取教工号最后两位
		int max1 = 1;
		String two = teacher.getTno().charAt(0)+"";
		two += teacher.getTno().charAt(1);
		two += "%";
		String tql = "select tno from teacher where tno like '"+two+"'";
		ResultSet t = Query(tql);
		try {
			while(t.next()) {
				max1++;
			}
			if(max1>0&&max1<10) {
				String k = teacher.getTno();
				k += "0";
				k += Integer.toString(max1);
				teacher.setTno(k);
			}else {
				String k = teacher.getTno();
				k += Integer.toString(max1);
				teacher.setTno(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql ="update teacher set tno='"+teacher.getTno()+"',name='"+teacher.getName()+"',password='"+teacher.getPassword()+"',sex='"+teacher.getSex()+"',rztime='"+teacher.getRztime()+"',phone='"+teacher.getPhone()+"',academic='"+teacher.getAcademic()+"',major = '"+teacher.getMajor()+"' where id="+teacher.getId();
		return Update(sql);
	}
	//删除教师记录
	public boolean deleteTeacher(String ids) {
		String sql = "delete from teacher where id in("+ids+")";
		return Update(sql);
	}
	//查找指定id的教师
	public Teacher getTeacher(int id){
		String sql = "select * from teacher where id = " + id;
		Teacher teacher = null;
		ResultSet resultSet = Query(sql);
		try {
			if(resultSet.next()){
				teacher = new Teacher();

				return teacher;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}
	//查找所有教师
	public List<Teacher> getAllTeacherList(Teacher teacher,Page page){
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找教师记录条数
	public int getAllTeacherListTotal(Teacher teacher){
		int total = 0;
		String sql = "select count(*) as total from teacher";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找经管学院教师记录
	public List<Teacher> getJinGuanTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '01%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找经管学院教师记录条数
	public int getJinGuanTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '01%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找数学院教师记录
	public List<Teacher> getShuXueTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '02%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找数学院教师记录条数
	public int getShuXueTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '02%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找物理学院教师记录
	public List<Teacher> getWuLiTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '03%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找物理学院教师记录条数
	public int getWuLiTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '03%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找计算机学院教师记录
	public List<Teacher> getJiSuanJiTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '04%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找计算机学院教师记录条数
	public int getJiSuanJiTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '04%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找化学院教师记录
	public List<Teacher> getHuaXueTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '05%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找化学院教师记录条数
	public int getHuaXueTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '05%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找环境学院教师记录
	public List<Teacher> getHuanJingTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '06%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找环境学院教师记录条数
	public int getHuanJingTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '06%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找教育学院教师记录
	public List<Teacher> getJiaoYuTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '07%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找教育学院教师记录条数
	public int getJiaoYuTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '07%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找历史学院教师记录
	public List<Teacher> getLiShiTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '08%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找历史学院教师记录条数
	public int getLiShiTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '08%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找生命学院教师记录
	public List<Teacher> getShengMingTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '09%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找生命学院教师记录条数
	public int getShengMingTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '09%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找外国语学院教师记录
	public List<Teacher> getWaiGuoYuTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '10%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找外国语学院教师记录条数
	public int getWaiGuoYuTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '10%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找政治学院教师记录
	public List<Teacher> getZhengZhiTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '11%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找政治学院教师记录条数
	public int getZhengZhiTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '11%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找国际学院教师记录
	public List<Teacher> getGuoJiTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '12%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找国际学院教师记录条数
	public int getGuoJiTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '12%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找音乐学院教师记录
	public List<Teacher> getYinYueTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '13%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找音乐学院教师记录条数
	public int getYinYueTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '13%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找体育学院教师记录
	public List<Teacher> getTiYuTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '14%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找体育学院教师记录条数
	public int getTiYuTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '14%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找文学院教师记录
	public List<Teacher> getWenXueYuanTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '15%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找文学院教师记录条数
	public int getWenXueYuanTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '15%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找法学院教师记录
	public List<Teacher> getFaXueYuanTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '16%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找法学院教师记录条数
	public int getFaXueYuanTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '16%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找美术学院教师记录
	public List<Teacher> getMeiShuTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '17%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找美术学院教师记录条数
	public int getMeiShuTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '17%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找土木学院教师记录
	public List<Teacher> getTuMuTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '18%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找土木学院教师记录条数
	public int getTuMuTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '18%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//查找新闻学院教师记录
	public List<Teacher> getXinWenTeacherList(Teacher teacher, Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher where tno like '19%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select * from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select * from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select * from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}
	//查找新闻学院教师记录条数
	public int getXinWenTeacherListTotal(Teacher teacher) {
		int total = 0;
		String sql = "select count(*) as total from teacher where tno like '19%'";
		if(!StringUtil.isEmpty(teacher.getTno())) {
			sql = "select count(*) as total from teacher where tno like '%" +teacher.getTno()+ "%'";
		}
		if(!StringUtil.isEmpty(teacher.getName())) {
			sql = "select count(*) as total from teacher where name like '%" +teacher.getName()+ "%'";
		}
		if((!StringUtil.isEmpty(teacher.getTno()))&&(!StringUtil.isEmpty(teacher.getName()))){
			sql = "select count(*) as total from teacher where tno like '%" + teacher.getTno() + "%' and name like '%" + teacher.getName() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	//修改密码
	public boolean editPassword(Teacher teacher,String newPassword) {
		String sql = "update teacher set password = '"+newPassword+"' where id = " + teacher.getId();
		return Update(sql);
	}
	//设置头像
	public boolean setTeacherPhoto(Teacher teacher) {
		String sql = "update teacher set photo = ? where tno = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setBinaryStream(1, teacher.getPhoto());
			prepareStatement.setString(2, teacher.getTno());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Update(sql);
	}
	//获取老师
	public Teacher getTeacher1(String tno) {
		Teacher teacher = new Teacher();
		String sql ="select * from teacher where tno='"+tno+"'";
		ResultSet resultset = Query(sql);
		try {
			//有内容返回一个对象
			if(resultset.next()) {
				teacher.setPhoto(resultset.getBinaryStream("photo"));
				return teacher;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teacher;
	}
	//查找教师记录
	public List<Teacher> FindTeacherList(String tno, String academic, String major, String name, String sex,Page page) {
		List<Teacher> ret = new ArrayList<Teacher>();
		String sql = "select * from teacher";
		if(!(StringUtil.isEmpty(tno))) {
			sql += " and tno='"+tno+"'";
		}
		if(!(StringUtil.isEmpty(major))) {
			sql += " and major='"+major+"'";
		}
		if(!(StringUtil.isEmpty(name))) {
			sql += " and name='"+name+"'";
		}
		if(!(StringUtil.isEmpty(sex))) {
			sql += " and sex='"+sex+"'";
		}
		if(!StringUtil.isEmpty(academic)){
			sql += " and academic='"+academic+"'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				Teacher cl = new Teacher();
				cl.setId(resultSet.getInt("id"));
				cl.setTno(resultSet.getString("tno"));
				cl.setName(resultSet.getString("name"));
				cl.setPassword(resultSet.getString("password"));
				cl.setSex(resultSet.getString("sex"));
				cl.setPhone(resultSet.getString("phone"));
				cl.setRztime(resultSet.getString("rztime"));
				cl.setAcademic(resultSet.getString("academic"));
				cl.setMajor(resultSet.getString("major"));
				ret.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	//查找教室记录条数
	public int getFindTeacherListTotal(String tno, String academic, String major, String name, String sex) {
		int total = 0;
		String sql = "select count(*) as total from teacher";
		if(!(StringUtil.isEmpty(tno))) {
			sql += " and tno='"+tno+"'";
		}
		if(!(StringUtil.isEmpty(major))) {
			sql += " and major='"+major+"'";
		}
		if(!(StringUtil.isEmpty(name))) {
			sql += " and name='"+name+"'";
		}
		if(!(StringUtil.isEmpty(sex))) {
			sql += " and sex='"+sex+"'";
		}
		if(!StringUtil.isEmpty(academic)){
			sql += " and academic='"+academic+"'";
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