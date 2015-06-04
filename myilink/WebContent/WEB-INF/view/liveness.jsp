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
                    <div id="clientChart" style="height: 240px;"></div>
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

    $('.main_t').html(ailink.getDom('${ctx}/dom/title3.html'));
    $('.main_t .title').text("活跃度");

    var ln = {
        getLastMonInfo: function() {
            $.get(GLOBAL_URL + '/active?storeId=0', function(data) {
                ln.setInfo(data);
            });
        },
        getMonthInfo: function(m) {
            $.get(GLOBAL_URL + '/active?storeId=0&year=2015&month=' + m, function(data) {
                ln.setInfo(data);
            });
        },
        getQuarterInfo: function(q) {
            $.get(GLOBAL_URL + '/active?storeId=0&year=2015&quarter=' + q, function(data) {
                ln.setInfo(data);
            });
        },
        setInfo: function(data) {
        	var hasData = false;

        	for (var k in data) {
        		if (data[k] != 0) {
        			hasData = true;
        			break;
        		}
        	}

        	if (!hasData) {
	            $('#clientChart').text('There is no data!');
	            return;
        	}

            require(['echarts', 'echarts/chart/pie'], function(ec) {
                ec.init(document.getElementById('clientChart')).setOption({
                    title: ailink.option.title('顾客活跃度信息'),
                    legend: {
                        x: 'right',
                        data: ['高活跃度', '中活跃度', '低活跃度', '沉睡顾客'],
                        selectedMode: false
                    },
                    color: ['#000000', '#656565', '#555555', '#e9e9e9'],
                    tooltip: ailink.option.tooltip(function(params) {
                        return params.name + ':' + params.value + '人';
                    }),
                    series: [{
                        type: 'pie',
                        radius: ['45%'],
                        data: [{
                            value: data.high,
                            name: '高活跃度'
                        }, {
                            value: data.middle,
                            name: '中活跃度'
                        }, {
                            value: data.low,
                            name: '低活跃度'
                        }, {
                            value: typeof data['deep'] == 'undefined' ? 0 : data.deep,
                            name: '沉睡顾客'
                        }],
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true
                                },
                                labelLine: {
                                    show: true
                                }
                            }
                        }
                    }],
                });
            });
        }
    };

    require.config({
        paths: {
            echarts: 'js'
        }
    });

    ln.getLastMonInfo();
    </script>
</body>

</html>
