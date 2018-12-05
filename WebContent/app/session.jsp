
    <%@page import="com.anchor.model.User"%>
     <%@page import="com.anchor.util.StringUtil"%>
  <%

  HttpSession Session = request.getSession();
   String userID=(String)Session.getAttribute("userID");
   String username=(String)Session.getAttribute("username");
   String[] role=(String[])Session.getAttribute("role");
  
   String path = request.getContextPath(); 
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
if(null==userID || userID=="" ){
	response.sendRedirect("login.jsp");
}
   
  %>