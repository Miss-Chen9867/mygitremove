<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--利用easy-ui框架-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand"><!--用于描述网页-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><!--定义浏览器的渲染方式，IE装上chrome大脑-->
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" /><!--用户界面显示的可视化规则-->
<meta http-equiv="Cache-Control" content="no-siteapp" /><!--控制http缓存，搜索引擎不转码，感觉这行代码不是必要的-->
<!--引用link标签进而引用css代码-->
<link rel="shortcut icon" href="favion.ico"/><!--给网页加图标-->
<link rel="bookmark" href="favicon.ico"/>
<!-- （../）开头的目录表示该目录为当前目录的父目录  无论是上面还是下面都考虑了移动端浏览器的显示方式，在这里有点多余了-->
<!--移动端无法识别css样式和web网站（而是wap网站）-->
<!--stylesheet表示文档的外部样式表-->
<link href="h-ui/css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="h-ui/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="h-ui/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
<!--增添脚本 图像验证 表单刷新 动态内容-->
<script type="text/javascript" src="easyui/jquery.min.js"></script> 
<script type="text/javascript" src="h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="h-ui/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function(){
		//点击图片切换验证码
		$("#vcodeImg").click(function(){
			this.src="CpachaServlet?method=loginCapcha&t="+new Date().getTime();
		});
	
		//登录 利用ajax可以完成异步刷新
		$("#submitBtn").click(function(){			
			var data = $("#form").serialize();//输出序列值的表单
			$.ajax({
				type: "post",//规定请求类型
				url: "LoginServlet?method=Login",//规定发送请求 的页面，默认是当前界面
				data: data, //规定要发送给服务器的数据，就是上面我们获取的form表单里的数据
				dataType: "text", //预期服务器响应的数据类型
				success: function(msg){//请求成功运行的函数	
						if(msg == "userNull"){
							alert("用户名不允许为空！");
							$("#vcodeImg").click();//切换验证码
							$("input[name='vcode']").val("");//清空验证码输入框
						} else if("passwordNull" == msg){
							alert("密码不允许为空！");
							$("#vcodeImg").click();//切换验证码
							$("input[name='vcode']").val("");//清空验证码输入框
						} else if("vcodeNull" == msg){
							alert("验证码不允许为空！");
							$("#vcodeImg").click();//切换验证码
							$("input[name='vcode']").val("");//清空验证码输入框
						} else if("vcodeError" == msg){
							alert("验证码错误！请重新输入！");
							$("#vcodeImg").click();//切换验证码
							$("input[name='vcode']").val("");//清空验证码输入框
						} else if("loginError" == msg){
							alert("用户名或密码有误！请检查！");
							$("#vcodeImg").click();//切换验证码
							$("input[name='vcode']").val("");//清空验证码输入框
						} else if("loginSuccess" == msg){
							window.location.href = "SystemServlet?method=toAdminView";
						} else{
							alert(msg);
						}
				}
			});
		});
		
		//设置复选框
		$(".skin-minimal input").iCheck({
			radioClass:'iradio-pink',
			increaseAread: '25%'
		});
		
	});
</script>
<title>登录界面</title>
<meta name="keywords" content="学生选课管理系统"><!--关键词-->
</head>
<body>
	<div class="loginWraper"><!--div-块级元素 属于登录界面类 加载背景页面-->
		<div id="loginform" class="loginBox"><!--id类似于身份证 设置界面位置-->
			<form id="form" class="form form-horizontal" method="post"><!--图标与框框的水平距离 提交内容的表单-->
				
				<div class="row cl"><!--分行 提高美观度-->
					<!--i标签斜体 label这样点击文字也可以选中 增加鼠标用户体验-->
					<label class="form-label col-3"><i class="Hui-iconfont">&#xe64a;</i></label><!--获取图标-->
						<input id="username" name="username" type="text" placeholder="请输入用户名" class="input-text size-L">
				</div>
				
				<div class="row cl"><!--分行，提高美观度，要不然是挤在一起的-->
					<label class="form-label col-3"><i class="Hui-iconfont">&#xe6d1;</i></label><!--图标+图标和框框的距离-->
					<input id="password" name="password" type="password" placeholder="请输入密码" class="input-text size-L">
				</div>
				
				<div class="row cl">
					<label class="form-label col-3"><i class="Hui-iconfont">&#xe6a2;</i></label>
					<div class="formControls col-8 col-3">
						<input class="input-text size-L" name="vcode" type="text" placeholder="请输入验证码" style="width: 200px;">
						<img title="不要点我！不要点我！" id="vcodeImg" src="CpachaServlet?method=loginCapcha">
					</div>
				</div>
				
				<div class="mt-20 skin-minimal" style="text-align: center;">
					<div class="radio-box">
						<input type="radio" id="radio-1" name="type" checked value="1">
						<label for="radio-1">管理员</label><!--这样子点管理员字就可选中-->
					</div>
					
					<div class="radio-box">
						<input type="radio" id="radio-2" name="type" checked value="2">
						<label for="radio-2">老师</label><!--这样子点管理员字就可选中-->
					</div>
					
					<div class="radio-box">
						<input type="radio" id="radio-3" name="type" checked value="3">
						<label for="radio-3">学生</label><!--这样子点管理员字就可选中-->
					</div>
				</div>

				<div class="row">
					<div class="formControls col-9 col-offset-5">
						<input id="submitBtn" type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
					</div>
				</div>
			</form>
		</div>
	</div>	
</body>
</html>