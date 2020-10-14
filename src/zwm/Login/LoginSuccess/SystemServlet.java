package zwm.Login.LoginSuccess;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zwm.Admin.Admin;
import zwm.Admin.AdminDao;
import zwm.Student.Student;
import zwm.Student.StudentDao;
import zwm.Teacher.Teacher;
import zwm.Teacher.TeacherDao;

public class SystemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("EditPasswordView")) {
			request.getRequestDispatcher("views/system/editpassword.jsp").forward(request, response);	
		} else if(method.equals("EditPassword")) {
			EditPassword(request,response);
		} else if(method.equals("toAboutSystem")) {
			toAboutSystem(request,response);
		} else {
			request.getRequestDispatcher("views/system/system.jsp").forward(request, response);	
		}
	}
	private void toAboutSystem(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/system/about.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//修改密码
	private void EditPassword(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newpassword");
		response.setCharacterEncoding("UTF-8");
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		if(userType == 1){
			Admin admin = (Admin)request.getSession().getAttribute("user");
			if(!admin.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			AdminDao adminDao = new AdminDao();
			if(adminDao.editPassword(admin, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					adminDao.Close();
				}
			}else{
				try {
					response.getWriter().write("修改出错");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					adminDao.Close();
				}
			}
		}
		if(userType == 2){
			//教师
			Teacher teacher = (Teacher)request.getSession().getAttribute("user");
			if(!teacher.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			TeacherDao teacherDao = new TeacherDao();
			if(teacherDao.editPassword(teacher, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					teacherDao.Close();
				}
			}else{
				try {
					response.getWriter().write("修改出错");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					teacherDao.Close();
				}
			}
		}
		if(userType == 3){
			//学生
			Student student = (Student)request.getSession().getAttribute("user");
			if(!student.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			StudentDao studentDao = new StudentDao();
			if(studentDao.editPassword(student, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					studentDao.Close();
				}
			}else{
				try {
					response.getWriter().write("修改出错");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					studentDao.Close();
				}
			}
		}
	}
	
}
