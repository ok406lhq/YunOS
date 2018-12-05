/*
 * dialog
 * http://sufangyu.github.io
 * 方雨_Yu
 * 1.0.0(2016-06-04)
 */
;(function(win,$){

    /*
     * Private methods 
     */    
    var wrap, overlay, content, title, close, cancelBtn, okBtn, delBtn, settings, timer;

    var _renderDOM = function(){
        if( $('.dialog-wrap').length > 0){
            return;
        }

        clearTimeout(timer);
        settings.onBeforeShow();
        
        $('body').append( dialogWrapper = $('<div class="dialog-wrap '+ settings.dialogClass +'"></div>') );
        dialogWrapper.append(
            overlay = $('<div class="dialog-overlay"></div>'),
            content = $('<div class="dialog-content"></div>')
        );

        switch (settings.type){
            case 'alert' :
                if(settings.showTitle){
                    content.append(
                        title = $('<div class="dialog-content-hd"><h4 class="dialog-content-title">'+ settings.titleText +'</h4></div>')
                    );
                }
                content.append(
                    contentBd = $('<div class="dialog-content-bd">'+ settings.contentHtml +'</div>')
                );
                content.append(
                    contentFt = $('<div class="dialog-content-ft"></div>')                   
                );
                contentFt.append(
                    okBtn = $('<a class="dialog-btn dialog-btn-ok '+ settings.buttonClass.ok +'" href="javascript:;">'+ settings.buttonText.ok +'</a>')
                );
                break;

            case 'confirm' :
                if(settings.showTitle){
                    content.append(
                        title = $('<div class="dialog-content-hd"><h4 class="dialog-content-title">'+ settings.titleText +'</h4></div>')
                    );
                }
                content.append(
                    contentBd = $('<div class="dialog-content-bd">'+ settings.contentHtml +'</div>')
                );
                content.append(
                    contentFt = $('<div class="dialog-content-ft"></div>')
                );
                contentFt.append(
                    cancelBtn = $('<a class="dialog-btn dialog-btn-cancel '+ settings.buttonClass.cancel +'" href="javascript:;">'+ settings.buttonText.cancel +'</a>'),
                    okBtn = $('<a class="dialog-btn dialog-btn-ok '+ settings.buttonClass.ok +'" href="javascript:;">'+ settings.buttonText.ok +'</a>')
                );
                break;

            case 'info' :
                var infoContent = settings.contentHtml || '<img class="info-icon" src="'+ settings.infoIcon +'" alt="'+ settings.infoText +'" /><p class="info-text">'+ settings.infoText +'</p>';
                content.append(
                    contentBd = $('<div class="dialog-content-bd">'+ infoContent +'</div>')
                );
                dialogWrapper.addClass('dialog-wrap-info');
                content.addClass('dialog-content-info');
                break;

            default :
                break;
        }

        setTimeout(function(){
            dialogWrapper.addClass('dialog-wrap-show');
            settings.onShow();
        }, 10);

    };

    var _bindEvent = function() {

        $(okBtn).on('click', function(e){
            settings.onClickOk();
            $.dialog.close();
            return false;
        });

        $(cancelBtn).on('click', function(e){
            settings.onClickCancel();
            $.dialog.close();
            return false;
        });

        // overlay clisk hide
        if( settings.overlayClose ){
            overlay.on('click', function(e){
                $.dialog.close();
            });
        }

        // auto close, set autoClose and type isn't info
        if( settings.autoClose > 0 ){
            _autoClose();
        }

    };

    var _autoClose = function(){
        clearTimeout(timer);
        timer = window.setTimeout(function(){
            $.dialog.close();
        }, settings.autoClose);
    };



    /*
     * Public methods 
     */

    $.dialog = function(options) {
        settings = $.extend({}, $.fn.dialog.defaults, options);        
        $.dialog.init();
        return this;
    };   

    $.dialog.init = function(){
        _renderDOM();
        _bindEvent();
    };


    $.dialog.close = function(){
        settings.onBeforeClosed();

        dialogWrapper.removeClass('dialog-wrap-show');
        setTimeout(function(){
            dialogWrapper.remove();
            settings.onClosed();
        }, 200);
    };

    $.dialog.update = function(params) {
        if(params.infoText) {
            content.find('.info-text').html(params.infoText);
        }
        if(params.infoIcon) {            
            content.find('.info-icon').attr('src', params.infoIcon);
        }
        if(params.autoClose>0){
            window.setTimeout(function(){
                $.dialog.close();
            }, params.autoClose);
        }
    };


    // 插件
    $.fn.dialog = function(options){
        return this;
    };


    $.fn.dialog.defaults = {
        type : 'alert',     // alert、confirm、info
        titleText : '信息提示',
        showTitle : true,
        contentHtml : '',
        dialogClass : '',
        autoClose : 0,
        overlayClose : false,
        drag : false,

        buttonText : {
            ok : '确定',
            cancel : '取消',
            delete : '删除'
        },
        buttonClass : {
            ok : '',
            cancel : '',
            delete : ''
        },

        infoText : '',      // working in info type
        infoIcon : '',      // working in info type

        onClickOk : function(){},
        onClickCancel : function(){},
        onClickClose : function(){},

        onBeforeShow : function(){},
        onShow : function(){},
        onBeforeClosed : function(){},
        onClosed : function(){}
    }

})(window, window.Zepto || window.jQuery);

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

function CheckVal(Str){
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
  return Str;

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

function CurentTime()
    { 
        var now = new Date();
       
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       
        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var m=now.getSeconds();
        var clock = year + "-";
       
        if(month < 10)
            clock += "0";
       
        clock += month + "-";
       
        if(day < 10)
            clock += "0";
           
        clock += day + " ";
       
        if(hh < 10)
            clock += "0";
           
        clock += hh + ":";
        if (mm < 10) clock += '0'; 
        clock += mm+ ":"; 
        if(m<10) clock+='0';
           clock += m; 
        
        return(clock); 
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
  return Str;

}

function format_number(pnumber,decimals){
    if (isNaN(pnumber)) { return 0};
    if (pnumber=='') { return 0};
     
    var snum = new String(pnumber);
    var sec = snum.split('.');
    var whole = parseFloat(sec[0]);
    var result = '';
     
    if(sec.length > 1){
        var dec = new String(sec[1]);
        dec = String(parseFloat(sec[1])/Math.pow(10,(dec.length - decimals)));
        dec = String(whole + Math.round(parseFloat(dec))/Math.pow(10,decimals));
        var dot = dec.indexOf('.');
        if(dot == -1){
            dec += '.';
            dot = dec.indexOf('.');
        }
        while(dec.length <= dot + decimals) { dec += '0'; }
        result = dec;
    } else{
        var dot;
        var dec = new String(whole);
        dec += '.';
        dot = dec.indexOf('.');    
        while(dec.length <= dot + decimals) { dec += '0'; }
        result = dec;
    }  
    return result;
}
function ajaxquerydata(){
    this.dataType="json";
    this.type="POST";
    this.timeout=3000;
    this.data={};
    this.AjaxGet=function(url,_scuessfun){
      var isconnect=false;
      
        
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
    
    this.beforhand=function(){Loading("please write.....");}
    this.handerror=function(){
       LoadingHide();
       WrongAlert();
      
    
}


function Loading(str){
var wd=$(window).width();
var wh=720;

$("body").append("<div class='loadalert' style='position: absolute;width:100%; height:670px;float:left; text-align:center; background:#fff;z-index:100000000000;  top:0; left:0'><img src='images/loading.gif' style=' background:#fff;position: absolute; width:42px; height:42px'></div>");

var a_wd=$(".loadalert img").width();
var a_hd=$(".loadalert img").height();

var h=(wh-a_hd)/2;
var w=(wd-a_wd)/2;
$(".loadalert img").css({"left":w+"px","top":h+"px"});

}

function LoadingHide(){
  $("body").find(".loadalert").remove();
  
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
 bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:100%;height:"+scrt+"px;filter:Alpha(Opacity=50);opacity:0.8;background-color:#ffffff;z-index:101;";
 $("body").append(bgObj); 
 $("body").append("<div class='wrongAlert_cnt' style='position:absolute; width:180px;float:left;  text-align:center; border-radius:50%;  top:40%; left:80px;z-index:1001' onclick='hideWrongAlert()'><button style='border:none;font-size:18px; background:none; cursor:pointer; color:#999;width:100%;text-align' class='reset_btn'> 没有查询到数据！</button></div>");
       $(window).scroll(function(){  
   var vtop=$(document).scrollTop();
     var xv=eval(vtop+100)+"px";
     

   $(".wrongAlert_cnt").animate({top:xv},0);
  
  })
 

}



var TopBox = {

    alert: function (content, succ) {
        if ($("#popTips").length > 0) $("#popTips").remove();

        box = '<div style="top: 20%; display: block;" id="popTips" class="pop_tips">' +
            '<div class="oval"></div>' +
            '<div class="pop_show">' +
                '<h4 id="tipsTitle">温馨提示</h4>' +
                '<div id="tipsMsg" class="pop_info">' + content + '</div>' +
                '<div class="pop_btns">' +
                '   <a id="tipsOK" href="javascript:void(0);">确定</a> <a style="display: none;" id="tipsCancel" href="javascript:void(0);">取消</a>' +
            '</div></div></div>';
        var $top = $(box).appendTo($("body"));
        $top.find("#tipsOK").click(function () { if ($("#popTips").length > 0) $("#popTips").remove(); if (typeof succ === "function") succ(); });
        $top.find("#tipsCancel").click(function () { if ($("#popTips").length > 0) $("#popTips").remove(); });
    }
}