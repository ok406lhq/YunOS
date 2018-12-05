<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="session.jsp"%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="utf-8">
<title>sflgauge</title>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../layui/css/layui.css" />

<script src="../js/jquery-3.3.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/config.js"></script>
<script src="../layui/layui.all.js"></script>
<script src="../js/echart/echarts.min.js"></script>
<script src="../js/getSensorName.js"></script>

<style>
body {
	height: 100%;
	width: 100%;
}

.clearfix_w {
	padding: 0px 10px 0px 20px;
}

.clearfix_hr {
	padding: 0px 0px 0px 5px;
}

.static_input {
	width: 120px;
	height: 38px;
}

.static_select {
	width: 100px;
	height: 38px;
}

.static_input_y {
	width: 50px;
	height: 38px;
}

.static_button {
	height: 38px;
}

.static_checkbox {
	width: 38px;
	height: 38px;
	vertical-align: bottom;
}

.search_form {
	padding-top: 20px;
}

.time_search {
	padding-left: 102px;
}

.temp_to {
	padding-left: 10px;
}

.no_data {
	font-size: 20px;
	margin-left: 30%;
}

table {
	cellspacing: 0;
	*border-collapse: collapse; /* IE7 and lower */
	border-spacing: 0;
	width: 100%;
}

.bordered tr:hover {
	background: #fbf8e9;
	-o-transition: all 0.1s ease-in-out;
	-webkit-transition: all 0.1s ease-in-out;
	-moz-transition: all 0.1s ease-in-out;
	-ms-transition: all 0.1s ease-in-out;
	transition: all 0.1s ease-in-out;
}

.bordered th {
	padding: 7px;
	text-align: center;
	cellspacing: 0;
	width: 100px;
}

.bordered td {
	padding: 7px;
	text-align: center;
	cellspacing: 0;
}

.bordered th {
	background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc),
		to(#dce9f9));
	background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: -moz-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: -ms-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: -o-linear-gradient(top, #ebf3fc, #dce9f9);
	background-image: linear-gradient(top, #ebf3fc, #dce9f9);
}

.bordered td:first-child, .bordered th:first-child {
	border-left: none;
}

.bordered  tr:nth-of-type(2n) {
	background: #FFFFFF;
	cursor: pointer;
}

.bordered  tr:nth-of-type(2n+1) {
	background: #F7FAFC;
	cursor: pointer;
}

.bordered  tbody tr:hover {
	background: #fbf8e9;
	-o-transition: all 0.1s ease-in-out;
	-webkit-transition: all 0.1s ease-in-out;
	-moz-transition: all 0.1s ease-in-out;
	-ms-transition: all 0.1s ease-in-out;
	transition: all 0.1s ease-in-out;
}
</style>

</head>

<body>
	<div class="row clearfix clearfix_w">
		<div class="col-md-12 column" id="header">
			<form role="form" class="form-inline search_form">
				<div class="form-group">
					<label for="" class="temp_to">固定&nbsp;Y轴：</label> <input
						type="checkbox" checked="checked" class="static_checkbox"
						class="isY" name="isY" id="isY" /> <input type="text"
						class="form-control-static static_input_y chartY" />
				</div>
				<div class="form-group">
					<label for="" class="temp_to">测点编号：</label> <select
						class="form-control-static static_select" id="station">
					</select>
				</div>
				<div class="form-group">
					<label for="" class="temp_to">时间区间：</label> <input type="text"
						class="starttime form-control-static J-lay-date-starttime static_input"
						name="starttime" id="starttime">
				</div>
				<div class="form-group">
					<label for="">至</label> <input type="text"
						class="endtime form-control-static J-lay-date-endtime static_input"
						name="endtime" id="endtime">
				</div>
				<button type="button" class="btn btn-info static_button"
					id="queryBtn" data-toggle="tooltip" data-placement="bottom"
					title="查询">
					<span class="fa fa-search"></span>
				</button>
				<button type="button"
					class="btn btn-default static_button timeBtn"
					name="day" data-toggle="tooltip" data-placement="bottom"
					title="最近一天">天</button>
				<button type="button" class="btn btn-default btn-primary static_button timeBtn"
					name="week" data-toggle="tooltip" data-placement="bottom"
					title="最近一周">周</button>
				<button type="button" class="btn btn-default static_button timeBtn"
					name="month" data-toggle="tooltip" data-placement="bottom"
					title="最近一月">月</button>
				<button type="button" class="btn btn-default static_button timeBtn"
					name="year" data-toggle="tooltip" data-placement="bottom"
					title="最近一年">年</button>
				<button type="button" class="btn btn-default static_button timeBtn"
					name="timeAll" data-toggle="tooltip" data-placement="bottom"
					title="全部">全</button>

			</form>
			<br />
			<!-- <div class="col-md-12 column" align="center">
                	 <form role="form" class="form-inline">
                       <div class="btn-group time_search">
                            <button type="button" class="btn btn-default btn-primary static_button timeBtn" name="day">最近一天</button>
                            <button type="button" class="btn btn-default static_button timeBtn" name="week">最近一周</button>
                            <button type="button" class="btn btn-default static_button timeBtn" name="month">最近一月</button>
                            <button type="button" class="btn btn-default static_button timeBtn" name="year">最近一年</button>
                            <button type="button" class="btn btn-default static_button timeBtn" name="timeAll">全部</button>
                        </div>
                    </form>
                </div> -->

		</div>
	</div>
	<div class="row clearfix clearfix_hr">
		<div class="col-md-12 column" id="header">
			<hr />
		</div>
	</div>
	<div class="row clearfix clearfix_w">
		<div class="col-md-2 column" id="content_left">
			<img class="img-responsive" src="" id="SenosrImage" />
			<!-- ../images/1530865433.png -->
		</div>
		<div class="col-md-10 column" id="content_right"></div>
		<div class="col-md-10 column" id="content_right_no_data"
			style="display: none">
			<strong class="no_data">该时段暂无数据</strong>
		</div>
	</div>


	<script type="text/javascript">
	$(document).ready(function () {
		//alert($("#content_right").width());
	    layui.laydate.render({
	        elem: '.J-lay-date-starttime',
	        value: daysJian(6),
	        //value: daysJian(1),
	        max: -1,//7天后
	        isInitValue: false, //是否允许填充初始值，默认为 true
	        done: function (value, date) {
	            var pElem = this.elem;
	        }
	    });
	    layui.laydate.render({
	        elem: '.J-lay-date-endtime',
	        value: new Date(),
	        max: 0,//7天后
	        isInitValue: false, //是否允许填充初始值，默认为 true
	        done: function (value, date) {
	            var pElem = this.elem;
	        }
	    });
	});
	
	//时间选择插件
	Date.prototype.Format = function (fmt) {
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	var date= new Date().Format("yyyy-MM-dd");//Format("输入你想要的时间格式:yyyy-MM-dd,yyyyMMdd")
	//时间获取
	function daysJian(params){
		  var date = new Date();//获取当前时间
		  date.setDate(date.getDate()-params);//设置天数 -1 天
		  var time = date.Format("yyyy-MM-dd");
		  return time;
	} 

	
	var PrjName = getCookie("prjName");
	var statusDes = getCookie("statusDes");
	var sensorName = getUrlVars()["sensorname"];
	var staName = getUrlVars()["staName"];//设备名
	var name = getUrlVars()["name"];
	//var sensorType = getUrlVars()["sensorType"];
	var sensorType = "TemperatureMeter";
	var tempSensor = "";
	if (!CheckValIsNull(sensorName)) {
		tempSensor = $("#station").val();
	} else {
		tempSensor = sensorName;
		
	}
	//console.log(PrjName+"--"+statusDes+"--"+sensorName+"--"+staName+"--"+name+"--"+sensorType);
	//监测点
	var siteName = "";
	/* 项目监测点列表 */
	var data = {type : "stationAll", prjId : PrjName, sensorType:sensorType};
	$.post("./../TemperatureMeterServlet",data,function(data){
		var data = eval(data);
		var htmlStr = "";
		$.each(data,function(index,result){
			htmlStr += "<option name='"+result.sSiteName+"' value='"+result.nSensorID+"' >"+result.sSiteName+"</option>";
			if(sensorName == result.nSensorID){
				siteName = result.sSiteName;
			}
		});
		$("#station").append(htmlStr);
	});
	
	
	$(function(){
		$("[data-toggle='tooltip']").tooltip(); //提示工具
		var tempH = 600;
		var header = tempH * 0.15;//$(document).height() 头部导航
		var content = tempH * 0.70;  //内容
		$("#header").css({"height": header+"px"});
		$("#content_left").css({"height": content+"px"});
		$("#content_right").css({"height": content+"px"});
		$("#SenosrImage").css({"padding-top": "10px" });
		var myChart = null;
		var JSONData = [];
		var descDatalist = [];//倒序排列数据
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		
		if(tempSensor != $("#station").val()){
			siteName = staName;
		}
		dataLoad(null,PrjName,starttime,endtime,siteName,tempSensor);
		
		
		$("#station").change(function(){
			tempSensor = $("#station").val();
		});
		
		$("#isY").click(function() {
			var nn = $("#isY").is(":checked");
			if(nn == true) {
				$(".chartY").val("");
				$(".chartY").removeAttr("disabled");
			}
			if(nn == false) {
				$(".chartY").val("自适应");
				$(".chartY").attr("disabled","disabled");
			}
		})
		
		//固定Y轴
		function isY(){
			var numY = null;
	        if ($("#isY").is(":checked")) {
	        	//alert("我进来了");
	            var value = $(".chartY").val();
	            if (!value || isNaN(value)) {
	                alert("Y轴限定值必须为数字！");
	                return;
	            }
	            numY = value;
	        }
			return numY
		}
		
 		//查询
		$("#queryBtn").click(function(){
			var starttime = $("#starttime").val();
			var endtime = $("#endtime").val();
			siteName = $("#station").find("option[value='"+tempSensor+"']").attr("name");
			dataLoad(isY(),PrjName,starttime,endtime,siteName,tempSensor);
		}); 
		
		/* 时间范围查询 */
		$(".timeBtn").each(function(){
			$(this).click(function(){
				if (!$(this).hasClass("btn-primary")) {
					$(this).addClass("btn-primary").siblings("button").removeClass("btn-primary");
		        }
				var endtime = $("#endtime").val();
				siteName = $("#station").find("option[value='"+tempSensor+"']").attr("name");
				var date = new Date();
				var timeLength = $(this).attr("name");
				switch (timeLength) {
				case "day":
					$("#starttime").val(daysJian(1));
					starttime = daysJian(1);
					endtime = daysJian(0);
					dataLoad(isY(),PrjName,starttime,endtime,siteName,tempSensor);
					break;
				case "week":
					$("#starttime").val(getRecentweekDays(date));
					starttime = getRecentweekDays(date);
					endtime = daysJian(0);
					dataLoad(isY(),PrjName,starttime,endtime,siteName,tempSensor);
					break;
				case "month":
					$("#starttime").val(getLastMonthYestdy(date));
					starttime = getLastMonthYestdy(date);
					endtime = daysJian(0);
					dataLoad(isY(),PrjName,starttime,endtime,siteName,tempSensor);
					break;
				case "year":
					$("#starttime").val(getLastYearYestdy(date));
					starttime = getLastYearYestdy(date);
					endtime = daysJian(0);
					dataLoad(isY(),PrjName,starttime,endtime,siteName,tempSensor);
					break;
				default:
					$("#starttime").val("1970-01-01");
				    starttime = "1970-01-01";
				    endtime = daysJian(0);
				    dataLoad(isY(),PrjName,starttime,endtime,siteName,tempSensor);
					break;
				}
			});
		});
		
		
		
		//数据加载
		function dataLoad(numY,PrjName,starttime,endtime,siteName,tempSensor){
			getsensorimages(tempSensor);
			var dom = document.getElementById("content_right");
			var myChart = echarts.init(dom);
			var colors = ['#FD2446','#248EFD','#C916F2','#6669B1'];//自定义颜色数组
			var app = {};
			option = null;
			//数据加载
			var data = {type : "initializeData", starttime : starttime, endtime : endtime, sensorID:tempSensor};
			myChart.showLoading();
			
			$.post("./../TemperatureMeterServlet",data,function(data){
				var data = eval(data);
				var scale = "scale:true";
				if(data.length > 1){
			    $("#isY").attr("checked","checked");
				$(".chartY").removeAttr("disabled");
				var temp = 0;
					for (var i = data.length - 1; i >= 0; i--) {
						descDatalist.push(data[i]);
						temp += data[i].displacement;
					}
					if ($("#isY").is(":checked")) {
						if(numY == null){
							numY = parseInt(((temp/data.length)*2)/10)*10;//取平均数
							$(".chartY").val(numY);
						}
						scale = "";
			        }
					JSONData = descDatalist;//导出数据
					$("#content_right").show();
	                $("#content_right_no_data").hide();
					var dStrain = [];
					var dPressure = [];
					var displacement = [];
					var dTemp = [];
					var time = [];
					$.each(data,function(index,result){
						//dStrain.push(result.dStrain);
						//dPressure.push(result.dPressure);
						displacement.push(result.displacement);
						//dTemp.push(result.dTemp);
						time.push(result.dtCreateDT);
					});
					
					myChart.hideLoading();
					
					option = {
					    color: colors,
						title: {
							left: 'center',
							text: siteName+'温度-时间曲线图'
						},
					    grid: {
					        bottom: 80
					    },
						tooltip: {
							trigger: 'axis',
						},
						legend: {
							data:['温度'],
							orient:"verticality", 
							left: 'right',
							top: 49,
						    tooltip: {
						        show: true
						    },
						    
					    },
						xAxis: {
							name : '时间：s',
							type : 'category',
							data: time.map(function (str) {
				                return str.replace(' ', '\n')
				            }),
							axisTick: {
								alignWithLabel:true,
								},//刻度跟标签对齐
							boundaryGap : false,
				            axisLine: {onZero: false},
						},
						yAxis: {
							max: numY,
							name : '温度：（℃）',
							type : 'value',
							splitLine: {
				               show: true
				            },
				            scale,
				            axisLabel: {                   
                             formatter: function (value, index) {           
            	                  return value.toFixed(2);      
            	                  }                
            	             },
				            //scale:true,//自适应
							
						},
						toolbox: {
							show: true,
							left: 'right',
							feature: {
								dataZoom: {
									yAxisIndex: 'none'
								},
								dataView: {
									readOnly:true,
									show: true,
				                    title: siteName+'温度-时间数据列表',
				                    optionToContent: function(opt) {
				                        var colName = "序号";
				                        var timeName = "时间";
				                        var dataview = opt.toolbox[0].feature.dataView;  //获取dataview
				                        var table = '<div style="position:absolute;top: 5px;left: 0px;right: 0px;line-height: 1.4em;text-align:center;font-size:14px;">'+dataview.title+'</div>'
				                        table += getTable(opt,colName,timeName);
				                        return table;
				                    }
								},
								magicType: {type: ['line', 'bar']},
								restore: {},
								saveAsImage: {},
								myExport: {
					                show: true,
					                title: '导出Excel',
					                icon: 'path://M928 179.2H563.2V96c0-6.4 0-12.8-6.4-12.8s-6.4-6.4-12.8 0l-448 128c-6.4 0-12.8 6.4-12.8 12.8v576c0 6.4 6.4 12.8 12.8 12.8l448 128h6.4c6.4 0 6.4 0 12.8-6.4 6.4 0 6.4-6.4 6.4-12.8v-76.8h371.2c6.4 0 12.8-6.4 12.8-12.8V192c-6.4-6.4-19.2-12.8-25.6-12.8zM524.8 192v716.8l-416-121.6V236.8l416-121.6V192z m384 627.2H563.2v-76.8H640c6.4 0 12.8-6.4 12.8-12.8s-6.4-25.6-12.8-25.6H563.2V633.6H640c6.4 0 12.8-6.4 12.8-12.8s-6.4-19.2-12.8-19.2H563.2V524.8H640c6.4 0 12.8-6.4 12.8-12.8s-6.4-12.8-12.8-12.8H563.2V422.4H640c6.4 0 12.8-6.4 12.8-12.8s-6.4-19.2-12.8-19.2H563.2V320H640c6.4 0 12.8-6.4 12.8-12.8s-6.4-19.2-12.8-19.2H563.2V204.8h352v614.4zM704 320h96c6.4 0 12.8-6.4 12.8-12.8s-6.4-12.8-12.8-12.8H704c-6.4 0-12.8 6.4-12.8 12.8s6.4 12.8 12.8 12.8zM704 422.4h96c6.4 0 12.8-6.4 12.8-12.8s-6.4-12.8-12.8-12.8H704c-6.4 0-12.8 6.4-12.8 12.8s6.4 12.8 12.8 12.8zM704 524.8h96c6.4 0 12.8-6.4 12.8-12.8s-6.4-12.8-12.8-12.8H704c-6.4 0-12.8 6.4-12.8 12.8s6.4 12.8 12.8 12.8zM704 633.6h96c6.4 0 12.8-6.4 12.8-12.8s-6.4-12.8-12.8-12.8H704c-6.4 0-12.8 6.4-12.8 12.8s6.4 12.8 12.8 12.8zM704 736h96c6.4 0 12.8-6.4 12.8-12.8s-6.4-19.2-12.8-19.2H704c-6.4 0-12.8 6.4-12.8 12.8s6.4 19.2 12.8 19.2zM249.6 640l44.8-83.2 51.2 96h51.2L320 505.6l76.8-134.4h-57.6l-44.8 89.6L256 384h-44.8l64 121.6-64 128z',
					                /* icon: 'image://./../images/excel.png', */
					                onclick: function (){
				                	      var namestr=siteName+"_"+$("#starttime").val()+"_"+$("#endtime").val();
				                	      if(JSONData.length<=0){
					                		  alert("当前数据为空不能导出数据");
					                	  }else{
					                		  console.log(JSONData.toString);
					                		  JSONToCSVConvertor(JSONData, namestr, true);
					                	  }
					                }
					            }
							},
						},
						dataZoom: [{
							startValue: '1970-01-01',
						}, {
							type: 'inside'
						}],
						series: [
							/* {
								name: '张力',
								type: 'line',
								data: dStrain,
								smooth:true,  //这句就是让曲线变平滑的 
							},
							{
								name: "压力",
								type: 'line',
								data: dPressure,
								smooth:true,  //这句就是让曲线变平滑的 
							}, */
							{
								name: "温度",
								type: 'line',
								data: displacement,
								smooth:true,  //这句就是让曲线变平滑的 
							}/* ,
							{
								name: "临时",
								type: 'line',
								data: dTemp,
								smooth:true,  //这句就是让曲线变平滑的 
							}, */
						]
					
					}

					if (option && typeof option === "object") {
						myChart.resize();
						myChart.setOption(option, true);
					}
				}else{
					$(".chartY").val("自适应");
					$(".chartY").attr("disabled","disabled");
					$("#isY").removeAttr("checked");
	                $("#content_right").hide();
	                $("#content_right_no_data").show();
				}
				$("#station").find("option[value='"+tempSensor+"']").attr("selected",true);
				
				
				
				function getTable(opt,colName,typeName){
			        var axisData = opt.xAxis[0].data;//获取图形的data数组
			        var series = opt.series;//获取series
			        var num = 0;//记录序号
			        var sum = new Array();//获取合计数组（根据对应的系数生成相应数量的sum）
			        for(var i=0; i<series.length; i++){
			            sum[i] = 0;
			        }
			        var table = '<table class="bordered"><thead><tr>'
			            + '<th>'+colName+'</th>'
			            + '<th>'+typeName+'</th>';
			        for(var i=0; i<series.length;i++){
			            table += '<th>'+series[i].name+'</th>'
			        }
			        table += '</tr></thead><tbody>';
			        for (var i = 0, l = axisData.length; i < l; i++) {
			            num += 1;
			            for(var n=0;n<series.length;n++){
			                if(series[n].data[i]){
			                    sum[n] += Number(series[n].data[i]);
			                }else{
			                    sum[n] += Number(0);
			                }
			 
			            }
			            table += '<tr>'
			                + '<td>' + num + '</td>'
			                + '<td>' + axisData[i] + '</td>';
			            for(var j=0; j<series.length;j++){
			                if(series[j].data[i]){
			                    table += '<td>' + series[j].data[i] + '</td>';
			                }else{
			                    table += '<td>' + 0 + '</td>';
			                }
			 
			            }
			            table += '</tr>';
			        }
			        
			        table += '</tr></tbody></table>';
			        return table;
			    }
			
				
			});
			
		}
			
			
		
		function getsensorimages(sensorName) {
	        $.post("./../PointAll", {"oper": "ImgUrlBySensorName", "SensorName": sensorName}, function (data) {
	            var data = eval("(" + data + ")");
	            var imageUrl = data[0].imgURL;
	            if (CheckValIsNull(imageUrl)) {
	                $("#SenosrImage").attr("src", imageUrl);
	            }
	        });
	    }
		
	});
	
</script>
</body>
</html>
