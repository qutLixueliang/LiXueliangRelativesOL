<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>




 
			<jsp:include page="top.jsp"></jsp:include>
			
			
			
			
			
			<div class="met-column-nav bordernone">
            <div class="container">
                <div class="row">
                    <div class="product-search">
                        <form name="formsearch" action="searchNews_lt.do" method="post" >
                            <input type="hidden" name="kwtype" value="0" />
                            <div class="form-group">
                                <div class="input-search">
                                    <button type="submit" class="input-search-btn">
                                        <i class="icon wb-search" aria-hidden="true">
                                        </i>
                                    </button>
                                    <input type="text" class="form-control" name="name" placeholder="输入论坛名称" value="" >
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="met-product animsition type-3">
            <div class="container-fluid">
                <ul class="blocks-2 blocks-sm-2 blocks-md-4 blocks-xlg-4  met-page-ajax met-grid"
                id="met-grid" data-scale='0.75117370892019' >
                <c:forEach items="${list }" var="n">
                    <li class="shown">
                        <div class="widget widget-shadow" >
                            <figure class="widget-header cover">
                                <a href="showNewx_lt.do?id=${n.id }" title="${n.name }" target='_self'>
                                    <img  src="./upload/${n.img }" alt="${n.name }"  style='height:300px!important' height="300px;">
                                </a>
                            </figure>
                            <div class="widget-body">
                                <h4 class="widget-title">
                                    ${n.name }
                                </h4>
                                <p>
                                    ${n.pubtime }
                                </p>
                                <div class="widget-body-footer">
                                    <div class="widget-actions pull-right">
                                        <a href="showNewx_lt.do?id=${n.id }" title="${n.name }">
                                            <i class="icon wb-eye" aria-hidden="true">
                                            </i>
                                            <span>
                                                ${n.hit }
                                            </span>
                                        </a>
                                    </div>
                                    <a href="showNewx_lt.do?id=${n.id }" title="${n.name }" target='_self'
                                    class="btn btn-outline btn-primary btn-squared">
                                        查看详情
                                    </a>
                                </div>
                            </div>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
                <div>
                	<c:if test="${sessionScope.p==1 }">
                    <div class='met_pager'>
                    <c:if test="${page.page>1}">
		             <a href="newsListFore_lt.do?page=1" >首页</a>
		             <a href="newsListFore_lt.do?page=${page.page-1 }" class="Ahover"> 上一页</a> 
		             <span class='PreSpan'>下一页</span>
		             <span class='PreSpan'>末页</span>
		             </c:if>
		    	     <c:if test="${page.page<page.totalPage}">
		    	     <span class='PreSpan'>首页</span>
		             <span class='PreSpan' >上一页</span>
				     <a href="newsListFore_lt.do?page=${page.page+1 }" class="Ahover">下一页</a>
					 <a href="newsListFore_lt.do?page=${page.totalPage }">末页</a>
				    </c:if> 
                    </div>
                    </c:if>
                </div>
                
            </div>
        </div>
        
       
			<jsp:include page="foot.jsp"></jsp:include>
	