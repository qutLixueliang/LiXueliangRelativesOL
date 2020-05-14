<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="js/regist.js"></script>
	<jsp:include page="top.jsp"></jsp:include>




	<section class="met-message animsition">
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <div class="row">
                        
                            <div class="met-message-submit">
                            <h3 class="msg-title">会员修改</h3>
                                <form action="addShowInfo.do" onsubmit="return check();"  method="post" class="met-form met-form-validation" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <div>
                                        <label>用户名：</label>
                                            <input  type='text' name='uname' id="username" value="${user.uname }" class='form-control' placeholder="登录名" onblur="" readonly="readonly"/>
                                            <input type="hidden" name="id" value="${user.id }">
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <div><label>姓名：</label>
                                            <input  type='text' name='tname'  value="${user.tname }" class='form-control' placeholder="真实姓名" onblur="return checkuname()"  />
                                            <span id='checku2' class="spanInit" ></span>
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <div><label>图片：</label>
                                        <img alt="" src="./upload/${user.img }" width="50px" height="50px;">
                                            <input  type='file' name='file'  class='form-control' required="required"  />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div><label>密码：</label>
                                            <input  type='password'  value="${user.pwd }" name='pwd' id="txtPwd" class='form-control' placeholder="密码"  required="required" />
                                            <span id='prompt_pwd' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                           <label>性别：</label>
											<select name="sex" >
											<option value="${user.sex }">${user.sex }</option>
											<option value="男">男</option>
											<option value="女">女</option>
											</select>
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                   <div class="form-group">
                                        <div> <label>年龄：</label>
                                            <input  type='text' name='age' value="${user.age }" id="" class='form-control' placeholder="请输入年龄"  />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <div><label>电话：</label>
                                            <input name='tel'  id="txtPhone" value="${user.tel }" class='form-control' type='text' placeholder='电话' onblur="return checktel()" required="required"  />
                                            <span id='prompt_phone' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <div><label>邮箱：</label>
                                            <input name='email'  id="" class='form-control' value="${user.email }"  type='text' placeholder='邮箱'  />
                                            <span id='' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <div><label>联系地址：</label>
                                            <input name='address'  id="" class='form-control' value="${user.address }" type='text' placeholder='联系地址'  />
                                            <span id='' class="spanInit" ></span>
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

