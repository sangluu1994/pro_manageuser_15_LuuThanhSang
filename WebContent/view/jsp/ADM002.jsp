<%@page import="entity.MstGroup"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon" href="view/img/logo-icon.png">
		<link href="view/css/style.css" rel="stylesheet" type="text/css" />
		<title>ユーザ管理</title>
</head>
<body>
	<%@ include file="../layout/header.jsp"%>

	<!-- Begin vung dieu kien tim kiem -->
	<form action="" method="post" name="mainform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left">
								<input class="txBox" type="text" name="full_name"
									value="<c:out value="" escapeXml="true" />"
									size="20" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px">
								<select name="group_id">
									<option value="0" selected="selected">全て</option>
									<c:forEach var="group" items="${listGroup}">
										<c:choose>
											<c:when test="${group.groupId}">
												<option value="${group.groupId}">${group.groupName}</option>
											</c:when>
											<c:otherwise>
												<option value="${group.groupId}">${group.groupName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select></td>
							<td align="left">
								<input class="btn" type="submit" value="検索" />
								<input class="btn" type="button" value="新規追加" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
		width="80%">

		<tr class="tr2">
			<th align="center" width="20px">ID</th>
			<th align="left">氏名 <a href="">▲▽</a></th>
			<th align="left">生年月日</th>
			<th align="left">グループ</th>
			<th align="left">メールアドレス</th>
			<th align="left" width="70px">電話番号</th>
			<th align="left">日本語能力 <a href="">▲▽</a></th>
			<th align="left">失効日 <a href="">△▼</a></th>
			<th align="left">点数</th>
		</tr>

		<c:choose>
			<c:when test="${not empty listUser}">
				<c:forEach var="item" items="${listUser}">
					<tr>
						<td align="right"><a href="ADM005.html">${item.userId}</a></td>
						<td>${item.fullName}</td>
						<td align="center"><fmt:formatDate pattern="yyyy/MM/dd" value="${item.birthday}" /></td>
						<td>${item.groupName}</td>
						<td>${item.email}</td>
						<td>${item.tel}</td>
						<td>${item.nameLevel }</td>
						<td align="center"><fmt:formatDate pattern="yyyy/MM/dd" value="${item.endDate}" /></td>
						<td align="right">${item.total}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tbody>
					<tr align="center">
						<td colspan="9">${MessageProperties.messageProperties.get(ConstantProperties.MSG005)}</td>
					</tr>
				</tbody>
			</c:otherwise>
		</c:choose>

	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<td class="lbl_paging"><a href="#">1</a> &nbsp;<a href="#">2</a>
				&nbsp;<a href="#">3</a>&nbsp;<a href="#">>></a></td>
		</tr>
	</table>
	<!-- End vung paging -->

	<%@ include file = "../layout/footer.jsp" %>

</body>
</html>