<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String finalPath=URLEncoder.encode(basePath);
String urlOblix="http://ssodes.unix.banorte.com/oam/server/logout?end_url="+finalPath;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Logout</title>
<script type="text/javascript">
	var ventana=null;
	function cerrarSesion(){
		ventana=window.open('<%=urlOblix%>','_self');
	}
</script>
</head>
<body onload="cerrarSesion();">
	Atendiendo peticion de cierre de sesion.
</body>
</html>
