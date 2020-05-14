<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%></></>


<jsp:include page="top.jsp"></jsp:include>


<div class="met-banner" data-height='' style='height: 750px'>
    <c:forEach items="${xlist }" var="n">
        <div class="slick-slide">
            <img class="cover-image" src="./upload/${n.img }" height="750px" sizes="(max-width: 767px) 767px "
                 style='height: 750px' alt="${n.name }">
        </div>
    </c:forEach>
</div>





<%--2--%>
<div class="met-index-hot met-index-body" id="hot">
    <div class="title">
        <h2 class="">
            寻亲大全
        </h2>
        <div class="home-line" style="background:url(images/1514365123.png)center no-repeat;">
        </div>
        <p class="desc">
            <!-- 我们用心为您做的更好 -->
        </p>
    </div>
    <div class="container">
        <ul class="blocks-100 blocks-sm-2 blocks-md-4 blocks-xlg-4 " data-scale=''>
            <!--string(7) "product" -->
            <c:forEach items="${glist }" var="g">
                <li class="">
                    <div class="widget widget-article widget-shadow">
                        <div class="widget-header cover overlay overlay-hover">
                            <img class="cover-image overlay-scale" src="./upload/${g.img }"
                                 alt="" width="263px" height="197px;">
                        </div>
                        <div class="widget-body">
                            <h3 class="widget-title">
                                    ${g.name }
                            </h3>
                            <p class="des">
                               分类：<c:forEach items="${tlist }" var="t">
                               <c:if test="${t.id==g.fid }">${t.name }</c:if>
                               </c:forEach> 
                            </p>
                            <div class="widget-body-footer">
                                <a class="btn btn-info waves-effect waves-light" href="showGoodsx.do?id=${g.id }">
                                    查看详情
                                </a>
                                <div class="widget-actions pull-right">
                                    <i class="fa fa-eye">
                                    </i>
                                        ${g.hit}
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <div class="hot-btns">
            <a href="goodsListFore.do" ​>
                查看更多
            </a>
        </div>
    </div>
</div>

<%--3--%>
<div class="met-index-product met-index-body" id='product'>
    <div class="title">
        <h2 class="">
            新闻
        </h2>
        <div class="home-line" style="background:url(images/1514365123.png)center no-repeat;">
        </div>
        <p class="desc ">
        </p>
    </div>
    <div class="container">
        <div class=''>
            <%-- <ul class="nav nav-tabs">
                <c:forEach items="${xlist }" var="k">
                    <li>
                        <a href="searchNews_sp.do?fid=${k.id }" title="${k.name }">
                            <h3> ${k.name }</h3>
                        </a>
                    </li>
                </c:forEach>
            </ul> --%>
        </div>
        <ul class="blocks-2 blocks-sm-2 blocks-md-4 blocks-xlg-4 no-space" id="indexprolist"
            data-scale='0.75117370892019'>
            <!--string(7) "product" -->
            <c:forEach items="${xlist }" var="k">
                <li>
                    <div class="widget">
                        <figure class="widget-header cover">
                            <a href="showNewx.do?id=${k.id }" title="${k.name }" target='_self'>
                                <img class="cover-image" src="./upload/${k.img }"
                                     style='height:300px;' alt="${k.name }">
                            </a>
                        </figure>
                        <h4 class="widget-title">
                            <a href="showNewx.do?id=${k.id }" title="${k.name }" target='_self'>
                                    ${k.name }
                                <span>
                                        <i class="fa fa-eye">
                                        </i>
                                        ${k.hit}人浏览
                                    </span>
                            </a>
                        </h4>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<%--3--%>
<!--string(4) "news" string(4) "news" -->


<%--4--%>
<div class="met-index-about met-index-body" id="about">
    <div class="title">
        <h2 class="">
            指南
        </h2>
        <div class="home-line" style="background:url(images/1514365123.png)center no-repeat;">
        </div>
        <p class="desc">
        </p>
    </div>
    <div class="container">
        <div class="about-list">

            <c:forEach items="${splist }" var="k" varStatus="num">
                <div>
                    <div class="col-md-6 img">
                        <img class="cover-image" src="./upload/${k.img }"
                             style='height:300px;' alt="${k.name }">
                       <%-- <img src="picture/1-1p1031q0321a.jpg" alt="">--%>
                    </div>

                    <div class="col-md-6 text">
                        <h3>
                                ${k.name}
                            <span>
                                    / ${num.count}
                                </span>
                        </h3>
                        <p>
                                ${k.note}
                        </p>
                        <a href="newsListFore.do" title="${k.name}" target='_self'
                           class="btn btn-info waves-effect waves-light">
                            查看更多
                        </a>
                    </div>
                </div>
            </c:forEach>


        </div>
    </div>
</div>

<%--
<div class="met-index-product met-index-body" id='product2'>
    <div class="title">
        <h2 class="">
            论坛
        </h2>
        <div class="home-line" style="background:url(images/1514365123.png)center no-repeat;">
        </div>
        <p class="desc ">
        </p>
    </div>
    <div class="container">
        <div class=''>
            <ul class="nav nav-tabs">
                <c:forEach items="${klist2 }" var="k">
                    <li>
                        <a href="searchNews.do?fid=${k.id }" title="${k.name }">
                            <h3> ${k.name }</h3>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <ul class="blocks-2 blocks-sm-2 blocks-md-4 blocks-xlg-4 no-space" id="indexprolist2"
            data-scale='0.75117370892019'>
            <!--string(7) "product" -->
            <c:forEach items="${slist }" var="k">
                <li>
                    <div class="widget">
                        <figure class="widget-header cover">
                            <a href="showNewx.do?id=${k.id }" title="${k.name }" target='_self'>
                                <img class="cover-image" src="./upload/${k.img }"
                                     style='height:300px;' alt="${k.name }">
                            </a>
                        </figure>
                        <h4 class="widget-title">
                            <a href="showNewx.do?id=${k.id }" title="${k.name }" target='_self'>
                                    ${k.name }
                                <span>
                                        <i class="fa fa-eye">
                                        </i>
                                        ${k.hit}人浏览
                                    </span>
                            </a>
                        </h4>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>--%>


<jsp:include page="foot.jsp"></jsp:include>
			
	