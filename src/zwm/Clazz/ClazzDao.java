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
	
	//增加
	public boolean addClazz(Clazz clazz){
		//查找班级是否存在
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
						//查询id最大值
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
						//如果中间有一个漏的后面的补上
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
				//添加记录
				String sql2 = "insert into clazz values('"+clazz.getId()+"','"+clazz.getClazzid()+"','"+clazz.getGrade()+"','"+clazz.getAcademic()+"','"+clazz.getMajor()+"','"+clazz.getName()+"') ";
				return Update(sql2);
	}
	
	//删除
	public boolean deleteClazz(String ci){
		//如果班级下面有学生
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
	
	//查找所有班级
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
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
	
	//查找大一班级
	public List<Clazz> getOneClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='大一'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
	
	//查找大二班级
	public List<Clazz> getTwoClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='大二'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
	
	//查找大三班级
	public List<Clazz> getThreeClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='大三'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
	
	//查找大四班级
	public List<Clazz> getFourClazzList(Clazz clazz,Page page){
		List<Clazz> ret = new ArrayList<Clazz>();
		String sql = "select * from clazz where grade='大四'";
		if(!StringUtil.isEmpty(clazz.getMajor())){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%'";
		}
		if(!StringUtil.isEmpty(clazz.getAcademic())){
			sql = "select * from clazz where academic like '%" + clazz.getAcademic() + "%'";
		}
		if((!StringUtil.isEmpty(clazz.getMajor()))&&(!StringUtil.isEmpty(clazz.getAcademic()))){
			sql = "select * from clazz where major like '%" + clazz.getMajor() + "%' and academic like '%" + clazz.getAcademic() + "%'";
		}
		sql += " limit " + page.getStart() + "," + page.getPageSize();//从第0行跳过哪10条数据
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
	
	//修改
	public boolean editClazz(Clazz clazz) {
		String sql = "update clazz set clazzid = '"+clazz.getClazzid()+"',grade = '"+clazz.getGrade()+"',name = '"+clazz.getName()+"',academic = '"+clazz.getAcademic()+"' where id = " + clazz.getId();
		return Update(sql);
	}
	//获取记录条数
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
		String sql = "select count(*) as total from clazz where grade='大一'";
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
		String sql = "select count(*) as total from clazz where grade='大二'";
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
		String sql = "select count(*) as total from clazz where grade='大三'";
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
		String sql = "select count(*) as total from clazz where grade='大四'";
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
		//查询指定班号的班级信息
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
	//查询班级
	public List<Clazz> FindClazzList(String grade, String academic, String major, String clazz,String clazzid, Page page) {
				List<Clazz> ret = new ArrayList<Clazz>();
				String sql = "select * from clazz";
				if(!(StringUtil.isEmpty(clazzid))) {
					sql += " and clazzid='"+clazzid+"'";
				}
				if(!StringUtil.isEmpty(grade)){
					if(grade.equals("大一")) {
						sql += " and clazzid like '1%'";
					}else if(grade.equals("大二")) {
						sql += " and clazzid like '2%'";
					}else if(grade.equals("大三")) {
						sql += " and clazzid like '3%'";
					}else if(grade.equals("大四")) {
						sql += " and clazzid like '4%'";
					}
				}
				if(!StringUtil.isEmpty(academic)){
					if(academic.equals("经济与管理学院")) {
						sql += " and substr(clazzid,2,2)='01'";
					}else if(academic.equals("数学与统计学院")) {
						sql += " and substr(clazzid,2,2)='02'";
					}else if(academic.equals("物理与电子工程学院")) {
						sql += " and substr(clazzid,2,2)='03'";
					}else if(academic.equals("计算机科学与技术学院")) {
						sql += " and substr(clazzid,2,2)='04'";
					}else if(academic.equals("化学与制药工程学院")) {
						sql += " and substr(clazzid,2,2)='05'";
					}else if(academic.equals("环境科学与旅游学院")) {
						sql += " and substr(clazzid,2,2)='06'";
					}else if(academic.equals("教育科学学院")) {
						sql += " and substr(clazzid,2,2)='07'";
					}else if(academic.equals("历史文化学院")) {
						sql += " and substr(clazzid,2,2)='08'";
					}else if(academic.equals("生命科学与技术学院")) {
						sql += " and substr(clazzid,2,2)='09'";
					}else if(academic.equals("外国语学院")) {
						sql += " and substr(clazzid,2,2)='10'";
					}else if(academic.equals("政治与公共管理学院")) {
						sql += " and substr(clazzid,2,2)='11'";
					}else if(academic.equals("国际教育学院")) {
						sql += " and substr(clazzid,2,2)='12'";
					}else if(academic.equals("音乐学院")) {
						sql += " and substr(clazzid,2,2)='13'";
					}else if(academic.equals("体育学院")) {
						sql += " and substr(clazzid,2,2)='14'";
					}else if(academic.equals("文学院")) {
						sql += " and substr(clazzid,2,2)='15'";
					}else if(academic.equals("法学院")) {
						sql += " and substr(clazzid,2,2)='16'";
					}else if(academic.equals("美术与艺术设计学院")) {
						sql += " and substr(clazzid,2,2)='17'";
					}else if(academic.equals("土木建筑工程学院")) {
						sql += " and substr(clazzid,2,2)='18'";
					}else if(academic.equals("新闻与传播学院")) {
						sql += " and substr(clazzid,2,2)='19'";
					}
				}
				if(!StringUtil.isEmpty(major)){
					//班名 采用正则匹配
					if(major.equals("工商管理专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("市场营销专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("人力资源专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("财务管理专业")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("会计学专业")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("国际经济与贸易专业")) {
						sql += " and substr(clazzid,4,1)='6'";
					}else if(major.equals("金融学专业")) {
						sql += " and substr(clazzid,4,1)='7'";
					}else if(major.equals("保险专业")) {
						sql += " and substr(clazzid,4,1)='8'";
					}else if(major.equals("财政学专业")) {
						sql += " and substr(clazzid,4,1)='9'";
					}else if(major.equals("数学与应用数学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("统计学专业 ")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("金融工程专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("金融科学专业")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("信息与计算科学专业")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("物理学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("电子信息科学与技术专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("自动化专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("光电信息科学专业")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("计算机科学与技术专业 ")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("大数据工程专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("物联网工程专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("软件工程专业")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("网络工程专业")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("化学工程与工艺专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("能源化学工程专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("制药工程专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("药物制剂专业")) {
						sql += " and substr(clazzid,4,1)='4'";
					}else if(major.equals("药学专业")) {
						sql += " and substr(clazzid,4,1)='5'";
					}else if(major.equals("地理科学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("地理信息科学专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("测绘工程专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("教育学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("心理学专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("学前教育学专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("文化产业管理专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("人文教育专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("生物技术专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("园林专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("英语专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("商务英语专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("日语专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}else if(major.equals("政治学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("哲学专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("汉语国际教育专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("钢琴专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("声乐专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("体育教育专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("社会体育专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("中国文学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("语言学专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("法学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("侦查学专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("工业设计专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("平面设计专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("土木工程专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("建筑环境与能源应用工程专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("广告学专业")) {
						sql += " and substr(clazzid,4,1)='1'";
					}else if(major.equals("新闻学专业")) {
						sql += " and substr(clazzid,4,1)='2'";
					}else if(major.equals("传播学专业")) {
						sql += " and substr(clazzid,4,1)='3'";
					}
			   }
				if(!(StringUtil.isEmpty(clazz))) {
					if(clazz.equals("1班")) {
						sql += " and substr(clazzid,5,1)='1'";
					}else if(clazz.equals("2班")) {
						sql += " and substr(clazzid,5,1)='2'";
					}else if(clazz.equals("3班")) {
						sql += " and substr(clazzid,5,1)='3'";
					}else if(clazz.equals("4班")) {
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
		//返回查询条数
		public int getFindClazzListTotal(String grade, String academic, String major, String clazz,String clazzid) {
			int total = 0;
			String sql = "select count(*) as total from clazz";
			if(!(StringUtil.isEmpty(clazzid))) {
				sql += " and clazzid ='"+clazzid+"'";
			}
			if(!StringUtil.isEmpty(grade)){
				if(grade.equals("大一")) {
					sql += " and clazzid like '1%'";
				}else if(grade.equals("大二")) {
					sql += " and clazzid like '2%'";
				}else if(grade.equals("大三")) {
					sql += " and clazzid like '3%'";
				}else if(grade.equals("大四")) {
					sql += " and clazzid like '4%'";
				}
			}
			if(!StringUtil.isEmpty(academic)){
				if(academic.equals("经济与管理学院")) {
					sql += " and substr(clazzid,2,2)='01'";
				}else if(academic.equals("数学与统计学院")) {
					sql += " and substr(clazzid,2,2)='02'";
				}else if(academic.equals("物理与电子工程学院")) {
					sql += " and substr(clazzid,2,2)='03'";
				}else if(academic.equals("计算机科学与技术学院")) {
					sql += " and substr(clazzid,2,2)='04'";
				}else if(academic.equals("化学与制药工程学院")) {
					sql += " and substr(clazzid,2,2)='05'";
				}else if(academic.equals("环境科学与旅游学院")) {
					sql += " and substr(clazzid,2,2)='06'";
				}else if(academic.equals("教育科学学院")) {
					sql += " and substr(clazzid,2,2)='07'";
				}else if(academic.equals("历史文化学院")) {
					sql += " and substr(clazzid,2,2)='08'";
				}else if(academic.equals("生命科学与技术学院")) {
					sql += " and substr(clazzid,2,2)='09'";
				}else if(academic.equals("外国语学院")) {
					sql += " and substr(clazzid,2,2)='10'";
				}else if(academic.equals("政治与公共管理学院")) {
					sql += " and substr(clazzid,2,2)='11'";
				}else if(academic.equals("国际教育学院")) {
					sql += " and substr(clazzid,2,2)='12'";
				}else if(academic.equals("音乐学院")) {
					sql += " and substr(clazzid,2,2)='13'";
				}else if(academic.equals("体育学院")) {
					sql += " and substr(clazzid,2,2)='14'";
				}else if(academic.equals("文学院")) {
					sql += " and substr(clazzid,2,2)='15'";
				}else if(academic.equals("法学院")) {
					sql += " and substr(clazzid,2,2)='16'";
				}else if(academic.equals("美术与艺术设计学院")) {
					sql += " and substr(clazzid,2,2)='17'";
				}else if(academic.equals("土木建筑工程学院")) {
					sql += " and substr(clazzid,2,2)='18'";
				}else if(academic.equals("新闻与传播学院")) {
					sql += " and substr(clazzid,2,2)='19'";
				}
			}
			if(!StringUtil.isEmpty(major)){
				//班名 采用正则匹配
				if(major.equals("工商管理专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("市场营销专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("人力资源专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("财务管理专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("会计学专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("国际经济与贸易专业")) {
					sql += " and substr(clazzid,4,1)='6'";
				}else if(major.equals("金融学专业")) {
					sql += " and substr(clazzid,4,1)='7'";
				}else if(major.equals("保险专业")) {
					sql += " and substr(clazzid,4,1)='8'";
				}else if(major.equals("财政学专业")) {
					sql += " and substr(clazzid,4,1)='9'";
				}else if(major.equals("数学与应用数学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("统计学专业 ")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("金融工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("金融科学专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("信息与计算科学专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("物理学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("电子信息科学与技术专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("自动化专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("光电信息科学专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("计算机科学与技术专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("大数据工程专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("物联网工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("软件工程专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("网络工程专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("化学工程与工艺专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("能源化学工程专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("制药工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("药物制剂专业")) {
					sql += " and substr(clazzid,4,1)='4'";
				}else if(major.equals("药学专业")) {
					sql += " and substr(clazzid,4,1)='5'";
				}else if(major.equals("地理科学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("地理信息科学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("测绘工程专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("教育学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("心理学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("学前教育学专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("文化产业管理专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("人文教育专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("生物技术专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("园林专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("英语专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("商务英语专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("日语专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}else if(major.equals("政治学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("哲学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("汉语国际教育专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("钢琴专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("声乐专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("体育教育专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("社会体育专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("中国文学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("语言学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("法学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("侦查学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("工业设计专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("平面设计专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("土木工程专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("建筑环境与能源应用工程专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("广告学专业")) {
					sql += " and substr(clazzid,4,1)='1'";
				}else if(major.equals("新闻学专业")) {
					sql += " and substr(clazzid,4,1)='2'";
				}else if(major.equals("传播学专业")) {
					sql += " and substr(clazzid,4,1)='3'";
				}
		   }
			if(!(StringUtil.isEmpty(clazz))) {
				if(clazz.equals("1班")) {
					sql += " and substr(clazzid,5,1)='1'";
				}else if(clazz.equals("2班")) {
					sql += " and substr(clazzid,5,1)='2'";
				}else if(clazz.equals("3班")) {
					sql += " and substr(clazzid,5,1)='3'";
				}else if(clazz.equals("4班")) {
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
