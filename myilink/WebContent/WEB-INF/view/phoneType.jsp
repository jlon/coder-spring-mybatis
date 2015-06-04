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
<link rel="stylesheet" href="${ctx}/css/overview.css">
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
					<div class='main-title'>
						<span>设备详情</span> <i class="glyphicon glyphicon-question-sign"></i>
					</div>
					<div class="main-body">
						<div class="customer-title">超市发</div>
						<div id="customerChart" style="height: 200px;"></div>
						<div class="custromer-detail">
							<table>
								<tr>
									<th class='customer-detail-title'></th>
									<th class='cd-num device-middle'>品牌占比</th>
									<th class='cd-rate'>到访次数</th>
								</tr>
								<tr class='cd-new'>
									<td class='cd-dot'><span class='new-dot'></span> 苹果</td>
									<td class='device-middle'>22.2%</td>
									<td>1次/月</td>
								</tr>
								<tr>
									<td class='cd-dot'><span class='old-dot'></span> 其它</td>
									<td class='device-middle'>77.8%</td>
									<td>1.3次/月</td>
								</tr>
							</table>
						</div>
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
		$('header').html(ailink.getDom('${ctx}/dom/header1.html'));
		$('.main_l').html(ailink.getDom('${ctx}/dom/bar1.html'));

		$('.main_t').html(ailink.getDom('${ctx}/dom/title1.html'));
		$('.main_t .title').text("手机品牌");

		require.config({
			paths : {
				echarts : 'js'
			}
		});

		//在店人数图表
		require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
			ec.init(document.getElementById('customerChart')).setOption({
				tooltip : {
					trigger : 'item'
				},
				color : [ '#e0f6c9', '#7ed321' ],
				series : [ {
					type : 'pie',
					radius : [ '45%' ],
					data : [ {
						value : 3,
						name : '苹果'
					}, {
						value : 6,
						name : '其它'
					} ],
					itemStyle : {
						normal : {
							label : {
								show : false
							},
							labelLine : {
								show : false
							}
						}
					}
				} ]
			});
		});
	</script>
</body>

</html>
