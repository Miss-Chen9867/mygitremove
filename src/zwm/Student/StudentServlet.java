package zwm.Student;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zwm.Clazz.Clazz;
import zwm.Clazz.ClazzDao;
import zwm.SelectedCourse.SelectedCourseDao;
import zwm.page.Page;


public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("toAllStudentListView".equals(method)){
			toAllstudentList(request,response);
		}else if("toStudentSelfListView".equals(method)) {
			toStudentSelfList(request,response);
		} else if("toInStudentListView".equals(method)) {
			toInstudentList(request,response);
		} else if("toOneStudentListView".equals(method)) {
			toOnestudentList(request,response);
		} else if("toTwoStudentListView".equals(method)) {
			toTwostudentList(request,response);
		} else if("toThreeStudentListView".equals(method)) {
			toThreestudentList(request,response);
		} else if("toFourStudentListView".equals(method)) {
			toFourstudentList(request,response);
		} else if("toOutStudentListView".equals(method)) {
			toOutstudentList(request,response);
		}  else if("AddStudent".equals(method)){
			addStudent(request,response);
		} else if("EditStudent".equals(method)){
			editStudent(request,response);
		} else if("DeleteStudent".equals(method)){
			deleteStudent(request,response);
		} else if("getAllStudentList".equals(method)){
			getAllStudentList(request,response);
		} else if("getInStudentList".equals(method)){
			getInStudentList(request,response);
		} else if("getOneStudentList".equals(method)){
			getOneStudentList(request,response);
		} else if("getTwoStudentList".equals(method)){
			getTwoStudentList(request,response);
		} else if("getThreeStudentList".equals(method)){
			getThreeStudentList(request,response);
		} else if("getFourStudentList".equals(method)){
			getFourStudentList(request,response);
		} else if("getOutStudentList".equals(method)){
			getOutStudentList(request,response);
		} else if("toStudentSelf".equals(method)) {
			toStudentSelf(request,response);
		} else if("FindStudent".equals(method)) {
			toFindStudent(request,response);
		}
	}
		//学生个人
		private void toStudentSelf(HttpServletRequest request,HttpServletResponse response) throws IOException {
			HttpSession session = request.getSession();
			Student student = (Student)session.getAttribute("user");
			String clazzid = student.getClazzid();
			//查询班级，获取班级所属学院，班级名称，专业，班级
			ClazzDao clazzDao = new ClazzDao();
			Clazz clazz = clazzDao.getSpecialClazz(clazzid);
			session.setAttribute("clazz", clazz);
			try {
				request.getRequestDispatcher("views/student/studentself.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
				
			}
		}
	//所有学生
	private void toAllstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/allstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//在校
	private void toInstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/instudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//大一学生
	private void toOnestudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/onestudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//大二学生
	private void toTwostudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/twostudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//大三学生
	private void toThreestudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/threestudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//大四学生
	private void toFourstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/fourstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	//毕业学生
	private void toOutstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/outstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//学生个人
	private void toStudentSelfList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/studentself.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//查询所有学生信息
	private void getAllStudentList(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Student student = new Student();
		student.setSno(sno);
		student.setClazzid(clazzid);
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.getAllStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getAllStudentListTotal(student);
		studentDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", studentList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//查询在校学生信息
	private void getInStudentList(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Student student = new Student();
		student.setSno(sno);
		student.setClazzid(clazzid);
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.getInStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getInStudentListTotal(student);
		studentDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", studentList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//查询大一学生信息
	private void getOneStudentList(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Student student = new Student();
		student.setSno(sno);
		student.setClazzid(clazzid);
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.getOneStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getOneStudentListTotal(student);
		studentDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", studentList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//查询大二学生信息
	private void getTwoStudentList(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Student student = new Student();
		student.setSno(sno);
		student.setClazzid(clazzid);
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.getTwoStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getTwoStudentListTotal(student);
		studentDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", studentList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	//查询大三学生信息
	private void getThreeStudentList(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Student student = new Student();
		student.setSno(sno);
		student.setClazzid(clazzid);
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.getThreeStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getThreeStudentListTotal(student);
		studentDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", studentList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	//查询大四学生信息
	private void getFourStudentList(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Student student = new Student();
		student.setSno(sno);
		student.setClazzid(clazzid);
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.getFourStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getFourStudentListTotal(student);
		studentDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", studentList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	//查询毕业学生信息
	private void getOutStudentList(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Student student = new Student();
		student.setSno(sno);
		student.setClazzid(clazzid);
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.getOutStudentList(student, new Page(currentPage, pageSize));
		int total = studentDao.getOutStudentListTotal(student);
		studentDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", studentList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	//升级学生（暂定）
	private void toUpStudent(HttpServletRequest request, HttpServletResponse response) {
		StudentDao studentDao = new StudentDao();
		if(studentDao.UpStudent()){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				studentDao.Close();
			}
		}	
	}
	
	//删除学生
	private void deleteStudent(HttpServletRequest request,HttpServletResponse response) {
		String[] ids = request.getParameterValues("ids[]");
		String[] snos = request.getParameterValues("numbers[]");
		String snoStr = "";
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		for(String sno : snos) {
			snoStr += sno + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);//返回序号子字符串
		snoStr = snoStr.substring(0,snoStr.length()-1);//返回学号字符串
		StudentDao studentDao = new StudentDao();
		if(studentDao.deleteStudent(snoStr,idStr)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				studentDao.Close();
			}
		}
	}
	
	//编辑学生
	private void editStudent(HttpServletRequest request,HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String sno = request.getParameter("sno");
		String clazzid = request.getParameter("clazzid");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String gratuated = request.getParameter("gratuated");
		String phone = request.getParameter("phone");
		String home = request.getParameter("home");
		String birthday = request.getParameter("birthday");
		//获取年龄
		int age = getAge(birthday);
		Student student = new Student();
		student.setId(id);
		student.setSno(sno);
		student.setClazzid(clazzid);
		student.setPhone(phone);
		student.setName(name);
		student.setPassword(password);
		student.setSex(sex);
		student.setAge(age);
		student.setHome(home);
		student.setBirthday(birthday);
		student.setGratuated(gratuated);
		if(student.getAge()<14 || student.getAge()>24) {
			try {
				response.getWriter().write("AgeError");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else {
			StudentDao studentDao = new StudentDao();
			if(studentDao.editStudent(student)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					studentDao.Close();
				}
			}
		}
	}
	//增加学生
	private void addStudent(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");
		String home = request.getParameter("home");
		String birthday = request.getParameter("birthday");
		String major = request.getParameter("major"); 
		String clazz = request.getParameter("clazz"); 
		String grade = request.getParameter("grade");
		String academic = request.getParameter("academic");
		//获取班号
		String clazzid = setCD(clazz,major,grade,academic);
		//获取年龄
		int age = getAge(birthday);
		Student student = new Student();
		student.setPhone(phone);
		student.setName(name);
		student.setPassword(password);
		student.setSex(sex);
		student.setAge(age);
		student.setClazzid(clazzid);
		student.setHome(home);
		student.setBirthday(birthday);
		if(student.getAge()<14 || student.getAge()>24) {
			try {
				response.getWriter().write("AgeError");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else {
			//如果是大一的同学，则自行添加课程，这些课程属于必修课程是不可更改的，不同学院选择的课程不一样
			SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
			
			StudentDao studentDao = new StudentDao();
			if(studentDao.addStudent(student)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					studentDao.Close();
				}
			}			
		}
	}
	
	//获取年龄
	public int getAge(String birthday) {
		//转化为日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(birthday);
			Calendar cal = Calendar.getInstance();
			//before可理解位小于的意思  after可理解为大于的意思
			//如果生日在当前日期之后，抛出异常
			if(cal.before(date)) {
				return -1;
			}
			//获取当前时间的年月日
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH);
			int dayNow = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(date);
			
			//获取出生日期的年月日
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayBirth = cal.get(Calendar.DAY_OF_MONTH);
			
			//计算年龄
			int age = yearNow - yearBirth;
			if(monthNow < monthBirth) {
				age--;
			}
			if((monthNow==monthBirth)&&(dayNow<dayBirth)) {
				age--;
			}
			if(!(age>=14&&age<=24))
				return -1;
			return age;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//班号自动化
		private String setCD(String name1,String name,String grade,String academic) {
			String clazzid="1";
			//年级
			if(grade.equals("大一")) {
				clazzid="1";
			}else if(grade.equals("大二")) {
				clazzid="2";
			}else if(grade.equals("大三")) {
				clazzid="3";
			}else if(grade.equals("大四")) {
				clazzid="4";
			}
			//院名
			if(academic.equals("经济与管理学院")) {
				clazzid+="01";
			}else if(academic.equals("数学与统计学院")) {
				clazzid+="02";
			}else if(academic.equals("物理与电子工程学院")) {
				clazzid+="03";
			}else if(academic.equals("计算机科学与技术学院")) {
				clazzid+="04";
			}else if(academic.equals("化学与制药工程学院")) {
				clazzid+="05";
			}else if(academic.equals("环境科学与旅游学院")) {
				clazzid+="06";
			}else if(academic.equals("教育科学学院")) {
				clazzid+="07";
			}else if(academic.equals("历史文化学院")) {
				clazzid+="08";
			}else if(academic.equals("生命科学与技术学院")) {
				clazzid+="09";
			}else if(academic.equals("外国语学院")) {
				clazzid+="10";
			}else if(academic.equals("政治与公共管理学院")) {
				clazzid+="11";
			}else if(academic.equals("国际教育学院")) {
				clazzid+="12";
			}else if(academic.equals("音乐学院")) {
				clazzid+="13";
			}else if(academic.equals("体育学院")) {
				clazzid+="14";
			}else if(academic.equals("文学院")) {
				clazzid+="15";
			}else if(academic.equals("法学院")) {
				clazzid+="16";
			}else if(academic.equals("美术与艺术设计学院")) {
				clazzid+="17";
			}else if(academic.equals("土木建筑工程学院")) {
				clazzid+="18";
			}else if(academic.equals("新闻与传播学院")) {
				clazzid+="19";
			}
			
			//班名 采用正则匹配
			if(Pattern.matches(".*工商管理专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*市场营销专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*人力资源专业.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*财务管理专业.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*会计学专业.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*国际经济与贸易专业.*",name)) {
				clazzid+="6";
			}else if(Pattern.matches(".*金融学专业.*",name)) {
				clazzid+="7";
			}else if(Pattern.matches(".*保险专业.*",name)) {
				clazzid+="8";
			}else if(Pattern.matches(".*财政学专业.*",name)) {
				clazzid+="9";
			}else if(Pattern.matches(".*数学与应用数学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*统计学专业 .*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*金融工程专业.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*金融科学专业.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*信息与计算科学专业.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*物理学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*电子信息科学与技术专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*自动化专业.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*光电信息科学专业.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*计算机科学与技术专业 .*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*大数据工程专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*物联网工程专业.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*软件工程专业.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*网络工程专业.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*化学工程与工艺专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*能源化学工程专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*制药工程专业.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*药物制剂专业.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*药学专业.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*地理科学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*地理信息科学专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*测绘工程专业.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*教育学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*心理学专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*学前教育学专业*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*文化产业管理专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*人文教育专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*生物技术专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*园林专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*英语专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*商务英语专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*日语专业.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*政治学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*哲学专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*汉语国际教育专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*钢琴专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*声乐专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*体育教育专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*社会体育专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*中国文学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*语言学专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*法学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*侦查学专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*工业设计专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*平面设计专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*土木工程专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*建筑环境与能源应用工程专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*广告学专业.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*新闻学专业.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*传播学专业.*",name)) {
				clazzid+="3";
			}

			if(Pattern.matches(".*1班.*",name1)) {
				clazzid+="1";
			}else if(Pattern.matches(".*2班.*",name1)) {
				clazzid+="2";
			}else if(Pattern.matches(".*3班.*",name1)) {
				clazzid+="3";
			}else if(Pattern.matches(".*4班.*",name1)) {
				clazzid+="4";
			}
			return clazzid;
		}
		//查找学生
		private void toFindStudent(HttpServletRequest request, HttpServletResponse response) {
			String sno =request.getParameter("sno"); 
			String grade =request.getParameter("grade");
			String academic=request.getParameter("academic");
			String major=request.getParameter("major");
			String clazz=request.getParameter("clazz");
			String name=request.getParameter("name");
			String sex=request.getParameter("sex");
			Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
			if(pageSize==10)
				pageSize = 18;
			StudentDao studentDao = new StudentDao();
			List<Student> studentList = studentDao.FindStudentList(sno,grade,academic,major,clazz,name,sex,new Page(currentPage, pageSize));
			int total = studentDao.getFindStudentListTotal(sno,grade,academic,major,clazz,name,sex);
			studentDao.Close();
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("total", total);
			ret.put("rows", studentList);
			try {
				String from = request.getParameter("from");
				if("combox".equals(from)){
					response.getWriter().write(JSONArray.fromObject(studentList).toString());
				}else{
					response.getWriter().write(JSONObject.fromObject(ret).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
}