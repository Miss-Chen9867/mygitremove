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
	        url:"TeachCourseServlet?method=getTeachCourseList&t="+new Date().getTime(),
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
		//下拉框通用属性
	  	$("#add_teacherList,#teacherList").combobox({
	  		width: "140",
	  		height: "30",
	  		valueField: "tno",
	  		textField: "tno",
	  		multiple: false, //不可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"TeacherServlet?method=AllTeacherList&t="+new Date().getTime()+"&from=combox",
	  	});
	  	$("#add_courseList,#courseList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "courseId",
	  		textField: "courseName",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"CourseServlet?method=getAllCourseList&t="+new Date().getTime()+"&from=combox",
	  	});
		//增加点击事件
		$("#add").click(function(){
		    $("#addDialog").dialog("open");
		});
		//设置添加班级窗口
	    $("#addDialog").dialog({
	    	title: "添加班级",
	    	width: 500,
	    	height: 340,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请您务必输入信息!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "TeachCourseServlet?method=AddTeachCourse",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_teacherList").textbox('setValue', "");
										$("#add_courseList").textbox('setValue', "");
										$("#add_classroom_building").textbox('setValue', "");
										$("#add_classroom_block").textbox('setValue', "");
										$("#add_classroom_room").textbox('setValue', "");
										$("#add_weeks_1").textbox('setValue', "");
										$("#add_weeks_2").textbox('setValue', "");
										$("#add_weeks_3").textbox('setValue', "");
										$("#add_numbers_1").textbox('setValue', "");
										$("#add_numbers_2").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
									}else if(msg == "weekserror"){
										$.messager.alert("消息提醒","添加失败,您选择的周数有误，请重新填写！","warning");
										return;
									}else if(msg == "numberserror"){
										$.messager.alert("消息提醒","添加失败,您选择的节数有误，请重新填写！","warning");
										return;
									}else if(msg == "isTeached"){
										$.messager.alert("消息提醒","添加失败,该授课记录已添加，无需重复添加！","warning");
										return;
									}else{
										$.messager.alert("消息提醒","添加失败,上课时段冲突!可以先去查询剩余时段哦！","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#add_teacherList").textbox('setValue', "");
						$("#add_courseList").textbox('setValue', "");
						$("#add_classroom_building").textbox('setValue', "");
						$("#add_classroom_block").textbox('setValue', "");
						$("#add_classroom_room").textbox('setValue', "");
						$("#add_weeks_1").textbox('setValue', "");
						$("#add_weeks_2").textbox('setValue', "");
						$("#add_weeks_3").textbox('setValue', "");
						$("#add_numbers_1").textbox('setValue', "");
						$("#add_numbers_2").textbox('setValue', "");
					}
				},
			],
	    	onBeforeOpen: function(){
	    		$("#add_teacherList").textbox('setValue', "");
				$("#add_courseList").textbox('setValue', "");
				$("#add_classroom_building").textbox('setValue', "");
				$("#add_classroom_block").textbox('setValue', "");
				$("#add_classroom_room").textbox('setValue', "");
				$("#add_weeks_1").textbox('setValue', "");
				$("#add_weeks_2").textbox('setValue', "");
				$("#add_weeks_3").textbox('setValue', "");
				$("#add_numbers_1").textbox('setValue', "");
				$("#add_numbers_2").textbox('setValue', "");
		}
	    });
	  	//编辑点击事件
		$("#edit").click(function(){
			   	var selectRow = $("#dataList").datagrid("getSelected"); 
			  	if(selectRow == null){
				$.messager.alert("消息提醒","请选择数据进行修改！","warning");
				return;
			  	}
			  	$("#editDialog").dialog("open");
		   });
		  
		 //添加修改窗口
		 $("#editDialog").dialog({
		    	title: "编辑班级",
		    	width: 500,
		    	height: 300,
		    	iconCls: "icon-add",
		    	modal: true,
		    	collapsible: false,
		    	minimizable: false,
		    	maximizable: false,
		    	draggable: true,
		    	closed: true,
		    	buttons: [
		    		{
						text:'确定修改',
						plain: true,
						iconCls:'icon-add',
						handler:function(){
							var validate = $("#editForm").form("validate");
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{
								$.ajax({
									type: "post",
									url: "TeachCourseServlet?method=EditTeachCourse",
									data: $("#editForm").serialize(),
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","修改成功!","info");
											//关闭窗口
											$("#editDialog").dialog("close");
											//清空原表格数据
											$("#edit_teacherList").textbox('setValue', "");
											$("#edit_courseList").textbox('setValue', "");
											$("#edit_classroom_building").textbox('setValue', "");
											$("#edit_classroom_block").textbox('setValue', "");
											$("#edit_classroom_room").textbox('setValue', "");
											$("#edit_weeks_1").textbox('setValue', "");
											$("#edit_weeks_2").textbox('setValue', "");
											$("#edit_weeks_3").textbox('setValue', "");
											$("#edit_numbers_1").textbox('setValue', "");
											$("#edit_numbers_2").textbox('setValue', "");
											//重新刷新页面数据
								  			$('#dataList').datagrid("reload");
										}else if(msg == "weekserror"){
											$.messager.alert("消息提醒","编辑失败,您选择的周数有误，请重新填写！","warning");
											return;
										}else if(msg == "numberserror"){
											$.messager.alert("消息提醒","编辑失败,您选择的节数有误，请重新填写！","warning");
											return;
										}else{
											$.messager.alert("消息提醒","编辑失败,上课时段冲突!可以先去查询剩余时段哦！","warning");
											return;
										}
									}
								});
							}
						}
					},
					{
						text:'重置',
						plain: true,
						iconCls:'icon-reload',
						handler:function(){
							$("#edit_teacherList").textbox('setValue', "");
							$("#edit_courseList").textbox('setValue', "");
							$("#edit_classroom_building").textbox('setValue', "");
							$("#edit_classroom_block").textbox('setValue', "");
							$("#edit_classroom_room").textbox('setValue', "");
							$("#edit_weeks_1").textbox('setValue', "");
							$("#edit_weeks_2").textbox('setValue', "");
							$("#edit_weeks_3").textbox('setValue', "");
							$("#edit_numbers_1").textbox('setValue', "");
							$("#edit_numbers_2").textbox('setValue', "");
						}
					},
				],
				onBeforeOpen: function(){
					var selectRow = $("#dataList").datagrid("getSelected");
					//设置值
					$("#edit-courseid").val(selectRow.courseid);
					$("#edit-id").val(selectRow.id);
					$("#edit-tno").val(selectRow.tno);
				}
		    });
		 
		 //删除授课
		 $("#delete").click(function(){
		    	var selectRows = $("#dataList").datagrid("getSelections");
		    	var selectRow = $("#dataList").datagrid("getSelected");
		      	if(selectRow == null && selectRows != 1){
		          	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
		          }else{
		        	 	var id = selectRow.id;
		        	 	var courseid = selectRow.courseid;
		        	 	var tno = selectRow.tno;
			  			$.messager.confirm("消息提醒", "是否确认没有任何学生选择该门课程？", function(r){
		            		if(r){
		            			$.ajax({
									type: "post",
									url: "TeachCourseServlet?method=DeleteTeachCourse",
									data: {courseid: courseid, id: id ,tno: tno},
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","删除成功!","info");
											//刷新表格
											$("#dataList").datagrid("reload");
										} else{
											$.messager.alert("消息提醒","删除失败!有学生在上该门课！","warning");
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
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a></div>
	</div>
	
	<!-- 添加数据窗口 -->
	<div id="addDialog" style="padding: 10px">
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:40px">教师:</td>
	    			<td colspan="3">
	    				<input id="add_teacherList" style="width: 160px; height: 30px;" class="easyui-textbox" data-options="required:true, missingMessage:'请选择教师信息',panelHeight: 190, width: 90, height: 30" name="tno" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">课程:</td>
	    			<td colspan="3">
	    				<input id="add_courseList" style="width: 200px; height: 30px;" class="easyui-textbox" data-options="required:true, missingMessage:'请选择课程信息', panelHeight: 200, width: 90, height: 30" name="courseid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>课室:</td>
	    			<td>
	    				<select id="add_classroom_building" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 135, width: 90, height: 30" name="classroom_building">
	    					<option value="1">第一教学楼</option>
	    					<option value="2">第二教学楼</option>
	    					<option value="3">第三教学楼</option>
	    					<option value="4">第四教学楼</option>
	    					<option value="5">第五教学楼</option>
	    					<option value="6">第六教学楼</option>
	    				</select>
	    				<select id="add_classroom_block" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 90, width: 55, height: 30" name="classroom_block">
	    					<option value="A">A栋</option>
	    					<option value="B">B栋</option>
	    					<option value="C">C栋</option>
	    					<option value="D">D栋</option>
	    				</select>
	    				<select id="add_classroom_room" class="easyui-combobox" data-options="required:true, missingMessage:'请选择上课地点', editable: false, panelHeight: 170, width: 50, height: 30" name="classroom_room">
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
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>时间:</td>
	    			<td>
						第
	    				<select id="add_weeks_1" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 60, height: 30" name="weeks_1">
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
	    				<select id="add_weeks_2" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 60, height: 30" name="weeks_2">
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
	    				<select id="add_weeks_3" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 50, height: 30" name="weeks_3">
	    					<option value="1">  一</option>
	    					<option value="2">  二</option>
	    					<option value="3">  三</option>
	    					<option value="4">  四</option>
	    					<option value="5">  五</option>
	    					<option value="6"> 六</option>
	    					<option value="7">  日</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>节数:</td>
	    			<td>
						第
	    				<select id="add_numbers_1" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 50, height: 30" name="numbers_1">
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
	    				<select id="add_numbers_2" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 50, height: 30" name="numbers_2">
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
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!--编辑窗口-->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    	<input type="hidden" id="edit-id" name="id">
    	<input type="hidden" id="edit-tno" name="tno">
    	<input type="hidden" id="edit-courseid" name="courseid">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>课室:</td>
	    			<td>
	    				<select id="edit_classroom_building" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 135, width: 90, height: 30" name="classroom_building">
	    					<option value="1">第一教学楼</option>
	    					<option value="2">第二教学楼</option>
	    					<option value="3">第三教学楼</option>
	    					<option value="4">第四教学楼</option>
	    					<option value="5">第五教学楼</option>
	    					<option value="6">第六教学楼</option>
	    				</select>
	    				<select id="edit_classroom_block" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 90, width: 55, height: 30" name="classroom_block">
	    					<option value="A">A栋</option>
	    					<option value="B">B栋</option>
	    					<option value="C">C栋</option>
	    					<option value="D">D栋</option>
	    				</select>
	    				<select id="edit_classroom_room" class="easyui-combobox" data-options="required:true, missingMessage:'请选择上课地点', editable: false, panelHeight: 170, width: 50, height: 30" name="classroom_room">
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
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>时间:</td>
	    			<td>
						第
	    				<select id="edit_weeks_1" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 60, height: 30" name="weeks_1">
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
	    				<select id="edit_weeks_2" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 60, height: 30" name="weeks_2">
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
	    				<select id="edit_weeks_3" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 50, height: 30" name="weeks_3">
	    					<option value="1">  一</option>
	    					<option value="2">  二</option>
	    					<option value="3">  三</option>
	    					<option value="4">  四</option>
	    					<option value="5">  五</option>
	    					<option value="6"> 六</option>
	    					<option value="7">  日</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>节数:</td>
	    			<td>
						第
	    				<select id="edit_numbers_1" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 50, height: 30" name="numbers_1">
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
	    				<select id="edit_numbers_2" class="easyui-combobox" data-options="required:true, editable: false, panelHeight: 155, width: 50, height: 30" name="numbers_2">
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
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
</body>
</html>