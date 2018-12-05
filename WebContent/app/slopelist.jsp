<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/app.css" />

<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/config.js"></script>
<script src="../js/baidumap_api.js?v=2"></script>
<!-- <script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=R3LQvkwLczemVKoB0pcT0h8RuWjaA7Uk&services=&t=20180823194355"></script>
 -->
  <!-- 去除百度地图logo -->
  <style>
          .BMap_cpyCtrl {
            display: none;
        }
 
        .anchorBL {
            display: none;
        }
  
  </style>
 </head>
 <body >
 <div class="wrapper" >
 <div class="prjstatus">

			<ul class="normalstaus">
				<li>状态正常结构
					<p>
						<font class="normalCount">0</font>个
					</p>
				</li>
			</ul>
			<ul  style="background-color: #ff0502">
				<li>一级预警结构
					<p>
						<font class="redCount">0</font>个
					</p>
				</li>
			</ul>
			<ul  style="background-color: #f9d243">
				<li>二级预警结构
					<p>
						<font class="yellowCount">0</font>个
					</p>
				</li>
			</ul>
			<ul  style="background-color: #f5fb58">
				<li>三级预警结构
					<p>
						<font class="lightYellowCount">0</font>个
					</p>
				</li>
			</ul>
			<ul  style="background-color: #62c9e6">
				<li>四级预警结构
					<p>
						<font class="fourLevelWarn">0</font>个
					</p>
				</li>
			</ul>
		</div>
    <!-- <div id="map_canvas" style="width: 100%; height: 460px;  float: left;"></div> -->
	<div id="map_canvas" style="width: 100%; height: 920px;  float: left;"></div>
   <!-- <table class="table table-bordered table-hover prj">
  <caption id="base_caption" title="点击展开" style="position:relative;"><label style="position:relative;top:0px;font-size:8px;float:left"><img src="../images/up.jpg" height="25"><a>点击收起</a></label><font class="prj_title"></font>结构基本信息一览表 </caption>
  <thead>
    <tr >
     <th colspan="1" style="width:100px;">结构类型</th> 
      <th colspan="1">结构名称</th>
      <th colspan="1">结构地址</th>
      <th colspan="1" style="width:600px;">结构概述</th>
      <th colspan="1">监测起止时间</th>
       <th colspan="1">监测内容</th>
       <th colspan="1">当前状态</th>
       <th colspan="1">最新报表</th>
    </tr>
  </thead>
  <tbody>
    
  </tbody>
</table> -->


 </div>


  <script type="text/javascript">
    	
  var prj=getUrlVars()['prj'];
   prj=decodeURIComponent(prj);
 if(CheckValIsNull(prj)){
   $(".prj_title").text(prj);
 }


  var center; var markerList = [];
    var GPSData;
    var z=0;var vectorMarker;
 	var map = new BMap.Map("map_canvas",{mapType: BMAP_HYBRID_MAP});  // 创建Map实例
    map.centerAndZoom(new BMap.Point(103.388611,35.563611), 30);   // 初始化地图,设置中心点坐标和地图级别
	
	
  var pageSize=20;
    var totalPage ;
    var totalRecords;
    var pageNo = getParameter('pno');
  if(!pageNo){
    pageNo = 1;
  }
    
$(document).ready(function(e) {
var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE}); //定义向地图中添加缩放控件
var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1}); //向地图中添加缩略图控件
var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});  //向地图中添加比例尺控件

map.enableScrollWheelZoom(); 
map.enableContinuousZoom(); 
map.addControl(ctrl_nav);
map.addControl(ctrl_ove);  //向地图中添加缩略图控件
map.addControl(ctrl_sca); //向地图中添加比例尺控件
map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_SATELLITE_MAP,BMAP_HYBRID_MAP]}));

   var SprjName=$('.ProjectName', parent.document).val(); 
   SprjName=CheckValIsNull(SprjName)?SprjName:"All";
   //$.post("./../ProjectInfo",{prjName:""},function(data){
   $.post("./../myprojects",{prjName:prj},function(data){
   var data=eval("("+data+")");
   console.log(data);
    var projectListArr=[];
    var projectList=data[0].PrjInfo;
    if(CheckValIsNull(prj)){
         for(var i=0;i< projectList.length;i++){
           if($.trim(projectList[i].prjType) == $.trim(prj)){
             console.log(projectList[i]);
             projectListArr.push(projectList[i]);
           }
         }
     }else{
       projectListArr=projectList;
     }
     projectList=null;
     var PrjStatus=data[1].PrjStatus;
    $(".normalCount").text(PrjStatus[3].normal);
    $(".redCount").text(PrjStatus[1].alarm);
    $(".yellowCount").text(PrjStatus[2].action);
	$(".lightYellowCount").text(PrjStatus[0].alert);
	$(".fourLevelWarn").text(PrjStatus[4].fourLevelWarn);
	//记得删除该代码，目前写死测试用
	//projectListArr=[{Bgimg:"http://58.20.17.214:8080/picserver/web/1514252620426.png",BiaoDuan:"BiaoDuan",JianceNeirong:"表层位移、深层位移、降雨量",JieGouDes:"工业园边坡及测试设备接入",Latitude:28.31,Longitude:112.86,StatusDes:"",endTime:"2050-12-12",lxr1:"lxr1",lxr2:"lxr2",prjAddr:"湖南长沙望城",prjAlais:"联智工业园",prjID:"1",prjName:"联智桥隧工业园",prjType:"边坡",startTime:"2016-11-10",statusVal:"一级预警"}];
/* 	data[0].PrjInfo[0].statusVal='一级预警';
	data[0].PrjInfo[1].statusVal='一级预警';
	data[0].PrjInfo[2].statusVal='一级预警';
	data[0].PrjInfo[3].statusVal='二级预警';
	data[0].PrjInfo[4].statusVal='二级预警';
	data[0].PrjInfo[5].statusVal='三级预警'; */
var len=projectListArr.length;
var longVal = 0;
var latVal = 0;
var ptCount = 0;
 var str="";
for(var i=0;i<len;i++){
	var  StatusDes=CheckValIsNull(projectListArr[i].StatusDes)?projectListArr[i].StatusDes:"";
	var statuVal1 = projectListArr[i].statusVal;
	statuVal1.toLowerCase();
    if (statuVal1 == "二级预警"){
    	statuVal1="<a style=‘color:#f9d243;font-weight:bold’>二级预警</a>";
    }
    else if (statuVal1 == "一级预警"){
    	statuVal1="<a style=‘color:#ff0502;font-weight:bold’>一级预警</a>";
    }else if(statuVal1 == "三级预警"){
    	statuVal1="<a style=‘color:#f5fb58;font-weight:bold’>三级预警</a>";
    }else if(statuVal1 == "四级预警"){
    	statuVal1="<a style=‘color:#62c9e6;font-weight:bold’>四级预警</a>";
    }
    else if(statuVal1 == "离线"){
    	statuVal1="离线";
    }else
    {
    	statuVal1="正常";
    }
	var sURLAddress=projectListArr[i].sURLAddress,sURLAddressHTML;
	if(!sURLAddress){
		sURLAddressHTML="没有报表";
	}else{
		sURLAddressHTML="<a href=\"javascript:window.open('"+sURLAddress+"','','left=00,top=0,width=1180,height=680,toolbar=no,scrollbars=no,resizable=no,statusbars=no')\">查看</a>";
	}
	str="<tr>"+
            "<td colspan='1' style='vertical-align:middle;width: 8%'>"+projectListArr[i].prjType+"</td>"+
            "<td colspan='1' style='vertical-align:middle;width: 10%'><a href='prjlist.jsp?prjName="+projectListArr[i].prjID+"&prjType="+encodeURI(projectListArr[i].prjType)+"&prjAlais="+encodeURI(projectListArr[i].prjAlais)+"'>"+projectListArr[i].prjAlais+"</a></td>"+
            "<td colspan='1' style='vertical-align:middle;width: 10%'>"+projectListArr[i].prjAddr+"</td>"+
            "<td colspan='1' style='text-align:left;'>"+projectListArr[i].JieGouDes+"</td>"+
            "<td colspan='1' style='vertical-align:middle;width: 10%'>"+projectListArr[i].startTime+"至"+projectListArr[i].endTime+"</td>"+
            "<td colspan='1' style='vertical-align:middle;width: 10%'>"+projectListArr[i].JianceNeirong+"</td>"+
             "<td colspan='1' style='vertical-align:middle;width: 8%'>"+statuVal1+"</td>"+
             "<td colspan='1' style='vertical-align:middle;width: 8%'>"+sURLAddressHTML+"</td>"+
          "</tr>"+
         "<tr>";
   $(".prj").find("tbody").append(str);
  if(len!=null){

    if(CheckValIsNull(projectListArr[i].Longitude) && parseInt(projectListArr[i].Longitude)!=0){

    addMarkerPoint(projectListArr[i]);
    longVal += Number(projectListArr[i].Longitude);
    latVal += Number(projectListArr[i].Latitude);
    ptCount ++;
    }else{
      projectListArr[i].Longitude="116.4054";
      projectListArr[i].Latitude="39.9136";
    }
    
  }
}

setZoom(projectListArr);



   })




function addMarkerPoint(_dataJson){

  var _point=new BMap.Point(_dataJson.Longitude,_dataJson.Latitude);
  
   var opts = {
        width: 320,     // 信息窗口宽度
        height: 200,     // 信息窗口高度
        title: _dataJson.prjName, // 信息窗口标题
        enableMessage: false,//设置允许信息窗发送短息
        message: ""
    }
     var SensorName=_dataJson.prjName; 
   	var prjId = _dataJson.prjID;
  var PrjAlias=_dataJson.prjAlais;
      var StatusVal=_dataJson.statusVal;
    
      var color="#15cc02";
    var marker_ico_src="../images/gnss_g.png";
/*     switch(StatusVal){
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
    } */
    switch(StatusVal){
    case "一级预警":
    marker_ico_src="../images/gnss_r.png";
    color="#ff1800";
    break;
    case "二级预警":
    marker_ico_src="../images/gnss_y.png";
    color="#ffd800";
    break;
    case "三级预警":
    marker_ico_src="../images/gnss_ye.png";
    color="#ffd800";
    break;
    case "四级预警":
    marker_ico_src="../images/gnss_b.png";
    color="#ffd800";
    break;
  }
  var myIcon=new BMap.Icon(marker_ico_src,new BMap.Size(32,32));
  var _marker=new BMap.Marker(_point,{icon:myIcon});
  var StaName=_dataJson.StaName;
  var sorName=SensorName.replace(/-/g, "_");
  var TableName=StaName+"_"+sorName.toLowerCase();
  StatusVal.toLowerCase();
  var prjTipTxt = "";
  if (StatusVal == "一级预警"){
	  prjTipTxt = "<p style='font-size:14px;'>结构名称 :"+PrjAlias+"</p><p >结构类型："+_dataJson.prjType+"&nbsp;</p><p>结构状态："
	  +"<a style='color:#ff0502;font-weight:bold'>" + StatusVal+"</a></p><a href='prjlist.jsp?prjName="+prjId+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="
	  +encodeURI(PrjAlias)+"' ><p>查看</p></a>";
  }
  else if (StatusVal == "二级预警"){
	prjTipTxt = "<p style='font-size:14px;'>结构名称 :"+PrjAlias+"</p><p >结构类型："+_dataJson.prjType+"&nbsp;</p><p>结构状态："
	  +"<a style='color:#f9d243;font-weight:bold'>" + StatusVal+"</a></p><a href='prjlist.jsp?prjName="+prjId+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="
	  +encodeURI(PrjAlias)+"' ><p>查看</p></a>";
  }
  else if (StatusVal == "三级预警"){
	prjTipTxt = "<p style='font-size:14px;'>结构名称 :"+PrjAlias+"</p><p >结构类型："+_dataJson.prjType+"&nbsp;</p><p>结构状态："
	  +"<a style='color:#f5fb58;font-weight:bold'>" + StatusVal+"</a></p><a href='prjlist.jsp?prjName="+prjId+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="
	  +encodeURI(PrjAlias)+"' ><p>查看</p></a>";
  }
  else if (StatusVal == "四级预警"){
	prjTipTxt = "<p style='font-size:14px;'>结构名称 :"+PrjAlias+"</p><p >结构类型："+_dataJson.prjType+"&nbsp;</p><p>结构状态："
	  +"<a style='color:#62c9e6;font-weight:bold'>" + StatusVal+"</a></p><a href='prjlist.jsp?prjName="+prjId+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="
	  +encodeURI(PrjAlias)+"' ><p>查看</p></a>";
  }
  else{
	prjTipTxt = "<p style='font-size:14px;'>结构名称 :"+PrjAlias+"</p><p >结构类型："+_dataJson.prjType+"&nbsp;</p><p>结构状态："
	  + StatusVal+"</p><a href='prjlist.jsp?prjName="+prjId+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="
	  +encodeURI(PrjAlias)+"' ><p>查看</p></a>";
    
  }      
  var _iw = new BMap.InfoWindow(prjTipTxt);
  // var myLabel = new BMap.Label("<a href='#')>"+SensorName+"</a>");
      var myLabel = new BMap.Label("<a href='prjlist.jsp?prjName="+sorName+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="+encodeURI(PrjAlias)+"' style='color:"+color+"' >"+PrjAlias+"</a>",{offset:new BMap.Size(8,-22),position:_point});
  myLabel.setStyle({ 
            color : color, 
            fontSize : "12px",
            backgroundColor :"0.6",
            border :"0px",
            padding:"0px 0px 0px 0px",
            fontWeight :"bold" ,background:"#f6f6f6"
            });
//  _marker.setLabel(myLabel); 
  _marker.addEventListener("mouseover",function(){
    this.openInfoWindow(_iw);
  });
  _marker.addEventListener("click",function(){
    //this.openInfoWindow(_iw);
    showgraphics(TableName,_dataJson.SensorType,_dataJson.groupname,encodeURI(_dataJson.CurStatus),_dataJson.groupname,'graphic');
  });
  markerList.push(_marker);
  map.addOverlay(_marker);
}


$(".slidedownbaidu").bind(touchEvents.touchstart,function(){
  if($("#map_sensor_calcul").is(":visible")){
    $("#map_sensor_calcul").hide();
     $(".slidedownbaidu").css({"left":0});
     $(".slidedownbaidu").attr("src","images/slide_right.png");
  }else{
    $("#map_sensor_calcul").show();
    $(".slidedownbaidu").attr("src","images/slide_left.png");
    $(".slidedownbaidu").css({"left":"240px"});
  }
     

});


});



function showgraphics(TableName,SensorType,SensorName,CurStatus,groupname,str){
    //alert("graphic");


  var SensorHref="";
  var sensortype_l=SensorType.toLowerCase();
    var parm="tbale="+TableName+"&sensortype="+SensorType+"&groupname="+groupname+"&sensorname="+SensorName+"&Status="+encodeURI(CurStatus);
    
   switch(str){
    case "graphic":
      if(config.userAgent=="Android"){
           window.AndroidData.scree_oration(sensortype_l+".html?"+parm);
      }else{
        SensorHref=sensortype_l+".html?"+parm;
      }
    break;
    case "table":
    SensorHref="system_tabulate.html?"+parm;
    break;
    default:
     SensorHref=sensortype_l+".html?"+parm;
    break;
  }

    window.location.href=SensorHref;
}
function openwin(SensorName){
  
  art.dialog.open(config.server+'manager/hprogectmanager.html?SensorName='+SensorName, {title: '点位数据查看', width: '100%',height:540});
}

function movemap(lat,lng){
  map.centerAndZoom(new BMap.Point(lng,lat),16);
  map.panTo(new BMap.Point(lng,lat));  
  
  
}
function closeprshw(){
  $(".prshw").hide("slow");
}
        $(function () {
 
            //隐藏所有点标注
            $("#markhide").click(function () {
                if (markerList) {
                    for (i in markerList) {
                        //markerList[i].setMap(null);
                        markerList[i].hide();
                    }
                }
            })
         //显示所有点标注
            $("#markshw").click(function () {
                if (markerList) {
                    for (i in markerList) {
                        markerList[i].show();
                    }
                }
            })
        })
    //////////////////////////
  function shwmenu(){
    
  $(".prshw").show("slow");
  }

 function replaceSpace(str) {
            var s = str;
            return s.replace(/[ ]/g, "");
        }

  
 

 Array.prototype.unique = function() {
    var res = [], hash = {};
    for(var i=0, elem; (elem = this[i]) != null; i++)  {
        if (!hash[elem])
        {
            res.push(elem);
            hash[elem] = true;
        }
    }
    return res;
} 


function setZoom(points){
      if(points.length>0){
        var maxLng = points[0].Longitude;
        var minLng = points[0].Longitude;
        var maxLat = points[0].Latitude;
        var minLat = points[0].Latitude;
        var res;
        for (var i = points.length - 1; i >= 0; i--) {
          res = points[i];
          if(res.Longitude > maxLng) maxLng =res.Longitude;
          if(res.Longitude < minLng) minLng =res.Longitude;
          if(res.Latitude > maxLat) maxLat =res.Latitude;
          if(res.Latitude < minLat) minLat =res.Latitude;
        };
        var cenLng =(parseFloat(maxLng)+parseFloat(minLng))/2;
        var cenLat = (parseFloat(maxLat)+parseFloat(minLat))/2;
        var zoom = getZoom(maxLng, minLng, maxLat, minLat);
        map.centerAndZoom(new BMap.Point(cenLng, cenLat), 6);  
      }else{
        //没有坐标，显示全中国。
        map.centerAndZoom(new BMap.Point(103.388611,35.563611), 5);  
      } 
    }
function getZoom (maxLng, minLng, maxLat, minLat) {
      var zoom = ["50","100","200","500","1000","2000","5000","10000","20000","25000","50000","100000","200000","500000","1000000","2000000"]
      var pointA = new BMap.Point(maxLng,maxLat);  
      var pointB = new BMap.Point(minLng,minLat);  
      var distance = map.getDistance(pointA,pointB).toFixed(1);  
      for (var i = 0,zoomLen = zoom.length; i < zoomLen; i++) {
        if(zoom[i] - distance > 0){
          return 18-i+3;
        }
      };
    }

//默认隐藏
$(".prj thead").show();
$(".prj tbody").show();

$("#base_caption").bind("click",function(){
	
	if($(".prj thead").css("display")=="none"||$(".prj tbody").css("display")=="none"){
		$(".prj thead").show();
		$(".prj tbody").show();
		$("#base_caption img").attr("src","../images/up.jpg");
		$("#base_caption label a").html("点击收起");
	}else{
		$(".prj thead").hide();
		$(".prj tbody").hide();
		$("#base_caption img").attr("src","../images/down.jpg");
		$("#base_caption label a").html("点击展开");
	}
	

	});

    </script>
 </body>
 </html>

  <script src="../js/footer.js"></script>