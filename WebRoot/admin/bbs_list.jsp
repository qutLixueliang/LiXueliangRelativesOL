<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>




<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit"/>
	<meta name="force-rendering" content="webkit"/>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="名榜,wangid">
	<title>在线寻亲系统</title>

	<!-- CSS -->
	<link rel="stylesheet" href="css/style.css">
	 <link rel="stylesheet" href="css/children.css">

	<!-- 在线图标库 地址：http://fontawesome.dashgame.com/-->
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

	<!-- layui css -->
	<link rel="stylesheet" href="layui/css/layui.css" media="all">
	<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script> 
	<!-- layui js -->
	<script src="layui/layui.js"></script>
</head>

<body>
<div class="wangid_conbox">
	<!-- 当前位置 -->
	<div class="zy_weizhi bord_b">
		<i class="fa fa-home fa-3x"></i>
		<a>首页</a>
		<a>留言管理</a>
		<span>留言列表</span>
	</div>
	<!-- 筛选 --> 
	<form action="goodsListSearch.do" method="post" >
	<%-- <div class="shuaix">
		<div class="left" style="margin-right:10px;"> 
			<select name="tid">   
				<option value="">请选择留言板块</option> 
				<c:forEach items="${tlist }" var="t">
				<option value="${t.id }">${t.name }</option> 
				</c:forEach>  
			</select>
		</div> 
		<div class="right">
			<input type="text" name="name" placeholder="请输入留言的名称">
			<input type="submit" class="btn" value="查询" >
		</div>
	</div> --%>
	</form>
	<!-- 下面写内容 -->
	<table class="layui-table" lay-filter="mylist" lay-size="lg">
		<thead>
			<tr>
				<th lay-data="{field:'xh', align:'center',width:160}">序号</th>
				<th lay-data="{field:'time',align:'center', minWidth:170}">发布人</th>
				<th lay-data="{field:'www',align:'center',minWidth:260}">发布内容</th>
				<th lay-data="{field:'sj',align:'center',minWidth:260}">发布时间</th>
				<!-- <th lay-data="{field:'company',align:'center',minWidth:130}">回复人</th> -->
				<th lay-data="{field:'nr',align:'center',minWidth:130}">回复内容</th>
				<th lay-data="{field:'zt',align:'center',minWidth:130}">状态</th>
				<th lay-data="{field:'addr2',align:'center',minWidth:100}">操作</th>
			</tr> 
		</thead>
		<tbody>
		 <c:forEach items="${list }" var="u"  varStatus="num">
		 <tr>
				<!-- <td></td> -->
				<td>${num.count }</td>
				<td><c:forEach items="${ualist }" var="t">
				<c:if test="${t.id==u.uid }">${t.tname }</c:if>
				</c:forEach></td>
             <td>${u.note }</td>
             <td>${u.pubtime }</td>
				<%-- <td><c:forEach items="${ualist }" var="t">
				<c:if test="${t.id==u.hid }">${t.tname }</c:if>
				</c:forEach></td> --%>
             <td>${u.note2 }</td>
             <td>${u.sta }</td>

			  <td>
                  <c:if test="${u.sta=='待回复'}">
                      <a class="layui-btn layui-btn-xs"   href="doUpdateBbs.do?id=${u.id }" >回复</a>
                  </c:if>

                  <a class="layui-btn layui-btn-danger layui-btn-xs color2"  onclick="return del(1,1,1)" href="deleteBbs.do?id=${u.id }" >删除</a>
			  </td>
			</tr>
        </c:forEach> 
		</tbody>  
	</table>
</div>

<script type="text/javascript">
//单个删除
function del(id,mid,iscid){
	if(confirm("您确定要删除吗?")){
		return true;
	}else{
		return false;
	}
}

</script>
<script type="text/javascript">
	//静态表格
    layui.use('table',function(){
    	var table = layui.table;
		//转换静态表格
		table.init('mylist', {
		  height: 'full-130' //高度最大化减去差值,也可以自己设置高度值：如 height:300
		  ,count: 50 //数据总数 服务端获得
		  ,limit: 5 //每页显示条数 注意：请务必确保 limit 参数（默认：10）是与你服务端限定的数据条数一致
		  ,page:true //开启分页
		  ,toolbar: '#toolbarDemo'//工具栏
		  ,defaultToolbar:['filter', 'exports']
		  ,limits:[ 5, 10, 15, 20, 30, 50]//分页显示每页条目下拉选择
		  ,cellMinWidth: 60//定义全局最小单元格宽度，其余自动分配宽度
		}); 
	}); 

</script> 
</body>
</html>
