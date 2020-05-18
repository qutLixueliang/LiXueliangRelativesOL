<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

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

	<jsp:include page="top.jsp"></jsp:include>




	<section class="met-message animsition">
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <div class="row">
                        
                            <div class="met-message-submit">
                            <h3 class="msg-title">论坛修改</h3>
                                <form action="updateNews_lt.do"  method="post" class="met-form met-form-validation" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <div>
                                        <label>名称：</label>
                                            <input  type='text' name='name' class='form-control' value="${news.name}" required="required"/>
                                            <input  type='hidden' name='id' class='form-control' value="${news.id}" required="required"/>
                                            <span id='checku' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div><label>板块：</label>
                                            <select name="fid">
                                                <c:forEach items="${tlist}" var="t">
                                                    <c:if test="${news.fid==t.id}">
                                                    <option value="${t.id}">${t.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <c:forEach items="${tlist}" var="t">
                                                    <c:if test="${news.fid!=t.id}">
                                                        <option value="${t.id}">${t.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <div><label>图片：</label>
                                            <img src="upload/${news.img}" width="50px" height="50px">
                                            <input  type='file' name='file'  class='form-control'   />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <!-- <div class="form-group">
                                        <div><label>上传：</label>
                                            <input  type='file' name='file2'  class='form-control' required="required"  />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div> -->

                                    <div class="form-group">
                                        <div><label>说明：</label>
                                            <textarea name="note" id="editor_id" style="width:100%;height:400px;"  placeholder="请输入说明" class="layui-textarea">${news.note}</textarea>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group margin-bottom-0">
                                        <button type="submit" class="btn btn-primary btn-block btn-squared" >
                                            提交
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
    <jsp:include page="foot.jsp"></jsp:include>  
    
