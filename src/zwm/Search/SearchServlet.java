package zwm.Search;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("toStudentSearchView".equals(method)){
			toStudentSearchView(request,response);
		} else if("toTeacherSearchView".equals(method)) {
			toTeacherSearchView(request,response);
		} else if("toCourseSearchView".equals(method)) {
			toCourseSearchView(request,response);
		} else if("toClazzSearchView".equals(method)) {
			toClazzSearchView(request,response);
		} else if("toTeachCourseSearchView".equals(method)) {
			toTeachCourseSearchView(request,response);
		} else if("toSelectedCourseSearchView".equals(method)) {
			toSelectedCourseSearchView(request,response);
		}
	}
	//前往查询学生界面
	private void toStudentSearchView(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/search/searchstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//前往查询教师界面
	private void toTeacherSearchView(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/search/searchteacher.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//前往查询课程界面
	private void toCourseSearchView(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/search/searchcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//前往查询班级界面
	private void toClazzSearchView(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/search/searchclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//前往查询授课界面
	private void toTeachCourseSearchView(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/search/searchteachcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//前往查询选课界面
	private void toSelectedCourseSearchView(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/search/searchselectedcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
