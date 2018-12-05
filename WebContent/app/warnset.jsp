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
  <style>
  	.contlabel{
  		padding-left: 8px;
  		font-weight: normal;
  	}
  </style>
<body>


 <div class="wrapper" >
          <div class="panel panel-info warnigSet" >
            <div class="panel-heading">
              <h3 class="panel-title">
                	预警关联
              </h3>
            </div>
              <div class="panel-body warnigparma">
                   <p><label>接警人：</label>
                       <select class="userName" id="userName">
                         
                       </select>
                   </p>
                   <p>
                     <label>预警间隔：</label>
                      <select class="warningInterval" id="msgInterval">
                          <option class="30" value="30">30 分钟</option>
                          <option class="60" value="60">1 小时</option>
                          <option class="180" value="180">3 小时</option>
                          <option class="360" value="360">6 小时</option>
                          <option class="720" value="720">12 小时</option>
                          <option class="1440" value="1440">24 小时</option>
                      </select>
                   </p>
                   <p id="prjName" class="pre-scrollable" style="height: 100px; overflow-y:auto;">
                     
                   </p>
                   <br/>
                    <p id="warningtype" style="width: 100%;">
                     <label>预警形式：</label>&nbsp;&nbsp;
                     <label class="contlabel"><input type="checkbox" name='itemstype' value="1" />短信</label>
					 <label class="contlabel"><input type="checkbox" name='itemstype' value="2"/>邮件</label>
					 <label class="contlabel"><input type="checkbox" id='checktype'/>全选</label>
                   </p>
                    
                   <p id="warningLevel">
                     <label>预警类型：</label>
					 <label class="contlabel"><input type="checkbox" name="itemslevel" value="1" />一级预警</label>
					 <label class="contlabel"><input type="checkbox" name="itemslevel" value="2"/>二级预警</label>
					 <label class="contlabel"><input type="checkbox" name="itemslevel" value="3" />三级预警</label>
					 <label class="contlabel"><input type="checkbox" name="itemslevel" value="4" />四级预警</label>
					 <label class="contlabel"><input type="checkbox" id="checklevel"/>全选</label>
                      
                   </p>
              </div>
      </div>


     <br><br><br>
      <div style="text-align: center; float: left; width: 100%">
         <button class="btn btn-default btn-info btn-lg" id="btn_save"> &nbsp;&nbsp;保 存&nbsp;&nbsp;</button>
         <button class="btn btn-default btn-info btn-lg hidden" id="btn_edit"> &nbsp;&nbsp;保 存&nbsp;&nbsp;</button>
      </div>


 </div>
</body>
</html>
<script type="text/javascript">

    var ID = getUrlVars()['ID'];
    var nUserID = getUrlVars()['nUserID'];
    var nProjectID = getUrlVars()['nProjectID'];
    var nMsgHourInterval = getUrlVars()['nMsgHourInterval'];
    var sSendChannel = getUrlVars()['sSendChannel'];
    var sAlertLevel = getUrlVars()['sAlertLevel'];
    var projectStr = getUrlVars()['projectStr'];
    projectStr = decodeURI(projectStr)
    
	$(function(){
		var userName = "";
		$.post("./../WarningUserSetServlet",{"type":"add"},function(data){
			var data = eval(data);
			$.each(data,function(index,result){
				userName += "<option value='"+result.ID+"'>"+result.sUserName+"</option>";
			});
			$("#userName").append(userName);
			chushihua();
		});
		
		//项目信息
		var data = projectStr.split(",");
		var prjName = "";
		$.each(data,function(index,result){
			var prodata = result.split("|");
			prjName += "<label class='contlabel'><input type='checkbox' name='itemspro' value='"+prodata[0]+"' />"+prodata[1]+"</label>";
		});
		$("#prjName").append("<label>结构名称：</label>"+prjName+"<label class='contlabel'><input type='checkbox' id='checkpro'/>全选</label>");
		
		
		//保存
		$("#btn_save").click(function(){
			var userName = $("#userName").val();
			var prjName =[]; 
			$('input[name="itemspro"]:checked').each(function(){ //遍历复选框
				prjName.push($(this).val()); 
			}); 
			if(prjName.length==0){
				alert("结构名称：没有选择任何内容！");
				return false;
			}
			//参数：var data = chk_value.toString();
			var warningtype =[]; 
			$('input[name="itemstype"]:checked').each(function(){ //遍历复选框
				var data = "sms";
				if($(this).val()==1){
					data = "sms";
				}else{
					data = "email";
				}
				warningtype.push(data); 
			}); 
			if(warningtype.length==0){
				alert("预警形式：没有选择任何内容！");
				return false;
			}
			var warningLevel =[]; 
			$('input[name="itemslevel"]:checked').each(function(){ //遍历复选框
				warningLevel.push($(this).val()); 
			}); 
			if(warningLevel.length==0){
				alert("预警类型：没有选择任何内容！");
				return false;
			}
			var msgInterval = $("#msgInterval").val();
			var data = {
				"type" : "save",
				"userID" : userName,
				"proID" : prjName.toString(),
				"warningtype" : warningtype.toString(),
				"msgInterval" : msgInterval,
				"warningLevel" : warningLevel.toString()
			};
			
			//alert(prjName.toString()+","+warningtype.toString()+","+warningLevel.toString());
			
			$.post("./../WarningUserSetServlet",data,function(data){//
				var data = eval(data);
				
				if(data == undefined){
					alert("添加用户重复！");
				}else{
					if(data.msg=="success"){
						tip('保存成功!...',true);
						$("#btn_save").attr("disabled","disabled");
				           setTimeout(function(){
				            window.parent.location.reload();
				           },2000);
					}else{
						 tip('操作失败!...',true);
					}
				}
				
			});
			
		});
		
		//编辑
		$("#btn_edit").click(function(){
			var userName = $("#userName").val();
			var prjName =[]; 
			$('input[name="itemspro"]:checked').each(function(){ //遍历复选框
				prjName.push($(this).val()); 
			}); 
			if(prjName.length==0){
				alert("结构名称：没有选择任何内容！");
				return false;
			}
			//参数：var data = chk_value.toString();
			var warningtype =[]; 
			$('input[name="itemstype"]:checked').each(function(){ //遍历复选框
				var data = "sms";
				if($(this).val()==1){
					data = "sms";
				}else{
					data = "email";
				}
				warningtype.push(data); 
			}); 
			if(warningtype.length==0){
				alert("预警形式：没有选择任何内容！");
				return false;
			}
			var warningLevel =[]; 
			$('input[name="itemslevel"]:checked').each(function(){ //遍历复选框
				warningLevel.push($(this).val()); 
			}); 
			if(warningLevel.length==0){
				alert("预警类型：没有选择任何内容！");
				return false;
			}
			var msgInterval = $("#msgInterval").val();
			var data = {
				"type" : "update",
				"ID" : ID,
				"userID" : userName,
				"proID" : prjName.toString(),
				"warningtype" : warningtype.toString(),
				"msgInterval" : msgInterval,
				"warningLevel" : warningLevel.toString()
			};
			
			$.post("./../WarningUserSetServlet",data,function(data){
				var data = eval(data);
				if(data.msg=="success"){
					$("#btn_edit").attr("disabled","disabled");
					tip('保存成功!...',true);
			           setTimeout(function(){
			            window.parent.location.reload();
			           },2000);
				}else{
					 tip('操作失败!...',true);
				}
			});
		});

		//结构物全选
		$("#checkpro").click(function() {
			var nn = $("#checkpro").is(":checked");
			if(nn == true) {
				 var namebox = $("input[name^='itemspro']");  //获取name值为boxs的所有input
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=true;    //js操作选中checkbox
				 }
			 }
			 if(nn == false) {
				 var namebox = $("input[name^='itemspro']");
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=false;		 }
			 }
		})
		
		//类型全选
		$("#checktype").click(function() {
			var nn = $("#checktype").is(":checked");
			if(nn == true) {
				 var namebox = $("input[name^='itemstype']");  //获取name值为boxs的所有input
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=true;    //js操作选中checkbox
				 }
			 }
			 if(nn == false) {
				 var namebox = $("input[name^='itemstype']");
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=false;		 }
			 }
		})
		
		//级别全选
		$("#checklevel").click(function() {
			var nn = $("#checklevel").is(":checked");
			if(nn == true) {
				 var namebox = $("input[name^='itemslevel']");  //获取name值为boxs的所有input
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=true;    //js操作选中checkbox
				 }
			 }
			 if(nn == false) {
				 var namebox = $("input[name^='itemslevel']");
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=false;		 }
			 }
		})
		
		
	});
    
    function chushihua(){
    	if(ID == null || ID == "" || ID == undefined){
    		 return false;
		}else{
			 $("#btn_save").hide();
			 $("#btn_edit").removeClass("hidden");
			 $("#userName").find("option[value='"+nUserID+"']").attr("selected",true);
			 $("#prjName").find("input[value='"+nProjectID+"']").attr("checked",true);
			 if(sSendChannel=="3"){
				 $("#warningtype").find("input[value='"+(sSendChannel-2)+"']").attr("checked",true);
				 $("#warningtype").find("input[value='"+(sSendChannel-1)+"']").attr("checked",true);
			 }else{
				 $("#warningtype").find("input[value='"+sSendChannel+"']").attr("checked",true);
			 }
			 
			 $("#msgInterval").find("option[value='"+nMsgHourInterval+"']").attr("selected",true);
			 $("#warningLevel").find("input[value='"+sAlertLevel+"']").attr("checked",true);
			 var namebox = $("input[name^='itemslevel']");  //获取name值为boxs的所有input
			 for(var i = 0; i < namebox.length; i++) {
				 if(namebox[i].value < sAlertLevel){
					 namebox[i].checked=true;    //js操作选中checkbox
				 }
				 
			 }
		}
    }
</script>
