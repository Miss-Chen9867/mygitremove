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
	private static final long serialVersionUID = -5870852067427524781L;//���л� ��֤���ݴ���ĳ־���
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String username = request.getParameter("username");//��ȡ�û���
		String password = request.getParameter("password");//��ȡ����
		String vcode = request.getParameter("vcode");//��ȡ��֤��
		if("logout".equals(method)) {
			logout(request,response);
			return;
		}
		int type = Integer.parseInt(request.getParameter("type"));
		//����û���Ϊ��
		if(StringUtil.isEmpty(username)) {
			response.getWriter().write("userNull");
			return;
		}
		//�������Ϊ��
		if(StringUtil.isEmpty(password)) {
			response.getWriter().write("passwordNull");
			return;
		}
		//�����֤��Ϊ��
		if(StringUtil.isEmpty(vcode)) {
			response.getWriter().write("vcodeNull");
			return;
		}
		//У����֤��
		String loginCpacha = (String) request.getSession().getAttribute("loginCapcha".toString());
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
			response.getWriter().write("vcodeError");
			return;
		}
		//�ȶ��û����������Ƿ���ȷ
		String loginFlags = "loginFaild";
		switch (type) {
			//����ǹ���Ա
			case 1:
				AdminDao adminDao = new AdminDao();
				Admin admin = adminDao.login(username, password);//ȥ�����ݿ����ݱȶ�
				adminDao.Close();
				//���DB��û�й���Ա�����س�����Ϣ
				if(admin == null) {
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session = request.getSession();
				session.setAttribute("user", admin);	//���ò���userΪadmin userTypeΪ1
				session.setAttribute("userType",type);
				loginFlags = "loginSuccess";
				break;
				
			//�������ʦ	
			case 2:
				TeacherDao teacherDao = new TeacherDao();
				Teacher teacher = teacherDao.login(username,password);
				teacherDao.Close();
				//���DB��û����ʦ�����س�����Ϣ
				if(teacher == null) {
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session1 = request.getSession();
				session1.setAttribute("user", teacher);
				session1.setAttribute("userType",type);
				loginFlags = "loginSuccess";
				break;
				
			//�����ѧ��
			case 3:
				StudentDao studentDao = new StudentDao();
				Student student = studentDao.login(username,password);
				studentDao.Close();
				//���DB��û��ѧ�������س�����Ϣ
				if(student == null) {
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session2 = request.getSession();
				session2.setAttribute("user", student);
				session2.setAttribute("userType", type);
				loginFlags = "loginSuccess";
				break;	
			//����
			default:
				break;
		}
		//���ص�¼��Ϣ
		response.getWriter().write(loginFlags);
	}

	//�˳�����
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("userType");
		response.sendRedirect("index.jsp");
	}
}
