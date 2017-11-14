<%@ page import="common.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<c:url value="/view/img/logo-icon.png" />">
<link href="<c:url value="/view/css/style.css" />" rel="stylesheet" type="text/css" />
<script src="<c:url value="/view/js/fn.js" />"></script>
<title>ユーザ管理</title>
</head>
<body>
<%@ include file = "../layout/header.jsp" %>

<!-- Begin vung input-->	
	<form action="<c:url value="${Constant.EDIT_USER_PATH}" />" method="get" name="inputform">
	<input type="hidden" name="type" value="edit" />
	<input type="hidden" name="${Constant.USER_INFOR_ID}" value="${userInfor.userId}" />
	<table  class="tbl_input" border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					情報確認					
				</div>
				<div style="padding-left:100px;">&nbsp;</div>
			</th>			
		</tr>				
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0" >					
					<tr>
						<td class="lbl_left">アカウント名:</td>
						<td align="left"><c:out value="${userInfor.loginName}" escapeXml="true"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left"><c:out value="${userInfor.groupName}" escapeXml="true"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left"><c:out value="${userInfor.fullName}" escapeXml="true"></c:out></td>
					</tr>	
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left"><c:out value="${userInfor.fullNameKana}" escapeXml="true"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">生年月日:</td>
						<td align="left"><fmt:formatDate pattern="yyyy/MM/dd" value="${userInfor.birthday}" /></td>
					</tr>				
					<tr>
						<td class="lbl_left">メールアドレス:</td>
						<td align="left"><c:out value="${userInfor.email}" escapeXml="true"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">電話番号:</td>
						<td align="left"><c:out value="${userInfor.tel}" escapeXml="true"></c:out></td>
					</tr>	
					<tr>
						<th colspan = "2"><a href = "#">日本語能力</a></th>
					</tr>
					<tr>
						<td class="lbl_left">資格:</td>
						<td align="left"><c:out value="${userInfor.nameLevel}" escapeXml="true"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">資格交付日:</td>
						<td align="left"><fmt:formatDate pattern="yyyy/MM/dd" value="${userInfor.startDate}" /></td>
					</tr>
					<tr>
						<td class="lbl_left">失効日:</td>
						<td align="left"><fmt:formatDate pattern="yyyy/MM/dd" value="${userInfor.endDate}" /></td>
					</tr>	
					<tr>
						<td class="lbl_left">点数:</td>
						<td align="left"><c:out value="${userInfor.total == 0 ? '' : userInfor.total}" escapeXml="true"></c:out></td>
					</tr>													
				</table>
				</div>				
			</td>		
		</tr>
	</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<div style="padding-left:100px;">
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
			<td>
				<input class="btn" type="submit" value="編集" />					
			</td>
			<td>
				<input class="btn" type="button" value="削除" />					
			</td>	
			<td>
				<input class="btn" type="button" onclick="window.location.href = '<c:url value="${Constant.LIST_USER_PATH}" />?type=back';" value="戻る" />						
			</td>
		</tr>		
	</table>
	<!-- End vung button -->	
</form>
<!-- End vung input -->

<%@ include file = "../layout/footer.jsp" %>
</body>
</html>