<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
   <li><font color="red"> * </font> 如果你使用的是360浏览器，请切换到“极速模式” <img src="images/ie.png" width="140"></li>
    <li>1. <a href="http://rj.baidu.com/soft/detail/14744.html?ald">google chrome 浏览器下载</a></li>
    <li>2. <a href="http://www.firefox.com.cn/">Firefox 火狐浏览器下载</a></li>
    <li>3. <a href="http://www.xpgod.com/soft/5653.html">Safari浏览器</a></li>
	 </ul>
</div>
<div id='wraper'>

<div class='login_header'>
    <div class='login_logo'><img src='images/logo_login.png' ></div>
    <div><img src="images/weixin.jpg" style="float: right; width: 72px; margin: 20px 10px"><a href="http://www.hnlzqs.com" style="float: right;color: #fff;text-decoration: none; margin-top: 50px">关于我们</a></div>
</div>
<div class='login_wraper'>

   <div class='login_content'>
      <div class="login_panel">
			<ul> 
						        
	         <li><label>账号：</label><input class='username' name='username' type="text"  onblur="cleardialog('u')" ></li>
	         <p class="wrong_username"></p>
	         <li><label>密 码：</label><input class='userpassword' name='password' type="password" onBlur="cleardialog('p')" ></li>
	          <p class="wrong_password"></p>
	          
	       <li class="submitline"> <label>&nbsp;</label><input type="checkbox" class="remember" name="remember" /> <font class="remember_font">记住登录状态</font>
	         <button class='loginBtn' onClick="return cheLogin()">登 录</button></li>
	         <li><label>&nbsp;</label><font class="login_faile"></font></li>
						        
						         
						      </ul>			      	
      </div>
      
   </div>
 
</div>


</div>
</body>
</html>
<script language="javascript">

function GetRequest() {  
    var url = location.search; //获取url中"?"符后的字串   
    var theRequest = new Object();  
    if (url.indexOf("?") != -1) {  
        var str = url.substr(1);  
        strs = str.split("&");  
        for (var i = 0; i < strs.length; i++) {  
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);  
        }  
    }  
    return theRequest;  
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
   
	/*
	var username=getCookie("sift_username");
	if(CheckValIsNull(username)){
		if(username.length>0){
		 $("input[name='remember']").attr("checked",true);
	}
	$(".username").val(username);
	$(".userpassword").val(getCookie("sift_password"));
	
	}
	*/

    var Request = new Object();  
    Request = GetRequest();  
    var val= Request["uid"];    
	$(".username").val(val);
    var pwd= Request["pwd"];    
	$(".userpassword").val(pwd);
    
	cheLogin();
	
})
function cheLogin(){
	
	  var username=$(".username").val();
	  var userpassword=$(".userpassword").val();
	  var code=$(".code").val();
	  if(username.length<1){
		  $(".wrong_username").html("用户名填写错误。");
		
		  $(".username").focus();
		return false;  
	  }
	    if(userpassword.length<1){
		 $(".wrong_password").html("用户名或密码错误！");
		  $(".userpassword").focus();
		return false; 
	  }
	  /*无需验证码
	  if(code.length<1){
	  	 $(".wrong_code").html("验证码错误！");
		  $(".code").focus();
		return false; 
	  }
	  */
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
			}else if(data=="wrong"){
				$(".login_faile").html("用户名或密码错误！");
			}else if(data=="codewrong"){
				$(".login_faile").html("验证码填写错误！");
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
		         $(".wrong_username").html("用户名填写错误。");
		
		          $(".username").focus();
		         return false;  
			  }else{
				   $(".wrong_username").html("");
				   return true;
			  }
			 break;
			 
			 case "p":
			      var password=$(".userpassword").val();
			      if(password.length<1){
		         $(".wrong_password").html("密码填写错误。");
		
		          $(".userpassword").focus();
		         return false;  
			  }else{
				   $(".wrong_password").html("");
				   return true;
			  }
			 break;
			 case "c":
             var code=$(".code").val();
          
			      if(code.length<1){
		         $(".code").html("验证码错误！。");
		
		          $(".code").focus();
		         return false;  
			  }else{
				   $(".wrong_code").html("");
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