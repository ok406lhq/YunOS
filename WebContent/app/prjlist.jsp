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
<link rel="stylesheet" href="../js/skins/default.css" />
<link rel="stylesheet" href="../css/jquery-ui-v1.10.4.css">
<link rel="stylesheet" href="../css/jquery-ui-v1.10.4-style.css">
<link rel="stylesheet" href="../css/jquery.page.css">

<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/config.js"></script>
<script src="../js/artDialog.source.js"></script>
<script src="../js/iframeTools.source.js"></script>
<script src="../js/masonry-docs.min.js"></script>
<script src="../js/convertToPinyin.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.page.js?v=1.1"></script>

</head>
<body>

	 <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li><a href="slopelist.jsp" class="prjTypeStr">边坡</a></li>
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
   <ul class="redstaus">
 		<li>
        一级预警监测点
        
      
        <p><font class="alarmcount">0</font>个</p>
     </li>
 	</ul>
 	<ul class="yellostaus">
 		<li>
        二级预警监测点
        <p><font class="actioncount">0</font>个</p>
     </li>
 	</ul>
 	<ul class="lightYellow" style="background-color: #f5fb58">
 		<li>
        三级预警监测点
        <p><font class="sanji">0</font>个</p>
     </li>
 	</ul>
 	<ul  style="background-color: #62c9e6">
		<li>四级预警监测点
			<p>
				<font class="fourLevelWarn">0</font>个
			</p>
		</li>
	</ul>
	<ul>
 		<li><iframe name="weather_inc" src="" width="440" height="70" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></li>
 	</ul> 
 	<br/><br/>
 </div> 
      <div class="PrjInfo">
		     <div class="pro_img">
		         	 <ul class="proimageshw"><img src=""></ul>

		         	 <ul class="tgra">
		         	  <%
    if(StringUtil.isIn("edit",role)){out.print("<button type='button' aclass='btn btn-info Bgimg_edit' id='Bgimg_edit'><i class='fa fa-list-alt'></i> 编辑</button>");}
    %>
		         	
		            
		         	 </ul>
					
					<div class="ico_v pre-scrollable" style="width:760px; height:25px; overflow-x:auto;" ><!-- <img src="../images/ico_v.png" width="720"> --></div>
				
		         	 
		            
		         	 
		         </div>
		         <div class="baseinfo">
							    <table class="table table-bordered table-hover prjinfo" style="height:400px;">
								<!-- <caption>结构基本信息</caption> -->
								
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
		<div class="pointdatalist">
			 
			<div class="bmwyjc" style="width:760px;float:left;">
				<div id="bmwyjc_tabs" style="min-height:400px;margin-bottom:20px; ">
					<ul>
					</ul>
				</div>
			</div>
			
			<!--日常报表 开始-->
			<div class="rcbb" style="width:400px;float:left;padding-left:10px;">
				<div id="rcbb_tabs" style="min-height:400px;margin-bottom:20px;">
					<ul>
						<li><a href="#rcbb_tabs-0">日报表</a></li>
						<li><a href="#rcbb_tabs-1">周报表</a></li>
						<li><a href="#rcbb_tabs-2">月报表</a></li>
						<li><a href="#rcbb_tabs-3">年报表</a></li>
						<li><a href="#rcbb_tabs-4">分析报告</a></li>
						<li><a href="#rcbb_tabs-5">预警报告</a></li>
					</ul>
					<div id="rcbb_tabs-0"></div>
					<div id="rcbb_tabs-1"></div>
					<div id="rcbb_tabs-2"></div>
					<div id="rcbb_tabs-3"></div>
					<div id="rcbb_tabs-4"></div>
					<div id="rcbb_tabs-5"></div>
				</div>
			</div>
			<!--日常报表  结束-->
			
		</div>
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
var localtion_py;
var reportType="daily_day";//默认为日报表
//var sensorTypeStr={"USS":"超声测距","RainGauge":"雨量","Prism":"测量机器人棱镜","Piezometer":"地下水位","LRDM":"裂缝监测","loadcell":"土压力","Inclinometer":"深部位移","GNSS":"GPS/北斗表面位移","Crackmeter":"拉线位移计","Tiltmeter":"倾度盘"};
$.post("./../PointInfo",{"op":"prjNameAndPrjInfo","prjName":prjName},function(data){
	 var data=eval("("+data+")");
	 //////解析项目类型信息
	 var prjTypeData=data[0].PrjInfo;
	 var ptSummary=data[2].ptSummary;

	 try {/*天气预报相关 */
		 var sAddrCity=prjTypeData[0].sAddrCity;
		 var sAddrRegions=prjTypeData[0].sAddrRegions;
		 if(sAddrCity.indexOf("地区")>=0){
			 sAddrCity=sAddrCity.replace(/[地区]/g,"");
			 localtion_py=ConvertPinyin(sAddrCity);
		 }else {
			 if(sAddrRegions.length>2){
			 sAddrRegions=sAddrRegions.replace(/[县]/g,"");
			 }
			 localtion_py=ConvertPinyin(sAddrRegions);
		 }
		 //多音字特殊处理
		 if(sAddrRegions.indexOf("潮阳")>=0){
			 localtion_py="chaoyang2";
		 }
		 if(sAddrRegions.indexOf("苏仙")>=0){
			 localtion_py="suxian";
		 }
		 if(sAddrRegions.indexOf("秀英")>=0){
			 localtion_py="xiuying";
		 }
	} catch (e) {
		// TODO: handle exception
		 try{
			 sAddrCity=sAddrCity.replace(/[市]/g,"");
			 localtion_py=ConvertPinyin(sAddrCity);
		 }catch(e){
			 console.log(e);
		 }
	}
	$("iframe[name=weather_inc]").attr("src","http://i.tianqi.com/index.php?c=code&id=2&icon=1&num=2&py="+localtion_py);
	console.log($("iframe[name=weather_inc]").html());
	var normal=ptSummary[0].normal;
	var offline=ptSummary[1].offline;
	var alarm=ptSummary[2].alarm;
	var action=ptSummary[3].action;
	var alert=ptSummary[4].alert;
	var fourLevelWarn=ptSummary[5].fourLevelWarn;
	$(".normalcount").text(normal);
	$(".offlinecount").text(offline);
	$(".alarmcount").text(alarm);
	$(".actioncount").text(action);
	$(".sanji").text(alert);
	$(".fourLevelWarn").text(fourLevelWarn);
	$(".proimageshw").html("<img src='"+prjTypeData[0].Bgimg+"' width='760' height='400'>");
     //$.each(prjTypeData,function(index,prjs){

     	//if(prjs.prjName.toLowerCase()==prjName.toLowerCase()){

     		//var  Bgimg=prjs.Bgimg;
     		//if(CheckValIsNull(Bgimg)){
     			//$(".proimageshw").html("<img src='../"+Bgimg+"' width='760'>");
     			
     		//}
     	//}
     //})
	    
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
	   	  	                  $(".prjinfo tbody").find(".endTime").text("至 "+prjtype.endTime);
	   	  	                   $(".prjinfo tbody").find(".manager").text("管理方  "+prjtype.lxr1);
	   	  	                     $(".prjinfo tbody").find(".monitorer").text("监测方  "+prjtype.lxr2);
	   	  	              
	   	                 
	   	                              
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
								      case "离线":
								      marker_ico_src="../images/"+sensorType+"_o.png";
								 
								      break;
								      case "在线":
								      marker_ico_src="../images/"+sensorType+"_g.png";
								
								      break;
								      case "lowpower":
								      marker_ico_src="../images/marker_blue.png";
								     
								      break;
								      case "三级预警":
								      marker_ico_src="../images/"+sensorType+"_ye.png";
								     
								      break;
								      case "二级预警":
								       marker_ico_src="../images/"+sensorType+"_y.png";
								     
								      break;
								      case "一级预警":
								       marker_ico_src="../images/"+sensorType+"_r.png";
								    
								      break;
								      case "四级预警":
								       marker_ico_src="../images/"+sensorType+"_b.png";

								      break;
								      default:
								       marker_ico_src="../images/"+sensorType+"_g.png";
								     
								      break;
								    }
								    $(".proimageshw").append("<img src='"+marker_ico_src+"' class='pointcoord "+sensorType+"' style='left:"+imgX+"px;top:"+imgY+"px' onclick=shwgraphic('"+Pt.sensorType+"','"+Pt.SensorID+"','"+Pt.SensorName+"','"+index+"','"+Pt.groupname+"','"+Pt.staName+"')></i><font class='sensornamestr "+sensorType+"' style='left:"+imgX+"px;top:"+(imgY-19)+"px;color:"+color+"'>"+Pt.staName+"</font>");
                             /*
                    	  	$(".proimageshw").append("<i class='fa fa-flag pointcoord' style='left:"+imgX+"px;top:"+imgY+"px' onclick=shwgraphic('"+Pt.sensorType+"','"+Pt.SensorName+"')></i><font class='sensornamestr' style='left:"+imgX+"px;top:"+(imgY-12)+"px'>"+Pt.SensorName+"</font>");
                    	  	*/
                    	  	
                    	  }
	   		 sensorTypeArr.push(Pt.sensorType);
	   	})

	   sensorTypeArr=uniqueArray(sensorTypeArr);//去除重复的元素。
	   var temp = [];
	   for(var i=0; i<sensorTypeArr.length;i++){
			if(sensorTypeArr[i] == "GNSS"){
				temp.push(sensorTypeArr[i]);
			}
	   }
	   for(var i=0; i<sensorTypeArr.length;i++){
			if(sensorTypeArr[i] != "GNSS"){
				temp.push(sensorTypeArr[i]);
			}
	   }
	   sensorTypeArr = temp;
	   var strSort = "";
	 	    for(var i=0; i<sensorTypeArr.length;i++){
		 	    console.log(sensorTypeArr[i]);
	 	    	var tableHtmlStr="";
	 	    	var CnSensorType="";
	 	    	 for(k in sensorTypeStr){
	 	    	
                      if(k.toLowerCase()==sensorTypeArr[i].toLowerCase()){
                    	 
                      	CnSensorType=sensorTypeStr[k];
                      }
	 	    	 }
	 	    	 
	 	    	var image="<a style='text-decoration: none;' href='javascript:void(0);' class='img_nihao' name='"+sensorTypeArr[i].toLowerCase()+"')><img class='ico_img_"+sensorTypeArr[i].toLowerCase()+"' id='ico_v_"+sensorTypeArr[i].toLowerCase()+"' src=\"../images/"+sensorTypeArr[i].toLowerCase()	+"_g.png\" />&nbsp;<label class='ico_font_"+sensorTypeArr[i].toLowerCase()+" ico_font_xian' id='ico_font_"+sensorTypeArr[i].toLowerCase()+"' >"+CnSensorType;+"</label></a>"
                $('.ico_v').append(image);
	 	    	
	 	    	//console.log(CnSensorType);
				//if(CnSensorType == "北斗"){
				$("#bmwyjc_tabs ul").append("<li><a href='#tabs-"+i+"'>"+CnSensorType+"</a></li>");
				//}else{
				//	strSort += "<li><a href='#tabs-"+i+"'>"+CnSensorType+"</a></li>";
				//}

	 	    	
	 	    	
	 	    	
	 	    	 // rqliao这里请按照每一种类型设备单独设计表格头，因为不同类型设备表头不一样，
	 	    	// 如GPS,则ref1值改为 北向位移(毫米),ref2值改为 东向位移(毫米),垂直向位移(毫米),
	 	    	// 裂缝监测，则要ref1值改为，位移量（毫米），ref2,ref3去掉--!>
	 	    	  
	 	    	      tableHtmlStr+='<div id="tabs-'+i+'">'+
	         	                   '<table class="table table-bordered table-hover surface" style="text-align: center;">'+
					               /* '<caption>'+CnSensorType+'监测</caption>'+ */
					                '<thead>'+
						            '<tr>'+
							        '<th width="16%" style="white-space: nowrap;text-overflow:ellipsis; overflow:hidden">设备名</th>'+
							        //'<th width="12%">测站名</th>'+
							        '<th width="16%">状态</th>';
							        
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
                             var statusDes=Pt.StatusDes;
                             if(!CheckValIsNull(statusDes)){
                            	 statusDes="";
                             }
                             statusDesArr.push(statusDes);
                            tableHtmlStr+='<tr>'+
							                '<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") >'+Pt.staName+'</a></td>'//+
							                 //'<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorName+'",'+index+') >'+Pt.SensorName+'</a></td>';
							                 
							                //'<td>'+Pt.StatusDes+'</td>';
							                if(Pt.Status=="低电"){
							                	  tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:#ff160a;font-weight:bold" title="'+Pt.voltage+'">低电</a></td>';
						                    }
							                else if(Pt.Status=="三级预警"){
							                	  tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:#f7cb00;font-weight:bold" title="'+Pt.voltage+'">三级预警</a></td>';
						                    }
							                else if(Pt.Status=="四级预警"){
							                	  tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:#62c9e6;font-weight:bold" title="'+Pt.voltage+'">四级预警</a></td>';
						                    }
							                else if(Pt.Status=="二级预警"){
							                	  tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:#f6ba40;font-weight:bold" title="'+Pt.voltage+'">二级预警</a></td>';
						                    }
							                else if(Pt.Status=="一级预警"){
							                	  tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:#ff160a;font-weight:bold" title="'+Pt.voltage+'">一级预警</a></td>';
						                    }
							                else if(Pt.Status=="已结束"){
                                                tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:gray;font-weight:bold" title="'+Pt.voltage+'">已结束</a></td>';
							                }
							                else if(Pt.Status=="离线"){
                                                tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:#ff160a;font-weight:bold" title="'+Pt.voltage+'">离线</a></td>';
							                }else{
							                	tableHtmlStr+='<td style="vertical-align:middle;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") style="color:#00c818;font-weight:bold" title="'+Pt.voltage+'">在线</a></td>';
							                }
							                if(!statusDes&&Pt.staName.indexOf('JZ')>=0){
							                	statusDes="北斗基站";
							                }
							                tableHtmlStr+= '<td style="text-align: left;"><a href="javascript:void(0);" onclick=shwgraphic("'+Pt.sensorType+'","'+Pt.SensorID+'","'+Pt.SensorName+'",'+index+',"'+Pt.groupname+'","'+Pt.staName+'") >'+statusDes+'</a></td>';
							                
						             tableHtmlStr+='</tr>';
			 	   		 }
			 	   	});
                   
                    tableHtmlStr+='</tbody></table><div class="page"></div></div>';
                    
                    //$(".pointdatalist").append(tableHtmlStr);
                    $("#bmwyjc_tabs").append(tableHtmlStr);

                    var limit = 8;
                    $(".page").each(function () {
                        //总的结果
						var results = $(this).prev('table').find('tbody > tr');
						if( results.length >0){
                            //总的结果数
                            var totalNum = results.length;
                            var totalPages =  Math.ceil(totalNum/limit);
                            showPage(1,totalPages,totalNum,results,limit);
                            $(this).Page({
                                totalPages: totalPages,//分页总数
                                liNums: 7,
                                activeClass: 'activP', //active 类样式定义
                                callBack : function(page,obj){
                                    results = $(obj).prev('table').find('tbody > tr');
                                    //总的结果数
                                    totalNum = results.length;
                                    totalPages =  Math.ceil(totalNum/limit);
                                    showPage(page,totalPages,totalNum,results,limit);
                                }
                            });
						 }
                    })
                    var $container = $('.pointdatalist');
                    $container.imagesLoaded(function(){
                    $container.masonry({
                    itemSelector:'.pro_data_table_list',
                    gutter:0,
                    isAnimated:true
                    });
                     });
                    /*
                    var h=$(document).height();
					var right = $(window.parent.document).find("#right");
					right.height(h+20);
					*/
	 	    }
	 	   //$("#bmwyjc_tabs ul").append(strSort);
	   }

	   $(".img_nihao").each(function(i,e){
		   var param = $(this).attr("name");
		   $(this).bind("click",function(){
			   //console.log($(this).html()+i+"---"+param);
			   if($("."+param).is(":hidden")){//显示
				   $("."+param).show();
				   $(".ico_img_"+param).removeClass("ico_img_switchover");
				   $(".ico_font_"+param).removeClass("ico_font_switchover");
				   $(".ico_font_"+param).addClass("ico_font_xian");
			   }else{//隐藏
				   $(".ico_img_"+param).addClass("ico_img_switchover");
				   $(".ico_font_"+param).removeClass("ico_font_xian");
				   $(".ico_font_"+param).addClass("ico_font_switchover");
				   $("."+param).hide();
			   }
		   })
		});
	   
	   $(function() {
    	    $( "#bmwyjc_tabs" ).tabs();
    	});
})
function showPage(pageIndex,allPages,cnt,boxes,limit)
{
    var indexs = new Array(cnt);
    for(var i=0; i<cnt; i++) {
        indexs[i] = i;
    }
    if(pageIndex== 0 || pageIndex==(allPages+1)) {
        return;
    }
    var start = (pageIndex-1)* limit ;//8
    //alert("start:" + start);
    var end = start + limit ;
    end = end > cnt ? cnt : end;//16
    //alert("end:" + end);
    var subIndexs = indexs.slice(start, end);
    for(var i=0; i<cnt; i++) {
        if(contains(i, subIndexs)) {
            boxes.eq(i).show();
        }else{
            boxes.eq(i).hide();
        }
    }
}
var contains = function (element, arr) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == element) {
            return true;
        }
    }
    return false;
}
function shwgraphic(sensorType,sensorname,name,index,groupname,staName){
	setCookie("statusDes",statusDesArr[index]);
	var urlname=sensorType.toLowerCase();
var SensorHref=urlname+".jsp?name="+name+"&sensorname="+sensorname+"&sensorType="+sensorType+"&groupname="+groupname+"&staName="+staName;

if(sensorType=="RainGauge"){
	sensorType="雨量计";
}else if(sensorType=="Prism"){
	sensorType="测量机器人棱镜";
}else if(sensorType=="GNSS"){
	sensorType="北斗";
}else if(sensorType=="PiezoMeter"){
	sensorType="地下水位";
}else if(sensorType=="InclinoMeter"){
	sensorType="深部位移";
}else if(sensorType=="CrackMeter"){
	sensorType="表面测缝计";
}else if(sensorType=="DisMeter"){
    sensorType="拉线位移计";
}else if(sensorType=="SFLGauge"){
    sensorType="静力水准仪";
}else if(sensorType=="OsmoMeter"){
    sensorType="渗压计";
}else if(sensorType=="USS"){
	sensorType="超声测距";
}else if(sensorType=="CorrosionMeter"){
	sensorType="腐蚀传感器";
}else if(sensorType=="LoadCell"){
	sensorType="称重传感器";
}else if(sensorType=="VibrationCell"){
	sensorType="振动传感器";
}else if(sensorType=="FlexoMeter"){
	sensorType="液压变送器";
}else if(sensorType=="ClinoMeter"){
	sensorType="倾角仪";
}else if(sensorType=="CableForceMeter"){
	sensorType="索力计";
}else if(sensorType=="TemperatureMeter"){
	sensorType="温度传感器";
}else if(sensorType=="EarthPreCell"){
	sensorType="土压力计";
}else if(sensorType=="SoilHumidity"){
	sensorType="土壤含水率";
}else if(sensorType=="StrainGauge"){
	sensorType="应变计";
}else if(sensorType=="UpliftPressure"){
	sensorType="扬压力";
}else if(sensorType=="AnemoMeter"){
	sensorType="风速风向计";
}else if(sensorType=="THMeter"){
	sensorType="温湿度传感器"
}else if(sensorType=="Tiltmeter"){
	sensorType="倾度盘"
}else if(sensorType=="RropeMeter"){
	sensorType="锚索计"
}





//window.location.href=SensorHref; //这句打开则在当前页面
art.dialog.open(SensorHref, {title: "类型："+sensorType+"&nbsp;&nbsp;结构名称："+prjAlias, width: 1180,height:600,lock:true,top:0});
}

$("#Bgimg_edit").bind("click",function(){
	setCookie("prjType",prjType);
	//art.dialog.open("upload1.jsp?prjName="+prjName, {title: "上传项目图片", width: 480,height:180,lock:true});
	window.location.href="upload.jsp?prjName="+prjName;
	
}) 
//自动计算左侧结构信息宽度，自适应与下方监测div对齐
var prjstatus_width=$(".prjstatus").width();
var baseinfo_width=prjstatus_width-$(".pro_img").width()-(prjstatus_width/2-prjstatus_width/2*0.98)-30;
$(".baseinfo").width(baseinfo_width);
$(".rcbb").width(baseinfo_width);

$( "#rcbb_tabs" ).tabs();
queryReport("daily_day","rcbb_tabs-0");//默认查询日报表数据
//报表选项卡切换查询
$("#rcbb_tabs ul li a").bind("click",function(){
	var rcbb_div_id;
	if($(this).attr("href").indexOf("0")>=0){
		reportType="daily_day";
		rcbb_div_id="rcbb_tabs-0";
	}else if($(this).attr("href").indexOf("1")>=0){
		reportType="daily_week";
		rcbb_div_id="rcbb_tabs-1";
	}else if($(this).attr("href").indexOf("2")>=0){
		reportType="daily_month";
		rcbb_div_id="rcbb_tabs-2";
	}else if($(this).attr("href").indexOf("3")>=0){
		reportType="daily_year";
		rcbb_div_id="rcbb_tabs-3";
	}else if($(this).attr("href").indexOf("4")>=0){
		reportType="analysis";
		rcbb_div_id="rcbb_tabs-4";
	}else if($(this).attr("href").indexOf("5")>=0){
		reportType="warning";
		rcbb_div_id="rcbb_tabs-5";
	}

	if($("#"+rcbb_div_id).html()!='')//已查询过的数据将不再查询，提高性能
		return;
	queryReport(reportType,rcbb_div_id);
});

//查询日常报表数据
function queryReport(reportType,rcbb_div_id){
	$.post("./../ReportInfo",{"oper":"queryReportsForPrjlist","reportType":reportType,"prjName":prjName},function(data){
		var data=eval("("+data+")");
		if(data.length<=0){
			$("#"+rcbb_div_id).append("无报表数据");
			return;
		}
		var tableHtmlStr="";
		tableHtmlStr+='<table class="table table-bordered table-hover surface" style="color:grey;text-align: center;word-wrap: break-word; word-break: break-all;">'+
        /* '<caption>'+CnSensorType+'监测</caption>'+ */
         '<thead>'+
         '<tr>'+
	        '<th style="white-space: nowrap;text-overflow:ellipsis; overflow:hidden">报表名称</th>'+
	        //'<th width="12%">测站名</th>'+
	        '<th width="40%">生成时间</th>';
	        
	         //'<th>状态描述</th>'+
	       tableHtmlStr+= '<th style="width: 25%" >操作</th>'+
         '</tr>'+
         '</thead>'+
         '<tbody>';
         $.each(data,function(index,rp){
        	 tableHtmlStr+='<tr>'+
             '<td>'+rp.sReportName+'</td>'+
             '<td>'+rp.dtCreatAt+'</td>'+ 
             '<td><a style="cursor:pointer;" onclick="viewPDF(\''+rp.sURLAddress+'\')">查看</a></td>';
      		tableHtmlStr+='</tr>';
 	   	});
        tableHtmlStr+='</tbody></table>';
        
        if(data.length<=0){
			 $("#"+rcbb_div_id).append("");
		}
        
        $("#"+rcbb_div_id).append(tableHtmlStr);
	});
}

//报表数据在线查看，只支持PDF格式
function viewPDF(url){
   var url="http://bd.hnlzqs.com/picserver/export/"+url;
   var myWindow=window.open(url,'','left=00,top=0,width=1180,height=680,toolbar=no,scrollbars=no,resizable=no,statusbars=no');
}
</script>

<script src="../js/footer.js"></script>

