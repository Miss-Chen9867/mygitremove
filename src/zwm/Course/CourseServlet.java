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
	//��ѯ�γ�
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
	//ǰ������ѧԺ�γ��б�
	private void huanjingCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/huanjingcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void xinwenCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/xinwencourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ľѧԺ�γ��б�
	private void tumuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/tumucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void meishuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/meishucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧԺ�γ��б�
	private void faxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/faxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧ�γ��б�
	private void wenxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/wenxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void tiyuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/tiyucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�����ʽ���ѧԺ�γ��б�
	private void guojiCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/guojicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void yinyueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/yinyuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void zhengzhiCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/zhengzhicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�������ѧԺ�γ��б�
	private void waiguoyuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/waiguoyucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void shengmingCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/shengmingcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ʷѧԺ�γ��б�
	private void lishiCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/lishicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void jiaoyuCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/jiaoyucourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧѧԺ�γ��б�
	private void huaxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/huaxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�������ѧԺ�γ��б�
	private void jisuanjCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/jisuanjicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void wuliCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/wulicourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧԺ�γ��б�
	private void shuxueCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/shuxuecourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�����ѧԺ�γ��б�
	private void jinguanCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/jinguancourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�����пγ̽���
	private void allCourseList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/course/allcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		//��ѯ���пγ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ��ľѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ��ѧԺ�γ�
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
		//��ѯ��ѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ���ʽ����γ�
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
		//��ѯ���ογ�
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
		//��ѯ�����ѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ��ʷѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ��ѧԺ�γ�
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
		//��ѯ�����ѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ѯ��ѧԺ�γ�
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
		//��ѯ����ѧԺ�γ�
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
		//��ӿγ�
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
		//�༭�γ�
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
		//ɾ���γ�
		public void deleteCourse(HttpServletRequest request,HttpServletResponse response) {
			String[] ids = request.getParameterValues("ids[]");
			String idStr = "";
			for(String id : ids){
				idStr += id + ",";
			}
			idStr = idStr.substring(0, idStr.length()-1);//�������ַ���
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
		//��ȡԺ��
		private String setCD(String academic) {
			String courseid="";
			//��һλ��0��������ͨ�ÿγ�| 1��������ͨ�ÿγ�|2����ѧԺר�пγ�
			if(academic.equals("����ͨ�ÿγ�")) {
				courseid="000";
			}else if(academic.equals("��ͨ�ÿγ�")) {
				courseid="100";
			}else {
				courseid="2";
			}
			//Ժ��
			if(academic.equals("���������ѧԺ")) {
				courseid+="01";
			}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
				courseid+="02";
			}else if(academic.equals("��������ӹ���ѧԺ")) {
				courseid+="03";
			}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
				courseid+="04";
			}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
				courseid+="05";
			}else if(academic.equals("������ѧ������ѧԺ")) {
				courseid+="06";
			}else if(academic.equals("������ѧѧԺ")) {
				courseid+="07";
			}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
				courseid+="08";
			}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
				courseid+="09";
			}else if(academic.equals("�����ѧԺ")) {
				courseid+="10";
			}else if(academic.equals("�����빫������ѧԺ")) {
				courseid+="11";
			}else if(academic.equals("���ʽ���ѧԺ")) {
				courseid+="12";
			}else if(academic.equals("����ѧԺ")) {
				courseid+="13";
			}else if(academic.equals("����ѧԺ")) {
				courseid+="14";
			}else if(academic.equals("��ѧԺ")) {
				courseid+="15";
			}else if(academic.equals("��ѧԺ")) {
				courseid+="16";
			}else if(academic.equals("�������������ѧԺ")) {
				courseid+="17";
			}else if(academic.equals("��ľ��������ѧԺ")) {
				courseid+="18";
			}else if(academic.equals("�����봫��ѧԺ")) {
				courseid+="19";
			}
			return courseid;
		}		
}
