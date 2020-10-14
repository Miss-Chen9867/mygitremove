package zwm.SelectedCourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.util.StringUtil;

import zwm.Database.DBOperation;
import zwm.page.Page;

public class SelectedCourseDao extends DBOperation{
	//获取选课记录
	public List<SelectedCourse> getSelectedCourseList(SelectedCourse selectedCourse,Page page){
		List<SelectedCourse> ret = new ArrayList<SelectedCourse>();
		String sql = "select * from selectedcourse";
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				SelectedCourse cl = new SelectedCourse();
				cl.setId(resultSet.getInt("id"));
				cl.setCourseid(resultSet.getString("courseid"));
				cl.setSno(resultSet.getString("sno"));
				cl.setTno(resultSet.getString("tno"));
				cl.setScore(Double.parseDouble(resultSet.getString("score")));
				cl.setRemark(resultSet.getString("remark"));
				ret.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	
	//获取选课记录总条数
	public int getSelectedCourseListTotal(SelectedCourse selectedCourse){
		int total = 0;
		String sql = "select count(*) as total from selectedcourse";
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
	//查找课程是否已经被选择
	public boolean isSelected(String sno,String courseid) {
		String sql = "select * from selectedcourse where sno='"+sno+"' and courseid='"+courseid+"'";
		ResultSet query = Query(sql);
		try {
			if(query.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//添加选课记录
	public boolean addSelectedCourse(SelectedCourse selectedCourse) {
		//查询id最大值
		boolean kkk = true;
		String ss = "select id from selectedcourse";
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
					String s0 = "update selectedcourse set id=id-1 where id="+j;
					Update(s0);
				}
				kkk = false;
				break;
			}
		}
		if(max == 0 || kkk)
			max++;
		selectedCourse.setId(max);
		String sql1 = "insert into selectedcourse values('"+selectedCourse.getId()+"','"+selectedCourse.getSno()+"','"+selectedCourse.getTno()+"','"+selectedCourse.getCourseid()+"','"+selectedCourse.getScore()+"','"+selectedCourse.getRemark()+"')";
		return Update(sql1);
	}
	//删除选课记录
	public boolean deleteSelectedCourse(int id) {
		String sql = "delete from selectedcourse where id = " + id;
		return Update(sql);
	}
	//获取对应id的选课记录
	public SelectedCourse getSelectedCourse(int id){
		SelectedCourse ret = null;
		String sql = "select * from selectedcourse where id = " + id;
		ResultSet query = Query(sql);
		try {
			if(query.next()){
				ret = new SelectedCourse();
				ret.setId(query.getInt("id"));
				ret.setCourseid(query.getString("courseid"));
				ret.setSno(query.getString("sno"));
				ret.setTno(query.getString("tno"));
				ret.setRemark(query.getString("remark"));
				ret.setScore(query.getDouble("score"));
				return ret;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	//查询课程号
	public String getCourseid(String sno) {
		String courseid = null;
		String sql = "select courseid from selectedcourse where sno="+sno;
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				courseid = resultSet.getString("courseid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseid;
	}
	//获取个人课程信息
	public List<SelectedSelf> getMessageList(String courseid) {
		List<SelectedSelf> ret = new ArrayList<SelectedSelf>();
		String sql = "select course.courseid as courseId,course.coursename as courseName,course.credit as Credit,score.score,score.remark from course,score where course.courseid=score.courseid and course.courseid ="+courseid;
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				SelectedSelf cl = new SelectedSelf();
				cl.setCourseid(resultSet.getString("courseId"));
				cl.setCoursename(resultSet.getString("courseName"));
				cl.setCredit(Integer.parseInt(resultSet.getString("Credit")));
				cl.setScore(resultSet.getString("Score"));
				cl.setRemark(resultSet.getString("Remark"));
				if(cl.getScore()==null)
					cl.setScore("无");
				if(cl.getRemark()==null)
					cl.setRemark("无");
				ret.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	//获取个人课程信息记录
	public int getMessageTotal(String courseid){
		int total = 0;
		String sql = "select count(*) as total from score where courseid ="+courseid;
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
	//查找课程是否已经被要添加的教师授课（用于TeachCourse中）
	public boolean isTSelected(String tno,String courseid) {
			String sql = "select * from selectedcourse where tno='"+tno+"' and courseid='"+courseid+"'";
			ResultSet query = Query(sql);
			try {
				if(query.next()){
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
	}
	//查找是否有选择先选课
	public boolean selectedPrevious(String sno, String previous) {
		String sql = "select * from selectedcourse where sno='"+sno+"' and courseid='"+previous+"'";
		ResultSet rs = Query(sql);
		try {
			if(rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//修改成绩和评级
	public boolean editSelectedCourse(int id, Double score,String remark) {
		String sql = "update selectedcourse set score='"+score+"',remark='"+remark+"' where id="+id;
		return Update(sql);
	}
	//获取选课记录
	public List<SelectedCourse> getSelectedCourseList(SelectedCourse selectedCourse){
			List<SelectedCourse> ret = new ArrayList<SelectedCourse>();
			String sql = "select * from selectedcourse";
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					SelectedCourse cl = new SelectedCourse();
					cl.setId(resultSet.getInt("id"));
					cl.setCourseid(resultSet.getString("courseid"));
					cl.setSno(resultSet.getString("sno"));
					cl.setTno(resultSet.getString("tno"));
					cl.setScore(Double.parseDouble(resultSet.getString("score")));
					cl.setRemark(resultSet.getString("remark"));
					ret.add(cl);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
	}
	//查询选课记录
	public List<SelectedCourse> FindSelectedCourseList(String sno, String tno, String studentname, String teachername,String courseid, String coursename, String remark, Page page) {
		List<SelectedCourse> ret = new ArrayList<SelectedCourse>();
		String sql = "select * from selectedcourse";
		if(!(StringUtil.isEmpty(sno))) {
			sql += " and sno='"+sno+"'";
		}
		if(!(StringUtil.isEmpty(tno))) {
			sql += " and tno='"+tno+"'";
		}
		if(!(StringUtil.isEmpty(studentname))) {
			sql += " and sno='"+studentname+"'";
		}
		if(!(StringUtil.isEmpty(teachername))) {
			sql += " and tno='"+teachername+"'";
		}
		if(!(StringUtil.isEmpty(courseid))) {
			sql += " and courseid='"+courseid+"'";
		}
		if(!(StringUtil.isEmpty(coursename))) {
			sql += " and courseid='"+coursename+"'";
		}
		if(!(StringUtil.isEmpty(remark))) {
			sql += " and remark='"+remark+"'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				SelectedCourse cl = new SelectedCourse();
				cl.setId(resultSet.getInt("id"));
				cl.setCourseid(resultSet.getString("courseid"));
				cl.setSno(resultSet.getString("sno"));
				cl.setTno(resultSet.getString("tno"));
				cl.setScore(Double.parseDouble(resultSet.getString("score")));
				cl.setRemark(resultSet.getString("remark"));
				ret.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	//查询选课记录条数
	public int FindSelectedCourseListTotal(String sno, String tno, String studentname, String teachername,String courseid, String coursename, String remark) {
		int total = 0;
		String sql = "select count(*) as total from selectedcourse";
		if(!(StringUtil.isEmpty(sno))) {
			sql += " and sno='"+sno+"'";
		}
		if(!(StringUtil.isEmpty(tno))) {
			sql += " and tno='"+tno+"'";
		}
		if(!(StringUtil.isEmpty(studentname))) {
			sql += " and sno='"+studentname+"'";
		}
		if(!(StringUtil.isEmpty(teachername))) {
			sql += " and tno='"+teachername+"'";
		}
		if(!(StringUtil.isEmpty(courseid))) {
			sql += " and courseid='"+courseid+"'";
		}
		if(!(StringUtil.isEmpty(coursename))) {
			sql += " and courseid='"+coursename+"'";
		}
		if(!(StringUtil.isEmpty(remark))) {
			sql += " and remark='"+remark+"'";
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
	//获取已选课程
	public List<Map<String, Object>> getScoreSelfList(String sno) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		String sql = "select teachcourse.*,selectedcourse.*,teacher.name as teacherName,course.coursename as courseName from teachcourse,selectedcourse,teacher,course where selectedcourse.sno='"+sno+"' and selectedcourse.tno=teachcourse.tno and selectedcourse.tno=teacher.tno and selectedcourse.courseid=course.courseid and selectedcourse.courseid = teachcourse.courseid";
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Map<String, Object> s = new HashMap<String, Object>();
				s.put("id",resultSet.getInt("id"));
				s.put("teacherName",resultSet.getString("teacherName"));
				s.put("courseName",resultSet.getString("courseName"));
				s.put("classroom", resultSet.getString("Classroom"));
				String weeks = "第 "+resultSet.getString("Weeks").charAt(0);
				if(resultSet.getString("weeks").charAt(1)!='-') {
					weeks+=resultSet.getString("weeks").charAt(1);
					weeks+="-";
					weeks+=resultSet.getString("weeks").charAt(3);
					weeks+=resultSet.getString("weeks").charAt(4);
					weeks+=" 周 星期 ";
					weeks+=resultSet.getString("weeks").charAt(6);
				}else {
					weeks+="-";
					weeks+=resultSet.getString("weeks").charAt(2);
					if(resultSet.getString("weeks").charAt(3)!=',') {
						weeks+=resultSet.getString("weeks").charAt(3);
						weeks+=" 周 星期 ";
						weeks+=resultSet.getString("weeks").charAt(5);
					}else {
						weeks+=" 周 星期 ";
						weeks+=resultSet.getString("weeks").charAt(4);
					}
				}
				s.put("weeks", weeks);
				String numbers = "第 "+resultSet.getString("Numbers")+" 节";
				s.put("numbers", numbers);
				s.put("score", resultSet.getDouble("score"));
				s.put("remark", resultSet.getString("remark"));
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	//获取已选课程的学生信息
	public List<Map<String, Object>> getTeachStudentList(String tno) {
			List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
			String sql = "select selectedcourse.*,student.sno as Sno,student.name as studentName,course.courseid as Courseid,course.coursename as courseName from selectedcourse,student,course where selectedcourse.tno='"+tno+"' and selectedcourse.sno=student.sno and selectedcourse.courseid=course.courseid";
			ResultSet resultSet = Query(sql);
			try {
				while(resultSet.next()){
					Map<String, Object> s = new HashMap<String, Object>();
					s.put("id",resultSet.getInt("id"));
					s.put("sno",resultSet.getString("Sno"));
					s.put("studentname",resultSet.getString("studentName"));
					s.put("courseid",resultSet.getString("Courseid"));
					s.put("coursename",resultSet.getString("courseName"));
					s.put("score", resultSet.getDouble("score"));
					s.put("remark", resultSet.getString("remark"));
					ret.add(s);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
		}
}
