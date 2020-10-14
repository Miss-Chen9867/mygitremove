<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>查询班级</title>
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
	        url:"ClazzServlet?method=FindClazz&t="+new Date().getTime(),
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
	        pageSize: 18,//每页显示的记录条数，默认为10 
	        pageList: [18,36,48,72,90],//可以设置每页记录条数的列表 
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
	    
	    $('#academic').combobox({
	        valueField: 'id',
	        textField: 'value',
	        data: testTypes,
	        onSelect: function (data) {
	            var bingGo = data.id;
	            if (bingGo != null && bingGo != "") {
	                if (bingGo == '经济与管理学院') {
	                	 $('#major').combobox({
		                    valueField: 'id',
		                    textField: 'value',
		                    panelHeight: 'auto',
		           		    editable: false,
		                  	data: jinguan
		              	});
	                } else if (bingGo == '数学与统计学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: shuxue
	                    });
	                }else if (bingGo == '物理与电子工程学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: wuli
	                    });
	                } else if (bingGo == '计算机科学与技术学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: jisuanji
	                    });
	                }else if (bingGo == '化学与制药工程学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: huaxue
	                    });
	                }else if (bingGo == '教育科学学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: jiaoyu
	                    });
	                }else if (bingGo == '历史文化学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: lishi
	                    });
	                }else if (bingGo == '生命科学与技术学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: shengming
	                    });
	                }else if (bingGo == '外国语学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: waiguoyu
	                    });
	                }else if (bingGo == '政治与公共管理学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: zhengzhi
	                    });
	                }else if (bingGo == '国际教育学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: guoji
	                    });
	                }else if (bingGo == '音乐学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: yinyue
	                    });
	                }else if (bingGo == '体育学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: tiyu
	                    });
	                }else if (bingGo == '文学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: wenxue
	                    });
	                }else if (bingGo == '法学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: faxue
	                    });
	                }else if (bingGo == '美术与艺术设计学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: meishu
	                    });
	                }else if (bingGo == '土木建筑工程学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: tumu
	                    });
	                }else if (bingGo == '新闻与传播学院') {
	                    $('#major').combobox({
	                        valueField: 'id',
	                        textField: 'value',
	                        panelHeight: 'auto',
	                        editable: false,
	                        data: xinwen
	                    });
	                }else if (bingGo == '环境科学与旅游学院') {
	                    $('#major').combobox({
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
	  //班号列表
	  $("#clazzid").combobox({
		  	panelHeight:"135",
	  		width: "150",
	  		height: "23",
	  		valueField: "clazzid",
	  		textField: "clazzid",
	  		multiple: false, //可多选
	  		editable: true, //可编辑
	  		method: "post",
	  		url:"ClazzServlet?method=getAllClazzList&t="+new Date().getTime()+"&from=combox",
	  });
	  //搜索点击事件
	  $("#search").click(function(){
	  		$('#dataList').datagrid('load',{
	  			academic: $('#academic').textbox('getValue'),
	  			grade: $('#grade').textbox('getValue'),
	  			major: $('#major').textbox('getValue'),
	  			clazz: $('#clazz').textbox('getValue'),
	  			clazzid: $('#clazzid').textbox('getValue'),
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
		<div style="float: left;">&nbsp;&nbsp;年级：<select id="grade" class="easyui-combobox" data-options=" editable: false, panelHeight: 90, width: 150, height: 23" name="grade">
	    	<option value=""></option>
	    	<option value="大一">大一</option>
	    	<option value="大二">大二</option>
	    	<option value="大三">大三</option>
	    	<option value="大四">大四</option>
	    </select>
		</div>
		<div style="float: left;">&nbsp;&nbsp;学院：<select id="academic" class="easyui-combobox" data-options=" editable: false, panelHeight: 150, width: 150, height: 23" name="academic">
	    </select>
		</div>
		<div style="float: left;">&nbsp;&nbsp;专业：<select id="major" class="easyui-combobox" data-options=" editable: false, panelHeight: 150, width: 150, height: 23" name="major">
	    </select></div>
		<div style="float: left;">&nbsp;&nbsp;班级：<select id="clazz" class="easyui-combobox" data-options=" editable: false, panelHeight: 90, width: 150, height: 23" name="clazz">
	    					<option value=""></option>
	    					<option value="1班">1班</option>
	    					<option value="2班">2班</option>
	    					<option value="3班">3班</option>
	    					<option value="4班">4班</option>
	    </select>
	    </div><div style="float: left;">&nbsp;&nbsp;班号： <input id="clazzid" class="easyui-textbox" name="clazzid" /></div>	
		<a id="search" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		<a id="refresh" href="javascript:location.reload();" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">重置</a>
	</div>
</body>
</html>