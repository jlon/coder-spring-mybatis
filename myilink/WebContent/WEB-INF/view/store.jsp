<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
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
				<div class="main-con all-store-wrap">
					<table class='as-list'>
						<thead>
							<tr>
								<th>序号</th>
								<th>店铺名称</th>
								<th>店铺地址</th>
								<th>设备数量</th>
								<th style='width: 270px;'>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty shopList }">
									<c:forEach items="${shopList }" var="shop" varStatus="vs">
										<tr>
											<td>${vs.index+1}</td>
											<td>${shop.dpmc}</td>
											<td>${shop.address}</td>
											<td>${shop.deviceCount}</td>
											<td>
											<input type="hidden" value="${shop.id}"/>
											<button type='button' class="store_edit_btn">店铺编辑</button>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="4">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<div class="page_w">
						<form action="${ctx}/shop">
							<div class="page_and_btn">${shop.page.pageStr}</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</section>
	<section class="alert_w">
		<div class="mask"></div>
		<div class="alert_c edit_store_w">
			<div class="title">编辑店铺信息</div>
			<ul>
				<li><label for="oldPwd">名称</label> <input type="text"
					id="sotreName"></li>
				<li><label for="newPwd">详细地址地址</label> <input type="text"
					id="storeAddress"></li>
				<li class="btn_w"><input type="button" value="确定" id="okBtn"><input
					type="button" value="取消" id="cancelBtn"></li>
			</ul>
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
		$('.main_t .title').text("店铺管理");

		$('.store_edit_btn').click(function() {
			$('.alert_w').fadeIn();
		});

		$('#cancelBtn').click(function() {
			$('.alert_w').fadeOut();
		});

		$('#okBtn').click(function() {

			$.ajax({
				url : GLOBAL_URL + '/updateShop',
				type : 'GET',
				dataType : 'json',
				data : {
					id : $('#shopId').val(),
					dpmc : $('#storeName').val(),
					address : $('#storeAddress').val()
				}
			}).success(function(data) {

				if (data == 'success') {
					window.location.reload();
				}
			}).fail(function(e) {
				console.log(e);
			});
		});
	</script>
</body>

</html>
