<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
String prjName = request.getParameter("prjName");
%> 
 <script src="../js/jquery-1.11.1.min.js"></script>
  <script src="../js/config.js"></script>
  <script type="text/javascript" charset="utf-8" src="./../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="./../ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="./../ueditor/lang/zh-cn/zh-cn.js">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<html>  
  <head>  
 <link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/app.css" /> 
 <script src="../js/jquery.js"></script> 
   
  </head>  
    
  <body>   
  
  <script>
 
    var ue = UE.getEditor('editor');
</script>
<div>
<script id="editor" type="text/plain" style="width:100%;height:400px;"></script>
</div>
</body>
</html>