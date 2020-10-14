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

	//转到doPost()方法
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	//获取前端响应的请求
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
	//前往选择教学课程的学生信息
	private void toTeachStudent(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/selectedcourse/toteachstudent.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//获取教学课程的学生信息
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
	//学生已选课程界面
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
	//前往学生已选课程界面
	private void toSelectedCourseSelf(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/selectedcourse/selectedcourseself.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//查询选课
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
	//前往管理员选课界面，可获取数据库所有信息
	private void toSelectedCourseList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("views/selectedcourse/selectedcourse.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//获取选课记录
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
	//添加选课记录
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
		//检查评级和成绩是否对应
		if(!(StringUtil.isEmpty(request.getParameter("score")))) {
			if((score>=90&&(!remark.equals("优秀")))||(score<=89&&score>=80&&(!remark.equals("良好")))||(score<=79&&score>=60&&(!remark.equals("及格")))||(score<=59&&(!remark.equals("不及格")))){
				msg = "remarkerror";
				response.getWriter().write(msg);
				return;
			}
		}else {
			remark="无成绩";
		}
		CourseDao courseDao = new CourseDao();
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		//查找该老师是否有教学这门课
		if(!(teachCourseDao.haveTeach(tno, courseid))) {
			teachCourseDao.Close();
			msg = "NoTeacherTeach";
			response.getWriter().write(msg);
			return;
		}		
		//检查是否有先行课
		String previous = courseDao.havePrevious(courseid);
		//如果先行课不为无则查找该学生是否有上先行课
		if(!(previous.equals("无"))) {
			//查找学生是否已选先行课
			if(!(selectedCourseDao.selectedPrevious(sno,previous))){
				selectedCourseDao.Close();
				msg = "PreviousError";
				response.getWriter().write(msg);
				return;
			}
		}
		//查找是否已选
		if(selectedCourseDao.isSelected(sno, courseid)){
			selectedCourseDao.Close();
			msg = "courseSelected";
			response.getWriter().write(msg);
			return;
		}
		//选择课程
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
	//编辑选课记录
	private void editSelectedCourse(HttpServletRequest request,HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Double score=-1.00;
		if(!StringUtil.isEmpty(request.getParameter("score"))) {
			score = Double.parseDouble(request.getParameter("score"));
		}
		String msg="success";
		//检查是否为小数
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
		//检查评级和成绩是否对应
		if(!(StringUtil.isEmpty(request.getParameter("score")))) {
			if(score>=90){
				remark="优秀";
			}else if(score<=89&&score>=80) {
				remark="良好";
			}else if(score<=79&&score>=60) {
				remark="及格";
			}else if(score>=0&&score<=59) {
				remark="不及格";
			}
		}else {
			score=0.00;
			remark="无成绩";
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
	//学生选课
	private void SelectedTeachCourseSelf(HttpServletRequest request, HttpServletResponse response) {
		String msg = "success";
		HttpSession session = request.getSession();
		Student student = (Student)session.getAttribute("user");
		String sno = student.getSno();
		String courseid = request.getParameter("courseid");
		String tno = request.getParameter("tno");
		Double score=00.0;
		String remark = "无成绩";
		CourseDao courseDao = new CourseDao();
		SelectedCourseDao selectedCourseDao = new SelectedCourseDao();
		TeachCourseDao teachCourseDao = new TeachCourseDao();
		//查找该老师是否有教学这门课
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
		//检查是否有先行课
		String previous = courseDao.havePrevious(courseid);
		//如果先行课不为无则查找该学生是否有上先行课
		if(!(previous.equals("无"))) {
			//查找学生是否已选先行课
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
		//查找是否已选
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
		//选择课程
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
	//删除选课记录
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
	//导入选课记录包括成绩
	private void importSelectedCourse(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("111");
		FileUpload fileUpload = new FileUpload(request);
		fileUpload.setFileFormat("xls");//设置格式
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
				//获取第0列，学号
				if(cell == null) {
					errorMsg += "第" + rowNum + "行学生id缺失！\n";
					continue;
				}
				if(cell.getCellType() != cell.CELL_TYPE_STRING){
					errorMsg += "第" + rowNum + "行学生id类型不是字符串！\n";
					continue;
				}
				String sno = cell.getStringCellValue();
				//获取第1列，教师号
				cell = row.getCell(1);
				if(cell == null) {
					errorMsg += "第" + rowNum + "行教师id缺失！\n";
					continue;
				}
				if(cell.getCellType() != cell.CELL_TYPE_STRING){
					errorMsg += "第" + rowNum + "行教师id类型不是字符串！\n";
					continue;
				}
				String tno = cell.getStringCellValue();
				//获取第2列，课程id
				cell = row.getCell(2);
				if(cell == null){
					errorMsg += "第" + rowNum + "行课程id缺失！\n";
					continue;
				}
				if(cell.getCellType() != cell.CELL_TYPE_STRING){
					errorMsg += "第" + rowNum + "行课程id不是字符串！\n";
					continue;
				}
				String courseid = cell.getStringCellValue();
				//获取第3列，成绩
				cell = row.getCell(3);
				double scoreValue=0.00;
				if(cell != null){
					if(cell.getCellType() != cell.CELL_TYPE_NUMERIC){
						errorMsg += "第" + rowNum + "行课程id不是数字！\n";
						continue;
					}
					scoreValue = cell.getNumericCellValue();
				}
				//获取第4列，评级
				cell = row.getCell(4);
				String remark = null;
				if(cell != null){
					remark = cell.getStringCellValue();
				}
				remark = "无成绩";
				//检查有无该学生信息
				if(!(studentDao.getStudent(sno))){
					errorMsg += "第" + rowNum + "行学生id不存在！\n";
					continue;
				}
				//检查有无课程信息
				if(!(courseDao.getCourse(courseid))){
					errorMsg += "第" + rowNum + "行课程id不存在！\n";
					continue;
				}
				//检查有无授课信息
				if(!(teachCourseDao.getTeachCourse(tno,courseid))){
					errorMsg += "第" + rowNum + "行课程id不存在！\n";
					continue;
				}
				//该课程是否已选
				if(!selectedCourseDao.isSelected(sno, courseid)){
					errorMsg += "第" + rowNum + "行课程该同学未选，不合法！\n";
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
			errorMsg += "成功录入" + count + "条成绩信息！";
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
				response.getWriter().write("<div id='message'>上传协议错误！</div>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}catch (NullFileException e1) {
			try {
				response.getWriter().write("<div id='message'>上传的文件为空!</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
		}
		catch (SizeException e2) {
			try {
				response.getWriter().write("<div id='message'>上传文件大小不能超过"+fileUpload.getFileSize()+"！</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e2.printStackTrace();
		}
		catch (IOException e3) {
			try {
				response.getWriter().write("<div id='message'>读取文件出错！</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e3.printStackTrace();
		}
		catch (FileFormatException e4) {
			try {
				response.getWriter().write("<div id='message'>上传文件格式不正确，请上传 "+fileUpload.getFileFormat()+" 格式的文件！</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e4.printStackTrace();
		}
		catch (FileUploadException e5) {
			try {
				response.getWriter().write("<div id='message'>上传文件失败！</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			e5.printStackTrace();
		}
	}
	//导出成绩
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
			HSSFSheet createSheet = hssfWorkbook.createSheet("成绩列表");
			HSSFRow createRow = createSheet.createRow(0);
			createRow.createCell(0).setCellValue("学生");
			createRow.createCell(1).setCellValue("教师");
			createRow.createCell(2).setCellValue("课程");
			createRow.createCell(3).setCellValue("成绩");
			createRow.createCell(4).setCellValue("评级");
			//实现将数据装入到excel文件中
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
