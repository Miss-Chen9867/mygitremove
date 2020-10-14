<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>查询授课</title>
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
	        title:'查询授课', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"TeachCourseServlet?method=FindTeachCourseList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'tno',title:'教工号',width:200},
 		       	{field:'courseid',title:'课程号',width:200},
 		   		{field:'classroom',title:'上课教室',width:200},
		      	{field:'weeks',title:'上课时间',width:200},
		      	{field:'numbers',title:'上课节数',width:200,
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
	  			tno: $('#tno').textbox('getValue'),
	  			teachername: $('#teachername').textbox('getValue'),
	  			courseName: $('#courseName').textbox('getValue'),
	  			courseId: $('#courseId').textbox('getValue'),
	  			classroom_building: $('#classroom_building').textbox('getValue'),
	  			classroom_block: $('#classroom_block').textbox('getValue'),
	  			classroom_room: $('#classroom_room').textbox('getValue'),
	  			weeks_1: $('#weeks_1').textbox('getValue'),
	  			weeks_2: $('#weeks_2').textbox('getValue'),
	  			weeks_3: $('#weeks_3').textbox('getValue'),
	  			numbers_1: $('#numbers_1').textbox('getValue'),
	  			numbers_2: $('#numbers_2').textbox('getValue'),
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
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;教师号： <input id="tno" class="easyui-textbox" name="tno" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;教师名： <input id="teachername" class="easyui-textbox" name="teachername" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;课程号： <input id="courseId" class="easyui-textbox" name="courseId" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;课程名： <input id="courseName" class="easyui-textbox" name="courseName" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;教室： <select id="classroom_building" class="easyui-combobox" data-options=" editable: false, panelHeight: 140, width: 90, height: 23" name="classroom_building">
							<option value=""></option>
	    					<option value="1">第一教学楼</option>
	    					<option value="2">第二教学楼</option>
	    					<option value="3">第三教学楼</option>
	    					<option value="4">第四教学楼</option>
	    					<option value="5">第五教学楼</option>
	    					<option value="6">第六教学楼</option>
	    				</select>
	    				<select id="classroom_block" class="easyui-combobox" data-options=" editable: false, panelHeight: 100, width: 55, height: 23" name="classroom_block">
	    					<option value=""></option>
	    					<option value="A">A栋</option>
	    					<option value="B">B栋</option>
	    					<option value="C">C栋</option>
	    					<option value="D">D栋</option>
	    				</select>
	    				<select id="classroom_room" class="easyui-combobox" data-options=" missingMessage:'请选择上课地点', editable: false, panelHeight: 175, width: 50, height: 23" name="classroom_room">
	    					<option value=""></option>
	    					<option value="101">101</option>
	    					<option value="102">102</option>
	    					<option value="103">103</option>
	    					<option value="104">104</option>
	    					<option value="105">105</option>
	    					<option value="106">106</option>
	    					<option value="107">107</option>
	    					<option value="108">108</option>
	    					<option value="201">201</option>
	    					<option value="202">202</option>
	    					<option value="203">203</option>
	    					<option value="204">204</option>
	    					<option value="205">205</option>
	    					<option value="206">206</option>
	    					<option value="207">207</option>
	    					<option value="208">208</option>
	    					<option value="301">301</option>
	    					<option value="302">302</option>
	    					<option value="303">303</option>
	    					<option value="304">304</option>
	    					<option value="305">305</option>
	    					<option value="306">306</option>
	    					<option value="307">307</option>
	    					<option value="308">308</option>
	    					<option value="401">401</option>
	    					<option value="402">402</option>
	    					<option value="403">403</option>
	    					<option value="404">404</option>
	    					<option value="405">405</option>
	    					<option value="406">406</option>
	    					<option value="407">407</option>
	    					<option value="408">408</option>
	    					<option value="501">501</option>
	    					<option value="502">502</option>
	    					<option value="503">503</option>
	    					<option value="504">504</option>
	    					<option value="505">505</option>
	    					<option value="506">506</option>
	    					<option value="507">507</option>
	    					<option value="508">508</option>
	    					<option value="601">601</option>
	    					<option value="602">602</option>
	    					<option value="603">603</option>
	    					<option value="604">604</option>
	    					<option value="605">605</option>
	    					<option value="606">606</option>
	    					<option value="607">607</option>
	    					<option value="608">608</option>
	    					<option value="701">701</option>
	    					<option value="702">702</option>
	    					<option value="703">703</option>
	    					<option value="704">704</option>
	    					<option value="705">705</option>
	    					<option value="706">706</option>
	    					<option value="707">707</option>
	    					<option value="708">708</option>
	    					<option value="801">801</option>
	    					<option value="802">802</option>
	    					<option value="803">803</option>
	    					<option value="804">804</option>
	    					<option value="805">805</option>
	    					<option value="806">806</option>
	    					<option value="807">807</option>
	    					<option value="808">808</option>
	    				</select>
	    </div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;周数：第
	    				<select id="weeks_1" class="easyui-combobox" data-options=" editable: false, panelHeight: 160, width: 60, height: 23" name="weeks_1">
	    					<option value=""></option>
	    					<option value="01">  一</option>
	    					<option value="02">  二</option>
	    					<option value="03">  三</option>
	    					<option value="04">  四</option>
	    					<option value="05">  五</option>
	    					<option value="06"> 六</option>
	    					<option value="07">  七</option>
	    					<option value="08">  八</option>
	    					<option value="09">  九</option>
	    					<option value="10"> 十</option>
	    					<option value="11">  十一</option>
	    					<option value="12">  十二</option>
	    					<option value="13">  十三</option>
	    					<option value="14">  十四</option>
	    					<option value="15">  十五</option>
	    					<option value="16">  十六</option>
	    					<option value="17">  十七</option>
	    					<option value="18">  十八</option>
	    				</select>
	    				-
	    				<select id="weeks_2" class="easyui-combobox" data-options=" editable: false, panelHeight: 160, width: 60, height: 23" name="weeks_2">
	    					<option value=""></option>
	    					<option value="01">  一</option>
	    					<option value="02">  二</option>
	    					<option value="03">  三</option>
	    					<option value="04">  四</option>
	    					<option value="05">  五</option>
	    					<option value="06"> 六</option>
	    					<option value="07">  七</option>
	    					<option value="08">  八</option>
	    					<option value="09">  九</option>
	    					<option value="10"> 十</option>
	    					<option value="11">  十一</option>
	    					<option value="12">  十二</option>
	    					<option value="13">  十三</option>
	    					<option value="14">  十四</option>
	    					<option value="15">  十五</option>
	    					<option value="16">  十六</option>
	    					<option value="17">  十七</option>
	    					<option value="18">  十八</option>
	    				</select>
	    				周 星期
	    				<select id="weeks_3" class="easyui-combobox" data-options=" editable: false, panelHeight: 165, width: 50, height: 23" name="weeks_3">
	    					<option value=""></option>
	    					<option value="1">  一</option>
	    					<option value="2">  二</option>
	    					<option value="3">  三</option>
	    					<option value="4">  四</option>
	    					<option value="5">  五</option>
	    					<option value="6"> 六</option>
	    					<option value="7">  日</option>
	    				</select>
	    </div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						节数：
						 第
	    				<select id="numbers_1" class="easyui-combobox" data-options=" editable: false, panelHeight: 155, width: 50, height: 23" name="numbers_1">
	    					<option value=""></option>
	    					<option value="1">  1</option>
	    					<option value="2">  2</option>
	    					<option value="3">  3</option>
	    					<option value="4">  4</option>
	    					<option value="5">  5</option>
	    					<option value="6">  6</option>
	    					<option value="7">  7</option>
	    					<option value="8">  8</option>
	    					<option value="9">  9</option>
	    				</select>
	    				-
	    				<select id="numbers_2" class="easyui-combobox" data-options=" editable: false, panelHeight: 155, width: 50, height: 23" name="numbers_2">
	    					<option value=""></option>
	    					<option value="1">  1</option>
	    					<option value="2">  2</option>
	    					<option value="3">  3</option>
	    					<option value="4">  4</option>
	    					<option value="5">  5</option>
	    					<option value="6">  6</option>
	    					<option value="7">  7</option>
	    					<option value="8">  8</option>
	    					<option value="9">  9</option>
	    				</select>
	    				节
	    </div>
		<a id="search" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		<a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a>
	</div>
</body>
</html>