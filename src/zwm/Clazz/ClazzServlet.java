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
	
	//ǰ�����а༶����
	private void allClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/allclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//ǰ����һ�༶����
	private void oneClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/oneclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//ǰ������༶����
	private void twoClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/twoclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//ǰ�������༶����
	private void threeClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/threeclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//ǰ�����İ༶����
	private void fourClazzList(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/clazz/fourclazz.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ѯ���а༶
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
	
	//��ѯ��һ�༶
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

	//��ѯ����༶
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
	
	//��ѯ�����༶
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

	//��ѯ���İ༶
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
	
	//�༭�༶
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
	
	//ɾ���༶
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
	
	//���Ӱ༶
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
	
	//����Զ���
	private String setCD(Clazz clazz) {
		String name1 = clazz.getName();
		String name = clazz.getMajor();
		String grade = clazz.getGrade();
		String academic = clazz.getAcademic();
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
	//���Ұ༶
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
