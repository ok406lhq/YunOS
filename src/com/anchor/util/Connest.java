package com.anchor.util;

public class Connest {
   public static   String DbName="";
   public static   String DefaultPrjName="";
   public static   String dbUrl="";
   public static   String dbUserName="";
   public static String dbPassword="";
   public static   String jdbcName="";
   public static   String UploadFile="uploadfiles/";
//   public static final String UrlAndPort="http://172.20.39.14:80/214";
   
   public static final String UrlAndPort="http://bdmp-api.githubshop.com";//友情提示：各位同门师兄弟提交代码的时候不要覆盖此行，默认的是正式环境。手下留情，好人一生平安。
//   public static final String UrlAndPort="http://58.20.17.214:8080"; //测试环境

   
   
   //2.6锛堝凡娴嬶級
   public static final String URL_CRACKMETER_QuerycrackmeterResultJson = UrlAndPort+"/web/crackmeter_result_dao/querycrackmeterResultJson/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";
   
   //2.9锛堝凡娴嬶級
   public static final String URL_GSNN_QueryGNSSResultJson = UrlAndPort+"/web/g_n_s_s_result_dao/newqueryGNSSResultJson/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s/filter/%s";
   //2.10
   public static final String URL_IPI_QueryIPIResultList  = UrlAndPort+"/web/i_p_i_result_dao/queryIPIResultListif/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s/filter/%s";
   //2.11锛堝凡娴嬶級
   public static final String URL_Login=UrlAndPort+"/web/user_login/dopost/userName/%s/password/%s";
   //2.12锛堝凡娴嬶級
   public static final String URL_PROJECT_QueryMyPrjInfoList=UrlAndPort+"/web/project_info_dao/queryMyPrjInfoList/myproject/%s";
   //2.13锛堝凡娴嬶級
   public static final String URL_USER_QueryUserIfonByID = UrlAndPort+"/web/user_info_dao/queryuserifobyid/userID/%s";
   //2.14锛堝凡娴嬶級
   public static final String URL_WARNING_QueryWarningInfoTotalRecord = UrlAndPort+"/web/warning_info_dao/querywarningInfoTotalRecord/active/%s/starttime/%s/endtime/%s";
   //2.15锛堝凡娴嬶級
   //棰勮淇℃伅
   public static final String URL_WARNING_QueryLogListByActiveName = UrlAndPort+"/web/warning_info_dao/queryLogListByActive/active/%s/projectabbr/%s/starttime/%s/endtime/%s/page/%s/pagesize/%s/myproject/%s";
   public static final String URL_WARNING_QueryLogListByActiveType = UrlAndPort+"/web/warning_info_dao/queryLogListByActive/active/%s/projecttype/%s/starttime/%s/endtime/%s/page/%s/pagesize/%s/myproject/%s";
   public static final String URL_WARNING_QueryLogListByActiveNull = UrlAndPort+"/web/warning_info_dao/queryLogListByActive/active/%s/starttime/%s/endtime/%s/page/%s/pagesize/%s/myproject/%s";
   public static final String URL_WARNING_QueryLogListByActive = UrlAndPort+"/web/warning_info_dao/queryLogListByActive/active/%s/projecttype/%s/projectabbr/%s/starttime/%s/endtime/%s/page/%s/pagesize/%s/myproject/%s";
   //2.16锛堝凡娴嬶級
   public static final String URL_WARNING_QuerySensorInfoTotalRecord = UrlAndPort+"/web/warning_info_dao/QuerySensorInfoTotalRecord/sensor/%s/starttime/%s/endtime/%s";
   //2.17锛堝凡娴嬶級
   public static final String URL_WARNING_QueryLogListBysensor = UrlAndPort+"/web/warning_info_dao/queryLogListBysensor/sensor/%s/starttime/%s/endtime/%s";
   //2.18锛堝凡娴嬶級
   public static final String URL_WARNING_QueryWaringInfoList = UrlAndPort+"/web/warning_info_dao/queryWaringInfoList/myproject/%s";
   //2.19锛堝凡娴嬶級
   public static final String URL_WARNING_QueryJCJB  = UrlAndPort+"/web/warning_info_dao/queryJCJB/prjdice/%s";
   //2.20锛堝凡娴嬶級
   public static final String URL_SYSTEM_LOG_SaveAddSystemLog = UrlAndPort+"/web/system_log_dao/saveaddsystemlog/userid/%s/username/%s/active/%s/descript/%s/adddate/%s";
   //2.21锛堝凡娴嬶級
   public static final String URL_SYSTEM_LOG_QuerySystemLogoTotalRecord = UrlAndPort+"/web/system_log_dao/querySystemLogoTotalRecord/userid/%s/starttime/%s/endtime/%s";
   
   //2.22锛堟帴鍙ｆ祴閫氾紝椤甸潰浣嶇疆:棣栭〉绯荤粺鏃ュ織锛涢渶瑕佷笌UserInfoDao.QueryUserInfoAll鍏宠仈浣跨敤锛�
   public static final String URL_SYSTEM_LOG_QuerySystemLogoDataList  = UrlAndPort+"/web/system_log_dao/querySystemLogoDataList/userid/%s/starttime/%s/endtime/%s/page/%s/pagesize/%s";
   
   public static final String URL_SYSTEM_LOG_QueryMoniteSiteTotalRecord = UrlAndPort+"/web/monite_site_dao/queryMoniteSiteTotalRecord/siteid/%s";
   
   public static final String URL_SYSTEM_LOG_QueryMoniteSiteDataList  = UrlAndPort+"/web/monite_site_dao/queryMoniteSiteDataList/siteid/%s/page/%s/pagesize/%s";
   
   //2.23 鏃ュ織涓嶅彲鍒犻櫎锛屽洜姝ゆ鎺ュ彛绂佺敤
   //2.24锛堝凡娴嬶紝澶氫釜瀛楁涓虹┖锛�
   public static final String URL_POINT_QueryPtInfoList  = UrlAndPort+"/web/point_info_dao/queryPtInfoList/prjName/%s";
   //2.25锛堝凡娴嬶級
   public static final String URL_PROJECT_QueryPrjInfoList  = UrlAndPort+"/web/project_info_dao/queryPrjInfoList/prjName/%s";
   //2.26锛堝凡娴嬶級
   public static final String URL_PROJECT_QueryProjectsInfoByprjName  = UrlAndPort+"/web/project_info_dao/QueryProjectsInfoByprjName/prjName/%s";
   //2.27锛堝凡娴嬶級
   public static final String URL_POINT_UpdatePointImageXY = UrlAndPort+"/web/point_info_dao/updatePointImageXY/imgX/%s/imgY/%s/sensorname/%s";
   //2.28锛堝凡娴嬶級
   public static final String URL_PROJECT_QueryPtInfoJson = UrlAndPort+"/web/project_info_dao/queryPtInfoJson";
   //2.29锛堝凡娴嬶級
   public static final String URL_PROJECT_SaveProjectImageBg = UrlAndPort+"/web/project_image_bg/SaveProjectImageBg/prjName/%s/imgUrl/%s";
   //2.30锛堝凡娴嬶級
   public static final String URL_PROJECT_UpdateProjectInfo = UrlAndPort+"/web/project_info_dao/updateProjectInfo/prjName/%s/PrjAlias/%s/PrjType/%s/PrjAddr/%s/starttime/%s/endtime/%s/lati/%s/longti/%s";
   //2.31锛堝凡娴嬶級
   public static final String URL_PROJECT_Getuserproject  = UrlAndPort+"/web/project_info_dao/getuserproject/username/%s";
   //2.32锛堝凡娴嬶級
   public static final String URL_POINT_QueryPtInfoBySensorName = UrlAndPort+"/web/point_info_dao/queryPtInfoBySensorName/prjName/%s/sensorName/%s";
   //2.33锛堟帴鍙ｆ祴閫氾紱椤甸潰浣嶇疆锛氫富椤甸潰鏁版嵁瀵规瘮閫夐」锛涚敱浜庤鎺ュ彛閰嶅悎2.3鎺ュ彛浣跨敤鏆傛椂杩樹笉鑳藉湪椤甸潰鐪嬭鏁堟灉锛�
   public static final String URL_POINT_QueryPtInfoOnlyBySensorName = UrlAndPort+"/web/point_info_dao/queryPtInfoOnlyBySensorName/sensorName/%s";
   //2.34锛堝凡娴嬶級
   public static final String URL_POINT_QueryPtInfoBySensorTypeList  = UrlAndPort+"/web/point_info_dao/queryPtInfoBySensorTypeList/prjName/%s/sensorType/%s";
   //2.35锛堝凡娴嬶級
   public static final String URL_POINT_QueryPtInfoJson = UrlAndPort+"/web/point_info_dao/queryPtInfoJson/prj/%s";
   //2.36锛堝凡娴嬶級
   public static final String URL_POINT_QueryPointBySensorType = UrlAndPort+"/web/point_info_dao/QueryPointBySensorType/sensorType/%s";
   //2.37锛堝凡娴嬶級
   public static final String URL_POINT_QueryPointsByGroupName  = UrlAndPort+"/web/point_info_dao/QueryPointsByGroupName/groupName/%s";
   //2.38锛堝凡娴嬶級
   public static final String URL_POINT_QueryPointsSensorName  = UrlAndPort+"/web/point_info_dao/QueryPointsSensorName/sensorName/%s";
   //2.39锛堝凡娴嬶級
   public static final String URL_POINT_QueryPointsSensorNameToImgUr = UrlAndPort+"/web/point_info_dao/QueryPointsSensorNameToImgUr/sensorName/%s";
   
   //2.40-2.43绂佺敤
   //2.44锛堝凡娴嬶級
   public static final String URL_LOADCELL_QueryLoadcellResultJson = UrlAndPort+"/web/loadcell_result_dao/queryLoadcellResultJson/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";
   //2.45锛堝凡娴嬶級
   public static final String URL_LRDM_QueryLrdmResultJson = UrlAndPort+"/web/lrdm_result_dao/queryLrdmResultJson/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";
   //2.46锛堝凡娴嬶級
   public static final String URL_PIEZOMETER_QueryPiezometerResultJson = UrlAndPort+"/web/piezometer_result_dao/queryPiezometerResultJson/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";
   //2.47锛堝凡娴嬶級
   public static final String URL_PRISM_QueryPrismResultJson  = UrlAndPort+"/web/prism_result_dao/queryPrismResultJson/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";
   //2.48锛堝凡娴嬶級
   public static final String URL_RAIN_QueryRainResultJson = UrlAndPort+"/web/rain_result_dao/queryRainResultJson/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";
   //2.49锛堟湇鍔″櫒绔緟淇敼锛�
   public static final String URL_REPORT_QueryReports  = UrlAndPort+"/web/report_info_dao/queryReports/prjName/%s/starttime/%s/endtime/%s/sReportType/%s";
   //2.50锛堝湪鏈嶅姟鍣ㄧ瀹屾垚锛�
   //2.51锛堟湇鍔″櫒绔緟淇敼锛�
   //2.52
   public static final String URL_UPDATE_updateinfo  = UrlAndPort+"/web/update_info_dao/updateinfo/userid/%s/id/%s/info/%s";
   //2.56(宸叉祴)
   public static final String URL_USER_UserInfoEdit  = UrlAndPort+"/web/user_info_dao/UserInfoEdit/userID/%s/sUserName/%s/sTel/%s/sEMail/%s/sPartMent/%s/sPosition/%s/sAddress/%s";
   public static final String URL_USER_UserInfoEdit_pwd  = UrlAndPort+"/web/user_info_dao/UserInfoEdit/userID/%s/sUserName/%s/sTel/%s/sEMail/%s/sPartMent/%s/sPosition/%s/sAddress/%s/passWord/%s";
   
   //鍊捐鐩樻煡璇�  
   public static final String URL_TILTMETER_QueryTiltmeterResult = UrlAndPort+"/web/tiltmeter_result_dao/queryTiltmeterResult/prjName/%s/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";
   
   //鏍规嵁椤圭洰ID鏌ヨ璀﹀憡淇℃伅鎬昏褰曟暟
   public static final String URL_WARNING_QueryWaringcount = UrlAndPort+"/web/warning_info_dao/queryWaringcount/myproject/%s";
   //鏍规嵁缁勫悎鏉′欢鏌ヨ璀﹀憡淇℃伅鎬昏褰曟暟
   
   //http://172.10.3.45/BDSIMP/public/web/warning_info_dao/queryAllWarncount/active/All/structure_type/All/structure_name/All/starttime/20130622/endtime/20180622/myproject/1%2C2%2C7%2C13
   
   public static final String URL_WARNING_QueryWaringcountByActiveName = UrlAndPort+"/web/warning_info_dao/queryAllWarncount/myproject/%s/active/%s/name/%s/starttime/%s/endtime/%s";
   public static final String URL_WARNING_QueryWaringcountByActiveType = UrlAndPort+"/web/warning_info_dao/queryAllWarncount/myproject/%s/active/%s/type/%s/starttime/%s/endtime/%s";
   public static final String URL_WARNING_QueryWaringcountByActiveNull = UrlAndPort+"/web/warning_info_dao/queryAllWarncount/myproject/%s/active/%s/starttime/%s/endtime/%s";
   public static final String URL_WARNING_QueryWaringcountByActive = UrlAndPort+"/web/warning_info_dao/queryAllWarncount/myproject/%s/active/%s/structure_type/%s/structure_name/%s/starttime/%s/endtime/%s/";
   //棰勮纭
   public static final String URL_WARNING_UpdateWarningConfirmInfo = UrlAndPort+"/web/warning_info_dao/updateWarningConfirmInfo/id/%s";
   
   //鎶ヨ〃姣忔鏌ョ湅鏇存柊鏌ョ湅娆℃暟
   public static final String URL_REPORT_UpdateReadTimes  = UrlAndPort+"/web/report_info_dao/updateReadTimes/id/%s";
   //闆ㄩ噺缁熻 鏃� 鏈� 骞�
   public static final String URL_RAIN_QueryRainResultSum = UrlAndPort+"/web/rain_result_dao/queryRainResultSum/SensorName/%s/starttime/%s/endtime/%s/ymw/%s";

   
   //棣栭〉鏁版嵁鍏宠仈闇�瑕佷笌2.7鎺ュ彛鑱斿悎浣跨敤
   //棣栭〉棰勮淇℃伅-->鎿嶄綔淇濆瓨澶勭悊鎰忚闇�瑕佷笌updateinfoDao.updateinfo鑱斿悎浣跨敤
   //棣栭〉娴嬬偣棰勮鍊�-->鎿嶄綔鎻愪氦闇�瑕丳ointInfoDao.editPointBySensorType鎺ュ彛锛涘瓙鑿滃崟鎻愪氦闇�瑕丳ointInfoDao.editPointBySensorName鎺ュ彛
   //棣栭〉鍛婅浜洪厤缃偣鍑荤紪杈戞寜閽椂闇�瑕佸皢褰撳墠椤圭洰鐨刬d浼犺繃鍘�
}