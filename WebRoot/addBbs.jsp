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
        window.editor = K.create('#editor_id1');
    });
</script>
<script>

    KindEditor.ready(function(K) {

        K.create('textarea[name="note1"]', {

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
                            <h3 class="msg-title">留言添加</h3>
                                <form action="addBbs.do"  method="post" class="met-form met-form-validation" ><!-- enctype="multipart/form-data" -->
                                    <div class="form-group">
                                        <div>
                                        <label>名称：</label>
                                            <input  type='text' name='name' class='form-control' required="required"/>
                                            <span id='checku' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <div><label>内容：</label>
                                            <textarea name="note" id="editor_id" style="width:100%;height:400px;"  placeholder="请输入说明" class="layui-textarea"></textarea>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group margin-bottom-0">
                                        <button type="submit" class="btn btn-primary btn-block btn-squared" onclick="return checkAll()">
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
    
