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
					<div id="passTimesChart" style="height: 300px;"></div>
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
		$('.main_t .title').text("到访次数");

		require.config({
			paths : {
				echarts : 'js'
			}
		});

		var cmTmEvent = {
			getInfo: function () {
				ailink.ajax("/dwellTime/getCustomerByNum", function (data) {
					cmTmEvent.setInfo(data);
				});
			},
			setInfo: function (data) {
				require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
					ec.init(document.getElementById('passTimesChart')).setOption({
						tooltip : {
							trigger : 'item',
							formatter : function(params) {
								return 	params.name + ': ' + params.value + '人(' + parseInt(params.value / 346 * 10000) / 100 + '%' + ')';
							}
						},
						title: ailink.option.title('到访次数分布'),
						color: ['#b3b3b3'],
						xAxis : [ {
							name: '人数',
							type : 'value',
							splitLine : {
								show : false
							},
			                axisLine: {
			                    lineStyle: {
			                        color: '#000000',
			                        width: 2,
			                        type: 'solid'
			                    },
			                },
			                axisLabel: {
			                	formatter: function (value) {
			                	    return parseInt(value);
			                	}
			                }
						} ],
						grid: {
		                    borderWidth: 'none'
		                },	
						yAxis: {
							name: '来访次数',
							type : 'category',
							data : [ '1次', '2次', '3-5次', '5次以上' ],
			                axisLine: {
			                    lineStyle: {
			                        color: '#000000',
			                        width: 2,
			                        type: 'solid'
			                    },
			                }
						},
						series : [ {
							type : 'bar',
							data : data
						} ]
					});
				});
			}
		};

		var datePicker = new DatePicker(cmTmEvent.getInfo);
		cmTmEvent.getInfo();
	</script>
</body>

</html>