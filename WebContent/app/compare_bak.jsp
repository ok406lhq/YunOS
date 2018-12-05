<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/compare.css" />

 <script src="../js/jquery-1.11.0.min.js"></script>
 <script src="../js/config.js"></script>
  <script src="../js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/highstock.js"></script>
  <script src="../js/lhgcalendar.min.js"></script>
  <link href="../css/lhgcalendar.css" rel="stylesheet" type="text/css" />
 <script src="../js/artDialog.source.js"></script>
      <script src="../js/iframeTools.source.js"></script>
          <link rel="stylesheet" href="../js/skins/default.css" />

</head>
<body style="height: 560px">
 <ol class="breadcrumb">
    <li><a href="slope.html"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li><a href="#">数据分析</a></li>
    <li class="active">数据对比</li>
</ol>
<!-- <div class="wrapper"  style="margin-top: -15px">
	<div id="container" style="min-width: 310px; min-height: 420px; margin: 0 auto;clear:both"></div>
	 
	    <div class="panel panel-info">
	     <div class="panel-heading compare-panel">
		   <ul style="width:100%">
		     起始时间：
		     <input type='text' class='start_time notifc' id='project_Sname' readonly="readonly"  > 
		     
		   &nbsp;&nbsp; 结束时间：
		     <input type='text' class='end_time notifc' id='project_Sname' readonly="readonly"   > 

		          <li style="float:right;"> <button onclick="movecheck()" class="btn btn-warning"><i class="fa fa-times-circle"></i> 清除</button>&nbsp;&nbsp;&nbsp;<button onClick="update_date()" class="btn btn-info"><i class="fa fa-refresh"></i> 更新 </button></li>
		         </ul>
		         </div>
		    
		        <ul class="update_ul">
		         <li class="sensorlist"> </li>
		        
		        </ul>

		      <li class="updateBtn" style="text-align:right;">
		         <font color="#FF0000">*</font> <label class="maxsensors">Max. 2 sensors</label> 
		         </li>
		        

	         
	 </div>
	</div>
</div> -->
</body>
</html>

<!-- <script type="text/javascript">

var prjName=getCookie("prjName");
var start_time=getUrlVars()["start_time"];
//start_time ="2017-03-02";
var end_time=getUrlVars()["end_time"];
//end_time="2017-03-24";
	$(function(){
		getPtinfo();
	})
var d = new Date();
        var vYear = d.getFullYear();
        var vMon = d.getMonth() + 1;
       var vDay = d.getDate();
	    vMon=vMon<10 ? "0" + vMon : vMon;
	    vDay=vDay<10 ? "0"+ vDay : vDay;
		 var vDate=vYear+"-"+vMon+"-"+vDay;
		 
		var Lastday= getLastDayOfMonth(vYear,vMon);
		var lDay=vYear+"-"+vMon+"-"+Lastday;
		var preWeek=date2str(vYear,vMon-1,vDay,"pre"); 
		 if(!CheckValIsNull(start_time)){
	        $(".start_time").val(preWeek);
			start_time=preWeek;
		 }else{
			 $(".start_time").val(start_time);
		 }

		  if(!CheckValIsNull(end_time)){
	      $(".end_time").val(curDateTime());
		  end_time=curDateTime();
		 }else{
			 $(".end_time").val(end_time);
		 }
	 
$(".start_time").calendar();
 $(".end_time").calendar(); 
function getPtinfo(){
	
	  var prjName=getCookie("prjName");
     //var data={ptName:"All",prjName:prjName,"./../PointInfo"}
	 $.post("./../PointAll",{oper:"all",prjName:""},function(data){

         var data=eval("("+data+")");
		     //  data=data[0].ptInfo;
			

			 var projectListArr=data; 
			 var sensorStr="";
			 if(projectListArr.length>0){
			 
				  $.each(projectListArr,function(index,project){
					  var groupname=project.groupname;  //sensor
					  var SensorName=project.SensorName;
					  //var StaName=project.StaName.toLowerCase(); 
					  var sorName=SensorName.replace(/-/g, "_");  
					  var sensorID = project.SensorID;
					  //var TableName=StaName+"_"+sorName.toLowerCase(); 
					  var SensorType=project.SensorType;
					  var checked="";
					  if(index<2){
						  checked="checked";
					  }
					  sensorStr+="<div><input type='checkbox' value='"+sensorID+"' id='"+index+"' "+checked+" title='"+SensorType+"' name='sensor' >"+
					              " <label for='"+index+"'>"+SensorName+"</label></div>";
				  })
				       
				  $(".sensorlist").html(sensorStr);
				    var sensorArr=[];
                    var sensortypeArr=[];
				     $("input[name='sensor']:checked").each(function(index, element) {
                        sensorArr.push($(this).val());  
						sensortypeArr.push($(this).attr('title'));
                    });
					 
					  sensor_str=sensorArr.join(",");
					 getData(start_time,end_time,sensorArr,sensortypeArr,prjName);
					 
			 }else{
				 $(".sensorlist").html("No Sensor");
			 }
		})
		
		
}


function update_date(){
	               var sensorArr=[];
                   var sensortypeArr=[];
				     $("input[name='sensor']:checked").each(function(index, element) {
                        sensorArr.push($(this).val());  
						sensortypeArr.push($(this).attr('title'));
                    });
				
					if(sensorArr.length>2){
						art.dialog.alert('最多选取2个监测点!'); 
						
						return false;
					}else if(sensorArr.length==0){
						art.dialog.alert('至少选取一个测点'); 
						
					}
					var start_time=$(".start_time").val();
					var end_time=$(".end_time").val();
					 var datadiffuse=GetDateDiff(start_time,end_time,"day");
				 if(datadiffuse<1){
					art.dialog.alert('时间选取错误！'); 
					return false;
				 }
				  
				  sensor_str=sensorArr.join(",");
                 var prjName=getCookie("prjName");
				  getData(start_time,end_time,sensorArr,sensortypeArr,prjName);
				 
}


  function getData(start_time,end_time,sensorArr,sensortypeArr,prjName){
  	tip("正在查询数据.......");
		   var sensorName=sensorArr.join(",");
          
		   var sensorType=sensortypeArr.join(",");
		   
	               Highcharts.setOptions({
                 global: {  useUTC: false 
                }
            }) 	 		

var data={"start_time":start_time,"end_time":end_time,"sensorName":sensorName,"sensorType":sensorType,"oper":"compare","prjName":prjName} 
			
	 $.post("./../CompareDataServlet",data,function(e){
  
			tip_close();
			var seriesArr=[]; 
			var y=0;
			console.log(e);
			var coltype="spline";
			$.each(e,function(index,adddateList) {
               if(adddateList.length>0){
                  var PointArr=[];
				 for(var i=0;i<adddateList.length;i++){
				 	  var adddate=adddateList[i][0];
				 	var ad=adddate+":00:00";
					 ad=ad.replace(/-/g, "/");
					 var dateList= new   Date(Date.parse(ad));   
					var getTimedate=dateList.getTime()+0*60*60*1000;
                    PointArr[i]=[];
                    PointArr[i][0]=(getTimedate);
					PointArr[i][1]=(adddateList[i][1]);
				 }
				var nameStr=index.split("/");
				   var sensorname=nameStr[0];
				   sensorname=sensorname.toUpperCase();
				   var sensortype=nameStr[1];
				       var uffix="mm";
			           switch(sensortype){
			           	 case "Inclinometer":
			           	 uffix=" mm";
			           	 break;
			           	 case "GNSS":
			           	  uffix=" mm";
			           	 break;
			           	  case "Prism":
			           	  uffix=" mm";
			           	 break;
			           	  case "LRDM":
			           	  uffix=" mm";
			           	 break;
			           	  case "Piezometer":
			           	  uffix=" meter";
			           	 break;
			           	  case "RainGauge":
			           	  uffix=" mm";
			           	coltype="column";
			           	 break;
			           	   case "LoadCell":
			           	  uffix=" Kpa";
			           	 break;
			           	   case "Tiltmeter":
			           		uffix=" 度"; 
			           		break;
			           }
			       seriesArr.push({name:sensorname ,type: coltype,
			      marker : {enabled : true,radius : 4},tooltip: {
                valueSuffix: uffix
                                   },
                 data: PointArr});	
			
			    
		                y++;
				 
                    }
                       
				})


                    var sbutitle=sensorName;
			  	var y_val="mm";
		
		         if(y==0){
                        $("#container").html("<p style='width:100%; float:left;text-align:center;color:#999;margin-top:32px;font-size:16px;'>该时间范围内无对比数据。 </p>");
		         }else{
		         	 charts(seriesArr,sbutitle,y_val);
		         }		
		 })
	  }

		 

function charts(seriesArr,sbutitle,y_val){
	 
    $('#container').highcharts('StockChart',{
        chart: {
            zoomType: 'xy',
        },
        title: {
            text: "数据对照"
        },
        subtitle: {
            text: sbutitle
        },
        xAxis: [{
			tickPixelInterval: 50,
           
			labels: {
                rotation: 90,
                align: 'bottom',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },],
		
        yAxis: [{ // Primary yAxis
		
            labels: {
                format: '{value} '+y_val,
                style: {
                    color: '#89A54E',left: '0px', 
                }
            },
            title: {
                text: '',
                style: {
                    color: '#89A54E'
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '',
                style: {
                    color: '#4572A7'
                }
            },
            labels: {
                format: '{value} mm',
                style: {
                    color: '#4572A7'
                }
            },
            opposite: false
        }],
        tooltip: {
         xDateFormat: '%Y-%m-%d %H',valueDecimals: 2

      },
        legend: {
            enabled:true,
           
            backgroundColor: '#FFFFFF',borderWidth:'1px'  
        },
		scrollbar: {
  enabled: true,
  },
   rangeSelector: {
	          enabled:false,
                selected: 0,
				 buttons: [{
                type: 'hour',
                count: 24,
                text: 'hour'
            },
			
			 {
                type: 'all',
                text: 'All'
            }],
			
            },

		navigator: {
  enabled: true,
                      xAxis: {
                       type: 'datetime',                       
                     labels: {
                       formatter: function() {
                      // var vDate=(this.value);
                       
                     //  return vDate;
                        },
                        align: 'center',
                        
                             }
                           }
  },
		 credits: {
                enabled: false
            },
        series:seriesArr
		
    });
}

function regArr(str,ar){
	var p=0;
	for(var i=0;i<ar.length;i++){
        if(str==ar[i]){
        	p+=1;
        }
	}
	if(p>1){
		return true;
	}else{
		return false;
	}
}
function movecheck(){
	  $("input[name='sensor']").attr("checked",false);
}
</script> -->

 <script src="../js/footer.js"></script>