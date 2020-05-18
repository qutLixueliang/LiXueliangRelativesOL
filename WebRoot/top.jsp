<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>




<!DOCTYPE HTML>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>在线寻亲系统</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
		<meta name="generator" content="" data-variable="/,cn,10001,,10001,res032"/>
        <link rel='stylesheet' href='css/index.css'>
    </head>
    
    <body class="met-navfixed">
        <!--[if lte IE 8]>
            <div class="text-center padding-top-50 padding-bottom-50 bg-blue-grey-100">
                <p class="browserupgrade font-size-18">
                    你正在使用一个
                    <strong>
                        过时
                    </strong>
                    的浏览器。请
                    <a href="#"​ target="_blank">
                        升级您的浏览器
                    </a>
                    ，以提高您的体验。
                </p>
            </div>
        <![endif]-->
        <header id="header" class="header-fixed">
            <div class="container">
                <ul class="head-list">
                    <li class="left tel">
                        <img src="../admin/images/logo1.png" alt="">
                    </li>
                    <li class="right">
                        <ul>
							<c:if test="${sessionScope.user==null }">
                            <li>
                                <img src="picture/1514355047.png" alt="登录">
                                <span>
                                    <a href="login.do">登录</a>
                                </span>
                            </li>
                            <li>
                                <img src="picture/1514354958.png" alt="注册">
                                <span>
                                    <a href="regist.jsp" >注册</a>
                                </span>
                            </li>
                            </c:if>
                            <c:if test="${sessionScope.user!=null }">
                                <li>
                                    <img src="picture/1514355047.png" alt="个人信息管理">
                                    <span>
                                    <a href="showInfo.do">${sessionScope.user.uname } </a>
                                </span>
                                </li>
                                <li>
                                    <img src="picture/1514354958.png" alt="个人信息管理">
                                    <span>
                                    <a href="showInfo.do" >个人信息管理</a>
                                </span>
                                </li>
                            </c:if>

                            <li>
                                <img src="picture/1514354958.png" alt="后台">
                                <span>
                                    <a  href="admin/login.jsp">后台</a>
                                </span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </header>
        
        
        <nav class="navbar navbar-default met-nav navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="row">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle hamburger hamburger-close collapsed"
                        data-target="#navbar-default-collapse" data-toggle="collapse">
                            <span class="sr-only">
                                Toggle navigation
                            </span>
                            <span class="hamburger-bar">
                            </span>
                        </button>
                        <a href="#"​ class="navbar-brand navbar-logo vertical-align" title="(自适应手机端)">
                            <h1 class='hide'>
                                (自适应手机端)
                            </h1>
                            <div class="vertical-align-middle">
                                <img src="./admin/images/logo01.png" alt="(自适应手机端)" title="(自适应手机端)"
                                />
                            </div>
                        </a>
                        <h2 class='hide'>
                        </h2>
                    </div>
                    <div class="collapse navbar-collapse navbar-collapse-toolbar" id="navbar-default-collapse">
                        <ul class="nav navbar-nav navbar-right navlist">
                            <li>
                                <a href="index.do"​ title="网站首页" class="link  active">首页</a>
                            </li>
                            <li class="dropdown margin-left-20">
                                <a class=" link " href="newsListFore.do">寻亲新闻</a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                                </ul>
                            </li>
                            <li class="dropdown margin-left-20">
                                <a class=" link " href="newsListFore_zl.do">志愿者指南</a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                                </ul>
                            </li>

                            <li class="dropdown margin-left-20">
                                <a class="dropdown-toggle link " data-toggle="dropdown" data-hover="dropdown" aria-expanded="false" href="goodsListFore.do">
                                    寻亲大全 <span class="caret"> </span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                               		<c:forEach items="${tlist }" var="t">
                                   	 <li><a href="searchGoods.do?fid=${t.id }"​ class="" title="${t.name }">${t.name }</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                            <%--<li class="dropdown margin-left-20">
                                <a class=" link " href="goodsListFore.do">寻亲大全 </a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                                </ul>
                            </li>--%>
                            <li class="dropdown margin-left-20">
                                <a class="dropdown-togg link " data-toggle="dropdown" data-hover="dropdown" aria-expanded="false" href="newsListFore_lt.do">
                                    论坛<span class="caret"> </span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                                    <c:forEach items="${klist }" var="t">
                                        <li><a href="searchNews_lt.do?fid=${t.id }"​ class="" title="${t.name }">${t.name }</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                             <li class="dropdown margin-left-20">
                                <a class=" link " href="newsListFore_lt.do">寻亲论坛 </a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                                </ul>
                            </li>
                            <li class="dropdown margin-left-20">
                                <a class=" link " href="myBbsList.do">留言</a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                                </ul>
                            </li>

                          <c:if test="${sessionScope.user!=null }">
                            <li class="dropdown margin-left-20">
                                <a class="dropdown-toggle link " data-toggle="dropdown" data-hover="dropdown" aria-expanded="false" href="showInfo.do">
                                    ${sessionScope.user.uname }
                                    <span class="caret"> </span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right bullet">
                                    <li><a href="showInfo.do"​ class="" title="个人信息管理">个人信息管理 </a></li>
                                    <c:if test="${sessionScope.user.utype=='用户'}">
                                        <li><a href="myGoodsList.do"​ class="" title="我的寻亲">我的寻亲</a></li>
                                    </c:if>
                                    <c:if test="${sessionScope.user.utype=='志愿者'}">
                                        <li><a href="myGoodsList_zy.do"​ class="" title="我的任务">我的任务</a></li>
                                    </c:if>

                                    <li><a href="myBbsList.do"​ class="" title="我的留言">我的留言</a></li>
                                    <li><a href="newsList_lt.do"​ class="" title="我的论坛">我的论坛</a></li>
                                    <li><a href="myBbsList_pl.do"​ class="" title="我的评论">我的评论</a></li>
                                    <li><a href="loginout.do"​ class="" title="注销" >注销</a></li>
                                </ul>
                            </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>
        
 
