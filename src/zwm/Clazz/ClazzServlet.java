package zwm.Clazz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zwm.Student.Student;
import zwm.Student.StudentDao;
import zwm.page.Page;

public class ClazzServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toAllClazzListView".equals(method)){
			allClazzList(request,response);
		}else if("toOneClazzListView".equals(method)){
			oneClazzList(request, response);
		}else if("toTwoClazzListView".equals(method)){
			twoClazzList(request, response);
		}else if("toThreeClazzListView".equals(method)){
			threeClazzList(request, response);
		}else if("toFourClazzListView".equals(method)){
			fourClazzList(request, response);
		}else if("getAllClazzList".equals(method)){
			getAllClazzList(request, response);
		}else if("getOneClazzList".equals(method)){
			getOneClazzList(request, response);
		}else if("getTwoClazzList".equals(method)){
			getTwoClazzList(request, response);
		}else if("getThreeClazzList".equals(method)){
			getThreeClazzList(request, response);
		}else if("getFourClazzList".equals(method)){
			getFourClazzList(request, response);
		}else if("AddClazz".equals(method)){
			addClazz(request, response);
		}else if("DeleteClazz".equals(method)){
			deleteClazz(request, response);
		}else if("EditClazz".equals(method)){
			editClazz(request, response);
		}else if("FindClazz".equals(method)) {
			toFindClazz(request,response);
		}
	}
	
	//前往所有班级界面
	private void allClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/allclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//前往大一班级界面
	private void oneClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/oneclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//前往大二班级界面
	private void twoClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/twoclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//前往大三班级界面
	private void threeClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/threeclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//前往大四班级界面
	private void fourClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/fourclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//查询所有班级
	private void getAllClazzList(HttpServletRequest request,HttpServletResponse response){
		String major = request.getParameter("majorName");
		String academic = request.getParameter("academicName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Clazz clazz = new Clazz();
		clazz.setMajor(major);
		clazz.setAcademic(academic);
		ClazzDao clazzDao = new ClazzDao();
		List<Clazz> clazzList = clazzDao.getAllClazzList(clazz, new Page(currentPage, pageSize));
		int total = clazzDao.getAllClazzListTotal(clazz);
		clazzDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", clazzList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//查询大一班级
	private void getOneClazzList(HttpServletRequest request,HttpServletResponse response){
		String major = request.getParameter("majorName");
		String academic = request.getParameter("academicName");		
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Clazz clazz = new Clazz();
		clazz.setMajor(major);
		clazz.setAcademic(academic);
		ClazzDao clazzDao = new ClazzDao();
		List<Clazz> clazzList = clazzDao.getOneClazzList(clazz, new Page(currentPage, pageSize));
		int total = clazzDao.getOneClazzListTotal(clazz);
		clazzDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", clazzList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//查询大二班级
	private void getTwoClazzList(HttpServletRequest request,HttpServletResponse response){
		String major = request.getParameter("majorName");
		String academic = request.getParameter("academicName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Clazz clazz = new Clazz();
		clazz.setMajor(major);
		clazz.setAcademic(academic);
		ClazzDao clazzDao = new ClazzDao();
		List<Clazz> clazzList = clazzDao.getTwoClazzList(clazz, new Page(currentPage, pageSize));
		int total = clazzDao.getTwoClazzListTotal(clazz);
		clazzDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", clazzList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//查询大三班级
	private void getThreeClazzList(HttpServletRequest request,HttpServletResponse response){
		String major = request.getParameter("majorName");
		String academic = request.getParameter("academicName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Clazz clazz = new Clazz();
		clazz.setMajor(major);
		clazz.setAcademic(academic);
		ClazzDao clazzDao = new ClazzDao();
		List<Clazz> clazzList = clazzDao.getThreeClazzList(clazz, new Page(currentPage, pageSize));
		int total = clazzDao.getThreeClazzListTotal(clazz);
		clazzDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", clazzList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	//查询大四班级
	private void getFourClazzList(HttpServletRequest request,HttpServletResponse response){
		String major = request.getParameter("majorName");
		String academic = request.getParameter("academicName");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10)
			pageSize = 18;
		Clazz clazz = new Clazz();
		clazz.setMajor(major);
		clazz.setAcademic(academic);
		ClazzDao clazzDao = new ClazzDao();
		List<Clazz> clazzList = clazzDao.getFourClazzList(clazz, new Page(currentPage, pageSize));
		int total = clazzDao.getFourClazzListTotal(clazz);
		clazzDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", clazzList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	//编辑班级
	private void editClazz(HttpServletRequest request,HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String grade = request.getParameter("grade");
		String major = request.getParameter("major"); 
		String name = request.getParameter("name");
		String academic = request.getParameter("academic");
		Clazz clazz = new Clazz();
		clazz.setId(id);
		clazz.setGrade(grade);
		clazz.setMajor(major);
		clazz.setName(name);
		clazz.setAcademic(academic);
		clazz.setClazzid(setCD(clazz));
		ClazzDao clazzDao = new ClazzDao();
		if(clazzDao.editClazz(clazz)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				clazzDao.Close();
			}
		}
	}
	
	//删除班级
	private void deleteClazz(HttpServletRequest request,HttpServletResponse response) {
		String clazzid = request.getParameter("clazzid");
		System.out.println(clazzid);
		ClazzDao clazzDao = new ClazzDao();
		if(clazzDao.deleteClazz(clazzid)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				clazzDao.Close();
			}
		}
	}
	
	//增加班级
	private void addClazz(HttpServletRequest request,HttpServletResponse response) {
		String major = request.getParameter("major"); 
		String name = request.getParameter("name"); 
		String grade = request.getParameter("grade");
		String academic = request.getParameter("academic");
		Clazz clazz = new Clazz();
		clazz.setName(name);
		clazz.setGrade(grade);
		clazz.setMajor(major);
		clazz.setAcademic(academic);
		clazz.setClazzid(setCD(clazz));
		ClazzDao clazzDao = new ClazzDao();
		if(clazzDao.addClazz(clazz)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				clazzDao.Close();
			}
		}
		
	}
	
	//班号自动化
	private String setCD(Clazz clazz) {
		String name1 = clazz.getName();
		String name = clazz.getMajor();
		String grade = clazz.getGrade();
		String academic = clazz.getAcademic();
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
	//查找班级
	private void toFindClazz(HttpServletRequest request, HttpServletResponse response) {
				String grade =request.getParameter("grade");
				String academic=request.getParameter("academic");
				String major=request.getParameter("major");
				String clazz=request.getParameter("clazz");
				String clazzid=request.getParameter("clazzid");
				Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
				if(pageSize==10)
					pageSize = 18;
				ClazzDao clazzDao = new ClazzDao();
				List<Clazz> clazzList = clazzDao.FindClazzList(grade,academic,major,clazz,clazzid,new Page(currentPage, pageSize));
				int total = clazzDao.getFindClazzListTotal(grade,academic,major,clazz,clazzid);
				System.out.println("total="+total);
				clazzDao.Close();
				response.setCharacterEncoding("UTF-8");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("total", total);
				ret.put("rows", clazzList);
				try {
					String from = request.getParameter("from");
					if("combox".equals(from)){
						response.getWriter().write(JSONArray.fromObject(clazzList).toString());
					}else{
						response.getWriter().write(JSONObject.fromObject(ret).toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}	
	}
}
