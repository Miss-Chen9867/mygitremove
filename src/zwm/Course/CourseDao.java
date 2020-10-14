package zwm.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zwm.Database.DBOperation;
import zwm.Login.String.StringUtil;
import zwm.Student.Student;
import zwm.page.Page;

public class CourseDao extends DBOperation{
		//����ָ��id�Ŀγ��Լ��ɼ�
		
		//�������пγ̼�¼
		public List<Course> getAllCourseList(Course course,Page page){
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					cl.setPrevious(resultSet.getString("previous"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}	
		//��ȡ���пγ̼�¼����
		public int getAllCourseListTotal(Course course){
			int total = 0;
			String sql = "select count(*) as total from course ";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select count(*) as total from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select count(*) as total from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select count(*) as total from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//�༭�γ�
		public boolean editCourse(Course course) {
			String first = course.getCourseId().charAt(0)+"";
			first += course.getCourseId().charAt(1);
			//���ҿγ��Ƿ����
			String sql1 = "select coursename from course where courseid like '"+first+"%'";
			ResultSet resultSet1 = Query(sql1);
			try {
			while(resultSet1.next()){
				Course cl = new Course();
				cl.setCourseName(resultSet1.getString("courseName"));
				if(course.getCourseName().equals(cl.getCourseName())) {
					return false;
				}
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//�޸ļ�¼
			String sql3 = "update course set courseid = '"+course.getCourseId()+"',coursename = '"+course.getCourseName()+"',credit = '"+course.getCredit()+"' where id ="+course.getId();
			return Update(sql3);
		}
		//��ӿγ�
		public boolean addCourse(Course course){
			String first = course.getCourseId();
			//��������γ̺�
			String sql2 = "select courseid from course where courseid like '"+course.getCourseId()+"%'";
			int max = 0;
			ResultSet resultSet2 = Query(sql2);
			try {
				while(resultSet2.next()) {
					String cd = resultSet2.getString("courseid");
					if(cd.charAt(3)=='0') {
						String m = cd.charAt(4)+"";
						if(Integer.parseInt(m)>max) {
							max = Integer.parseInt(m);
						}
					}else {
						String m = cd.charAt(3)+cd.charAt(4)+"";
						if(Integer.parseInt(m)>max) {
							max = Integer.parseInt(m);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			max++;
			if(max<10) {
				String i = course.getCourseId();
				i += "0";
				i += Integer.toString(max);
				course.setCourseId(i);
			}else {
				String i = course.getCourseId();
				i += Integer.toString(max);
				course.setCourseId(i);
			}
			
			//���ҿγ��Ƿ����
			String sql1 = "select coursename from course where courseid = '"+first+"%'";
			ResultSet resultSet1 = Query(sql1);
			try {
			while(resultSet1.next()){
				Course cl = new Course();
				cl.setCourseName(resultSet1.getString("courseName"));
				if(course.getCourseName().equals(cl.getCourseName())) {
					return false;
				}
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//��ѯid���ֵ
			boolean kkk = true;
			String ss = "select id from course";
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
						String s0 = "update course set id=id-1 where id="+j;
						Update(s0);
					}
					kkk = false;
					break;
				}
			}
			if(max1 == 0 || kkk)
				max1++;
			course.setId(max1);
			//��Ӽ�¼
			String sql3 = "insert into course values('"+course.getId()+"','"+course.getCourseId()+"','"+course.getCourseName()+"','"+course.getCredit()+"','"+course.getPrevious()+"') ";
			return Update(sql3);
		}
		//ɾ���γ�
		public boolean deleteCourse(String ids) {
			String sql = "delete from course where id in("+ids+")";
			return Update(sql);
		}
		//���Ҿ���ѧԺ�γ̼�¼
		public List<Course> getJinGuanCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '201%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setPrevious(resultSet.getString("previous"));
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}			
			return ret;
		}
		//���Ҿ���ѧԺ�γ̼�¼����
		public int getJinGuanCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '201%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//������ѧԺ�γ̼�¼
		public List<Course> getShuXueCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '202%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//������ѧԺ�γ̼�¼����
		public int getShuXueCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '202%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//��������ѧԺ�γ̼�¼
		public List<Course> getWuLiCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '203%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					cl.setPrevious(resultSet.getString("previous"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��������ѧԺ�γ̼�¼����
		public int getWuLiCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '203%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//���Ҽ����ѧԺ�γ̼�¼
		public List<Course> getJiSuanJiCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '204%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//���Ҽ����ѧԺ�γ̼�¼����
		public int getJiSuanJiCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '204%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//���һ�ѧԺ�γ̼�¼
		public List<Course> getHuaXueCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '205%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					cl.setPrevious(resultSet.getString("previous"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//���һ�ѧԺ�γ̼�¼����
		public int getHuaXueCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '205%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//���һ���ѧԺ�γ̼�¼
		public List<Course> getHuanJingCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '206%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//���һ���ѧԺ�γ̼�¼����
		public int getHuanJingCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '206%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//���ҽ���ѧԺ�γ̼�¼
		public List<Course> getJiaoYuCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '207%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//���ҽ���ѧԺ�γ̼�¼����
		public int getJiaoYuCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '207%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//������ʷѧԺ�γ̼�¼
		public List<Course> getLiShiCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '208%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//������ʷѧԺ�γ̼�¼����
		public int getLiShiCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '208%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//��������ѧԺ�γ̼�¼
		public List<Course> getShengMingCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '209%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��������ѧԺ�γ̼�¼����
		public int getShengMingCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '209%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//���������ѧԺ�γ̼�¼
		public List<Course> getWaiGuoYuCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '210%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//���������ѧԺ�γ̼�¼����
		public int getWaiGuoYuCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '210%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//��������ѧԺ�γ̼�¼
		public List<Course> getZhengZhiCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '211%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��������ѧԺ�γ̼�¼����
		public int getZhengZhiCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '211%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//���ҹ���ѧԺ�γ̼�¼
		public List<Course> getGuoJiCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '212%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//���ҹ���ѧԺ�γ̼�¼����
		public int getGuoJiCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '212%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//��������ѧԺ�γ̼�¼
		public List<Course> getYinYueCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '213%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��������ѧԺ�γ̼�¼����
		public int getYinYueCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '213%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//��������ѧԺ�γ̼�¼
		public List<Course> getTiYuCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '214%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��������ѧԺ�γ̼�¼����
		public int getTiYuCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '214%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//������ѧԺ�γ̼�¼
		public List<Course> getWenXueYuanCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '215%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//������ѧԺ�γ̼�¼����
		public int getWenXueYuanCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '215%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//���ҷ�ѧԺ�γ̼�¼
		public List<Course> getFaXueYuanCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '216%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//���ҷ�ѧԺ�γ̼�¼����
		public int getFaXueYuanCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '216%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//��������ѧԺ�γ̼�¼
		public List<Course> getMeiShuCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '217%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��������ѧԺ�γ̼�¼����
		public int getMeiShuCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '217%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//������ľѧԺ�γ̼�¼
		public List<Course> getTuMuCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '218%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//������ľѧԺ�γ̼�¼����
		public int getTuMuCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '218%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		//��������ѧԺ�γ̼�¼
		public List<Course> getXinWenCourseList(Course course, Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course where courseid like '219%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��������ѧԺ�γ̼�¼����
		public int getXinWenCourseListTotal(Course course) {
			int total = 0;
			String sql = "select count(*) as total from course where courseid like '219%'";
			if(!StringUtil.isEmpty(course.getCourseId())){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%'";
			}
			if(!StringUtil.isEmpty(course.getCourseName())){
				sql = "select * from course where coursename like '%" + course.getCourseName() + "%'";
			}
			if((!StringUtil.isEmpty(course.getCourseId()))&&(!StringUtil.isEmpty(course.getCourseName()))){
				sql = "select * from course where courseid like '%" + course.getCourseId() + "%' and coursename like '%" + course.getCourseName() + "%'";
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
		public boolean getCourse(String courseid) {
			String sql ="select courseid from course where courseid='"+courseid+"'";
			ResultSet resultset = Query(sql);
			try {
				//�����ݷ���һ������
				if(resultset.next()) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//û�����ݾͷ���NULL
			return false;
		}
		//��ȡ���п�
		public String havePrevious(String courseid) {
			String previous="";
			String sql = "select previous from course where courseid = '"+courseid+"'";
			ResultSet rs = Query(sql);
			try {
				if(rs.next()) {
					previous = rs.getString("previous");
					return previous;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return previous;
		}
		//�������п��Ƿ����
		public boolean findPrevious(String previous) {
			String sql = "select previous from course where previous='"+previous+"'";
			ResultSet rs = Query(sql);
			try {
				if(rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		//��ѯ�γ̼�¼
		public List<Course> FindCourseList(String academic, String coursename, String courseid, String credit,Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course";
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("����ͨ�ÿγ�")){
					sql += " and substr(courseid,1,3)='000'";
				}else if(academic.equals("��ͨ�ÿγ�")) {
					sql += " and substr(courseid,1,3)='100'";
				}else if(academic.equals("���������ѧԺ")) {
					sql += " and substr(courseid,1,3)='201'";
				}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
					sql += " and substr(courseid,1,3)='202'";
				}else if(academic.equals("��������ӹ���ѧԺ")) {
					sql += " and substr(courseid,1,3)='203'";
				}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
					sql += " and substr(courseid,1,3)='204'";
				}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
					sql += " and substr(courseid,1,3)='205'";
				}else if(academic.equals("������ѧ������ѧԺ")) {
					sql += " and substr(courseid,1,3)='206'";
				}else if(academic.equals("������ѧѧԺ")) {
					sql += " and substr(courseid,1,3)='207'";
				}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
					sql += " and substr(courseid,1,3)='208'";
				}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
					sql += " and substr(courseid,1,3)='209'";
				}else if(academic.equals("�����ѧԺ")) {
					sql += " and substr(courseid,1,3)='210'";
				}else if(academic.equals("�����빫������ѧԺ")) {
					sql += " and substr(courseid,1,3)='211'";
				}else if(academic.equals("���ʽ���ѧԺ")) {
					sql += " and substr(courseid,1,3)='212'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(courseid,1,3)='213'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(courseid,1,3)='214'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(courseid,1,3)='215'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(courseid,1,3)='216'";
				}else if(academic.equals("�������������ѧԺ")) {
					sql += " and substr(courseid,1,3)='217'";
				}else if(academic.equals("��ľ��������ѧԺ")) {
					sql += " and substr(courseid,1,3)='218'";
				}else if(academic.equals("�����봫��ѧԺ")) {
					sql += " and substr(courseid,1,3)='219'";
				}
			}
			if(!(StringUtil.isEmpty(courseid))) {
				sql += " and coursename='"+courseid+"'";
			}
			if(!(StringUtil.isEmpty(coursename))) {
					sql += " and coursename='"+coursename+"'";
			}
			if(!(StringUtil.isEmpty(credit))) {
				int credit1 = Integer.parseInt(credit);
				sql += " and credit='"+credit1+"'";
			}
			sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
			ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
			try {
				while(resultSet.next()){
					Course cl = new Course();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseId(resultSet.getString("courseid"));
					cl.setPrevious(resultSet.getString("previous"));
					cl.setCourseName(resultSet.getString("coursename"));
					cl.setCredit(resultSet.getInt("credit"));
					ret.add(cl);
				}
			} catch (SQLException e) {
			}
			return ret;
		}
		//��ȡ�γ̼�¼����
		public int getFindCourseListTotal(String academic, String coursename, String courseid, String credit) {
			int total = 0;
			String sql = "select count(*) as total from course";
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("����ͨ�ÿγ�")){
					sql += " and substr(courseid,1,3)='000'";
				}else if(academic.equals("��ͨ�ÿγ�")) {
					sql += " and substr(courseid,1,3)='100'";
				}else if(academic.equals("���������ѧԺ")) {
					sql += " and substr(courseid,1,3)='201'";
				}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
					sql += " and substr(courseid,1,3)='202'";
				}else if(academic.equals("��������ӹ���ѧԺ")) {
					sql += " and substr(courseid,1,3)='203'";
				}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
					sql += " and substr(courseid,1,3)='204'";
				}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
					sql += " and substr(courseid,1,3)='205'";
				}else if(academic.equals("������ѧ������ѧԺ")) {
					sql += " and substr(courseid,1,3)='206'";
				}else if(academic.equals("������ѧѧԺ")) {
					sql += " and substr(courseid,1,3)='207'";
				}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
					sql += " and substr(courseid,1,3)='208'";
				}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
					sql += " and substr(courseid,1,3)='209'";
				}else if(academic.equals("�����ѧԺ")) {
					sql += " and substr(courseid,1,3)='210'";
				}else if(academic.equals("�����빫������ѧԺ")) {
					sql += " and substr(courseid,1,3)='211'";
				}else if(academic.equals("���ʽ���ѧԺ")) {
					sql += " and substr(courseid,1,3)='212'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(courseid,1,3)='213'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(courseid,1,3)='214'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(courseid,1,3)='215'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(courseid,1,3)='216'";
				}else if(academic.equals("�������������ѧԺ")) {
					sql += " and substr(courseid,1,3)='217'";
				}else if(academic.equals("��ľ��������ѧԺ")) {
					sql += " and substr(courseid,1,3)='218'";
				}else if(academic.equals("�����봫��ѧԺ")) {
					sql += " and substr(courseid,1,3)='219'";
				}
			}
			if(!(StringUtil.isEmpty(courseid))) {
				sql += " and coursename='"+courseid+"'";
			}
			if(!(StringUtil.isEmpty(coursename))) {
					sql += " and coursename='"+coursename+"'";
			}
			if(!(StringUtil.isEmpty(credit))) {
				int credit1 = Integer.parseInt(credit);
				sql += " and credit='"+credit1+"'";
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