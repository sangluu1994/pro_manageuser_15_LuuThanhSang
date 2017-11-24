<%@ page import="common.Constant"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
	<form action="${pageContext.request.contextPath}${userInfor.userId != 0 ? Constant.EDIT_VALIDATE_PATH : Constant.ADD_USER_VALIDATE_PATH}" method="post" name="inputform">	
	<input type="hidden" name="type" value="confirm"/>
	<c:if test="${userInfor.userId != 0}"><input type="hidden" name="${Constant.USER_INFOR_ID}" value="${userInfor.userId}"/></c:if>
	<table  class="tbl_input"   border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					会員情報編集
				</div>
			</th>			
		</tr>	
		<c:forEach items="${errList}" var="errMess">
			<tr>
				<td class="errMsg" colspan="2">
					<div style="padding-left:120px">
						<c:out value="${errMess}"></c:out>
					</div>
				</td>
			</tr>	
		</c:forEach>	
		
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="0" width="100%" class="tbl_input" cellpadding="4" cellspacing="0">					
					<tr>
						<td class="lbl_left"><font color = "red">*</font> アカウント名:</td>
						<td align="left">
							<input <c:if test='${userInfor.userId != 0}'>readonly</c:if> class="txBox" type="text" name="${Constant.LOGIN_NAME_ADM003}" value="<c:out value="${userInfor.loginName}" escapeXml="true"></c:out>"
							size="15" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> グループ:</td>
						<td align="left">						
							<select name="${Constant.GROUP_ID_ADM003}">
								<c:forEach var="group" items="${allMstGroup}">
									<option value="${group.groupId}" ${group.groupId == userInfor.groupId ? "selected" : ""}>${group.groupName}</option>
								</c:forEach>
							</select>							
							<span>&nbsp;&nbsp;&nbsp;</span>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="${Constant.FULL_NAME_ADM003}" value="<c:out value="${userInfor.fullName}" escapeXml="true"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="${Constant.KANA_NAME_ADM003}" value="<c:out value="${userInfor.fullNameKana}" escapeXml="true"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>	
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 生年月日:</td>
						<td align="left">
						<select name="${Constant.BIRTH_YEAR_ADM003}">
							<c:forEach var="listYear" items="${listYears}">
								<c:if test="${(currentYear + 1) != listYear}">
									<option value="${listYear}" ${listYear == userInfor.birthYear ? "selected" : ""}>${listYear}</option>
								</c:if>
							</c:forEach>
						</select>年
						<select name="${Constant.BIRTH_MONTH_ADM003}">
							<c:forEach var="listMonth" items="${listMonths}">
								<option value="${listMonth}" ${listMonth == userInfor.birthMonth ? "selected" : ""}>${listMonth}</option>
							</c:forEach>
						</select>月
						<select name="${Constant.BIRTH_DATE_ADM003}">
							<c:forEach var="listDay" items="${listDays}">
								<option value="${listDay}" ${listDay == userInfor.birthDate ? "selected" : ""}>${listDay}</option>
							</c:forEach>
						</select>日							
						</td>
					</tr>				
					<tr>
						<td class="lbl_left"><font color = "red">*</font> メールアドレス:</td>
						<td align="left">
							<input class="txBox" type="text" name="${Constant.EMAIL_ADM003}" value="<c:out value="${userInfor.email}" escapeXml="true"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font>電話番号:</td>
						<td align="left">
						<input class="txBox" type="text" name="${Constant.TEL_ADM003}" value="<c:out value="${userInfor.tel}" escapeXml="true"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />						
						</td>
					</tr>
					<c:if test="${userInfor.userId == 0}">
						<tr>
							<td class="lbl_left"><font color = "red">*</font> パスワード:</td>
							<td align="left">
								<input class="txBox" type="password" name="${Constant.PASS_ADM003}" value="<c:out value="${userInfor.pass}" escapeXml="true"></c:out>"
								size="30" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" />							
							</td>
						</tr>
						<tr>
							<td class="lbl_left">パスワード（確認）:</td>
							<td align="left">
								<input class="txBox" type="password" name="${Constant.CONFIRM_PASS_ADM003}" value="<c:out value="${userInfor.rePass}" escapeXml="true"></c:out>"
								size="30" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" />							
							</td>
						</tr>
					</c:if>
					<tr>
						<th align="left" colspan = "2" >							
							<a href="javascript:void(0)" onclick="toggleJpField()">日本語能力</a>
						</th>			
					</tr>
					<tr class="japaneseField" style="display:none;">
						<td class="lbl_left">資格:</td>
						<td align="left">
							<select name="${Constant.CODE_LEVEL_ADM003}">
								<c:forEach var="kyu" items="${allMstJapan}">
									<option value="${kyu.codeLevel}" ${kyu.codeLevel == userInfor.codeLevel ? "selected" : ""}>${kyu.nameLevel}</option>
								</c:forEach>
							</select>									
						</td>
					</tr>
					<tr class="japaneseField" style="display:none;">
						<td class="lbl_left">資格交付日: </td>
						<td align="left">
							<select name="${Constant.START_YEAR_ADM003}">
								<c:forEach var="listYear" items="${listYears}">
									<c:if test="${(currentYear + 1) != listYear}">
										<option value="${listYear}" ${listYear == userInfor.startYear ? "selected" : ""}>${listYear}</option>
									</c:if>
								</c:forEach>
							</select>年
							<select name="${Constant.START_MONTH_ADM003}">
								<c:forEach var="listMonth" items="${listMonths}">
									<option value="${listMonth}" ${listMonth == userInfor.startMonth ? "selected" : ""}>${listMonth}</option>
								</c:forEach>
							</select>月
							<select name="${Constant.START_DAY_ADM003}">
								<c:forEach var="listDay" items="${listDays}">
									<option value="${listDay}" ${listDay == userInfor.startDay ? "selected" : ""}>${listDay}</option>
								</c:forEach>
							</select>日							
						</td>
					</tr>
					<tr class="japaneseField" style="display:none;">
						<td class="lbl_left">失効日: </td>
						<td align="left">
							<select name="${Constant.END_YEAR_ADM003}">
								<c:forEach var="listYear" items="${listYears}">
									<option value="${listYear}" ${listYear == userInfor.endYear ? "selected" : ""}>${listYear}</option>
								</c:forEach>
							</select>年
							<select name="${Constant.END_MONTH_ADM003}">
								<c:forEach var="listMonth" items="${listMonths}">
									<option value="${listMonth}" ${listMonth == userInfor.endMonth ? "selected" : ""}>${listMonth}</option>
								</c:forEach>
							</select>月
							<select name="${Constant.END_DAY_ADM003}">
								<c:forEach var="listDay" items="${listDays}">
									<option value="${listDay}" ${listDay == userInfor.endDay ? "selected" : ""}>${listDay}</option>
								</c:forEach>
							</select>日							
						</td>
					</tr>
					<tr class="japaneseField" style="display:none;">
						<td class="lbl_left">点数: </td>
						<td align="left">
							<input class="txBox" type="text" name="${Constant.TOTAL_ADM003}" value="<c:out value="${userInfor.total}" escapeXml="true"></c:out>"
							size="5" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>									
				</table>
				</div>				
			</td>		
		</tr>
	</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<div style="padding-left:45px;">
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
				<td>
					<input class="btn" type="submit" value="確認" />					
				</td>	
				<td>
					<input class="btn" type="button" onclick="back(${userInfor.userId})" value="戻る" />						
				</td>
		</tr>		
	</table>
	<!-- End vung button -->	
</form>
<!-- End vung input -->
<script>
	function back(userId) {
		if (userId != 0) {
			window.location.href = 'detailUser.do?userId=' + userId;
		} else {
			window.location.href = 'listAllUser.do?type=back';
		}
	}
</script>

<%@ include file = "../layout/footer.jsp" %>
</body>
</html>