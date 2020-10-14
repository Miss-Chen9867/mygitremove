<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>查询选课</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'选课信息列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"SelectedCourseServlet?method=FindSelectedCourseList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:35, sortable: true},    
 		        {field:'sno',title:'学号',width:120},
 		       	{field:'tno',title:'教师号',width:130},
 		       	{field:'courseid',title:'课程号',width:50},
 		       	{field:'score',title:'成绩',width:45},
		      	{field:'remark',title:'评级',width:65,
		      	},
	 		]], 
	        toolbar: "#toolbar",
	    }); 
	  	//设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	    	pageSize: 18,//每页显示的记录条数，默认为10 
	        pageList: [18,36,54,72,108],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    });
	  	//学号列表
		$("#sno").combobox({
			  	panelHeight:"135",
		  		width: "150",
		  		height: "23",
		  		valueField: "sno",
		  		textField: "sno",
		  		multiple: false, //可多选
		  		editable: true, //可编辑
		  		method: "post",
		  		url:"StudentServlet?method=getAllStudentList&t="+new Date().getTime()+"&from=combox",
		  });
		  //学号列表
		  $("#studentname").combobox({
			  	panelHeight:"135",
		  		width: "150",
		  		height: "23",
		  		valueField: "sno",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: true, //可编辑
		  		method: "post",
		  		url:"StudentServlet?method=getAllStudentList&t="+new Date().getTime()+"&from=combox",
		  });
	  //教师号列表
	  $("#tno").combobox({
		  	panelHeight:"130",
	  		width: "150",
	  		height: "23",
	  		valueField: "tno",
	  		textField: "tno",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"TeacherServlet?method=AllTeacherList&t="+new Date().getTime()+"&from=combox",
	  });
	  //教师姓名列表
	  $("#teachername").combobox({
		  	panelHeight:"135",
	  		width: "150",
	  		height: "23",
	  		valueField: "tno",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"TeacherServlet?method=AllTeacherList&t="+new Date().getTime()+"&from=combox",
	  });
	  //学号列表
	  $("#courseName").combobox({
		  	panelHeight:"140",
	  		width: "150",
	  		height: "23",
	  		valueField: "courseId",
	  		textField: "courseName",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"CourseServlet?method=getAllCourseList&t="+new Date().getTime()+"&from=combox",
	  });
	  //学号列表
	  $("#courseId").combobox({
		  	panelHeight:"130",
	  		width: "150",
	  		height: "23",
	  		valueField: "courseId",
	  		textField: "courseId",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"CourseServlet?method=getAllCourseList&t="+new Date().getTime()+"&from=combox",
	  });
	  //搜索点击事件
	  $("#search").click(function(){
	  		$('#dataList').datagrid('load',{
	  			sno: $('#sno').textbox('getValue'),
	  			tno: $('#tno').textbox('getValue'),
	  			studentname: $('#studentname').textbox('getValue'),
	  			teachername: $('#teachername').textbox('getValue'),
	  			courseName: $('#courseName').textbox('getValue'),
	  			courseId: $('#courseId').textbox('getValue'),
	  			remark: $('#remark').textbox('getValue'),
	  		});
	  	});
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;学号： <input id="sno" class="easyui-textbox" name="sno" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;教师号： <input id="tno" class="easyui-textbox" name="tno" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;学生姓名： <input id="studentname" class="easyui-textbox" name="studentname" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;教师姓名： <input id="teachername" class="easyui-textbox" name="teachername" /></div>
		<br/><br/>&nbsp;&nbsp;课程号： <input id="courseId" class="easyui-textbox" name="courseId" />
		&nbsp;&nbsp;课程名： <input id="courseName" class="easyui-textbox" name="courseName" />
		&nbsp;&nbsp;评级： <select id="remark" class="easyui-combobox" data-options=" editable: false, panelHeight: 98, width: 150, height: 23" name="remark">
	    					<option value=""></option>
	    					<option value="优秀">优秀</option>
	    					<option value="良好">良好</option>
	    					<option value="及格">及格</option>
	    					<option value="不及格">不及格</option>
	    </select>
		<a id="search" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		<a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a>
	</div>
</body>
</html>