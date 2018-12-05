<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
  <link rel="stylesheet" href="../css/kkpager.css" />
<link rel="stylesheet" href="../css/user.css" />
<script src="../js/jquery-1.11.1.min.js"></script>
  <script src="../js/config.js"></script>
<script src="../js/kkpager.min.js"></script>

 <script src="../js/artDialog.source.js"></script>
    <script src="../js/iframeTools.source.js"></script>
  <link rel="stylesheet" href="../js/skins/default.css" />
  <style>
  	.wrapper{padding: 10px; margin: 0px}
  	.warinfo_parma{width: 100%; height: 46px; margin-bottom: 10px; background: #f0fafc; padding-top: 6px; text-indent: 10px}
	.warinfo_parma *{vertical-align: middle;}
	.warinfo_parma label{padding-top: 4px}
	.warinfo_parma label,button,select{float: left; margin-right: 10px}
	/* 新增样式  */
	.warinfo_parma .activeval,.warinfo_parma .structure_type,.warinfo_parma .project_name{width: 120px; height: 32px; padding: 2px; border: 1px #ddd solid;float: left;}
	  	
  </style>
<body>
	 <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li >预警管理</li>
    <li class="active">设备预警值</li>
	</ol>


     
<div class="wrapper">

   <div class="warinfo_parma" style="width: 96%;margin-left: 2%">
     <label>结构名称：</label>
     <select class="project_name" id="project_name"></select>
     <button type="button" class="btn btn-info activeBtn" style="margin-left: 20px">&nbsp;&nbsp;查询&nbsp;&nbsp;</button>
     <button type="button" class="btn btn-info" id="addVal" style="float: right;">&nbsp;&nbsp;添加&nbsp;&nbsp;</button>
   </div>
   <div style="width: 96%;margin-left: 2%">
	<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0"  style="float:left" id="project_tb">
	  <tr bgcolor="#e0edf5">
	    <td class="project_str">&nbsp;结构名称</td>
	    <td class="sensortype_str">&nbsp;传感器类型</td>
	    <td class="Alarm_str">&nbsp;一级预警阈值</td>
	    <td class="Action_str">&nbsp;二级预警阈值</td>
	    <td class="Action_str">&nbsp;三级预警阈值</td>
	    <td class="Action_str">&nbsp;四级预警阈值</td>
	    <td class="Operation_str" align="center">&nbsp;批量设置</td>
	    <td>&nbsp;&nbsp;</td>
	  </tr>
	</table>
	
	<div id="kkpager" style="padding-top: 20px;"></div>
	</div>
 </div> 
</body>
</html>
<script type="text/javascript">
	var pageSize=10;
	var totalPage;
	//var totalRecords;
	var page = getUrlVars()["page"];
	  if(!page){
	    page = 1;
	  }
	  var nProjectID=getUrlVars()["nProjectID"];
	  if(!CheckValIsNull(nProjectID)){
		  nProjectID="All";
     }else{
         $(".project_name").val(nProjectID);
     }
	//查询全部项目
	function QueryProlist(){
		$.post("./../WarningStrategyServlet",{"type":"QC"},function(data){
			  $('.project_name').html("");
			  var strHtml = "<option value='All' >全部</option>";
		       $.each(data, function(index,resultArr){
		    	   var sel="";
			       if(resultArr.ID==decodeURIComponent(nProjectID)){sel="selected";} 
		    	   strHtml += "<option value='"+resultArr.ID+"' "+sel+">"+resultArr.sProjectAbbr+"</option>"
		       })
         	   $('.project_name').html(strHtml);
	     }); 
	}

	function QueryWarningStrategylist(nProjectID){
		
		$('.structure').remove();
		tip("正在查询数据.......");
		var htmlStr = "";
		$.post("./../WarningStrategyServlet",{"type":"C","page":page,"pageSize":pageSize,"nProjectID":nProjectID},function(data){
			var totalRecords=data.totalRecord;
            var dataList=data.array;
            if(dataList.length>0){
    			$.each(dataList,function(index,result){
    				//全部阀值
    				var fazhi = result.dTopLevelValue+"|"+result.dMediumLevelValue+"|"+result.dLowLevelValue+"|"+result.dMicroLevelValue;
    				var name = result.sProjectAbbr+":"+result.sSensorType;
    				htmlStr += "<tr class='structure list"+result.nProjectID+"' bgcolor='#eff5f5'>"+
    				"<td>&nbsp;"+result.sProjectAbbr+"</td>"+
    	            "<td>&nbsp;"+result.sSensorTypeName+"</td>"+
    	             "<td>&nbsp;"+result.dTopLevelValue+"</td>"+
    	             "<td>&nbsp;"+result.dMediumLevelValue+"</td>"+
    	             "<td>&nbsp;"+result.dLowLevelValue+"</td>"+
    	             "<td>&nbsp;"+result.dMicroLevelValue+"</td>"+
    	             "<td align='center'>&nbsp;<a href='javascript:void(0);' onclick=editdescription('"+result.ID+"','"+result.sSensorType+"','"+name+"','"+fazhi+"','"+result.nProjectID+"','all') class='btn btn-warning btn-xs'>编辑</a>&nbsp;<a href='javascript:void(0);' onclick=addchildVal('"+result.nProjectID+"','"+result.sProjectAbbr+"','"+result.sSensorType+"','"+fazhi+"') class='btn btn-info btn-xs'>添加</a>&nbsp;<a href='javascript:void(0);' onclick=deleteAll('"+result.nProjectID+"','"+result.sSensorType+"') class='btn btn-danger btn-xs'>删除</a></td>"+
    	             "<td>&nbsp;<a href='javascript:void(0)' onclick=slidept('"+result.sProjectAbbr+"','"+result.nProjectID+"','"+result.sSensorType+"','"+result.ID+"')><img src='../images/slideup.png' id='img"+result.ID+"'></a></td>"+
    	              "</tr>"+
    	              "<tr bgcolor='#ffffff'><td colspan='9' id='list"+result.ID+"'  class='sensornamelist structure' style='padding:10px; display: none;'></tr>";
    			});

    		}else{
    			htmlStr += "<tr><td colspan='9' align='center'>无查询数据。</td></tr>";
    		}
    		htmlStr+="</table>"; 
    		$("#project_tb").append(htmlStr);
    		tip_close();

	        totalPage=Math.ceil(totalRecords/pageSize);
	        kkpager.generPageHtml({
	        pno : page, total : totalPage, totalRecords:totalRecords,hrefFormer : 'warnval',hrefLatter : '.jsp',
	        getLink : function(n){
	          return this.hrefFormer + this.hrefLatter + "?page="+n+"&nProjectID="+nProjectID;
	        },
	        lang : {
	          firstPageText : '首页',lastPageText : '末尾',prePageText : '上页',nextPageText : '下页',totalPageBeforeText : '总共',totalPageAfterText :'页',totalRecordsAfterText : '条记录',gopageBeforeText : '跳转',ButtonOkText : 'OK',gopageAfterText : '页',buttonTipBeforeText : 'OK',buttonTipAfterText :'页'
	        }});

		})
	}
	
$(function(){
	QueryProlist();
	QueryWarningStrategylist(nProjectID);
})


var role = "";
$.post("./../UserInfo",{oper:"userinfo"},function(data){
    if(CheckValIsNull(data)){
		role=data.role;
    }
});



//添加测点
function addchildVal(ID,sProjectAbbr,sSensorType,fazhi){
	if(role.indexOf("admin")>-1){
		art.dialog.open('warnvaladdchild.jsp?ID='+ID+'&abbr='+sProjectAbbr+'&type='+sSensorType+'&fazhi='+fazhi, {title: sProjectAbbr+":"+sSensorType+"添加", width: 600,height: 500, lock:true});
	}else{
		art.dialog.tips('管理员权限');
	}
}
//删除顶级
function deleteAll(nProjectID,sSensorType){
	if(role.indexOf("admin")>-1){
		var data = {
				type : "deleteAll",
				nProjectID : nProjectID,
				sSensorType : sSensorType
		};
		art.dialog.confirm('确定要删除这条记录吗?', function () {
			$.post("./../WarningStrategyServlet",data,function(data){
				var data = eval(data);
				if(data.msg == "success"){
					tip("删除成功",true);
					setTimeout(function(){
	            		location.reload();
	           		},1000);
				}else{
					tip("删除失败",true);
				}
			});
		 },function(){
		      art.dialog.tips('删除取消');
		 });
	}else{
		art.dialog.tips('管理员权限');
	}
}


//添加结构物
$("#addVal").click(function(){
	if(role.indexOf("admin")>-1){
		art.dialog.open('warnvaladd.jsp', {title: "添加", width: 600,height: 500, lock:true});
	}else{
		art.dialog.tips('管理员权限');
	}
});

$(".activeBtn").click(function(){
	var data = $("#project_name").val();
	QueryWarningStrategylist(data);
	window.location.href="?nProjectID="+data;
});


//子类下拉显示
function slidept(sProjectAbbr,nProjectID,sSensorType,ID){
	var htmlStr = "";
	$.post("./../WarningStrategyServlet",{"type":"S","nProjectID":nProjectID,"sSensorType":sSensorType},function(data){
		var data = eval(data);
		if(data.length > 0){
			if($("#list"+ID).is(":hidden")){
				$("#list"+ID).show();
				$("#img"+ID).attr({"src":"../images/slidedown.png"});
			}else{
				$("#list"+ID).hide();
				$("#img"+ID).attr({"src":"../images/slideup.png"});
			}
		}else{
			tip('未查询到数据!...',true);
		}
		$("#list"+ID).html("");
		var htmlStr = "<table class='structure'  width='92%'  align='center' cellpadding=1 cellspacing=0  class='sensorsTable'><tr bgcolor='#eff5f5'>"+
        "<td >&nbsp;设备名</td>"+
        "<td>&nbsp; 一级预警阈值</td>"+
        "<td>&nbsp; 二级预警阈值</td>"+
        "<td>&nbsp; 三级预警阈值</td>"+
        "<td>&nbsp; 四级预警阈值</td>"+
        "<td align=center>&nbsp;设备设置</td>"+
        "</tr>";
		$.each(data,function(index,result){
			//全部阀值
			var fazhi = result.dTopLevelValue+"|"+result.dMediumLevelValue+"|"+result.dLowLevelValue+"|"+result.dMicroLevelValue;
			var name = sProjectAbbr+":"+result.sSensorType+":"+result.sSiteName;
			htmlStr += "<tr bgcolor='#f9f9f9'>"+
             "<td >&nbsp;"+result.sSiteName+"</td>"+
             "<td>&nbsp;"+result.dTopLevelValue+"</td>"+
             "<td>&nbsp;"+result.dMediumLevelValue+"</td>"+
             "<td>&nbsp;"+result.dLowLevelValue+"</td>"+
             "<td>&nbsp;"+result.dMicroLevelValue+"</td>"+
             "<td align=center>&nbsp;<a href='javascript:void(0);' onclick=editdescription('"+result.ID+"','"+result.sSensorType+"','"+name+"','"+fazhi+"','"+result.nProjectID+"','single')  class='btn btn-warning btn-xs'>编辑</a>&nbsp;<a href='javascript:void(0);' onclick=deleteSingle('"+result.ID+"') class='btn btn-danger btn-xs'>删除</a></td>"+
             "</tr>";
             
		});
		$("#list"+ID).append("</table>"+htmlStr);
	});
}

//删除设备
function deleteSingle(id){
	if(role.indexOf("admin")>-1){
		var data = {
				type : "delete",
				id : id,
		};
		art.dialog.confirm('确定要删除这条记录吗?', function () {
			$.post("./../WarningStrategyServlet",data,function(data){
				var data = eval(data);
				if(data.msg == "success"){
					tip("删除成功",true);
					setTimeout(function(){
	            		location.reload();
	           		},1000);
				}else{
					tip("删除失败",true);
				}
			});
		 },function(){
		      art.dialog.tips('删除取消');
		 });
	
	}else{
		art.dialog.tips('管理员权限');
	}
}

//阀值编辑
function editdescription(id,type,name,fazhi,proid,batch){
	if(role.indexOf("admin")>-1){
		var width;
	   if(type=="GNSS"){
		width=600;
	  	}else{
	    width=600;
	   
	  	}
	   //window.location.href='editptinfo.jsp?act='+sen+'&S_Name='+SensorName;
	   art.dialog.open('editptinfo.jsp?id='+id+'&type='+type+'&name='+name+'&fazhi='+fazhi+'&proid='+proid+'&batch='+batch, {title: "编辑("+name+")", width: width,height:400, lock:true});
	}else{
		art.dialog.tips('管理员权限');
	}
   
 } 

		
</script>
