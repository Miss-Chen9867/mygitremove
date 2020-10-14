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
		//查找指定id的课程以及成绩
		
		//查找所有课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//获取所有课程记录条数
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
		//编辑课程
		public boolean editCourse(Course course) {
			String first = course.getCourseId().charAt(0)+"";
			first += course.getCourseId().charAt(1);
			//查找课程是否存在
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
			//修改记录
			String sql3 = "update course set courseid = '"+course.getCourseId()+"',coursename = '"+course.getCourseName()+"',credit = '"+course.getCredit()+"' where id ="+course.getId();
			return Update(sql3);
		}
		//添加课程
		public boolean addCourse(Course course){
			String first = course.getCourseId();
			//添加完整课程号
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
			
			//查找课程是否存在
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
			//查询id最大值
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
			//如果中间有一个漏的后面的补上
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
			//添加记录
			String sql3 = "insert into course values('"+course.getId()+"','"+course.getCourseId()+"','"+course.getCourseName()+"','"+course.getCredit()+"','"+course.getPrevious()+"') ";
			return Update(sql3);
		}
		//删除课程
		public boolean deleteCourse(String ids) {
			String sql = "delete from course where id in("+ids+")";
			return Update(sql);
		}
		//查找经管学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找经管学院课程记录条数
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
		//查找数学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找数学院课程记录条数
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
		//查找物理学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找物理学院课程记录条数
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
		//查找计算机学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找计算机学院课程记录条数
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
		//查找化学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找化学院课程记录条数
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
		//查找环境学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找环境学院课程记录条数
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
		//查找教育学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找教育学院课程记录条数
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
		//查找历史学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找历史学院课程记录条数
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
		//查找生命学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找生命学院课程记录条数
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
		//查找外国语学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找外国语学院课程记录条数
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
		//查找政治学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找政治学院课程记录条数
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
		//查找国际学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找国际学院课程记录条数
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
		//查找音乐学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找音乐学院课程记录条数
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
		//查找体育学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找体育学院课程记录条数
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
		//查找文学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找文学院课程记录条数
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
		//查找法学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找法学院课程记录条数
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
		//查找美术学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找美术学院课程记录条数
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
		//查找土木学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找土木学院课程记录条数
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
		//查找新闻学院课程记录
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//查找新闻学院课程记录条数
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
				//有内容返回一个对象
				if(resultset.next()) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//没有内容就返回NULL
			return false;
		}
		//获取先行课
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
		//查找先行课是否存在
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
		//查询课程记录
		public List<Course> FindCourseList(String academic, String coursename, String courseid, String credit,Page page) {
			List<Course> ret = new ArrayList<Course>();
			String sql = "select * from course";
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("文理通用课程")){
					sql += " and substr(courseid,1,3)='000'";
				}else if(academic.equals("理工通用课程")) {
					sql += " and substr(courseid,1,3)='100'";
				}else if(academic.equals("经济与管理学院")) {
					sql += " and substr(courseid,1,3)='201'";
				}else if(academic.equals("数学与统计学院")) {
					sql += " and substr(courseid,1,3)='202'";
				}else if(academic.equals("物理与电子工程学院")) {
					sql += " and substr(courseid,1,3)='203'";
				}else if(academic.equals("计算机科学与技术学院")) {
					sql += " and substr(courseid,1,3)='204'";
				}else if(academic.equals("化学与制药工程学院")) {
					sql += " and substr(courseid,1,3)='205'";
				}else if(academic.equals("环境科学与旅游学院")) {
					sql += " and substr(courseid,1,3)='206'";
				}else if(academic.equals("教育科学学院")) {
					sql += " and substr(courseid,1,3)='207'";
				}else if(academic.equals("历史文化学院")) {
					sql += " and substr(courseid,1,3)='208'";
				}else if(academic.equals("生命科学与技术学院")) {
					sql += " and substr(courseid,1,3)='209'";
				}else if(academic.equals("外国语学院")) {
					sql += " and substr(courseid,1,3)='210'";
				}else if(academic.equals("政治与公共管理学院")) {
					sql += " and substr(courseid,1,3)='211'";
				}else if(academic.equals("国际教育学院")) {
					sql += " and substr(courseid,1,3)='212'";
				}else if(academic.equals("音乐学院")) {
					sql += " and substr(courseid,1,3)='213'";
				}else if(academic.equals("体育学院")) {
					sql += " and substr(courseid,1,3)='214'";
				}else if(academic.equals("文学院")) {
					sql += " and substr(courseid,1,3)='215'";
				}else if(academic.equals("法学院")) {
					sql += " and substr(courseid,1,3)='216'";
				}else if(academic.equals("美术与艺术设计学院")) {
					sql += " and substr(courseid,1,3)='217'";
				}else if(academic.equals("土木建筑工程学院")) {
					sql += " and substr(courseid,1,3)='218'";
				}else if(academic.equals("新闻与传播学院")) {
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
			sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
		//获取课程记录条数
		public int getFindCourseListTotal(String academic, String coursename, String courseid, String credit) {
			int total = 0;
			String sql = "select count(*) as total from course";
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("文理通用课程")){
					sql += " and substr(courseid,1,3)='000'";
				}else if(academic.equals("理工通用课程")) {
					sql += " and substr(courseid,1,3)='100'";
				}else if(academic.equals("经济与管理学院")) {
					sql += " and substr(courseid,1,3)='201'";
				}else if(academic.equals("数学与统计学院")) {
					sql += " and substr(courseid,1,3)='202'";
				}else if(academic.equals("物理与电子工程学院")) {
					sql += " and substr(courseid,1,3)='203'";
				}else if(academic.equals("计算机科学与技术学院")) {
					sql += " and substr(courseid,1,3)='204'";
				}else if(academic.equals("化学与制药工程学院")) {
					sql += " and substr(courseid,1,3)='205'";
				}else if(academic.equals("环境科学与旅游学院")) {
					sql += " and substr(courseid,1,3)='206'";
				}else if(academic.equals("教育科学学院")) {
					sql += " and substr(courseid,1,3)='207'";
				}else if(academic.equals("历史文化学院")) {
					sql += " and substr(courseid,1,3)='208'";
				}else if(academic.equals("生命科学与技术学院")) {
					sql += " and substr(courseid,1,3)='209'";
				}else if(academic.equals("外国语学院")) {
					sql += " and substr(courseid,1,3)='210'";
				}else if(academic.equals("政治与公共管理学院")) {
					sql += " and substr(courseid,1,3)='211'";
				}else if(academic.equals("国际教育学院")) {
					sql += " and substr(courseid,1,3)='212'";
				}else if(academic.equals("音乐学院")) {
					sql += " and substr(courseid,1,3)='213'";
				}else if(academic.equals("体育学院")) {
					sql += " and substr(courseid,1,3)='214'";
				}else if(academic.equals("文学院")) {
					sql += " and substr(courseid,1,3)='215'";
				}else if(academic.equals("法学院")) {
					sql += " and substr(courseid,1,3)='216'";
				}else if(academic.equals("美术与艺术设计学院")) {
					sql += " and substr(courseid,1,3)='217'";
				}else if(academic.equals("土木建筑工程学院")) {
					sql += " and substr(courseid,1,3)='218'";
				}else if(academic.equals("新闻与传播学院")) {
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