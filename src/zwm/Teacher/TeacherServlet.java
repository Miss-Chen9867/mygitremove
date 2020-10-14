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
	
	//��תPost����
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	//ǰ����Ӧ�ķ���
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
	//���ҽ�ʦ
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
	//��ʦ����
	private void toTeacherSelf(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/teacher/teacherself.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void huanjingTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/huanjingteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�����н�ʦ����
	private void teacherList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/allteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void xinwenTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/xinwenteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ľѧԺ�γ��б�
	private void tumuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/tumuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void meishuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/meishuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧԺ�γ��б�
	private void faxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/faxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧ�γ��б�
	private void wenxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/wenxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void tiyuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/tiyuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�����ʽ���ѧԺ�γ��б�
	private void guojiTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/guojiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void yinyueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/yinyueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void zhengzhiTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/zhengzhiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�������ѧԺ�γ��б�
	private void waiguoyuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/waiguoyuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void shengmingTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/shengmingteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ʷѧԺ�γ��б�
	private void lishiTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/lishiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void jiaoyuTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/jiaoyuteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧѧԺ�γ��б�
	private void huaxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/huaxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�������ѧԺ�γ��б�
	private void jisuanjTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/jisuanjiteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������ѧԺ�γ��б�
	private void wuliTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/wuliteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ����ѧԺ�γ��б�
	private void shuxueTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/shuxueteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ�����ѧԺ�γ��б�
	private void jinguanTeacherList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/teacher/jinguanteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ȡȫ����ʦ��¼
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ��ľѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ��ѧԺ�γ�
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
			//��ѯ��ѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ���ʽ����γ�
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
			//��ѯ���ογ�
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
			//��ѯ�����ѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ��ʷѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ��ѧԺ�γ�
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
			//��ѯ�����ѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			//��ѯ��ѧԺ�γ�
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
			//��ѯ����ѧԺ�γ�
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
			
	//ɾ����ʦ��¼
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
	//�༭��ʦ��¼
	private void editTeacher(HttpServletRequest request,HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");//��ϵ�绰
		String rztime = request.getParameter("rztime");//��ְʱ��
		String major = request.getParameter("major");//ְ��
		String academic = request.getParameter("academic");//����Ժϵ
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
		//��ȡ�̹���
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
	//��ӽ�ʦ��¼
	private void addTeacher(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");//��ϵ�绰
		String rztime = request.getParameter("rztime");//��ְʱ��
		String major = request.getParameter("major");//ְ��
		String academic = request.getParameter("academic");//����Ժϵ
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
		//��ȡ�̹���
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
	//�̹����Զ���
	private String setTD(Teacher teacher) {
		String tno="";
		//Ժ��
		if(teacher.getAcademic().equals("���������ѧԺ")) {
			tno+="01";
		}else if(teacher.getAcademic().equals("��ѧ��ͳ��ѧԺ")) {
			tno+="02";
		}else if(teacher.getAcademic().equals("��������ӹ���ѧԺ")) {
			tno+="03";
		}else if(teacher.getAcademic().equals("�������ѧ�뼼��ѧԺ")) {
			tno+="04";
		}else if(teacher.getAcademic().equals("��ѧ����ҩ����ѧԺ")) {
			tno+="05";
		}else if(teacher.getAcademic().equals("������ѧ������ѧԺ")) {
			tno+="06";
		}else if(teacher.getAcademic().equals("������ѧѧԺ")) {
			tno+="07";
		}else if(teacher.getAcademic().equals("��ʷ�Ļ�ѧԺ")) {
			tno+="08";
		}else if(teacher.getAcademic().equals("������ѧ�뼼��ѧԺ")) {
			tno+="09";
		}else if(teacher.getAcademic().equals("�����ѧԺ")) {
			tno+="10";
		}else if(teacher.getAcademic().equals("�����빫������ѧԺ")) {
			tno+="11";
		}else if(teacher.getAcademic().equals("���ʽ���ѧԺ")) {
			tno+="12";
		}else if(teacher.getAcademic().equals("����ѧԺ")) {
			tno+="13";
		}else if(teacher.getAcademic().equals("����ѧԺ")) {
			tno+="14";
		}else if(teacher.getAcademic().equals("��ѧԺ")) {
			tno+="15";
		}else if(teacher.getAcademic().equals("��ѧԺ")) {
			tno+="16";
		}else if(teacher.getAcademic().equals("�������������ѧԺ")) {
			tno+="17";
		}else if(teacher.getAcademic().equals("��ľ��������ѧԺ")) {
			tno+="18";
		}else if(teacher.getAcademic().equals("�����봫��ѧԺ")) {
			tno+="19";
		}
		//��ְʱ��
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
		//ְ��
		if(teacher.getMajor().equals("Ժ��")) {
			tno+="01";
		} else if(teacher.getMajor().equals("��Ժ��")) {
			tno+="02";
		} else if(teacher.getMajor().equals("����")) {
			tno+="03";
		} else if(teacher.getMajor().equals("������")) {
			tno+="04";
		} else if(teacher.getMajor().equals("��ʦ")) {
			tno+="05";
		} else if(teacher.getMajor().equals("����Ա")) {
			tno+="06";
		}
		return tno;
	}
}
