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
<script type="text/javascript" src="../js/highstock-v6.1.0.js"></script>
<script src="../js/highcharts-zh_CN.js"></script>
<script src="../js/exporting.js"></script>
<script src="../js/offline-exporting.js"></script>
<script src="../js/lhgcalendar.min.js">	</script>
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
<link href="../css/myAlert.css" rel="stylesheet" type="text/css" />
<script src="../js/myAlert.js"></script>
<link rel="stylesheet" href="../css/jquery-ui-v1.10.4.css">
<link rel="stylesheet" href="../css/jquery-ui-v1.10.4-style.css">
<script src="../js/jquery-ui.js"></script>
<style type="text/css">
.ui-table tbody tr td {
	text-align: center;
}
</style>

</head>
<body>

	<div class="wrapper" style="margin-top: -10px">
		<div class="datalist">

			

			<div class="param">
				<div class="btn-group" style="float: right;">
					<button type="button" class="btn btn-default btn-success graphicbtn">
						<i class="fa fa-bar-chart"></i> &nbsp;&nbsp;曲线显示&nbsp;
					</button>
					<button type="button" class="btn btn-default tabletbtn">
						<i class="fa fa-table"></i> &nbsp;&nbsp;表格显示&nbsp;
					</button>
					<button type="button" class="btn btn-default explodedata">
						<i class="fa fa-save"></i> &nbsp;&nbsp;导出数据&nbsp;
					</button>
				</div>

				<label> 测点编号:&nbsp;</label> <select class="sensornamelist"
					id="sensornamelist"></select>
				<!-- <label for="filter">&nbsp;滤波结果</label> -->
				<!-- <input type="checkbox" class="filter" name="filter" id="filter"  /> -->
				<label>&nbsp;&nbsp;&nbsp;时间区间:</label> <input type="text"
					value="2017-07-01 " class="starttime" name="starttime"> <label>
					至 </label> <input type="text" value="2017-07-05 " class="endtime"
					name="endtime"> &nbsp; &nbsp;
				

				<div class="btn-group" style="display: inline-flex;margin-top:10px;">
					<div class="creatchartY">
						<label for="isY" class="isYlabel">固定Y轴:&nbsp;&nbsp; </label> <input
							type="checkbox" class="isY" name="isY" id="isY" value="1"/> <input
							type="tel" class="chartY" onkeyup="checkdig()" disabled="disabled">&nbsp;&nbsp;
						<!-- <button class="btn btn-default savechartY" onclick="saveChartY()"><i class="fa fa-check-circle"></i> 确定 
	            </button>-->
					</div>

					<button type="button" class="btn btn-default weekdata"
						onclick="querygnssdata('day')">最近一天</button>
					<button type="button" class="btn btn-default weekdata"
						onclick="querygnssdata('week')">最近一周</button>
					<button type="button" class="btn btn-default monthdata"
						onclick="querygnssdata('month')">最近一月</button>
					<button type="button" class="btn btn-default yeardata"
						onclick="querygnssdata('year')">最近一年</button>
					<button type="button" class="btn btn-default yeardata"
						onclick="querygnssdata('all')">&nbsp;&nbsp;全部&nbsp;&nbsp;</button>
					<button type="button" class="btn btn-info" onclick="querydata()"
						style="margin-left: 20px;">查询</button>

				</div>
				<!--  
                 <div>
                 	<label>界面自动刷新频率：</label>
                 	<select id="auto_refresh" class="auto_refresh" name="auto_refresh" > 
                 		<option value="0">不自动刷新</option>
                 		<option value="30000">30s</option>
                 		<option value="60000">60s</option>
                 		<option value="120000">120s</option>
					</select>
                 </div>
                 -->
			</div>
			<div class="data_shw">
				<div class="sensorname_img">
					<div class="dataimg">
						<img src="" id="SenosrImage" />
					</div>

					<%
						if (StringUtil.isIn("edit", role)) {
							out.print("<div id='upload'></div>");
						}
					%>
					<!-- <div style="height:200px;width:165px;float:left;">
						<div id="yz_tabs" >
						<ul>
							<li><a href="#yz_tabs-0" style="padding:1px 8px;">一级阈值</a></li>
							<li><a href="#yz_tabs-1" style="padding:1px 8px;">二级阈值</a></li>
							<li><a href="#yz_tabs-2" style="padding:1px 8px;">三级阈值</a></li>
							<li><a href="#yz_tabs-3" style="padding:1px 8px;">四级阈值</a></li>
						</ul>
						<div id="yz_tabs-0" style="font-size:5px;padding:0px"></div>
						<div id="yz_tabs-1" style="font-size:5px;padding:0px"></div>
						<div id="yz_tabs-2" style="font-size:5px;padding:0px"></div>
						<div id="yz_tabs-3" style="font-size:5px;padding:0px"></div>
				</div>
					</div> -->
				</div>
				<div id="tabs" style="float:left;width:85%;border-width:0px;" >
					<ul style="background: none;border-width: 0px;border-bottom-width: 1px;">
						<li id="tabs-li-0"><a href="#tabs-0">监测点累计位移-时间曲线图</a></li>
						<li id="tabs-li-1"><a href="#tabs-1">位移速率曲线图</a></li>
					</ul>
					<div id="tabs-0" style="padding:0px;">
						<div id="container" class="graphic" style="width:100%;"></div>
						<div id="datatable" class="datatable" style="width:100%;"></div>
						<div id="descript" class="descript"></div>
					</div>
					<div id="tabs-1" style="padding:0px;">
						<!-- 该功能暂未实现，敬请期待。 -->
						<div id="rate_container" class="graphic" style="width:100%;"></div>
						<div id="rate_datatable" class="datatable" style="width:100%;"></div>
					</div>
				</div>
				

			</div>



		</div>
	</div>
	<section class="imgzoom_pack">
        <div class="imgzoom_x"><img src="../images/close.png"></div>
        <div class="imgzoom_img"><img src="" /></div>
      </section>
      <input type="hidden" class="dateType">
      <script type="text/javascript">
	var tab_flag=0;//选择项卡默认 0，代表第一个
	var descDatalist = [];//gnss位移表格倒序排列数据
	var descRatelist = [];//位移速率表格倒序排列数据
	var JSONData, tableData = [];
	var sType = "";
	var PrjName = getCookie("prjName");
	var statusDes = getCookie("statusDes");

	var prjAlais = getCookie("PrjAlais");
	var tbale;
	var sensorName = getUrlVars()["sensorname"];
	var staName = getUrlVars()["staName"];//设备名
	var name = getUrlVars()["name"];
	var sensorType = getUrlVars()["sensorType"];
	var kalman;
	var tabledata = null;
	var weekago = getRecentweekDays();
	var starttime = getUrlVars()["starttime"];
	var endtime = getUrlVars()["endtime"];
	getsensorNames(sensorType, PrjName, sensorName);
	getsensorimages(sensorName);
	$(".graphicbtn").addClass("btn-success");

	if (!CheckValIsNull(starttime)) {
		$(".starttime").val(weekago);
		starttime = weekago;
	} else {
		$(".starttime").val(starttime);
	}
	if (!CheckValIsNull(endtime)) {
		$(".endtime").val(curDateTime());
		endtime = curDateTime();
	} else {
		$(".endtime").val(endtime);
	}
	var time = 0;
	/* $(".auto_refresh").bind("change", function() {
		time = $(".auto_refresh").val();
		getData(starttime, endtime, sensorName, PrjName, "day", false);
	}); */

	getData(starttime, endtime, sensorName, PrjName, "default", null);

	function getData(starttime, endtime, sensorName, prjName, ymw, numY) {
		tableData = [];
		/* if (isShow) {
			tip("正在查询数据......");
		} else {
			tip("正在更新数据......");
		} */
		Highcharts.setOptions({
			global : {
				useUTC : false
			//关闭UTC
			}
		})
		var dnorth = [];
		var deast = [];
		var dup = [];
		var sigma = [];//平面位移
		var azimuth= {};//方位角
		
		var rate_dnorth = [];
		var rate_deast = [];
		var rate_dup = [];
		var rate_sigma = [];
		
		var filter = $("input[name='filter']:checked").val();
		if (filter == "on") {
			filter = 1;
		} else {
			filter = 0;
		}
		var data = {
			"starttime" : starttime,
			"endtime" : endtime,
			"sensorName" : sensorName,
			"ymw" : ymw,
			"prjName" : prjName,
			"filter" : filter
		};

		var url = "./../GNSSResultServlet";
		$.post(
						url,
						data,
						function(data) {
							tip_close();
							descDatalist=[];
							descRatelist=[];
							var warpperData= eval("(" + data + ")");
							var datalist = warpperData.gnss;//北斗位移数据
							var ratelist = warpperData.rate;//北斗位移速率数据
							if(datalist != undefined){
								
								if (datalist.length > 0) {
									
									for (var i = datalist.length - 1; i >= 0; i--) {
										descDatalist.push(datalist[i]);
									}
									
									for (var i = ratelist.length - 1; i >= 0; i--) {
										ratelist[i].sDatetime=ratelist[i].sDatetime.substr(0, 10);
										descRatelist.push(ratelist[i]);
									}
									switch (tab_flag) {//导出数据
									case 0://导出选项卡1表格数据
										JSONData = descDatalist;
										break;
									case 1://导出选项卡2表格数据
										JSONData = descRatelist;
										break;
									default:
										break;
									}
									
									
									

										$("#datatable").append(
												'<div id="example_table" ></div>');
										tabledata = $('#example_table').columns({
											data : descDatalist,
											schema : [ {
												"header" : "数据时间",
												"key" : "st"
											}, {
												"header" : "△x(mm)",
												"key" : "dn"
											}, {
												"header" : "△y(mm)",
												"key" : "de"
											}, {
												"header" : "△z(mm)",
												"key" : "du"
											},{
												"header" : "△xy(mm)",
												"key" : "Sigma"
											},{
												"header" : "∠(°)",
												"key" : "azimuth"
											}

											]
										});
										
										if(ratelist.length > 0){
											$("#rate_datatable").append(
													'<div id="example_table1" ></div>');
											tabledata = $('#example_table1').columns({
												data : descRatelist,
												cellType : "th",
												schema : [ {
													"header" : "数据时间",
													"key" : "sDatetime"
												}, {
													"header" : "△Vx(mm/d)",
													"key" : "dNorth"
												}, {
													"header" : "△Vy(mm/d)",
													"key" : "dEast"
												}, {
													"header" : "△Vz(mm/d)",
													"key" : "dUp"
												},{
													"header" : "△Vxy(mm/d)",
													"key" : "sigma"
												}
			
												]
											});
										}

										//此段为gnss位移数据的char
										var i = 0;
										var timeArr=[];
										$.each(datalist, function(index, dataArr) {
											var ad = dataArr.st;
											ad = ad.replace(/-/g, "/");
											var getTimedate = Date.parse(ad);
											//timeArr.push(ad.substr(5, 11));
											timeArr.push(getTimedate);
											dataArr.dn = dataArr.dn.toString().replace(/,/g, "");
											dataArr.de = dataArr.de.toString().replace(/,/g, "");
											dataArr.du = dataArr.du.toString().replace(/,/g, "");
											dataArr.Sigma = dataArr.Sigma.toString().replace(/,/g,"");
											/* dnorth[i] = [];
											dnorth[i][0] = getTimedate;

											dnorth[i][1] = Number(dataArr.dn);

											deast[i] = [];
											deast[i][0] = getTimedate;
											deast[i][1] = Number(dataArr.de);
											dup[i] = [];
											dup[i][0] = getTimedate;
											dup[i][1] = Number(dataArr.du);
											
											sigma[i] = [];
											sigma[i][0] = getTimedate;
											sigma[i][1] = Number(dataArr.Sigma); */
											
											dnorth[i]=Number(dataArr.dn);
											deast[i] = Number(dataArr.de);
											dup[i] = Number(dataArr.du);
											sigma[i] = Number(dataArr.Sigma);									
											azimuth[getTimedate]=Number(dataArr.azimuth);
											i++;
										})
										charts(dnorth, deast, dup, sigma,azimuth,numY,timeArr);
										
										
										
										
										
										
										
										
										
										
										
										
										 //此段为位移速率的char
										var j= 0;
										$.each(ratelist, function(index, dataArr) {
											var ad = dataArr.sDatetime;
											ad = ad.replace(/-/g, "/");
											var getTimedate = Date.parse(ad);
											
											dataArr.dNorth = dataArr.dNorth.toString().replace(/,/g, "");
											dataArr.dEast = dataArr.dEast.toString().replace(/,/g, "");
											dataArr.dUp = dataArr.dUp.toString().replace(/,/g, "");
											dataArr.sigma = dataArr.sigma.toString().replace(/,/g,"");
											
											rate_dnorth[j] = [];
											rate_dnorth[j][0] = getTimedate;
											rate_dnorth[j][1] = Number(dataArr.dNorth);

											rate_deast[j] = [];
											rate_deast[j][0] = getTimedate;
											rate_deast[j][1] = Number(dataArr.dEast);
											
											rate_dup[j] = [];
											rate_dup[j][0] = getTimedate;
											rate_dup[j][1] = Number(dataArr.dUp);

											rate_sigma[j] = [];
											rate_sigma[j][0] = getTimedate;
											rate_sigma[j][1] = Number(dataArr.sigma); 

											j++;
										})
										rate_charts(rate_dnorth, rate_deast, rate_dup, rate_sigma,numY,timeArr);
										
										/* if (time != 0) {
											setTimeout(
													'getData(starttime,endtime,sensorName,PrjName,"day",false)',
													time);
										} */
									} else {
										$("#container").html(
												"<font size=3 class='nodata'>"
														+ staName
														+ "	无该时段查询数据。</font>");
										$('#datatable').html("<font size=3 class='nodata'>"+staName+"无该时段查询数据。</font>");
										
										$('#rate_datatable').html("<font size=3 class='nodata'>"+staName+"无该时段查询数据。</font>");
										$('#rate_container').html("<font size=3 class='nodata'>"+staName+"无该时段查询数据。</font>");
									}
								
								
								
								
							}else{
								$("#container").html(
										"<font size=3 class='nodata'>"
												+ staName
												+ "  无该时段查询数据。</font>");
								$('#datatable').html("<font size=3 class='nodata'>"+staName+"	无该时段查询数据。</font>");
								$('#rate_datatable').html("<font size=3 class='nodata'>"+staName+"	无该时段查询数据。</font>");
								$('#rate_container').html("<font size=3 class='nodata'>"+staName+"	无该时段查询数据。</font>");
							}
							
							

						})

	}
	//gnss
	function charts(dnorth, deast, dup, sigma, azimuth,numY,timeArr) {
		deNumY = null;
		if (null != numY) {
			deNumY = "-" + numY;
		}
		var options={
				lang:{
				       contextButtonTitle:"图表导出菜单",
				     },
				chart : {
					zoomType : 'xy',
					ignoreHiddenSeries : true,
					height : 430,
					width:980
				},
				title : {
					text : staName+" 监测点累计位移-时间曲线",
					style: { "color": "#333333", "fontSize": "16px" }
				},
				/* subtitle: {
				    text: statusDes
				}, */
				xAxis : [
						{
							crosshair: {
								color:'#cccccc',
								width:1
							},
							minTickInterval:dnorth.length/5,
							//tickPixelInterval : 110,
							categories : timeArr,
							//type : 'datetime',
							labels : {
								rotation : -45,
								align : 'right',
								style : {
									fontSize : '9px',
									fontFamily : 'Verdana, sans-serif'
								},

								formatter : function() {
									return Highcharts.dateFormat(
											'%m-%d %H:%M', this.value);
								},
							}
						}, ],

				yAxis : [{
					max : numY,//纵轴的最大值 
					min : deNumY,
					minRange:10,
					minTickInterval:5,
					title : {
						text : "单位: mm",

						style : {
							color : '#4572A7'
						}
					},
					labels : {
						formatter : function() {
							return this.value
						},
						style : {
							color : '#4572A7'
						},
					},
					opposite : false,

				} ],
				exporting:{
					filename: staName+" 监测点累计位移-时间曲线",
					chartOptions: {
						navigator : {
							enabled : false,
							},
						scrollbar : {
							enabled : false,
						},
			            chart: {
			                width: 980,
			                height: 400
			            }
			        }, 
					enabled : true,
					 buttons: {
			            contextButton: {
			            	title:"图表导出菜单",
			                menuItems: [{
			                    text: '下载 PNG 图片',
			                    onclick: function () {
			                        this.exportChartLocal(null,{chart: {
						                width: 980,
						                height: 400
						            }});
			                    }
			                },
			                /* {
			                    text: '下载 PDF 文件',
			                    onclick: function () {
			                        this.exportChartLocal({type:"application/pdf"},{chart: {
						                width: 980,
						                height: 400
						            }});
			                    }
			                } */]
			            }
					}
				},
				tooltip : {
					shared: true,
					//xDateFormat : '%Y-%m-%d %H:%M',
					valueDecimals : 2,
					useHTML:false,
					formatter: function() {
						/* console.log(azimuth);
						console.log(azimuth[this.x]); */
						//console.log(this);
						var strDate=new Date(this.x).Format("yyyy-MM-dd HH:mm");
						var formatStr='<span style="font-size:9px">'+strDate+'</span><br/>';
						for(var i=0;i<this.points.length;i++){
							if(this.points[i].series.name.indexOf('xy')>0){
								formatStr += '<span style="color: '+ this.points[i].series.color + '">\u25CF</span> '+this.points[i].series.name+': <b>'+ this.points[i].y +'mm</b>'
								+'(∠: <b>'+ azimuth[this.x] +'°</b>)<br/>';
							}else{
								formatStr+= '<span style="color: '+ this.points[i].series.color + '">\u25CF</span> '+this.points[i].series.name+': <b>'+ this.points[i].y +'mm</b><br/>';
							}
						}
						
						return formatStr;
					} 
				},
				legend : {
					enabled : true,
					align:"left",
					itemStyle : {
						"fontSize" : "16px"
					},
					//layout:"vertical",
					verticalAlign: 'top',
					floating: true,
					x: 60,
					y: 0,
				},
				scrollbar : {
					enabled : true,
				},
				rangeSelector : {
					enabled : false,
					selected : 0,
					buttons : [ {
						type : 'hour',
						count : 24,
						text : 'hour'
					},

					{
						type : 'all',
						text : 'All'
					} ],

				},

				navigator : {
					enabled : true,
					height : 20,
					series : {
						color : 'white',
						fillOpacity : 0,
						lineWidth : 0
					},
					xAxis : {
						type : 'datetime',
						labels : {
							formatter : function() {
								//return Highcharts.dateFormat('%m-%d', this.value); ;
							},
							align : 'center',
						}
					}
				},

				global : {
					useUTC : false
				},
				credits : {
					enabled : false
				},
				series : [ {
					name : "△x",
					color : '#FF0000',
					type : 'spline',
					lineWidth : 1,
					data : dnorth,
					marker : {
						enabled : true,
						radius : 1
					},
					shadow : false,
					tooltip : {
						valueSuffix : ' mm'
					},

				}, {
					name : "△y",
					color : '#28ca55',
					lineWidth : 1,
					type : 'spline',
					data : deast,
					marker : {
						enabled : true,
						radius : 1
					},
					shadow : false,
					tooltip : {
						valueSuffix : ' mm'
					},

				}, {
					name : "△z",
					color : '#4F94CD',
					type : 'spline',
					lineWidth : 1,
					data : dup,
					marker : {
						enabled : true,
						radius : 1
					},
					shadow : false,
					tooltip : {
						valueSuffix : ' mm'
					},

				} 
				,{
					name : "△xy",
					color : '#FFCC00',
					type : 'spline',
					lineWidth : 1,
					data : sigma,
					marker : {
						enabled : true,
						radius : 1
					},
					shadow : false,
					tooltip : {
						valueSuffix : 'mm'
					},
					visible: false,
					//showInLegend:false,
				}]
			}

		Highcharts.chart("container",options);
	}

	//位移速率
	function rate_charts(dnorth, deast, dup, sigma, numY,timeArr) {
		deNumY = null;
		if (null != numY) {
			deNumY = "-" + numY;
		}
		var options={
			lang:{
			       contextButtonTitle:"图表导出菜单",
			     },
			chart : {
				zoomType : 'xy',
				ignoreHiddenSeries : true,
				height : 430,
				width:980
			},
			title : {
				text : staName+" 位移速率曲线",
				style: { "color": "#333333", "fontSize": "16px" }
			},
			/* subtitle: {
			    text: statusDes
			}, */
			xAxis : [
					{
						crosshair: true,
						tickPixelInterval : 110,
						//categories : timeArr,
						type : 'datetime',
						labels : {
							rotation : -45,
							align : 'right',
							style : {
								fontSize : '9px',
								fontFamily : 'Verdana, sans-serif'
							},

							formatter : function() {

								return Highcharts.dateFormat(
										'%m-%d %H:%S', this.value);
								//return Highcharts.dateFormat('%S:%H %d-%m', this.value); 

							},
						}
					}, ],

			yAxis : [{
				max : numY,//纵轴的最大值 
				min : deNumY,
				minRange:5,
				minTickInterval:1,
				title : {
					text : "单位: mm/d",
					style : {
						color : '#4572A7'
					}
				},
				labels : {
					formatter : function() {
						return this.value
					},
					style : {
						color : '#4572A7'
					},
				},
				opposite : false,

			} ],
			exporting:{
				filename: staName+" 位移速率曲线",
				chartOptions: {
					navigator : {
						enabled : false,
						},
					scrollbar : {
						enabled : false,
					},
		            chart: {
		                width: 980,
		                height: 400
		            }
		        }, 
				enabled : true,
				buttons: {
		            contextButton: {
		            	title:"图表导出菜单",
		                menuItems: [{
		                    text: '下载 PNG 图片',
		                    onclick: function () {
		                        this.exportChartLocal(null,{chart: {
					                width: 980,
					                height: 400
					            }});
		                    }
		                },
		                /* {
		                    text: '下载 PDF 文件',
		                    onclick: function () {
		                        this.exportChartLocal({type:"application/pdf"},{chart: {
					                width: 980,
					                height: 400
					            }});
		                    }
		                } */]
		            }
				}
			},
			tooltip : {
				shared: true,
				xDateFormat : '%Y-%m-%d %H:%M',
				valueDecimals : 2
			},
			legend : {
				enabled : true,
				align:"left",
				itemStyle : {
					"fontSize" : "16px"
				},
				//layout:"vertical",
				verticalAlign: 'top',
				floating: true,
				x: 60,
				y: 0,
			},
			scrollbar : {
				enabled : true,
			},
			rangeSelector : {
				enabled : false,
				selected : 0,
				buttons : [ {
					type : 'hour',
					count : 24,
					text : 'hour'
				},

				{
					type : 'all',
					text : 'All'
				} ],

			},

			navigator : {
				enabled : true,
				height : 20,
				series : {
					color : 'white',
					fillOpacity : 0,
					lineWidth : 0
				},
				xAxis : {
					type : 'datetime',
					labels : {
						formatter : function() {
							//return Highcharts.dateFormat('%m-%d', this.value); ;
						},
						align : 'center',
					}
				}
			},

			global : {
				useUTC : false
			},
			credits : {
				enabled : false
			},
			series : [ {
				name : "△Vx",
				color : '#FF0000',
				type : 'spline',
				lineWidth : 1,
				data : dnorth,
				marker : {
					enabled : true,
					radius : 1
				},
				shadow : false,
				tooltip : {
					valueSuffix : ' mm/d'
				},

			}, {
				name : "△Vy",
				color : '#28ca55',
				lineWidth : 1,
				type : 'spline',
				data : deast,
				marker : {
					enabled : true,
					radius : 1
				},
				shadow : false,
				tooltip : {
					valueSuffix : ' mm/d'
				},

			}, {
				name : "△Vz",
				color : '#4F94CD',
				type : 'spline',
				data : dup,
				marker : {
					enabled : true,
					radius : 1
				},
				shadow : false,
				tooltip : {
					valueSuffix : ' mm/d'
				},

			}, {
				name : "△Vxy",
				color : '#FFCC00',
				type : 'spline',
				lineWidth : 1,
				data : sigma,
				marker : {
					enabled : true,
					radius : 1
				},
				shadow : false,
				visible: false,
				tooltip : {
					valueSuffix : ' mm/d'
				},

			}]
		}
		Highcharts.chart('rate_container',options);
	}
	function querydata() {
		var numY = null;
		if ($(".isY").is(":checked")) {
			var value = $(".chartY").val();
			if (isNaN(value)) {
				$.myAlert("y轴限定值必须为数字！");
				return;
			}
			numY = value;
		}

		tabledata = null;
		$("#datatable").html("");
		$("#rate_datatable").html("");
		var starttime = $(".starttime").val();
		var endtime = $(".endtime").val();
		var datadiffuse = GetDateDiff(starttime, endtime, "day");
		if (datadiffuse < 1) {
			art.dialog.alert('Time error, please re-input...');
			return false;
		}
		var sensorName = $(".sensornamelist").val();
		var prjName = getCookie("prjName");
		sensorName = sensorName.toLowerCase();
		getData(starttime, endtime, sensorName, prjName, "default",numY);
	}
	
	$( "#tabs" ).tabs();
	$( "#yz_tabs" ).tabs();
	queryThreshold();
    //获取阈值信息
	function queryThreshold(){
		/* $.post("./../auxiliaryServlet",{"method":"queryThresholdForBD","prjId":PrjName,"sensorId":sensorName,"sensorType":"gnss"},function(data){
			var datalist = eval("(" + data + ")"); 
			if(datalist.length>0){
				$("#yz_tabs").show();
				for (var i = 0; i < datalist.length; i++) {
					var appendStr;
					if(tab_flag==0){
						appendStr="累计变化量:<br/>△xy（水平）: "+datalist[i].value1+"mm<br/>△z （垂直）: "+datalist[i].value2+"mm<br/> ";
					}else if(tab_flag==1){
						appendStr="当日变化量:<br/>△Vxy（水平）: "+datalist[i].value3+"mm/d<br/>△Vz （垂直）: "+datalist[i].value4+"mm/d";
					}
					$("#yz_tabs-"+i).html(appendStr);
				}
			}else{
				$("#yz_tabs").hide();
				for(var i = 0;i< 4;i++){
					$("#yz_tabs-"+i).html("未设置阈值");
				}
			}
		}); */
	}
	
	$(".starttime").on("change",querydata);
	$(".endtime").on("change",querydata);
	
	$("#tabs-li-0").on("click",function(){
		tab_flag=0;
		JSONData=descDatalist;
		//queryThreshold();
	});
	$("#tabs-li-1").on("click",function(){
		tab_flag=1;
		JSONData=descRatelist;
		//queryThreshold();
	});
</script>
<script src="../js/pointgraphdata.js"></script>
 <script src="../js/footer.js"></script>
</body>
</html>


