<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザ管理</title>
<link href="<%= request.getContextPath() %>/view/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="../layout/header.jsp"%>
	
	<!-- Begin vung input-->	
		<form action="listAllUser.do?type=back" method="post" name="inputform">
		<table  class="tbl_input"   border="0" width="80%"  cellpadding="0" cellspacing="0" >	
			<tr>
				<td align="center" colspan="2">
					<div style="height:50px"></div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<font color = "red">Hiện thị câu thông báo lỗi ở đây.</font>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<div style="height:70px"></div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input class="btn" type="submit" value="OK" onclick=""/>
				</td>
			</tr>
		</table>
		</form>
	<!-- End vung input -->
	
	<%@ include file = "../layout/footer.jsp" %>
	
</body>
</html>