<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
function down1(fujianPath)
{
   var url="updown.jsp?fujianPath="+fujianPath;
   url=encodeURI(url); 
   url=encodeURI(url); 
   window.open(url,"_self");
}
</script>

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
                            <a href='/zaixianxunqin/goodsListFore.do/'>
                                寻亲登记
                            </a>
                            >
                            <c:forEach items="${tlist }" var="k" >
                            <c:if test="${goods.fid==k.id }">
                            <a href='/zaixianxunqin/searchGoods.do?fid=${goods.fid }'>
                                ${k.name }
                            </a></c:if>
                            </c:forEach>
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
                                    ${goods.name }
                                </h1>
                                <div class="info">
                                    <span>
                                        ${goods.pubtime }
                                    </span>
                                    <span>
                                       点击数： ${goods.hit }
                                    </span>

                                    <span>
                                        <i class="icon wb-eye margin-right-5" aria-hidden="true">
                                        </i>
                                    </span>
                                </div>
                            </div>
                            <div class="met-editor lazyload clearfix">
                                <div>
                                    <p>性别：${goods.sex}</p>
                                    <p>出生日期：${goods.age}</p>
                                    <p>身高：(cm)：${goods.shenggao}</p>
                                    <p>失踪时间：${goods.stime}</p>
                                    <p>地址：${goods.saddr}</p>

                                 	<p>分类：
                                 	<c:forEach items="${tlist }" var="t">
                                 	<c:if test="${t.id==goods.fid }">
                                 	${t.name }
                                 	</c:if>
                                 	</c:forEach> </p>



                                 	<c:if test="${sessionScope.user!=null }">
                                 	<p><c:if test="${goods.upload!=null }">
          <a title="下载文件"  href="#" onclick="down1('./upload/${goods.upload }')" class="btn btn-xs btn-info" style="color:#f00;"><i class="icon-edit bigger-120">文件下载</i></a>
          </c:if>
           <c:if test="${goods.upload==null }">
          暂无文件
          </c:if></p></c:if>
                                 	<p>发布时间：${goods.pubtime }</p>
                                 	<hr>
                                 	
                                 	<%--<c:if test="${sessionScope.user!=null }">
                                        <p>
                                        <a href="addKeep.do?gid=${goods.id}" class="btn btn-primary ">点赞</a>
                                        </p>
                                        <hr/>
                                    </c:if>--%>
                                 	<p>发布人：<c:forEach items="${ualist}" var="u">
                                        <c:if test="${u.id==goods.uid}">${u.tname}</c:if>
                                    </c:forEach></p>
                                 	<hr>
                                    <p>
                                    ${goods.note }
                                    </p>
                                   
                                    <p>&nbsp;
                                        
                                    </p>
                                    <hr/>



                                    <div id="metinfo_additional">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="row">
                            <div class="met-news-bar">
                                <form name="formsearch" action="searchGoods.do" method="post">
                                    <input type="hidden" name="kwtype" value="0" />
                                    <div class="form-group">
                                        <div class="input-search">
                                            <button type="submit" class="input-search-btn">
                                                <i class="icon wb-search" aria-hidden="true">
                                                </i>
                                            </button>
                                            <input type="text" class="form-control" name="name" placeholder="输入寻亲登记名称">
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
