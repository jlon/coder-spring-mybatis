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
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <link rel="stylesheet" href="${ctx}/css/login.css">
</head>

<body>
    <div class="login_w">
    	<div class="logo_w">
    		<img src="${ctx}/images/login_l.png" class="logo">
    	</div>
    	<img src="${ctx}/images/login_w.png" class="word">
    	<form class="login_c" action="login" id="loginForm" method="post">
    		<input type="text" placeholder="邮箱" name="loginname" class="email" id="loginname">
	    	<input type="password" placeholder="密码" name="password" class="password" id="password">
	    	<div class="btn_w">
                <input type="submit" class="login_btn" id="loginBtn" value="登录">
	    		<a href="javascript:" class="forget_psw" onclick="forgetPassword()">忘记密码？</a>
	    	</div>
    	</form>
    </div>

    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/common.js"></script>
    <script>
    sessionStorage.clear();

    $(function() {
        $('#loginBtn').click(function() {
            var username = $('#loginname').val(),
                password = $('#password').val();

            if (password == "" || username == "") {
            	return;
            }

            $('#loginForm').submit();
        });
    });

    //忘记密码
    function forgetPassword() {
        var loginname = $('#loginname').val();
        if (loginname == '') {
            return;
        }

        var url = "${ctx}/user/forget_password?loginName=" + loginname;

        $.get(url, function(data) {
            alert(data.msg);
        });
    }
    </script>
</body>

</html>
