<%@ page import="common.Constant"%>
<%@ page import="entity.MstGroup"%>
<%@ page import="entity.MstJapan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="<c:url value="/view/img/logo-icon.png" />">
<link href="<c:url value="/view/css/style.css" />" rel="stylesheet" type="text/css" />
<script src="<c:url value="/view/js/fn.js" />"></script>
<title>ユーザ管理</title>
</head>
<body>
<%@ include file = "../layout/header.jsp" %>

<!-- Begin vung input-->	
	<form action="<c:url value="${Constant.ADD_USER_INPUT_PATH}" />" method="post" name="inputform">	
	<table  class="tbl_input"   border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					会員情報編集
				</div>
			</th>			
		</tr>		
		<tr>
			<td class="errMsg">
				<div style="padding-left:120px">
					&nbsp;
				</div>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="0" width="100%" class="tbl_input" cellpadding="4" cellspacing="0" >					
					<tr>
						<td class="lbl_left"><font color = "red">*</font> アカウント名:</td>
						<td align="left">
							<input class="txBox" type="text" name="id" value=""
							size="15" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> グループ:</td>
						<td align="left">						
							<select name="groupId">
								<c:forEach var="group" items="${allMstGroup}">
									<c:choose>
										<c:when test="${group.groupId == userInfor.groupId}">
											<option value="${group.groupId}" selected="selected">${group.groupName}</option>
										</c:when>
										<c:otherwise>
											<option value="${group.groupId}">${group.groupName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>							
							<span>&nbsp;&nbsp;&nbsp;</span>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="fullName" value=""
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="kanaName" value=""
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>	
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 生年月日:</td>
						<td align="left">
						<select name="birthYear">
							<c:forEach var="listYear" items="${listYears}">
								<c:choose>
									<c:when test="${listYear == currentYear}">
										<option value="${listYear}" selected="selected">${listYear}</option>
									</c:when>
									<c:otherwise>
										<option value="${listYear}">${listYear}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>年
						<select name="birthMonth">
							<c:forEach var="listMonth" items="${listMonths}">
								<c:choose>
									<c:when test="${listMonth == currentMonth}">
										<option value="${listMonth}" selected="selected">${listMonth}</option>
									</c:when>
									<c:otherwise>
										<option value="${listMonth}">${listMonth}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>月
						<select name="birthDate">
							<c:forEach var="listDay" items="${listDays}">
								<c:choose>
									<c:when test="${listDay == currentDay}">
										<option value="${listDay}" selected="selected">${listDay}</option>
									</c:when>
									<c:otherwise>
										<option value="${listDay}">${listDay}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>日							
						</td>
					</tr>				
					<tr>
						<td class="lbl_left"><font color = "red">*</font> メールアドレス:</td>
						<td align="left">
							<input class="txBox" type="text" name="email" value=""
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font>電話番号:</td>
						<td align="left">
						<input class="txBox" type="text" name="tel" value=""
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />						
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> パスワード:</td>
						<td align="left">
							<input class="txBox" type="password" name="password" value=""
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">パスワード（確認）:</td>
						<td align="left">
							<input class="txBox" type="password" name="confirmPass" value=""
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<th align="left" colspan = "2" >							
								<a href="javascript:void(0)" onclick="toggleJpField()">日本語能力</a>
						</th>			
					</tr>
					<tr class="japaneseField" style="display: none;">
						<td class="lbl_left">資格:</td>
						<td align="left">
							<select name="kyu_id">
								<c:forEach var="kyu" items="${allMstJapan}">
									<c:choose>
										<c:when test="${kyu.codeLevel == userInfor.codeLevel}">
											<option value="${kyu.codeLevel}" selected="selected">${kyu.nameLevel}</option>
										</c:when>
										<c:otherwise>
											<option value="${kyu.codeLevel}">${kyu.nameLevel}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>									
						</td>
					</tr>
					<tr class="japaneseField" style="display: none;">
						<td class="lbl_left">資格交付日: </td>
						<td align="left">
							<select name="startYear">
								<c:forEach var="listYear" items="${listYears}">
									<option value="${listYear}">${listYear}</option>
								</c:forEach>
							</select>年
							<select name="startMonth">
								<c:forEach var="listMonth" items="${listMonths}">
									<option value="${listMonth}">${listMonth}</option>
								</c:forEach>
							</select>月
							<select name="startDate">
								<c:forEach var="listDay" items="${listDays}">
									<option value="${listDay}">${listDay}</option>
								</c:forEach>
							</select>日							
						</td>
					</tr>
					<tr class="japaneseField" style="display: none;">
						<td class="lbl_left">失効日: </td>
						<td align="left">
							<select name="endYear">
								<c:forEach var="listYear" items="${listYears}">
									<option value="${listYear}">${listYear}</option>
								</c:forEach>
							</select>年
							<select name="endMonth">
								<c:forEach var="listMonth" items="${listMonths}">
									<option value="${listMonth}">${listMonth}</option>
								</c:forEach>
							</select>月
							<select name="endDate">
								<c:forEach var="listDay" items="${listDays}">
									<option value="${listDay}">${listDay}</option>
								</c:forEach>
							</select>日							
						</td>
					</tr>
					<tr class="japaneseField" style="display: none;">
						<td class="lbl_left">点数: </td>
						<td align="left">
							<input class="txBox" type="text" name="total" value=""
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
					<input class="btn" type="button" value="戻る" />						
				</td>
		</tr>		
	</table>
	<!-- End vung button -->	
</form>
<!-- End vung input -->


<%@ include file = "../layout/footer.jsp" %>
</body>
</html>