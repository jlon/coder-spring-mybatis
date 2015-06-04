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
					<div class="inside-wrap">
						<div class='inside-number' id='insideNumber'>
							<span></span>
						</div>
						<div id="inNumChart" style="width: 200px; height: 260px;" class='chart'></div>
					</div>
					<div class="inside-run-chart">
						<div id="inRunChart" style="height: 260px;"></div>
					</div>
				</div>
				<div class="main-con">
					<div class="in-rate-wrap">
						<div class='main-title'>入店率</div>
						<div class='inside-rate'></div>
						<div class='main-title'>当前客流</div>
						<div id="passNumber"></div>
					</div>
					<div class="pass-run-chart">
						<div class='main-title'>路过客流走势</div>
						<div id="passRunChart" style="height: 260px;"></div>
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

		$('.main_t').html(ailink.getDom('${ctx}/dom/title2.html'));
		$('.main_t .title').text("实时统计");

		var rT = {
			isHasIn: false,
			isHasOut: false,
			inNum: 0,
			outNum: 0,
			getInData: function () {
				$('.main-con').eq(0).append('<img src="${ctx}/images/load.png" class="load" id="load1">');
				$.get(GLOBAL_URL + '/realTime/in?storeId=0', function(data) {
					rT.isHasIn = true;

					if ($.isEmptyObject(data)) {
						return;
					}

					rT.setInTrend(data);
					var tempData = ailink.getJsonLastValue(data);
					rT.inNum = parseInt(tempData.news) + parseInt(tempData.olds);
					rT.setInInfo(tempData);

					if (rT.isHasOut) {
						rT.setInRate();
					}

					$('#load1').remove();
				});
			},
			getOutData: function () {
				$('.main-con').eq(1).append('<img src="${ctx}/images/load.png" class="load" id="load2">');
				$.get(GLOBAL_URL + '/realTime/out?storeId=0', function(data) {
					rT.isHasOut = true;

					if ($.isEmptyObject(data)) {
						return;
					}

					rT.setOutTrend(data);
					rT.outNum = ailink.getJsonLastValue(data);
					rT.setOutInfo(rT.outNum);

					if (rT.isHasIn) {
						rT.setInRate();
					}
					$('#load2').remove();
				});
			},
			setInInfo: function (data) {
				$('#insideNumber span').text(parseInt(data.news) + parseInt(data.olds));

				require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					ec.init(document.getElementById('inNumChart')).setOption({
						title: ailink.option.title('当前在店'),
						tooltip : ailink.option.tooltip(function (params) {
							return params.name + ':' + params.value + '人';
						}),
						legend : {
							x : 'center',
							y : 40,
							data : [ '新顾客', '老顾客' ]
							// selectedMode : false
						},
						color: ['#888888', '#444444'],
						series : [ {
							clockWise : false,
							type : 'pie',
							radius : [ '50%', '80%' ],
							center : [ '50%', '60%' ],
							data : [ {
								value : data.news,
								name : '老顾客'
							}, {
								value : data.olds,
								name : '新顾客'
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
						}]
					});
				});
			},
			setOutInfo: function (data) {
			    $('#passNumber').text(data);
			},
			setInRate: function () {
			    $('.inside-rate').text(parseInt(rT.inNum * 10000 / (rT.inNum + rT.outNum)) / 100 + '%');
			},
			setInTrend: function (data) {
			    require([ 'echarts', 'echarts/chart/line', 'echarts/chart/bar' ], function(ec) {
					ec.init(document.getElementById('inRunChart')).setOption({
						legend : {
							x : 'right',
							data : [ '新顾客', '老顾客', '顾客总量' ]
						},
						xAxis : {
			                type: 'category',
			                data: ailink.getJsonKey(data),
			                // boundaryGap: false,
			                axisLine: {
			                    lineStyle: {
			                        color: '#000000',
			                        width: 2,
			                        type: 'solid'
			                    },
			                }
			            },
						yAxis : [{
			                name: '人数',
			                type: 'value',
			                splitLine: {
			                    show: false
			                },
			                axisLine: {
			                    lineStyle: {
			                        color: '#000000',
			                        width: 2,
			                        type: 'solid'
			                    }
			                },
			                splitArea: {
			                    show: true,
			                    color: [
			                        'rgba(250,250,250,0.3)',
			                        'rgba(200,200,200,0.3)'
			                    ]
			                }
			            }],
						color: ['#888888', '#444444', '#656565'],
						grid: {
		                    borderWidth: 'none'
		                },
						tooltip: {
			                trigger: 'axis',
			     //            formatter: function () {
								// return params.seriesName + ':' + params.value + '人';
			     //            },
			                showDelay: 0,
			                borderRadius: 0,
			                backgroundColor: 'rgba(0, 0, 0, 0.5)',
			                axisPointer: {
			                    type: 'line',
			                    lineStyle: {
			                        color: '#black',
			                        width: 1,
			                        type: 'solid'
			                    },
			                    shadowStyle: {
			                        color: 'rgba(0, 0, 0, 0)',
			                        width: 'auto',
			                        type: 'default'
			                    }
			                }
			            },
						series : [{
							name : '新顾客',
							type : 'bar',
							data : ailink.getJsonNew(data)
						}, {
							name : '老顾客',
							type : 'bar',
							data : ailink.getJsonOld(data)
						}, {
							name : '顾客总量',
							type : 'line',
							data : ailink.getJsonTotal(data),
							markPoint : {
								data : [{
									type : 'max',
									name : '最多'
								}, {
									type : 'min',
									name : '最少'
								}]
							}
						}]
					});
				});
			},
			setOutTrend: function (data) {
			    require([ 'echarts', 'echarts/chart/line', 'echarts/chart/bar' ], function(ec) {
					ec.init(document.getElementById('passRunChart')).setOption({
						xAxis : {
			                type: 'category',
			                data: ailink.getJsonKey(data),
			                axisLine: {
			                    lineStyle: {
			                        color: '#000000',
			                        width: 2,
			                        type: 'solid'
			                    },
			                }
			            },
						yAxis : [{
			                name: '人数',
			                type: 'value',
			                splitLine: {
			                    show: false
			                },
			                axisLine: {
			                    lineStyle: {
			                        color: '#000000',
			                        width: 2,
			                        type: 'solid'
			                    }
			                },
			                splitArea: {
			                    show: true,
			                    color: [
			                        'rgba(250,250,250,0.3)',
			                        'rgba(200,200,200,0.3)'
			                    ]
			                }
			            }],
						color: ['#656565'],
						tooltip : ailink.option.tooltip(function (params) {
							return params.seriesName + ':' + params.value + '人';
						}),
						series : [{
							name : '路过客流',
							type : 'line',
							data : ailink.getJsonValue(data),
							markPoint : {
								data : [ {
									type : 'max',
									name : '最多'
								}, {
									type : 'min',
									name : '最少'
								} ]
							}
						}]
					});
				});
			}
		};

		require.config({
			paths : {
				echarts : 'js'
			}
		});

	    rT.getInData();
	    rT.getOutData();
	</script>
</body>

</html>
