<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/view/img/logo-icon.png">
<link href="${pageContext.request.contextPath}/view/css/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/view/js/fn.js"></script>
<title>ユーザ管理</title>
</head>
<body>
<%@ include file = "../layout/header.jsp" %>

<!-- Begin vung input-->	
	<form action="listAllUser.do" method="post" name="inputform">
	<table  class="tbl_input"   border="0" width="80%"  cellpadding="0" cellspacing="0" >	
		<tr>
			<td align="center" colspan="2">
				<div style="height:50px"></div>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2" class="<c:out value="${status == 'success' ? '' : 'fail'}" />">
				<c:out value="${msg}" escapeXml="true" />
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