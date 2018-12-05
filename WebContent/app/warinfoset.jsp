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
  <link href="../css/myAlert.css" rel="stylesheet" type="text/css" />
      <script src="../js/myAlert.js"></script>
<body>


 <div class="wrapper" >
     
       


          <div class="panel panel-info warnigSet" >
            <div class="panel-heading">
              <h3 class="panel-title">
                	信息编辑
              </h3>
            </div>
              <div class="panel-body warnigparma">
                   		<label>告警结构:</label>
                     	<label id="PrjAlias" style="width:300px;">路桥</label>
              </div>
              <div class="panel-body warnigparma">
                     <label>告警点位：</label>
                      <label id="wSensor" style="width:300px;"></label>
              </div>
              <div class="panel-body warnigparma">
                     <label>告警时间:</label>
                      <label id="waringTime" style="width:300px;"></label>
              </div>
              <div class="panel-body warnigparma">
              	<label>告警内容:</label>
              	<label id="waringMsg" style="width:60%;">tesy</label>
              </div>
      </div>


      <div class="panel panel-info" style="width: 100%; float: left;margin-top: 10px">
            <div class="panel-heading">
              <h3 class="panel-title">
                	处理意见:
              </h3>
            </div>
              <div class="panel-body ">
                  <textarea rows="4" class="Wtext" id="info"> </textarea>
              </div>
      </div>
      <div style="text-align: center; float: left; width: 100%">
         <button class="btn btn-info btn-lg " onclick="update();"> &nbsp;&nbsp;提交&nbsp;&nbsp;</button>
         <button class="btn btn-default btn-lg "  onclick="javascript:window.history.back()"> &nbsp;&nbsp;返回&nbsp;&nbsp;</button>
      </div>
	

 </div>
</body>
</html>
<script type="text/javascript">

	var id=getUrlVars()["id"];
	var userId=getUrlVars()["userId"];
	var PrjAlias=decodeURIComponent(getUrlVars()["PrjAlias"]);
	var wSensor=getUrlVars()["wSensor"];
	var waringTime=decodeURIComponent(getUrlVars()["waringTime"]);
	var waringMsg=decodeURIComponent(getUrlVars()["waringMsg"]);
	var response=decodeURIComponent(getUrlVars()["response"]);
	$(function(){
		$('#PrjAlias').text(PrjAlias);
		$('#wSensor').text(wSensor);
		$('#waringTime').text(waringTime);
		$('#waringMsg').text(waringMsg);
		$('#info').val(response);
	});
 	function update(){
 		var info=$('#info').val();
 		if(!/.*[\u4e00-\u9fa5]+.*$/.test(info)&&!/.*[A-Za-z0-9]+.*$/.test(info)){
 			$.myAlert("处理意见不能为空！");
 			return;
 		}
		$.ajax({
			url:'./../updateinfo',
			type:'post',
			data:{'id':id,'info':encodeURI($('#info').val()),'userId':userId},
			success:function(data){
				options={title:'提示',message:data,callback:function(){window.history.back();}};
				$.myAlert(options);
				
			}
		});
	} 
</script>
