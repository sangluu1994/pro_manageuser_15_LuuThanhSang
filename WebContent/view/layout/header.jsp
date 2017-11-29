<%@ page import="common.Constant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Begin vung header -->	
	<div>			
		<div>
		<table>
		<tr>
		<td width = "80%">
			<img id="top" src="${pageContext.request.contextPath}/view/img/logo-manager-user.gif" alt="Luvina" />
		<td>
		<td align="left">
			<a href = "${pageContext.request.contextPath}${Constant.LOG_OUT_PATH}">ログアウト</a> &nbsp; <a href="${pageContext.request.contextPath}${Constant.LIST_USER_PATH}">トップ</a>
		<td>
		</tr>
		</table>
		</div>
	</div>
<!-- End vung header -->