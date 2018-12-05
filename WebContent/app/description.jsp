<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/jquery.min.js"></script>
<script type="text/javascript" src="./../simditor/scripts/module.js"></script>
<script type="text/javascript" src="./../simditor/scripts/hotkeys.js"></script>
<script type="text/javascript" src="./../simditor/scripts/uploader.js"></script>
<!-- <script type="text/javascript" src="./../simditor/scripts/simditor.js"></script>
<link media="all" rel="stylesheet" type="text/css" href="./../simditor/styles/simditor.css" /> -->
<script type="text/javascript" src="./../assets/trumbowyg.js"></script>
<script type="text/javascript" src="./../assets/plugins/base64/trumbowyg.base64.js"></script>
<link rel="stylesheet" href="./../assets/design/css/trumbowyg.css" />
<script src="../js/config.js"></script>
 <link rel="stylesheet" href="../css/main.css" />
<script src="../js/artDialog.source.js"></script>
<script src="../js/iframeTools.source.js"></script>
<link rel="stylesheet" href="../js/skins/default.css" />
</head>
<style type="text/css">
	.saveBtn{padding: 8px 26px; background: #00ae00; color: #fff; font-size: 16px; border-radius: 6px; border:none; cursor: pointer; }
</style>
<body>

<div style="width: 100%; margin:10px auto ">
<h3>双击文本框开始编辑</h3>
<div id="odiv" style="display:none;position:absolute;z-index:100;">
    <img src="assets/pic/sx.png" title="缩小" border="0" alt="缩小" onclick="sub(-1);"/>
    <img src="assets/pic/fd.png" title="放大" border="0" alt="放大" onclick="sub(1)"/>
    <img src="assets/pic/cz.png" title="重置" border="0" alt="重置" onclick="sub(0)"/>
    <img src="assets/pic/sc.png" title="删除" border="0" alt="删除" onclick="del();odiv.style.display='none';"/>
</div>
<div  onmousedown="show_element(event)" style="clear:both" id="editor" class="editor"></div>
<div style="width: 100%; float: left; margin-top: 10px; text-align: center;">
<button type="button" class="saveBtn" onclick="test();">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
</div>
</div>
</body>
</html>
<script type="text/javascript">
var groupname=getUrlVars()["groupname"];
var PrjName=getCookie("prjName");
function getSensorData(){
          $.post("./../PointAll",{"oper":"GroupName","prjName":PrjName,"groupname":groupname},function(data){
          	  var data= eval("(" + data + ")");
              console.log(data);
          	  data=data[0];
          	 if(CheckValIsNull(data)){
          	 	var Description=data.Demo;
          	 if(CheckValIsNull(Description)){
          		$('#editor').html(Description);
          	 	//$(".description").val(Description);
          	 }
          	
          	 }
          })
	}
$(".saveBtn").bind("click",function(){
	var description=$('#editor').html();
	if(description==null){
		tip("你还没有输入数据！",true);
	}else{
		$.post("./../PointAll",{"oper":"UpdateDescript","prjName":PrjName,"groupname":groupname,"description":description},function(data){
	    	  var data= eval("(" + data + ")");
	    	 if(data=="1"){
	    	 	tip("数据更新成功！",true);
	    	 	setTimeout(function(){
	            window.parent.location.reload();	                  
	           },1000);
	    	 	
	    	 }else{
	             tip("提交保存失败",true);
	    	 }
	    })
	}
})
$(function(){
	getSensorData();	
})
</script>
