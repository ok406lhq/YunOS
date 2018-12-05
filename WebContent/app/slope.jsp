<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/app.css" />

 <script src="../js/jquery-1.11.1.min.js"></script>
  <script src="../js/config.js"></script>

  <script src="../js/baidumap_api.js"></script>
 </head>
 <body >
 <div class="wrapper" >
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
    <div id="map_canvas" style="width: 100%; height: 460px;  float: left;"></div>

   <table class="table table-bordered table-hover prj">
  <caption><font class="prj_title"></font>项目基本信息一览表</caption>
  <thead>
    <tr >
     <th>项目类型</th> 
      <th>项目名</th>
      <th>项目地址</th>
      <th>标段</th>
      <th>结构概述</th>
      <th>检测起止时间</th>
       <th>主要监测内容</th>
       <th>状态</th>
       <th>状态描述</th>
    </tr>
  </thead>
  <tbody>
    
  </tbody>
</table>


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
 var map = new BMap.Map("map_canvas", {mapType:BMAP_HYBRID_MAP});
 
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
map.addControl(new BMap.MapTypeControl());

   var SprjName=$('.ProjectName', parent.document).val(); 
   SprjName=CheckValIsNull(SprjName)?SprjName:"All";
   
   $.post("./../ProjectInfo",{prjName:""},function(data){
   var data=eval("("+data+")");
 
   var projectListArr=[];
    var projectList=data[0].PrjInfo;
    if(CheckValIsNull(prj)){
         for(var i=0;i< projectList.length;i++){
            
           if($.trim(projectList[i].prjType) == $.trim(prj)){
          
             projectListArr.push(projectList[i]);
           }
         }
     }else{
       projectListArr=projectList;
     }

     projectList=null;
     var PrjStatus=data[1].PrjStatus;
 
    $(".prjstatus .alert").text(PrjStatus[0].alert);
    $(".prjstatus .alarm").text(PrjStatus[1].alarm);
    $(".prjstatus .action").text(PrjStatus[2].action);
    $(".prjstatus .normal").text(PrjStatus[3].normal);

var len=projectListArr.length;
   
var longVal = 0;
var latVal = 0;
var ptCount = 0;
var normalCount=0;
var alarmCount=0;
var actionCount=0;
 var str="";
for(var i=0;i<len;i++){
	var  StatusDes=CheckValIsNull(projectListArr[i].StatusDes)?projectListArr[i].StatusDes:"";
  var statusVal=projectListArr[i].statusVal;
         str="<tr>"+
            "<td ><a href='slope.jsp?prj="+encodeURI(projectListArr[i].prjType)+"'<span style='vertical-align: middle'>"+projectListArr[i].prjType+"</span></a></td>"+
            "<td><a href='list.jsp?prjName="+projectListArr[i].prjName+"&prjType="+encodeURI(projectListArr[i].prjType)+"&prjAlais="+encodeURI(projectListArr[i].prjAlais)+"'>"+projectListArr[i].prjAlais+"</a></td>"+
            "<td>"+projectListArr[i].prjAddr+"</td>"+
            "<td>"+projectListArr[i].BiaoDuan+"</td>"+
            "<td>"+projectListArr[i].JieGouDes+"</td>"+
            "<td>"+projectListArr[i].startTime+"-"+projectListArr[i].endTime+"</td>"+
            "<td>"+projectListArr[i].JianceNeirong+"</td>"+
             "<td>"+statusVal+"</td>"+
             "<td>"+StatusDes+"</td>"+
          "</tr>"+
         "<tr>";
   $(".prj").find("tbody").append(str);
     if(statusVal=="online"){
      normalCount++;
     }else if(statusVal=="alarm"){
      alarmCount++;
     }else if(statusVal=="action"){
      actionCount++;
     }

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
  $(".ActionCount").text(normalCount);$(".unprocSum").text(alarmCount); $(".AlarmCount").text(actionCount);




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
  var PrjAlias=_dataJson.prjAlais;
      var StatusVal=_dataJson.statusVal;
    
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

  var myIcon=new BMap.Icon(marker_ico_src,new BMap.Size(32,32));
  var _marker=new BMap.Marker(_point,{icon:myIcon});
   
                         
                          var StaName=_dataJson.StaName;

                var sorName=SensorName.replace(/-/g, "_");
                var TableName=StaName+"_"+sorName.toLowerCase();
                 
                 if (StatusVal != "在线" && StatusVal != "Online"){
                                StatusVal="不在线";
                 }else{
                                   StatusVal="在线";
                 }
               
  var _iw=new BMap.InfoWindow("<p style='font-size:14px;'>结构名称 :"+PrjAlias+"</p><p >结构类型："+_dataJson.prjType+"&nbsp;</p><p>结构状态："+StatusVal+"</p><a href='list.jsp?prjName="+sorName+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="+encodeURI(PrjAlias)+"' ><p>查看</p></a>");
  // var myLabel = new BMap.Label("<a href='#')>"+SensorName+"</a>");
      var myLabel = new BMap.Label("<a href='list.jsp?prjName="+sorName+"&prjType="+encodeURI(_dataJson.prjType)+"&prjAlais="+encodeURI(PrjAlias)+"' style='color:"+color+"' >"+PrjAlias+"</a>",{offset:new BMap.Size(8,-22),position:_point});
  myLabel.setStyle({ 
            color : color, 
            fontSize : "12px",
            backgroundColor :"0.6",
            border :"1",padding:"2px 52px 2px 2px",
            fontWeight :"bold" ,background:"#f6f6f6"
            });
_marker.setLabel(myLabel); 
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
        map.centerAndZoom(new BMap.Point(cenLng,cenLat), zoom);  
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

    </script>
 </body>
 </html>

  <script src="../js/footer.js"></script>