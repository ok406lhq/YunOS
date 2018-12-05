<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="shortcut icon" href="images/ico.png" />
<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/config.js"></script>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<title>北斗+安全监测云平台</title>
<body>
	<div class="navigator">
		<span><img src="images/del.gif" class="navigator_close"></span>
		<ul>
			<li>尊敬的用户：</li>

			<li>系统检测到您的浏览器版本太低（********），在使用本系统平台时，会出现异常影响使用，建议您选择以下浏览器之一访问，如果您已经安装了下面的浏览器，请选择其它浏览器访问。</li>
			<li><font color="red"> * </font> 如果你使用的是360浏览器，请切换到“极速模式” <img
				src="images/ie.png" width="140"></li>
			<li>1. <a href="http://rj.baidu.com/soft/detail/14744.html?ald">google
					chrome 浏览器下载</a></li>
			<li>2. <a href="http://www.firefox.com.cn/">Firefox 火狐浏览器下载</a></li>
			<li>3. <a href="http://www.xpgod.com/soft/5653.html">Safari浏览器</a></li>
		</ul>
	</div>
	<div id='wraper' onkeydown="keyLogin()">
		<div class='login_header'>
			<div class='login_logo'>
				<img src='images/logo_kexinyun.png' style="width:100;height:40;margin:20px,20px;"> 
			</div>
			<!-- <div style="float: right; width: 72px; margin: 20px 20px;color:#fff;font-size: 16px]";>
			联系客服</div> -->
			<div class="login_rok"><img  src="images/rok.png"/></div>
		</div>
		<div class='login_wraper'>
			<div class='login_content'>
				<div class="login_panel">
					<ul>
						<li><label>账号：</label><input class='username' name='username'
							id="username" type="text" onblur="cleardialog('u')"></li>
						<p class="wrong_username"></p>
						<li><label>密 码：</label><input class='userpassword'
							name='password' type="password" onBlur="cleardialog('p')"></li>
						<p class="wrong_password"></p>
						<li><label>验证码：</label><input class='code' name='code'
							type="text" onBlur="cleardialog('c')"> <a
							onclick="javascript:document.getElementById('img').src='./authImage?'+new Date().getTime();"><img
								src="./authImage" id="img" class="codeimg" /></a></li>
						<li class="submitline"><label>&nbsp;</label>
							<button class='loginBtn' id="loginBtn"
								onClick="return cheLogin()">登 录</button></li>
						<li><label>&nbsp;</label><font class="login_faile"></font></li>

						<li><a class="aaa">联系客服</a></li>
					</ul>
				</div>

			</div>
			

		</div>


	</div>
</body>
</html>
<script language="javascript">

window.onload = function(){
    document.getElementById('username').focus();
}

function keyLogin(){
	if (event.keyCode==13)  //回车键的键值为13
		document.getElementById("loginBtn").click(); //调用登录按钮的登录事件
}

$(function(){
	

	ua = getBrowser();
	
    var appname=ua.appname;
    var version=ua.version;
 
    if(appname=="msie"){
    	 if(version<10){
    	 	$(".navigator").slideDown('fast');
    	 }

    }


$(".navigator_close").click(function() {
		$(".navigator").slideUp('fast');
	});
	
    $(".weixing").bind(touchEvents.touchstart,function(){
    	$(".login_footer").find('div').toggle();
    });
   
	
	var username=getCookie("sift_username");
	if(CheckValIsNull(username)){
		if(username.length>0){
		 $("input[name='remember']").attr("checked",true);
	}
	$(".username").val(username);
	$(".userpassword").val(getCookie("sift_password"));
	
	}
	
	
})
function cheLogin(){
	
	  var username=$(".username").val();
	  var userpassword=$(".userpassword").val();
	  var code=$(".code").val();
	  if(username.length<1){
		  $(".login_faile").html("用户名不能为空！");
		
		  $(".username").focus();
		return false;  
	  }
	    if(userpassword.length<1){
		 $(".login_faile").html("密码不能为空！");
		  $(".userpassword").focus();
		return false; 
	  }
	  
	  if(code.length<1){
	  	 $(".login_faile").html("验证码不能为空！");
		  $(".code").focus();
		return false; 
	  }
	  
	  var menber= $("input[name='remember']").is(':checked');
	  if(menber){
		  setCookie("sift_username",username);
		  setCookie("sift_password",userpassword);
	  }else{
		  delCookie("sift_username");
		  delCookie("sift_password");
		  
	  }
	tip("正在提交数据.....");
	$.post("./UserLogin",{"username":username,"password":userpassword,"code":code}, function(data) {
		 tip_close();
        if(data.length>0){
        	
		  
			if(data=="ok"){
				tip("请稍后,正在跳转页面中.....");
				window.location.href="index.jsp";
				window.localStorage.setItem("pwd", userpassword);
			}else if(data=="wrong"){
				$(".login_faile").html("用户名或密码错误！");
			}else if(data=="codewrong"){
				$(".login_faile").html("验证码错误！");
			}else {
				$(".login_faile").html("未知错误");
			}
		}else{
			   $(".login_faile").html("系统错误，请稍后重试！");
		}
});
	  
}
 $(".loginBtn").hover(function(){
	 $(this).css({"background-color":"#ffba00","color":"#fff"});
 },function(){
	  $(this).css({"background-color":"#018ec2","color":"#fff"});
 })
 


 

 function cleardialog(str){
		
		 switch(str){
			 case "u":
			  var username=$(".username").val();
			      if(username.length<1){
		         $(".login_faile").html("用户名不能为空!");
		          //$(".username").focus();
		         return false;  
			  }else{
				   $(".login_faile").html("");
				   return true;
			  }
			 break;
			 
			 case "p":
			      var password=$(".userpassword").val();
			      if(password.length<1){
		         $(".login_faile").html("密码不能为空!");
		          //$(".userpassword").focus();
		         return false;  
			  }else{
				   $(".login_faile").html("");
				   return true;
			  }
			 break;
			 case "c":
             var code=$(".code").val();
          
			      if(code.length<1){
		         $(".login_faile").html("验证码不能为空！");
		
		          //$(".code").focus();
		         return false;  
			  }else{
				   $(".login_faile").html("");
				   return true;
			  }
		 break;
		 }
		 
	 }


function getBrowser(){ 
var browser = { 
msie: false, firefox: false, opera: false, safari: false, 
chrome: false, netscape: false, appname: 'unknown', version: 0 
}, 
userAgent = window.navigator.userAgent.toLowerCase(); 
if ( /(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test( userAgent ) ){ 
browser[RegExp.$1] = true; 
browser.appname = RegExp.$1; 
browser.version = RegExp.$2; 
} else if ( /version\D+(\d[\d.]*).*safari/.test( userAgent ) ){ // safari 
browser.safari = true; 
browser.appname = 'safari'; 
browser.version = RegExp.$2; 
} 
var rest={"appname":browser.appname,"version":browser.version};
return rest; 
} 



</script>