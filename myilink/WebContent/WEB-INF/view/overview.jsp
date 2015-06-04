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
					<div class='main-title'>店铺客流</div>
					<div class='target-wrap'>
						<div class="target-block">
							<div class="target-title">
								<span>客流量</span>
							</div>
							<div class="target-con target-con-first">
								<div class="target-detail">
									<span class='target-number' id="passNum"></span> <span>人</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>入店量</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id="getInTimes"></span> <span>人次</span>
								</div>
								<div class="target-supplement">
									<span>入店人数</span>
									<span id="getInNum"></span>
									<span>人</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>入店率</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id="getInRate"></span>
									<span>%</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="main-con">
					<div class='main-title'>客流质量</div>
					<div class='target-wrap'>
						<div class="target-block">
							<div class="target-title">
								<span>平均来访周期</span>
							</div>
							<div class="target-con target-con-first" style="height: 42px;">
								<div class="target-detail">
									<span class='target-number' id="comeCycle"></span> <span>天</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>新顾客</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id="newClient"></span> <span>人</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>老顾客</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id="oldClient"></span> <span>人</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="main-con">
					<div class='main-title'>店铺转化能力</div>
					<div class='target-wrap'>
						<div class="target-block">
							<div class="target-title">
								<span>平均驻店时长</span>
							</div>
							<div class="target-con target-con-first">
								<div class="target-detail">
									<span class='target-number' id="dwelltime"></span> <span>分钟</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>深访顾客</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id="deepTimes"></span> <span>人次</span>
								</div>
								<div class="target-supplement" id="deepNumber">
									<span>深访人数</span>
									<span id="deepNum"></span>
									<span>人</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>深访率</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id='deepRate'></span>
									<span>%</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>跳出顾客</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id="jumpTimes"></span> <span>人次</span>
								</div>
								<div class="target-supplement">
									<span>跳出人数</span>
									<span id="jumpNum"></span>
									<span>人</span>
								</div>
							</div>
						</div>
						<div class="target-block">
							<div class="target-title">
								<span>跳出率</span>
							</div>
							<div class="target-con">
								<div class="target-detail">
									<span class='target-number' id="jumpRate"></span>
									<span>%</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>
	<script src="${ctx}/js/jquery.js"></script>
	<script src="${ctx}/js/common.js"></script>
	<script>
		$('.user_w').html(ailink.getDom('${ctx}/dom/user.html'));
		$('header').html(ailink.getDom('${ctx}/dom/header1.html'));
		$('.main_l').html(ailink.getDom('${ctx}/dom/bar1.html'));

		$('.main_t').html(ailink.getDom('${ctx}/dom/title1.html'));
		$('.main_t .title').text("店铺概览");

		var overviewEvent = {
			getInfo: function () {
				$('.main_c').append('<img src="${ctx}/images/load.png" class="load">');
				ailink.ajax('/view', function (data) {
					$('.load').remove();
					overviewEvent.setInfo(data);
				});
			},
			setInfo: function (data) {
				$('#passNum').text(data.allNum);
				$('#getInTimes').text(data.getinTimes);
				$('#getInNum').text(data.getinNum);

				if (data.allNum != 0) {
					$('#getInRate').text(parseInt(data.getinNum * 100 / data.allNum));
				} else {
					$('#getInRate').text(0);
				}
				$('#comeCycle').text(Math.ceil(data.clientCycle / (3600 * 24)));
				$('#newClient').text(data.newClient);
				$('#oldClient').text(data.oldClient);

				$('#dwelltime').text(parseInt(data.dwellTime / 60 * 10) / 10);
				$('#deepTimes').text(data.deepTimes);
				$('#deepNum').text(data.deepNum);
				$('#deepRate').text(data.deepRate);
				$('#jumpTimes').text(data.jumpTimes);
				$('#jumpNum').text(data.jumpNum);
				$('#jumpRate').text(data.jumpRate);
			}
		};

		var datePicker = new DatePicker(overviewEvent.getInfo);
		overviewEvent.getInfo();
	</script>
</body>

</html>
