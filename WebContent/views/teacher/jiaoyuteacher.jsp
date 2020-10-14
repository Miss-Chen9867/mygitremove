<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>教师列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		var table;
		
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'教师列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"TeacherServlet?method=getJiaoYuTeacherList&t="+new Date().getTime(),
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
	    	pageSize: 18,//每页显示的记录条数，默认为10 
	        pageList: [18,36,54,72,108],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    //设置工具类按钮
	    
	    //增添
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "将删除与教师相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "TeacherServlet?method=DeleteTeacher",
							data: {ids: ids},
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
	    
	  	//设置添加窗口
	    $("#addDialog").dialog({
	    	title: "添加教师",
	    	width: 650,
	    	height: 460,
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
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "TeacherServlet?method=AddTeacher",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_password").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$("#add_sex").textbox('setValue', "男");
										$("#add_phone").textbox('setValue', "");
										$("#add_rztime").textbox('setValue',"");
										$("#add_academic").textbox('setValue', "");
										$("#add_major").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","添加失败!","warning");
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
						$("#add_password").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
						$("#add_sex").textbox('setValue', "男");
						$("#add_phone").textbox('setValue', "");
						$("#add_rztime").textbox('setValue',"");
						$("#add_academic").textbox('setValue', "");
						$("#add_major").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  	//编辑教师信息
	  	$("#editDialog").dialog({
	  		title: "修改教师信息",
	    	width: 850,
	    	height: 550,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'确认',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "TeacherServlet?method=EditTeacher&t="+new Date().getTime(),
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","修改成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据
										$("#edit_password").textbox('setValue', "");
										$("#edit_name").textbox('setValue', "");
										$("#edit_sex").textbox('setValue', "男");
										$("#edit_phone").textbox('setValue', "");
										$("#edit_rztime").textbox('setValue',"");
										$("#edit_academic").textbox('setValue', "");
										$("#edit_major").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","修改失败!","warning");
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
						$("#edit_password").textbox('setValue', "");
						$("#edit_name").textbox('setValue', "");
						$("#edit_sex").textbox('setValue', "男");
						$("#edit_phone").textbox('setValue', "");
						$("#edit_rztime").textbox('setValue',"");
						$("#edit_academic").textbox('setValue', "");
						$("#edit_major").textbox('setValue', "");	
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit-id").val(selectRow.id);
				$("#edit_password").textbox('setValue',selectRow.password);
				$("#edit_name").textbox('setValue',selectRow.name);
				$("#edit_sex").textbox('setValue',selectRow.sex);
				$("#edit_phone").textbox('setValue',selectRow.phone);
				$("#edit_rztime").textbox('setValue',selectRow.rztime)
				$("#edit_academic").textbox('setValue',selectRow.academic);
				$("#edit_major").textbox('setValue',selectRow.major);
			}
	    });
	   	
	  	 //搜索按钮监听事件
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			tno:$('#tno').val(),
	  			teachername: $('#teachername').val()
	  		});
	  	});
	  	 
		//格式化增加生日下拉框
	  	$('#add_rztime').datebox({
	  		formatter:function(date){
	  			var y = date.getFullYear();
	  			var m = date.getMonth()+1;
	  			var d = date.getDate();
		  		return y+'-'+m+'-'+d	
	  		},
	  		parser:function(s){
	  			var t = Date.parse(s);
	  			if (!isNaN(t)){
	  				return new Date(t);
	  			} else {
	  				return new Date();
	  			}
	  	}
	  	});
	  	
	  	//格式化编辑生日下拉框
	  	$('#edit_rztime').datebox({
	  		formatter:function(date){
	  			var y = date.getFullYear();
	  			var m = date.getMonth()+1;
	  			var d = date.getDate();
	  			return y+'-'+m+'-'+d;	
	  		},
	  		parser:function(s){
	  			var t = Date.parse(s);
	  			if (!isNaN(t)){
	  				return new Date(t);
	  			} else {
	  				return new Date();
	  			}
	  	}
	  	}); 
	});
	
	//上传图片按钮事件
	$("#upload-photo-btn").click(function(){
		
	});
	function uploadPhoto(){
		var action = $("#uploadForm").attr('action');
		var pos = action.indexOf('tid');
		if(pos != -1){
			action = action.substring(0,pos-1);
		}
		$("#uploadForm").attr('action',action+'&tid='+$("#set-photo-id").val());
		$("#uploadForm").submit();
		setTimeout(function(){
			var message =  $(window.frames["photo_target"].document).find("#message").text();
			$.messager.alert("消息提醒",message,"info");
			
			$("#edit_photo").attr("src", "PhotoServlet?method=getPhoto&tid="+$("#set-photo-id").val());
		}, 1500)
	}
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
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
		<div style="float: left;"><a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a></div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">   
   		<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td>
	    				<input id="add_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请输入登录密码'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td>
	    				<select id="add_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex">
	    					<option value="男">男</option>
	    					<option value="女">女</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>联系电话:</td>
	    			<td><input id="add_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" validType="mobile" data-options="required:true, missingMessage:'请填写联系电话'" /></td>
	    		</tr>
	    		<tr>
	    			<td>入职日期:</td>
	    			<td><input id="add_rztime" style="width: 200px; height: 30px;" class="easyui-datebox" type="text" required="required" name="rztime" data-option="formatter:myformatter;parse:myparser"></td>
	    		</tr>
	    		<tr>
	    			<td>所属院系:</td>
	    			<td>
	    				<select id="add_academic" class="easyui-combobox" data-options="required:true, missingMessage:'请选择院系', editable: false, panelHeight: 150, width: 150, height: 30" name="academic">
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
	    			<td>职务:</td>
	    			<td>
	    				<select id="add_major" class="easyui-combobox" data-options="required:true, missingMessage:'请选择专业', editable: false, panelHeight: 'auto', width: 150, height: 30" name="major">
							<option value="院长">院长</option>
	    					<option value="副院长">副院长</option>
	    					<option value="教授">教授</option>
	    					<option value="副教授">副教授</option>
	    					<option value="讲师">讲师</option>
	    					<option value="辅导员">辅导员</option>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	
	<!-- 修改窗口 -->
		<div id="editDialog" style="padding: 10px">  
    		<form id="editForm" method="post">
    		<input type="hidden" name="id" id="edit-id">
    			<table cellpadding="8" >
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td>
	    				<input id="edit_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请输入登录密码'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td>
	    				<select id="edit_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex">
	    					<option value="男">男</option>
	    					<option value="女">女</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>联系电话:</td>
	    			<td><input id="edit_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" validType="mobile" data-options="required:true, missingMessage:'请填写联系电话'" /></td>
	    		</tr>
	    		<tr>
	    			<td>入职日期:</td>
	    			<td><input id="edit_rztime" style="width: 200px; height: 30px;" class="easyui-datebox" type="text" required="required" name="rztime" data-option="formatter:myformatter;parse:myparser"></td>
	    		</tr>
	    		<tr>
	    			<td>所属院系:</td>
	    			<td>
	    				<select id="edit_academic" class="easyui-combobox" data-options="required:true, missingMessage:'请选择院系', editable: false, panelHeight: 150, width: 150, height: 30" name="academic">
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
	    			<td>职务:</td>
	    			<td>
	    				<select id="edit_major" class="easyui-combobox" data-options="required:true, missingMessage:'请选择专业', editable: false, panelHeight: 'auto', width: 150, height: 30" name="major">
							<option value="院长">院长</option>
	    					<option value="副院长">副院长</option>
	    					<option value="教授">教授</option>
	    					<option value="副教授">副教授</option>
	    					<option value="讲师">讲师</option>
	    					<option value="辅导员">辅导员</option>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>	
</body>
</html>