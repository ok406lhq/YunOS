<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/bootstrap.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/warnset.css" />
<link rel="stylesheet" href="../css/kkpager.css" />
 <script src="../js/jquery-1.11.1.min.js"></script>
  <script src="../js/config.js"></script>
 <script src="../js/artDialog.source.js"></script>
    <script src="../js/iframeTools.source.js"></script>
     <script src="../js/kkpager.min.js"></script>
  <link rel="stylesheet" href="../js/skins/default.css" />
  <style>
  	.wrapper{padding: 10px; margin: 0px}
  	.warinfo_parma{width: 100%; height: 46px; margin-bottom: 10px; background: #f0fafc; padding-top: 6px; text-indent: 10px}
	.warinfo_parma *{vertical-align: middle;}
	.warinfo_parma label{padding-top: 4px}
	.warinfo_parma label,button,select{float: left; margin-right: 10px}
	/* 新增样式  */
	.warinfo_parma .activeval,.warinfo_parma .structure_type,.warinfo_parma .project_name{width: 120px; height: 32px; padding: 2px; border: 1px #ddd solid;float: left;}
	.opener{
		text-align: center;
	}
  </style>
<body>
	 <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li ><a href="#">预警管理</a></li>
    <li class="active">接警人配置</li>
   
</ol>


<div class="wrapper">
   <div class="warinfo_parma" style="width: 96%;margin-left: 2%">
     <label>结构名称：</label>
     <select class="project_name" id="project_name" onchange="projectChange()"><option value='All' selected='selected'>全部</option></select>
     <label class="hidden">监测点：</label>
     <select class="project_name hidden" id="site_name"><option value='All' selected='selected'>全部</option></select> 
     <button type="button" class="btn btn-info activeBtn" id="searchbtn" style="margin-left: 20px">&nbsp;&nbsp;查询&nbsp;&nbsp;</button>
     <button type="button" class="btn btn-danger activeBtn" id=deleteAll style="float: right;">批量删除</button>
   	 <button type="button" class="btn btn-info activeBtn" id="addbtn" style="float: right;">&nbsp;&nbsp;添加&nbsp;&nbsp;</button>
   	 
   </div>
   <div style="width: 96%;margin-left: 2%;">
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th><input type="checkbox" id="select" title="全选"></th>
	      <th>结构名称</th>
	      <th>接警人</th>
	      <th>联系电话</th>
	      <th>邮箱</th>
	      <th>预警类型</th>
	      <th>预警间隔</th>
	      <th class='opener'>操作</th>
	    </tr>
	  </thead>
	  <tbody id="bodycontent">
	  
	  </tbody>
	</table>
	</div>
	<div id="kkpager"></div>
 </div> 



</body>
</html>
<script type="text/javascript">

	var role = "";
	$.post("./../UserInfo",{oper:"userinfo"},function(data){
	    if(CheckValIsNull(data)){
			role=data.role;
	    }
	});

	var projectArray = [];
	$(function(){
		
		
		
		var globalProListArr = [];//全局数据
		/* 初始化 结构类型, 结构名-动态下拉框   张庭伟  2018-06-15 16:40*/
		$.post("../myprojects",{prjName:""},function(data){
	         $('.project_name').html("");
	         var data=eval("("+data+")");
	         
			 var myproject=[];
	         var projectListArr=data[0].PrjInfo;
	         globalProListArr = projectListArr;//赋值全局
	         
	         var prjTypeArr=[];
	         for(var i in projectListArr){
	             prjTypeArr.push(projectListArr[i].prjType);
	         }
	         prjTypeArr= uniqueArray(prjTypeArr).reverse();
	         var s=prjTypeArr.length;
	         var structureName = [];//预警名字
	         for(var i=0;i<s;i++){
	             $.each(projectListArr,function(index,prj){
		              if(prjTypeArr[i]==prj.prjType){
							structureName.push(prj.prjID+"|"+prj.prjAlais);
							projectArray.push(prj.prjID+"|"+prj.prjAlais);
							//console.log(prj.prjAlais+"--"+prj.prjID);
		              }
	              });
	         }
	         
	         var structure_name = "<option value='All'>全部</option>";
	         
	         for(var i = 0; i < structureName.length; i++){
	        	 var project_name = structureName[i].split("|");
	        	 var sel="";
	        	 //alert(project_name[0]+"**"+proid);
			     if(project_name[0]==proid){sel="selected";} 
	        	 structure_name += "<option value='"+project_name[0]+"' "+sel+">"+project_name[1]+"</option>";
	         }
	         $('.project_name').html(structure_name);
	         //$(".project_name").find("option[value="+proid+"]").attr("selected",true);
		});
		
		
		$("#addbtn").click(function(){
			if(role.indexOf("admin")>-1){
				var projectStr = projectArray.toString();
				  var href="warnset.jsp?projectStr="+projectStr;
				  art.dialog.open(href, {title: "添加数据", width: 780,height:520,lock:true,top:0});
			}else{
				art.dialog.tips('管理员权限');
			}
		});
		
		$("#searchbtn").click(function(){
			var proid=$("#project_name").val();
			/* window.location.href="?proid="+proid */
			selectInfo(proid,page,pageSize);
			window.location.href="?proid="+proid;
			
		});
		
		 var pageSize=8;
		 var totalPage;
		 var totalRecords;
		 var page = getUrlVars()["page"];
		 if(!page){
		    page = 1;
		 }
		 var str = $("#project_name").val();
		 var proid = getUrlVars()["proid"];
		 if(!CheckValIsNull(proid)){
			 $("#project_name").val(str);
			 proid = str;
			 
		 }
		/* 初始化 */
		var htmlStr="";
		tip("正在查询数据.......");
		selectInfo(proid,page,pageSize);
		
	});
	
	function selectInfo(proid,page,pageSize){
		$("#bodycontent").html("");
		$.post("./../WarningUserSetServlet",{"type":"C","page":page,"pageSize":pageSize,"proid":proid},function(data){
			var arrSensor = eval(data.arrSensor);
			var arrProject = eval(data.arrProject);
			totalRecords = eval(data.totalRecords);
			//目录
			var data = eval(data.array);
			var htmlStr="";
			if(data.length>0){
				$.each(data,function(index,result){
					var sAlertLevel = "&nbsp;";
					if(result.sAlertLevel==1){
						sAlertLevel = "一级预警";
					}else if(result.sAlertLevel==2){
						sAlertLevel = "二级预警";
					}else if(result.sAlertLevel==3){
						sAlertLevel = "三级预警";
					}else if(result.sAlertLevel==4){
						sAlertLevel = "四级预警";
					}else{
						sAlertLevel = "&nbsp;";
					}
					var sTel = result.sTel;
					if(result.sTel == "" || result.sTel == null || result.sTel == undefined){
						sTel = "&nbsp;";
					}
					var sEMail = result.sEMail;
					if(result.sEMail == "" || result.sEMail == null || result.sEMail == undefined){
						sEMail = "&nbsp;";
					}
					var nMsgHourInterval = result.nMsgHourInterval;
					if(nMsgHourInterval >= 60){
						var minute = nMsgHourInterval % 60;//分钟
						var hour = parseInt(nMsgHourInterval/60) 
						nMsgHourInterval = hour+" 小时 "+minute+" 分钟"
					}else{
						nMsgHourInterval = nMsgHourInterval+" 分钟";
					}
					var sSendChannel = result.sSendChannel
					if(sSendChannel == "sms"){
						sSendChannel = 1;
					}else if(sSendChannel == "email"){
						sSendChannel = 2;
					}else{
						sSendChannel = 3;
					}
					htmlStr += "<tr class='data"+result.ID+"'>"+
					"<td><input type='checkbox' name='items' value='"+result.ID+"' /></td>"+
					"<td>&nbsp;"+result.sProjectAbbr+"</td>"+
		             "<td>&nbsp;"+result.sUserName+"</td>"+
		             "<td>&nbsp;"+sTel+"</td>"+
		             "<td>&nbsp;"+sEMail+"</td>"+
		             "<td>&nbsp;"+sAlertLevel+"</td>"+
		             "<td>&nbsp;"+nMsgHourInterval+"</td>"+
		             "<td class='opener'><a class='btn btn-warning btn-xs' onclick='editInfo("+result.ID+","+result.nUserID+","+result.nProjectID+","+result.nMsgHourInterval+","+sSendChannel+","+result.sAlertLevel+")'>编辑</a>&nbsp;<a class='btn btn-danger btn-xs' onclick='deleteSingle("+result.ID+")'>删除</a></td>"+
		             "</tr>";
				});
				
			}else{
				htmlStr += "<tr><td colspan='8' align='center'>无查询数据。</td></tr>";
			}
			$("#bodycontent").append(htmlStr);
			tip_close();

			totalRecords = totalRecords.total;
			totalPage = Math.ceil(totalRecords / pageSize);
			kkpager.generPageHtml({
				pno : page,
				total : totalPage,
				totalRecords : totalRecords,
				hrefFormer : 'warnlist',
				hrefLatter : '.jsp',
				getLink : function(pno) {
					return this.hrefFormer
							+ this.hrefLatter + "?page="+ pno + "&pageSize=" + pageSize
				},
				lang : {
					firstPageText : '首页',
					lastPageText : '末尾',
					prePageText : '上页',
					nextPageText : '下页',
					totalPageBeforeText : '总共',
					totalPageAfterText : '页',
					totalRecordsAfterText : '条记录',
					gopageBeforeText : '跳转',
					ButtonOkText : 'OK',
					gopageAfterText : '页',
					buttonTipBeforeText : 'OK',
					buttonTipAfterText : '页'
				}
			});
			 //结构物
			/*  var htmlStr = "";
			$("#project_name").html(""); 
			$.each(arrProject,function(index,result){
				var data = result.ID+"|"+result.sProjectAbbr;
				projectArray.push(data);
				htmlStr += "<option value='"+result.ID+"'>"+result.sProjectAbbr+"</option>"
			});
			$("#project_name").append(htmlStr+"<option value='All' >全部</option>");  */
			
			
		});
		
		/* $("#project_name").find("option[value='"+proid+"']").attr("selected",true); */
	}
	
	
	function projectChange(){
		var val = $("#project_name").val();
		//$("#project_name").find("option[value='"+val+"']").attr("selected",true);
	}

	function editInfo(ID,nUserID,nProjectID,nMsgHourInterval,sSendChannel,sAlertLevel){
		if(role.indexOf("admin")>-1){
			 var projectStr = projectArray.toString();
			  var href="warnset.jsp?projectStr="+projectStr+"&ID="+ID+"&nUserID="+nUserID+"&nProjectID="+nProjectID+"&nMsgHourInterval="+nMsgHourInterval+"&sSendChannel="+sSendChannel+"&sAlertLevel="+sAlertLevel;
			  art.dialog.open(href, {title: "编辑数据", width: 780,height:520,lock:true,top:0});
		}else{
			art.dialog.tips('管理员权限');
		}
	}
	
	function deleteSingle(id){
		if(role.indexOf("admin")>-1){
			art.dialog.confirm('确定要删除这条记录吗?', function () {
	            $.post("./../WarningUserSetServlet",{"type":"delete","ID":id},function(data){
	                  var msg=data.msg;
	                  if(msg=="success"){
	               	   tip("删除成功！",true);
	               	   $(".data"+id).remove();
	                  }else{
	               	   tip("操作失败",true);
	                  }
	            });
	     },function(){
	          art.dialog.tips('删除取消');
	     });
			
		}else{
			art.dialog.tips('管理员权限');
		}
	}
	
	
	//复选框全选与全不选
	$(function() { 
		$("#select").click(function() {
			var nn = $("#select").is(":checked");
			if(nn == true) {
				 var namebox = $("input[name^='items']");  //获取name值为boxs的所有input
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=true;    //js操作选中checkbox
				 }
			 }
			 if(nn == false) {
				 var namebox = $("input[name^='items']");
				 for(var i = 0; i < namebox.length; i++) {
					 namebox[i].checked=false;
				 }
			 }
		});
	});
	
	//删除复选框选中的条目
	$(function(){
		$("#deleteAll").click(function(){
			if(role.indexOf("admin")>-1){
				var chk_value =[]; 
				$('input[name="items"]:checked').each(function(){ //遍历复选框
					chk_value.push($(this).val()); 
				}); 
				if(chk_value.length==0){
					tip("你还没有选择任何内容！", true);
					return false;
				}
				var single = chk_value.toString();
				var arr = single.split(",");
				art.dialog.confirm('确定要删除选中记录吗?', function () {
		            $.post("./../WarningUserSetServlet",{"type":"deleteAll","chk_value":chk_value.toString()},function(data){
		                  var msg=data.msg;
		                  if(msg=="success"){
		               	   tip("删除成功！",true);
		               	   for (var i = 0; i < arr.length; i++) {
		               		$(".data"+arr[i]).remove();
						   }
		               	   
		                  }else{
		               	   tip("操作失败",true);
		                  }
		            });
		            
			     },function(){
			          art.dialog.tips('删除取消');
			     });
				
			}else{
				art.dialog.tips('管理员权限');
			}
			

		});
	});
</script>
