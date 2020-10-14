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
		//ѧ������
		private void toStudentSelf(HttpServletRequest request,HttpServletResponse response) throws IOException {
			HttpSession session = request.getSession();
			Student student = (Student)session.getAttribute("user");
			String clazzid = student.getClazzid();
			//��ѯ�༶����ȡ�༶����ѧԺ���༶���ƣ�רҵ���༶
			ClazzDao clazzDao = new ClazzDao();
			Clazz clazz = clazzDao.getSpecialClazz(clazzid);
			session.setAttribute("clazz", clazz);
			try {
				request.getRequestDispatcher("views/student/studentself.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
				
			}
		}
	//����ѧ��
	private void toAllstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/allstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//��У
	private void toInstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/instudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//��һѧ��
	private void toOnestudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/onestudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//���ѧ��
	private void toTwostudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/twostudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//����ѧ��
	private void toThreestudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/threestudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//����ѧ��
	private void toFourstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/fourstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	//��ҵѧ��
	private void toOutstudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/outstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	//ѧ������
	private void toStudentSelfList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			request.getRequestDispatcher("views/student/studentself.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	//��ѯ����ѧ����Ϣ
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
	
	//��ѯ��Уѧ����Ϣ
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
	
	//��ѯ��һѧ����Ϣ
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
	
	//��ѯ���ѧ����Ϣ
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
	
	//��ѯ����ѧ����Ϣ
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

	//��ѯ����ѧ����Ϣ
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

	//��ѯ��ҵѧ����Ϣ
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
	
	//����ѧ�����ݶ���
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
	
	//ɾ��ѧ��
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
		idStr = idStr.substring(0, idStr.length()-1);//����������ַ���
		snoStr = snoStr.substring(0,snoStr.length()-1);//����ѧ���ַ���
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
	
	//�༭ѧ��
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
		//��ȡ����
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
	//����ѧ��
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
		//��ȡ���
		String clazzid = setCD(clazz,major,grade,academic);
		//��ȡ����
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
			//����Ǵ�һ��ͬѧ����������ӿγ̣���Щ�γ����ڱ��޿γ��ǲ��ɸ��ĵģ���ͬѧԺѡ��Ŀγ̲�һ��
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
	
	//��ȡ����
	public int getAge(String birthday) {
		//ת��Ϊ����
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(birthday);
			Calendar cal = Calendar.getInstance();
			//before�����λС�ڵ���˼  after�����Ϊ���ڵ���˼
			//��������ڵ�ǰ����֮���׳��쳣
			if(cal.before(date)) {
				return -1;
			}
			//��ȡ��ǰʱ���������
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH);
			int dayNow = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(date);
			
			//��ȡ�������ڵ�������
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayBirth = cal.get(Calendar.DAY_OF_MONTH);
			
			//��������
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
	
	//����Զ���
		private String setCD(String name1,String name,String grade,String academic) {
			String clazzid="1";
			//�꼶
			if(grade.equals("��һ")) {
				clazzid="1";
			}else if(grade.equals("���")) {
				clazzid="2";
			}else if(grade.equals("����")) {
				clazzid="3";
			}else if(grade.equals("����")) {
				clazzid="4";
			}
			//Ժ��
			if(academic.equals("���������ѧԺ")) {
				clazzid+="01";
			}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
				clazzid+="02";
			}else if(academic.equals("��������ӹ���ѧԺ")) {
				clazzid+="03";
			}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
				clazzid+="04";
			}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
				clazzid+="05";
			}else if(academic.equals("������ѧ������ѧԺ")) {
				clazzid+="06";
			}else if(academic.equals("������ѧѧԺ")) {
				clazzid+="07";
			}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
				clazzid+="08";
			}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
				clazzid+="09";
			}else if(academic.equals("�����ѧԺ")) {
				clazzid+="10";
			}else if(academic.equals("�����빫������ѧԺ")) {
				clazzid+="11";
			}else if(academic.equals("���ʽ���ѧԺ")) {
				clazzid+="12";
			}else if(academic.equals("����ѧԺ")) {
				clazzid+="13";
			}else if(academic.equals("����ѧԺ")) {
				clazzid+="14";
			}else if(academic.equals("��ѧԺ")) {
				clazzid+="15";
			}else if(academic.equals("��ѧԺ")) {
				clazzid+="16";
			}else if(academic.equals("�������������ѧԺ")) {
				clazzid+="17";
			}else if(academic.equals("��ľ��������ѧԺ")) {
				clazzid+="18";
			}else if(academic.equals("�����봫��ѧԺ")) {
				clazzid+="19";
			}
			
			//���� ��������ƥ��
			if(Pattern.matches(".*���̹���רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*�г�Ӫ��רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*������Դרҵ.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*�������רҵ.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*���ѧרҵ.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*���ʾ�����ó��רҵ.*",name)) {
				clazzid+="6";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="7";
			}else if(Pattern.matches(".*����רҵ.*",name)) {
				clazzid+="8";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="9";
			}else if(Pattern.matches(".*��ѧ��Ӧ����ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*ͳ��ѧרҵ .*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*���ڹ���רҵ.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*���ڿ�ѧרҵ.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*��Ϣ������ѧרҵ.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*������Ϣ��ѧ�뼼��רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*�Զ���רҵ.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*�����Ϣ��ѧרҵ.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*�������ѧ�뼼��רҵ .*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*�����ݹ���רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*����������רҵ.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*�������רҵ.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*���繤��רҵ.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*��ѧ�����빤��רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*��Դ��ѧ����רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*��ҩ����רҵ.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*ҩ���Ƽ�רҵ.*",name)) {
				clazzid+="4";
			}else if(Pattern.matches(".*ҩѧרҵ.*",name)) {
				clazzid+="5";
			}else if(Pattern.matches(".*�����ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*������Ϣ��ѧרҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*��湤��רҵ.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*ѧǰ����ѧרҵ*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*�Ļ���ҵ����רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*���Ľ���רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*���＼��רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*԰��רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*Ӣ��רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*����Ӣ��רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*����רҵ.*",name)) {
				clazzid+="3";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*��ѧרҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*������ʽ���רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*����רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*����רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*��������רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*�������רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*�й���ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*��ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*���ѧרҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*��ҵ���רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*ƽ�����רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*��ľ����רҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*������������ԴӦ�ù���רҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*���ѧרҵ.*",name)) {
				clazzid+="1";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="2";
			}else if(Pattern.matches(".*����ѧרҵ.*",name)) {
				clazzid+="3";
			}

			if(Pattern.matches(".*1��.*",name1)) {
				clazzid+="1";
			}else if(Pattern.matches(".*2��.*",name1)) {
				clazzid+="2";
			}else if(Pattern.matches(".*3��.*",name1)) {
				clazzid+="3";
			}else if(Pattern.matches(".*4��.*",name1)) {
				clazzid+="4";
			}
			return clazzid;
		}
		//����ѧ��
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