package zwm.Clazz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zwm.Database.DBOperation;
import zwm.Login.String.StringUtil;
import zwm.Student.Student;
import zwm.Teacher.Teacher;
import zwm.page.Page;

public class ClazzDao extends DBOperation{
	
	//����
	public boolean addClazz(Clazz clazz){
		//���Ұ༶�Ƿ����
				String sql1 = "select clazzid from clazz";
				ResultSet resultSet1 = Query(sql1);
				try {
					while(resultSet1.next()){
						Clazz cl = new Clazz();
						cl.setClazzid(resultSet1.getString("clazzid"));
						if(clazz.getClazzid().equals(cl.getClazzid())) {
							return false;
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
						//��ѯid���ֵ
						boolean kkk = true;
						String ss = "select id from clazz";
						int max = 0;
						int[] flag = new int[99999999];
						for(int i=0;i<99999999;i++) {
							flag[i] = 0;
						}
						ResultSet resultSet = Query(ss);
						try {
							while(resultSet.next()){
								flag[resultSet.getInt("id")] = 1;
								if(resultSet.getInt("id")>max) {
									max = resultSet.getInt("id");
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						//����м���һ��©�ĺ���Ĳ���
						for(int i=1;i<=max;i++) {
							if(flag[i]!=1) {
								for(int j=i+1;j<=max;j++) {
									String s0 = "update clazz set id=id-1 where id="+j;
									Update(s0);
								}
								kkk = false;
								break;
							}
						}
						if(max == 0 || kkk)
							max++;
						clazz.setId(max);
				//��Ӽ�¼
				String sql2 = "insert into clazz values('"+clazz.getId()+"','"+clazz.getClazzid()+"','"+clazz.getGrade()+"','"+clazz.getAcademic()+"','"+clazz.getMajor()+"','"+clazz.getName()+"') ";
				return Update(sql2);
	}
	
	//ɾ��
	public boolean deleteClazz(String ci){
		//����༶������ѧ��
		String sql = "select * from student where clazzid= '"+ci+"'";
		ResultSet resultset = Query(sql);
		try {
			while(resultset.next()) {
				Student student = new Student();
				student.setClazzid(resultset.getString("clazzid"));
				if(!StringUtil.isEmpty(student.getClazzid()))
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql1 = "delete from clazz where clazzid = '"+ci+"'";
		return Update(sql1);
	}
	
	//�������а༶
	public List<Clazz> getAllClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setClazzid(resultSet.getString("clazzid"));
				cl.setGrade(resultSet.getString("grade"));
				cl.setMajor(resultSet.getString("major"));
				cl.setName(resultSet.getString("name"));
				cl.setAcademic(resultSet.getString("academic"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}		
	
	//���Ҵ�һ�༶
	public List<Clazz> getOneClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='��һ'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setClazzid(resultSet.getString("clazzid"));
				cl.setGrade(resultSet.getString("grade"));
				cl.setName(resultSet.getString("name"));
				cl.setMajor(resultSet.getString("major"));
				cl.setAcademic(resultSet.getString("academic"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}		
	
	//���Ҵ���༶
	public List<Clazz> getTwoClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='���'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setClazzid(resultSet.getString("clazzid"));
				cl.setGrade(resultSet.getString("grade"));
				cl.setMajor(resultSet.getString("major"));
				cl.setName(resultSet.getString("name"));
				cl.setAcademic(resultSet.getString("academic"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}	
	
	//���Ҵ����༶
	public List<Clazz> getThreeClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='����'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setClazzid(resultSet.getString("clazzid"));
				cl.setMajor(resultSet.getString("major"));
				cl.setGrade(resultSet.getString("grade"));
				cl.setName(resultSet.getString("name"));
				cl.setAcademic(resultSet.getString("academic"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}	
	
	//���Ҵ��İ༶
	public List<Clazz> getFourClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='����'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//�ӵ�0��������10������
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				Clazz cl = new Clazz();
				cl.setId(resultSet.getInt("id"));
				cl.setClazzid(resultSet.getString("clazzid"));
				cl.setMajor(resultSet.getString("major"));
				cl.setGrade(resultSet.getString("grade"));
				cl.setName(resultSet.getString("name"));
				cl.setAcademic(resultSet.getString("academic"));
				ret.add(cl);
			}
		} catch (SQLException e) {
		}
		return ret;
	}	
	
	//�޸�
	public boolean editClazz(Clazz clazz) {
		String sql = "update clazz set clazzid = '"+clazz.getClazzid()+"',grade = '"+clazz.getGrade()+"',name = '"+clazz.getName()+"',academic = '"+clazz.getAcademic()+"' where id = " + clazz.getId();
		return Update(sql);
	}
	//��ȡ��¼����
	public int getAllClazzListTotal(Clazz clazz){
		int total = 0;
		String sql = "select count(*) as total from clazz ";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select count(*) as total from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public int getOneClazzListTotal(Clazz clazz){
		int total = 0;
		String sql = "select count(*) as total from clazz where grade='��һ'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select count(*) as total from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public int getTwoClazzListTotal(Clazz clazz){
		int total = 0;
		String sql = "select count(*) as total from clazz where grade='���'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select count(*) as total from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public int getThreeClazzListTotal(Clazz clazz){
		int total = 0;
		String sql = "select count(*) as total from clazz where grade='����'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select count(*) as total from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	public int getFourClazzListTotal(Clazz clazz){
		int total = 0;
		String sql = "select count(*) as total from clazz where grade='����'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select count(*) as total from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select count(*) as total from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		ResultSet resultSet = Query(sql);
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public Clazz getSpecialClazz(String clazzid) {
		//��ѯָ����ŵİ༶��Ϣ
		String sql = "select * from clazz where clazzid="+clazzid;
		Clazz clazz = new Clazz();
		ResultSet rs = Query(sql);
		try {
			while(rs.next()) {
				clazz.setAcademic(rs.getString("academic"));
				clazz.setMajor(rs.getString("major"));
				clazz.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	//��ѯ�༶
	public List<Clazz> FindClazzList(String grade, String academic, String major, String clazz,String clazzid, Page page) {
				List<Clazz> ret = new ArrayList<Clazz>();
				String sql = "select * from clazz";
				if(!(StringUtil.isEmpty(clazzid))) {
					sql += " and clazzid='"+clazzid+"'";
				}
				if(!StringUtil.isEmpty(grade)){
					if(grade.equals("��һ")) {
						sql += " and clazzid like '1%'";
					}else if(grade.equals("���")) {
						sql += " and clazzid like '2%'";
					}else if(grade.equals("����")) {
						sql += " and clazzid like '3%'";
					}else if(grade.equals("����")) {
						sql += " and clazzid like '4%'";
					}
				}
				if(!StringUtil.isEmpty(academic)){
					if(academic.equals("���������ѧԺ")) {
						sql += " and substr(clazzid,2,2)='01'";
					}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
						sql += " and substr(clazzid,2,2)='02'";
					}else if(academic.equals("��������ӹ���ѧԺ")) {
						sql += " and substr(clazzid,2,2)='03'";
					}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
						sql += " and substr(clazzid,2,2)='04'";
					}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
						sql += " and substr(clazzid,2,2)='05'";
					}else if(academic.equals("������ѧ������ѧԺ")) {
						sql += " and substr(clazzid,2,2)='06'";
					}else if(academic.equals("������ѧѧԺ")) {
						sql += " and substr(clazzid,2,2)='07'";
					}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
						sql += " and substr(clazzid,2,2)='08'";
					}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
						sql += " and substr(clazzid,2,2)='09'";
					}else if(academic.equals("�����ѧԺ")) {
						sql += " and substr(clazzid,2,2)='10'";
					}else if(academic.equals("�����빫������ѧԺ")) {
						sql += " and substr(clazzid,2,2)='11'";
					}else if(academic.equals("���ʽ���ѧԺ")) {
						sql += " and substr(clazzid,2,2)='12'";
					}else if(academic.equals("����ѧԺ")) {
						sql += " and substr(clazzid,2,2)='13'";
					}else if(academic.equals("����ѧԺ")) {
						sql += " and substr(clazzid,2,2)='14'";
					}else if(academic.equals("��ѧԺ")) {
						sql += " and substr(clazzid,2,2)='15'";
					}else if(academic.equals("��ѧԺ")) {
						sql += " and substr(clazzid,2,2)='16'";
					}else if(academic.equals("�������������ѧԺ")) {
						sql += " and substr(clazzid,2,2)='17'";
					}else if(academic.equals("��ľ��������ѧԺ")) {
						sql += " and substr(clazzid,2,2)='18'";
					}else if(academic.equals("�����봫��ѧԺ")) {
						sql += " and substr(clazzid,2,2)='19'";
					}
				}
				if(!StringUtil.isEmpty(major)){
					//���� ��������ƥ��
					if(major.equals("���̹���רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("�г�Ӫ��רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("������Դרҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("�������רҵ")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("���ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("���ʾ�����ó��רҵ")) {
						sql += " and substr(clazzid,4,1)='6'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='7'";
					}else if(major.equals("����רҵ")) {
						sql += " and substr(clazzid,4,1)='8'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='9'";
					}else if(major.equals("��ѧ��Ӧ����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("ͳ��ѧרҵ ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("���ڹ���רҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("���ڿ�ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("��Ϣ������ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("������Ϣ��ѧ�뼼��רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("�Զ���רҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("�����Ϣ��ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("�������ѧ�뼼��רҵ ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("�����ݹ���רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("����������רҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("�������רҵ")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("���繤��רҵ")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("��ѧ�����빤��רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("��Դ��ѧ����רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("��ҩ����רҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("ҩ���Ƽ�רҵ")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("ҩѧרҵ")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("�����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("������Ϣ��ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("��湤��רҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("ѧǰ����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("�Ļ���ҵ����רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("���Ľ���רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("���＼��רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("԰��רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("Ӣ��רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("����Ӣ��רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("����רҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("��ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("������ʽ���רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("����רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("����רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("��������רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("�������רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("�й���ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("��ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("���ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("��ҵ���רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("ƽ�����רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("��ľ����רҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("������������ԴӦ�ù���רҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("���ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("����ѧרҵ")) {
						sql += " and substr(clazzid,4,1)='3'";
					}
			   }
				if(!(StringUtil.isEmpty(clazz))) {
					if(clazz.equals("1��")) {
						sql += " and substr(clazzid,5,1)='1'";
					}else if(clazz.equals("2��")) {
						sql += " and substr(clazzid,5,1)='2'";
					}else if(clazz.equals("3��")) {
						sql += " and substr(clazzid,5,1)='3'";
					}else if(clazz.equals("4��")) {
						sql += " and substr(clazzid,5,1)='4'";
					}
				}
				sql += " limit " + page.getStart() + "," + page.getPageSize();
				ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
				try {
					while(resultSet.next()){
						Clazz cl = new Clazz();
						cl.setId(resultSet.getInt("id"));
						cl.setClazzid(resultSet.getString("clazzid"));
						cl.setGrade(resultSet.getString("grade"));
						cl.setMajor(resultSet.getString("major"));
						cl.setName(resultSet.getString("name"));
						cl.setAcademic(resultSet.getString("academic"));
						ret.add(cl);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return ret;
		}
		//���ز�ѯ����
		public int getFindClazzListTotal(String grade, String academic, String major, String clazz,String clazzid) {
			int total = 0;
			String sql = "select count(*) as total from clazz";
			if(!(StringUtil.isEmpty(clazzid))) {
				sql += " and clazzid ='"+clazzid+"'";
			}
			if(!StringUtil.isEmpty(grade)){
				if(grade.equals("��һ")) {
					sql += " and clazzid like '1%'";
				}else if(grade.equals("���")) {
					sql += " and clazzid like '2%'";
				}else if(grade.equals("����")) {
					sql += " and clazzid like '3%'";
				}else if(grade.equals("����")) {
					sql += " and clazzid like '4%'";
				}
			}
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("���������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='01'";
				}else if(academic.equals("��ѧ��ͳ��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='02'";
				}else if(academic.equals("��������ӹ���ѧԺ")) {
					sql += " and substr(clazzid,2,2)='03'";
				}else if(academic.equals("�������ѧ�뼼��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='04'";
				}else if(academic.equals("��ѧ����ҩ����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='05'";
				}else if(academic.equals("������ѧ������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='06'";
				}else if(academic.equals("������ѧѧԺ")) {
					sql += " and substr(clazzid,2,2)='07'";
				}else if(academic.equals("��ʷ�Ļ�ѧԺ")) {
					sql += " and substr(clazzid,2,2)='08'";
				}else if(academic.equals("������ѧ�뼼��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='09'";
				}else if(academic.equals("�����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='10'";
				}else if(academic.equals("�����빫������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='11'";
				}else if(academic.equals("���ʽ���ѧԺ")) {
					sql += " and substr(clazzid,2,2)='12'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='13'";
				}else if(academic.equals("����ѧԺ")) {
					sql += " and substr(clazzid,2,2)='14'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='15'";
				}else if(academic.equals("��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='16'";
				}else if(academic.equals("�������������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='17'";
				}else if(academic.equals("��ľ��������ѧԺ")) {
					sql += " and substr(clazzid,2,2)='18'";
				}else if(academic.equals("�����봫��ѧԺ")) {
					sql += " and substr(clazzid,2,2)='19'";
				}
			}
			if(!StringUtil.isEmpty(major)){
				//���� ��������ƥ��
				if(major.equals("���̹���רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("�г�Ӫ��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("������Դרҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�������רҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("���ʾ�����ó��רҵ")) {
					sql += " and substr(clazzid,4,1)='6'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='7'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='8'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='9'";
				}else if(major.equals("��ѧ��Ӧ����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("ͳ��ѧרҵ ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("���ڹ���רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("���ڿ�ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("��Ϣ������ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("������Ϣ��ѧ�뼼��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("�Զ���רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�����Ϣ��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("�������ѧ�뼼��רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("�����ݹ���רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("����������רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�������רҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("���繤��רҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("��ѧ�����빤��רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("��Դ��ѧ����רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ҩ����רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("ҩ���Ƽ�רҵ")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("ҩѧרҵ")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("�����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("������Ϣ��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��湤��רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("ѧǰ����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("�Ļ���ҵ����רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("���Ľ���רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("���＼��רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("԰��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("Ӣ��רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����Ӣ��רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("������ʽ���רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��������רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("�������רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("�й���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ҵ���רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("ƽ�����רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("��ľ����רҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("������������ԴӦ�ù���רҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("���ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("����ѧרҵ")) {
					sql += " and substr(clazzid,4,1)='3'";
				}
		   }
			if(!(StringUtil.isEmpty(clazz))) {
				if(clazz.equals("1��")) {
					sql += " and substr(clazzid,5,1)='1'";
				}else if(clazz.equals("2��")) {
					sql += " and substr(clazzid,5,1)='2'";
				}else if(clazz.equals("3��")) {
					sql += " and substr(clazzid,5,1)='3'";
				}else if(clazz.equals("4��")) {
					sql += " and substr(clazzid,5,1)='4'";
				}
			}
			ResultSet resultSet = Query(sql.replaceFirst("and", "where"));
			try {
				while(resultSet.next()){
					total = resultSet.getInt("total");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return total;
		}
}
