<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<style type="text/css">
		.layui-form-item .layui-form-checkbox[lay-skin=primary]{margin-right: 20px;}
		 table {
            cellspacing:0 ;
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
            cellspacing:0;
            width: 100px;
        }
 
        .bordered td{
            padding: 7px;
            text-align: center;
            cellspacing:0;
        }
 
 
        .bordered th {
 
             background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
             background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
             background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);
             background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);
             background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);
             background-image:         linear-gradient(top, #ebf3fc, #dce9f9);
        }
        
        .bordered td:first-child, .bordered th:first-child {
            border-left: none;
        }
 
 
        .bordered  tr:nth-of-type(2n){background:#FFFFFF;cursor: pointer;}
        .bordered  tr:nth-of-type(2n+1){background:#F7FAFC;cursor: pointer;}
 
        .bordered  tbody tr:hover{  background: #fbf8e9;
            -o-transition: all 0.1s ease-in-out;
            -webkit-transition: all 0.1s ease-in-out;
            -moz-transition: all 0.1s ease-in-out;
            -ms-transition: all 0.1s ease-in-out;
            transition: all 0.1s ease-in-out;
        }
	</style>
	<link rel="stylesheet" href="../css/bootstrap.min.css" />
	<link rel="stylesheet" href="../layui/css/layui.css" />
	<link rel="stylesheet" href="../css/compare.css?1" />
	<link rel="stylesheet" href="../css/font-awesome.min.css" />
	

	<script src="../js/jquery-1.11.0.min.js"></script>
	<script src="../layui/layui.all.js"></script>
	<link href="../css/myAlert.css" rel="stylesheet" type="text/css" />
	<script src="../js/myAlert.js"></script>
	<script src="../js/config.js?1"></script>
	<script src="../js/echart/echarts.min.js"></script>
	<script type="text/javascript">
        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "h+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
        var layer = layui.layer,
            element = layui.element,
            laydate = layui.laydate,
            form = layui.form;
		var maxDate = new Date().setDate(new Date().getDate() - 1);
        $(document).ready(function () {
            element.render();
            form.render();

            lay('.J-lay-date-range').each(function(){
                laydate.render({
                    elem: this,
                    range: true,
                    max: new Date(maxDate).getTime(),
                    trigger: 'click',
                    done: function (value, date, endDate) {
                        var pElem = this.elem;
//                        var beginId = pElem.attr("beginId");
//                        var endId = pElem.attr("endId");
//                        if (beginId == null || beginId == 'undefined' || beginId == '') {
//                            beginId = 'beginDate';
//                        }
//                        if (endId == null || endId == 'undefined' || endId == '') {
//                            endId = "endDate";
//                        }
//                        if (value != '') {
//                            var dateArr = value.split(" - ");
//                            $("#"+beginId).val(dateArr[0]);
//                            $("#"+endId).val(dateArr[1]);
//                        }else{
//                            $("#"+beginId).val("");
//                            $("#"+endId).val("");
//                        }
                    }
                });
            });
            lay('.J-lay-date').each(function(){
                laydate.render({
                    elem: this,
                    max: new Date(maxDate).getTime(),
                    trigger: 'click',
                    done: function (value, date) {
                        var compareTimeOne = $("#compareTimeOne").val();
                        if(!compareTimeOne){
                            $.myAlert("请先选择对比时间段！");
                            $("#compareTimeTwo").val('')
                            return;
                        }
                        var timeOneArr = compareTimeOne.split(" - ");
                        var dayCount = parseInt((new Date(timeOneArr[1]) - new Date(timeOneArr[0]))/1000/3600/24);
                        var compareTimeTwoEnd = new Date(value);
                        compareTimeTwoEnd = compareTimeTwoEnd.setDate(compareTimeTwoEnd.getDate() + dayCount)
                        if (new Date(compareTimeTwoEnd).getTime() > new Date().getTime()){
                            compareTimeTwoEnd = new Date(compareTimeTwoEnd).setDate(new Date().getDate() - 1)
                            $.myAlert("两个时间段天数不一致！");
						}
                        $("#compareTimeTwo").val(value +' - '+new Date(compareTimeTwoEnd).Format("yyyy-MM-dd"))
                    }
                });
            });
            $("#compareTimeTwo").click(function () {
				$(".J-lay-date").click();
            })
        });
	</script>
</head>
<body style="height: 1080px;">
<ol class="breadcrumb">
	<li><a href="slope.html"><i class="fa fa-home"></i> &nbsp;首页</a></li>
	<li><a href="#">数据分析</a></li>
	<li class="active">数据对比</li>
</ol>
<div class="compare-container">
	<blockquote class="layui-elem-quote  mlr-20 mb-10" style="padding: 15px 15px 5px;">
		<div class="layui-form">
			<div class="layui-row">
				<div class="layui-inline">
					<select id="selectProject" lay-filter="selectProject">
						<option value="">请选择结构物</option>
					</select>
				</div>
				<div class="layui-inline">
					<select id="selectPointType" lay-filter="selectPointType">
						<option value="">请选择测点类型</option>
					</select>
				</div>
				<div class="layui-inline">
					<input type="text" id="compareTimeOne" style="width: 200px;"  placeholder="选择时间段" autocomplete="off" class="layui-input J-lay-date-range">
				</div>
				<div class="layui-inline">
					<input type="text"  id="compareTimeTwo" style="width: 200px;"  placeholder="选择对比开始时间" autocomplete="off" class="layui-input">
					<input type="text"  id="compareTimeTwos" style=" position: absolute; opacity: 0; filter:Alpha(opacity=0);margin-top: -30px;"  placeholder="选择对比开始时间" autocomplete="off" class="layui-input J-lay-date">
				</div>
				<div class="layui-inline" style="margin-left: 10px;">
					<button class="layui-btn" id="gotoCompare">立即对比</button>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-form-item" style="margin-bottom: 0px;">
					<label class="layui-form-label" style="width: 170px; margin-bottom: 0;">选择监测点：</label>
					<div class="layui-input-inline" style="width: auto;" id="sensorPanel"></div>
				</div>
			</div>
		</div>
	</blockquote>

	<div id="compare-line" style="min-width:400px; height: 600px; margin: 0 20px;">
		<p style="text-align: center; margin: 100px; color: #dcdcdc; font-size: 20px;">请选择监测点和时间段进行对比</p>
	</div>
</div>

<script type="text/javascript">
	Loading("加载中....");
    $.post("./../CompareDataServlet?requestType=getProject",{prjName:""},function(data){
        var data=eval(data);
		for(var i=0; i< data.length; i++){
		    $("#selectProject").append("<option value=\""+data[i].ID+"\">"+data[i].sProjectAbbr.trim()+"</option>");
		}
		form.render();
        LoadingHide();
    });

    form.on('select(selectProject)', function(data){
		var projectId = data.value;
        $("#selectPointType").val("");
//		var pointType = $("#selectPointType").val();
//        if(projectId != '' && pointType != ''){
//            postSensorData(projectId, pointType);
//        }else if (projectId != ''){
            postSensorType(projectId);
//        }
	});
    form.on('select(selectPointType)', function(data){
        var projectId = $("#selectProject").val();
        var pointType = data.value;
        if(projectId != '' && pointType != ''){
            postSensorData(projectId, pointType);
        }
	});
    form.on('checkbox(selectDevices)', function(data){

        var checkedCount = $("input:checkbox[name='selectDevices']:checked").length;
		if(checkedCount == 2){
            $("#compareTimeTwo").parent().addClass("hidden");
		}
        else if(checkedCount < 2){
            $("#compareTimeTwo").parent().removeClass("hidden");
        }
		else if (checkedCount > 2){
            $.myAlert("最多选择2个监测点！");
            $(data.elem).prop("checked", false);
            form.render("checkbox");
		}
	});

    var mapSensorType = {
        "GNSS": '<option value="GNSS">北斗</option>',
        "Tiltmeter": '<option value="Tiltmeter">倾度盘</option>',
        "Crackmeter": '<option value="Crackmeter">拉线位移计</option>',
        "Inclinometer": '<option value="Inclinometer">深部位移</option>',
        "Piezometer": '<option value="Piezometer">地下水位</option>',
        "RainGauge": '<option value="RainGauge">雨量计</option>',
        "Prism": '<option value="Prism">测量机器人棱镜</option>'
    }
    function postSensorType(projectId){
        Loading("加载中....");
        $("#selectPointType").html('<option value="">请选择测点类型</option>');
        $("#sensorPanel").html("");
        $.post("./../CompareDataServlet?requestType=getSensorType",{projectId:projectId},function(data){
            var data=eval(data);
            for(var i=0; i< data.length; i++){
                $("#selectPointType").append(mapSensorType[data[i]]);
            }
            form.render();
            LoadingHide();
        })
    }

    function postSensorData(projectId, pointType){
        Loading("加载中....");
        $("#sensorPanel").html("");
        $.post("./../CompareDataServlet?requestType=getSensor",{projectId:projectId, sensorType: pointType},function(data){
            var data=eval(data);
            for(var i=0; i< data.length; i++){
                if (data[i].sSiteName.indexOf("JZ") < 0){
                    $("#sensorPanel").append("<input type=\"checkbox\" lay-filter='selectDevices' class='selectDevices' name=\"selectDevices\" value=\""+data[i].nSensorID+"\" title=\""+data[i].sSiteName.trim()+"\" lay-skin=\"primary\"/>");
                }
            }
            form.render();
            LoadingHide();
        })
	}


    $("#gotoCompare").click(function(){
        var checkedCount = $("input:checkbox[name='selectDevices']:checked").length;
        var checkedSensor = $("input:checkbox[name='selectDevices']:checked");
        var sensorIdOne="", sensorIdTwo="", compareTimeOne="", compareTimeTwo="";
        if (checkedCount == 1){
            sensorIdOne = $(checkedSensor[0]).val();
            compareTimeOne = $("#compareTimeOne").val();
            compareTimeTwo = $("#compareTimeTwo").val();
            if(compareTimeOne == '' || compareTimeTwo == ''){
                $.myAlert("请选择两个对比时间！");
                return false;
            }
		}else if(checkedCount == 2){
            sensorIdOne = $(checkedSensor[0]).val();
            sensorIdTwo = $(checkedSensor[1]).val();
            compareTimeOne = $("#compareTimeOne").val();
          	if(compareTimeOne == ''){
                $.myAlert("请选择对比时间！");
                return false;
            }
		}else{
			$.myAlert("至少选择一个监测点！");
			return false;
		}

        var requestOneData = '', requestTwoData = '';

        if (sensorIdOne != '' && sensorIdTwo == '' && compareTimeOne != '' && compareTimeTwo != ''){
            var timeOneArr = compareTimeOne.split(" - ");
            var timeTwoArr = compareTimeTwo.split(" - ");
            // var dayCount = parseInt((new Date(timeOneArr[1]) - new Date(timeOneArr[0]))/1000/3600/24);
            //
            // var compareTimeTwoEnd = new Date(compareTimeTwo);
            // compareTimeTwoEnd.setDate(compareTimeTwoEnd.getDate() + dayCount)
            requestOneData = {"sensorId":sensorIdOne, "startTime":timeOneArr[0], "endTime": timeOneArr[1]};
            requestTwoData = {"sensorId":sensorIdOne, "startTime":timeTwoArr[0], "endTime": timeTwoArr[1]};
        }
        else if (sensorIdOne != '' && sensorIdTwo != '' && compareTimeOne != ''){
            var timeOneArr = compareTimeOne.split(" - ");
            requestOneData = {"sensorId":sensorIdOne, "startTime":timeOneArr[0], "endTime": timeOneArr[1]};
            requestTwoData = {"sensorId":sensorIdTwo, "startTime":timeOneArr[0], "endTime": timeOneArr[1]};
        }

		var selectPointType = $("#selectPointType").val();
		if(selectPointType == 'GNSS')
		{
            var legendData='';
            if (checkedCount == 1){
                legendData = ['时间段一 △X(mm)','时间段一 △Y(mm)','时间段一 △Z(mm)', '时间段二 △X(mm)','时间段二 △Y(mm)','时间段二 △Z(mm)'];
            }else if(checkedCount == 2){
                legendData = [$(checkedSensor[0]).attr("title")+' △X(mm)',$(checkedSensor[0]).attr("title")+' △Y(mm)',$(checkedSensor[0]).attr("title")+' △Z(mm)', $(checkedSensor[1]).attr("title")+' △X(mm)',$(checkedSensor[1]).attr("title")+' △Y(mm)',$(checkedSensor[1]).attr("title")+' △Z(mm)'];
            }
            requestGnssData(requestOneData, requestTwoData, legendData);
		}
        else if(selectPointType == 'RainGauge'){
            var legendData='';
            if (checkedCount == 1){
                legendData = ['时间段一 雨量(mm)','时间段二 雨量(mm)'];
            }else if(checkedCount == 2){
                legendData = [$(checkedSensor[0]).attr("title")+' 雨量(mm)',$(checkedSensor[1]).attr("title")+' 雨量(mm)'];
            }
            requestRainData(requestOneData, requestTwoData, legendData);
		}
        else{
            $("#compare-line").html('<p style="text-align: center; margin: 100px; color: #f6d1d1; font-size: 20px;">该类型传感器数据未接入</p>');
		}
    });
</script>

<script type="text/javascript">
	function requestRainData(requestOneData, requestTwoData, legendData){
        Loading("加载中....");

        var xAxis1=[], xAxis2=[];
        var rainData1=[], rainData2=[];
        $.post("./../CompareDataServlet?requestType=getRainCompareData", requestOneData,function(data){
            for(var i=0; i < data.length; i++){
                xAxis1[i] = data[i].dtCreateDT;
                rainData1[i] = data[i].dRealRain;
            }

            $.post("./../CompareDataServlet?requestType=getRainCompareData", requestTwoData,function(data2){
                for(var i=0; i < data2.length; i++){
                    xAxis2[i] = data2[i].dtCreateDT;
                    rainData2[i] = data2[i].dRealRain;
                }
                showRainLine(xAxis1, xAxis2, rainData1, rainData2, legendData);
            });
        });
	}
    function showRainLine(xAxis1, xAxis2, rainData1, rainData2, legendData){
        LoadingHide();
        if (xAxis1.length < 1 && xAxis2.length < 1){
            $("#compare-line").html('<p style="text-align: center; margin: 100px; color: #f6d1d1; font-size: 20px;">暂无数据进行对比，请重新选择监测点和时间段</p>');
            return false;
        }

        var dom = document.getElementById("compare-line");
        var myChart = echarts.init(dom);
        var lineOption = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: legendData
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {
                        type: "jpeg"
					},
                    dataView:{},
                    magicType:{type: ['line', 'bar']}
                }
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxis1
                }
                ,
                {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxis2
                }
            ],
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:legendData[0],
                    type:'line',
                    xAxisIndex: 0,
                    data:rainData1
                },
                {
                    name:legendData[1],
                    type:'line',
                    xAxisIndex: 1,
                    data:rainData2
                }
            ]
        };
        myChart.setOption(lineOption, true);
    }


	function requestGnssData(requestOneData, requestTwoData, legendData){
        Loading("加载中....");

        var xAxis1=[], xAxis2=[];
        var xData1=[], yData1=[], zData1=[], xData2=[], yData2=[], zData2=[];
        $.post("./../CompareDataServlet?requestType=getGnssCompareData",requestOneData,function(data){
            for(var i=0; i < data.length; i++){
                xAxis1[i] = data[i].sDatetime;
                xData1[i] = data[i].dNorth;
                yData1[i] = data[i].dEast;
                zData1[i] = data[i].dUp;
            }

            $.post("./../CompareDataServlet?requestType=getGnssCompareData",requestTwoData,function(data2){
                for(var i=0; i < data2.length; i++){
                    xAxis2[i] = data2[i].sDatetime;
                    xData2[i] = data2[i].dNorth;
                    yData2[i] = data2[i].dEast;
                    zData2[i] = data2[i].dUp;
                }
                showGnssLine(xAxis1, xAxis2, xData1, yData1, zData1, xData2, yData2, zData2, legendData);
            });
        });
	}
	function showGnssLine(xAxis1, xAxis2, xData1, yData1, zData1, xData2, yData2, zData2, legendData){
        LoadingHide();
	    if (xAxis1.length < 1 && xAxis2.length < 1){
			$("#compare-line").html('<p style="text-align: center; margin: 100px; color: #f6d1d1; font-size: 20px;">暂无数据进行对比，请重新选择监测点和时间段</p>');
	        return false;
		}

		var imageName = $("#selectProject").next().find("input").val()+"-北斗";
        var checkedCount = $("input:checkbox[name='selectDevices']:checked").length;
        var checkedSensor = $("input:checkbox[name='selectDevices']:checked");
        if (checkedCount == 1){
            imageName += $(checkedSensor[0]).attr("title");
            imageName += "-时间："+$("#compareTimeOne").val() + "、" + $("#compareTimeTwo").val();
		}
        else if (checkedCount == 2){
            imageName += $(checkedSensor[0]).attr("title")+"、"+$(checkedSensor[1]).attr("title");
            imageName += "-时间："+$("#compareTimeOne").val();
		}


        var dom = document.getElementById("compare-line");
        var myChart = echarts.init(dom);
        var lineOption = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: legendData
                ,formatter: function (name) {
                    if(name.indexOf("X") < 0){
                        name = name.replace("时间段一", "");
                        name = name.replace("时间段二", "");
					}
                    return name;
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {
                        type: "jpeg",
                        name: imageName
					},
                    dataView: {
						readOnly:true,
						show: true,
	                    title: '数据列表',
	                    optionToContent: function(opt) {
	                        var colName = "序号";
	                        var timeName = "时间";
	                        var dataview = opt.toolbox[0].feature.dataView;  //获取dataview
	                        var table = '<div style="position:absolute;top: 5px;left: 0px;right: 0px;line-height: 1.4em;text-align:center;font-size:14px;">'+dataview.title+'</div>'
	                        table += getTable(opt,colName,timeName);
	                        return table;
	                    }
					},
//                    dataZoom:{},
                    magicType:{type: ['line', 'bar']}
                }
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxis1
                }
                ,
				{
                    type: 'category',
                    boundaryGap: false,
                    data: xAxis2
                }
			],
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:legendData[0],
                    type:'line',
                    xAxisIndex: 0,
                    data:xData1
                },
                {
                    name:legendData[1],
                    type:'line',
                    xAxisIndex: 0,
                    data:yData1
                },
                {
                    name:legendData[2],
                    type:'line',
                    xAxisIndex: 0,
                    data:zData1
                },
                {
                    name:legendData[3],
                    type:'line',
                    xAxisIndex: 1,
                    data:xData2
                },
                {
                    name:legendData[4],
                    type:'line',
                    xAxisIndex: 1,
                    data:yData2
                },
                {
                    name:legendData[5],
                    type:'line',
                    xAxisIndex: 1,
                    data:zData2
                }
            ]
        };
        myChart.setOption(lineOption, true);
    }

	function getTable(opt,colName,typeName){
        var axisData = opt.xAxis[0].data;//获取图形的data数组
        var series = opt.series;//获取series
		console.log(axisData);
		console.log(series);
        var num = 0;//记录序号
        var sum = new Array();//获取合计数组（根据对应的系数生成相应数量的sum）
        for(var i=0; i<series.length; i++){
            sum[i] = 0;
        }
        var table = '<table class="bordered"><thead><tr>'
            + '<th>'+colName+'</th>'
            + '<th>'+typeName+'</th>';
        for(var i=0; i<series.length;i++){
			if (i > 0 && series[i-1].xAxisIndex == 0 && series[i].xAxisIndex == 1){
                table += '<th>'+typeName+'</th>';
            }
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
                if (j > 0 && series[j-1].xAxisIndex == 0 && series[j].xAxisIndex == 1){
                    if(opt.xAxis[1].data[i])
                    	table += '<td>'+opt.xAxis[1].data[i]+'</td>';
                    else
                        table += '<td>' + 0 + '</td>';
                }
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
	
</script>
</body>
</html>
<script src="../js/footer.js"></script>