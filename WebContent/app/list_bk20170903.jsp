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
        状态正常结构 
      
        <p><font class="ActionCount">0</font>个</p>
     </li>
 	</ul>
   <ul class="yellostaus">
 		<li>
        黄色预警结构 
        
      
        <p><font class="unprocSum">0</font>个</p>
     </li>
 	</ul>
 	<ul class="redstaus">
 		<li>
        红色预警结构 
        
      
        <p><font class="AlarmCount">0</font>个</p>
     </li>
 	</ul>
 	
 </div>
      <div class="PrjInfo">
		     <div class="pro_img">
		         	 <ul class="proimageshw"><img src=""></ul>
		         	 <ul class="tgra">
		         	  <%
    if(StringUtil.isIn("edit",role)){out.print("<button type='button' class='btn btn-info Bgimg_edit' ><i class='fa fa-list-alt'></i> 编辑图表</button>");}
    %>
		         	 
		            
		         	 </ul>

		         	 
		            
		         	 
		         </div>
		         <div class="baseinfo">
							    <table class="table table-bordered table-hover prjinfo">
								<caption>基本信息</caption>
								
								<tbody>
<<<<<<< .mine
									<tr><td class='p1'>结构类型：</td><td class='prjtypestr'></td></tr>
	   	                             <tr><td class='p1'>结构名称：</td><td class='prjName'></td></tr>
	   	                            <tr><td class='p1'>结构地址：</td><td class='prjAddr'></td></tr>
=======
									<tr><td class='p1'>结构名称：</td><td class='prjName'></td></tr>
	   	                             <tr><td class='p1'>结构类型：</td><td class='prjtypestr'></td></tr>
	   	                            <tr><td class='p1'>结构地址：</td><td class='prjAddr'></td></tr>
>>>>>>> .r55
	   	                             <tr><td class='p1'>结构概述：</td><td class='JieGouDes'></td></tr>
<<<<<<< .mine
	   	                             <tr><td class='p1'>监测内容：</td><td class='JianceNeirong'></td></tr>
	   	                             <tr><td class='p1'>项目开始：</td><td class='startTime'></td></tr>
	   	                             <tr><td class='p1'>项目结束：</td><td class='endTime'></td></tr>
	   	                             <tr><td class='p1'>管理方：</td><td class='StatusDes'></td></tr>
	   	                             <tr><td class='p1'>监测方：</td><td class='BiaoDuan'></td></tr>
=======
	   	                             <tr><td class='p1'>监测内容：</td><td class='JianceNeirong'></td></tr>
	   	                             <tr><td class='p1' rowspan='2'><span style="margin-top:20px;float: left;">监测时间：</span></td><td class='startTime'></td></tr>
	   	                             <tr><td class='endTime'></td></tr>
	   	                             <tr><td class='p1' rowspan='2'><span style="margin-top:20px;float: left;">监测联系人</span></td><td class='manager'></td></tr>
	   	                               <tr ><td class='monitorer'></td></tr>
	   	                             
>>>>>>> .r55
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

var sensorTypeStr={"USS":"超声测距","RainGauge":"雨量","Prism":"测量机器人棱镜","Piezometer":"地下水位","LRDM":"裂缝监测","loadcell":"土压力","Inclinometer":"深部位移","GNSS":"GPS/北斗表面位移","Crackmeter":"拉线位移计","Tiltmeter":"倾度盘"};
$.post("./../PointInfo",{"op":"prjNameAndPrjInfo","prjName":prjName},function(data){

	 var data=eval("("+data+")");

	 //////解析项目类型信息
	 var prjTypeData=data[0].PrjInfo;
	 
     $.each(prjTypeData,function(index,prjs){

     	if(prjs.prjName.toLowerCase()==prjName.toLowerCase()){

     		var  Bgimg=prjs.Bgimg;
     		if(CheckValIsNull(Bgimg)){
     			$(".proimageshw").html("<img src='./../"+Bgimg+"' width='760'>");
     		}
     	}
     })
	    
	   $.each(prjTypeData,function(index,prjtype){
	   	  var prjtypestr=prjtype.prjType;
	   	  var startTime=prjtype.startTime;
	   	  startTime=CheckValIsNull(startTime)?startTime:"";
	   	   var endTime=prjtype.endTime;
	   	    endTime=CheckValIsNull(endTime)?endTime:"";
	   	  if($.trim(prjtypestr)==$.trim(prjType)){
	   	  	                  $(".prjinfo tbody").find(".prjtypestr").text(prjtypestr);
	   	  	                  $(".prjinfo tbody").find(".prjName").text(prjtype.prjAlais);
	   	  	                  $(".prjinfo tbody").find(".prjAddr").text(prjtype.prjAddr);
	   	  	                  $(".prjinfo tbody").find(".JieGouDes").text(prjtype.JieGouDes);
	   	  	                  $(".prjinfo tbody").find(".JianceNeirong").text(prjtype.JianceNeirong);
	   	  	                  $(".prjinfo tbody").find(".startTime").text("起 "+startTime);
	   	  	                  $(".prjinfo tbody").find(".endTime").text("至 "+endTime);
	   	  	                   $(".prjinfo tbody").find(".manager").text("管理方联系人:"+prjtypestr);
	   	  	                     $(".prjinfo tbody").find(".monitorer").text("监测方联系人:"+prjtypestr);
	   	  	              
	   	                 
	   	                              
	   	  	return false;

	   	  }

	   })
	   //////解析点位信息
	   var colors=["green","blue","yello","violet","red"];
	   var ptStatus=data[1].ptStatus;
	  console.log(ptStatus);
	   var sensorTypeArr=[]; //定义一个sensorType的数组，用来装载ensorType;
	   if(ptStatus.length>0){
		//console.log(ptStatus);
	   	$.each(ptStatus,function(index,Pt){
	   		             var imgX=Pt.imgX;
                    	  var imgY=Pt.imgY;
                    	  if(CheckValIsNull(imgX) && CheckValIsNull(imgY) ){
                    	  	         var  StatusVal=Pt.StatusVal;
                    	            var color="#15cc02";
								    var marker_ico_src="../images/marker_green.png";
								    switch(StatusVal){
								      case "offline":
								      marker_ico_src="../images/marker_red.png";
								      color="#ffd800";
								      break;
								      case "normal":
								      marker_ico_src="../images/marker_green.png";
								      color="#15cc02";
								      break;
								      case "lowpower":
								      marker_ico_src="../images/marker_blue.png";
								      color="#ffd800";
								      break;
								      case "alert":
								      marker_ico_src="../images/marker_orange.png";
								      color="#ff1800";
								      break;
								      case "alarm":
								      marker_ico_src="../images/marker_violet.png";
								      color="#ff1800";
								      break;
								      case "action":
								      marker_ico_src="../images/marker_deepred.png";
								      color="#ff1800";
								      break;
								      default:
								      marker_ico_src="../images/marker_green.png";
								      color="#15cc02";
								      break;
								    }
								    $(".proimageshw").append("<img src='"+marker_ico_src+"' class='pointcoord' style='left:"+imgX+"px;top:"+imgY+"px' onclick=shwgraphic('"+Pt.sensorType+"','"+Pt.SensorName+"')></i><font class='sensornamestr' style='left:"+imgX+"px;top:"+(imgY-12)+"px;color:"+color+"'>"+Pt.SensorName+"</font>");
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
                            tableHtmlStr+='<tr>'+
							                '<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'") >'+Pt.SensorName+'</a></td>'+
							                 '<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'") >'+Pt.staName+'</a></td>';
							                 
							                //'<td>'+Pt.StatusDes+'</td>';
							                if(Pt.Status=="Online"){
							                	  tableHtmlStr+='<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'") style="color:#00c818;font-weight:bold">在线</a></td>';
							                    }else{
                                                tableHtmlStr+='<td><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'") style="color:#ff160a;font-weight:bold">离线</a></td>';
							                    }
							                
							                tableHtmlStr+= '<td ><a href="javascript:void()" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'") style="float:left">'+statusDes+'</a></td>';
							                
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
function shwgraphic(sensorType,sensorname){

	var urlname=sensorType.toLowerCase();
var SensorHref=urlname+".jsp?sensorname="+sensorname+"&sensorType="+sensorType;

//window.location.href=SensorHref; //这句打开则在当前页面
art.dialog.open(SensorHref, {title: "类型："+sensorType+", 点位:"+sensorname, width: 1180,height:600,lock:true,top:0});
}

$(".Bgimg_edit").bind("click",function(){
	setCookie("prjType",prjType);
	//art.dialog.open("upload1.jsp?prjName="+prjName, {title: "上传项目图片", width: 480,height:180,lock:true});
	window.location.href="upload.jsp?prjName="+prjName;
	
}) 
</script>

 <script src="../js/footer.js"></script>

