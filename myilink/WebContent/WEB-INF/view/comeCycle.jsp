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
					<div id="comeCycleChart" style="height: 300px;"></div>
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
		$('.main_t .title').text("到访周期");

		var cmCyEvent = {
			getInfo: function () {
				ailink.ajax("/dwellTime/getVisitPeriodTrend", function (data) {

					if(ailink.getJsonLength(data) == 1 && data.hasOwnProperty(0) && data[0] == 0){
	                    $('#comeCycleChart').text("There is no data!");
	                    return;
	                }
					cmCyEvent.setInfo(data);
				});
			},
			setInfo: function (data) {
				var timeArr = [],
					dataArr = [];

				for (var k in data) {
					timeArr.push(k);
					dataArr.push(parseInt(parseInt(data[k]) / (3600 * 24)));
				}

				var axisLabel = {
                    formatter: function(params) {
                        return parseInt(params);
                    }
                };

				require([ 'echarts', 'echarts/chart/line' ], function(ec) {
					var option = ailink.option.create('平均到访周期趋势', timeArr, '天', axisLabel, dataArr, function(params) {
	                    return parseInt(params.value);
	                });

					ec.init(document.getElementById('comeCycleChart')).setOption(option);
				});
			}

		};

		require.config({
			paths : {
				echarts : 'js'
			}
		});

		var datePicker = new DatePicker(cmCyEvent.getInfo);
		cmCyEvent.getInfo()
	</script>
</body>

</html>