<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,smartRecruiter.Company, smartRecruiter.Platform" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<jsp:include page="header.jsp" />
	<form action="startTest" method="post">
		<table>
			<tr>
				<td>Company:</td>
				<td><select name="company">
						<option value="none">Select a Company</option>
						<% ArrayList<Company> companies = (ArrayList<Company>)session.getAttribute("companies");
						if(companies != null){
							for(Company com : companies){%>
								<option value="<%=com.getId() %>"><%=com.getName() %> - <%= com.getJobProfile()%></option>
							<%}
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Platform:</td>
				<td><select name="platform">
						<option value="none">Choose a Platform</option>
						<% ArrayList<Platform> platforms = (ArrayList<Platform>)session.getAttribute("platforms");
						if(platforms != null){
							for(Platform plat : platforms){%>
								<option value="<%= plat.getId() %>"><%= plat.getPlatForm() %></option>
							<%}
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Start Test">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>