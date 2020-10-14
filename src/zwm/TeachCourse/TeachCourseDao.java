package zwm.TeachCourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.util.StringUtil;

import zwm.Database.DBOperation;
import zwm.Teacher.Teacher;
import zwm.page.Page;

public class TeachCourseDao extends DBOperation{
	//获取指定信息
	public List<Map<String, Object>> getScoreList() {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		String sql = "select teachcourse.*,teacher.name as teacherName,course.coursename as courseName,course.previous as Previous from teachcourse,teacher,course where teachcourse.tno=teacher.tno and teachcourse.courseid=course.courseid ";
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Map<String, Object> s = new HashMap<String, Object>();
				s.put("id",resultSet.getInt("id"));
				s.put("tno",resultSet.getString("tno"));
				s.put("teacherName",resultSet.getString("teacherName"));
				s.put("courseid",resultSet.getString("courseid"));
				s.put("courseName",resultSet.getString("courseName"));
				s.put("previous",resultSet.getString("Previous"));
				s.put("classroom", resultSet.getString("classroom"));
				String weeks = "第 "+resultSet.getString("weeks").charAt(0);
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
				String numbers = "第 "+resultSet.getString("numbers")+" 节";
				s.put("numbers", numbers);
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	//获取授课记录
	public List<TeachCourse> getTeachCourseList(TeachCourse teachCourse,Page page){
		List<TeachCourse> ret = new ArrayList<TeachCourse>();
		String sql = "select * from teachcourse";
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		sql = sql.replaceFirst("and", "where");
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				TeachCourse cl = new TeachCourse();
				cl.setId(resultSet.getInt("id"));
				cl.setCourseid(resultSet.getString("courseid"));
				cl.setTno(resultSet.getString("tno"));
				cl.setNumbers("第 "+resultSet.getString("numbers")+" 节");
				String weeks = "第 "+resultSet.getString("weeks").charAt(0);
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
				cl.setWeeks(weeks);
				cl.setClassroom(resultSet.getString("classroom"));
				ret.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}	
	//获取授课记录总条数
	public int getTeachCourseListTotal(TeachCourse teachCourse){
		int total = 0;
		String sql = "select count(*) as total from teachcourse";
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
	//查询是否冲突
	public boolean isSelected(String tno,String classroom,int weeks_1,int weeks_2,int weeks_3,int numbers_1,int numbers_2) {
		//查询时间段是否相同
		String sql1 = "select classroom from teachcourse where classroom='"+classroom+"'";
		ResultSet r1 = Query(sql1);
		try {
			if(r1.next()) {
				//如果地点相同，再看看周数相不相同,，如果是包含状态，则返回错误
				//获取周数
				String sql2 = "select * from teachcourse where classroom='"+classroom+"'";
				String sql3 = "select * from teachcourse where tno='"+tno+"'";
				ResultSet r2 = Query(sql2);
				ResultSet r3 = Query(sql3);
				while(r2.next()||r3.next()) { 
					//获取周数
					String w = r2.getString("weeks");
					String n = r2.getString("numbers");
					String w11;
					String w22;
					String w33;
					if(w.charAt(0)!='0') w11 = w.charAt(0)+w.charAt(1)+"";
					else w11 = w.charAt(1)+"";
					if(w.charAt(3)!='0') w22 = w.charAt(3)+w.charAt(4)+"";
					else w22 = w.charAt(4)+"";
					w33 = w.charAt(6)+"";
					int w1 = Integer.parseInt(w11);
					int w2 = Integer.parseInt(w22);
					int w3 = Integer.parseInt(w33);
					//是否包含
					if((weeks_1>=w1 && weeks_1<=w2 && weeks_2<=w1 &&weeks_2<=w2)||(weeks_1<=w1 && weeks_1<=w2 && weeks_2>=w1 && weeks_2>=w2)) {
						//如果星期重复，查看节数s
						if(weeks_3==w3) {
							String n11 = n.charAt(0)+"";
							String n22 = n.charAt(2)+"";
							int n1 = Integer.parseInt(n11);
							int n2 = Integer.parseInt(n22);
							if((numbers_1>=n1 && numbers_1<=n2 && numbers_2<=n1 &&numbers_2<=n2)||(numbers_1<=n1 && numbers_1<=n2 && numbers_2>=n1 && numbers_2>=n2)) {
								return true;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//查询是否已添加
	public boolean isTeach(String tno,String courseid) {
		String sql = "select * from teachcourse where tno='"+tno+"' and courseid='"+courseid+"'";
		ResultSet r = Query(sql);
		try {
			if(r.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//添加授课
	public boolean addTeachCourse(TeachCourse teachCourse) {
		//查询id最大值
				boolean kkk = true;
				String ss = "select id from teachcourse";
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
							String s0 = "update teachcourse set id=id-1 where id="+j;
							Update(s0);
						}
						kkk = false;
						break;
					}
				}
				if(max == 0 || kkk)
					max++;
				teachCourse.setId(max);
		String sql = "insert into teachcourse values('"+teachCourse.getId()+"','"+teachCourse.getTno()+"','"+teachCourse.getCourseid()+"','"+teachCourse.getClassroom()+"','"+teachCourse.getWeeks()+"','"+teachCourse.getNumbers()+"')";
		return Update(sql);
	}
	//查找该教师是否有授课（用于TeachCourse中）
	public boolean haveTeach(String tno,String courseid) {
			String sql = "select * from teachcourse where tno='"+tno+"' and courseid='"+courseid+"'";
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
	//编辑授课 可以编辑上课地点
	public boolean editTeachCourse(TeachCourse teachCourse) {
		String sql = "update teachcourse set classroom='"+teachCourse.getClassroom()+"',weeks='"+teachCourse.getWeeks()+"',numbers='"+teachCourse.getNumbers()+"' where id="+teachCourse.getId();
		return Update(sql);
	}
	//删除授课
	public boolean deleteTeachCourse(int id) {
		String sql = "delete from teachcourse where id="+id;
		return Update(sql);
	}
	//查看有无授课信息
	public boolean getTeachCourse(String tno,String courseid) {
		String sql ="select * from teachcourse where tno='"+tno+"'and courseid='"+courseid+"'";
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
	public List<TeachCourse> FindTeachCourseList(String tno, String teachername, String courseid, String coursename,String classroom_building, String classroom_block, String classroom_room, String weeks, String weeks_3, String numbers,Page page) {
		List<TeachCourse> ret = new ArrayList<TeachCourse>();
		String sql = "select * from teachcourse";
		if(!(StringUtil.isEmpty(tno))) {
			sql += " and tno='"+tno+"'";
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
		if(!StringUtil.isEmpty(classroom_building)){
			if(classroom_building.equals("1")) {
				sql += " and substr(classroom,1,1)='1'";
			}else if(classroom_building.equals("2")) {
				sql += " and substr(classroom,1,1)='2'";
			}else if(classroom_building.equals("3")) {
				sql += " and substr(classroom,1,1)='3'";
			}else if(classroom_building.equals("4")) {
				sql += " and substr(classroom,1,1)='4'";
			}else if(classroom_building.equals("5")) {
				sql += " and substr(classroom,1,1)='5'";
			}else if(classroom_building.equals("6")) {
				sql += " and substr(classroom,1,1)='6'";
			}
		}
		if(!StringUtil.isEmpty(classroom_block)){
			if(classroom_block.equals("A")) {
				sql += " and substr(classroom,2,1)='A'";
			}else if(classroom_block.equals("B")) {
				sql += " and substr(classroom,2,1)='B'";
			}else if(classroom_block.equals("C")) {
				sql += " and substr(classroom,2,1)='C'";
			}else if(classroom_block.equals("D")) {
				sql += " and substr(classroom,2,1)='D'";
			}
		}
		if(!StringUtil.isEmpty(classroom_room)){
			if(classroom_room.equals("101")) {
				sql += " and substr(classroom,4,3)='101'";
			}else if(classroom_room.equals("102")) {
				sql += " and substr(classroom,4,3)='102'";
			}else if(classroom_room.equals("103")) {
				sql += " and substr(classroom,4,3)='103'";
			}else if(classroom_room.equals("104")) {
				sql += " and substr(classroom,4,3)='104'";
			}else if(classroom_room.equals("105")) {
				sql += " and substr(classroom,4,3)='105'";
			}else if(classroom_room.equals("106")) {
				sql += " and substr(classroom,4,3)='106'";
			}else if(classroom_room.equals("107")) {
				sql += " and substr(classroom,4,3)='107'";
			}else if(classroom_room.equals("108")) {
				sql += " and substr(classroom,4,3)='108'";
			}else if(classroom_room.equals("201")) {
				sql += " and substr(classroom,4,3)='201'";
			}else if(classroom_room.equals("202")) {
				sql += " and substr(classroom,4,3)='202'";
			}else if(classroom_room.equals("203")) {
				sql += " and substr(classroom,4,3)='203'";
			}else if(classroom_room.equals("204")) {
				sql += " and substr(classroom,4,3)='204'";
			}else if(classroom_room.equals("205")) {
				sql += " and substr(classroom,4,3)='205'";
			}else if(classroom_room.equals("206")) {
				sql += " and substr(classroom,4,3)='206'";
			}else if(classroom_room.equals("207")) {
				sql += " and substr(classroom,4,3)='207'";
			}else if(classroom_room.equals("208")) {
				sql += " and substr(classroom,4,3)='208'";
			}else if(classroom_room.equals("301")) {
				sql += " and substr(classroom,4,3)='301'";
			}else if(classroom_room.equals("302")) {
				sql += " and substr(classroom,4,3)='302'";
			}else if(classroom_room.equals("303")) {
				sql += " and substr(classroom,4,3)='303'";
			}else if(classroom_room.equals("304")) {
				sql += " and substr(classroom,4,3)='304'";
			}else if(classroom_room.equals("305")) {
				sql += " and substr(classroom,4,3)='305'";
			}else if(classroom_room.equals("306")) {
				sql += " and substr(classroom,4,3)='306'";
			}else if(classroom_room.equals("307")) {
				sql += " and substr(classroom,4,3)='307'";
			}else if(classroom_room.equals("308")) {
				sql += " and substr(classroom,4,3)='308'";
			}else if(classroom_room.equals("401")) {
				sql += " and substr(classroom,4,3)='401'";
			}else if(classroom_room.equals("402")) {
				sql += " and substr(classroom,4,3)='402'";
			}else if(classroom_room.equals("403")) {
				sql += " and substr(classroom,4,3)='403'";
			}else if(classroom_room.equals("404")) {
				sql += " and substr(classroom,4,3)='404'";
			}else if(classroom_room.equals("405")) {
				sql += " and substr(classroom,4,3)='405'";
			}else if(classroom_room.equals("406")) {
				sql += " and substr(classroom,4,3)='406'";
			}else if(classroom_room.equals("407")) {
				sql += " and substr(classroom,4,3)='407'";
			}else if(classroom_room.equals("408")) {
				sql += " and substr(classroom,4,3)='408'";
			}
			else if(classroom_room.equals("501")) {
				sql += " and substr(classroom,4,3)='501'";
			}else if(classroom_room.equals("502")) {
				sql += " and substr(classroom,4,3)='502'";
			}else if(classroom_room.equals("503")) {
				sql += " and substr(classroom,4,3)='503'";
			}else if(classroom_room.equals("504")) {
				sql += " and substr(classroom,4,3)='504'";
			}else if(classroom_room.equals("505")) {
				sql += " and substr(classroom,4,3)='505'";
			}else if(classroom_room.equals("506")) {
				sql += " and substr(classroom,4,3)='506'";
			}else if(classroom_room.equals("507")) {
				sql += " and substr(classroom,4,3)='507'";
			}else if(classroom_room.equals("508")) {
				sql += " and substr(classroom,4,3)='508'";
			}
			else if(classroom_room.equals("601")) {
				sql += " and substr(classroom,4,3)='601'";
			}else if(classroom_room.equals("602")) {
				sql += " and substr(classroom,4,3)='602'";
			}else if(classroom_room.equals("603")) {
				sql += " and substr(classroom,4,3)='603'";
			}else if(classroom_room.equals("604")) {
				sql += " and substr(classroom,4,3)='604'";
			}else if(classroom_room.equals("605")) {
				sql += " and substr(classroom,4,3)='605'";
			}else if(classroom_room.equals("606")) {
				sql += " and substr(classroom,4,3)='606'";
			}else if(classroom_room.equals("607")) {
				sql += " and substr(classroom,4,3)='607'";
			}else if(classroom_room.equals("608")) {
				sql += " and substr(classroom,4,3)='608'";
			}
			else if(classroom_room.equals("701")) {
				sql += " and substr(classroom,4,3)='701'";
			}else if(classroom_room.equals("702")) {
				sql += " and substr(classroom,4,3)='702'";
			}else if(classroom_room.equals("703")) {
				sql += " and substr(classroom,4,3)='703'";
			}else if(classroom_room.equals("704")) {
				sql += " and substr(classroom,4,3)='704'";
			}else if(classroom_room.equals("705")) {
				sql += " and substr(classroom,4,3)='705'";
			}else if(classroom_room.equals("706")) {
				sql += " and substr(classroom,4,3)='706'";
			}else if(classroom_room.equals("707")) {
				sql += " and substr(classroom,4,3)='707'";
			}else if(classroom_room.equals("708")) {
				sql += " and substr(classroom,4,3)='708'";
			}
			else if(classroom_room.equals("801")) {
				sql += " and substr(classroom,4,3)='801'";
			}else if(classroom_room.equals("802")) {
				sql += " and substr(classroom,4,3)='802'";
			}else if(classroom_room.equals("803")) {
				sql += " and substr(classroom,4,3)='803'";
			}else if(classroom_room.equals("804")) {
				sql += " and substr(classroom,4,3)='804'";
			}else if(classroom_room.equals("805")) {
				sql += " and substr(classroom,4,3)='805'";
			}else if(classroom_room.equals("806")) {
				sql += " and substr(classroom,4,3)='806'";
			}else if(classroom_room.equals("807")) {
				sql += " and substr(classroom,4,3)='807'";
			}else if(classroom_room.equals("808")) {
				sql += " and substr(classroom,4,3)='808'";
			}
		}
		if(!StringUtil.isEmpty(weeks)){
			sql += " and substr(weeks,1,5)='"+weeks+"'";
		}
		if(!StringUtil.isEmpty(weeks_3)){
			sql += " and substr(weeks,7,1)='"+weeks_3+"'";
		}
		if(!StringUtil.isEmpty(numbers)){
			sql += " and numbers='"+numbers+"'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();
		ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				TeachCourse cl = new TeachCourse();
				cl.setId(resultSet.getInt("id"));
				cl.setCourseid(resultSet.getString("courseid"));
				cl.setTno(resultSet.getString("tno"));
				cl.setNumbers("第 "+resultSet.getString("numbers")+" 节");
				String weeks1 = "第 "+resultSet.getString("weeks").charAt(0);
				if(resultSet.getString("weeks").charAt(1)!='-') {
					weeks1+=resultSet.getString("weeks").charAt(1);
					weeks1+="-";
					weeks1+=resultSet.getString("weeks").charAt(3);
					weeks1+=resultSet.getString("weeks").charAt(4);
					weeks1+=" 周 星期 ";
					weeks1+=resultSet.getString("weeks").charAt(6);
				}else {
					weeks1+="-";
					weeks1+=resultSet.getString("weeks").charAt(2);
					if(resultSet.getString("weeks").charAt(3)!=',') {
						weeks1+=resultSet.getString("weeks").charAt(3);
						weeks1+=" 周 星期 ";
						weeks1+=resultSet.getString("weeks").charAt(5);
					}else {
						weeks1+=" 周 星期 ";
						weeks1+=resultSet.getString("weeks").charAt(4);
					}
				}
				cl.setWeeks(weeks1);
				cl.setClassroom(resultSet.getString("classroom"));
				ret.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	public int FindTeachCourseListTotal(String tno, String teachername, String courseid, String coursename,String classroom_building, String classroom_block, String classroom_room, String weeks, String weeks_3, String numbers) {
		int total = 0;
		String sql = "select count(*) as total from teachcourse";
		if(!(StringUtil.isEmpty(tno))) {
			sql += " and tno='"+tno+"'";
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
		if(!StringUtil.isEmpty(classroom_building)){
			if(classroom_building.equals("1")) {
				sql += " and substr(classroom,1,1)='1'";
			}else if(classroom_building.equals("2")) {
				sql += " and substr(classroom,1,1)='2'";
			}else if(classroom_building.equals("3")) {
				sql += " and substr(classroom,1,1)='3'";
			}else if(classroom_building.equals("4")) {
				sql += " and substr(classroom,1,1)='4'";
			}else if(classroom_building.equals("5")) {
				sql += " and substr(classroom,1,1)='5'";
			}else if(classroom_building.equals("6")) {
				sql += " and substr(classroom,1,1)='6'";
			}
		}
		if(!StringUtil.isEmpty(classroom_block)){
			if(classroom_block.equals("A")) {
				sql += " and substr(classroom,2,1)='A'";
			}else if(classroom_block.equals("B")) {
				sql += " and substr(classroom,2,1)='B'";
			}else if(classroom_block.equals("C")) {
				sql += " and substr(classroom,2,1)='C'";
			}else if(classroom_block.equals("D")) {
				sql += " and substr(classroom,2,1)='D'";
			}
		}
		if(!StringUtil.isEmpty(classroom_room)){
			if(classroom_room.equals("101")) {
				sql += " and substr(classroom,4,3)='101'";
			}else if(classroom_room.equals("102")) {
				sql += " and substr(classroom,4,3)='102'";
			}else if(classroom_room.equals("103")) {
				sql += " and substr(classroom,4,3)='103'";
			}else if(classroom_room.equals("104")) {
				sql += " and substr(classroom,4,3)='104'";
			}else if(classroom_room.equals("105")) {
				sql += " and substr(classroom,4,3)='105'";
			}else if(classroom_room.equals("106")) {
				sql += " and substr(classroom,4,3)='106'";
			}else if(classroom_room.equals("107")) {
				sql += " and substr(classroom,4,3)='107'";
			}else if(classroom_room.equals("108")) {
				sql += " and substr(classroom,4,3)='108'";
			}else if(classroom_room.equals("201")) {
				sql += " and substr(classroom,4,3)='201'";
			}else if(classroom_room.equals("202")) {
				sql += " and substr(classroom,4,3)='202'";
			}else if(classroom_room.equals("203")) {
				sql += " and substr(classroom,4,3)='203'";
			}else if(classroom_room.equals("204")) {
				sql += " and substr(classroom,4,3)='204'";
			}else if(classroom_room.equals("205")) {
				sql += " and substr(classroom,4,3)='205'";
			}else if(classroom_room.equals("206")) {
				sql += " and substr(classroom,4,3)='206'";
			}else if(classroom_room.equals("207")) {
				sql += " and substr(classroom,4,3)='207'";
			}else if(classroom_room.equals("208")) {
				sql += " and substr(classroom,4,3)='208'";
			}else if(classroom_room.equals("301")) {
				sql += " and substr(classroom,4,3)='301'";
			}else if(classroom_room.equals("302")) {
				sql += " and substr(classroom,4,3)='302'";
			}else if(classroom_room.equals("303")) {
				sql += " and substr(classroom,4,3)='303'";
			}else if(classroom_room.equals("304")) {
				sql += " and substr(classroom,4,3)='304'";
			}else if(classroom_room.equals("305")) {
				sql += " and substr(classroom,4,3)='305'";
			}else if(classroom_room.equals("306")) {
				sql += " and substr(classroom,4,3)='306'";
			}else if(classroom_room.equals("307")) {
				sql += " and substr(classroom,4,3)='307'";
			}else if(classroom_room.equals("308")) {
				sql += " and substr(classroom,4,3)='308'";
			}else if(classroom_room.equals("401")) {
				sql += " and substr(classroom,4,3)='401'";
			}else if(classroom_room.equals("402")) {
				sql += " and substr(classroom,4,3)='402'";
			}else if(classroom_room.equals("403")) {
				sql += " and substr(classroom,4,3)='403'";
			}else if(classroom_room.equals("404")) {
				sql += " and substr(classroom,4,3)='404'";
			}else if(classroom_room.equals("405")) {
				sql += " and substr(classroom,4,3)='405'";
			}else if(classroom_room.equals("406")) {
				sql += " and substr(classroom,4,3)='406'";
			}else if(classroom_room.equals("407")) {
				sql += " and substr(classroom,4,3)='407'";
			}else if(classroom_room.equals("408")) {
				sql += " and substr(classroom,4,3)='408'";
			}
			else if(classroom_room.equals("501")) {
				sql += " and substr(classroom,4,3)='501'";
			}else if(classroom_room.equals("502")) {
				sql += " and substr(classroom,4,3)='502'";
			}else if(classroom_room.equals("503")) {
				sql += " and substr(classroom,4,3)='503'";
			}else if(classroom_room.equals("504")) {
				sql += " and substr(classroom,4,3)='504'";
			}else if(classroom_room.equals("505")) {
				sql += " and substr(classroom,4,3)='505'";
			}else if(classroom_room.equals("506")) {
				sql += " and substr(classroom,4,3)='506'";
			}else if(classroom_room.equals("507")) {
				sql += " and substr(classroom,4,3)='507'";
			}else if(classroom_room.equals("508")) {
				sql += " and substr(classroom,4,3)='508'";
			}
			else if(classroom_room.equals("601")) {
				sql += " and substr(classroom,4,3)='601'";
			}else if(classroom_room.equals("602")) {
				sql += " and substr(classroom,4,3)='602'";
			}else if(classroom_room.equals("603")) {
				sql += " and substr(classroom,4,3)='603'";
			}else if(classroom_room.equals("604")) {
				sql += " and substr(classroom,4,3)='604'";
			}else if(classroom_room.equals("605")) {
				sql += " and substr(classroom,4,3)='605'";
			}else if(classroom_room.equals("606")) {
				sql += " and substr(classroom,4,3)='606'";
			}else if(classroom_room.equals("607")) {
				sql += " and substr(classroom,4,3)='607'";
			}else if(classroom_room.equals("608")) {
				sql += " and substr(classroom,4,3)='608'";
			}
			else if(classroom_room.equals("701")) {
				sql += " and substr(classroom,4,3)='701'";
			}else if(classroom_room.equals("702")) {
				sql += " and substr(classroom,4,3)='702'";
			}else if(classroom_room.equals("703")) {
				sql += " and substr(classroom,4,3)='703'";
			}else if(classroom_room.equals("704")) {
				sql += " and substr(classroom,4,3)='704'";
			}else if(classroom_room.equals("705")) {
				sql += " and substr(classroom,4,3)='705'";
			}else if(classroom_room.equals("706")) {
				sql += " and substr(classroom,4,3)='706'";
			}else if(classroom_room.equals("707")) {
				sql += " and substr(classroom,4,3)='707'";
			}else if(classroom_room.equals("708")) {
				sql += " and substr(classroom,4,3)='708'";
			}
			else if(classroom_room.equals("801")) {
				sql += " and substr(classroom,4,3)='801'";
			}else if(classroom_room.equals("802")) {
				sql += " and substr(classroom,4,3)='802'";
			}else if(classroom_room.equals("803")) {
				sql += " and substr(classroom,4,3)='803'";
			}else if(classroom_room.equals("804")) {
				sql += " and substr(classroom,4,3)='804'";
			}else if(classroom_room.equals("805")) {
				sql += " and substr(classroom,4,3)='805'";
			}else if(classroom_room.equals("806")) {
				sql += " and substr(classroom,4,3)='806'";
			}else if(classroom_room.equals("807")) {
				sql += " and substr(classroom,4,3)='807'";
			}else if(classroom_room.equals("808")) {
				sql += " and substr(classroom,4,3)='808'";
			}
		}
		if(!StringUtil.isEmpty(weeks)){
			sql += " and substr(weeks,1,5)='"+weeks+"'";
		}
		if(!StringUtil.isEmpty(weeks_3)){
			sql += " and substr(weeks,7,1)='"+weeks_3+"'";
		}
		if(!StringUtil.isEmpty(numbers)){
			sql += " and numbers='"+numbers+"'";
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
	public List<Map<String, Object>> getTeachList(String tno) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		String sql = "select teachcourse.*,course.coursename as courseName from teachcourse,course where teachcourse.tno='"+tno+"' and teachcourse.courseid=course.courseid";
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Map<String, Object> s = new HashMap<String, Object>();
				s.put("id",resultSet.getInt("id"));
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
				ret.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}