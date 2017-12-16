<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<ul class="nav navbar-nav navbar-right">
   <li class="dropdown">
      <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>登入</b> <span class="caret"></span></a>
      <ul id="login-dp" class="dropdown-menu">
         <li>
            <div class="row">
               <div class="col-md-12">
				 選擇登入方式
                  <div class="social-buttons">
                     <a href="#" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
                     <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i> Twitter</a>
                  </div>
				或
                  <form class="form" accept-charset="UTF-8" id="login-nav">
                     <div class="form-group">
                        <label class="sr-only" for="account">Email address</label>
                        <input type="text" class="form-control" id="account" placeholder="帳號" required>
                     </div>
                     <div class="form-group">
                        <label class="sr-only" for="pswd">Password</label>
                        <input type="password" class="form-control" id="pswd" placeholder="密碼" required>
                        <div class="help-block text-right"><a href="">忘記密碼？</a></div>
                     </div>
                     <div class="form-group">
                        <button type="submit" value="Send" class="submit btn btn-primary btn-block">登入</button>
                     </div>
                     <div class="checkbox">
                        <label>
                        <input type="checkbox"> 記住我
                        </label>
                     </div>
                  </form>
               </div>
               <div class="bottom text-center">
                  <a href="#"><b>加入我們</b></a>
               </div>
            </div>
         </li>
      </ul>
   </li>
</ul>