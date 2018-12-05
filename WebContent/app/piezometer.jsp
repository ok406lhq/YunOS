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
<script src="../js/jquery-1.11.0.min.js"></script>
 <script src="../js/config.js"></script>
  <script src="../js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/highstock.js"></script>
  <script src="../js/lhgcalendar.min.js"></script>
   <script src="../js/jquery.columns.min.js"></script>
  <link href="../css/lhgcalendar.css" rel="stylesheet" type="text/css" />
    <link href="../css/clean.css" rel="stylesheet" type="text/css" />
      <script src="../js/artDialog.source.js"></script>
      <script src="../js/iframeTools.source.js"></script>
      <script src="../js/scale.js"></script>
   
    <script src="../js/ajaxfileupload2.js"></script>
      <link href="../css/Huploadify.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="../js/skins/default.css" />
         <script src="../js/getSensorName.js"></script>
</head>
<body>
	<div id="descript" class="descript" ></div>  
 <div class="wrapper"  style="margin-top: -10px">
       <div class="datalist">
              <div class="btn-group" style="float: right;" >
              <button type="button" class="btn btn-default btn-success graphicbtn"><i class="fa fa-bar-chart"></i>  &nbsp;&nbsp;曲线显示&nbsp;</button>
              <button type="button" class="btn btn-default tabletbtn"><i class="fa fa-table"></i>  &nbsp;&nbsp;表格显示&nbsp;</button>
              <button type="button" class="btn btn-default explodedata"><i class="fa fa-save"></i>   &nbsp;&nbsp;导出数据&nbsp;</button>
               </div>
               
		         <div class="param">
                <label> 测点编号:&nbsp;</label>
                <select class="sensornamelist" id="sensornamelist"></select>
               
		         	 <label>&nbsp;&nbsp;&nbsp;时间区间：</label>
		         	 <input type="text" value="2017-07-01 " class="starttime" name="starttime" > <label> 至 </label>
		         	 <input type="text" value="2017-07-05 " class="endtime" name="endtime" >
                &nbsp; &nbsp;
               <button type="button" class="btn btn-info" onclick="querydata()" style="margin-left: 20px;display:none"> 查询</button>
                        &nbsp; &nbsp; &nbsp; &nbsp; 
		         	  <div class="btn-group dateBtn">
                <button type="button" class="btn btn-success hourdata" onclick="querygnss('hour')">&nbsp;小时查询&nbsp;</button>
					    <button type="button" class="btn btn-default daydata" onclick="querygnss('day')">&nbsp;&nbsp;天统计查询&nbsp;&nbsp;</button>
					    <button type="button" class="btn btn-default monthdata" onclick="querygnss('month')">&nbsp;&nbsp;月统计查询&nbsp;&nbsp;</button>
					    <button type="button" class="btn btn-default yeardata" onclick="querygnss('year')">&nbsp;&nbsp;年统计查询&nbsp;&nbsp;</button>
					    
                      </div>
		         </div>
		          <div class="data_shw">
              <div class="sensorname_img">
                 <div class="dataimg"><img src="" id="SenosrImage" width="230" /></div> 
                 <%
    if(StringUtil.isIn("edit",role)){out.print("<div id='upload'></div>");}
    %>
                
              </div>
		    	  
		    	  <div id="container" class="graphic"></div>
            <div class="datatable"></div>
             <div id="descript" class="descript" ></div>  
              
		    	  </div>
		 
		
  
</div>
 </div>
 <section class="imgzoom_pack">
        <div class="imgzoom_x"><img src="../images/close.png"></div>
        <div class="imgzoom_img"><img src="" /></div>
      </section>
      <input type="hidden" class="dateType">
</body>
</html>


<script type="text/javascript">
var JSONData,tableData=[];
var sType="";

var PrjName=getCookie("prjName");
var statusDes=getCookie("statusDes");
var tbale;
var sensorName=getUrlVars()["sensorname"];
var sensorType=getUrlVars()["sensorType"];
var kalman;var tabledata=null;
var weekago=getRecentweekDays();
var starttime=getUrlVars()["starttime"];
var endtime=getUrlVars()["endtime"];
getsensorNames(sensorType,PrjName,sensorName);
getsensorimages();
$(".graphicbtn").addClass("btn-success");
   if(!CheckValIsNull(starttime)){
          $(".starttime").val(weekago);
           starttime=weekago;
     }else{
         $(".starttime").val(starttime);
     }
     if(!CheckValIsNull(endtime)){
        $(".endtime").val(curDateTime());
          endtime=curDateTime();
     }else{
          $(".endtime").val(endtime);
     }
    

 

  getData(starttime,endtime,sensorName,PrjName,"");
  
    

      function getData(starttime,endtime,sensorName,PrjName,ymw){
    	  tableData=[];
         tip("正在查询数据......");
    Highcharts.setOptions({
          global: {  useUTC: false //关闭UTC
}
})     
      var hyetalArr=[];
      var temperatureArr=[];
      var timeArr=[];
      var tickPixel=40;
   
      var tipFormat='%Y-%m-%d %H';
      if(ymw=="day"){
        tipFormat='%Y-%m-%d';
      }
     if(ymw=="month"){
        tipFormat='%Y-%m';
      }
      if(ymw=="year"){
        tipFormat='%Y';
      }
    $.post("./../piezometerResultServlet",{"starttime":starttime,"endtime":endtime,"sensorName":sensorName,"prjName":PrjName,"ymw":ymw},function(data){
     tip_close();
     var datalist=eval("("+data+")");

          JSONData=datalist;
        if(datalist.length>0){
            for(var i=0;i<datalist.length;i++){
                if(i%2!=0){
               
                 var Jd=extend(datalist[i-1],datalist[i],0);
                  tableData.push(Jd);
                }
                 if(i==datalist.length && i%2==0){
                  tableData.push(datalist[i]);
                 }
                 if(datalist.length==1){
                	 var Jd=extend(datalist[0],datalist[0],1);
                     tableData.push(Jd);
                 }
              }
           $(".datatable").append('<div id="example_table" ></div>');
             tabledata=  $('#example_table').columns({
           data: tableData,
                schema: [
                    {"header":"日期时间", "key":"date"},
                    {"header":"水位高(meter)", "key":"wheight"},
                    {"header":"温度(°C)", "key":"temperature"},
                    {"header":"日期时间", "key":"date_"},
                    {"header":"水位高(meter)", "key":"wheight_"},
                    {"header":"温度(°C)", "key":"temperature_"}
                 
                    
                ]
            });
          if(datalist.length<=2){
            tickPixel=320;
          }
            
            var i=0;
        $.each(datalist,function(index,dataArr) {
          var ad=dataArr.date+":00:00";
         if(ymw=="day"){ad=dataArr.date+" 00:00:00";}
         if(ymw=="month"){ad=dataArr.date+"-01 00:00:00";}
          if(ymw=="year"){ad=dataArr.date+"-12-01 00:00:00";}
           ad=ad.replace(/-/g, "/");
         
          
          var dateList= new   Date(Date.parse(ad));   
           timeArr.push(dataArr.adddate);
          var getTimedate=dateList.getTime()+0*60*60*1000;
            
          hyetalArr[i]=[];

          hyetalArr[i][0]=getTimedate;
          //geodnArr[i][0]=Date.UTC(dateList.getFullYear(), dateList.getMonth(), dateList.getDate());
          hyetalArr[i][1]=Number(dataArr.wheight);
          // timeArr.push(dataArr.adddate);
          temperatureArr[i]=[];
          temperatureArr[i][0]=getTimedate;
          temperatureArr[i][1]=Number(dataArr.temperature);
           i++;
        })
        var num;
      num=$(".chartY").val();
       if(!CheckValIsNull(num) || parseInt(num)==0){
          num=null;
          }
         charts(hyetalArr,temperatureArr,timeArr,tickPixel,tipFormat,num);
        }else{
          $("#container").html("<font size=3 class='nodata'>"+ sensorName + "  无该时段查询数据。</font>");
          $('.datatable').html("<font size=3 class='nodata'>无该时段查询数据。</font>");
        }
      
        
        
      })
    
      
    }
     
 function charts(hyetalArr,temperatureArr,timeArr,num){
  
    $('#container').highcharts('StockChart',{
        chart: {
            zoomType: 'xy',
        },
        title: {
            text: sensorName + ' 水位高'
        },
        subtitle: {
            text: statusDes
        },
        xAxis: [{
      tickPixelInterval: 60,
            categories:timeArr,
      type: 'datetime',
      labels: {
                rotation: 90,
                align: 'bottom',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                },
                formatter: function () {  
                          
                            return Highcharts.dateFormat('%m-%d %H', this.value); 

                             
                        } ,
            }
        },],
    
        yAxis: [{ // Primary yAxis
          max:num,
      min:0,
            labels: {
                format: '{value}°C',
                style: {
                    color: '#89A54E',left: '0px', 
                }
            },
            title: {
                text: '温度 ( 单位:°C)',
                style: {
                    color: '#89A54E'
                }
            }
        }, { // Secondary yAxis
         
            title: {
                text: '水位高(单位: meter)',
                style: {
                    color: '#4572A7'
                }
            },
            labels: {
                format: '{value}',
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
        series: [{
            name: '水位高',
            color: '#4572A7',
            type: 'spline',
            yAxis: 1,
            data: hyetalArr, marker : {enabled : true,radius : 4},shadow : false,lineWidth:1,
            tooltip: {
                valueSuffix: ' m'
            },
         
        }, {
            name: "温度",
            color: '#89A54E',
            type: 'spline',
            data: temperatureArr, marker : {enabled : true,radius : 4},shadow : false,lineWidth:1,
            tooltip: {
                valueSuffix: '°C'
            }
        }]
    });
}

 
  
function querydata(){
  tabledata=null;
  $(".datatable").html("");
  var starttime=$(".starttime").val();
  var endtime=$(".endtime").val();
   var datadiffuse=GetDateDiff(starttime,endtime,"day");
         if(datadiffuse<1){
          tip("时间选取错误！",true);
          return false;
         }
  var sensorName=$(".sensornamelist").val();
  sensorName=sensorName.toLowerCase();

  var PrjName=getCookie("prjName");
  getData(starttime,endtime,sensorName,PrjName,"");
  }

  


</script>
 <script src="../js/pointgraphdata.js"></script>
 <script src="../js/footer.js"></script>

