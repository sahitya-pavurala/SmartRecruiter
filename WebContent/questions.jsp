<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
     <title>Smart Recruiter</title>
     <link href="CSS/style-index.css" rel="stylesheet" type="text/css" media="screen">
  </head>
<script>
function showQuestion(number, count)
{
	for (var i=1;i<=count;i++){
		document.getElementById('displayQuestion'+ i).style.display = 'none';
		document.getElementById('Answer'+ i).style.display = 'none';
	}
	document.getElementById('displayQuestion'+ number).style.display = 'block';
	document.getElementById('Answer'+ number).style.display = 'block';
}
     </script>
<body>
<jsp:include page="header.jsp" />
	<div>
		<div id="Questions" style="height: 500px; width: 200px; float: left;">
			<ul style="list-style-type:none">
				<% ArrayList<String> questions = (ArrayList<String>)session.getAttribute("questions");%>
                  <% for(int i=0; i<questions.size(); i++){
                	  %>
				<li onclick="showQuestion(<%= i+1%>, <%=questions.size() %>);"><a href="javascript:void(0)">Question <%=i+1 %></a></li>
				<%} %>
			</ul>
		</div>
		<form method="post" action="submitTest">
			<% for(int i=0; i<questions.size(); i++){ 
				if(i==0){%>
					<div id="displayQuestion<%= i+1%>"><%= questions.get(i)%></div>
					<div id="Answer<%= i+1%>">
						<textarea rows="25" cols="100" name="Solution<%= i+1%>"></textarea>
					</div>
				<%}else{%>
					<div id="displayQuestion<%= i+1%>" style=display:none><%= questions.get(i)%></div>
					<div id="Answer<%= i+1%>" style=display:none>
						<textarea rows="25" cols="100" name="Solution<%= i+1%>"></textarea>
					</div>
				<%} %>
			<%} %>
			<input type="submit" value="submit">
			<input type="hidden" name="company" value="<%=request.getAttribute("company") %>">
			<input type="hidden" name="platform" value="<%=request.getAttribute("platform") %>">
		</form>
	</div>
</body>
</html>