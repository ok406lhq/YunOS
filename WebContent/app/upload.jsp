<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
 
String prjName = request.getParameter("prjName");
%> 
  <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<html>  
  <head>  
 <link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />


 <script src="../js/jquery-1.11.0.min.js"></script> 

<script src="../js/config.js"></script>
<script src="../js/artDialog.source.js"></script>
<script src="../js/dialog.js"></script>
      <script src="../js/iframeTools.source.js"></script>
       <link rel="stylesheet" href="../js/skins/default.css" />
         <script src="../js/lhgcalendar.min.js"></script>
     <link href="../css/lhgcalendar.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/coord.css" />
 <script type="text/javascript">
 var prjName=getUrlVars()['prjName'];
var  drag = 0;
var move = 0;
var w_type="img";
var prjType=getCookie("prjType");
       var prjAlias=getCookie("prjAlias");
       var prjName=getCookie("prjName");
var SensorNameImgxy=[]; var sensorTypes=[];
 var SensorType;var SensorName; var groupname;
    var Bgimg=('Bgimg');
      $(function(){
         getProjectInfo();
         getSensorType();
         $(".rulback").bind("click",function(){
        	 window.location.href="prjlist.jsp?prjName="+prjName+"&prjAlais="+encodeURI(prjAlias)+"&prjType="+encodeURI(prjType);
        //window.location.href="list.jsp?prjName="+prjName+"&prjAlais="+encodeURI(prjAlias)+"&prjType="+encodeURI(prjType);
         })
      })






function DisplayCoord(event)   
{   

var top,left,oDiv;   
oDiv=document.getElementById("proimg");   
top=getY(oDiv);   
left=getX(oDiv);   
$("#imgX").val($("#imag1").position().left);  
$("#imgY").val($("#imag1").position().top);   

//$("#imgX").val((event.clientX-left+document.body.scrollLeft)  +40);   
//$("#imgY").val((event.clientY-top+document.body.scrollTop) -40);   
  
} 
function getX(obj){   
 var ParentObj=obj;   
 var left=obj.offsetLeft;   
 while(ParentObj=ParentObj.offsetParent){   
  left+=ParentObj.offsetLeft;   
 }   
 return left;   
}   
  
function getY(obj){   
 var ParentObj=obj;   
 var top=obj.offsetTop;   
 while(ParentObj=ParentObj.offsetParent){   
  top+=ParentObj.offsetTop;   
 }   
 return top;   
}   
  
$(document).ready(function()
        {
           
            $("#imag1").mousedown(function(e)//e鼠标事件
            {
                $(this).css("cursor","move");//改变鼠标指针的形状
                
                var offset = $(this).offset();//DIV在页面的位置
                var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离
                var y = e.pageY - offset.top+35;//获得鼠标指针离DIV元素上边界的距离
                $(document).bind("mousemove",function(ev)//绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件
                {
                     $("#imag1").stop();//加上这个之后
                    
                    var _x = ev.pageX - x;//获得X轴方向移动的值
                    var _y = ev.pageY - y;//获得Y轴方向移动的值
                    if(_x>740){_x=740};
                    $("#imag1").css({"left":_x+"px","top":_y+"px"});
                    //$("#imag1").animate({left:_x+"px",top:_y+"px"},0);
                });
                
            });
            
            $(document).mouseup(function()
            {
                $("#imag1").css("cursor","default");
                $(this).unbind("mousemove");
                DisplayCoord(event);
            })
            
            
        })

$(function(){
   
  $.each(SensorNameImgxy,function(index,Imgxy){
           var sensor_Name=$(".sensor_Name").val();
           if(Imgxy[0]==sensor_Name){
                 $("#imgX").val(Imgxy[1]);
                 $("#imgY").val(Imgxy[2]);
                 $("#div1").css({"left":Imgxy[1]+"px","top":Imgxy[2]+"px"});

           }
      })
 

      $(".sensor_Name").bind('change', function(event) {
          var sensor_Name=$(".sensor_Name").val();
			
           $.each(SensorNameImgxy,function(index,Imgxy){
           
           if(Imgxy[0]==sensor_Name){
                 $("#imgX").val(Imgxy[1]);
                 $("#imgY").val(Imgxy[2]);
               
                  $("#imag1").css({"left":Imgxy[1]+"px","top":Imgxy[2]+"px"});
                  var sensorTypeIco="../images/"+sensorTypes[index]+"_g.png";

                $("#imag1").attr("src",sensorTypeIco);
           }
      })
      });

      $(".saveImgsxy").bind("click",function(){

          var imgX= $(".imgX").val();
          var imgY=$(".imgY").val();
          var sensorname=$(".sensor_Name").val();
          var checkIndex=$(".sensor_Name ").get(0).selectedIndex; 
          checkIndex=checkIndex>0?checkIndex-1:checkIndex;
        
         if(sensorname.length==0){
           tip("请选取一个点位来保存！",true);
          return false;
         }
          if(imgX.length<1 || imgY.length<1){
            art.dialog.alert('请先获取坐标');
            return false;
          }

          $.post("./../ProjectUpdate",{"op":"updatePointImageXY","prjName":prjName,"imgX":imgX,"imgY":imgY,"sensorname":sensorname},function(e){
        	 
                  if(e=="ok"){
                              art.dialog({
                            lock: true,
                         
                            opacity: 0.87,  // 透明度
                            content: "保存成功!是否继续获取点位位置？",
                            icon: 'succeed',
                            ok: function () {
                               var xy=[];
                              xy.push(sensorname);
                              xy.push(imgX);
                              xy.push(imgY);
                              SensorNameImgxy[checkIndex]=xy;
                                art.dialog.close();
                                
                            },
                            cancel: function(){
                            
                             var prjType=getCookie("prjType");
                             var prjAlias=getCookie("prjAlias");
                             var prjName=getCookie("prjName");
                              window.location.href="list.jsp?prjName="+prjName+"&prjAlais="+encodeURI(prjAlias)+"&prjType="+encodeURI(prjType);
                             

                            }
                        });
                  }else{

                  }
          });
          
         
      })

      $(".sensorTypee").bind("change",function(){
         $(".imgX").val("");$(".imgY").val("");
      })
 })







      function getProjectInfo(){
        $.post("./../ProjectUpdate",{"prjName":prjName,"op":"query"},function(data){

            var data=data;
        
            var project_image=data.Bgimg;
             var startTime=data.startTime;
                 startTime=CheckValIsNull(startTime)?startTime:"";
           var endTime=data.endTime;
                  endTime=CheckValIsNull(endTime)?endTime:"";
              var prjtypestr=data.prjType;
              //$(".showimg").append("<img src='../uploadfiles/1512358135078.jpg' width='760' >");
              $(".showimg").append("<img src='"+project_image+"' width='760' height='400'>");
              //if(CheckValIsNull(project_image)){
            //    project_image=project_image;
                //$(".showimg").css({"background-image":"url("+project_image+")"})
           //    $(".showimg").append("<img src='"+project_image+"' width='760' >");
           //    setCookie("proimag",project_image);
           // }else{
                // $(".showimg").css({"background-image":"url(../images/up/p1.png)"})
           //      $(".showimg").append("<img src='../images/up/p1.png' width='760'>");

          //  }
             $(".prjinfo tbody").find(".PrjType").val(prjtypestr);
                            $(".prjinfo tbody").find(".PrjAlias").val(data.prjAlais);
                            $(".prjinfo tbody").find(".prjAddr").val(data.prjAddr);
                            $(".prjinfo tbody").find(".JieGouDes").val(data.JieGouDes);
                            $(".prjinfo tbody").find(".JianceNeirong").val(data.JianceNeirong);
                            $(".prjinfo tbody").find(".jingdu").val(data.Longitude);
                            $(".prjinfo tbody").find(".weidu").val(data.Latitude);
                            $(".prjinfo tbody").find(".starttime").val(startTime);
                            $(".prjinfo tbody").find(".endtime").val(endTime);
                             $(".prjinfo tbody").find(".manager").val(data.lxr1);
                               $(".prjinfo tbody").find(".monitorer").val(data.lxr2);
                        
        })
      }
function getSensorType(){
	$.post("./../PointInfo",{"prjName":prjName,"op":"byprjName"},function(data){

      var  data=eval("(" + data + ")");
        data=data[0].ptInfo;

        if(data.length>0){
            var optionStr="";
            $.each(data,function(index,list){
               var img_x=list.imgX;
               var img_y=list.imgY;
                var sensorname=list.staName;
                var sensorID = list.SensorID;

               var xy=[];
                xy.push(sensorID);
                xy.push(img_x);
                xy.push(img_y);
                SensorNameImgxy[index]=xy;
               sensorTypes.push(list.sensorType.toLowerCase());
               optionStr+="<option value='"+sensorID+"'>"+sensorname+"</option>";

            }) 

            $(".sensor_Name").append(optionStr);
        }
	})
}
function uploadshow(){
    $(".imageformupload").toggle();
    //art.dialog.open("crop.jsp", {title: "图片编辑上传", width: 980,height:580,lock:true,top:0});
    
}

 </script>
  
 <body>
 <div class="wrapper">
<div class='coodsconditions'>
 <ul class="senornode">

 
 <label for='sensor_Name' class='sensor_Name_str'>点位名称：</label>
  <select id='sensor_Name' class='sensor_Name' name='sensor_Name'>
       <option value="">--选取点位--</option>
  </select> 
   &nbsp;<font class="xcoordstr"> x坐标: &nbsp;</font>
   <input type='text' id="imgX" class="imgX">  &nbsp;<font class="ycoordstr"> y坐标:&nbsp; </font>
    <input type='text' id="imgY" class="imgY"> 
    <%
    if(StringUtil.isIn("edit",role)){out.print("&nbsp;<button class='saveImgsxy btn btn-info'>保存</button>");}
    %>
   
      <% //if(StringUtil.isIn("add",role)){out.print(" <button  class='btn btn-success'  onclick='uploadshow()'>&nbsp;上传图片&nbsp;</button>");}
     
  
 %>
   <% //if(StringUtil.isIn("adding",role)){out.print(" <a href='crop.jsp'><button  class='btn btn-success'  >&nbsp;上传图片&nbsp;</button></a>");}
     
  
 %>
 </ul>
<ul class="imageformupload">
 <form name="form_uploadImg" action="./../UploadProcessorServlet?prjName=<%=prjName %>&type=project" method="POST" enctype="multipart/form-data" > 
        <div class="form-group">
        <label class="sr-only" for="inputfile">文件输入</label>
        <input type="file"  id="uploadFileInput" name="uploadFileInput" class="input" style="width: 180px">
        <input type="hidden" value="project" name="type">
        <button type="submit" class="btn btn-success" >上传</button>
    </div>
   
   
 </form>  
 </ul>
</div>
 

<div id="pro_bg_img" class="PrjInfo">

   <div class="showimg pro_img" id="proimg" >
  <img src="../images/marker_green.png"  id="imag1" width=22>


   
   </div> 
   
   <div  class="coord_text" style="color: #000; margin-top: 450px;">移动图标到适合位置，获取坐标值。</div>
 <button class="btn btn-default rulback" ><i class="fa fa-arrow-left"></i> 返回</button>
 
 
    <!--  <div class="baseinfo">
       <table class="table table-bordered table-hover prjinfo">
                <caption>
                编辑结构基本信息

                </caption>
                
                <tbody>
                                 <tr><td class='p1'>结构名称</td><td  alt="PrjAlias"><input type="text" class='PrjAlias inputedit'></td></tr>
                                   <tr><td class='p1'>结构类型</td><td  alt="PrjType"><input type="text" class='PrjType inputedit'></td></td></tr>
                                  <tr><td class='p1'>结构地址</td><td alt="PrjAddr"><input type="text" class='prjAddr inputedit'></td></tr>
                                   <tr><td class='p1'>结构概述</td><td  alt="JieGouDes"><input type="text" class='JieGouDes inputedit'></td></tr>
                                   <tr><td class='p1'>监测内容</td><td ><input type="text" class='JianceNeirong inputedit'></td></tr>
                                   <tr><td class='p1'>经度</td><td ><input type="text" class='jingdu inputedit'></td></tr>
                                   <tr><td class='p1'>维度</td><td ><input type="text" class='weidu inputedit'></td></tr>
                                   <tr><td class='p1' rowspan='2'><span style="margin-top:20px;float: left;">监测时间</span></td><td ><label style="line-height: 2.2em">起</label> <input type="text" class='starttime' readonly></td></tr>
                                   <tr><td > <label style="line-height: 2.2em">止</label><input type="text" class='endtime' readonly></td></tr>
                                   <tr><td class='p1' rowspan='2'><span style="margin-top:20px;float: left;">监测联系人</span></td><td ><input type="text" class='manager lxr1 inputedit'></td></tr>
                                     <tr ><td ><input type="text" class='monitorer lxr2 inputedit'></td></tr>
                                   
                </tbody>
              </table>
               <button type="button" class="btn btn-info saveinfo" style="float: right; "> &nbsp;保存信息 &nbsp;</button>
    </div> -->
</div>


</div>

</body>
<script type="text/javascript">
  function showcrop(){
    art.dialog.open("cropsample.html", {title: "如何裁剪图片？", width: 780,height:760,lock:true,top:0});
  }
$(".starttime").calendar();
$(".endtime").calendar(); 
  $(function(){        
     
   


 $(".saveinfo").bind("click",function(){
  var PrjAlias=$(".PrjAlias").val();
   var PrjType=$(".PrjType").val();
    var PrjAddr=$(".prjAddr").val();
var JieGouDes=$(".JieGouDes").val();
var JianceNeirong=$(".JianceNeirong").val();
var jingdu=$(".jingdu").val();
var weidu=$(".weidu").val();
var starttime=$(".starttime").val();
var endtime=$(".endtime").val();
var lxr1=$(".lxr1").val();
var lxr2=$(".lxr2").val();

if(starttime.length<1){
	tip("请选择起始时间",true);
	$(".starttime").focus();
	return false;
}
if(endtime.length<1){
	tip("请选择起始时间",true);
	$(".endtime").focus();
	return false;
}

$.post("./../ProjectUpdate",{PrjAlias:PrjAlias,PrjType:PrjType,PrjAddr:PrjAddr,JieGouDes:JieGouDes,JianceNeirong:JianceNeirong,starttime:starttime,endtime:endtime,lxr1:lxr1,lxr2:lxr2,prjName:prjName,jingdu:jingdu,weidu:weidu,op:"update"},function(data){
       if(parseInt(data)==1){
         tip("信息修改保存成功！",true);
       }else{
        tip("信息修改保存失败！",true);
       }
})
 })
})  


</script>