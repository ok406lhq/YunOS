


 $(".starttime").calendar();
    $(".endtime").calendar(); 
 
 $(".tabletbtn").bind("click",function(){
   $(this).addClass("btn-success");
   $(".graphicbtn").removeClass("btn-success");
     $(".sensordescript").removeClass("btn-success");
    $(".datatable").show();
    $(".graphic").hide();
    $(".descript").hide();
  })
  $(".graphicbtn").bind("click",function(){
  $(this).addClass("btn-success");
  $(".tabletbtn").removeClass("btn-success");
  $(".sensordescript").removeClass("btn-success");
    $(".datatable").hide();
    $(".descript").hide();
    $(".graphic").show();
  })
$(".sensordescript").bind("click",function(){
    $(this).addClass("btn-success");
      $(".tabletbtn").removeClass("btn-success");
      $(".graphicbtn").removeClass("btn-success");
  //art.dialog({title:sensorName+"测点描述",content:document.getElementById('descript'), width: 860,lock:true,top:0});
     $(".descript").show();
     $(".datatable").hide();
     $(".graphic").hide();
})

   document.addEventListener("DOMContentLoaded", function(event){
               
        ImagesZoom.init({
          "elem": ".dataimg"
        });
      }, false);

$(".explodedata").bind("click",function(){
  var dateType=$(".dateType").val();
  var namestr="";
  if(!CheckValIsNull(dateType)){
    var starttime=$(".starttime").val();
    var endtime=$(".endtime").val();
     namestr=staName+"_"+starttime+"_"+endtime;
  }else{
    namestr=staName+"_"+dateType;
  }
  if(JSONData.length<=0){
	  alert("当前数据为空不能导出数据");
  }else{
	  if(sensorType=='Prism'||sensorType=='Crackmeter'){
		  alert("========");
		  JSONToCSV(JSONData, namestr, true);
	  }else{
		  JSONToCSVConvertor(JSONData, namestr, true);
	  }
  }
})





 function querygnssdata(ymw) {
	var numY = null;
	if ($(".isY").is(":checked")) {
		var value = $(".chartY").val();
		if (isNaN(value)) {
			$.myAlert("y轴限定值必须为数字！");
			return;
		}
		numY = value;
	}
	var sensorName = $(".sensornamelist").val();
	endtime = curDateTime();
	var prjName = getCookie("prjName");
	var date = new Date();
	$(".endtime").val(curDateTime());
	$(".dateType").val(ymw);
	tabledata = null;
	$('.datatable').html("");
	if (ymw == "year") {
		var getLastyd = getLastYearYestdy(date);
		$(".starttime").val(getLastyd);
		starttime = getLastyd;

	} else if (ymw == "month") {
		var getLastmd = getLastMonthYestdy(date);
		$(".starttime").val(getLastmd);
		starttime = getLastmd;
	} else if (ymw == "week") {
		var getweekday = getRecentweekDays();
		$(".starttime").val(getweekday);
		starttime = getweekday;

	} else if (ymw == "day") {
		var yestoday = getYestoday(date);
		$(".starttime").val(yestoday);
		starttime = yestoday;

	} else if (ymw == "all") {
		getData(starttime, endtime, sensorName, prjName, "all",numY);
		return;
	}
	getData(starttime,endtime,sensorName,prjName,"default",numY);
}


function querygnss(ymw){
  $(".dateBtn button").removeClass("btn-success").addClass("btn-default");
  $("."+ymw+"data").addClass("btn-success");
  $(".dateType").val(ymw);
   tabledata=null;
  $('.datatable').html("");
  

  var starttime=$(".starttime").val();
  var endtime=$(".endtime").val();
   var datadiffuse=GetDateDiff(starttime,endtime,"day");
         if(datadiffuse<1){
          tip("时间选取错误！",true);
          return false;
         }
  var sensorName=$(".sensornamelist").val();
  sensorName=sensorName.toLowerCase();
    var prjName=getCookie("prjName");
  
   getData(starttime,endtime,sensorName,prjName,ymw);
}


//$(function(){
//
//  UploadPointImages(sensorName);
//  
//})



$(".isY").bind("click",function(){
  if($(".isY").is(":checked")){
     $(".chartY").attr("disabled",false);
     
  }else{
      $(".chartY").attr("disabled",true);
       
      $(".chartY").val("");

  }
})
function saveChartY(){
  var chartY=$(".chartY").val();
if(!CheckValIsNull(chartY))chartY=0;
  var isY=0;
 if($(".isY").is(":checked"))isY=1;

 $.post("./../PointAll",{"oper":"saveChartY","SensorName":sensorName,"chartY":chartY,"isY":isY},function(data){

  if(data>=1){
  
    querydata();
  }
 })
}
function checkdig()
        {
           var value= $(".chartY").val();
          //$(".chartY").val(value.replace(/[^\d]/g,''));//个输入框也要支持输入小数（比如说0.5），当前只能整数 所以注释掉这句代码
        }