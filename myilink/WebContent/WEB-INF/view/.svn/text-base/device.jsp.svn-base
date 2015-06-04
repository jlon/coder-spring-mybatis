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
				<div class="main-con">
					<table class='as-list'>
						<thead>
							<tr>
								<th>序号</th>
								<th>设备MAC</th>
								<th>所属店铺</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty deviceList }">
									<c:forEach items="${deviceList }" var="device" varStatus="vs">
										<tr>
											<td>${vs.index+1}</td>
											<td>${device.mac}</td>
											<td>${device.address}</td>
											<td>
												<c:choose>
													<c:when test="${equipment.zt eq 0}">
														<span class='ap-sign'>
													</c:when>
													<c:otherwise>
														<span class='ap-sign-bad'>
													</c:otherwise>
												</c:choose>
												AP</span>
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
						<form action="${ctx}/device">
							<div class="page_and_btn">${device.page.pageStr }</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</section>
	<script src="${ctx}/js/jquery.js"></script>
	<script src="${ctx}/js/echarts.js"></script>
	<script src="${ctx}/js/common.js"></script>
	<script>
		$('.user_w').html(ailink.getDom('${ctx}/dom/user.html'));
		$('header').html(ailink.getDom('${ctx}/dom/header2.html'));
		$('.main_l').html(ailink.getDom('${ctx}/dom/bar2.html'));

		$('.main_t').html(ailink.getDom('${ctx}/dom/title2.html'));
		$('.main_t .title').text("设备管理");
	</script>
</body>

</html>
