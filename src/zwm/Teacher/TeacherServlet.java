package zwm.Teacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zwm.Clazz.Clazz;
import zwm.Clazz.ClazzDao;
import zwm.Course.Course;
import zwm.Course.CourseDao;
import zwm.Student.Student;
import zwm.Student.StudentDao;
import zwm.page.Page;

public class TeacherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	//跳转Post方法
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	//前往对应的方法
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toTeacherListView".equals(method)){
			teacherList(request,response);
		}else if("AddTeacher".equals(method)){
			addTeacher(request,response);
		}else if("AllTeacherList".equals(method)){
			getAllTeacherList(request,response);
		}else if("EditTeacher".equals(method)){
			editTeacher(request,response);
		}else if("DeleteTeacher".equals(method)){
			deleteTeacher(request,response);
		}else if("tojinguanTeacherListView".equals(method)){
			jinguanTeacherList(request, response);
		}else if("toshuxueTeacherListView".equals(method)){
			shuxueTeacherList(request, response);
		}else if("towuliTeacherListView".equals(method)){
			wuliTeacherList(request, response);
		}else if("tojisuanjiTeacherListView".equals(method)){
			jisuanjTeacherList(request, response);
		}else if("tohuaxueTeacherListView".equals(method)){
			huaxueTeacherList(request, response);
		}else if("tojiaoyuTeacherListView".equals(method)){
			jiaoyuTeacherList(request, response);
		}else if("tolishiTeacherListView".equals(method)){
			lishiTeacherList(request, response);
		}else if("toshengmingTeacherListView".equals(method)){
			shengmingTeacherList(request, response);
		}else if("towaiguoyuTeacherListView".equals(method)){
			waiguoyuTeacherList(request, response);
		}else if("tozhengzhiTeacherListView".equals(method)){
			zhengzhiTeacherList(request, response);
		}else if("toguojiTeacherListView".equals(method)){
			guojiTeacherList(request, response);
		}else if("toyinyueTeacherListView".equals(method)){
			yinyueTeacherList(request, response);
		}else if("totiyuTeacherListView".equals(method)){
			tiyuTeacherList(request, response);
		}else if("towenxueTeacherListView".equals(method)){
			wenxueTeacherList(request, response);
		}else if("tofaxueTeacherListView".equals(method)){
			faxueTeacherList(request, response);
		}else if("tomeishuTeacherListView".equals(method)){
			meishuTeacherList(request, response);
		}else if("totumuTeacherListView".equals(method)){
			tumuTeacherList(request, response);
		}else if("toxinwenTeacherListView".equals(method)){
			xinwenTeacherList(request, response);
		}else if("tohuanjingTeacherListView".equals(method)){
			huanjingTeacherList(request, response);
		}else if("getJinGuanTeacherList".equals(method)){
			getJinGuanTeacherList(request, response);
		}else if("getShuXueTeacherList".equals(method)){
			getShuXueTeacherList(request, response);
		}else if("getWuLiTeacherList".equals(method)){
			getWuLiTeacherList(request, response);
		}else if("getJiSuanJiTeacherList".equals(method)){
			getJiSuanJiTeacherList(request, response);
		}else if("getHuaXueTeacherList".equals(method)){
			getHuaXueTeacherList(request, response);
		}else if("getJiaoYuTeacherList".equals(method)){
			getJiaoYuTeacherList(request, response);
		}else if("getLiShiTeacherList".equals(method)){
			getLiShiTeacherList(request, response);
		}else if("getShengMingTeacherList".equals(method)){
			getShengMingTeacherList(request, response);
		}else if("getWaiGuoYuTeacherList".equals(method)){
			getWaiGuoYuTeacherList(request, response);
		}else if("getZhengZhiTeacherList".equals(method)){
			getZhengZhiTeacherList(request, response);
		}else if("getGuoJiTeacherList".equals(method)){
			getGuoJiTeacherList(request, response);
		}else if("getYinYueTeacherList".equals(method)){
			getYinYueTeacherList(request, response);
		}else if("getTiYuTeacherList".equals(method)){
			getTiYuTeacherList(request, response);
		}else if("getWenXueYuanTeacherList".equals(method)){
			getWenXueYuanTeacherList(request, response);
		}else if("getFaXueYuanTeacherList".equals(method)){
			getFaXueYuanTeacherList(request, response);
		}else if("getMeiShuTeacherList".equals(method)){
			getMeiShuTeacherList(request, response);
		}else if("getTuMuTeacherList".equals(method)){
			getTuMuTeacherList(request, response);
		}else if("getXinWenTeacherList".equals(method)){
			getXinWenTeacherList(request, response);
		}else if("getHuanJingTeacherList".equals(method)){
			getHuanJingTeacherList(request, response);
		}else if("toTeacherSelf".equals(method)) {
			toTeacherSelf(request,response);
		}else if("FindTeacher".equals(method)) {
			FindTeacher(request,response);
		}
	}
	//查找教师
	private void FindTeacher(HttpServletRequest request, HttpServletResponse response) {
		String tno =request.getParameter("tno"); 
		String academic=request.getParameter("academic");
		String major=request.getParameter("major");
		String name=request.getParameter("name");
		String sex=request.getParameter("sex");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		TeacherDao teacherDao = new TeacherDao();
		List<Teacher> teacherList = teacherDao.FindTeacherList(tno,academic,major,name,sex,new Page(currentPage, pageSize));
		int total = teacherDao.getFindTeacherListTotal(tno,academic,major,name,sex);
		teacherDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", teacherList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teacherList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//教师个人
	private void toTeacherSelf(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/teacher/teacherself.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//前往环境学院课程列表
	private void huanjingTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/huanjingteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往所有教师界面
	private void teacherList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/allteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往新闻学院课程列表
	private void xinwenTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/xinwenteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往土木学院课程列表
	private void tumuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/tumuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往美术学院课程列表
	private void meishuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/meishuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往法学院课程列表
	private void faxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/faxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往文学课程列表
	private void wenxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/wenxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往体育学院课程列表
	private void tiyuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/tiyuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往国际教育学院课程列表
	private void guojiTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/guojiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往音乐学院课程列表
	private void yinyueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/yinyueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往政治学院课程列表
	private void zhengzhiTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/zhengzhiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往外国语学院课程列表
	private void waiguoyuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/waiguoyuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往生命学院课程列表
	private void shengmingTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/shengmingteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往历史学院课程列表
	private void lishiTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/lishiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往教育学院课程列表
	private void jiaoyuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/jiaoyuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往化学学院课程列表
	private void huaxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/huaxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往计算机学院课程列表
	private void jisuanjTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/jisuanjiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往物理学院课程列表
	private void wuliTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/wuliteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往数学院课程列表
	private void shuxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/shuxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//前往金管学院课程列表
	private void jinguanTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/jinguanteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//获取全部教师记录
			private void getAllTeacherList(HttpServletRequest request,HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getAllTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getAllTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询环境学院课程
			private void getHuanJingTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getHuanJingTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getHuanJingTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询新闻学院课程
			private void getXinWenTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getXinWenTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getXinWenTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询土木学院课程
			private void getTuMuTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getTuMuTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getTuMuTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询美术学院课程
			private void getMeiShuTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getMeiShuTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getMeiShuTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询法学院课程
			private void getFaXueYuanTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getFaXueYuanTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getFaXueYuanTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询文学院课程
			private void getWenXueYuanTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getWenXueYuanTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getWenXueYuanTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询体育学院课程
			private void getTiYuTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getTiYuTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getTiYuTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询音乐学院课程
			private void getYinYueTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getYinYueTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getYinYueTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询国际教育课程
			private void getGuoJiTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getGuoJiTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getGuoJiTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询政治课程
			private void getZhengZhiTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getZhengZhiTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getZhengZhiTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询外国语学院课程
			private void getWaiGuoYuTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getWaiGuoYuTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getWaiGuoYuTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询生命学院课程
			private void getShengMingTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getShengMingTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getShengMingTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询历史学院课程
			private void getLiShiTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getLiShiTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getLiShiTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询教育学院课程
			private void getJiaoYuTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getJiaoYuTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getJiaoYuTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询化学院课程
			private void getHuaXueTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getHuaXueTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getHuaXueTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询计算机学院课程
			private void getJiSuanJiTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getJiSuanJiTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getJiSuanJiTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询物理学院课程
			private void getWuLiTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getWuLiTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getWuLiTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询数学院课程
			private void getShuXueTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getShuXueTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getShuXueTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//查询经管学院课程
			private void getJinGuanTeacherList(HttpServletRequest request, HttpServletResponse response) {
				String tno = request.getParameter("tno");
				String name = request.getParameter("teachername");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				Teacher teacher = new Teacher();
				teacher.setName(name);
				teacher.setTno(tno);
				TeacherDao teacherDao = new TeacherDao();
				List<Teacher> teacherList = teacherDao.getJinGuanTeacherList(teacher, new Page(currentPage, pageSize));
				int total = teacherDao.getJinGuanTeacherListTotal(teacher);
				teacherDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", teacherList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(teacherList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
	//删除教师记录
	private void deleteTeacher(HttpServletRequest request,HttpServletResponse response) {
		String[] ids = request.getParameterValues("ids[]");
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		TeacherDao teacherDao = new TeacherDao();
		if(teacherDao.deleteTeacher(idStr)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				teacherDao.Close();
			}
		}
	}
	//编辑教师记录
	private void editTeacher(HttpServletRequest request,HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");//联系电话
		String rztime = request.getParameter("rztime");//入职时间
		String major = request.getParameter("major");//职务
		String academic = request.getParameter("academic");//所属院系
		Teacher teacher = new Teacher();
		teacher.setId(id);
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setRztime(rztime);
		teacher.setMajor(major);
		teacher.setAcademic(academic);
		teacher.setPhone(phone);
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setSex(sex);
		//获取教工号
		String tno = setTD(teacher);
		teacher.setTno(tno);
		TeacherDao teacherDao = new TeacherDao();
		if(teacherDao.editTeacher(teacher)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				teacherDao.Close();
			}
		}
	}
	//添加教师记录
	private void addTeacher(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");//联系电话
		String rztime = request.getParameter("rztime");//入职时间
		String major = request.getParameter("major");//职务
		String academic = request.getParameter("academic");//所属院系
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setRztime(rztime);
		teacher.setMajor(major);
		teacher.setAcademic(academic);
		teacher.setPhone(phone);
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setSex(sex);
		//获取教工号
		String tno = setTD(teacher);
		teacher.setTno(tno);
		TeacherDao teacherDao = new TeacherDao();
		if(teacherDao.addTeacher(teacher)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				teacherDao.Close();
			}
		}
	}
	//教工号自动化
	private String setTD(Teacher teacher) {
		String tno="";
		//院名
		if(teacher.getAcademic().equals("经济与管理学院")) {
			tno+="01";
		}else if(teacher.getAcademic().equals("数学与统计学院")) {
			tno+="02";
		}else if(teacher.getAcademic().equals("物理与电子工程学院")) {
			tno+="03";
		}else if(teacher.getAcademic().equals("计算机科学与技术学院")) {
			tno+="04";
		}else if(teacher.getAcademic().equals("化学与制药工程学院")) {
			tno+="05";
		}else if(teacher.getAcademic().equals("环境科学与旅游学院")) {
			tno+="06";
		}else if(teacher.getAcademic().equals("教育科学学院")) {
			tno+="07";
		}else if(teacher.getAcademic().equals("历史文化学院")) {
			tno+="08";
		}else if(teacher.getAcademic().equals("生命科学与技术学院")) {
			tno+="09";
		}else if(teacher.getAcademic().equals("外国语学院")) {
			tno+="10";
		}else if(teacher.getAcademic().equals("政治与公共管理学院")) {
			tno+="11";
		}else if(teacher.getAcademic().equals("国际教育学院")) {
			tno+="12";
		}else if(teacher.getAcademic().equals("音乐学院")) {
			tno+="13";
		}else if(teacher.getAcademic().equals("体育学院")) {
			tno+="14";
		}else if(teacher.getAcademic().equals("文学院")) {
			tno+="15";
		}else if(teacher.getAcademic().equals("法学院")) {
			tno+="16";
		}else if(teacher.getAcademic().equals("美术与艺术设计学院")) {
			tno+="17";
		}else if(teacher.getAcademic().equals("土木建筑工程学院")) {
			tno+="18";
		}else if(teacher.getAcademic().equals("新闻与传播学院")) {
			tno+="19";
		}
		//入职时间
		tno+=teacher.getRztime().charAt(0);
		tno+=teacher.getRztime().charAt(1);
		tno+=teacher.getRztime().charAt(2);
		tno+=teacher.getRztime().charAt(3);
		if(teacher.getRztime().length()==8) {
			tno+="0";
			tno+=teacher.getRztime().charAt(5);
			tno+="0";
			tno+=teacher.getRztime().charAt(7);
		}else if(teacher.getRztime().length()==9) {
			if(teacher.getRztime().charAt(6)=='-') {
				tno+="0";
				tno+=teacher.getRztime().charAt(5);
				tno+=teacher.getRztime().charAt(7);
				tno+=teacher.getRztime().charAt(8);
			}else {
				tno+=teacher.getRztime().charAt(5);
				tno+=teacher.getRztime().charAt(6);
				tno+="0";
				tno+=teacher.getRztime().charAt(8);
			}
		}else if(teacher.getRztime().length()==10) {
			tno+=teacher.getRztime().charAt(5);
			tno+=teacher.getRztime().charAt(6);
			tno+=teacher.getRztime().charAt(8);
			tno+=teacher.getRztime().charAt(9);
		}	
		//职务
		if(teacher.getMajor().equals("院长")) {
			tno+="01";
		} else if(teacher.getMajor().equals("副院长")) {
			tno+="02";
		} else if(teacher.getMajor().equals("教授")) {
			tno+="03";
		} else if(teacher.getMajor().equals("副教授")) {
			tno+="04";
		} else if(teacher.getMajor().equals("讲师")) {
			tno+="05";
		} else if(teacher.getMajor().equals("辅导员")) {
			tno+="06";
		}
		return tno;
	}
}
