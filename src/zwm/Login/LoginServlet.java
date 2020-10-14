package zwm.Login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.util.StringUtil;

import zwm.Admin.Admin;
import zwm.Admin.AdminDao;
import zwm.Student.Student;
import zwm.Student.StudentDao;
import zwm.Teacher.Teacher;
import zwm.Teacher.TeacherDao;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -5870852067427524781L;//序列化 保证数据传输的持久性
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String username = request.getParameter("username");//获取用户名
		String password = request.getParameter("password");//获取密码
		String vcode = request.getParameter("vcode");//获取验证码
		if("logout".equals(method)) {
			logout(request,response);
			return;
		}
		int type = Integer.parseInt(request.getParameter("type"));
		//如果用户名为空
		if(StringUtil.isEmpty(username)) {
			response.getWriter().write("userNull");
			return;
		}
		//如果密码为空
		if(StringUtil.isEmpty(password)) {
			response.getWriter().write("passwordNull");
			return;
		}
		//如果验证码为空
		if(StringUtil.isEmpty(vcode)) {
			response.getWriter().write("vcodeNull");
			return;
		}
		//校验验证码
		String loginCpacha = (String) request.getSession().getAttribute("loginCapcha".toString());
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
			response.getWriter().write("vcodeError");
			return;
		}
		//比对用户名和密码是否正确
		String loginFlags = "loginFaild";
		switch (type) {
			//如果是管理员
			case 1:
				AdminDao adminDao = new AdminDao();
				Admin admin = adminDao.login(username, password);//去和数据库内容比对
				adminDao.Close();
				//如果DB中没有管理员，返回出错消息
				if(admin == null) {
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session = request.getSession();
				session.setAttribute("user", admin);	//设置参数user为admin userType为1
				session.setAttribute("userType",type);
				loginFlags = "loginSuccess";
				break;
				
			//如果是老师	
			case 2:
				TeacherDao teacherDao = new TeacherDao();
				Teacher teacher = teacherDao.login(username,password);
				teacherDao.Close();
				//如果DB中没有老师，返回出错消息
				if(teacher == null) {
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session1 = request.getSession();
				session1.setAttribute("user", teacher);
				session1.setAttribute("userType",type);
				loginFlags = "loginSuccess";
				break;
				
			//如果是学生
			case 3:
				StudentDao studentDao = new StudentDao();
				Student student = studentDao.login(username,password);
				studentDao.Close();
				//如果DB中没有学生，返回出错消息
				if(student == null) {
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session2 = request.getSession();
				session2.setAttribute("user", student);
				session2.setAttribute("userType", type);
				loginFlags = "loginSuccess";
				break;	
			//其它
			default:
				break;
		}
		//返回登录信息
		response.getWriter().write(loginFlags);
	}

	//退出操作
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("userType");
		response.sendRedirect("index.jsp");
	}
}
