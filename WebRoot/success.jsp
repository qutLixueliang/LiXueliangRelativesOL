<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


			<jsp:include page="top.jsp"></jsp:include>

			<div class="page-container" id="innerpage-wrap">
				<div class="container">
					<div class="row">
						<div class="main col-md-9 inner-left" role="main">
							<div class="blog-post blog-post-wrap">
								<h3 class="text-center bp-title">恭喜您，操作成功！</h3>
								<div class="com-cnt page-content bp-content">
									<div id="pages" class="page"></div>
								</div>
							</div>
						</div>
						<aside class="sidebar col-md-3 inner-right" role="complementary">
						</aside>
					</div>
				</div>
			</div>
					<jsp:include page="foot.jsp"></jsp:include>
