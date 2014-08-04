<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Smart Recruiter</title>
</head>
<body>
<table width="80%">
<tr>
<td style="width:40%"><img src ="./images/sr.jpg" width ="250px" height = "100px" /></td>
<td><b><font size="20">Smart Recruiter</font></b></td>
</tr>
</table>
<br/>
<% 
String msg=(String)request.getAttribute("message");
if(msg!=null)
{
%>
<%= msg %>
<%
}
%>
<br/>
</body>
</html>