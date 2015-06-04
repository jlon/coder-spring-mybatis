<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<link rel="stylesheet" href="${ctx}/css/bootstrap.css">
<link rel="stylesheet" href="${ctx}/css/common.css">
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/common.js"></script>
<style>
body {
	height: 100%;
	background: url(${ctx}/images/bg.jpg) no-repeat center center;
	background-size: 100% 100%;
}

.login-wrap {
	width: 300px;
	margin: 80px auto 0;
	-webkit-animation: showLoginWrap 1s ease-in-out;
}

@
-webkit-keyframes showLoginWrap { 0% {
	transform: translateY(-300px);
	opacity: 0;
}

60%
{
transform










:





 





translateY










(50
px








);
opacity










:





 





0
.8










;
}
100%
{
transform










:





 





translateY










(0);
opacity










:





 





1;
}
}
.login-wrap h2 {
	font-family: '楷体';
	text-align: center;
	-webkit-animation: h2Anima 2s ease-in-out;
}

@
-webkit-keyframes h2Anima { 0% {
	transform: translateX(-40px);
}

25%
{
transform










:





 





translateX










(40
px








);
}
50%
{
transform










:





 





translateX










(-40
px








);
}
80%
{
transform










:





 





translateX










(10
px








);
}
100%
{
transform










:





 





translateX










(0);
}
}
.input-group {
	margin: 20px auto 0;
}

.input-info {
	background: #d9edf7;
	border: 1px solid #bce8f1;
}

.form-control {
	border: 1px solid #bce8f1;
}

.alert {
	display: none;
	margin: 0 5px;
	padding: 5px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}

.rem-username {
	font-size: 13px;
	color: #31708f;
}

.btn-wrap {
	margin-top: 20px;
}

.btn-wrap button {
	width: 100%;
	text-align: center;
}

.panel-footer {
	overflow: hidden;
	background: #d9edf7;
}

.forget-psw {
	font-size: 12px;
	color: #31708f;
}
</style>
<script type="text/javascript">
	//改变密码
	function changePassword() {
		var loginname = $("#loginname").val();
		var password = $("#password").val();
		var url = "${ctx}/user/change_password?loginname=" + loginname
				+ "&password=" + password;
		$.get(url, function(data) {
			if (data == 'success') {
				$("#changeDiv").hide();
				$("#showDiv").show();
				clock();
			}
		});
	}
	var time =4;
	function clock() {
		time--;
		document.getElementById("info").innerHTML = "修改成功，窗口将在" + time
				+ "秒后自动跳转";
		if (time == 0) {
			window.location.href = "${ctx}/login";
		}
	}
	setInterval("clock();", 1000);
</script>
</head>

<body>
	<div id="changeDiv">
		<input type="hidden" id="loginname" name="loginname"
			value="${loginName}" /> 新密码： <input type="text" name="password"
			id="password"> 确认密码： <input type="text" id="surPwd">
		<button type="button" id="cpwdBtn" onclick="changePassword();">登录</button>
	</div>
	
	<div style="display: none" id="showDiv">
		<table width="50%" align="center" cellspacing="0" class="faTable">
			<tr>
				<td align="center" class="faTdleft" id="info">
					发送成功！本窗口将在3秒后自动跳转</td> 如果没跳转情点击下面的连接：
				<a href="${ctx}/login">登陆</a>
			</tr>
		</table>
	</div>

	<div class="container footer">我联科技</div>
</body>

</html>
