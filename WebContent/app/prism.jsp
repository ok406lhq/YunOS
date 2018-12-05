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
               <button type="button" class="btn btn-info" onclick="querydata()" style="margin-left: 20px;"> 查询</button>
                        &nbsp; &nbsp; &nbsp; &nbsp; 
                <div class="btn-group">
                <button type="button" class="btn btn-default weekdata" onclick="querygnssdata('day')">最近一天</button>
              <button type="button" class="btn btn-default weekdata" onclick="querygnssdata('week')">最近一周</button>
              <button type="button" class="btn btn-default monthdata" onclick="querygnssdata('month')">最近一月</button>
              <button type="button" class="btn btn-default yeardata" onclick="querygnssdata('year')">最近一年</button>
              
                      </div>
             </div>
           <div class="data_shw">
             <div class="sensorname_img">
                 <div class="dataimg"><img src="../images/up/p2.png" id="SenosrImage" width="230" /></div> 
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
var PrjName;
var tbale;
var name=getUrlVars()["name"];
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
              global: { useUTC: false }
    })   

      var dnorth=[];
      var deast=[];
       var dup=[];
      var timeArr=[];
   
     $.post("./../PrismResultSevrlet",{"sensorName":sensorName,"starttime":starttime,"endtime":endtime,"prjName":PrjName},function(data){
       tip_close();
       if(!CheckValIsNull(data)){
        tip("数据查询错误！",true);
       }
       var data=eval("("+data+")");
      
        
           JSONData=data;
          if(data.length>0){
              for(var i=0;i<data.length;i++){
                if(i%2!=0){
               
                 var Jd=extend(data[i-1],data[i],0);
                  tableData.push(Jd);
                }
                 if(i==data.length && i%2==0){
                  
                  tableData.push(data[i]);
                 }
                 if(data.length==1){
                   var Jd=extend(data[0],data[0],1);
                     tableData.push(Jd);
                 }
              }
           $(".datatable").append('<div id="example_table" ></div>');
             tabledata=  $('#example_table').columns({
           data: tableData,
               schema: [
                    {"header":"日期时间", "key":"asdatetime"},
                    {"header":"东方向", "key":"aeast"},
                    {"header":"北方向", "key":"anorth"},
                    {"header":"rl", "key":"arl"},
                    {"header":"日期时间", "key":"asdatetime_"},
                    {"header":"东方向", "key":"aeast_"},
                    {"header":"北方向", "key":"anorth_"},
                    {"header":"rl", "key":"arl_"}
                 
                    
                ]
            });
              if(data.length<=2){
            tickPixel=320;
          }
            if(sType=="day"){
             var startTime=$(".start_time").val();
             var endTime=$(".end_time").val(); 
               
            }
            var i=0;
        $.each(data,function(index,dataArr) {
          var ad=dataArr.asdatetime;
           ad=ad.replace(/-/g, "/");
          var dateList= new   Date(Date.parse(ad));   
           timeArr.push(ad.substr(5,11));
          var getTimedate=dateList.getTime();
         
        
          dnorth[i]=[];
          dnorth[i][0]=getTimedate;
          
          dnorth[i][1]=Number(dataArr.anorth);
          
          deast[i]=[];
          deast[i][0]=getTimedate;
          deast[i][1]=Number(dataArr.aeast);
                dup[i]=[];
          dup[i][0]=getTimedate;
          dup[i][1]=Number(dataArr.arl);
        
           i++;
        })
        var num;
      num=$(".chartY").val();
       if(!CheckValIsNull(num) || parseInt(num)==0){
          num=null;
          }
       name = decodeURI(name);
         charts(dnorth,deast,dup,timeArr,sensorName,num);
        }else{
          $("#container").html("<font size=3 class='nodata'>"+ sensorName + "  无该时段查询数据。</font>");
          $('.datatable').html("<font size=3 class='nodata'>无该时段查询数据。</font>");

        }
      
        
          
         
  
      })
    }
    
      
   
     
 function charts(dnorth,deast,dup,timeArr,sensorName,num){
  
    $('#container').highcharts('StockChart',{
        chart: {
            zoomType: 'xy',
        },
        title: {
            text: name + '位 移'
        },
        subtitle: {
            text: statusDes
        },
        xAxis: [{
      tickPixelInterval: 60,
            categories:timeArr,
              type: 'date',
      labels: {
                rotation: 90,
                align: 'bottom',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },],
    
        yAxis: [{ max:num,title: {
        
                 
                text: ' 位 移(单位: mm)',
                }}, { // Secondary yAxis
                max:num,
            title: {
                text: name+   '单位(mm)',
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
         xDateFormat: '%Y-%m-%d %H:%M',valueDecimals: 2
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
            name: '北方向(毫米)',
            color: '#4572A7',
            type: 'spline',lineWidth:1,
            yAxis: 1,
            data: dnorth, marker : {enabled : true,radius : 5},shadow : false,
            tooltip: {
                valueSuffix: ' mm',valueDecimals: 2
            },
         
        }, {
            name: '东方向(毫米)',
            color: '#89A54E',lineWidth:1,
            type: 'spline',
            data: deast, marker : {enabled : true,radius : 5},shadow : false,
            tooltip: {
                valueSuffix: 'mm',valueDecimals: 2
            },

        },
        {
            name: 'rl',
            color: '#009ae1',
            type: 'spline',lineWidth:1,
            data: dup, marker : {enabled : true,radius : 5},shadow : false,
            tooltip: {
                valueSuffix: 'mm',valueDecimals: 2
            },
            
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
          tip('日期选取错误！',true); 
          return false;
         }
  var sensorName=$(".sensornamelist").val();
  sensorName=sensorName.toLowerCase();
  var prjName=getCookie("prjName");
  getData(starttime,endtime,sensorName,prjName,"");
  }

  
</script>
  <script src="../js/pointgraphdata.js"></script>
 <script src="../js/footer.js"></script>

