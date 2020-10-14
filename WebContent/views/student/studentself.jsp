<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>在校学生列表</title>
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
</head>
<body>
	<div>
	<div style="float:right;position: relative; right:500px;bottom:10px;margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF" id="photo">
	    <img alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="PhotoServlet?method=getsPhoto" />
	</div>
	<table>
		<tr>
	   		<td>学号:</td>
	    	<td><input id="sno" value="${user.sno}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="sno" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
		<tr>
	   		<td>姓名:</td>
	    	<td><input id="name" value="${user.name}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
		<tr>
	   		<td>性别:</td>
	    	<td><input id="sex" value="${user.sex}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="sex" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
		<tr>
	   		<td>年龄:</td>
	    	<td><input id="age" value="${user.age}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="age" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
		<tr>
	   		<td>出生日期:</td>
	    	<td><input id="birthday" value="${user.birthday}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="birthday" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
	    <tr>
	   		<td>家庭住址:</td>
	    	<td><input id="home" value="${user.home}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="home" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
	   	<tr>
	   		<td>联系电话:</td>
	    	<td><input id="phone" value="${user.phone}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
	    <tr>
	   		<td>学院:</td>
	    	<td><input id="academic" value="${clazz.academic}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="academic" readonly="true"/></td>
	    </tr>
	    <tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr><tr><td>
	    <tr>
	   		<td>专业:</td>
	    	<td><input id="major" value="${clazz.major}" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="major" readonly="true"/></td>
	    </tr>
	</table>
	</div>
</body>
</html>