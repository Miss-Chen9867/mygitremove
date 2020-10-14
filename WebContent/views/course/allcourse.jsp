<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>课程列表</title>
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
	        title:'所有课程', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"CourseServlet?method=getAllCourseList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: false,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName:'id',
	        sortOrder:'ASC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},
 		     	{field:'courseId',title:'课程号',width:50},
		        {field:'courseName',title:'课程名',width:100},
 		       	{field:'credit',title:'学分',width:50},
 		       	{field:'previous',title:'先行课',width:50,
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
	  //增加点击事件
	  $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    	$("#add_academic").textbox('setValue', "");
			$("#add_coursename").textbox('setValue', "");
			$("#add_credit").textbox('setValue', "");
			$("#add_previous").textbox('setValue', "无");
	  });
	  //设置添加班级窗口
	  $("#addDialog").dialog({
	    	title: "添加班级",
	    	width: 500,
	    	height: 400,
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
							$.messager.alert("消息提醒","请务必输入信息!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "CourseServlet?method=AddCourse",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_academic").textbox('setValue', "");
										$("#add_coursename").textbox('setValue', "");
										$("#add_credit").textbox('setValue', "");
										$("#add_previous").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
									} else if(msg == "previousError"){
										$.messager.alert("消息提醒","添加失败,先行课错误，请正确输入/选择!","warning");
										return;
									} else{
										$.messager.alert("消息提醒","添加失败,该课程已存在!","warning");
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
						$("#add_academic").textbox('setValue', "");
						$("#add_coursename").textbox('setValue', "");
						$("#add_credit").textbox('setValue', "");
						$("#add_previous").textbox('setValue', "无");
					}
				},
			],
	 		onBeforeOpen: function(){
				//设置值
	 			$("#add_academic").textbox('setValue', "");
				$("#add_coursename").textbox('setValue', "");
				$("#add_credit").textbox('setValue', "");
				$("#add_previous").textbox('setValue', "无");
		}
	 });
	 //添加先选课选择下拉框
	 $("#add_previous").combobox({
		width: "150",
	 	height: "30",
	 	valueField: "courseId",
	 	textField:"courseName",
	 	multiple:false,//是否多选
	 	editable:false,//不可编辑
	 	method: "post",
	 	url:"CourseServlet?method=getAllCourseList&t="+new Date().getTime()+"&from=combox",
	 });
	//编辑先选课选择下拉框
	 $("#edit_previous").combobox({
		width: "150",
	 	height: "30",
	 	valueField: "courseId",
	 	textField:"courseName",
	 	multiple:false,//是否多选
	 	editable:false,//不可编辑
	 	method: "post",
	 	url:"CourseServlet?method=getAllCourseList&t="+new Date().getTime()+"&from=combox",
	 });
	 //编辑点击事件
	 $("#edit").click(function(){
		   	var selectRow = $("#dataList").datagrid("getSelected"); 
		  	if(selectRow == null){
				$.messager.alert("消息提醒","请选择数据进行修改！","warning");
				return;
		  	}
		  	$("#editDialog").dialog("open");
		  	$("#edit_coursename").textbox('setValue', selectRow.courseName);
			$("#edit_credit").textbox('setValue', selectRow.credit);
		  	$("#edit_previous").textbox('setValue', "无");
	 });  
	 //添加修改窗口
	 $("#editDialog").dialog({
	    	title: "编辑班级",
	    	width: 500,
	    	height: 400,
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
								url: "CourseServlet?method=EditCourse",
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","修改成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据
										$("#edit_coursename").textbox('setValue', "");
										$("#edit_credit").textbox('setValue', "");
										$("#add_previous").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","修改失败，已经存在该课程!","warning");
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
						var selectRow = $("#dataList").datagrid("getSelected"); 
						$("#edit_coursename").textbox('setValue', selectRow.courseName);
						$("#edit_credit").textbox('setValue', selectRow.credit);
						$("#add_previous").textbox('setValue', "无");
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit-courseid").val(selectRow.courseId);
				$("#edit-id").val(selectRow.id);
				$("#edit_coursename").textbox('setValue', selectRow.courseName);
				$("#edit_credit").textbox('setValue', selectRow.credit);
				$("#add_previous").textbox('setValue', "无");
			}
	    });	  
	  	//删除课程
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
	    	var selectRow = $("#dataList").datagrid("getSelected");
	      	if(selectRow == null && selectRows != 1){
	          	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
	          }else{
		  			var numbers = [];
		  			$(selectRows).each(function(i,row){
						numbers[i] = row.sno;	  				
		  			});
		  			var ids = [];
		  			$(selectRows).each(function(i, row){
	            		ids[i] = row.id;
	            	});
		  			$.messager.confirm("消息提醒", "将删除与课程相关的所有数据(包括成绩)，确认继续？", function(r){
	            		if(r){
	            			$.ajax({
								type: "post",
								url: "CourseServlet?method=DeleteCourse",
								data: {sns: numbers, ids: ids},
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","删除成功!","info");
										//刷新表格
										$("#dataList").datagrid("reload");
									} else{
										$.messager.alert("消息提醒","删除失败!","warning");
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
	<table id="dataList"> 
	</table>
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a></div>
	</div>
	
	
	<!--添加窗口-->
		<div id="addDialog" style="padding: 10px">  
    		<form id="addForm" method="post">
	    		<table cellpadding="8" >
	    			<tr>
	    			<td>院系:</td>
	    			<td>
	    				<select id="add_academic" class="easyui-combobox" data-options="required:true, missingMessage:'请选择院系', editable: false, panelHeight: 150, width: 150, height: 30" name="academic">
	    					<option value="文理通用课程">文理通用课程</option>
	    					<option value="理工通用课程">理工通用课程</option>
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
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>课程名:</td>
	    			<td>
	    				<input id="add_coursename" style="width: 150px; height: 30px;" class="easyui-textbox" type="text" name="coursename" data-options="required:true, missingMessage:'请填写课程名'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>学分:</td>
	    			<td>
	    			<input id="add_credit" style="width: 40px; height: 30px;" class="easyui-textbox" type="text" name="credit" validType="number" data-options="required:true, missingMessage:'请填写学分'" /> 分
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>先选课:</td>
	    			<td>
	    			<input id="add_previous" style="width: 150px; height: 30px;" class="easyui-textbox" type="text" name="previous" data-options="required:true, missingMessage:'请选择先选课，若无请手动填写“无”<br>填写无关字样会导致报错，谢谢！'" />
	    			</td>
	    		</tr>
	    	</table>
	</form>
	</div>
	
	<!--编辑窗口-->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    	<input type="hidden" id="edit-id" name="id">
    	<input type="hidden" id="edit-courseid" name="courseid">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>课程名:</td>
	    			<td>
	    				<input id="edit_coursename" style="width: 150px; height: 30px;" class="easyui-textbox" type="text" name="coursename" data-options="required:true, missingMessage:'请填写课程名'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>学分:</td>
	    			<td>
	    			<input id="edit_credit" style="width: 40px; height: 30px;" class="easyui-textbox" type="text" name="credit" validType="number" data-options="required:true, missingMessage:'请填写学分'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>先选课:</td>
	    			<td>
	    			<input id="edit_previous" style="width: 150px; height: 30px;" class="easyui-textbox" type="text" name="previous" data-options="required:true, missingMessage:'请选择先选课，若无请手动填写“无”<br>填写无关字样会导致报错，谢谢！'" />
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
</body>
</html>