<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>授课列表</title>
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
	        title:'授课信息列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"SelectedCourseServlet?method=getSelectedCourseSelfList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:40, sortable: true},    
 		       	{field:'teacherName',title:'教师姓名',width:110},
 		       	{field:'courseName',title:'课程名',width:200},
 		   		{field:'classroom',title:'上课教室',width:150},
		      	{field:'weeks',title:'上课时间',width:150},
		      	{field:'numbers',title:'上课节数',width:150},
		      	{field:'score',title:'成绩',width:150},
		      	{field:'remark',title:'评级',width:150,
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
	  	//选课
		 $("#add").click(function(){
		    	var selectRow = $("#dataList").datagrid("getSelected");
		      	if(selectRow == null){
		          	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
		          }else{
		        	 	var id = selectRow.id;
		        	 	var courseid = selectRow.courseid;
		        	 	var tno = selectRow.tno;
			  			$.messager.confirm("消息提醒", "是否选择该门课程？", function(r){
		            		if(r){
		            			$.ajax({
									type: "post",
									url: "SelectedCourseServlet?method=SelectedTeachCourseSelf",
									data: {courseid: courseid, id: id ,tno: tno},
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","选课信息添加成功!","info");
											//刷新
											$('#dataList').datagrid("reload");
										} else if(msg == "courseSelected"){
											$.messager.alert("消息提醒","该课程已经被选，不可再选!","warning");
											return;
										} else if(msg == "PreviousError"){
											$.messager.alert("消息提醒","您还没有学习这门课的先行课!","warning");
											return;
										} else if(msg == "remarkerror"){
											$.messager.alert("消息提醒","您的评级和您的成绩不符，请认真选择!","warning");
											return;
										} else if(msg == "NoTeacherTeach"){
											$.messager.alert("消息提醒","该教师没有教授这门课程，请重新选择!","warning");
											return;
										} else if(msg == "ScoreError"){
											$.messager.alert("消息提醒","您输入的成绩有误!","warning");
											return;
										} else{
											$.messager.alert("消息提醒","选课失败!","warning");
											return;
										}
									}
								});
							}
			  			});
		          	}     	
		    });
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	</table>
</body>
</html>