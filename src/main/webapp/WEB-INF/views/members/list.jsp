<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
    <jsp:include page="/WEB-INF/views/templates/header.jsp">
        <jsp:param value="게시글작성" name="title"/>
        <jsp:param value="<script type='text/javascript' 
        src='/js/board.js'></script>" name="scripts"/>
    </jsp:include>
	<table class="grid">
		<h1>회원정보 목록</h1>
		<div>총 ${searchCount}개의 회원.</div>
		<thead>
			<tr>
				<th>이메일</th>
				<th>이름</th>
				<th>비밀번호</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty searchResult}">
					<c:forEach items="${searchResult}" var="members">
						<tr>
							<td>${members.email}</td>
							<td>
							<a href="/member/view/${members.email}">${members.name}</a>
							</td>
							<td>${members.password}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colsapn="3">검색된 데이터가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<div class="btn-group">
		<div class="right-align">
			<a href="/regist">회원가입</a>
		</div>
	</div>
    <jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>