package zwm.SelectedCourse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.github.pagehelper.util.StringUtil;
import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zwm.Course.CourseDao;
import zwm.Student.Student;
import zwm.Student.StudentDao;
import zwm.TeachCourse.TeachCourseDao;
import zwm.Teacher.Teacher;
import zwm.page.Page;

public class SelectedCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//ת��doPost()����
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	//��ȡǰ����Ӧ������
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("toSelectedCourseListView".equals(method)){
			toSelectedCourseList(request,response);
		} else if("SelectedCourseList".equals(method)){
			getSelectedCourse(request,response);
		} else if("AddSelectedCourse".equals(method)) {
			addSelectedCourse(request,response);
		} else if("DeleteSelectedCourse".equals(method)) {
			deleteSelectedCourse(request,response);
		} else if("EditSelectedCourse".equals(method)) {
			editSelectedCourse(request,response);
		}else if("ImportSelectedCourse".equals(method)){
			importSelectedCourse(request,response);
		}else if("ExportSelectedCourse".equals(method)){
			exportSelectedCourse(request,response);
		}else if("FindSelectedCourseList".equals(method)){
			FindSelectedCourse(request,response);
		}else if("SelectedTeachCourseSelf".equals(method)){
			SelectedTeachCourseSelf(request,response);
		}else if("toSelectedCourseSelf".equals(method)){
			toSelectedCourseSelf(request,response);
		}else if("getSelectedCourseSelfList".equals(method)){
			getSelectedCourseSelfList(request,response);
		}else if("getTeachStudent".equals(method)){
			getTeachStudent(request,response);
		}else if("toTeachStudent".equals(method)){
			toTeachStudent(request,response);
		}
	}
	//ǰ��ѡ���ѧ�γ̵�ѧ����Ϣ
	private void toTeachStudent(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/selectedcourse/toteachstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ȡ��ѧ�γ̵�ѧ����Ϣ
	private void getTeachStudent(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		String tno = teacher.getTno();
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10) {
			pageSize = 18;
		}
		SelectedCourseDao teachCourseDao = new SelectedCourseDao();
		List<Map<String, Object>> teachCourseStudentList = teachCourseDao.getTeachStudentList(tno);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", teachCourseStudentList);
		retMap.put("type", "suceess");
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teachCourseStudentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(retMap).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ѧ����ѡ�γ̽���
	private void getSelectedCourseSelfList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Student student = (Student)session.getAttribute("user");
		String sno = student.getSno();
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		List<Map<String, Object>> teachCourseStudentList = selectedCourseDao.getScoreSelfList(sno);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", teachCourseStudentList);
		retMap.put("type", "suceess");
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(teachCourseStudentList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(retMap).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ��ѧ����ѡ�γ̽���
	private void toSelectedCourseSelf(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/selectedcourse/selectedcourseself.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ѯѡ��
	private void FindSelectedCourse(HttpServletRequest request, HttpServletResponse response) {
		String sno =request.getParameter("sno"); 
		String tno =request.getParameter("tno");
		String studentname=request.getParameter("studentname");
		String teachername=request.getParameter("teachername");
		String courseid=request.getParameter("courseId");
		String coursename=request.getParameter("courseName");
		String remark=request.getParameter("remark");
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10){pageSize=18;}
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		List<SelectedCourse> courseList = selectedCourseDao.FindSelectedCourseList(sno,tno,studentname,teachername,courseid,coursename,remark,new Page(currentPage, pageSize));
		int total = selectedCourseDao.FindSelectedCourseListTotal(sno,tno,studentname,teachername,courseid,coursename,remark);
		selectedCourseDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", courseList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(courseList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ǰ������Աѡ�ν��棬�ɻ�ȡ���ݿ�������Ϣ
	private void toSelectedCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/selectedcourse/selectedcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ȡѡ�μ�¼
	private void getSelectedCourse(HttpServletRequest request,HttpServletResponse response) {
		Integer currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = request.getParameter("rows") == null ? 999 : Integer.parseInt(request.getParameter("rows"));
		if(pageSize==10){pageSize=18;}
		SelectedCourse selectedCourse = new SelectedCourse();
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		List<SelectedCourse> courseList = selectedCourseDao.getSelectedCourseList(selectedCourse, new Page(currentPage, pageSize));
		int total = selectedCourseDao.getSelectedCourseListTotal(selectedCourse);
		selectedCourseDao.Close();
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", total);
		ret.put("rows", courseList);
		try {
			String from = request.getParameter("from");
			if("combox".equals(from)){
				response.getWriter().write(JSONArray.fromObject(courseList).toString());
			}else{
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//���ѡ�μ�¼
	private void addSelectedCourse(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String msg = "success";
		String tno = request.getParameter("tno");
		String sno = request.getParameter("sno");
		String courseid = request.getParameter("courseid");
		Double score=00.0;
		try {
			score = Double.parseDouble(request.getParameter("score"));
		}catch(NumberFormatException e){
			if(!(request.getParameter("score").equals(""))){
				msg = "ScoreError";
				response.getWriter().write(msg);
				return;	
			}	
		}
		String remark = request.getParameter("remark");
		//��������ͳɼ��Ƿ��Ӧ
		if(!(StringUtil.isEmpty(request.getParameter("score")))) {
			if((score>=90&&(!remark.equals("����")))||(score<=89&&score>=80&&(!remark.equals("����")))||(score<=79&&score>=60&&(!remark.equals("����")))||(score<=59&&(!remark.equals("������")))){
				msg = "remarkerror";
				response.getWriter().write(msg);
				return;
			}
		}else {
			remark="�޳ɼ�";
		}
		CourseDao courseDao = new CourseDao();
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		//���Ҹ���ʦ�Ƿ��н�ѧ���ſ�
		if(!(teachCourseDao.haveTeach(tno, courseid))) {
			teachCourseDao.Close();
			msg = "NoTeacherTeach";
			response.getWriter().write(msg);
			return;
		}		
		//����Ƿ������п�
		String previous = courseDao.havePrevious(courseid);
		//������пβ�Ϊ������Ҹ�ѧ���Ƿ��������п�
		if(!(previous.equals("��"))) {
			//����ѧ���Ƿ���ѡ���п�
			if(!(selectedCourseDao.selectedPrevious(sno,previous))){
				selectedCourseDao.Close();
				msg = "PreviousError";
				response.getWriter().write(msg);
				return;
			}
		}
		//�����Ƿ���ѡ
		if(selectedCourseDao.isSelected(sno, courseid)){
			selectedCourseDao.Close();
			msg = "courseSelected";
			response.getWriter().write(msg);
			return;
		}
		//ѡ��γ�
		SelectedCourse selectedCourse = new SelectedCourse();
		selectedCourse.setTno(tno);
		selectedCourse.setSno(sno);
		selectedCourse.setCourseid(courseid);
		selectedCourse.setScore(score);
		selectedCourse.setRemark(remark);
		if(selectedCourseDao.addSelectedCourse(selectedCourse)){
			msg = "success";
		}
		selectedCourseDao.Close();
		response.getWriter().write(msg);
	}
	//�༭ѡ�μ�¼
	private void editSelectedCourse(HttpServletRequest request,HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Double score=-1.00;
		if(!StringUtil.isEmpty(request.getParameter("score"))) {
			score = Double.parseDouble(request.getParameter("score"));
		}
		String msg="success";
		//����Ƿ�ΪС��
		try {
			score = Double.parseDouble(request.getParameter("score"));
		}catch(NumberFormatException e){
			if(!(request.getParameter("score").equals(""))){
				msg = "ScoreError";
				response.getWriter().write(msg);
				return;	
			}	
		}
		String remark="";
		//��������ͳɼ��Ƿ��Ӧ
		if(!(StringUtil.isEmpty(request.getParameter("score")))) {
			if(score>=90){
				remark="����";
			}else if(score<=89&&score>=80) {
				remark="����";
			}else if(score<=79&&score>=60) {
				remark="����";
			}else if(score>=0&&score<=59) {
				remark="������";
			}
		}else {
			score=0.00;
			remark="�޳ɼ�";
		}
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		if(!(selectedCourseDao.editSelectedCourse(id,score,remark))){
			msg = "editError";
			response.getWriter().write(msg);
			selectedCourseDao.Close(); 
			return;
		}else {
			response.getWriter().write(msg);
			selectedCourseDao.Close();
			return;
		}
	}
	//ѧ��ѡ��
	private void SelectedTeachCourseSelf(HttpServletRequest request, HttpServletResponse response) {
		String msg = "success";
		HttpSession session = request.getSession();
		Student student = (Student)session.getAttribute("user");
		String sno = student.getSno();
		String courseid = request.getParameter("courseid");
		String tno = request.getParameter("tno");
		Double score=00.0;
		String remark = "�޳ɼ�";
		CourseDao courseDao = new CourseDao();
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		//���Ҹ���ʦ�Ƿ��н�ѧ���ſ�
		if(!(teachCourseDao.haveTeach(tno, courseid))) {
			teachCourseDao.Close();
			msg = "NoTeacherTeach";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}		
		//����Ƿ������п�
		String previous = courseDao.havePrevious(courseid);
		//������пβ�Ϊ������Ҹ�ѧ���Ƿ��������п�
		if(!(previous.equals("��"))) {
			//����ѧ���Ƿ���ѡ���п�
			if(!(selectedCourseDao.selectedPrevious(sno,previous))){
				selectedCourseDao.Close();
				msg = "PreviousError";
				try {
					response.getWriter().write(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		//�����Ƿ���ѡ
		if(selectedCourseDao.isSelected(sno, courseid)){
			selectedCourseDao.Close();
			msg = "courseSelected";
			try {
				response.getWriter().write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//ѡ��γ�
		SelectedCourse selectedCourse = new SelectedCourse();
		selectedCourse.setTno(tno);
		selectedCourse.setSno(sno);
		selectedCourse.setCourseid(courseid);
		selectedCourse.setScore(score);
		selectedCourse.setRemark(remark);
		if(selectedCourseDao.addSelectedCourse(selectedCourse)){
			msg = "success";
		}
		selectedCourseDao.Close();
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ɾ��ѡ�μ�¼
	private void deleteSelectedCourse(HttpServletRequest request,HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		SelectedCourse selectedCourse = selectedCourseDao.getSelectedCourse(id);
		String msg = "success";
		if(selectedCourse == null){
			msg = "not found";
			response.getWriter().write(msg);
			selectedCourseDao.Close(); 
			return;
		}
		if(selectedCourseDao.deleteSelectedCourse(selectedCourse.getId())){
			selectedCourseDao.Close();
			response.getWriter().write(msg); 
		}else{
			msg = "error";
		}
	}
	//����ѡ�μ�¼�����ɼ�
	private void importSelectedCourse(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("111");
		FileUpload fileUpload = new FileUpload(request);
		fileUpload.setFileFormat("xls");//���ø�ʽ
		fileUpload.setFileFormat("xlsx");
		fileUpload.setFileSize(2048);
		response.setCharacterEncoding("UTF-8");
		try {
			InputStream uploadInputStream = fileUpload.getUploadInputStream();
			HSSFWorkbook hssfworkbook = new HSSFWorkbook(uploadInputStream);
			HSSFSheet sheetAt = hssfworkbook.getSheetAt(0);
			int count = 0;
			String errorMsg = "";
			StudentDao studentDao = new StudentDao();
			CourseDao courseDao = new CourseDao();
			TeachCourseDao teachCourseDao = new TeachCourseDao();
			SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
			for(int rowNum = 1;rowNum <= sheetAt.getLastRowNum();rowNum++) {
				HSSFRow row = sheetAt.getRow(rowNum);
				HSSFCell cell = row.getCell(0);
				//��ȡ��0�У�ѧ��
				if(cell == null) {
					errorMsg += "��" + rowNum + "��ѧ��idȱʧ��\n";
					continue;
				}
				if(cell.getCellType() != cell.CELL_TYPE_STRING){
					errorMsg += "��" + rowNum + "��ѧ��id���Ͳ����ַ�����\n";
					continue;
				}
				String sno = cell.getStringCellValue();
				//��ȡ��1�У���ʦ��
				cell = row.getCell(1);
				if(cell == null) {
					errorMsg += "��" + rowNum + "�н�ʦidȱʧ��\n";
					continue;
				}
				if(cell.getCellType() != cell.CELL_TYPE_STRING){
					errorMsg += "��" + rowNum + "�н�ʦid���Ͳ����ַ�����\n";
					continue;
				}
				String tno = cell.getStringCellValue();
				//��ȡ��2�У��γ�id
				cell = row.getCell(2);
				if(cell == null){
					errorMsg += "��" + rowNum + "�пγ�idȱʧ��\n";
					continue;
				}
				if(cell.getCellType() != cell.CELL_TYPE_STRING){
					errorMsg += "��" + rowNum + "�пγ�id�����ַ�����\n";
					continue;
				}
				String courseid = cell.getStringCellValue();
				//��ȡ��3�У��ɼ�
				cell = row.getCell(3);
				double scoreValue=0.00;
				if(cell != null){
					if(cell.getCellType() != cell.CELL_TYPE_NUMERIC){
						errorMsg += "��" + rowNum + "�пγ�id�������֣�\n";
						continue;
					}
					scoreValue = cell.getNumericCellValue();
				}
				//��ȡ��4�У�����
				cell = row.getCell(4);
				String remark = null;
				if(cell != null){
					remark = cell.getStringCellValue();
				}
				remark = "�޳ɼ�";
				//������޸�ѧ����Ϣ
				if(!(studentDao.getStudent(sno))){
					errorMsg += "��" + rowNum + "��ѧ��id�����ڣ�\n";
					continue;
				}
				//������޿γ���Ϣ
				if(!(courseDao.getCourse(courseid))){
					errorMsg += "��" + rowNum + "�пγ�id�����ڣ�\n";
					continue;
				}
				//��������ڿ���Ϣ
				if(!(teachCourseDao.getTeachCourse(tno,courseid))){
					errorMsg += "��" + rowNum + "�пγ�id�����ڣ�\n";
					continue;
				}
				//�ÿγ��Ƿ���ѡ
				if(!selectedCourseDao.isSelected(sno, courseid)){
					errorMsg += "��" + rowNum + "�пγ̸�ͬѧδѡ�����Ϸ���\n";
					continue;
				}
				SelectedCourse selectedCourse = new SelectedCourse();
				selectedCourse.setCourseid(courseid);
				selectedCourse.setRemark(remark);
				selectedCourse.setScore(scoreValue);
				selectedCourse.setSno(sno);
				selectedCourse.setTno(tno);
				if(selectedCourseDao.addSelectedCourse(selectedCourse)){
					count++;
				}
			}
			errorMsg += "�ɹ�¼��" + count + "���ɼ���Ϣ��";
			studentDao.Close();
			courseDao.Close();
			selectedCourseDao.Close();
			selectedCourseDao.Close();
			try {
				response.getWriter().write("<div id='message'>"+errorMsg+"</div>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ProtocolException e) {
			try {
				response.getWriter().write("<div id='message'>�ϴ�Э�����</div>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}catch (NullFileException e1) {
			try {
				response.getWriter().write("<div id='message'>�ϴ����ļ�Ϊ��!</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
		}
		catch (SizeException e2) {
			try {
				response.getWriter().write("<div id='message'>�ϴ��ļ���С���ܳ���"+fileUpload.getFileSize()+"��</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e2.printStackTrace();
		}
		catch (IOException e3) {
			try {
				response.getWriter().write("<div id='message'>��ȡ�ļ�����</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e3.printStackTrace();
		}
		catch (FileFormatException e4) {
			try {
				response.getWriter().write("<div id='message'>�ϴ��ļ���ʽ����ȷ�����ϴ� "+fileUpload.getFileFormat()+" ��ʽ���ļ���</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e4.printStackTrace();
		}
		catch (FileUploadException e5) {
			try {
				response.getWriter().write("<div id='message'>�ϴ��ļ�ʧ�ܣ�</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e5.printStackTrace();
		}
	}
	//�����ɼ�
	private void exportSelectedCourse(HttpServletRequest request,HttpServletResponse response) {
		SelectedCourse selectedCourse = new SelectedCourse();
		try {
			//response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("score_list_sid_"+sno+"_cid_"+courseId+".xls", "UTF-8"));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/octet-stream");
			ServletOutputStream outputStream = response.getOutputStream();
			SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
			List<SelectedCourse> selectedCourseList = selectedCourseDao.getSelectedCourseList(selectedCourse);
			selectedCourseDao.Close();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			HSSFSheet createSheet = hssfWorkbook.createSheet("�ɼ��б�");
			HSSFRow createRow = createSheet.createRow(0);
			createRow.createCell(0).setCellValue("ѧ��");
			createRow.createCell(1).setCellValue("��ʦ");
			createRow.createCell(2).setCellValue("�γ�");
			createRow.createCell(3).setCellValue("�ɼ�");
			createRow.createCell(4).setCellValue("����");
			//ʵ�ֽ�����װ�뵽excel�ļ���
			int row = 1;
			for(SelectedCourse entry:selectedCourseList){
				createRow = createSheet.createRow(row++);
				createRow.createCell(0).setCellValue(entry.getSno());
				createRow.createCell(1).setCellValue(entry.getTno());
				createRow.createCell(2).setCellValue(entry.getCourseid());
				createRow.createCell(3).setCellValue(new Double(entry.getScore()+""));
				createRow.createCell(4).setCellValue(entry.getRemark());
			}
			hssfWorkbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
