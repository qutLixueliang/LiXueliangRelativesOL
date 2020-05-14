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
                            <h3 class="msg-title">会员注册</h3>
                                <form action="reg.do" onsubmit="return check();"  method="post" class="met-form met-form-validation" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <div>
                                        <label>用户名：</label>
                                            <input  type='text' name='uname' id="username" class='form-control' placeholder="登录名" onblur="return checkuname()" required="required"/>
                                            <span id='checku' class="spanInit" ></span>
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <div><label>姓名：</label>
                                            <input  type='text' name='tname'  class='form-control' placeholder="真实姓名" onblur="return checkuname()"  />
                                            <span id='checku2' class="spanInit" ></span>
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <div><label>图片：</label>
                                            <input  type='file' name='file'  class='form-control' required="required"  />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div><label>密码：</label>
                                            <input  type='password' name='pwd' id="txtPwd" class='form-control' placeholder="密码" onblur="return checkpwd()" required="required" />
                                            <span id='prompt_pwd' class="spanInit" ></span>
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <div><label>确认密码：</label>
                                            <input  type='password' name='pwdc' id="txtConfirmPwd" class='form-control' placeholder="确认密码" onblur="return checkpwdc()" required="required" />
                                            <span id='prompt_confirmpwd' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                           <label>性别：</label>
											<select name="sex" >
											<option value="男">男</option>
											<option value="女">女</option>
											</select>
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                            <label>身份：</label>
                                            <select name="utype" >
                                                <option value="用户">用户</option>
                                                <option value="志愿者">志愿者</option>
                                            </select>
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                   <div class="form-group">
                                        <div> <label>年龄：</label>
                                            <input  type='text' name='age'  class='form-control' placeholder="请输入年龄"  />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <div><label>电话：</label>
                                            <input name='tel'  id="txtPhone" class='form-control' type='text' placeholder='电话' onblur="return checktel()" required="required"  />
                                            <span id='prompt_phone' class="spanInit" ></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <div><label>邮箱：</label>
                                            <input name='email'   class='form-control' type='text' placeholder='邮箱'  />
                                            <span  class="spanInit" ></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <div><label>联系地址：</label>
                                            <input name='address'   class='form-control' type='text' placeholder='联系地址'  />
                                            <span  class="spanInit" ></span>
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
    
