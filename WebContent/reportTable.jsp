<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,smartRecruiter.CandidateInfoBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Recruiter</title>
</head>
<body>
<% List<CandidateInfoBean> candidates = (ArrayList<CandidateInfoBean>)session.getAttribute("candidates");
		if(candidates != null && !candidates.isEmpty()){ %>
Candidates appeared for : <%=candidates.get(0).getJobId() %><br><br> 
<table border="1" style="width: 500px;">
	<tr>
		<th>ID</th>
		<th>First_Name</th>
		<th>Last_Name</th>
		<th>Linkedin Profile</th>
		<th>Platform</th>
		<th>Interview Packet</th>
	</tr>
	<% 	for(CandidateInfoBean cand : candidates){%>
				<tr>
					<td><%=cand.getId() %></td>
					<td><%=cand.getFirstName() %></td>
					<td><%=cand.getLastName() %></td>
					<td><a href="<%=cand.getLinkToProfile() %>" target="_blank"><%=cand.getLinkToProfile() %></a></td>
					<td><%=cand.getPlatform() %></td>
					<td><%=cand.getInterviewProfile() %></td>
				</tr>
		<%}
		}%>
		<tr>
			<td colspan="6">Note : Place the above bucket link into S3Browser. <a href="http://s3browser.com/download.php">Click here</a> to download</td>
		</tr>
</table>
</body>
</html>