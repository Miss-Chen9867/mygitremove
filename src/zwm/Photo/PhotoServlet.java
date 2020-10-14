package zwm.Photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;

import com.github.pagehelper.util.StringUtil;
import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;

import zwm.Student.Student;
import zwm.Student.StudentDao;
import zwm.Teacher.Teacher;
import zwm.Teacher.TeacherDao;

public class PhotoServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("getPhoto".equals(method)){
			getPhoto(request,response);
		}else if("SetPhoto".equals(method)){
			uploadPhoto(request,response);
		}else if("getsPhoto".equals(method)){
			getSnoPhoto(request,response);
		}else if("gettPhoto".equals(method)){
			getTnoPhoto(request,response);
		}
	}
	//�ϴ�ͷ��
	private void uploadPhoto(HttpServletRequest request,HttpServletResponse response) {
		String sno = request.getParameter("sno");
		String tno = request.getParameter("tno");
		FileUpload fileUpload = new FileUpload(request);//�ļ��ϴ�
		fileUpload.setFileFormat("jpg");//�ļ��ϴ���ʽ
		fileUpload.setFileFormat("png");
		fileUpload.setFileFormat("jpeg");
		fileUpload.setFileFormat("gif");
		fileUpload.setFileSize(2048);//�ļ���С
		response.setCharacterEncoding("UTF-8");
		try {
			InputStream uploadInputStream = fileUpload.getUploadInputStream();//��ȡ�ļ�IO��
			if(!(StringUtil.isEmpty(sno))){
				Student student = new Student();
				student.setSno(sno);
				student.setPhoto(uploadInputStream);
				StudentDao studentDao = new StudentDao();
				if(studentDao.setStudentPhoto(student)){
					response.getWriter().write("<div id='message'>�ϴ��ɹ���</div>");
				}else{
					response.getWriter().write("<div id='message'>�ϴ�ʧ�ܣ�</div>");
				}
			}
			if(!(StringUtil.isEmpty(tno))){
				Teacher teacher = new Teacher();
				teacher.setTno(tno);
				teacher.setPhoto(uploadInputStream);
				TeacherDao teacherDao = new TeacherDao();
				if(teacherDao.setTeacherPhoto(teacher)){
					response.getWriter().write("<div id='message'>�ϴ��ɹ���</div>");
				}else{
					response.getWriter().write("<div id='message'>�ϴ�ʧ�ܣ�</div>");
				}
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
	//��ȡͷ��ȫ��
	private void getPhoto(HttpServletRequest request,HttpServletResponse response) {
			String sno = request.getParameter("sno");
			String tno = request.getParameter("tno");
			if(!(StringUtil.isEmpty(sno))){
				//ѧ��
				StudentDao studentDao = new StudentDao();
				Student student = studentDao.getStudent1(sno);
				studentDao.Close();
				if(student != null){
					InputStream photo = student.getPhoto();
					if(photo != null){
						try {
							byte[] b = new byte[photo.available()];
							photo.read(b);
							response.getOutputStream().write(b,0,b.length);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
			if(!(StringUtil.isEmpty(tno))){
				//��ʦ
				TeacherDao teacherDao = new TeacherDao();
				Teacher teacher = teacherDao.getTeacher1(tno);
				teacherDao.Close();
				if(teacher != null){
					InputStream photo = teacher.getPhoto();
					if(photo != null){
						try {
							byte[] b = new byte[photo.available()];
							photo.read(b);
							response.getOutputStream().write(b,0,b.length);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
			String path = request.getSession().getServletContext().getRealPath("");
			File file = new File(path+"\\file\\please.jpg");
			try {
				FileInputStream fis = new FileInputStream(file);
				byte[] b = new byte[fis.available()];
				fis.read(b);
				response.getOutputStream().write(b,0,b.length);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	//��ȡͷ��ѧ��
	private void getSnoPhoto(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		Student student1 = (Student)session.getAttribute("user");
		String sno = student1.getSno();
		String tno="";
		if(!(StringUtil.isEmpty(sno))){
			//ѧ��
			StudentDao studentDao = new StudentDao();
			Student student = studentDao.getStudent1(sno);
			studentDao.Close();
			if(student != null){
				InputStream photo = student.getPhoto();
				if(photo != null){
					try {
						byte[] b = new byte[photo.available()];
						photo.read(b);
						response.getOutputStream().write(b,0,b.length);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
		if(!(StringUtil.isEmpty(tno))){
			//��ʦ
			TeacherDao teacherDao = new TeacherDao();
			Teacher teacher = teacherDao.getTeacher1(tno);
			teacherDao.Close();
			if(teacher != null){
				InputStream photo = teacher.getPhoto();
				if(photo != null){
					try {
						byte[] b = new byte[photo.available()];
						photo.read(b);
						response.getOutputStream().write(b,0,b.length);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
		String path = request.getSession().getServletContext().getRealPath("");
		File file = new File(path+"\\file\\please.jpg");
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			response.getOutputStream().write(b,0,b.length);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//��ȡͷ����ʦ
	private void getTnoPhoto(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		Teacher teacher1 = (Teacher)session.getAttribute("user");
		String sno = "";
		String tno = teacher1.getTno();
			if(!(StringUtil.isEmpty(sno))){
				//ѧ��
				StudentDao studentDao = new StudentDao();
				Student student = studentDao.getStudent1(sno);
				studentDao.Close();
				if(student != null){
					InputStream photo = student.getPhoto();
					if(photo != null){
						try {
							byte[] b = new byte[photo.available()];
							photo.read(b);
							response.getOutputStream().write(b,0,b.length);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
			if(!(StringUtil.isEmpty(tno))){
				//��ʦ
				TeacherDao teacherDao = new TeacherDao();
				Teacher teacher = teacherDao.getTeacher1(tno);
				teacherDao.Close();
				if(teacher != null){
					InputStream photo = teacher.getPhoto();
					if(photo != null){
						try {
							byte[] b = new byte[photo.available()];
							photo.read(b);
							response.getOutputStream().write(b,0,b.length);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
			String path = request.getSession().getServletContext().getRealPath("");
			File file = new File(path+"\\file\\please.jpg");
			try {
				FileInputStream fis = new FileInputStream(file);
				byte[] b = new byte[fis.available()];
				fis.read(b);
				response.getOutputStream().write(b,0,b.length);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
