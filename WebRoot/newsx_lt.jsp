<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


			<jsp:include page="top.jsp"></jsp:include>

<div class="met-position  pattern-show">
            <div class="container">
                <div class="row">
                    <ol class="breadcrumb">
                        <li>
                            <a href='/zaixianxunqin/'>
                                主页
                            </a>
                            >
                            <a href='/zaixianxunqin/newsListFore_lt.do/'>
                                论坛
                            </a>
                            >
                            ${news.name}
                            >
                        </li>
                    </ol>
                </div>
            </div>
        </div>
		<section class="met-shownews animsition">
            <div class="container">
                <div class="row">
                    <div class="col-md-9 met-shownews-body">
                        <div class="row">
                            <div class="met-shownews-header">
                                <h1>
                                    ${news.name }
                                </h1>
                                <div class="info">
                                    <span>
                                        ${news.pubtime }
                                    </span>
                                   
                                    <span>
                                       点击数： ${news.hit }
                                    </span>
                                    <span>
                                        <i class="icon wb-eye margin-right-5" aria-hidden="true">
                                        </i>

                                       <hr>
                                    </span>
                                    <p>板块：
                                 	<c:forEach items="${klist }" var="t">
                                 	<c:if test="${t.id==news.fid }">
                                 	${t.name }
                                 	</c:if>
                                 	</c:forEach> </p>
                                   <%--  <video height="600px" width="800px;" src="upload/${news.upload}" controls="controls">
                                        你的浏览器不支持video
                                    </video> --%>
                                </div>
                            </div>
                            <div class="met-editor lazyload clearfix">
                                <div>


                                    <p>
                                    ${news.note }
                                    </p>
                                   <hr/>
                                     <c:forEach items="${list }" var="b">
                                   <p>
                                   <c:forEach items="${ulist }"  var="u">
                                   <c:if test="${u.id==b.uid }">
                                   <img alt="" src="./upload/${u.img }" width="50px;" height="50px;">
                                   发布人：${u.tname } ,发布时间：${b.pubtime }
                                   <hr>
                                   </c:if>
                                   </c:forEach>
                                   发布内容： ${b.note }
                                   </p>
                                   <hr/>
                                   </c:forEach>
                                   
                                   
                                    
                                      <c:if test="${sessionScope.user!=null }">
                                    <div id="metinfo_additional">
                                    <form action="addBbs_pl.do" method="post">
                                        <div class="form-group">
                                        <div>
                                            <textarea name='note' class='form-control' placeholder='留言内容 ' rows='5' required="required">
                                            </textarea>
                                        </div>
                                        <input value="${news.id }"  name="gid" type="hidden">
                                        <input value="${news.gtype }"  name="gtype" type="hidden">
                                            <input value="评论"  name="btype" type="hidden">
                                    </div>
                                    <input type="submit" value="提交" class="btn btn-primary btn-block btn-squared">
                                    </form>
                                    </div>
                                     </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="row">
                            <div class="met-news-bar">
                                <form name="formsearch" action="searchNews_lt.do" method="post">
                                    <input type="hidden" name="kwtype" value="0" />
                                    <div class="form-group">
                                        <div class="input-search">
                                            <button type="submit" class="input-search-btn">
                                                <i class="icon wb-search" aria-hidden="true">
                                                </i>
                                            </button>
                                            <input type="text" class="form-control" name="name" placeholder="输入论坛名称">
                                        </div>
                                    </div>
                                </form>
                              
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
        
					<jsp:include page="foot.jsp"></jsp:include>
