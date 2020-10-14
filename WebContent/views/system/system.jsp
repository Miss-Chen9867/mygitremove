<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统后台</title>
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="bookmark" href="favicon.ico"/>
<link rel="stylesheet" type="text/css" href="easyui/css/default.css" />
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src='easyui/js/outlook2.js'> </script>
<script type="text/javascript">	
	var _menus = {"menus":[
		//如果是管理员，可以看到全部学生的信息
		//还可以继续分类大一，大二，大三，大四
		<c:if test="${userType == 1}">
		{"menuid":"2","icon":"","menuname":"学生信息管理",
			"menus":[
					{"menuid":"21","menuname":"所有学生列表","icon":"icon-user-student","url":"StudentServlet?method=toAllStudentListView"},
					{"menuid":"21","menuname":"在校学生列表","icon":"icon-user-student","url":"StudentServlet?method=toInStudentListView"},
					{"menuid":"21","menuname":"大一学生列表","icon":"icon-user-student","url":"StudentServlet?method=toOneStudentListView"},
					{"menuid":"21","menuname":"大二学生列表","icon":"icon-user-student","url":"StudentServlet?method=toTwoStudentListView"},
					{"menuid":"21","menuname":"大三学生列表","icon":"icon-user-student","url":"StudentServlet?method=toThreeStudentListView"},
					{"menuid":"21","menuname":"大四学生列表","icon":"icon-user-student","url":"StudentServlet?method=toFourStudentListView"},
					{"menuid":"21","menuname":"毕业学生列表","icon":"icon-user-student","url":"StudentServlet?method=toOutStudentListView"},
				]
		},
		//管理员显示所有班级
		//同样也可以按照年级来分类
		{"menuid":"4","icon":"","menuname":"班级信息管理",
			"menus":[
					{"menuid":"42","menuname":"所有班级列表","icon":"icon-house","url":"ClazzServlet?method=toAllClazzListView"},
					{"menuid":"42","menuname":"大一班级列表","icon":"icon-house","url":"ClazzServlet?method=toOneClazzListView"},
					{"menuid":"42","menuname":"大二班级列表","icon":"icon-house","url":"ClazzServlet?method=toTwoClazzListView"},
					{"menuid":"42","menuname":"大三班级列表","icon":"icon-house","url":"ClazzServlet?method=toThreeClazzListView"},
					{"menuid":"42","menuname":"大四班级列表","icon":"icon-house","url":"ClazzServlet?method=toFourClazzListView"},
				]
		},		
		//只有管理员呢才有所有教师
		{"menuid":"3","icon":"","menuname":"教师信息管理",
			"menus":[
					{"menuid":"31","menuname":"所有教师","icon":"icon-user-teacher","url":"TeacherServlet?method=toTeacherListView"},
					{"menuid":"31","menuname":"经济与管理学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tojinguanTeacherListView"},
					{"menuid":"31","menuname":"数学与统计学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=toshuxueTeacherListView"},
					{"menuid":"31","menuname":"物理与电子工程学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=towuliTeacherListView"},
					{"menuid":"31","menuname":"计算机科学与技术学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tojisuanjiTeacherListView"},
					{"menuid":"31","menuname":"化学与制药工程学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tohuaxueTeacherListView"},
					{"menuid":"31","menuname":"环境科学与旅游学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tohuanjingTeacherListView"},
					{"menuid":"31","menuname":"教育科学学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tojiaoyuTeacherListView"},
					{"menuid":"31","menuname":"历史文化学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tolishiTeacherListView"},
					{"menuid":"31","menuname":"生命科学与技术学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=toshengmingTeacherListView"},
					{"menuid":"31","menuname":"外国语学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=towaiguoyuTeacherListView"},
					{"menuid":"31","menuname":"政治与公共管理学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tozhengzhiTeacherListView"},
					{"menuid":"31","menuname":"国际教育学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=toguojiTeacherListView"},
					{"menuid":"31","menuname":"音乐学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=toyinyueTeacherListView"},
					{"menuid":"31","menuname":"体育学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=totiyuTeacherListView"},
					{"menuid":"31","menuname":"文学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=towenxueTeacherListView"},
					{"menuid":"31","menuname":"法学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tofaxueTeacherListView"},
					{"menuid":"31","menuname":"美术与艺术设计学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=tomeishuTeacherListView"},
					{"menuid":"31","menuname":"土木建筑工程学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=totumuTeacherListView"},
					{"menuid":"31","menuname":"新闻与传播学院教师","icon":"icon-user-teacher","url":"TeacherServlet?method=toxinwenTeacherListView"},
				]
		},
		//管理员可以看到全部课程
		//按年级分类
		{"menuid":"6","icon":"","menuname":"课程信息管理",
			"menus":[
					{"menuid":"61","menuname":"所有课程","icon":"icon-book-open","url":"CourseServlet?method=toAllCourseListView"},
					{"menuid":"61","menuname":"经济与管理学院课程","icon":"icon-book-open","url":"CourseServlet?method=tojinguanCourseListView"},
					{"menuid":"61","menuname":"数学与统计学院课程","icon":"icon-book-open","url":"CourseServlet?method=toshuxueCourseListView"},
					{"menuid":"61","menuname":"物理与电子工程学院课程","icon":"icon-book-open","url":"CourseServlet?method=towuliCourseListView"},
					{"menuid":"61","menuname":"计算机科学与技术学院课程","icon":"icon-book-open","url":"CourseServlet?method=tojisuanjiCourseListView"},
					{"menuid":"61","menuname":"化学与制药工程学院课程","icon":"icon-book-open","url":"CourseServlet?method=tohuaxueCourseListView"},
					{"menuid":"61","menuname":"环境科学与旅游学院课程","icon":"icon-book-open","url":"CourseServlet?method=tohuanjingCourseListView"},
					{"menuid":"61","menuname":"教育科学学院课程","icon":"icon-book-open","url":"CourseServlet?method=tojiaoyuCourseListView"},
					{"menuid":"61","menuname":"历史文化学院课程","icon":"icon-book-open","url":"CourseServlet?method=tolishiCourseListView"},
					{"menuid":"61","menuname":"生命科学与技术学院课程","icon":"icon-book-open","url":"CourseServlet?method=toshengmingCourseListView"},
					{"menuid":"61","menuname":"外国语学院课程","icon":"icon-book-open","url":"CourseServlet?method=towaiguoyuCourseListView"},
					{"menuid":"61","menuname":"政治与公共管理学院课程","icon":"icon-book-open","url":"CourseServlet?method=tozhengzhiCourseListView"},
					{"menuid":"61","menuname":"国际教育学院课程","icon":"icon-book-open","url":"CourseServlet?method=toguojiCourseListView"},
					{"menuid":"61","menuname":"音乐学院课程","icon":"icon-book-open","url":"CourseServlet?method=toyinyueCourseListView"},
					{"menuid":"61","menuname":"体育学院课程","icon":"icon-book-open","url":"CourseServlet?method=totiyuCourseListView"},
					{"menuid":"61","menuname":"文学院课程","icon":"icon-book-open","url":"CourseServlet?method=towenxueCourseListView"},
					{"menuid":"61","menuname":"法学院课程","icon":"icon-book-open","url":"CourseServlet?method=tofaxueCourseListView"},
					{"menuid":"61","menuname":"美术与艺术设计学院课程","icon":"icon-book-open","url":"CourseServlet?method=tomeishuCourseListView"},
					{"menuid":"61","menuname":"土木建筑工程学院课程","icon":"icon-book-open","url":"CourseServlet?method=totumuCourseListView"},
					{"menuid":"61","menuname":"新闻与传播学院课程","icon":"icon-book-open","url":"CourseServlet?method=toxinwenCourseListView"},
				]
		},
		//授课信息管理
		{"menuid":"7","icon":"","menuname":"授课信息管理",
			"menus":[
					{"menuid":"71","menuname":"授课列表","icon":"icon-book-open","url":"TeachCourseServlet?method=toTeachCourseListView"},
				]
		},
		//选课信息管理
		{"menuid":"7","icon":"","menuname":"选课信息管理",
			"menus":[
					{"menuid":"71","menuname":"选课列表","icon":"icon-book-open","url":"SelectedCourseServlet?method=toSelectedCourseListView"},
				]
		},
		//根据需求查询信息，并且不同的身份查询权限不同
		{"menuid":"8","icon":"","menuname":"查询列表",
			"menus":[
					{"menuid":"81","menuname":"查询学生","icon":"icon-search","url":"SearchServlet?method=toStudentSearchView"},
					{"menuid":"81","menuname":"查询教师","icon":"icon-search","url":"SearchServlet?method=toTeacherSearchView"},
					{"menuid":"81","menuname":"查询课程","icon":"icon-search","url":"SearchServlet?method=toCourseSearchView"},
					{"menuid":"81","menuname":"查询班级","icon":"icon-search","url":"SearchServlet?method=toClazzSearchView"},
					{"menuid":"81","menuname":"查询授课","icon":"icon-search","url":"SearchServlet?method=toTeachCourseSearchView"},
					{"menuid":"81","menuname":"查询选课","icon":"icon-search","url":"SearchServlet?method=toSelectedCourseSearchView"},
			]
		},
		//修改密码
		{"menuid":"5","icon":"","menuname":"修改密码","menus":[
			{"menuid":"51","menuname":"修改密码","icon":"icon-set","url":"SystemServlet?method=EditPasswordView"},
		]
		},
		</c:if>
		
		//接下来是教师
		//教师可以查到自己的个人信息，并且没有权利修改,修改需要找管理员
		//教师可以查看自己教学课程的安排，并做出调整
		//展现给教师的是学生一部分信息，包括：学号，姓名，所属学院，所学专业，并且同时显示学生的成绩和考评情况
		<c:if test="${userType == 2}">
		//教师个人信息
		{"menuid":"3","icon":"","menuname":"教师个人信息列表",
			"menus":[
					{"menuid":"31","menuname":"个人信息","icon":"icon-user-teacher","url":"TeacherServlet?method=toTeacherSelf"},
				]
		},
		//教授课程
		{"menuid":"6","icon":"","menuname":"授课信息管理",
			"menus":[
					{"menuid":"61","menuname":"所有授课","icon":"icon-book-open","url":"TeachCourseServlet?method=toTeachCourse"},
			]
		},
		//授课学生
		{"menuid":"2","icon":"","menuname":"学生信息列表",
			"menus":[
					{"menuid":"21","menuname":"学生信息","icon":"icon-user-student","url":"SelectedCourseServlet?method=toTeachStudent"},	
				]
		},
		//成绩信息管理
		/* {"menuid":"10","icon":"","menuname":"成绩信息管理","menus":[
					{"menuid":"101","menuname":"成绩列表","icon":"icon-book-open","url":"ScoreServlet?method=toScoreListView"},
					{"menuid":"101","menuname":"成绩统计","icon":"icon-book-open","url":"ScoreServlet?method=toScoreStatsView"},
				]
		}, */
		//修改密码
		{"menuid":"5","icon":"","menuname":"修改密码","menus":[
			{"menuid":"51","menuname":"修改密码","icon":"icon-set","url":"SystemServlet?method=EditPasswordView"},
		]
		},
		</c:if>
		//最后是学生
		//学生可以查到自己的个人信息，并且没有权利修改，修改需要找管理员
		//学生可以查看自己所选择的课程的安排，以及关于该课程的信息，上课地点和教学老师
		//展现给学生的课程情况是自己的分数，如果挂科会弹出提示框
		<c:if test="${userType == 3}">
		//学生个人信息
		{"menuid":"2","icon":"","menuname":"学生个人信息",
			"menus":[
					{"menuid":"21","menuname":"个人信息","icon":"icon-user-student","url":"StudentServlet?method=toStudentSelf"},	
				]
		},
		//所选课程
		{"menuid":"7","icon":"","menuname":"个人选课信息",
			"menus":[
					{"menuid":"71","menuname":"个人选课","icon":"icon-book-open","url":"SelectedCourseServlet?method=toSelectedCourseSelf"},
				]
		},
		//可选课程
		{"menuid":"811","icon":"","menuname":"可选课程信息",
			"menus":[
					{"menuid":"812","menuname":"可选课程","icon":"icon-book-open","url":"TeachCourseServlet?method=toSelectedCourseSelf"},
				]
		},
		//修改密码
		{"menuid":"5","icon":"","menuname":"修改密码","menus":[
			{"menuid":"51","menuname":"修改密码","icon":"icon-set","url":"SystemServlet?method=EditPasswordView"},
		]
		},
		</c:if>
		//关于系统
		{"menuid":"10","icon":"","menuname":"关于系统","menus":[
					{"menuid":"101","menuname":"使用说明书","icon":"icon-book-open","url":"SystemServlet?method=toAboutSystem"},
				]
		},
	]};
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	
	<!--北部-->
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: #000000 repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${user.name}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="LoginServlet?method=logout" id="loginOut">安全退出</a></span>
    </div>
    
    <!--南部-->
    <div region="south" split="true" style="height: 30px; background: #000000; ">
    </div>
    
    <!--西部-->
    <div region="west" hide="true" split="true" title="导航菜单" style="width:220px; background: #000000;" id="west">
		<div id="nav" class="easyui-accordion" fit="true" border="false">
		</div>
    </div>
    
    <!--中间容器-->
    <div id="mainPanle" region="center" style="background: #ffffff; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<jsp:include page="welcome.jsp" />
		</div>
    </div>
    
	<iframe width=0 height=0 src="refresh.jsp"></iframe>
	
</body>
</html>