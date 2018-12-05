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

 	<ul>
 		<li class="ico p0"><i class="fa fa-dropbox"></i></li>
 		<li class="st"><span>优</span><br><font class="alert"></font>个异常设备</li>
 	</ul>
   <ul>
 		<li class="ico p1"><i class="fa fa-openid"></i></li>
 		<li class="st"><span>优</span><br><font class="alarm"></font>个一11级警告</li>
 	</ul>
 	<ul >
 		<li class="ico p2"><i class="fa fa-bell"></i></li>
 		<li class="st"><span>优</span><br><font class="action"></font>个二级警告</li>
 	</ul>
 	<ul >
 		<li class="ico p3"><i class="fa fa-anchor"></i></li>
 		<li class="st"><span>优</span><br><font class="normal"></font>个三级警告</li>
 	</ul>
 </div>
    <div id="map_canvas" style="width: 100%; height: 460px; margin-top: 10px; float: left;"></div>

   <table class="table table-bordered table-hover prj">
  <caption>边（滑）坡基本信息一览表</caption>
  <thead>
    <tr >
     <th>结构类型</th>
      <th>结构名</th>
      <th>结构地址</th>
      <th>标段</th>
      <th>结构概述</th>
      <th>监测起止时间</th>
       <th>主要监测内容</th>
       <th>状态</th>
       <th>状态描述</th>
    </tr>
  </thead>
  <tbody>
    
  </tbody>
</table>


 </div>

 <script src="../js/footer.js"></script>
  <script type="text/javascript">
    	
  


 


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
   
    var projectListArr=data[0].PrjInfo;
     var PrjStatus=data[1].PrjStatus;
    $(".prjstatus .alert").text(PrjStatus[0].alert);
    $(".prjstatus .alarm").text(PrjStatus[1].alarm);
    $(".prjstatus .action").text(PrjStatus[2].action);
    $(".prjstatus .normal").text(PrjStatus[3].normal);

var len=projectListArr.length;
   
var longVal = 0;
var latVal = 0;
var ptCount = 0;
 var str="";
for(var i=0;i<len;i++){
         str="<tr>"+
            "<td ><span style='vertical-align: middle'>"+projectListArr[i].prjType+"</span></td>"+
            "<td>"+projectListArr[i].prjName+"</td>"+
            "<td>"+projectListArr[i].prjAddr+"</td>"+
            "<td>"+projectListArr[i].BiaoDuan+"</td>"+
            "<td>"+projectListArr[i].JieGouDes+"</td>"+
            "<td>"+projectListArr[i].startTime+"-"+projectListArr[i].endTime+"</td>"+
            "<td>"+projectListArr[i].JianceNeirong+"</td>"+
             "<td>"+projectListArr[i].statusVal+"</td>"+
             "<td>"+projectListArr[i].StatusDes+"</td>"+
          "</tr>"+
         "<tr>";
   $(".prj").find("tbody").append(str);
  if(len!=null){
    if(projectListArr[i].Longitude!=null){
    addMarkerPoint(projectListArr[i]);
    longVal += Number(projectListArr[i].Longitude);
    latVal += Number(projectListArr[i].Latitude);
    ptCount ++;
    }
  }
}

setZoom(projectListArr);
   })
NewAjax=null;



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

      var StatusVal=_dataJson.statusVal;
    
      var color="#00c280";
    var marker_ico_src="../images/marker_green.png";
    switch(StatusVal){
      case "offline":
      marker_ico_src="../images/marker_red.png";
      color="#e20000";
      break;
      case "normal":
      marker_ico_src="../images/marker_green.png";
      color="#00c280";
      break;
      case "lowpower":
      marker_ico_src="../images/marker_blue.png";
      color="#0098d1";
      break;
      case "alert":
      marker_ico_src="../images/marker_orange.png";
      color="#eda700";
      break;
      case "alarm":
      marker_ico_src="../images/marker_violet.png";
      color="#ea5698";
      break;
      case "action":
      marker_ico_src="../images/marker_deepred.png";
      color="#910e00";
      break;
      default:
      marker_ico_src="../images/marker_green.png";
      color="#910e00";
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
               
  var _iw=new BMap.InfoWindow("<p>&nbsp;</p><p style='font-size:14px;'>"+SensorName+"类 型 :"+_dataJson.prjType+","+StatusVal+"</p><p><a href='javascript:void()'  target='_blank' onclick=\"showgraphics('"+TableName+"','"+_dataJson.SensorType+"','"+_dataJson.groupname+"','"+encodeURI(_dataJson.CurStatus)+"','"+_dataJson.groupname+"','graphic')\"   style='cursor:hand;color:#f08d00;'>查看曲线</a></p><P><a href='javascript:void()'  target='_blank' onclick=\"showgraphics('"+TableName+"','"+_dataJson.SensorType+"','"+_dataJson.groupname+"','"+encodeURI(_dataJson.CurStatus)+"','"+_dataJson.groupname+"','table')\"   style='cursor:hand;color:#f08d00;'>查看数据</a></p>");
   var myLabel = new BMap.Label("<a href='#')>"+SensorName+"</a>");
  myLabel.setStyle({
    "color": "#ccc",
    "border":"border: 0px",
   
    "margin-left":"29px",
     "fontSize":"12px",
    

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

  
function getsensorStatus(){
 var NewAjax=new wtyan();

       NewAjax.data={"sensorGroup":"AllSensorList","PrjName":PrjName,"platform":config.platform};
       var url=config.server+"ajax/queryptinfo.php?jsoncallback=?";
       NewAjax.AjaxGet(url,handleData);
        function handleData(data){

     
           var data_list=data.items;

           var total=data_list.length;
        
         var array3 = SensorTypeProjectArr.concat(SensorTypeArr);
         SensorTypeArr=array3;

         var sensorStrHtml=new Array();
           if(total>0){

             var strHtml="";
               strHtml+="<li style='width:100%; border:none;margin:20px 0px 20px 0px;font-size:14px'>点位：<font size=+2>"+total+" </font>个</li>"
               strHtml+="<li><label>&nbsp;</label><span>总计</span><span>离线</span></li>"
             var offlineTotal=0;
               for(var i=0;i<SensorTypeArr.length;i++){
                  var sensor_num=0;
                   var offline=0;
                    $.each(data_list,function(index,List){
                          if(SensorTypeArr[i]==List.SensorType){
                            sensor_num++;
                            if(List.StatusVal!="Online"){
                               offline++;
                            }
                          }
                      
                       
                    })

                   var  SensorTypeStr=SensorTypeArr[i];
                       for( x in SensorTypeLanStr){
                        if(SensorTypeLanStr[x].toLowerCase()==SensorTypeArr[i].toLowerCase()){

                          SensorTypeStr=SensorTypeLan[x];
                        }
                   
                  
                      }
                      
               
                    strHtml+="<li><label>"+SensorTypeStr+"</label><span>"+sensor_num+"</span><span>"+offline+"</span></li>";

             
             }
           
          $(".baidu_sensor_list ul").html(strHtml);
        } //if(total>0)
       
    }  ///handleData
 NewAjax=null;
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