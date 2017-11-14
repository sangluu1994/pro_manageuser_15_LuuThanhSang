<%@ page import="common.Constant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<c:url value="/view/img/logo-icon.png" />">
<link href="<c:url value="/view/css/style.css" />" rel="stylesheet" type="text/css" />
<title>ユーザ管理</title>
</head>
<body align="center">
	<%@ include file="../layout/header.jsp"%>
	
	<!-- Begin vung input-->
	<form action="<c:url value="${Constant.CHANGE_PASS_PATH}" />" method="post">
		<center>
			<table class="tbl_input" border="0" style="padding-top: 50px">
				<c:forEach var="errMess" items="${errList}">
					<tr>
						<td class="errMsg" colspan="2"><c:out value="${errMess}"></c:out></td>
					</tr>
				</c:forEach>
				<tr align="left">
					<td class="lbl_left">New Password:</td>
					<td align="left"><input class="txBox" type="password"
						name="${Constant.PASS_ADM003}"
						value="<c:out value="${loginName}" escapeXml="true" />" size="22"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td class="lbl_left">Confirm Password:</td>
					<td align="left"><input class="txBox" type="password"
						name="${Constant.CONFIRM_PASS_ADM003}" value="" size="22"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><input class="btn btn_wider" type="submit"
						value="Change Pass" /> <input class="btn" type="button"
						value="戻る" onclick="window.location.href = '<c:url value="${Constant.DETAIL_USER_PATH}" />?userId=${userId}'" /></td>
				</tr>
			</table>
		</center>
		<input type="hidden" value="${userId}" name="Constant.USER_ID" />
	</form>
	<!-- End vung input -->
	
	<%@ include file = "../layout/footer.jsp" %>
	
</body>
</html>