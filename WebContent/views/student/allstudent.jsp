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
	        url:"StudentServlet?method=getAllStudentList&t="+new Date().getTime(),
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
	    
	    //添加
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
	    
	    //编辑
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
	    
	    	
	  //添加点击事件
	  $('#add').click(function(){
		  	$("#addDialog").dialog("open");
		  	$("#add_home").textbox('setValue', "");
			$("#add_password").textbox('setValue', "");
			$("#add_name").textbox('setValue', "");
			$("#add_sex").textbox('setValue', "男");
			$("#add_phone").textbox('setValue', "");
			$("#add_clazzid").textbox('setValue', "");
			$("#add_birthday").textbox('setValue',"");
			$("#add_grade").textbox('setValue', "");
			$("#add_academic").textbox('setValue', "");
			$("#add_major").textbox('setValue', "");
			$("#add_clazz").textbox('setValue', "");
	  });
	  //添加窗口  
	  $("#addDialog").dialog({
	    	title: "添加学生",
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
							$.messager.alert("消息提醒","请输入数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "StudentServlet?method=AddStudent",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_home").textbox('setValue', "");
										$("#add_password").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$("#add_sex").textbox('setValue', "男");
										$("#add_phone").textbox('setValue', "");
										$("#add_clazzid").textbox('setValue', "");
										$("#add_birthday").textbox('setValue',"");
										$("#add_grade").textbox('setValue', "");
										$("#add_academic").textbox('setValue', "");
										$("#add_major").textbox('setValue', "");
										$("#add_clazz").textbox('setValue', "");	
										//重新刷新页面数据
										window.location.reload();	
									} else if(msg == "AgeError"){
										$.messager.alert("消息提醒","不符合入学年龄要求!","warning");
										return;
									} else{
										$.messager.alert("消息提醒","添加失败，请检查欲加入班级是否存在!","warning");
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
						$("#add_home").textbox('setValue', "");
						$("#add_password").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
						$("#add_sex").textbox('setValue', "男");
						$("#add_phone").textbox('setValue', "");
						$("#add_clazzid").textbox('setValue', "");
						$("#add_birthday").textbox('setValue',"");
						$("#add_grade").textbox('setValue', "");
						$("#add_academic").textbox('setValue', "");
						$("#add_major").textbox('setValue', "");
						$("#add_clazz").textbox('setValue', "");
					}
				},
			]
	    });
	  
	  //编辑点击事件
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
      	if(selectRows.length < 1){
          	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
          } else{
		    	$("#editDialog").dialog("open");
          }
	    });
	  
	  //设置编辑学生窗口
	    $("#editDialog").dialog({
	    	title: "学生信息（可修改）",
	    	width: 650,
	    	height: 460,
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
								url: "StudentServlet?method=EditStudent&t="+new Date().getTime(),
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","更新成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//刷新表格
										$("#edit_home").textbox('setValue', "");
										$("#edit_password").textbox('setValue', "");
										$("#edit_name").textbox('setValue', "");
										$("#edit_sex").textbox('setValue', "");
										$("#edit_gratuated").textbox('setValue', "");
										$("#edit_phone").textbox('setValue', "");
										$("#edit_birthday").textbox('setValue',"");
										window.location.reload();
									} else if(msg == "AgeError"){
										$.messager.alert("消息提醒","年龄有误!","warning");
										return;
									} else{
										$.messager.alert("消息提醒","更新失败，请检查欲加入的班级是否存在!","warning");
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
						//清空表单
						$("#edit_home").textbox('setValue', "");
						$("#edit_password").textbox('setValue', "");
						$("#edit_name").textbox('setValue', "");
						$("#edit_sex").textbox('setValue', "男");
						$("#edit_gratuated").textbox('setValue', "否");
						$("#edit_phone").textbox('setValue', "");
						$("#edit_clazzid").textbox('setValue', "");
						$("#edit_birthday").textbox('setValue',"");
					}
				}
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_home").textbox('setValue', selectRow.home);
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_sex").textbox('setValue', selectRow.sex);
				$("#edit_gratuated").textbox('setValue', selectRow.gratuated);
				$("#edit_password").textbox('setValue', selectRow.password);
				$("#edit_phone").textbox('setValue', selectRow.phone);	
				$("#edit_birthday").textbox('setValue', selectRow.birthday);	
				$("#edit-id").val(selectRow.id);
				$("#edit-sno").val(selectRow.sno);
				$("#edit-clazzid").val(selectRow.clazzid);
				$("#edit_photo").attr("src", "PhotoServlet?method=getPhoto&sno="+selectRow.sno);
				$("#set-photo-sno").val(selectRow.sno);
			}
	    });
	  	//删除点击事件
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
		  			$.messager.confirm("消息提醒", "将删除与学生相关的所有数据(包括成绩)，确认继续？", function(r){
	            		if(r){
	            			$.ajax({
								type: "post",
								url: "StudentServlet?method=DeleteStudent",
								data: {numbers: numbers, ids: ids},
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
	  //格式化增加生日下拉框
	  	$('#add_birthday').datebox({
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
	  	//格式化编辑生日下拉框
	  	$('#edit_birthday').datebox({
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
	//上传图片
	function uploadPhoto(){
		var action = $("#uploadForm").attr('action');
		$("#uploadForm").attr('action',action+'&sno='+$("#set-photo-sno").val());
		$("#uploadForm").submit();
		setTimeout(function(){
			var message =  $(window.frames["photo_target"].document).find("#message").text();
			$.messager.alert("消息提醒",message,"info");
			$("#edit_photo").attr("src", "PhotoServlet?method=getPhoto&sno="+$("#set-photo-sno").val());
		}, 1500)
	}
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
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">查询OR修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div><a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a></div>
	</div>
	<!-- 添加学生窗口 -->
	<div id="addDialog" style="padding: 10px">
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
	    			<td>出生日期:</td>
	    			<td><input id="add_birthday" style="width: 200px; height: 30px;" class="easyui-datebox" type="text" required="required" name="birthday" data-option="formatter:myformatter;parse:myparser"></td>
	    		</tr>
	    		<tr>
	    			<td>家庭住址:</td>
	    			<td><input id="add_home" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="home" data-options="required:true, missingMessage:'请填写家庭住址'" /></td>
	    		</tr>
	    		<tr>
	    			<td>联系电话:</td>
	    			<td><input id="add_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" validType="mobile" data-options="required:true, missingMessage:'请填写联系电话'" /></td>
	    		</tr>
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
	    				<select id="add_clazz" class="easyui-combobox" data-options="required:true, missingMessage:'请选择班级', editable: false, panelHeight: 90, width: 150, height: 30" name="clazz">
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
	<!-- 编辑学生窗口 -->
	<div id="editDialog" style="padding: 10px">
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF">
	    	<img id="edit_photo" alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="" />
	    	<form id="uploadForm" method="post" enctype="multipart/form-data" action="PhotoServlet?method=SetPhoto" target="photo_target">
	    		<input type="hidden" name="sno" id="set-photo-sno">
		    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:75px;">
		    	<input id="upload-photo-btn" onClick="uploadPhoto()" class="easyui-linkbutton" style="width: 74px; height: 24px;" type="button" value="Upload File"/>
		    </form>
	    </div>
    	<form id="editForm" method="post">
    	<input type="hidden" name="id" id="edit-id">
    	<input type="hidden" name="sno" id="edit-sno">
    	<input type="hidden" name="clazzid" id="edit-clazzid">
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
	    			<td>出生日期:</td>
	    			<td><input id="edit_birthday" style="width: 200px; height: 30px;" class="easyui-datebox" type="text" required="required" name="birthday" data-option="formatter:myformatter;parse:myparser"></td>
	    		</tr>
	    		<tr>
	    			<td>家庭住址:</td>
	    			<td><input id="edit_home" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="home" data-options="required:true, missingMessage:'请填写家庭住址'" /></td>
	    		</tr>
	    		<tr>
	    			<td>联系电话:</td>
	    			<td><input id="edit_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" validType="mobile" data-options="required:true, missingMessage:'请填写联系电话'" /></td>
	    		</tr>
	    		<tr>
	    			<td>是否毕业:</td>
	    			<td>
	    				<select id="edit_gratuated" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="gratuated">
	    					<option value="是">是</option>
	    					<option value="否">否</option>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>	
	<!-- 提交表单处理iframe框架 -->
	<iframe id="photo_target" name="photo_target"></iframe>
</body>
</html>