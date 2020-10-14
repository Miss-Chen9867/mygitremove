package zwm.TeachCourse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zwm.Course.Course;
import zwm.Course.CourseDao;
import zwm.SelectedCourse.SelectedCourse;
import zwm.SelectedCourse.SelectedCourseDao;
import zwm.Student.Student;
import zwm.Teacher.Teacher;
import zwm.page.Page;

public class TeachCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//转到doPost()方法
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	//获取前端响应的请求
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("toTeachCourseListView".equals(method)){
			toTeachCourseList(request,response);
		}else if("getTeachCourseList".equals(method)){
			getTeachCourse(request,response);
		}else if("AddTeachCourse".equals(method)){
			addTeachCourse(request,response);
		}else if("EditTeachCourse".equals(method)){
			editTeachCourse(request,response);
		}else if("DeleteTeachCourse".equals(method)){
			deleteTeachCourse(request,response);
		}else if("FindTeachCourseList".equals(method)){
			FindTeachCourse(request,response);
		}else if("getTeachCourseStudentList".equals(method)){
			TeachCourseStudentList(request,response);
		}else if("toSelectedCourseSelf".equals(method)){
			toSelectedCourseSelf(request,response);
		}else if("toTeachCourse".equals(method)){
			toTeachCourse(request, response);
		}else if("gettoTeachCourseList".equals(method)){
			gettoTeachCourseList(request, response);
		}
	}
	private void gettoTeachCourseList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		String tno = teacher.getTno();
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10) {
			pageSize = 18;
		}
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		List<Map<String, Object>> teachCourseStudentList = teachCourseDao.getTeachList(tno);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", teachCourseStudentList);
		retMap.put("type", "suceess");
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teachCourseStudentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(retMap).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往授课界面
	private void toTeachCourse(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.getRequestDispatcher("views/teachcourse/toteachcourse.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	//前往学生可选课程界面，可获取数据库所有信息
	private void toSelectedCourseSelf(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teachcourse/selectedcourseself.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//学生选课
	private void TeachCourseStudentList(HttpServletRequest request, HttpServletResponse response) {
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10) {
			pageSize = 18;
		}
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		List<Map<String, Object>> teachCourseStudentList = teachCourseDao.getScoreList();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", teachCourseStudentList);
		retMap.put("type", "suceess");
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teachCourseStudentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(retMap).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//查询授课
	private void FindTeachCourse(HttpServletRequest request, HttpServletResponse response) {
		String tno = request.getParameter("tno");
		String courseid = request.getParameter("courseId");
		String teachername = request.getParameter("teachername");
		String coursename = request.getParameter("courseName");
		String classroom_building = request.getParameter("classroom_building");//教学楼
		String classroom_block = request.getParameter("classroom_block");//单元楼
		String classroom_room = request.getParameter("classroom_room");//课室
		String weeks_1 = request.getParameter("weeks_1");//初始周
		String weeks_2 = request.getParameter("weeks_2");//结束周
		String weeks_3 = request.getParameter("weeks_3");//星期
		String numbers_1 = request.getParameter("numbers_1");//初始节
		String numbers_2 = request.getParameter("numbers_2");//结束节
		//整合上课时间
		String weeks = "";
		if(!StringUtil.isEmpty(weeks_1)) {
			weeks = ""+weeks_1+"-"+weeks_2;
		}
		//整合上课节数
		String numbers = "";
		if(!StringUtil.isEmpty(numbers_1)) {
			numbers = numbers_1+"-"+numbers_2;
		}
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10) {
			pageSize = 18;
		}
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		List<TeachCourse> teachcourseList = teachCourseDao.FindTeachCourseList(tno,teachername,courseid,coursename,classroom_building,classroom_block,classroom_room,weeks,weeks_3,numbers,new Page(currentPage, pageSize));
		int total = teachCourseDao.FindTeachCourseListTotal(tno,teachername,courseid,coursename,classroom_building,classroom_block,classroom_room,weeks,weeks_3,numbers);
		teachCourseDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", teachcourseList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teachcourseList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往管理员授课界面
	private void toTeachCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teachcourse/teachcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	//获取授课记录
	private void getTeachCourse(HttpServletRequest request,HttpServletResponse response) {
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10) {
			pageSize = 18;
		}
		TeachCourse teachCourse = new TeachCourse();
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		List<TeachCourse> teachcourseList = teachCourseDao.getTeachCourseList(teachCourse, new Page(currentPage, pageSize));
		int total = teachCourseDao.getTeachCourseListTotal(teachCourse);
		teachCourseDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", teachcourseList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teachcourseList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//添加授课
	private void addTeachCourse(HttpServletRequest request, HttpServletResponse response) {
		String tno = request.getParameter("tno");
		String courseid = request.getParameter("courseid");
		String classroom_building = request.getParameter("classroom_building");//教学楼
		String classroom_block = request.getParameter("classroom_block");//单元楼
		String classroom_room = request.getParameter("classroom_room");//课室
		String weeks_1 = request.getParameter("weeks_1");//初始周
		String weeks_2 = request.getParameter("weeks_2");//结束周
		String weeks_3 = request.getParameter("weeks_3");//星期
		String numbers_1 = request.getParameter("numbers_1");//初始节
		String numbers_2 = request.getParameter("numbers_2");//结束节
		String msg = "success";
		//如果结束周周数小于初始周返回
		if(Integer.parseInt(weeks_2)<Integer.parseInt(weeks_1)) {
			msg = "weekserror";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//如果结束节节数小于初始节返回
		if(Integer.parseInt(numbers_2)<Integer.parseInt(numbers_1)) {
			msg = "numberserror";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//整合上课地点
		String classroom = classroom_building+classroom_block+"-"+classroom_room;
		//整合上课时间
		String weeks = weeks_1+"-"+weeks_2+","+weeks_3;
		//整合上课节数
		String numbers = numbers_1+"-"+numbers_2;
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		/*
		 * //查询是否被授课 if(teachCourseDao.isTeach(tno, courseid)) { msg = "isTeached"; try
		 * { response.getWriter().write(msg); } catch (IOException e) {
		 * e.printStackTrace(); } teachCourseDao.Close(); return; }
		 */
		//查找授课是否被冲突
		if(teachCourseDao.isSelected(tno,classroom,Integer.parseInt(weeks_1),Integer.parseInt(weeks_2),Integer.parseInt(weeks_3),Integer.parseInt(numbers_1),Integer.parseInt(numbers_2))){
			msg = "teachCourseSelected";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			teachCourseDao.Close();
			return;
		}
		//添加授课
		TeachCourse teachCourse = new TeachCourse();
		teachCourse.setTno(tno);
		teachCourse.setCourseid(courseid);
		teachCourse.setClassroom(classroom);
		teachCourse.setWeeks(weeks);
		teachCourse.setNumbers(numbers);
		if(teachCourseDao.addTeachCourse(teachCourse)){
			msg = "success";
		}
		teachCourseDao.Close();
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//编辑授课
	private void editTeachCourse(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String tno = request.getParameter("tno");
		String courseid = request.getParameter("courseid");
		String classroom_building = request.getParameter("classroom_building");//教学楼
		String classroom_block = request.getParameter("classroom_block");//单元楼
		String classroom_room = request.getParameter("classroom_room");//课室
		String weeks_1 = request.getParameter("weeks_1");//初始周
		String weeks_2 = request.getParameter("weeks_2");//结束周
		String weeks_3 = request.getParameter("weeks_3");//星期
		String numbers_1 = request.getParameter("numbers_1");//初始节
		String numbers_2 = request.getParameter("numbers_2");//结束节
		String msg = "success";
		//如果结束周周数小于初始周返回
		if(Integer.parseInt(weeks_2)<Integer.parseInt(weeks_1)) {
			msg = "weekserror";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//如果结束节节数小于初始节返回
		if(Integer.parseInt(numbers_2)<Integer.parseInt(numbers_1)) {
			msg = "numberserror";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//整合上课地点
		String classroom = classroom_building+classroom_block+"-"+classroom_room;
		//整合上课时间
		String weeks = weeks_1+"-"+weeks_2+","+weeks_3;
		//整合上课节数
		String numbers = numbers_1+"-"+numbers_2;
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		//查找授课是否被冲突
		if(teachCourseDao.isSelected(tno,classroom,Integer.parseInt(weeks_1),Integer.parseInt(weeks_2),Integer.parseInt(weeks_3),Integer.parseInt(numbers_1),Integer.parseInt(numbers_2))){
			msg = "teachCourseSelected";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			teachCourseDao.Close();
			return;
		}
		//编辑授课
		TeachCourse teachCourse = new TeachCourse();
		teachCourse.setId(id);
		teachCourse.setTno(tno);
		teachCourse.setCourseid(courseid);
		teachCourse.setClassroom(classroom);
		teachCourse.setWeeks(weeks);
		teachCourse.setNumbers(numbers);
		if(teachCourseDao.editTeachCourse(teachCourse)){
			msg = "success";
		}
		teachCourseDao.Close();
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//删除授课
	private void deleteTeachCourse(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));//获取Id
		String tno = request.getParameter("tno");
		String courseid = request.getParameter("courseid");
		//查找该门课程有无被学生选课
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		if(selectedCourseDao.isTSelected(tno,courseid)) {
			selectedCourseDao.Close();
			try {
				response.getWriter().write("isSelected");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//删除该授课信息
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		if(teachCourseDao.deleteTeachCourse(id)) {
			teachCourseDao.Close();
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			teachCourseDao.Close();
			try {
				response.getWriter().write("error");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}
}

