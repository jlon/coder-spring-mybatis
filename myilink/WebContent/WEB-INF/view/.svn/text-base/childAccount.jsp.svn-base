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
				<div class="main-con account-other-wrap">
					<div class="other-account-header">
						<div class="oa-header-left"></div>
						<div class="all-account typ-select" id=''>
							<span>所有帐号</span> <span id="allNumber">(0)</span>
						</div>
						<div class="stop-account typ-select no-active">
							<span>已停用帐号</span> <span id="stopNumber">(0)</span>
						</div>
						<div class="oa-header-right"></div>
					</div>
					<div class="operate-row">
						<button type="button" id='addAccountBtn'>添加新账号</button>
					</div>
					<table class="account-list">
						<thead>
							<tr class='acc-list-header'>
								<th></th>
								<th>序号</th>
								<th>登录邮箱</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="checkbox"></td>
								<td>1</td>
								<td>atxkm@163.com</td>
								<td>启用</td>
								<td>
									<button type="button" class="btn delete">删除</button>
									<button type="button" class="btn stop">停用</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</section>
	</section>
	<section class="alert_w alert_a_w">
		<div class="mask"></div>
		<div class="alert_c add_acc_w">
			<div class="title">添加新账号</div>
			<ul>
				<li><label for="username">登录邮箱</label> <input type="text" id="username"></li>
				<li><label for="password">密码</label> <input type="text" id="password"></li>
				<li class="btn_w"><input type="button" value="确定" class="btn"><input type="button" value="取消" class="cancel_btn"></li>
			</ul>
		</div>
		<div class="alert_c stop_acc_w">
			<div class="title">停用确认</div>
			<div class="btn_w">
				<input type="button" value="确定" class="btn"> <input type="button" value="取消" class="btn cancel_btn">
			</div>
		</div>
		<div class="alert_c delete_acc_w">
			<div class="title">删除确认</div>
			<div class="btn_w">
				<input type="button" value="确定" class="btn"> <input type="button" value="取消" class="btn cancel_btn">
			</div>
		</div>
	</section>
	<script src="${ctx}/js/jquery.js"></script>
	<script src="${ctx}/js/echarts.js"></script>
	<script src="${ctx}/js/common.js"></script>
	<script>
		$('.user_w').html(ailink.getDom('${ctx}/dom/user.html'));
		$('header').html(ailink.getDom('${ctx}/dom/header2.html'));
		$('.main_l').html(ailink.getDom('${ctx}/dom/bar2.html'));

		$('.main_t').html(ailink.getDom('${ctx}/dom/title2.html'));
		$('.main_t .title').text("子帐号管理");

		$('#addAccountBtn').click(function() {
			$('.add_acc_w').show();
			$('.alert_w').fadeIn();
		});

		$('.edit_btn').click(function() {
			$('.edit_acc_w').show();
			$('.alert_w').fadeIn();
		});

		$('.delete').click(function() {
			$('.delete_acc_w').show();
			$('.alert_w').fadeIn();
		});

		$('.stop').click(function() {
			$('.stop_acc_w').show();
			$('.alert_w').fadeIn();
		});

		$('.cancel_btn').click(function() {
			$('.alert_w').fadeOut(function() {
				$('.alert_c').hide();
			});
		});

		$('.typ-select').click(function() {
			$('.typ-select').addClass('no-active');
			$(this).removeClass('no-active');
		});
	</script>
</body>

</html>
