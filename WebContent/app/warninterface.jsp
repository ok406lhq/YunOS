<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/warnset.css" />

 <script src="../js/jquery.min.js"></script>
  <script src="../js/config.js"></script>
 <script src="../js/artDialog.source.js"></script>
    <script src="../js/iframeTools.source.js"></script>
  <link rel="stylesheet" href="../js/skins/default.css" />
<body>

   <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li ><a href="#">预警管理</a></li>
   
</ol>
 <div class="wrapper" style="width: 96%; margin-left: 2%" >
     
       


          <div class="panel panel-info warnigSet" >
            <div class="panel-heading">
              <h3 class="panel-title">
                自定义邮件服务器
              </h3>
            </div>
              <div class="panel-body emailset">
                    <div><label>服务器地址：</label><input type="text" class="emailserver"></div>
                     <div><label>服务器端口：</label><input type="text" class="emailport"></div>
                     <div><label>邮箱地址：</label><input type="text" class="emailaddress"></div>
                     <div><label>邮箱密码：</label><input type="password" class="emailpass"></div>
              </div>
      </div>


      <div class="panel panel-info" style="width: 100%; float: left;margin-top: 5px">
            <div class="panel-heading">
              <h3 class="panel-title">
                自定义短信服务器
              </h3>
            </div>
              <div class="panel-body emailset">
              <div>
                  <label>设备名：</label>
                  <select class="sensornames">
                    
                  </select>
                  </div>
              </div>
      </div>
      <div style="text-align: center; float: left; width: 100%">
         <button class="btn btn-default btn-info btn-lg btn_save"  > &nbsp;&nbsp;保 存&nbsp;&nbsp;</button>
      </div>


 </div>
</body>
</html>
<script type="text/javascript">
var act=getUrlVars()['act'];
var sensor;


   

  $(".btn_save").bind("click",function(){
	 tip(' 正在提交数据...');
     var emailserver=$(".emailserver").val();
     var emailport=$(".emailport").val();
     var emailaddress=$(".emailaddress").val();
     var emailpass=$(".emailpass").val();
     var sensorname=$(".sensornames").val();
   if(emailserver.length<1){
    tip("请填写服务器地址!",true);
    $(".emailserver").focus();
    return false;
   } 
    if(emailport.length<1){
    tip("请填写服务器端口!",true);
    $(".emailport").focus();
    return false;
   } 
   var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
     if(!myreg.test(emailaddress)){
               tip("请填写有效的邮箱地址!",true);
                
               $(".emailaddress").focus();
               return false;
          }
  if(emailpass.length<1){
      tip("请填写邮箱密码!",true);
    $(".emailpass").focus();
    return false;
   }
   
     $.ajax({  
    	 async:false,  
    	 url: "http://121.42.15.5/json/wtyang.php?srvAddr="+emailserver+"&srvPort="+emailport+"&user="+emailaddress+"&pwd="+emailpass+"&SMSDev="+sensorname+"",  
    	 type: "GET",  
    	 dataType: 'jsonp',  
    	 jsonp: 'jsoncallback',  
    	 data: "",  
    	 timeout: 5000,  
    	   
    	 success: function (json) {
        tip_close();
    		 var msg=json.items.msg;
          if(msg=="OK"){
             art.dialog.alert("提交保存成功！")
          }else{
            art.dialog.alert("保存失败！！")
          }
    	 },error:function(){
          tip_close();
          art.dialog.alert("系统错误，稍后重试！！")
       }  
    	 })
    	
  })

 
$(function(){

getPtinfo();

})

function getPtinfo(){
     $.post("./../PointAll",{oper:"all",prjName:""},function(data){
       var data=eval("("+data+")");
        var projectListArr=data; 
       var sensorStr="";

       if(projectListArr.length>0){
          $.each(projectListArr,function(index,project){
           var groupname=project.groupname;
          var SensorName=project.SensorName;
          var SensorType=project.SensorType;
          var select="";
          if(SensorName==sensor){
            select="selected";
          }
          if(SensorType=="SMS"){
            sensorStr+="<option value="+SensorName+" "+select+">"+SensorName+"</option>";
          }
          
        })
           $(".sensornames").append(sensorStr);
       }

     })
  }
</script>
