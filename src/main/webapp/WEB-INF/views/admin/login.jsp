<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login-manage.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body>
<div class="loginmanage">
    <div class="py-container">
        <h4 class="manage-title">云借阅-图书管理系统</h4>
        <div class="loginform">
            <ul class="sui-nav nav-tabs tab-wraped">
                <li class="active">
                    <h3>账户登录</h3>
                </li>
            </ul>
            <div class="tab-content tab-wraped">
                <%--登录提示信息--%>
                <c:if test="${not empty msg}">
                    <span style="color: red">${msg}</span>
                </c:if>
                <c:if test="${not empty message}">
                    <span style="color: red; font-weight: bold;">${message}</span>
                </c:if>
                <div id="profile" class="tab-pane  active">
                    <form id="loginform" class="sui-form" action="${pageContext.request.contextPath}/user/login"
                          method="post">
                        <div class="input-prepend"><span class="add-on loginname">用户名</span>
                            <input type="text" placeholder="企业邮箱" class="span2 input-xfat" name="email">
                        </div>
                        <div class="input-prepend"><span class="add-on loginpwd">密码</span>
                            <input type="password" placeholder="请输入密码" class="span2 input-xfat" name="password">
                        </div>
                        <div class="logined">
                            <a class="sui-btn btn-block btn-xlarge btn-danger"
                               href='javascript:document:loginform.submit();' target="_self">登&nbsp;&nbsp;录</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- 添加学号和姓名 -->
        <div style="text-align: center; margin-top: 100px; color: #7adddd;font-size: 20px">
            学号：2023463030327 姓名：魏潇雅
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    /**
     * 登录超时 展示区跳出iframe
     */
    var _topWin = window;
    while (_topWin != _topWin.parent.window) {
        _topWin = _topWin.parent.window;
    }
    if (window != _topWin)
        _topWin.document.location.href = '${pageContext.request.contextPath}/admin/login';
</script>
</html>