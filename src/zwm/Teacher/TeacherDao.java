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
	//��ʦ��¼
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
	//��ӽ�ʦ��¼
	public boolean addTeacher(Teacher teacher){
		//��ѯid���ֵ
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
		//����м���һ��©�ĺ���Ĳ���
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
		//��ȡ�̹��������λ
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
	//�༭��ʦ��¼
	public boolean editTeacher(Teacher teacher) {
		//��ȡ�̹��������λ
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
	//ɾ����ʦ��¼
	public boolean deleteTeacher(String ids) {
		String sql = "delete from teacher where id in("+ids+")";
		return Update(sql);
	}
	//����ָ��id�Ľ�ʦ
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
	//�������н�ʦ
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���ҽ�ʦ��¼����
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
	//���Ҿ���ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���Ҿ���ѧԺ��ʦ��¼����
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
	//������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//������ѧԺ��ʦ��¼����
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
	//��������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//��������ѧԺ��ʦ��¼����
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
	//���Ҽ����ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���Ҽ����ѧԺ��ʦ��¼����
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
	//���һ�ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���һ�ѧԺ��ʦ��¼����
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
	//���һ���ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���һ���ѧԺ��ʦ��¼����
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
	//���ҽ���ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���ҽ���ѧԺ��ʦ��¼����
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
	//������ʷѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//������ʷѧԺ��ʦ��¼����
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
	//��������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//��������ѧԺ��ʦ��¼����
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
	//���������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���������ѧԺ��ʦ��¼����
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
	//��������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//��������ѧԺ��ʦ��¼����
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
	//���ҹ���ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���ҹ���ѧԺ��ʦ��¼����
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
	//��������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//��������ѧԺ��ʦ��¼����
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
	//��������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//��������ѧԺ��ʦ��¼����
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
	//������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//������ѧԺ��ʦ��¼����
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
	//���ҷ�ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//���ҷ�ѧԺ��ʦ��¼����
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
	//��������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//��������ѧԺ��ʦ��¼����
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
	//������ľѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//������ľѧԺ��ʦ��¼����
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
	//��������ѧԺ��ʦ��¼
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
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
	//��������ѧԺ��ʦ��¼����
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
	//�޸�����
	public boolean editPassword(Teacher teacher,String newPassword) {
		String sql = "update teacher set password = '"+newPassword+"' where id = " + teacher.getId();
		return Update(sql);
	}
	//����ͷ��
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
	//��ȡ��ʦ
	public Teacher getTeacher1(String tno) {
		Teacher teacher = new Teacher();
		String sql ="select * from teacher where tno='"+tno+"'";
		ResultSet resultset = Query(sql);
		try {
			//�����ݷ���һ������
			if(resultset.next()) {
				teacher.setPhoto(resultset.getBinaryStream("photo"));
				return teacher;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teacher;
	}
	//���ҽ�ʦ��¼
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
	//���ҽ��Ҽ�¼����
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