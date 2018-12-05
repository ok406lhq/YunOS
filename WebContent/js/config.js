var config={
	"server":"http://118.190.143.70:25030/",
	"dataPath":"jlsz"
}

var calendarStr={
  month_names: ["一月份", "二月份", "三月份", "四月份", "五月份", "六月份", "七月份", "八月份", "九月份", "十月份", "十一月份", "十二月份"],
    short_month_names: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    short_day_names: ["日", "一", "二", "三", "四", "五", "六"],
     short_day_week: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
    start_of_week: 1
}

var marningStr={"Alarm":"一级预警","Action":"二级预警","Alert":"三级预警","All":"全部"}
//var marningStr={"Alarm":"一级预警","Action":"二级预警","Alert":"三级预警","LWPWR":"低电警告","LOFF":"长期离线警告","All":"全部"}
var PageArr={
   "page_Ch":{First:"首页",Last:"末尾",Previous:"上页",Next:"下页",Total:"总共",Page:"页",Records:"条记录",Turnto:"跳转",BtnOk:"OK",BtnNo:"第",BtnTip:"页"},
   "page_En":{First:"First",Last:"Last",Previous:"Previous",Next:"Next",Total:"Total",Page:"Page",Records:"Records",Turnto:"Turn to",BtnOk:"OK",BtnNo:"No.",BtnTip:"Page"},
   "page_korea":{First:"홈",Last:"마지막 페이지",Previous:"위 페이지",Next:"다음 페이지",Total:"총",Page:"페이지",Records:"조기록",Turnto:"이동",BtnOk:"OK",BtnNo:"제",BtnTip:"페이지"}
};
var PageConfig=PageArr.page_Ch;
var touchEvents={
	touchstart:"touchstart",
	touchmove:"touchmove",
	touchend:"touchend",
	
	initTouchEvents:function(){
		if(IsPC()){
			this.touchstart="mousedown";
			this.touchmove="mousemove";
			this.touchend="mouseup";
		}
	}
}
touchEvents.initTouchEvents();
function isWarning(Str){
	if(Str=="正常"){
		return  false;
	}
	return  true;
}
/**************************
返回获取的数据是否合乎需要
*/
function CheckValIsNull(Str){
  if(typeof Str=="undefined"){
    Str=false;
  }
  if(Str=="undefined"){
    Str=false;
  }
  if(Str==null){
    Str=false;
  }
  if(Str=="null"){
    Str=false;
  }
  if(Str==""){
    Str=false;
  }
 if(Str=="false"){
   Str=false;
 }
 if(Str=="正常"){
   Str=true;
 }
  return Str;
}
function getUrlVars() {
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function getParameter(name) { 
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) return unescape(r[2]); return null;
}


//////////////////////////////返回当前日期YYYY-mm-dd
function curDateTime(){
var d = new Date(); 
var year = d.getFullYear(); 
var month = d.getMonth()+1; 
var date = d.getDate(); 
var day = d.getDay();
var curDateTime= year;
if(month>9)
curDateTime = curDateTime +"-"+month;
else
curDateTime = curDateTime +"-0"+month;
if(date>9)
curDateTime = curDateTime +"-"+date;
else
curDateTime = curDateTime +"-0"+date;

return curDateTime; 
}
/* 获取某年某月的最后一天  
               */ 
function getLastDayOfMonth(year,month)
{
  //获取本年本月的第一天日期
  var date = new Date(year,month-1,'01');
  //设置日期
  date.setDate(1);
  //设置月份
  date.setMonth(date.getMonth() + 1);
  //获取本月的最后一天
  cdate = new Date(date.getTime() - 1000*60*60*24);
  //打印某年某月的最后一天
//  alert(cdate.getFullYear()+"年"+(Number(cdate.getMonth())+1)+"月最后一天的日期:"+cdate.getDate()+"日");
  //返回结果
  return cdate.getDate();
}
 function date2str(yy, mm, dd, prenext) {
    var s, d, t, t2;
    t = Date.UTC(yy, mm, dd);
    t2 = 7 * 1000 * 3600 * 24; //加减7天的时间
    if (prenext == 'pre') {
        t-= t2;
    } else {
        t+= t2;
    }
    d = new Date(t);

    s = d.getUTCFullYear() + "-";
    s += ("00"+(d.getUTCMonth()+1)).slice(-2) + "-";
    s += ("00"+d.getUTCDate()).slice(-2);

    return s;
}
 function getYestoday(date){    
  var yesterday_milliseconds=date.getTime()-1000*60*60*24;     
  var yesterday = new Date();     
      yesterday.setTime(yesterday_milliseconds);     
    
  var strYear = yesterday.getFullYear();  
  var strDay = yesterday.getDate();  
  if(strDay<10)  
  {  
	  strDay="0"+strDay;  
  }
  var strMonth = yesterday.getMonth()+1;
  if(strMonth<10)  
  {  
    strMonth="0"+strMonth;  
  }  
  datastr = strYear+"-"+strMonth+"-"+strDay;
  return datastr;
  }
  
  //获得上个月在昨天这一天的日期
  function getLastMonthYestdy(date){
     var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);
   var strYear = date.getFullYear();  
   var strDay = date.getDate();  
   var strMonth = date.getMonth()+1;
   if(strYear%4 == 0 && strYear%100 != 0){
    daysInMonth[2] = 29;
   }
   if(strMonth - 1 == 0)
   {
    strYear -= 1;
    strMonth = 12;
   }
   else
   {
    strMonth -= 1;
   }
     strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];
   if(strMonth<10)  
   {  
    strMonth="0"+strMonth;  
   }
   if(strDay<10)  
   {  
    strDay="0"+strDay;  
   }
   datastr = strYear+"-"+strMonth+"-"+strDay;
   return datastr;
  }
  //获取当前前一天时间
  function getStartDay(){
	  var today=new Date();
      var yesterday_milliseconds=today.getTime()-1000*60*60*24;
       
      var yesterday=new Date();      
      yesterday.setTime(yesterday_milliseconds);      
          
      var strYear=yesterday.getFullYear();   
      var strDay=yesterday.getDate();   
      var strMonth=yesterday.getMonth()+1; 
      if(strDay<10){
    	  strDay = "0"+strDay;
      }
      if(strMonth<10)   
      {   
          strMonth="0"+strMonth;   
      }   
      var strYesterday=strYear+"-"+strMonth+"-"+strDay;   
	   return strYesterday;
  }
  
  //获得上一年在昨天这一天的日期
  function getLastYearYestdy(date){ 
   var strYear = date.getFullYear() - 1;  
   var strDay = date.getDate();  
   var strMonth = date.getMonth()+1;
   if(strMonth<10)  
   {  
    strMonth="0"+strMonth;  
   }
   if(strDay<10)  
   {  
    strDay="0"+strDay;  
   }
   datastr = strYear+"-"+strMonth+"-"+strDay;
   return datastr;
  }
/**************getRecentDays()获取最近一个星期的开始日期****************/
function  getRecentweekDays(){
    var now  = new Date();
    var date = new Date(now.getTime() - 6  * 24 * 3600 * 1000);
    return date.Format("yyyy-MM-dd");
}

function getCookie(name)
{
  var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
  if(arr != null) return unescape(arr[2]); return null;
}

function delCookie(name)
{
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval=getCookie(name);
  if(cval!=null) document.cookie=name +"="+cval+";expires="+exp.toGMTString();
}
function setCookie(name,value)
{
  var Days = 30; //此 cookie 将被保存 30 天
  var exp  = new Date();    //new Date("December 31, 9998");
  exp.setTime(exp.getTime() + Days*24*60*60*1000);
  document.cookie = name + "="+ escape(value) +";expires="+ exp.toGMTString();
}
function   trim(vStr)   { 
      return   vStr.replace(/\s/g, "")
}
function getPastDate(m){
	var n=new Date((new Date()).valueOf(10)-30*m*24*3600000);
	 var year=n.getFullYear();
	 var month = n.getMonth()+1; 
var date = n.getDate(); 
var curDateTime= year;
if(month>9)
curDateTime = curDateTime +"-"+month;
else
curDateTime = curDateTime +"-0"+month;
if(date>9)
curDateTime = curDateTime +"-"+date;
else
curDateTime = curDateTime +"-0"+date;

return curDateTime; 
}
 function IsPC() 
       { 
           var userAgentInfo = navigator.userAgent; 
           var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"); 
           var flag = true; 
           for (var v = 0; v < Agents.length; v++) { 
               if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; } 
           } 
           return flag; 
       } 


function wtyan(){
		this.dataType="json";
		this.type="POST";
		this.timeout=3000;
		this.data={};
		this.AjaxGet=function(url,_scuessfun){
			var isconnect=false;
			if(config.platform==1){
					var isConnectIntel=window.AndroidData.isConnect();
					if(isConnectIntel=="true"){
						isconnect=true;
					}else{
						isconnect=false;
						alert("网络连接不正常！");
					}
			}else{
			     isconnect=true;
			}
			if(isconnect){
				
		 $.ajax({
		  url:url,
		   dataType:this.dataType,  
		  async:false,
		 type:this.type,data:this.data, 
		 timeout:this.timeout, 
		  beforeSend:this.beforhand, success:function(data){  LoadingHide();_scuessfun(data);},error:this.handerror
			});
			
			}
		}
		
		this.beforhand=function(){

      Loading("please write.....");
    }
		this.handerror=function(){
			 LoadingHide();
			 WrongAlert();
			
		}
}

function WrongAlert(){
	   var doc = window.document;
	      var cw = doc.compatMode == "BackCompat" ? doc.body.clientWidth : doc.documentElement.clientWidth;
         var ch = doc.compatMode == "BackCompat" ? doc.body.clientHeight : doc.documentElement.clientHeight;
		     var sl = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
    var st = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop); //考虑滚动的情况
	 var bgObj = document.createElement("div"); 
	 var scrt=ch+st;
	var ck=280;
	if(IsPC()){
		ck=cw*0.3;
	}
    var L=(cw-ck)/2;
 bgObj.className="wrongAlert";
 bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:100%;height:"+scrt+"px;filter:Alpha(Opacity=50);opacity:0.5;background-color:#ffffff;z-index:101;";
 $("body").append(bgObj); 
 $("body").append("<div class='wrongAlert_cnt' style='position:absolute; width:"+ck+"px; height:"+ck+"px;  text-align:center; border-radius:50%;  top:10%; left:"+L+"px;z-index:1000000000' onclick='hideWrongAlert()'><img src='../js/skins/icons/warning.png' style='margin-top:60px; cursor:pointer' width=48 ><br><br><button style='border:none;font-size:20px; background:none; cursor:pointer; color:#999' class='reset_btn'>未查询到数据</button></div>");
  	   $(window).scroll(function(){  
   var vtop=$(document).scrollTop();
     var xv=eval(vtop+100)+"px";
	   

   $(".wrongAlert_cnt").animate({top:xv},0);
  
  })
 

}

function hideWrongAlert(){
	  $(".wrongAlert").remove(); $(".wrongAlert_cnt").remove();
}

function Loading(str){
	
$("body").append("<div class='loadalert' style='position: absolute;width:100%; height:100%; text-align:center;z-index:100000000000'><div style='margin-top:20%;   '><img src='../images/loading.gif'><br><br>"+str+"</div></div>");
var wd=$("body").width();
var wh=$("body").height();
var a_wd=$(".loadalert").width();
var a_hd=$(".loadalert").height();

$(".loadalert").css({"left":((wd-a_wd)/2)+"px","top":((wh-a_hd)/2)+"px"});

}
function LoadingHide(){
	$("body").find(".loadalert").remove();

}

function AlertStr(){
	var h=$(document).height();
$("body").append("<div class='loadalert' style='position: absolute;width:100%; height:"+h+"px;background-color:rgba(0,0,0,0.2); text-align:center;z-index:100000000000'><li style='padding-top:40%'><img src='../images/loading.gif' style='padding:20px;border-radius:6px;background:#fff'></li></div>");
var wd=$("body").width();
var wh=$("body").height();
var a_wd=$(".loadalert").width();
var a_hd=$(".loadalert").height();
$(".loadalert").css({"left":((wd-a_wd)/2)+"px","top":((wh-a_hd)/2)+"px"});

}
function tip(msg,autoClose){
     var div = $("#poptip");
     var content =$("#poptip_content");
     if(div.length<=0){
         div = $("<div id='poptip'></div>").appendTo(document.body);
         content =$("<div id='poptip_content'>" + msg + "</div>").appendTo(document.body);
     }
     else{
         content.html(msg);
         content.show(); div.show();
     }
     if(autoClose) {
        setTimeout(function(){
            content.fadeOut(500);
            div.fadeOut(500);

        },1000);
     }
}
function tip_close(){
    $("#poptip").fadeOut(500);
     $("#poptip_content").fadeOut(500);
}

 /**
  * 去除数组重复元素
  */
function uniqueArray(data){  
   data = data || [];  
   var a = {};  
   for (var i=0; i<data.length; i++) {  
       var v = data[i];  
       if (typeof(a[v]) == 'undefined'){  
            a[v] = 1;  
       }  
   };  
   data.length=0;  
   for (var i in a){  
        data[data.length] = i;  
   }  
   return data;  
}  

 /**
  * 比较两个日期
  */
function GetDateDiff(startTime, endTime, diffType) {
           
            startTime = startTime.replace(/\-/g, "/");
            endTime = endTime.replace(/\-/g, "/");
            diffType = diffType.toLowerCase();
            var sTime = new Date(startTime);      //开始时间
            var eTime = new Date(endTime);  //结束时间
            //作为除数的数字
            var divNum = 1;
            switch (diffType) {
                case "second":
                    divNum = 1000;
                    break;
                case "minute":
                    divNum = 1000 * 60;
                    break;
                case "hour":
                    divNum = 1000 * 3600;
                    break;
                case "day":
                    divNum = 1000 * 3600 * 24;
                    break;
                default:
                    break;
            }
            return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
        }

    
 var sensorTypeStr={"USS":"超声测距","RainGauge":"雨量","Prism":"测量机器人棱镜",
		 "PiezoMeter":"地下水位","LRDM":"裂缝监测","loadcell":"土压力","Inclinometer":"深部位移",
		 "GNSS":"北斗","CrackMeter":"表面侧缝计","DisMeter":"拉线位移计","Tiltmeter":"倾度盘","JLSZ":"JLSZ","THMeter":"温湿度传感器",
		 "SMS":"SMS","SFLGauge": "静力水准仪","OsmoMeter":"渗压计","CorrosionMeter":"腐蚀传感器","LoadCell":"称重传感器",
		 "VibrationCell":"振动传感器","FlexoMeter":"液压变送器","ClinoMeter":"倾角仪","CableForceMeter":"索力计","RropeMeter":"锚索计",
		 "TemperatureMeter":"温度传感器","EarthPreCell":"土压力计","SoilHumidity":"土壤含水率","StrainGauge":"应变计","UpliftPressure":"扬压力","AnemoMeter":"风速风向计"};
 var roleCH={"add":"添加","edit":"编辑","view":"查看","del":"删除","export":"导出","admin":"管理员"};

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
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