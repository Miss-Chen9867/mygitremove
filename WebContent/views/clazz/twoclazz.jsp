<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>班级列表</title>
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
	        title:'班级列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"ClazzServlet?method=getTwoClazzList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName: 'id',
	        sortOrder: 'ASC', 
	        remoteSort: false,
	        columns: [[
	        	{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:30, sortable: true}, 
 		       	{field:'clazzid',title:'班号',width:60},
 		       	{field:'grade',title:'年级',width:40},
 		        {field:'academic',title:'院系',width:200},
 		       	{field:'major',title:'专业',width:200},
 		        {field:'name',title:'班级',width:100, 
 		        },
	 		]], 
	 		toolbar: "#toolbar"
	    });
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 18,//每页显示的记录条数，默认为18 
	        pageList: [18,36,54,72,108],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    });
	    
	    //设置工具类按钮
	    //添加 点击事件
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    	$("#add_grade").textbox('setValue', "");
			$("#add_academic").textbox('setValue', "");
			$("#add_major").textbox('setValue', "");
			$("#add_name").textbox('setValue', "");
	    });
	    
	    var testTypes = [
	    	{id: '经济与管理学院', value: '经济与管理学院'},
	        {id: '数学与统计学院', value: '数学与统计学院'},
	        {id: '物理与电子工程学院', value: '物理与电子工程学院'},
	        {id: '计算机科学与技术学院', value: '计算机科学与技术学院'},
	        {id: '化学与制药工程学院', value: '化学与制药工程学院'},
	        {id: '教育科学学院', value: '教育科学学院'},
	        {id: '历史文化学院', value: '历史文化学院'},
	        {id: '生命科学与技术学院', value: '生命科学与技术学院'},
	        {id: '外国语学院', value: '外国语学院'},
	        {id: '政治与公共管理学院', value: '政治与公共管理学院'},
	        {id: '国际教育学院', value: '国际教育学院'},
	        {id: '音乐学院', value: '音乐学院'},
	        {id: '体育学院', value: '体育学院'},
	        {id: '文学院', value: '文学院'},
	        {id: '法学院', value: '法学院'},
	        {id: '美术与艺术设计学院', value: '美术与艺术设计学院'},
	        {id: '土木建筑工程学院', value: '土木建筑工程学院'},
	        {id: '新闻与传播学院', value: '新闻与传播学院'},
	        {id: '环境科学与旅游学院', value: '环境科学与旅游学院'},
	    ];

	    var jinguan = [
	    	{id: '工商管理专业', value: '工商管理专业'},
	        {id: '市场营销专业', value: '市场营销专业'},
	        {id: '人力资源专业', value: '人力资源专业'},
	        {id: '财务管理专业', value: '财务管理专业'},
	        {id: '会计学专业', value: '会计学专业'},
	        {id: '财政学专业', value: '财政学专业'},
	        {id: '国际经济与贸易专业', value: '国际经济与贸易专业'},
	        {id: '金融学专业', value: '金融学专业'},
	        {id: '保险专业', value: '保险专业'},
	        ];
	    
	    var wuli = [
	    	{id: '物理学专业', value: '物理学专业'},
	        {id: '电子信息科学与技术专业', value: '电子信息科学与技术专业'},
	        {id: '光电信息科学专业', value: '光电信息科学专业'},
	        {id: '自动化专业', value: '自动化专业'},
	        ];
	    
	    var jisuanji = [
	    	{id: '计算机科学与技术专业', value: '计算机科学与技术专业'},
	        {id: '大数据工程专业', value: '大数据工程专业'},
	        {id: '物联网工程专业', value: '物联网工程专业'},
	        {id: '软件工程专业', value: '软件工程专业'},
	        {id: '网络工程专业', value: '网络工程专业'},
	        ];

	    var huaxue = [
	    	{id: '化学工程与工艺专业', value: '化学工程与工艺专业'},
	        {id: '能源化学工程专业', value: '能源化学工程专业'},
	        {id: '制药工程专业', value: '制药工程专业'},
	        {id: '药物制剂专业', value: '药物制剂专业'},
	        {id: '药学专业', value: '药学专业'}
	        ];
	    
	    var huanjing = [
	    	{id: '地理科学专业', value: '地理科学专业'},
	        {id: '地理信息科学专业', value: '地理信息科学专业'},
	        {id: '测绘工程专业', value: '测绘工程专业'}
	        ];
	    
	    var jiaoyu = [
	    	{id: '教育学专业', value: '教育学专业'},
	        {id: '心理学专业', value: '心理学专业'},
	        {id: '学前教育学专业', value: '学前教育学专业'}
	        ];
	    
	    var lishi = [
	    	{id: '文化产业管理专业', value: '文化产业管理专业'},
	        {id: '人文教育专业', value: '人文教育专业'}
	        ];
	    
	    var shengming = [
	    	{id: '生物技术专业专业', value: '生物技术专业专业'},
	        {id: '园林专业', value: '园林专业'}
	        ];
	    
	    var waiguoyu = [
	    	{id: '英语专业', value: '英语专业'},
	        {id: '商务英语专业', value: '商务英语专业'},
	        {id: '日语专业', value: '日语专业'}
	        ];
	    
	    var zhengzhi = [
	    	{id: '哲学专业', value: '哲学专业'},
	        {id: '政治学专业专业', value: '政治学专业专业'}
	        ];
	    
	    
	    var guoji = [
	    	{id: '汉语国际教育专业', value: '汉语国际教育专业'}
	        ];
	    
	    var yinyue = [
	    	{id: '钢琴专业', value: '钢琴专业'},
	        {id: '声乐专业', value: '声乐专业'}
	        ];
	    
	    var tiyu = [
	    	{id: '体育教育专业', value: '体育教育专业'},
	        {id: '社会教育专业', value: '社会教育专业'}
	        ];
	    
	    
	    var wenxue = [
	    	{id: '中国文学专业', value: '中国文学专业'},
	        {id: '语言学专业', value: '语言学专业'}
	        ];
	    
	    
	    var faxue = [
	    	{id: '法学专业', value: '法学专业'},
	        {id: '侦查学专业', value: '侦查学专业'}
	        ];
	    
	    var meishu = [
	    	{id: '平面设计专业', value: '平面设计专业'},
	        {id: '工业设计专业', value: '工业设计专业'}
	        ];
	    
	    var tumu = [
	    	{id: '土木工程专业', value: '土木工程专业'},
	        {id: '建筑环境与能源应用工程专业', value: '建筑环境与能源应用工程专业'}
	        ];
	    
	    var xinwen = [
	        {id: '新闻学专业', value: '新闻学专业'},
	        {id: '传播学专业', value: '传播学专业'}
	        ];
	    
	    var shuxue = [
	    	{id: '数学与应用数学专业', value: '数学与应用数学专业'},
	        {id: '统计学专业', value: '统计学专业'},
	        {id: '金融工程专业', value: '金融工程专业'},
	        {id: '金融科学专业', value: '金融科学专业'},
	        {id: '信息与计算科学专业', value: '信息与计算科学专业'}
	        ];
	    
	    $('#add_academic').combobox({
	        valueField: 'id',
	        textField: 'value',
	        data: testTypes,
	        onSelect: function (data) {
	            var bingGo = data.id;
	            if (bingGo != null && bingGo != "") {
	                if (bingGo == '经济与管理学院') {
	                	 $('#add_major').combobox({
		                    valueField: 'id',
		                    textField: 'value',
		                    panelHeight: 'auto',
		           		    editable: false,
		                  	data: jinguan
		              	});
	                } else if (bingGo == '数学与统计学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: shuxue
	                    });
	                }else if (bingGo == '物理与电子工程学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: wuli
	                    });
	                } else if (bingGo == '计算机科学与技术学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: jisuanji
	                    });
	                }else if (bingGo == '化学与制药工程学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: huaxue
	                    });
	                }else if (bingGo == '教育科学学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: jiaoyu
	                    });
	                }else if (bingGo == '历史文化学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: lishi
	                    });
	                }else if (bingGo == '生命科学与技术学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: shengming
	                    });
	                }else if (bingGo == '外国语学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: waiguoyu
	                    });
	                }else if (bingGo == '政治与公共管理学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: zhengzhi
	                    });
	                }else if (bingGo == '国际教育学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: guoji
	                    });
	                }else if (bingGo == '音乐学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: yinyue
	                    });
	                }else if (bingGo == '体育学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: tiyu
	                    });
	                }else if (bingGo == '文学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: wenxue
	                    });
	                }else if (bingGo == '法学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: faxue
	                    });
	                }else if (bingGo == '美术与艺术设计学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: meishu
	                    });
	                }else if (bingGo == '土木建筑工程学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: tumu
	                    });
	                }else if (bingGo == '新闻与传播学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: xinwen
	                    });
	                }else if (bingGo == '环境科学与旅游学院') {
	                    $('#add_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: huanjing
	                    });
	                }
	            }
	        }
	    });
	    
	    $('#edit_academic').combobox({
	        valueField: 'id',
	        textField: 'value',
	        data: testTypes,
	        onSelect: function (data) {
	            var bingGo = data.id;
	            if (bingGo != null && bingGo != "") {
	                if (bingGo == '经济与管理学院') {
	                	 $('#edit_major').combobox({
		                    valueField: 'id',
		                    textField: 'value',
		                    panelHeight: 'auto',
		           		    editable: false,
		                  	data: jinguan
		              	});
	                } else if (bingGo == '数学与统计学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: shuxue
	                    });
	                }else if (bingGo == '物理与电子工程学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: wuli
	                    });
	                } else if (bingGo == '计算机科学与技术学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: jisuanji
	                    });
	                }else if (bingGo == '化学与制药工程学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: huaxue
	                    });
	                }else if (bingGo == '教育科学学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: jiaoyu
	                    });
	                }else if (bingGo == '历史文化学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: lishi
	                    });
	                }else if (bingGo == '生命科学与技术学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: shengming
	                    });
	                }else if (bingGo == '外国语学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: waiguoyu
	                    });
	                }else if (bingGo == '政治与公共管理学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: zhengzhi
	                    });
	                }else if (bingGo == '国际教育学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: guoji
	                    });
	                }else if (bingGo == '音乐学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: yinyue
	                    });
	                }else if (bingGo == '体育学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: tiyu
	                    });
	                }else if (bingGo == '文学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: wenxue
	                    });
	                }else if (bingGo == '法学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: faxue
	                    });
	                }else if (bingGo == '美术与艺术设计学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: meishu
	                    });
	                }else if (bingGo == '土木建筑工程学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: tumu
	                    });
	                }else if (bingGo == '新闻与传播学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: xinwen
	                    });
	                }else if (bingGo == '环境科学与旅游学院') {
	                    $('#edit_major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: huanjing
	                    });
	                }
	            }
	        }
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
							$.messager.alert("消息提醒","请务必选择你的年级、院系、专业和班级!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "ClazzServlet?method=AddClazz",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_grade").textbox('setValue', "");
										$("#add_academic").textbox('setValue', "");
										$("#add_major").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									}else{
										$.messager.alert("消息提醒","添加失败,可能造成的原因：该班级已存在!","warning");
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
						$("#add_grade").textbox('setValue', "");
						$("#add_academic").textbox('setValue', "");
						$("#add_major").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  	//修改点击事件
	  	$("#edit").click(function(){
	  		var selectRow = $("#dataList").datagrid("getSelected");
	  		if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行修改!", "warning");
            	return;
	  		}
	  		$("#editDialog").dialog("open");
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
								url: "ClazzServlet?method=EditClazz",
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","修改成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据
										$("#edit_name").textbox('setValue', "");
										$("#edit_grade").textbox('setValue', "");
										$("#edit_academic").textbox('setValue', "");
										$("#edit_major").textbox('setValue', "");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","修改失败，原先班级存在学生!","warning");
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
						$("#edit_name").textbox('setValue', "");
						$("#edit_grade").textbox('setValue', "");
						$("#edit_academic").textbox('setValue', "");
						$("#add_major").textbox('setValue', "");
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_grade").textbox('setValue', selectRow.grade);
				$("#edit_academic").textbox('setValue', selectRow.academic);
				$("#edit_major").textbox('setValue', selectRow.major);
				$("#edit-id").val(selectRow.id);
			}
	    });	  	
	  	
	  	//删除点击事件
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{
            	var clazzid = selectRow.clazzid;
            	$.messager.confirm("消息提醒", "将删除班级信息（如果班级下存在学生或教师则不能删除），确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "ClazzServlet?method=DeleteClazz",
							data: {clazzid:clazzid},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//重新加载表格
									$("#dataList").datagrid("reload");
								} else{
									$.messager.alert("消息提醒","删除失败，可能存在学生和教师!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	  	
	  	//搜索点击事件
	  	$("#search").click(function(){
	  		$('#dataList').datagrid('load',{
	  			majorName: $('#majorName').val(),
	  			academicName: $('#academicName').val()
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
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;院名： <input id="academicName" class="easyui-textbox" name="academicName" /></div>
		<div style="float: left; margin-top:4px;">&nbsp;&nbsp;专业： <input id="majorName" class="easyui-textbox" name="majorName" /></div>
		<a id="search" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
    	
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>年级:</td>
	    			<td>
	    				<select id="add_grade" class="easyui-combobox" data-options="required:true, missingMessage:'请选择年级', editable: false, panelHeight: 90, width: 150, height: 30" name="grade">
	    					<option value="大一">大一</option>
	    					<option value="大二">大二</option>
	    					<option value="大三">大三</option>
	    					<option value="大四">大四</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>院系:</td>
	    			<td>
	    				<select id="add_academic" class="easyui-combobox" data-options="required:true, missingMessage:'请选择院系', editable: false, panelHeight: 150, width: 150, height: 30" name="academic">
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>专业:</td>
	    			<td>
	    				<select id="add_major" class="easyui-combobox" data-options="required:true, missingMessage:'请选择专业', editable: false, panelHeight: 'auto', width: 150, height: 30" name="major">
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td>
	    				<select id="add_name" class="easyui-combobox" data-options="required:true, missingMessage:'请选择班级', editable: false, panelHeight: 90, width: 150, height: 30" name="name">
	    					<option value="1班">1班</option>
	    					<option value="2班">2班</option>
	    					<option value="3班">3班</option>
	    					<option value="4班">4班</option>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	</form>
	</div>
	
	<!-- 编辑窗口 -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    	<input type="hidden" id="edit-id" name="id">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>年级:</td>
	    			<td>
	    				<select id="edit_grade" class="easyui-combobox" data-options="required:true, missingMessage:'请选择年级', editable: false, panelHeight: 90, width: 150, height: 30" name="grade">
	    					<option value="大一">大一</option>
	    					<option value="大二">大二</option>
	    					<option value="大三">大三</option>
	    					<option value="大四">大四</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>院系:</td>
	    			<td>
	    				<select id="edit_academic" class="easyui-combobox" data-options="required:true, missingMessage:'请选择院系', editable: false, panelHeight: 150, width: 150, height: 30" name="academic">
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>专业:</td>
	    			<td>
	    				<select id="edit_major" class="easyui-combobox" data-options="required:true, missingMessage:'请选择专业', editable: false, panelHeight: 'auto', width: 150, height: 30" name="major">
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td>
	    				<select id="edit_name" class="easyui-combobox" data-options="required:true, missingMessage:'请选择班级', editable: false, panelHeight: 90, width: 150, height: 30" name="name">
	    					<option value="1班">1班</option>
	    					<option value="2班">2班</option>
	    					<option value="3班">3班</option>
	    					<option value="4班">4班</option>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>