<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%>
	<%@ include file="session.jsp" %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
				<link rel="stylesheet" href="../css/bootstrap.min.css" />
				<link rel="stylesheet" href="../css/font-awesome.min.css" />
				<link rel="stylesheet" href="../css/main.css" />
				<link rel="stylesheet" href="../css/kkpager.css" />
				<link rel="stylesheet" href="../css/app.css" />
				<script src="../js/jquery-1.11.1.min.js"></script>
				
				<script src="../js/bootstrap.min.js"></script>
				<script src="../js/config.js"></script>
				<script src="../js/artDialog.source.js"></script>
				<script src="../js/iframeTools.source.js"></script>
				<script src="../js/lhgcalendar.min.js"></script>
				<link href="../css/lhgcalendar.css" rel="stylesheet" type="text/css" />
				<script src="../js/kkpager.min.js"></script>
				 <link rel="stylesheet" href="../js/skins/default.css" />
				
				
				  <style>
					/* 新增样式  */
					.warinfo_parma .activeval,.warinfo_parma .structure_type,.warinfo_parma .project_name,.warinfo_parma .starttime,.warinfo_parma .endtime{width: 100px; height: 32px; padding: 2px; border: 1px #ddd solid;float: left;}
				  </style>
				
			</head>
			
			<body style="width:100%;">

			  <ol class="breadcrumb">
			    <li>
			      <a href="slopelist.jsp">
			        <i class="fa fa-home"></i>&nbsp;首页</a>
			    </li>
			    <li>
			      <a href="#">预警管理</a></li>
			    <li class="active">预警信息</li></ol>
			  <div class="wrapper">
			    <div class="warinfo_parma" style="width: 96%;margin-left: 2%">
			      <label>预警级别：</label>
			      <select class="activeval" >
			        <option value="All">全部</option>
			      	<option value="一级预警">一级预警</option>
			      	<option value="二级预警">二级预警</option>
			      	<option value="三级预警">三级预警</option>
			      	<option value="四级预警">四级预警</option>
			      </select>
			      
			      <label>结构类型：</label>
			      <select class="structure_type" onchange="changeType()"></select>
			      
			      <label>结构名称：</label>
			      <select class="structure_name" onchange="changeName()"></select>
			     
			      <label>开始日期</label>
			      <input class="starttime">
			      <label>至</label>
			      <input class="endtime">
			      <button type="button" class="btn btn-info activeBtn" style="margin-left: 20px">&nbsp;&nbsp;查询&nbsp;&nbsp;</button>
			    </div>
			    <table class="table table-bordered table-hover waringMsgTb" style="width: 96%;margin-left: 2%">
			      <thead style="background: #f6f4f3">
			        <tr>
			          <th width="10%">预警结构</th>
			          <th width="10%">预警设备</th>
			          <th width="10%">预警级别</th>
			          <th width="20%">预警内容</th>
			          <th width="10%">预警时间</th>
			          <th width="10%">处理结果</th>
			          <th width="10%">预警确认</th>
			          <th width="10%">预警处理</th>
			          <th width="5%">确认</th>
			          <th width="5%">处理</th>
			      </thead>
			      <tbody style="text-align: center;"></tbody>
			    </table>
			  </div>
			  <div id="kkpager" class="fenye" style="margin-top: -20px"></div>
			  <p>&nbsp;</p>
			</body>

<script type="text/javascript">

	var role = "";
	$.post("./../UserInfo",{oper:"userinfo"},function(data){
	    if(CheckValIsNull(data)){
			role=data.role;
	    }
	});

	var pageSize=10;
	var totalPage;
	//var totalRecords;
	var page = getUrlVars()["page"];
	  if(!page){
	    page = 1;
	  }
	  var activeval=getUrlVars()["activeval"];
	  var structure_type=getUrlVars()["structure_type"];
	  var structure_name=getUrlVars()["structure_name"];
	  
	 if(!CheckValIsNull(activeval)){
		 activeval="All";
	 }else{
         $(".activeval").val(activeval);
     }
	 if(!CheckValIsNull(structure_type)){
		 structure_type="All";
	 }else{
         $(".structure_type").val(structure_type);
     }
	 if(!CheckValIsNull(structure_name)){
		 structure_name="All";
	 }else{
         $(".structure_name").val(structure_name);
     }
	  var weekago=getRecentweekDays();
	  var starttime=getUrlVars()["starttime"];
	  var endtime=getUrlVars()["endtime"];
	 if(!CheckValIsNull(starttime)){
	          $(".starttime").val(weekago);
	           starttime=weekago;
	     }else{
	         $(".starttime").val(starttime);
	     }
	     if(!CheckValIsNull(endtime)){
	        $(".endtime").val(curDateTime());
	          endtime=curDateTime();
	     }else{
	          $(".endtime").val(endtime);
	     }  

	    $(".starttime").calendar();
	 	$(".endtime").calendar(); 
	 	var userId = getUrlVars()["userId"];

	 	//查询选择条件
		function QueryProlist(){
			$.post("./../WarningStrategyServlet",{"type":"QC"},function(data){
				  $('.structure_name').html("");
				  var strHtml = "<option value='All' name='All'>全部</option>";
				  var strType = [];
			       $.each(data, function(index,resultArr){
				       //var i = 0;
			    	   var sel="";
				       if(resultArr.ID==decodeURIComponent(structure_name)){
					       sel="selected";
				       } 
				       if(decodeURIComponent(structure_type)!="All"){
				    	   if(resultArr.nProjectType==decodeURIComponent(structure_type)){
					    	   strHtml += "<option value='"+resultArr.ID+"' "+sel+" name='"+resultArr.nProjectType+"'>"+resultArr.sProjectAbbr+"</option>";
						   }
					   }else{
						   strHtml += "<option value='"+resultArr.ID+"' "+sel+" name='"+resultArr.nProjectType+"'>"+resultArr.sProjectAbbr+"</option>";
					}
				       
			    	   
			    	   strType.push(resultArr.nProjectType);
				   })
	         	   $('.structure_name').html(strHtml);
			       var strHtml2 = "<option value='All' name='All'>全部</option>";
			       var strType2 = [];
			       for(var i=0; i < strType.length; i++){
			    	   if(strType2.indexOf(strType[i]) == -1){
			    		   strType2.push(strType[i]);
				       }
				   }
			       $.each(strType2, function(index,resultArr){
			    	   var sel="";
				       if(resultArr==decodeURIComponent(structure_type)){sel="selected";} 
			    	   strHtml2 += "<option value='"+resultArr+"' "+sel+" name='"+resultArr+"'>"+resultArr+"</option>"
				   })
				   $('.structure_type').html(strHtml2);
		     });
			$(".activeval").find("option[value="+decodeURIComponent(activeval)+"]").attr("selected",true);
			
			
		}
		
		//结构类型切换
		function changeType(){
			var options=$(".structure_type option:selected");
			var type = options.val();
			$.post("./../WarningStrategyServlet",{"type":"QC"},function(data){
				  $('.structure_name').html("");
				  var strHtml = "<option value='All' name='All'>全部</option>";
				  var strType = [];
			       $.each(data, function(index,resultArr){
			    	   var sel="";
				       if(resultArr.ID==structure_name){sel="selected";} 
				       if(type == resultArr.nProjectType){
				    	   strHtml += "<option value='"+resultArr.ID+"' "+sel+" name='"+resultArr.nProjectType+"'>"+resultArr.sProjectAbbr+"</option>"
					   }
				   })
	         	   $('.structure_name').html(strHtml);
		     });
			
		}
		
		//结构名称切换
		function changeName(){
			var options=$(".structure_name option:selected");
			var name = options.attr("name");
			$(".structure_type").find("option[name="+name+"]").attr("selected",true);
		}


		function QueryWarningInfolist(activeval, structure_type, structure_name, starttime, endtime){
			$(".waringMsgTb tbody").html("");
			tip("正在查询数据.......")
			$.post("./../WarningInfoServlet",
					{
						"qType" : "Q",
						"activeval" : activeval,
						"structure_type" : structure_type,
						"structure_name" : structure_name,
						"endtime" : endtime,
						"starttime" : starttime,
						"page" : page,
						"pageSize" : pageSize
					},
					function(data) {
						var data = data;
						totalRecords = data.totalRecord;
						var datalist = data.datalist;
						if (datalist.length > 0) {
							var hstr = "";
							$.each(
									datalist,
									function(index, list) {
										var response = '';
										var responses = '';
										var buttnCon = "";
										if (list.response == ''
												|| list.response == null || responses == undefined) {
											response = "<font color=\"#FF0000\">未处理</font>";
											responses = "";
										} else {
											response = list.response;
											responses = list.response;
										}
										var confirm = '';
										if(list.confirm == 0){
											confirm = "<font color=\"#FF0000\">未确认</font>";
											buttnCon = "<a href='javascript:confirm("+list.ID+");'><button type=\"button\" class=\"btn btn-info btn-xs activeBtn\">确认</button></a>";
										}else{
											confirm = "<font color=\"#0000FF\">确认</font>";
											buttnCon = "<a href='javascript:void(0);'><button type=\"button\" disabled='disabled' class=\"btn btn-default btn-xs activeBtn\">确认</button></a>";
										}
										if(list.handle == 0){
											handle = "<font color=\"#FF0000\">未处理</font>";
										}else{
											handle = "<font color=\"#0000FF\">处理</font>";
										}
										var buttn = "";
										if(role.indexOf("admin")>-1){
											buttn = "<a href=\"warinfoset.jsp?id="
												+ list.ID
												+ "&PrjAlias="
												+ list.PrjAlias
												+ "&wSensor="
												+ list.wSensor
												+ "&waringTime="
												+ list.waringTime
												+ "&waringMsg="
												+ list.waringMsg
												+ "&response="
												+ responses
												+ "&userId="
												+ userId
												+ "\"><button type=\"button\" class=\"btn btn-info btn-xs activeBtn\">操作</button></a>";
										}else{
											buttn = "<a href='javascript:caozuo();'><button type=\"button\" class=\"btn btn-info btn-xs activeBtn\">操作</button></a>";
										}
										
										hstr += "<tr>" + "<td>"
												+ list.PrjAlias
												+ "</td>" + "<td>"
												+ list.wSensor
												+ "</td>" + "<td>"
												+ list.sAlertLevel
												+ "</td>" + "<td>"
												+ list.waringMsg
												+ "</td>" + "<td>"
												+ list.waringTime
												+ "</td>" + "<td>"
												+ responses
												+ "</td>" + "<td id='"+list.ID+"'>"
												+ confirm
												+ "</td>" + "<td>"
												+ handle
												+ "</td>" + "<td>"
												+ buttnCon
												+ "</td>" + "<td>"
												+ buttn +"</td>"
												+ "</tr>";

									})

						} else {
							hstr += "<tr><td colspan='10' align='center'>该时段无查询数据。</td></tr>";
						}
						$(".waringMsgTb tbody").append(hstr);
						tip_close();
						
						totalPage=Math.ceil(totalRecords/pageSize);
				        kkpager.generPageHtml({
				        pno : page, total : totalPage, totalRecords:totalRecords,hrefFormer : 'warninfo',hrefLatter : '.jsp',
				        getLink : function(n){
				          return this.hrefFormer + this.hrefLatter + "?page="+n+"&activeval="+activeval+"&structure_type="+structure_type+"&structure_name="+structure_name+"&starttime="+starttime+"&endtime="+endtime;
				        },
				        lang : {
				          firstPageText : '首页',lastPageText : '末尾',prePageText : '上页',nextPageText : '下页',totalPageBeforeText : '总共',totalPageAfterText :'页',totalRecordsAfterText : '条记录',gopageBeforeText : '跳转',ButtonOkText : 'OK',gopageAfterText : '页',buttonTipBeforeText : 'OK',buttonTipAfterText :'页'
				        }});
					});
		}


		function caozuo(){
			art.dialog.tips('管理员权限');
		}
		function confirm(id){
			if(role.indexOf("admin")>-1){	
				$.post("./../WarningInfoServlet",
						{
							"qType" : "Confirm",
							"id" : id,
						},
						function(data) {
							console.log(data.datalist);
							if(data.msg == "success"){
								$("#"+id).html("<font color=\"#0000FF\">确认</font>");
							}
						}
				);
			}else{
				art.dialog.tips('管理员权限');
			}
			
		}		
		
		
		//初始化
		$(function(){
			QueryProlist();
			QueryWarningInfolist(activeval, structure_type, structure_name, starttime, endtime);
			
			$(".activeBtn").bind("click",function() {
					var activeval = $(".activeval").val();
					var structure_type = $(".structure_type").val();
					var structure_name = $(".structure_name").val();
					var starttime = $(".starttime").val();
					var endtime = $(".endtime").val();
					QueryWarningInfolist(activeval,structure_type, structure_name, starttime, endtime);
					window.location.href="?activeval="+activeval+"&structure_type="+structure_type+"&structure_name="+structure_name+"&starttime="+starttime+"&endtime="+endtime;
			})
		});

</script>

</html>
		