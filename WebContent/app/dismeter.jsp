<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html style="height:100%;">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/font-awesome.min.css"/>
    <link rel="stylesheet" href="../layui/css/layui.css"/>
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/echart/echarts.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../layui/layui.all.js"></script>
    <script src="../js/config.js"></script>
    <style>
        body{
            height:100%; width:100%;
        }
        .clearfix_w{
            padding:0px 10px 0px 20px;
        }
        .clearfix_hr{
            padding:0px 0px 0px 5px;
        }
        .static_input{
            width:120px; height:38px;
        }
        .static_select{
            width:100px; height:38px;
        }
        .static_input_y{
            width:50px; height:38px;
        }
        .static_button{
            height:38px;
        }
        .static_checkbox{
            width:38px; height:38px; vertical-align: bottom;
        }
        .search_form{
            padding-top:20px;
        }
        .time_search{
            padding-left:102px;
        }
        .temp_to{
            padding-left:10px;
        }
        .no_data {
            font-size: 20px;
            margin-left: 40%;
        }
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
    <script type="text/javascript">
        $(document).ready(function () {
            lay('.J-lay-date-range').each(function () {
                layui.laydate.render({
                    elem: this,
                    max: '<%=new Date()%>',
                    done: function (value, date) {
                        var pElem = this.elem;
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="row clearfix clearfix_w">
    <div class="col-md-12 column" id="header">
        <form role="form" class="form-inline search_form">
            <div class="form-group">
                <label  class="temp_to">固定&nbsp;Y轴：</label>
                <input type="checkbox" class="static_checkbox isY" name="isY" id="isY"/>
                <input type="text"  class="form-control-static static_input_y chartY"  disabled/>
            </div>
            <div class="form-group">
                <label class="temp_to">测点编号：</label>
                <select class="form-control-static static_select sensornamelist" id="sensornamelist">
                </select>
            </div>
            <div class="form-group">
                <label  class="temp_to">时间区间：</label>
                <input type="text" class="starttime form-control-static J-lay-date-range static_input" name="starttime" id="starttime">
            </div>
            <div class="form-group">
                <label >至</label>
                <input type="text" class="endtime form-control-static J-lay-date-range static_input" name="endtime" id="endtime">
            </div>
            <button type="button" onclick="getAjaxData()" class="btn btn-info static_button" id="queryBtn" data-toggle="tooltip" data-placement="bottom"
                    title="查询"><span class="fa fa-search"></span></button>
            <button type="button" onclick="querygnssdata('day',this)" class="btn btn-default static_button timeBtn"  data-toggle="tooltip" data-placement="bottom"
                    title="最近一天">天</button>
            <button type="button" onclick="querygnssdata('week',this)" class="btn btn-default btn-primary static_button timeBtn"  data-toggle="tooltip" data-placement="bottom"
                    title="最近一周">周</button>
            <button type="button" onclick="querygnssdata('month',this)" class="btn btn-default static_button timeBtn"  data-toggle="tooltip" data-placement="bottom"
                    title="最近一月">月</button>
            <button type="button" onclick="querygnssdata('year',this)" class="btn btn-default static_button timeBtn"  data-toggle="tooltip" data-placement="bottom"
                    title="最近一年">年</button>
            <button type="button" onclick="querygnssdata('all',this)" class="btn btn-default static_button timeBtn"  data-toggle="tooltip" data-placement="bottom"
                    title="全部">全</button>
        </form>
        <br />
    </div>
</div>
<div class="row clearfix clearfix_hr">
    <div class="col-md-12 column">
        <hr />
    </div>
</div>
<div class="row clearfix clearfix_w">
    <div class="col-md-2 column" id="content_left" style="height: 420px">
        <img class="img-responsive" src="" id="SenosrImage" style="height: 80%;width: 100%"/>
    </div>
    <div class="col-md-10 column" id="content_right"  style="height: 420px; ">
    </div>
    <div class="col-md-10 column" id="content_right_no_data" style="display: none">
        <strong class="no_data">该时段暂无数据</strong>
    </div>
</div>
<script type="text/javascript">    //数据加载
var myChart = echarts.init(document.getElementById("content_right"));
$(document).ready(function () {
    $("#sensornamelist").bind("change", function() {
        getAjaxData();
        getsensorimages($(this).val());
    })
    $("#isY").click(function () {
        if($(this).is(':checked')){
            $(".chartY").removeAttr('disabled');
        }else{
            $(".chartY").val('').attr("disabled",true);
        }
    })
    getAjaxData();
})
var PrjName = getCookie("prjName");
var statusDes = getCookie("statusDes");
var sensorName = getUrlVars()["sensorname"];
var staName = getUrlVars()["staName"];//设备名
var sensorType = getUrlVars()["sensorType"];
var starttime = getUrlVars()["starttime"];
var endtime = getUrlVars()["endtime"];
var JSONData = [];
getsensorNames(sensorType, PrjName, sensorName);
getsensorimages(sensorName);
if (!CheckValIsNull(starttime)) {
    $(".starttime").val(getRecentweekDays());
} else {
    $(".starttime").val(starttime);
}
if (!CheckValIsNull(endtime)) {
    $(".endtime").val(curDateTime());
} else {
    $(".endtime").val(endtime);
}
function querygnssdata(ymw, this_) {
    if ($(".isY").is(":checked")) {
        var value = $(".chartY").val();
        if (!value || isNaN(value)) {
            alert("y轴限定值必须为数字！");
            return;
        }
    }
    if (!$(this_).hasClass("btn-primary")) {
        $(this_).addClass("btn-primary").siblings("button").removeClass("btn-primary");
    }
    endtime = curDateTime();
    var date = new Date();
    $(".endtime").val(endtime);
    if (ymw == "year") {
        $(".starttime").val(getLastYearYestdy(date));
    } else if (ymw == "month") {
        $(".starttime").val(getLastMonthYestdy(date));
    } else if (ymw == "week") {
        $(".starttime").val(getRecentweekDays());
    } else if (ymw == "day") {
        $(".starttime").val(endtime);
    } else if (ymw == "all") {
        $(".starttime").val("1970-01-01");
        $(".endtime").val(endtime);
    }
    getAjaxData();
}

function getAjaxData() {
    if ($(".isY").is(":checked")) {
        var value = $(".chartY").val();
        if (!value || isNaN(value)) {
            alert("y轴限定值必须为数字！");
            return;
        }
    }
    var selectedSensorName = $("#sensornamelist option:selected").val();
    if (typeof(selectedSensorName) == 'undefined') {
        selectedSensorName = sensorName;
    }
    var param = {
        sensorName: selectedSensorName,
        startTime: $(".starttime").val(),
        endTime: $(".endtime").val()
    };
    $.ajax({
        url: "./../dismeterRecordServlet",
        data: param,
        type: "POST",
        dataType: "JSON",
        beforeSend: function () {
            myChart.showLoading();
        },
        complete: function () {
            myChart.hideLoading();
        },
        success: function (data) {
            if (data != null && data.length > 1) {
                $("#content_right_no_data").hide();
                $("#content_right").show();
                dataLoad(data);
            } else {
                $("#content_right").hide();
                $("#content_right_no_data").show();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            myChart.hideLoading();
            console.log("XMLHttpRequest=" + XMLHttpRequest);
            console.log("textStatus=" + textStatus);
            console.log("errorThrown=" + errorThrown);
        }
    })
}
function dataLoad(data) {
    JSONData = data;
    var numY = null;
    if ($(".isY").is(":checked")) {
        numY = $(".chartY").val();
    }
    var selectStaName = $("#sensornamelist option:selected").text();
    if (typeof(selectStaName) == 'undefined'|| selectStaName=='') {
        selectStaName = staName;
    }
    var dtCreateDT = [];
    var dRealLen = [];
    $.each(data,function(index,result){
        dtCreateDT.push(result.createTime);
        dRealLen.push(result.displacement);
    });
    var option = {
        title: {
            left: 'center',
            text: selectStaName+'位移-时间曲线图',
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
	        bottom: 80
	    },
        legend: {
            data:['X方向角度','Y方向角度'],
            top :25
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: true,
                    title: selectStaName+'沉降-时间数据列表',
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
                    onclick: function (){
                        var namestr=selectStaName+"_"+$("#starttime").val()+"_"+$("#endtime").val();
                        if(JSONData.length<=0){
                            alert("当前数据为空不能导出数据");
                        }else{
                            JSONToCSVConvertor(JSONData, namestr, true);
                        }
                    }
                }
            }
        },
        
        xAxis: {
			name : '时间：s',
			type : 'category',
			data: dtCreateDT.map(function (str) {
                return str.replace(' ', '\n')
            }),
			axisTick: {
				alignWithLabel:true,
				},//刻度跟标签对齐
			boundaryGap : false,
            axisLine: {onZero: false},
		},
        yAxis: {
            name : '单位：mm',
            type: 'value',
            max: numY,
            axisLabel: {
                formatter: '{value} mm'
            }
        },
        //区域缩放
        dataZoom: [
            {
                startValue: '2014-06-01'
            }, {
                type: 'inside'
            }
        ],
        series: [
            {
                name:selectStaName,
                type:'line',
                data:dRealLen,
            }
        ]
    }
    if (option && typeof option === "object") {
        myChart.resize();
        myChart.setOption(option, true);
    }
}
function getsensorNames(sensorType,PrjName,sensor){
    $.post("./../PointAll",{oper:"SensorTypePoints",SensorType:sensorType,PrjName:PrjName},function(data){
        var list = document.getElementById("sensornamelist");
        if(data.length>0){
            var selectStr="<select>";
            $.each(data,function(i,datalist){
                var selected="";
                if(sensorName.toLowerCase()==(datalist.SensorName.toLowerCase())){

                    selected="selected";
                }
                selectStr+="<option value='"+datalist.SensorID+"' "+selected+">"+datalist.staName+"</option>";
            })
            selectStr+="</select>";
            $(".sensornamelist").html(selectStr);
            list.value = sensor;
        }
    })
}
function getsensorimages(sensorName){
    $.post("./../PointAll",{"oper":"ImgUrlBySensorName","SensorName":sensorName},function(data){
        if(data){
            var data=eval("("+data+")");
            var imageUrl=data[0].imgURL;
            if(CheckValIsNull(imageUrl)){
                $("#SenosrImage").attr("src",imageUrl);
            }
        }
    })
}

//导出表格表头中文映射
var chineseLaberMapping={dtCreateDT:"数据时间",dRealLen:"位移量(mm)"};

function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
    var CSV = '';
    //Set Report title in first row or line
    // CSV += ReportTitle + '\r\n\n';
    //This condition will generate the Label/Header
    if (ShowLabel) {
        var row = "";
        //This loop will extract the label from 1st index of on array
        for (var index in arrData[0]) {
            console.log(index);
            //Now convert each value to string and comma-seprated
            var th=index.toString().replace(/_/,'');
            if(row.indexOf(th)>=0){
            }else{
                row += index + ',';
            }
        }
        row = row.slice(0, -1);
        var englishLabels=row.split(",");
        for (var i = 0; i < englishLabels.length; i++) {
            if(chineseLaberMapping[englishLabels[i]]){
                console.log(englishLabels[i]);
                console.log(englishLabels[i]+"--"+chineseLaberMapping[englishLabels[i]]);
                englishLabels[i]=chineseLaberMapping[englishLabels[i]];
            }
        }
        row = englishLabels.join(",");
        //append Label row with line break
        CSV += row + '\r\n';
    }
    //1st loop is to extract each row
    for (var i = 0; i < arrData.length; i++) {
        var row = "";

        //2nd loop will extract each column and convert it in string comma-seprated
        for (var index in arrData[i]) {
            if(index.toString().indexOf("_")>=0){
                break;
            }
            row += '"' + arrData[i][index] + '",';
        }
        row.slice(0, row.length - 1);
        //add a line break after each row
        CSV += row + '\r\n';
    }

    if (CSV == '') {
        alert("Invalid data");
        return;
    }

    //Generate a file name
    var fileName = "";
    //this will remove the blank-spaces from the title and replace it with an underscore
    fileName += ReportTitle.replace(/ /g, "_");

    //Initialize file format you want csv or xls
    var uri = 'data:text/csv;charset=utf-8,\uFEFF' + encodeURI(CSV);

    // Now the little tricky part.
    // you can use either>> window.open(uri);
    // but this will not work in some browsers
    // or you will not get the correct file extension

    //this trick will generate a temp <a /> tag
    var link = document.createElement("a");
    link.href = uri;

    //set the visibility hidden so it will not effect on your web-layout
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";

    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

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
</script>
</body>
</html>
