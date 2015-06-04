<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>我联科技</title>
<link rel="shortcut icon" href="${ctx}/favicon.ico">
<link rel="bookmark" href="${ctx}/favicon.ico" />
<link rel="stylesheet" href="${ctx}/css/common.css">
<link rel="stylesheet" href="${ctx}/css/manager.css">
<!--[if lt IE 9]>
    <script src="${ctx}/js/html5shiv.js"></script>
    <![endif]-->
</head>

<body>
	<!--[if lt IE 8]>
    <div class="al_tips">
        <p>
            <span>已经有超过90%的用户使用更高版本</span>
            <a target="_blank" title="下载Chrome" href="http://www.google.com/chrome/">Google Chrome</a>
            <span>或</span>
            <a target="_blank" href="http://www.microsoft.com/zh-cn/download/ie.aspx?q=internet+explorer">Internet Explorer</a>
            <span>体验到了更精彩的页面，你还不试试？</span>
        </p>
    </div>
    <![endif]-->
	<section class="user_w"></section>
	<header></header>
	<section class="main">
		<section class="main_l"></section>
		<section class="main_r">
			<div class="main_t"></div>
			<div class="main_c">
				<ul class="main-con account-option-wrap">
				
					<li>
						<div>公司名称</div>
						<div>${sessionUser.account.gsmc}</div>
					</li>
					<li>
						<div>邮箱</div>
						<div>${sessionUser.loginname}</div>
					</li>
					<li>
						<div>账号名称</div>
						<div>
							<input type="text" class="form-control" 
							id="username" value="${sessionUser.username}">
						</div>
					</li>
					<li>
						<div>联系电话</div>
						<div>
							<input type="text" class="form-control"
							 id="lxdh"
							 value="${sessionUser.account.lxdh}"
							 >
						</div>
					</li>
					<li>
						<div>账号密码</div>
						<div>
							<a href="javascript:;" id="changePswBtn">更改密码</a>
						</div>
					</li>
					<li>
						<div></div>
						<div>
							<button type="button" class="btn btn-success" 
							onclick="subm()">提交</button>
						</div>
					</li>
				</ul>
			</div>
		</section>
	</section>
	<section class="alert_w">
		<div class="mask"></div>
		<div class="alert_c ch_pwd_w">
			<div class="title">更改密码</div>
			<ul>
				<li><label for="oldPwd">旧密码</label> 
				<input type="password" id="oldPwd"></li>
				<li><label for="newPwd">新密码</label> 
				<input type="password" id="newPwd"></li>
				<li><label for="rePwd">重复密码</label>
				 <input type="password" id="rePwd"></li>
				<li class="btn_w">
				<input type="button" value="确定" id="okBtn" onclick="changePwd()">
				<input type="button" value="取消" id="cancelBtn">
				</li>
			</ul>
		</div>
	</section>
	<script src="${ctx}/js/jquery.js"></script>
	<script src="${ctx}/js/echarts.js"></script>
	<script src="${ctx}/js/common.js"></script>
	<script type="text/javascript">
		$('.user_w').html(ailink.getDom('${ctx}/dom/user.html'));
		$('header').html(ailink.getDom('${ctx}/dom/header2.html'));
		$('.main_l').html(ailink.getDom('${ctx}/dom/bar2.html'));

		$('.main_t').html(ailink.getDom('${ctx}/dom/title2.html'));
		$('.main_t .title').text("账户管理");

		$('#changePswBtn').click(function() {
			$('.alert_w').fadeIn();
		});

		$('#okBtn').click(changePwd);

		$('#cancelBtn').click(function() {
			$('.alert_w').fadeOut();
		});
		
		//提交
		function subm(){
			var url = "${ctx}/user/changAc?loginName=${sessionUser.loginname}&username=" 
					+ encodeURI(encodeURI($("#username").val())) + "&lxdh=" + $("#lxdh").val();
			$.get(url,function(data){

				if(data == 'success'){
					window.location.reload();
				}else{
					alert(data);
				}
			});
		}
		
		//提交
		function changePwd(){

			if ($("#newPwd").val() == "" || $("#oldPwd").val() == "") {
				return;
			}

			var url = "${ctx}/user/change_password?loginName=${sessionUser.loginname}&newPwd=" + $("#newPwd").val() + "&oldPwd=" + $("#oldPwd").val();

			$.get(url,function(date){

				if(date == 'success'){
					window.location.reload();

				} else if(date = 'error'){
					alert('旧密码不对或者用户不存在!');
				}
			});
		}
	</script>
</body>

</html>
