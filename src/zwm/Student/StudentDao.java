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
			//�����ݷ���һ������
			if(resultset.next()) {
				Student student = new Student();//����һ��ѧ������
				student.setId(resultset.getInt("id"));
				student.setSno(resultset.getString("sno"));//ѧ��
				student.setName(resultset.getString("name"));//����
				student.setPassword(resultset.getString("password"));//����
				student.setSex(resultset.getString("sex"));//�Ա�
				student.setAge(resultset.getInt("age"));//����
				student.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student.setBirthday(change(date));//����
				student.setHome(resultset.getString("home"));//��ͥסַ
				student.setPhone(resultset.getString("phone"));//��ϵ�绰
				return student;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//û�����ݾͷ���NULL
		return null;
	}	
	//�Ƿ���ڸ�ѧ��
	public boolean getStudent(String sno) {
		String sql ="select * from student where sno='"+sno+"'";
		ResultSet resultset = Query(sql);
		try {
			//�����ݷ���һ������
			if(resultset.next()) {
				Student student = new Student();//����һ��ѧ������
				student.setId(resultset.getInt("id"));
				student.setSno(resultset.getString("sno"));//ѧ��
				student.setName(resultset.getString("name"));//����
				student.setPassword(resultset.getString("password"));//����
				student.setSex(resultset.getString("sex"));//�Ա�
				student.setAge(resultset.getInt("age"));//����
				student.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student.setBirthday(change(date));//����
				student.setHome(resultset.getString("home"));//��ͥסַ
				student.setPhone(resultset.getString("phone"));//��ϵ�绰
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//��ȡѧ��
	public Student getStudent1(String sno) {
		Student student = new Student();
		String sql ="select photo from student where sno='"+sno+"'";
		ResultSet resultset = Query(sql);
		try {
			//�����ݷ���һ������
			if(resultset.next()) {
				student.setPhoto(resultset.getBinaryStream("photo"));
				return student;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}
	
	//����ѧ��
	public boolean addStudent(Student student){
		//�Ȳ�ѯ�༶�Ƿ����
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
		//��ѯid���ֵ
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
		//����м���һ��©�ĺ���Ĳ���
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
		//��ȡѧ��
		student.setSno(sno);
		if(student.getSno().charAt(3)=='6'||student.getSno().charAt(3)=='7'||student.getSno().charAt(3)=='8'||student.getSno().charAt(3)=='9') {
			student.setGratuated("��");
		}else {
			student.setGratuated("��");
		}
		//ѧ�������λ
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
	//�޸�
	public boolean editStudent(Student student) {
		String sql = "update student set sno='"+student.getSno()+"',name='"+student.getName()+"',password='"+student.getPassword()+"',sex='"+student.getSex()+"',age = '"+student.getAge()+"',birthday='"+student.getBirthday()+"',home='"+student.getHome()+"',phone='"+student.getPhone()+"',clazzid='"+student.getClazzid()+"',gratuated = '"+student.getGratuated()+"' where id="+student.getId();
		return Update(sql);
	}	
	
	//��ȡѧ��
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
	//����ѧ��(�ݶ�)
	public boolean UpStudent() {
		//��������������Ҫ��ȡԭ��ѧ��
		String sql = "update student set clazzid='��' where clazzid like '4%'";
		Update(sql);
		sql = "update student set clazzid='��' where clazzid like '4%'";
		Update(sql);
		sql = "update student set clazzid='��' where clazzid like '4%'";
		Update(sql);
		sql = "update student set clazzid='��' where clazzid like '4%'";
		Update(sql);
		return true;
	}
	//ɾ��
	public boolean deleteStudent(String snos,String ids) {
		
		//��ѯѧ���Ƿ���ѡ��
		String sql0 = "select * from selectedcourse where sno in("+snos+")";
		//���ѧ����ѡ�μ�¼��ɾ��ѧ����ѡ�μ�¼
		ResultSet rs0 = Query(sql0);
		try {
			if(rs0.next()) {
				sql0 = "delete from selectedcourse where sno in("+snos+")";
				Update(sql0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//��ѯѧ���Ƿ��гɼ�
		String sql1 = "select * from score where sno in("+snos+")";
		//ɾ��ѧ����ѡ�γɼ�
		ResultSet rs1 = Query(sql1);
		try {
			if(rs1.next()) {
				sql1 = "delete from score where sno in("+snos+")";
				Update(sql1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//ɾ��ѧ�������ѧ��
		String sql = "delete from student where id in("+ids+")";
		return Update(sql);
	}	

	//��������ѧ��
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
				Student student1 = new Student();//����һ��ѧ������
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//ѧ��
				student1.setName(resultset.getString("name"));//����
				student1.setPassword(resultset.getString("password"));//����
				student1.setSex(resultset.getString("sex"));//�Ա�
				student1.setAge(resultset.getInt("age"));//����
				student1.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//����
				student1.setHome(resultset.getString("home"));//��ͥסַ
				student1.setPhone(resultset.getString("phone"));//��ϵ�绰
				student1.setGratuated(resultset.getString("gratuated"));
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	//������Уѧ��
	public List<Student> getInStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where gratuated='��'";
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
				Student student1 = new Student();//����һ��ѧ������
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//ѧ��
				student1.setName(resultset.getString("name"));//����
				student1.setPassword(resultset.getString("password"));//����
				student1.setSex(resultset.getString("sex"));//�Ա�
				student1.setAge(resultset.getInt("age"));//����
				student1.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//����
				student1.setHome(resultset.getString("home"));//��ͥסַ
				student1.setPhone(resultset.getString("phone"));//��ϵ�绰
				student1.setGratuated(resultset.getString("gratuated"));//�Ƿ��ҵ
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}		
	
	//���Ҵ�һѧ��
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
				Student student1 = new Student();//����һ��ѧ������
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//ѧ��
				student1.setName(resultset.getString("name"));//����
				student1.setPassword(resultset.getString("password"));//����
				student1.setSex(resultset.getString("sex"));//�Ա�
				student1.setAge(resultset.getInt("age"));//����
				student1.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//����
				student1.setHome(resultset.getString("home"));//��ͥסַ
				student1.setPhone(resultset.getString("phone"));//��ϵ�绰
				student1.setGratuated(resultset.getString("gratuated"));//�Ƿ��ҵ
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	

	//���Ҵ��ѧ��
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
				Student student1 = new Student();//����һ��ѧ������
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//ѧ��
				student1.setName(resultset.getString("name"));//����
				student1.setPassword(resultset.getString("password"));//����
				student1.setSex(resultset.getString("sex"));//�Ա�
				student1.setAge(resultset.getInt("age"));//����
				student1.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//����
				student1.setHome(resultset.getString("home"));//��ͥסַ
				student1.setPhone(resultset.getString("phone"));//��ϵ�绰
				student1.setGratuated(resultset.getString("gratuated"));//�Ƿ��ҵ
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}		
	
	//���Ҵ���ѧ��
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
				Student student1 = new Student();//����һ��ѧ������
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//ѧ��
				student1.setName(resultset.getString("name"));//����
				student1.setPassword(resultset.getString("password"));//����
				student1.setSex(resultset.getString("sex"));//�Ա�
				student1.setAge(resultset.getInt("age"));//����
				student1.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//����
				student1.setHome(resultset.getString("home"));//��ͥסַ
				student1.setPhone(resultset.getString("phone"));//��ϵ�绰
				student1.setGratuated(resultset.getString("gratuated"));//�Ƿ��ҵ
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	
	
	//���Ҵ���ѧ��
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
				Student student1 = new Student();//����һ��ѧ������
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//ѧ��
				student1.setName(resultset.getString("name"));//����
				student1.setPassword(resultset.getString("password"));//����
				student1.setSex(resultset.getString("sex"));//�Ա�
				student1.setAge(resultset.getInt("age"));//����
				student1.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//����
				student1.setHome(resultset.getString("home"));//��ͥסַ
				student1.setPhone(resultset.getString("phone"));//��ϵ�绰
				student1.setGratuated(resultset.getString("gratuated"));//�Ƿ��ҵ
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	
	
	//���ұ�ҵѧ��
	public List<Student> getOutStudentList(Student student,Page page){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from student where gratuated='��'";
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
				Student student1 = new Student();//����һ��ѧ������
				student1.setId(resultset.getInt("id"));
				student1.setSno(resultset.getString("sno"));//ѧ��
				student1.setName(resultset.getString("name"));//����
				student1.setPassword(resultset.getString("password"));//����
				student1.setSex(resultset.getString("sex"));//�Ա�
				student1.setAge(resultset.getInt("age"));//����
				student1.setClazzid(resultset.getString("clazzid"));//���
				Date date = resultset.getDate("birthday");
				student1.setBirthday(change(date));//����
				student1.setHome(resultset.getString("home"));//��ͥסַ
				student1.setPhone(resultset.getString("phone"));//��ϵ�绰
				student1.setGratuated(resultset.getString("gratuated"));//�Ƿ��ҵ
				ret.add(student1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}		
	
	//����ͼ��
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
	//��ȡ����ѧ����¼������
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
		//��ȡ��У����¼������
				public int getInStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where gratuated='��'";
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
				//��ȡ��һѧ����¼������
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
				//��ȡ���ѧ����¼������
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
				//��ȡ����ѧ����¼������
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
				//��ȡ����ѧ����¼������
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
				//��ȡ��ҵ����¼������
				public int getOutStudentListTotal(Student student){
					int total = 0;
					String sql = "select count(*) as total from student where gratuated='��'";
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
		
	//Date����ת��ΪString����
	public String change(Date date) {
		String dt;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dt = sdf.format(date);
		return dt;
	}

	//�޸�����
	public boolean editPassword(Student student, String newPassword) {
		String sql = "update student set password = '"+newPassword+"' where id = " + student.getId();
		return Update(sql);
	}
	//��ѯѧ��
	public List<Student> FindStudentList(String sno, String grade, String academic, String major, String clazz,String name, String sex, Page page) {
			List<Student> ret = new ArrayList<Student>();
			String sql = "select * from student";
			if(!(StringUtil.isEmpty(sno))) {
				sql += " and sno='"+sno+"'";
			}
			if(!StringUtil.isEmpty(grade)){
				if(grade.equals("��һ")) {
					sql += " and clazzid like '1%'";
				}else if(grade.equals("���")) {
					sql += " and clazzid like '2%'";
				}else if(grade.equals("����")) {
					sql += " and clazzid like '3%'";
				}else if(grade.equals("����")) {
					sql += " and clazzid like '4%'";
				}
			}
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("���������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='01'";
				}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='02'";
				}else if(academic.equals("��������ӹ���ѧԺ")) {
					sql += " and substr(clazzid,2,2)='03'";
				}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='04'";
				}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='05'";
				}else if(academic.equals("������ѧ������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='06'";
				}else if(academic.equals("������ѧѧԺ")) {
					sql += " and substr(clazzid,2,2)='07'";
				}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
					sql += " and substr(clazzid,2,2)='08'";
				}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='09'";
				}else if(academic.equals("�����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='10'";
				}else if(academic.equals("�����빫������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='11'";
				}else if(academic.equals("���ʽ���ѧԺ")) {
					sql += " and substr(clazzid,2,2)='12'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='13'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='14'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='15'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='16'";
				}else if(academic.equals("�������������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='17'";
				}else if(academic.equals("��ľ��������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='18'";
				}else if(academic.equals("�����봫��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='19'";
				}
			}
			if(!StringUtil.isEmpty(major)){
				//���� ��������ƥ��
				if(major.equals("���̹���רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("�г�Ӫ��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("������Դרҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�������רҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("���ʾ�����ó��רҵ")) {
					sql += " and substr(clazzid,4,1)='6'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='7'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='8'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='9'";
				}else if(major.equals("��ѧ��Ӧ����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("ͳ��ѧרҵ ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("���ڹ���רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("���ڿ�ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("��Ϣ������ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("������Ϣ��ѧ�뼼��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("�Զ���רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�����Ϣ��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("�������ѧ�뼼��רҵ ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("�����ݹ���רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("����������רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�������רҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("���繤��רҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("��ѧ�����빤��רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("��Դ��ѧ����רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ҩ����רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("ҩ���Ƽ�רҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("ҩѧרҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("�����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("������Ϣ��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��湤��רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("ѧǰ����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�Ļ���ҵ����רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("���Ľ���רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("���＼��רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("԰��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("Ӣ��רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����Ӣ��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("������ʽ���רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��������רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("�������רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("�й���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ҵ���רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("ƽ�����רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ľ����רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("������������ԴӦ�ù���רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}
		   }
			if(!(StringUtil.isEmpty(clazz))) {
				if(clazz.equals("1��")) {
					sql += " and substr(clazzid,5,1)='1'";
				}else if(clazz.equals("2��")) {
					sql += " and substr(clazzid,5,1)='2'";
				}else if(clazz.equals("3��")) {
					sql += " and substr(clazzid,5,1)='3'";
				}else if(clazz.equals("4��")) {
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
					Student student1 = new Student();//����һ��ѧ������
					student1.setId(resultset.getInt("id"));
					student1.setSno(resultset.getString("sno"));//ѧ��
					student1.setName(resultset.getString("name"));//����
					student1.setPassword(resultset.getString("password"));//����
					student1.setSex(resultset.getString("sex"));//�Ա�
					student1.setAge(resultset.getInt("age"));//����
					student1.setClazzid(resultset.getString("clazzid"));//���
					Date date = resultset.getDate("birthday");
					student1.setBirthday(change(date));//����
					student1.setHome(resultset.getString("home"));//��ͥסַ
					student1.setPhone(resultset.getString("phone"));//��ϵ�绰
					student1.setGratuated(resultset.getString("gratuated"));//�Ƿ��ҵ
					ret.add(student1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
	}
	//���ز�ѯ����
	public int getFindStudentListTotal(String sno, String grade, String academic, String major, String clazz,String name, String sex) {
		int total = 0;
		String sql = "select count(*) as total from student";
		if(!(StringUtil.isEmpty(sno))) {
			sql += " and sno='"+sno+"'";
		}
		if(!StringUtil.isEmpty(grade)){
			if(grade.equals("��һ")) {
				sql += " and clazzid like '1%'";
			}else if(grade.equals("���")) {
				sql += " and clazzid like '2%'";
			}else if(grade.equals("����")) {
				sql += " and clazzid like '3%'";
			}else if(grade.equals("����")) {
				sql += " and clazzid like '4%'";
			}
		}
		if(!StringUtil.isEmpty(academic)){
			if(academic.equals("���������ѧԺ")) {
				sql += " and substr(clazzid,2,2)='01'";
			}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
				sql += " and substr(clazzid,2,2)='02'";
			}else if(academic.equals("��������ӹ���ѧԺ")) {
				sql += " and substr(clazzid,2,2)='03'";
			}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
				sql += " and substr(clazzid,2,2)='04'";
			}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
				sql += " and substr(clazzid,2,2)='05'";
			}else if(academic.equals("������ѧ������ѧԺ")) {
				sql += " and substr(clazzid,2,2)='06'";
			}else if(academic.equals("������ѧѧԺ")) {
				sql += " and substr(clazzid,2,2)='07'";
			}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
				sql += " and substr(clazzid,2,2)='08'";
			}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
				sql += " and substr(clazzid,2,2)='09'";
			}else if(academic.equals("�����ѧԺ")) {
				sql += " and substr(clazzid,2,2)='10'";
			}else if(academic.equals("�����빫������ѧԺ")) {
				sql += " and substr(clazzid,2,2)='11'";
			}else if(academic.equals("���ʽ���ѧԺ")) {
				sql += " and substr(clazzid,2,2)='12'";
			}else if(academic.equals("����ѧԺ")) {
				sql += " and substr(clazzid,2,2)='13'";
			}else if(academic.equals("����ѧԺ")) {
				sql += " and substr(clazzid,2,2)='14'";
			}else if(academic.equals("��ѧԺ")) {
				sql += " and substr(clazzid,2,2)='15'";
			}else if(academic.equals("��ѧԺ")) {
				sql += " and substr(clazzid,2,2)='16'";
			}else if(academic.equals("�������������ѧԺ")) {
				sql += " and substr(clazzid,2,2)='17'";
			}else if(academic.equals("��ľ��������ѧԺ")) {
				sql += " and substr(clazzid,2,2)='18'";
			}else if(academic.equals("�����봫��ѧԺ")) {
				sql += " and substr(clazzid,2,2)='19'";
			}
		}
		if(!StringUtil.isEmpty(major)){
			//���� ��������ƥ��
			if(major.equals("���̹���רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("�г�Ӫ��רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("������Դרҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("�������רҵ")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("���ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("���ʾ�����ó��רҵ")) {
				sql += " and substr(clazzid,4,1)='6'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='7'";
			}else if(major.equals("����רҵ")) {
				sql += " and substr(clazzid,4,1)='8'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='9'";
			}else if(major.equals("��ѧ��Ӧ����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("ͳ��ѧרҵ ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("���ڹ���רҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("���ڿ�ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("��Ϣ������ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("������Ϣ��ѧ�뼼��רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("�Զ���רҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("�����Ϣ��ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("�������ѧ�뼼��רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("�����ݹ���רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("����������רҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("�������רҵ")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("���繤��רҵ")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("��ѧ�����빤��רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("��Դ��ѧ����רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("��ҩ����רҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("ҩ���Ƽ�רҵ")) {
				sql += " and substr(clazzid,4,1)='4'";
			}else if(major.equals("ҩѧרҵ")) {
				sql += " and substr(clazzid,4,1)='5'";
			}else if(major.equals("�����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("������Ϣ��ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("��湤��רҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("ѧǰ����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("�Ļ���ҵ����רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("���Ľ���רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("���＼��רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("԰��רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("Ӣ��רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("����Ӣ��רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("����רҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("��ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("������ʽ���רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("����רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("����רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("��������רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("�������רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("�й���ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("��ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("���ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("��ҵ���רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("ƽ�����רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("��ľ����רҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("������������ԴӦ�ù���רҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("���ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='1'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='2'";
			}else if(major.equals("����ѧרҵ")) {
				sql += " and substr(clazzid,4,1)='3'";
			}
	   }
		if(!(StringUtil.isEmpty(clazz))) {
			if(clazz.equals("1��")) {
				sql += " and substr(clazzid,5,1)='1'";
			}else if(clazz.equals("2��")) {
				sql += " and substr(clazzid,5,1)='2'";
			}else if(clazz.equals("3��")) {
				sql += " and substr(clazzid,5,1)='3'";
			}else if(clazz.equals("4��")) {
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
