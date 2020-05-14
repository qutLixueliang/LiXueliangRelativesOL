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
    <link rel="stylesheet" href="layui/css/layui.css">
    <script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
   <style>
     
 .layui-form-pane .layui-form-checkbox {
    margin: 12px 0 4px 10px!important;
}
    </style>  
</head>
<script charset="utf-8" src="/zaixianxunqin/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="/zaixianxunqin/kindeditor/lang/zh-CN.js"></script>
<script>
	        KindEditor.ready(function(K) {
	                window.editor = K.create('#editor_id');
	        });
	</script>
	<script>

KindEditor.ready(function(K) {

K.create('textarea[name="note"]', {

uploadJson : '/zaixianxunqin/kindeditor/jsp/upload_json.jsp',

                fileManagerJson : '/zaixianxunqin/kindeditor/jsp/file_manager_json.jsp',

                allowFileManager : true,

                allowImageUpload : true, 

autoHeightMode : true,

afterCreate : function() {this.loadPlugin('autoheight');},

afterBlur : function(){ this.sync(); }  //Kindeditor下获取文本框信息

});

});

</script>
<body>
    <div class="wangid_conbox">
        <!-- 当前位置 -->
        <div class="zy_weizhi bord_b">
            <i class="fa fa-home fa-3x"></i>
            <a>首页</a>
            <a>寻亲管理</a>
            <span>寻亲修改</span>
        </div>
        <!-- 内容 -->    
        <div class="kehubh_tj_k">
            <form class="layui-form layui-form-pane" action="updateGoods.do" method="post" enctype="multipart/form-data" > 
            <ul> 
                <li>
                    <div class="left">寻亲名：</div>
                    <div class="right"> 
                        <input type="text" name="name" required="required" value="${goods.name }" placeholder="请输入寻亲名" autocomplete="off" class="layui-input">
                    	<input type="hidden" name="id" value="${goods.id }">
                    </div>
                </li>
                <li>
                    <div class="left">图片：</div>
                    <div class="right">
                    <img alt="" src="../upload/${goods.img }" width="50px" height="50px;">
                            <input style="margin-top: 8px;" type="file" name="file" >
                    </div>
                </li>
                 <li>
                    <div class="left">寻亲上传：</div>
                    <div class="right">
                            <input style="margin-top: 8px;" type="file" name="file2" >
                    </div>
                </li>
                <li>
                    <div class="left">性别：</div>
                    <div class="right">
                        <c:if test="${goods.sex=='男'}">
                            <input type="radio" name="sex" value="男" title="男" checked>
                            <input type="radio" name="sex" value="女" title="女" >
                        </c:if>
                        <c:if test="${goods.sex!='男'}">
                            <input type="radio" name="sex" value="男" title="男" checked>
                            <input type="radio" name="sex" value="女" title="女" >
                        </c:if>

                    </div>
                </li>
                <li>
                    <div class="left">出生日期：</div>
                    <div class="right">
                        <input type="text" name="age" required="required" value="${goods.age}" placeholder="请输入出生日期" autocomplete="off" class="layui-input">
                    </div>
                </li>
                <li>
                    <div class="left">身高：(cm)：</div>
                    <div class="right">
                        <input type="text" name="shenggao" required="required" value="${goods.shenggao}" placeholder="请输入身高" autocomplete="off" class="layui-input">
                    </div>
                </li>
                <li>
                    <div class="left">失踪时间：</div>
                    <div class="right">
                        <input type="text" name="stime" required="required" value="${goods.stime}" placeholder="请输入失踪时间" autocomplete="off" class="layui-input">
                    </div>
                </li>
                <li>
                    <div class="left">地址：</div>
                    <div class="right">
                        <input type="text" name="saddr" required="required" value="${goods.saddr}" placeholder="请输入saddr" autocomplete="off" class="layui-input">
                    </div>
                </li>
                <li style="height: 38px; overflow:initial;">
                    <div class="left">志愿者：</div>
                    <div class="right">
                        <select name="fid" >
                            <c:forEach items="${ualist}" var="t">
                                <c:if test="${goods.zyid==t.id}">
                                    <option value="${t.id}">${t.tname}</option>
                                </c:if>
                            </c:forEach>
                            <c:forEach items="${ulist}" var="t">
                                <c:if test="${goods.zyid!=t.id}">
                                    <option value="${t.id}">${t.tname}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </li>

                

                 <li style="height: 38px; overflow:initial;">
                    <div class="left">分类：</div>
                    <div class="right"> 
                        <select name="fid" >
                        	<c:forEach items="${tlist }" var="t">
                        	<c:if test="${t.id==goods.fid }">
                        	<option value="${t.id }">${t.name }</option>
                        	</c:if>
                        	</c:forEach>
                        	<c:forEach items="${tlist }" var="t">
                        	<c:if test="${t.id!=goods.fid }">
                        	<option value="${t.id }">${t.name }</option>
                        	</c:if>
                        	</c:forEach>
                        </select> 
                    </div>
                </li>
                 <li>
                    <div class="left">说明：</div>
                    <div class="right"> 
                     	<textarea name="note" id="editor_id" style="width:100%;height:400px;"  placeholder="请输入说明" class="layui-textarea">${goods.note }</textarea>
                    </div>
                </li>
                <li>
                    <div class="left"> &nbsp;</div>
                    <div class="right"> 
                        <button class="button_qr" type="submit">确定修改</button>
                    </div>
                </li>
            </ul> 
            </form>
        </div>
    </div>   
   
	<!-- houl --> 
	<!-- 结束 -->
    <!-- layui js -->
    <script src="layui/layui.js"></script>
    
</body>
<script>
        layui.use(['form', 'layedit', 'laydate'], function(){
          var form = layui.form
          ,layer = layui.layer
          ,layedit = layui.layedit
          ,laydate = layui.laydate;
        });
        </script>
</html>

