<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
    
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/app.css" />

 <script src="../js/jquery-1.11.1.min.js"></script>
  <script src="../js/config.js"></script>
  <script src="../js/artDialog.source.js"></script>
    <script src="../js/iframeTools.source.js"></script>
     <script src="../js/masonry-docs.min.js"></script>
  <link rel="stylesheet" href="../js/skins/default.css" />
</head>
<body>

	 <ol class="breadcrumb">
    <li><a href="slope.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li><a href="slope.jsp" class="prjTypeStr">边坡</a></li>
    <li class="active prjNameStr">pt01</li>
</ol>

     <div class="wrapper"  style="margin-top: -15px">

     <div class="prjstatus">
     
 	<ul class="normalstaus">
 		 <li>
        状态正常监测点
      
        <p><font class="normalcount">0</font>个</p>
     </li>
 	</ul>
 	<ul class="offlinestaus">
 		 <li>
        离线状态监测点
      
        <p><font class="offlinecount">0</font>个</p>
     </li>
 	</ul>
   <ul class="yellostaus">
 		<li>
        黄色预警监测点
        
      
        <p><font class="alarmcount">0</font>个</p>
     </li>
 	</ul>
 	<ul class="redstaus">
 		<li>
        红色预警监测点
        
      
        <p><font class="actioncount">0</font>个</p>
     </li>
 	</ul>
 	
 </div> 
      <div class="PrjInfo">
		     <div class="pro_img">
		         	 <ul class="proimageshw"><img src=""></ul>

		         	 <ul class="tgra">
		         	  <%
    if(StringUtil.isIn("edit",role)){out.print("<button type='button' class='btn btn-info Bgimg_edit' ><i class='fa fa-list-alt'></i> 编辑图表及信息</button>");}
    %>
		         	
		            
		         	 </ul>

		         	  <div class="ico_v"><img src="../images/ico_v.png" width="720"></div>
		            
		         	 
		         </div>
		         <div class="baseinfo">
							    <table class="table table-bordered table-hover prjinfo">
								<caption>结构基本信息</caption>
								
								<tbody>
									<tr><td class='p1'>结构名称</td><td class='prjName'></td></tr>
	   	                             <tr><td class='p1'>结构类型</td><td class='prjtypestr'></td></tr>
	   	                            <tr><td class='p1'>结构地址</td><td class='prjAddr'></td></tr>
	   	                             <tr><td class='p1'>结构概述</td><td class='JieGouDes'></td></tr>
	   	                             <tr><td class='p1'>监测内容</td><td class='JianceNeirong'></td></tr>
	   	                             <tr><td class='p1' rowspan='2'><span style="margin-top:20px;float: left;">监测时间</span></td><td class='startTime'></td></tr>
	   	                             <tr><td class='endTime'></td></tr>
	   	                             <tr><td class='p1' rowspan='2'><span style="margin-top:20px;float: left;">监测联系人</span></td><td class='manager'></td></tr>
	   	                               <tr ><td class='monitorer'></td></tr>
	   	                             
								</tbody>
							</table>

		         </div>
         </div>
         <!--表面位移监测 和深度位移监测 表数据-->
         <div class="pointdatalist"></div>
         <!--雨量监测 和预警监测 表数据 结束-->
     </div>


</body>
</html>
<script type="text/javascript">
var prjAlias=decodeURIComponent(getUrlVars()['prjAlais']);
var prjName=getUrlVars()['prjName'];
$(".prjNameStr").text(prjAlias);
setCookie("prjName",prjName);
setCookie("prjAlias",prjAlias);
var prjType=decodeURIComponent(getUrlVars()['prjType']); //获取url项目类型
$(".prjTypeStr").text(prjType);
var statusDesArr=[];
//var sensorTypeStr={"USS":"超声测距","RainGauge":"雨量","Prism":"测量机器人棱镜","Piezometer":"地下水位","LRDM":"裂缝监测","loadcell":"土压力","Inclinometer":"深部位移","GNSS":"GPS/北斗表面位移","Crackmeter":"拉线位移计"};
$.post("./../PointInfo",{"op":"prjNameAndPrjInfo","prjName":prjName},function(data){

	 var data=eval("("+data+")");
	 console.log("data:"+data)
	 //////解析项目类型信息
	 var prjTypeData=data[0].PrjInfo;
	 var ptSummary=data[2].ptSummary;
	
	var normal=ptSummary[0].normal;
	var offline=ptSummary[1].offline;
	var alarm=ptSummary[2].alarm;
	var action=ptSummary[3].action;
	$(".normalcount").text(normal);$(".offlinecount").text(offline);$(".alarmcount").text(alarm);$(".actioncount").text(action);
     $.each(prjTypeData,function(index,prjs){

     	if(prjs.prjName.toLowerCase()==prjName.toLowerCase()){

     		var  Bgimg=prjs.Bgimg;
     		if(CheckValIsNull(Bgimg)){
     			$(".proimageshw").html("<img src='"+Bgimg+"' width='760'>");
     		}
     	}
     })
	    
	   $.each(prjTypeData,function(index,prjtype){
	   	  var prjtypestr=prjtype.prjType;
	   	  var startTime=prjtype.startTime;
	   	  startTime=CheckValIsNull(startTime)?startTime:"";
	   	   var endTime=prjtype.endTime;
	   	    endTime=CheckValIsNull(endTime)?endTime:"";
	   	  if($.trim(prjtype.prjName)==$.trim(prjName)){

	   	  	                  $(".prjinfo tbody").find(".prjtypestr").text(prjtypestr);
	   	  	                  $(".prjinfo tbody").find(".prjName").text(prjtype.prjAlais);
	   	  	                  $(".prjinfo tbody").find(".prjAddr").text(prjtype.prjAddr);
	   	  	                  $(".prjinfo tbody").find(".JieGouDes").text(prjtype.JieGouDes);
	   	  	                  $(".prjinfo tbody").find(".JianceNeirong").text(prjtype.JianceNeirong);
	   	  	                  $(".prjinfo tbody").find(".startTime").text("起 "+startTime);
	   	  	                  $(".prjinfo tbody").find(".endTime").text("至 "+endTime);
	   	  	                   $(".prjinfo tbody").find(".manager").text(prjtype.lxr1);
	   	  	                     $(".prjinfo tbody").find(".monitorer").text(prjtype.lxr2);
	   	  	              
	   	                 
	   	                              
	   	  	return false;

	   	  }

	   })
	   //////解析点位信息
	   var colors=["green","blue","yello","violet","red"];
	   var ptStatus=data[1].ptStatus;
	 
	   var sensorTypeArr=[]; //定义一个sensorType的数组，用来装载ensorType;
	   if(ptStatus.length>0){
		
	   	$.each(ptStatus,function(index,Pt){
	   		             var imgX=Pt.imgX;
                    	  var imgY=Pt.imgY;
                    	  var sensorType=Pt.sensorType;
                    	  sensorType=sensorType.toLowerCase();
                    	  if(CheckValIsNull(imgX) && CheckValIsNull(imgY) ){
                    	  	         var  StatusVal=Pt.Status;
                    	  	       if(CheckValIsNull(StatusVal)){StatusVal=StatusVal.toLowerCase()}

                    	            var color="#666";
								    var marker_ico_src="../images/marker_green.png";
								    switch(StatusVal){
								      case "offline":
								      marker_ico_src="../images/"+sensorType+"_o.png";
								 
								      break;
								      case "online":
								      marker_ico_src="../images/"+sensorType+"_g.png";
								
								      break;
								      case "lowpower":
								      marker_ico_src="../images/marker_blue.png";
								     
								      break;
								      case "alert":
								      marker_ico_src="../images/marker_orange.png";
								     
								      break;
								      case "alarm":
								       marker_ico_src="../images/"+sensorType+"_y.png";
								     
								      break;
								      case "action":
								       marker_ico_src="../images/"+sensorType+"_r.png";
								    
								      break;
								      default:
								       marker_ico_src="../images/"+sensorType+"_g.png";
								     
								      break;
								    }
								    $(".proimageshw").append("<img src='"+marker_ico_src+"' class='pointcoord' style='left:"+imgX+"px;top:"+imgY+"px' onclick=shwgraphic('"+sensorType+"','"+Pt.SensorName+"')></i><font class='sensornamestr' style='left:"+imgX+"px;top:"+(imgY-19)+"px;color:"+color+"'>"+Pt.SensorName+"</font>");
                             /*
                    	  	$(".proimageshw").append("<i class='fa fa-flag pointcoord' style='left:"+imgX+"px;top:"+imgY+"px' onclick=shwgraphic('"+Pt.sensorType+"','"+Pt.SensorName+"')></i><font class='sensornamestr' style='left:"+imgX+"px;top:"+(imgY-12)+"px'>"+Pt.SensorName+"</font>");
                    	  	*/
                    	  	
                    	  }
	   		 sensorTypeArr.push(Pt.sensorType);
	   	})

	   sensorTypeArr=uniqueArray(sensorTypeArr);//去除重复的元素。

	 	    for(var i=0; i<sensorTypeArr.length;i++){ 
	 	    	var tableHtmlStr="";
	 	    	var CnSensorType="";
	 	    	 for(k in sensorTypeStr){
	 	    	
                      if(k.toLowerCase()==sensorTypeArr[i].toLowerCase()){
                    	 
                      	CnSensorType=sensorTypeStr[k];
                      	
                      }
	 	    	 }
	 	    	
	 	    	 // rqliao这里请按照每一种类型设备单独设计表格头，因为不同类型设备表头不一样，
	 	    	// 如GPS,则ref1值改为 北向位移(毫米),ref2值改为 东向位移(毫米),垂直向位移(毫米),
	 	    	// 裂缝监测，则要ref1值改为，位移量（毫米），ref2,ref3去掉--!>
	 	    	 
	 	    	      tableHtmlStr+='<div class="pro_data_table_list">'+
	         	                   '<table class="table table-bordered table-hover surface">'+
					               '<caption>'+CnSensorType+'监测</caption>'+
					                '<thead>'+
						            '<tr>'+
							        '<th width="10%" style="white-space: nowrap;text-overflow:ellipsis; overflow:hidden">设备名</th>'+
							        '<th width="12%">测站名</th>'+
							        '<th width="8%">状态</th>';
							        
							         //'<th>状态描述</th>'+
							       tableHtmlStr+= '<th>数据</th>'+
						            '</tr>'+
					                '</thead>'+
					                '<tbody>';
	 	    	 
				
                    $.each(ptStatus,function(index,Pt){

			 	   		 if(sensorTypeArr[i]==Pt.sensorType){
                             var statusDes=Pt.StatusDes;
                             if(!CheckValIsNull(statusDes)){
                            	 statusDes="";
                             }
                             statusDesArr.push(statusDes);
                            tableHtmlStr+='<tr>'+
							                '<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'",'+index+') >'+Pt.SensorName+'</a></td>'+
							                 '<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'",'+index+') >'+Pt.staName+'</a></td>';
							                 
							                //'<td>'+Pt.StatusDes+'</td>';
							                if(Pt.Status=="Online"){
							                	  tableHtmlStr+='<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'",'+index+') style="color:#00c818;font-weight:bold">在线</a></td>';
							                    }else{
                                                tableHtmlStr+='<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'",'+index+') style="color:#ff160a;font-weight:bold">离线</a></td>';
							                    }
							                
							                tableHtmlStr+= '<td ><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'",'+index+') style="float:left">'+statusDes+'</a></td>';
							                
						             tableHtmlStr+='</tr>';
			 	   		 }
			 	   	});
                   
                    tableHtmlStr+'</tbody></table></div>';
                    
                    $(".pointdatalist").append(tableHtmlStr);
                  
                    var $container = $('.pointdatalist');
                    $container.imagesLoaded(function(){
                    $container.masonry({
                    itemSelector:'.pro_data_table_list',
                    gutter:0,
                    isAnimated: true,
                    });
                     });
                    /*
                    var h=$(document).height();
					var right = $(window.parent.document).find("#right");
					right.height(h+20);
					*/
	 	    }
	   }

})
function shwgraphic(sensorType,sensorname,index){
    setCookie("statusDes",statusDesArr[index]);
	var urlname=sensorType.toLowerCase();
var SensorHref=urlname+".jsp?sensorname="+sensorname+"&sensorType="+sensorType;

//window.location.href=SensorHref;
art.dialog.open(SensorHref, {title: "类型："+sensorType+", 点位:"+sensorname, width: 1180,height:600,lock:true,top:0});
}

$(".Bgimg_edit").bind("click",function(){
	setCookie("prjType",prjType);
	//art.dialog.open("upload1.jsp?prjName="+prjName, {title: "上传项目图片", width: 480,height:180,lock:true});
	window.location.href="upload.jsp?prjName="+prjName;
	
}) 
</script>

 <script src="../js/footer.js"></script>

