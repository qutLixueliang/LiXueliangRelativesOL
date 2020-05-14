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
                            <h3 class="msg-title">寻亲登记添加</h3>
                                <form action="goodsAdd.do"  method="post" class="met-form met-form-validation" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <div>
                                        <label>姓名：</label>
                                            <input  type='text' name='name' class='form-control' required="required"/>
                                            <span id='checku' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                            <label>性别：</label>
                                            <select name="sex" >
                                                <option value="男">男</option>
                                                <option value="女">女</option>
                                            </select>
                                            <span class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div> <label>出生日期：</label>
                                            <input  type='text' name='age' value="2020-01-01" class='form-control' placeholder="请输入出生日期"  />
                                            <span class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div> <label>身高：(cm)</label>
                                            <input  type='text' name='shenggao' value="100" class='form-control' placeholder="请输入身高"  />
                                            <span class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div> <label>失踪时间：</label>
                                            <input  type='text' name='stime' value="2020-01-01" class='form-control' placeholder="请输入失踪时间"  />
                                            <span class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                            <label>地址：</label>
                                            <input  type='text' name='saddr' class='form-control' value="地址1" required="required"/>
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div><label>分类：</label>
                                            <select name="fid">
                                                <c:forEach items="${tlist}" var="t">
                                                    <option value="${t.id}">${t.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div><label>志愿者：</label>
                                            <select name="zyid">
                                                <c:forEach items="${ulist}" var="t">
                                                    <option value="${t.id}">${t.tname}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                     <div class="form-group">
                                        <div><label>图片：</label>
                                            <input  type='file' name='file'  class='form-control' required="required"  />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div><label>上传：</label>
                                            <input  type='file' name='file2'  class='form-control' />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div><label>说明：</label>
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
    
