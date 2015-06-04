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
                <div class='main-con'>
                    <div class="employee-add-row">
                        <button type="button" id='addBtn'>添加员工MAC</button>
                    </div>
                    <table class="employee-list">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>MAC地址</th>
                                <th>员工姓名</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty blackLists }">
                                    <c:forEach items="${blackLists }" var="bl" varStatus="vs">
                                        <tr>
                                            <td>${vs.index+1}</td>
                                            <td>${bl.mac}</td>
                                            <td>${bl.name}</td>
                                            <td>${bl.remark}</td>
                                            <td>
                                                <input type="hidden" value="${bl.id}">
                                                <button type="button" class='btn edit_btn'>编辑</button>
                                                <button type="button" class='btn delete_btn'>删除</button>
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
                        <form action="${ctx}/filter">
                            <div class="page_and_btn">${blackList.page.pageStr}</div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </section>
    <section class="alert_w alert_f_w">
        <div class="mask"></div>
        <div class="alert_c add_mac_w">
            <div class="title">添加员工MAC</div>
            <ul>
                <li>
                    <label for="addMac">MAC地址</label>
                    <input type="text" id="addMac">
                </li>
                <li>
                    <label for="addName">员工姓名</label>
                    <input type="text" id="addName">
                </li>
                <li>
                    <label for="addRemark">备注</label>
                    <input type="text" id="addRemark">
                </li>
                <li class="btn_w">
                    <input type="button" value="确定" id="saveBtn">
                    <input type="button" value="取消" class="cancel_btn">
                </li>
            </ul>
        </div>
        <div class="alert_c edit_mac_w">
            <div class="title">编辑信息</div>
            <ul>
                <li>
                    <label for="number">序号</label>
                    <input readonly type="text" id="number">
                </li>
                <li>
                    <label for="edit_mac">MAC地址</label>
                    <input type="text" id="edit_mac">
                </li>
                <li>
                    <label for="edit_name">员工姓名</label>
                    <input type="text" id="edit_name">
                </li>
                <li>
                    <label for="edit_remark">备注</label>
                    <input type="text" id="edit_remark">
                </li>
                <li class="btn_w">
                    <input type="button" value="确定" id="editBtn">
                    <input type="button" value="取消" class="cancel_btn">
                </li>
            </ul>
        </div>
        <div class="alert_c delete_mac_w">
            <div class="title">删除确认</div>
            <div class="btn_w">
                <input type="button" value="确定" class="btn" id="okBtn">
                <input type="button" value="取消" class="btn cancel_btn">
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
    $('.main_t .title').text("过滤名单");

    $('#saveBtn').click(function() {
        var mac = $('#addMac').val(),
            name = $('#addName').val(),
            remark = $('#addRemark').val();

        if (mac == "") {
            return;
        }

        $.ajax({
            url: '/AilinkPre/blackList/insert',
            type: 'POST',
            data: {
                storeId: 0,
                userId: 0,
                mac: mac,
                name: name,
                remark: remark
            }
        }).success(function(data) {

            if (data == 'success') {
                alert('添加成功');
                window.location.reload();
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('#editBtn').click(function() {
        var number = $('#number').val(),
            mac = $('#edit_mac').val(),
            name = $('#edit_name').val(),
            remark = $('#edit_remark').val();

        $.ajax({
            url: GLOBAL_URL + '/blackList/edit',
            type: 'POST',
            data: {
                storeId: 0,
                userId: 0,
                id: number,
                mac: mac,
                name: name,
                remark: remark
            }
        }).done(function(data) {

            if (data == 'success') {
                alert('修改成功');
                window.location.reload();
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('#okBtn').click(function() {
        $.ajax({
            url: GLOBAL_URL + '/blackList/delete',
            type: 'GET',
            data: {
                storeId: 0,
                userId: 0,
                id: deleteNum
            }
        }).done(function(data) {

            if (data == 'success') {
                alert('删除成功');
                window.location.reload();
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('#addBtn').click(function() {
        $('.add_mac_w').show();
        $('.alert_w').fadeIn();
    });

    $('.edit_btn').click(function() {
        $('.edit_mac_w').show();
        $('.alert_w').fadeIn();
    });

    var deleteNum = null;
    $('.delete_btn').click(function() {
        $('.delete_mac_w').show();
        $('.alert_w').fadeIn();

        deleteNum = $(this).prev().prev().val();
    });

    $('.cancel_btn').click(function() {
        $('.alert_w').fadeOut(function() {
            $('.alert_c').hide();
        });
    });
    </script>
</body>

</html>
