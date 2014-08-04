<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,smartRecruiter.Company" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Recruiter</title>
</head>
<body>
	<table>
		<tr>
			<td>Position:</td>
			<td><select name="position" onChange="load(this)">
					<option value="none">Select a position</option>
					<%
						ArrayList<Company> companies = (ArrayList<Company>) session.getAttribute("companies");
						if (companies != null) {
							for (Company com : companies) {
					%>
					<option value=<%=com.getId()  %>><%=com.getName()%> - <%=com.getJobProfile()%></option>
					<%
							}
						}
					%>
			</select></td>
		</tr>
	</table>
</body>
</html>