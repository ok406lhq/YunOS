<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
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
        var layer = layui.layer,
            element = layui.element,
            laydate = layui.laydate,
            form = layui.form;

        $(document).ready(function () {
            element.render();
            form.render();

            lay('.J-lay-date-range').each(function(){
                laydate.render({
                    elem: this,
                    range: true,
                    max: new Date().getTime(),
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
        });

	</script>
</head>
<body style="height: 1080px;">
<ol class="breadcrumb">
	<li><a href="slope.html"><i class="fa fa-home"></i> &nbsp;首页</a></li>
	<li><a href="#">数据分析</a></li>
	<li class="active">数据关联</li>
</ol>
<div class="compare-container">
	<blockquote class="layui-elem-quote  mlr-20 mb-10">
		<div class="layui-form">
			<div class="layui-inline">
				<input type="text" id="compareTime" style="width: 200px;"  placeholder="对比时间段" autocomplete="off" class="layui-input J-lay-date-range">
			</div>
			<div class="layui-inline">
				<select id="selectProject" lay-filter="selectProject">
					<option value="">请选择结构物</option>
				</select>
			</div>
			<div class="layui-inline">
				<select id="selectPointType" lay-filter="selectPointType">
					<option value="">请选择测点类型</option>
					<option value="GNSS" name="type">GPS/北斗表面位移</option>
					<option value="Tiltmeter" name="type">倾度盘</option>
					<option value="Crackmeter" name="type">拉线位移计</option>
					<option value="Inclinometer" name="type">深部位移</option>
					<option value="Piezometer" name="type">地下水位</option>
					<option value="RainGauge" name="type">雨量计</option>
					<option value="Prism" name="type">测量机器人棱镜</option>
				</select>
			</div>
			<div class="layui-inline"  style="width: 140px;">
				<select id="selectDeviceOne">
					<option value="">请选择检测点一</option>
				</select>
			</div>
			<div class="layui-inline"  style="width: 140px;">
				<select id="selectDeviceTwo">
					<option value="">请选择检测点二</option>
				</select>
			</div>
			<div class="layui-inline">
				<button class="layui-btn" id="gotoCompare">立即对比</button>
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
        var pointType = $("#selectPointType").val();
        if(projectId != '' && pointType != ''){
            postSensorData(projectId, pointType);
        }
    });
    form.on('select(selectPointType)', function(data){
        var projectId = $("#selectProject").val();
        var pointType = data.value;
        if(projectId != '' && pointType != ''){
            postSensorData(projectId, pointType);
        }
    });

    function postSensorData(projectId, pointType){
        Loading("加载中....");
        $("#selectDeviceOne").html("<option value=\"\">请选择检测点</option>");
        $("#selectDeviceTwo").html("<option value=\"\">请选择检测点</option>");
        $.post("./../CompareDataServlet?requestType=getSensor",{projectId:projectId, sensorType: pointType},function(data){
            var data=eval(data);
            for(var i=0; i< data.length; i++){
                $("#selectDeviceOne").append("<option value=\""+data[i].nSensorID+"\">"+data[i].sSensorName.trim()+"</option>");
                $("#selectDeviceTwo").append("<option value=\""+data[i].nSensorID+"\">"+data[i].sSensorName.trim()+"</option>");
            }
            form.render();
            LoadingHide();
        })
    }


    $("#gotoCompare").click(function(){
        var sensorIdOne = $("#selectDeviceOne").val();
        var sensorIdTwo = $("#selectDeviceTwo").val();
        var compareTime = $("#compareTime").val();
        var startTime="", endTime="";

        if(compareTime != ""){
            var compareTimeArr = compareTime.split(" - ");
            startTime = compareTimeArr[0];
            endTime = compareTimeArr[1];
        }

        if(startTime == '' || endTime == ''){
            $.myAlert("请选择对比时间段！");
            return false;
        }
        else if(sensorIdOne == '' || sensorIdTwo == ''){
            $.myAlert("请选择两个对比监测点！");
            return false;
        }

        var selectPointType = $("#selectPointType").val();
        if(selectPointType == 'GNSS')
            requestGnssData(sensorIdOne, sensorIdTwo, startTime, endTime);
        else if(selectPointType == 'RainGauge')
            requestRainData(sensorIdOne, sensorIdTwo, startTime, endTime);
        else{
            $("#compare-line").html('<p style="text-align: center; margin: 100px; color: #f6d1d1; font-size: 20px;">该类型传感器数据未接入</p>');
        }
    });
</script>

<script type="text/javascript">
    function requestRainData(sensorIdOne, sensorIdTwo, startTime, endTime){
        var xAxis1=[], xAxis2=[];
        var rainData1=[], rainData2=[];
        $.post("./../CompareDataServlet?requestType=getRainCompareData",{"sensorId":sensorIdOne, "startTime":startTime, "endTime": endTime},function(data){
            for(var i=0; i < data.length; i++){
                xAxis1[i] = data[i].dtCreateDT;
                rainData1[i] = data[i].dRealRain;
            }

            $.post("./../CompareDataServlet?requestType=getRainCompareData",{"sensorId":sensorIdTwo, "startTime":startTime, "endTime": endTime},function(data2){
                for(var i=0; i < data2.length; i++){
                    xAxis2[i] = data2[i].dtCreateDT;
                    rainData2[i] = data2[i].dRealRain;
                }
                showRainLine(xAxis1, xAxis2, rainData1, rainData2);
            });
        });
    }
    function showRainLine(xAxis1, xAxis2, rainData1, rainData2){
        LoadingHide();
        if (xAxis1.length < 1 && xAxis2.length < 1){
            $("#compare-line").html('<p style="text-align: center; margin: 100px; color: #f6d1d1; font-size: 20px;">暂无数据进行对比，请重新选择监测点和时间段</p>');
            return false;
        }

        var deviceOneName = $("#selectDeviceOne").find("[value='"+$("#selectDeviceOne").val()+"']").text();
        var deviceTwoName = $("#selectDeviceTwo").find("[value='"+$("#selectDeviceTwo").val()+"']").text();

        var dom = document.getElementById("compare-line");
        var myChart = echarts.init(dom);
        var lineOption = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:[deviceOneName+' 雨量(mm)',deviceOneName+' 雨量(mm)']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {},
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
                    name:deviceOneName+' 雨量(mm)',
                    type:'line',
                    xAxisIndex: 0,
                    data:rainData1
                },
                {
                    name:deviceTwoName+' 雨量(mm)',
                    type:'line',
                    xAxisIndex: 1,
                    data:rainData2
                }
            ]
        };
        myChart.setOption(lineOption, true);
    }

	function requestGnssData(sensorIdOne, sensorIdTwo, startTime, endTime) {
        Loading("加载中....");
        var xAxis1=[], xAxis2=[];
        var xData1=[], yData1=[], zData1=[], xData2=[], yData2=[], zData2=[];
        $.post("./../CompareDataServlet?requestType=getGnssCompareData",{"sensorId":sensorIdOne, "startTime":startTime, "endTime": endTime},function(data){
            for(var i=0; i < data.length; i++){
                xAxis1[i] = data[i].sDatetime;
                xData1[i] = data[i].dNorth;
                yData1[i] = data[i].dEast;
                zData1[i] = data[i].dUp;
            }

            $.post("./../CompareDataServlet?requestType=getGnssCompareData",{"sensorId":sensorIdTwo, "startTime":startTime, "endTime": endTime},function(data2){
                for(var i=0; i < data2.length; i++){
                    xAxis2[i] = data2[i].sDatetime;
                    xData2[i] = data2[i].dNorth;
                    yData2[i] = data2[i].dEast;
                    zData2[i] = data2[i].dUp;
                }
                showGnssLine(xAxis1, xAxis2, xData1, yData1, zData1, xData2, yData2, zData2);
            });
        });
    }
    function showGnssLine(xAxis1, xAxis2, xData1, yData1, zData1, xData2, yData2, zData2){
        LoadingHide();
        if (xAxis1.length < 1 && xAxis2.length < 1){
            $("#compare-line").html('<p style="text-align: center; margin: 100px; color: #f6d1d1; font-size: 20px;">暂无数据进行对比，请重新选择监测点和时间段</p>');
            return false;
        }

        var deviceOneName = $("#selectDeviceOne").find("[value='"+$("#selectDeviceOne").val()+"']").text();
        var deviceTwoName = $("#selectDeviceTwo").find("[value='"+$("#selectDeviceTwo").val()+"']").text();

        var dom = document.getElementById("compare-line");
        var myChart = echarts.init(dom);
        var lineOption = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:[deviceOneName+' △X(mm)',deviceOneName+' △Y(mm)',deviceOneName+' △Z(mm)', deviceTwoName+' △X(mm)',deviceTwoName+' △Y(mm)',deviceTwoName+' △Z(mm)']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {},
                    dataView:{},
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
                    name:deviceOneName+' △X(mm)',
                    type:'line',
                    xAxisIndex: 0,
                    data:xData1
                },
                {
                    name:deviceOneName+' △Y(mm)',
                    type:'line',
                    xAxisIndex: 0,
                    data:yData1
                },
                {
                    name:deviceOneName+' △Z(mm)',
                    type:'line',
                    xAxisIndex: 0,
                    data:zData1
                },
                {
                    name:deviceTwoName+' △X(mm)',
                    type:'line',
                    xAxisIndex: 1,
                    data:xData2
                },
                {
                    name:deviceTwoName+' △Y(mm)',
                    type:'line',
                    xAxisIndex: 1,
                    data:yData2
                },
                {
                    name:deviceTwoName+' △Z(mm)',
                    type:'line',
                    xAxisIndex: 1,
                    data:zData2
                }
            ]
        };
        myChart.setOption(lineOption, true);
    }
</script>
</body>
</html>
<script src="../js/footer.js"></script>