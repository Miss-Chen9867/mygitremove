<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>查询学生</title>
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
	        title:'教师列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"TeacherServlet?method=FindTeacher&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'tno',title:'教工号',width:140},    
 		        {field:'name',title:'姓名',width:60},
 		      	{field:'password',title:'密码',width:100},
 		        {field:'sex',title:'性别',width:35},
 		        {field:'phone',title:'联系电话',width:100},
 		        {field:'rztime',title:'入职时间',width:100},
 		       	{field:'academic',title:'所属院系',width:150},
 		       	{field:'major',title:'职务',width:70,
 		        },
	 		]], 
	        toolbar: "#toolbar",
	    });
	  //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,40,50],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    });
	  //教师号列表
	  $("#tno").combobox({
		  	panelHeight:"135",
	  		width: "150",
	  		height: "23",
	  		valueField: "tno",
	  		textField: "tno",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"TeacherServlet?method=AllTeacherList&t="+new Date().getTime()+"&from=combox",
	  });
	  //学号列表
	  $("#name").combobox({
		  	panelHeight:"135",
	  		width: "150",
	  		height: "23",
	  		valueField: "name",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"TeacherServlet?method=AllTeacherList&t="+new Date().getTime()+"&from=combox",
	  });
	  //搜索点击事件
	  $("#search").click(function(){
	  		$('#dataList').datagrid('load',{
	  			academic: $('#academic').textbox('getValue'),
	  			major: $('#major').textbox('getValue'),
	  			tno: $('#tno').textbox('getValue'),
	  			name: $('#name').textbox('getValue'),
	  			sex: $('#sex').textbox('getValue')
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
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;学院：<select id="academic" class="easyui-combobox" data-options=" editable: false, panelHeight: 159, width: 150, height: 23" name="academic">
				<option value=""></option>
	    		<option value="经济与管理学院">经济与管理学院</option>
	    		<option value="数学与统计学院">数学与统计学院</option>
	    		<option value="物理与电子工程学院">物理与电子工程学院</option>
	    		<option value="计算机科学与技术学院">计算机科学与技术学院</option>
	    		<option value="化学与制药工程学院">化学与制药工程学院</option>
	    		<option value="教育科学学院">教育科学学院</option>
	    		<option value="历史文化学院">历史文化学院</option>
	    		<option value="生命科学与技术学院">生命科学与技术学院</option>
	    		<option value="外国语学院">外国语学院</option>
	    		<option value="政治与公共管理学院">政治与公共管理学院</option>
	    		<option value="国际教育学院">国际教育学院</option>
	    		<option value="音乐学院">音乐学院</option>
	    		<option value="体育学院">体育学院</option>
	    		<option value="文学院">文学院</option>
	    		<option value="法学院">法学院</option>
	    		<option value="美术与艺术设计学院">美术与艺术设计学院</option>
	 			<option value="土木建筑工程学院">土木建筑工程学院</option>
	    		<option value="新闻与传播学院">新闻与传播学院</option>
	    		<option value="环境科学与旅游学院">环境科学与旅游学院</option>
	    </select>
		</div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;<select id="major" class="easyui-combobox" data-options=" editable: false, panelHeight: 'auto', width: 150, height: 23" name="major">
							<option value=""></option>
							<option value="院长">院长</option>
	    					<option value="副院长">副院长</option>
	    					<option value="教授">教授</option>
	    					<option value="副教授">副教授</option>
	    					<option value="讲师">讲师</option>
	    					<option value="辅导员">辅导员</option>
	   	</select></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;教工号： <input id="tno" class="easyui-textbox" name="tno" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;姓名： <input id="name" class="easyui-textbox" name="name" /></div>
		&nbsp;&nbsp;性别： <select id="sex" class="easyui-combobox" data-options=" editable: false, panelHeight: 50, width: 150, height: 23" name="sex">
	    					<option value=""></option>
	    					<option value="男">男</option>
	    					<option value="女">女</option>
	    </select>
	    <a id="search" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		<a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a>
	</div>
</body>
</html>