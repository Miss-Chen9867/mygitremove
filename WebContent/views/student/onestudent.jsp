<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>所有学生列表</title>
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
	        title:'所有学生列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"StudentServlet?method=getOneStudentList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
	        	{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:45, sortable: true},    
 		        {field:'sno',title:'学号',width:100},    
 		        {field:'name',title:'姓名',width:60},
 		       	{field:'password',title:'密码',width:100},
 		        {field:'sex',title:'性别',width:35},
 		       	{field:'age',title:'年龄',width:35},
 		        {field:'birthday',title:'出生日期',width:100},
 		        {field:'home',title:'家庭住址',width:300},
 		        {field:'phone',title:'联系电话',width:100},
 		       	{field:'clazzid',title:'班号',width:60},
 		       	{field:'gratuated',title:'是否毕业',width:60,
 		       	},
	 		]],
	 		toolbar: "#toolbar"
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
	});
	</script>
</head>
<body>
	<!-- 学生列表 -->
	<table id="dataList"> 
	</table>
</body>
</html>