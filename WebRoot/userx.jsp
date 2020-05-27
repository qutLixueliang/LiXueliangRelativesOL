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
                            <a href='/zaixianxunqin/userListFore.do/'>
                                老师
                            </a>
                            >
                            ${user.tname}
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
                                    ${user.tname }
                                </h1>
                                <div class="info">
                                    <span>
                                        ${user.pubtime }
                                    </span>
                                    <span>
                                    </span>
                                    <span>
                                        <i class="icon wb-eye margin-right-5" aria-hidden="true">
                                        </i>
                                    </span>
                                </div>
                            </div>
                            <div class="met-editor lazyload clearfix">
                                <div>
                                    <p>
                                        性别：${user.sex }
                                    </p>
                                    <hr/>
                                    <p>
                                    介绍：${user.note }
                                    </p>
                                   <hr/>
                                    <p>
                                        <a href="searchGoods.do?uid=${user.id}" class="btn btn-primary ">查看他发布的寻亲登记</a>
                                        <a href="searchNews_sp.do?uid=${user.id}" class="btn btn-primary ">查看他发布的论坛</a>
                                    </p>
                                    <hr/>




                                    <c:if test="${sessionScope.user!=null }">
                                    <div id="metinfo_additional">
                                    <form action="addBbs.do" method="post">
                                        <div class="form-group">
                                        <div>
                                            <textarea name='note' class='form-control' placeholder='留言内容 ' rows='5' required="required">
                                            </textarea>
                                        </div>
                                        <input value="老师"  name="gtype" type="hidden">
                                        <input value="${user.id }"  name="hid" type="hidden">
                                            <input value="留言"  name="btype" type="hidden">
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
                                <form name="formsearch" action="searchUser.do" method="post">
                                    <input type="hidden" name="kwtype" value="0" />
                                    <div class="form-group">
                                        <div class="input-search">
                                            <button type="submit" class="input-search-btn">
                                                <i class="icon wb-search" aria-hidden="true">
                                                </i>
                                            </button>
                                            <input type="text" class="form-control" name="tname" placeholder="输入老师名称">
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
