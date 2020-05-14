<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>




<!-- layui css -->
	<link rel="stylesheet" href="./admin/layui/css/layui.css" media="all">
	<script type="text/javascript" src="./admin/js/jquery-1.11.0.min.js"></script> 
	<!-- layui js -->
	<script src="./admin/layui/layui.js"></script>
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
 
			<jsp:include page="top.jsp"></jsp:include>
			
       <table class="layui-table" lay-filter="mylist" lay-size="lg" >
		<thead>
			<tr>
				<th lay-data="{field:'xh', align:'center',width:160}">序号</th>
				<th lay-data="{field:'yx', align:'center',width:160}">姓名</th>
			    <th lay-data="{field:'www',align:'center',minWidth:260}">添加时间</th>
				<th lay-data="{field:'addr2',align:'center',minWidth:100}">操作</th> 
			</tr> 
		</thead>
		<tbody style="height: 250px;">
		 <c:forEach items="${list }" var="u"  varStatus="num">
		 <tr>
				<!-- <td></td> -->
				<td>${num.count }</td>
				<td>
				<c:forEach items="${ualist }" var="n">
				<c:if test="${n.id==u.hid }">
				<a href="searchGoods.do?uid=${n.id }">${n.tname }(查看寻亲)</a></c:if>
				</c:forEach></td>
	          	<td>${u.pubtime }</td>
			  <td><a class="layui-btn layui-btn-danger layui-btn-xs color2"  onclick="return del(1,1,1)" href="deleteHaoYou.do?id=${u.id }" >删除</a>
			  </td>
			</tr>
        </c:forEach> 
		</tbody>  
	</table>
       
			<jsp:include page="foot.jsp"></jsp:include>
	
