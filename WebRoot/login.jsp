<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<jsp:include page="top.jsp"></jsp:include>


		<section class="met-message animsition">
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <div class="row">
                            <div class="met-message-submit">
                            <h3 class="msg-title">登录</h3>
                                <form action="login.do"  method="post"  class="met-form met-form-validation" >
                                    <div class="form-group">
                                        <div>
                                            <input  name="uname" id="name" type="text" placeholder="登录名" class='form-control' type='text' required="required"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                            <input name="pwd" id="pwd" type="password" placeholder="密码" class='form-control'  />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                       <select name="utype">
                                       <option value="用户">用户</option>
                                       <option value="志愿者">志愿者</option>
                                       </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                            ${sessionScope.suc }
                                        </div>
                                    </div>
                                   
                                    <div class="form-group margin-bottom-0">
                                        <button type="submit" class="btn btn-primary btn-block btn-squared">登录 </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
    <jsp:include page="foot.jsp"></jsp:include>     
        
