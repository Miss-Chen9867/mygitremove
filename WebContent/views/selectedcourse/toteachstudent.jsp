<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生选课列表</title>
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
	        url:"SelectedCourseServlet?method=getTeachStudent&t="+new Date().getTime(),
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
 		   		{field:'sno',title:'学号',width:110},
 		       	{field:'studentname',title:'学生姓名',width:110},
 		       	{field:'courseid',title:'课程号',width:110},
 		       	{field:'coursename',title:'课程名',width:200},
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
			    	width: 300,
			    	height: 150,
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
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	</table>
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">评测成绩</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a></div>
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
</body>
</html>