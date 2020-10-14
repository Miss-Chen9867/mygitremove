<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>选课列表</title>
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
	        url:"SelectedCourseServlet?method=SelectedCourseList&t="+new Date().getTime(),
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
	    //设置工具类按钮
	    //添加
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
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
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var id = selectRow.id;
            	$.messager.confirm("消息提醒", "将删除与课程相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "SelectedCourseServlet?method=DeleteSelectedCourse",
							data: {id: id},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
								}else if(msg == "not found"){
									$.messager.alert("消息提醒","不存在该选课记录!","info");
								}else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	  	//添加窗口
	    $("#addDialog").dialog({
	    	title: "添加选课信息",
	    	width: 450,
	    	height: 350,
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
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "SelectedCourseServlet?method=AddSelectedCourse",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","选课信息添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_studentList").textbox('setValue', "");
										$("#add_teacherList").textbox('setValue',"");
										$("#add_courseList").textbox('setValue', "");
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
										$.messager.alert("消息提醒","请务必填入学号和课程号!","warning");
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
					iconCls:'icon-book-reset',
					handler:function(){
						$("#add_studentList").textbox('setValue', "");
						$("#add_teacherList").textbox('setValue',"");
						$("#add_courseList").textbox('setValue', "");
					}
				},
			]
	    });
	  //教师列表
	  $("#add_teacherList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "tno",
	  		textField: "tno",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"TeacherServlet?method=AllTeacherList&t="+new Date().getTime()+"&from=combox",
	  });
	  //学生列表
	  $("#add_studentList").combobox({
		  	panelHeight:"135",
	  		width: "200",
	  		height: "30",
	  		valueField: "sno",
	  		textField: "sno",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"StudentServlet?method=getAllStudentList&t="+new Date().getTime()+"&from=combox",
	  });
	  //课程列表
	  $("#add_courseList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "courseId",
	  		textField: "courseName",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"CourseServlet?method=getAllCourseList&t="+new Date().getTime()+"&from=combox",
	  });
	  //添加修改窗口
	  $("#editDialog").dialog({
		    	title: "编辑班级",
		    	width: 500,
		    	height: 400,
		    	iconCls: "icon-edit",
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
						iconCls:'icon-edit',
						handler:function(){
							var validate = $("#editForm").form("validate");
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{
								$.ajax({
									type: "post",
									url: "SelectedCourseServlet?method=EditSelectedCourse",
									data: $("#editForm").serialize(),
									success: function(msg){
										if(msg == "success"){
											$.messager.alert("消息提醒","修改成功!","info");
											//关闭窗口
											$("#editDialog").dialog("close");
											//清空原表格数据
											$("#edit_score").textbox('setValue', "");
											//重新刷新页面数据
								  			$('#dataList').datagrid("reload");
										} else if(msg == "ScoreError"){ 
											$.messager.alert("消息提醒","您输入的成绩有误!","warning");
											return;
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
							$("#edit_score").textbox('setValue', selectRow.score);
						}
					},
				],
				onBeforeOpen: function(){
					var selectRow = $("#dataList").datagrid("getSelected");
					//设置值
					$("#edit-id").val(selectRow.id);
					$("#edit_score").textbox('setValue', selectRow.score);
				}
		});
	  	//导入
	    $("#import").click(function(){
	    	$("#importDialog").dialog("open");
	    });
		//设置导入窗口
	    $("#importDialog").dialog({
	    	title: "导入成绩信息",
	    	width: 450,
	    	height: 150,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'确认导入',
					plain: true,
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#importForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请选择文件!","warning");
							return;
						} else{
							importSelectedCourse();
							$("#importDialog").dialog("close");
						}
					}
				}
			]
	    });
		//导入选课记录
		function importSelectedCourse(){
				$("#importForm").submit();
				$.messager.progress({text:'正在上传导入中...'});
				var interval = setInterval(function(){
					var message =  $(window.frames["import_target"].document).find("#message").text();
					if(message != null && message != ''){
						$.messager.progress('close');
						$.messager.alert("消息提醒",message,"info");
						$('#dataList').datagrid("reload");
						clearInterval(interval);
					}
				}, 1000)
		};
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
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">退选</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="import" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">导入</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="export" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">导出</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a></div>
	</div>
	
	<!-- 添加数据窗口 -->
	<div id="addDialog" style="padding: 10px">
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:40px">教师号:</td>
	    			<td colspan="3">
	    				<input id="add_teacherList" style="width: 200px; height: 30px;" class="easyui-textbox" data-options="required:true, missingMessage:'请选择教师信息'" name="tno" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">学号:</td>
	    			<td colspan="3">
	    				<input id="add_studentList" style="width: 200px; height: 30px;" class="easyui-textbox" data-options="required:true, missingMessage:'请选择学生信息'" name="sno" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">课程号:</td>
	    			<td colspan="3">
	    				<input id="add_courseList" style="width: 200px; height: 30px;" class="easyui-textbox" data-options="required:true, missingMessage:'请选择课程信息'" name="courseid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">成绩:</td>
	    			<td colspan="3">
	    				<input id="add_courseList" style="width: 200px; height: 30px;" class="easyui-textbox" data-options="missingMessage:'请输入成绩信息，格式如：99.5，99.0等'" name="score" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>评级:</td>
	    			<td>
	    				<select id="add_remark" class="easyui-combobox" data-options="editable: false, panelHeight: 90, width: 70, height: 30" name="remark">
	    					<option value="优秀">优秀</option>
	    					<option value="良好">良好</option>
	    					<option value="及格">及格</option>
	    					<option value="不及格">不及格</option>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<!--编辑窗口-->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    	<input type="hidden" id="edit-id" name="id">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>成绩:</td>
	    			<td>
	    			<input id="edit_score" style="width: 50px; height: 30px;" class="easyui-textbox" type="text" name="score" data-options="missingMessage:'请填写成绩，并按小数格式如：95.0等'" /> 分
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	<!-- 导入数据窗口 -->
	<div id="importDialog" style="padding: 10px">  
    	<form id="importForm" method="post" enctype="multipart/form-data" action="SelectedCourseServlet?method=ImportSelectedCourse" target="import_target">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>请选择文件:</td>
	    			<td>
	    				<input class="easyui-filebox" name="importSelectedCourse" data-options="required:true,min:0,precision:2, missingMessage:'请选择文件',prompt:'选择文件'" style="width:200px;">
	    			</td>
	    		</tr>
	    		
	    	</table>
	    </form>
	</div>
</body>
</html>