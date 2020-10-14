package zwm.Course;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zwm.Student.Student;
import zwm.Student.StudentDao;
import zwm.page.Page;

public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("toAllCourseListView".equals(method)){
			allCourseList(request,response);
		}else if("getAllCourseList".equals(method)){
			getAllCourseList(request, response);
		}else if("AddCourse".equals(method)){
			addCourse(request, response);
		}else if("EditCourse".equals(method)){
			editCourse(request, response);
		}else if("DeleteCourse".equals(method)){
			deleteCourse(request, response);
		}else if("tojinguanCourseListView".equals(method)){
			jinguanCourseList(request, response);
		}else if("toshuxueCourseListView".equals(method)){
			shuxueCourseList(request, response);
		}else if("towuliCourseListView".equals(method)){
			wuliCourseList(request, response);
		}else if("tojisuanjiCourseListView".equals(method)){
			jisuanjCourseList(request, response);
		}else if("tohuaxueCourseListView".equals(method)){
			huaxueCourseList(request, response);
		}else if("tojiaoyuCourseListView".equals(method)){
			jiaoyuCourseList(request, response);
		}else if("tolishiCourseListView".equals(method)){
			lishiCourseList(request, response);
		}else if("toshengmingCourseListView".equals(method)){
			shengmingCourseList(request, response);
		}else if("towaiguoyuCourseListView".equals(method)){
			waiguoyuCourseList(request, response);
		}else if("tozhengzhiCourseListView".equals(method)){
			zhengzhiCourseList(request, response);
		}else if("toguojiCourseListView".equals(method)){
			guojiCourseList(request, response);
		}else if("toyinyueCourseListView".equals(method)){
			yinyueCourseList(request, response);
		}else if("totiyuCourseListView".equals(method)){
			tiyuCourseList(request, response);
		}else if("towenxueCourseListView".equals(method)){
			wenxueCourseList(request, response);
		}else if("tofaxueCourseListView".equals(method)){
			faxueCourseList(request, response);
		}else if("tomeishuCourseListView".equals(method)){
			meishuCourseList(request, response);
		}else if("totumuCourseListView".equals(method)){
			tumuCourseList(request, response);
		}else if("toxinwenCourseListView".equals(method)){
			xinwenCourseList(request, response);
		}else if("tohuanjingCourseListView".equals(method)){
			huanjingCourseList(request, response);
		}else if("getJinGuanCourseList".equals(method)){
			getJinGuanCourseList(request, response);
		}else if("getShuXueCourseList".equals(method)){
			getShuXueCourseList(request, response);
		}else if("getWuLiCourseList".equals(method)){
			getWuLiCourseList(request, response);
		}else if("getJiSuanJiCourseList".equals(method)){
			getJiSuanJiCourseList(request, response);
		}else if("getHuaXueCourseList".equals(method)){
			getHuaXueCourseList(request, response);
		}else if("getJiaoYuCourseList".equals(method)){
			getJiaoYuCourseList(request, response);
		}else if("getLiShiCourseList".equals(method)){
			getLiShiCourseList(request, response);
		}else if("getShengMingCourseList".equals(method)){
			getShengMingCourseList(request, response);
		}else if("getWaiGuoYuCourseList".equals(method)){
			getWaiGuoYuCourseList(request, response);
		}else if("getZhengZhiCourseList".equals(method)){
			getZhengZhiCourseList(request, response);
		}else if("getGuoJiCourseList".equals(method)){
			getGuoJiCourseList(request, response);
		}else if("getYinYueCourseList".equals(method)){
			getYinYueCourseList(request, response);
		}else if("getTiYuCourseList".equals(method)){
			getTiYuCourseList(request, response);
		}else if("getWenXueYuanCourseList".equals(method)){
			getWenXueYuanCourseList(request, response);
		}else if("getFaXueYuanCourseList".equals(method)){
			getFaXueYuanCourseList(request, response);
		}else if("getMeiShuCourseList".equals(method)){
			getMeiShuCourseList(request, response);
		}else if("getTuMuCourseList".equals(method)){
			getTuMuCourseList(request, response);
		}else if("getXinWenCourseList".equals(method)){
			getXinWenCourseList(request, response);
		}else if("getHuanJingCourseList".equals(method)){
			getHuanJingCourseList(request, response);
		}else if("FindCourse".equals(method)){
			toFindCourse(request, response);
		}
	}
	//查询课程
	private void toFindCourse(HttpServletRequest request, HttpServletResponse response) {
		String academic=request.getParameter("academic");
		String coursename=request.getParameter("courseName");
		String courseid=request.getParameter("courseId");
		String credit=request.getParameter("credit");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		CourseDao courseDao = new CourseDao();
		List<Course> courseList = courseDao.FindCourseList(academic,coursename,courseid,credit,new Page(currentPage, pageSize));
		int total = courseDao.getFindCourseListTotal(academic,coursename,courseid,credit);
		courseDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", courseList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(courseList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	//前往环境学院课程列表
	private void huanjingCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/huanjingcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往新闻学院课程列表
	private void xinwenCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/xinwencourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往土木学院课程列表
	private void tumuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/tumucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往美术学院课程列表
	private void meishuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/meishucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往法学院课程列表
	private void faxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/faxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往文学课程列表
	private void wenxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/wenxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往体育学院课程列表
	private void tiyuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/tiyucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往国际教育学院课程列表
	private void guojiCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/guojicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往音乐学院课程列表
	private void yinyueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/yinyuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往政治学院课程列表
	private void zhengzhiCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/zhengzhicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往外国语学院课程列表
	private void waiguoyuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/waiguoyucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往生命学院课程列表
	private void shengmingCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/shengmingcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往历史学院课程列表
	private void lishiCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/lishicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往教育学院课程列表
	private void jiaoyuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/jiaoyucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往化学学院课程列表
	private void huaxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/huaxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往计算机学院课程列表
	private void jisuanjCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/jisuanjicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往物理学院课程列表
	private void wuliCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/wulicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往数学院课程列表
	private void shuxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/shuxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往金管学院课程列表
	private void jinguanCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/jinguancourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往所有课程界面
	private void allCourseList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/allcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		//查询所有课程
		private void getAllCourseList(HttpServletRequest request,HttpServletResponse response){
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getAllCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getAllCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询环境学院课程
		private void getHuanJingCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getHuanJingCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getHuanJingCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询新闻学院课程
		private void getXinWenCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getXinWenCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getXinWenCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询土木学院课程
		private void getTuMuCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getTuMuCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getTuMuCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询美术学院课程
		private void getMeiShuCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getMeiShuCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getMeiShuCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询法学院课程
		private void getFaXueYuanCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getFaXueYuanCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getFaXueYuanCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询文学院课程
		private void getWenXueYuanCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getWenXueYuanCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getWenXueYuanCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询体育学院课程
		private void getTiYuCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getTiYuCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getTiYuCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询音乐学院课程
		private void getYinYueCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getYinYueCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getYinYueCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询国际教育课程
		private void getGuoJiCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getGuoJiCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getGuoJiCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询政治课程
		private void getZhengZhiCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getZhengZhiCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getZhengZhiCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询外国语学院课程
		private void getWaiGuoYuCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getWaiGuoYuCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getWaiGuoYuCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询生命学院课程
		private void getShengMingCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getShengMingCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getShengMingCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询历史学院课程
		private void getLiShiCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getLiShiCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getLiShiCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询教育学院课程
		private void getJiaoYuCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getJiaoYuCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getJiaoYuCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询化学院课程
		private void getHuaXueCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getHuaXueCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getHuaXueCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询计算机学院课程
		private void getJiSuanJiCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getJiSuanJiCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getJiSuanJiCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询物理学院课程
		private void getWuLiCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getWuLiCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getWuLiCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询数学院课程
		private void getShuXueCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getShuXueCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getShuXueCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//查询经管学院课程
		private void getJinGuanCourseList(HttpServletRequest request, HttpServletResponse response) {
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			Course course = new Course();
			course.setCourseId(courseid);
			course.setCourseName(coursename);
			CourseDao courseDao = new CourseDao();
			List<Course> courseList = courseDao.getJinGuanCourseList(course, new Page(currentPage, pageSize));
			int total = courseDao.getJinGuanCourseListTotal(course);
			courseDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", courseList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(courseList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//添加课程
		private void addCourse(HttpServletRequest request,HttpServletResponse response) {
			String previous = request.getParameter("previous");
			String coursename = request.getParameter("coursename"); 
			String academic = request.getParameter("academic");
			Integer credit = Integer.parseInt(request.getParameter("credit"));
			Course course = new Course();
			course.setCourseName(coursename);
			course.setCourseId(setCD(academic));
			course.setPrevious(previous);
			course.setCredit(credit);
			CourseDao courseDao = new CourseDao();
			if(courseDao.addCourse(course)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					courseDao.Close();
				}
			}	
		}
		//编辑课程
		private void editCourse(HttpServletRequest request,HttpServletResponse response) {
			Integer id = Integer.parseInt(request.getParameter("id"));
			String courseid = request.getParameter("courseid");
			String coursename = request.getParameter("coursename");
			int credit = Integer.parseInt(request.getParameter("credit"));
			String previous = request.getParameter("previous");
			Course course = new Course();
			course.setId(id);
			course.setCourseName(coursename);
			course.setCredit(credit);
			course.setCourseId(courseid);
			course.setPrevious(previous);
			CourseDao courseDao = new CourseDao();
			if(courseDao.editCourse(course)) {
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					courseDao.Close();
				}
			}
		}	
		//删除课程
		public void deleteCourse(HttpServletRequest request,HttpServletResponse response) {
			String[] ids = request.getParameterValues("ids[]");
			String idStr = "";
			for(String id : ids){
				idStr += id + ",";
			}
			idStr = idStr.substring(0, idStr.length()-1);//返回子字符串
			CourseDao courseDao = new CourseDao();
			if(courseDao.deleteCourse(idStr)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					courseDao.Close();
				}
			}			
		}		
		//获取院号
		private String setCD(String academic) {
			String courseid="";
			//第一位：0代表文理通用课程| 1代表理工科通用课程|2代表学院专有课程
			if(academic.equals("文理通用课程")) {
				courseid="000";
			}else if(academic.equals("理工通用课程")) {
				courseid="100";
			}else {
				courseid="2";
			}
			//院名
			if(academic.equals("经济与管理学院")) {
				courseid+="01";
			}else if(academic.equals("数学与统计学院")) {
				courseid+="02";
			}else if(academic.equals("物理与电子工程学院")) {
				courseid+="03";
			}else if(academic.equals("计算机科学与技术学院")) {
				courseid+="04";
			}else if(academic.equals("化学与制药工程学院")) {
				courseid+="05";
			}else if(academic.equals("环境科学与旅游学院")) {
				courseid+="06";
			}else if(academic.equals("教育科学学院")) {
				courseid+="07";
			}else if(academic.equals("历史文化学院")) {
				courseid+="08";
			}else if(academic.equals("生命科学与技术学院")) {
				courseid+="09";
			}else if(academic.equals("外国语学院")) {
				courseid+="10";
			}else if(academic.equals("政治与公共管理学院")) {
				courseid+="11";
			}else if(academic.equals("国际教育学院")) {
				courseid+="12";
			}else if(academic.equals("音乐学院")) {
				courseid+="13";
			}else if(academic.equals("体育学院")) {
				courseid+="14";
			}else if(academic.equals("文学院")) {
				courseid+="15";
			}else if(academic.equals("法学院")) {
				courseid+="16";
			}else if(academic.equals("美术与艺术设计学院")) {
				courseid+="17";
			}else if(academic.equals("土木建筑工程学院")) {
				courseid+="18";
			}else if(academic.equals("新闻与传播学院")) {
				courseid+="19";
			}
			return courseid;
		}		
}
