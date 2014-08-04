<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Recruiter</title>
</head>
<body>
	<form action="addJob" method="post">
		<table>
			<tr>
				<td>Company :</td>
				<td><input type="text" name="company"><br></td>
			</tr>
			<tr>
				<td>Job Profile :</td>
				<td><textarea rows="10" cols="30" name="profile"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit"></td>
			</tr>
		</table>
	</form>
</body>
</html>