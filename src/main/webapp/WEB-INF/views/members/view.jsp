<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
    <jsp:include page="/WEB-INF/views/templates/header.jsp">
        <jsp:param value="회원 정보 조회: ${member.email}" name="title"/>
    </jsp:include>

    <h1>회원 정보 조회</h1>
    <div class="grid member-view">
      <span>이메일</span>
      <div>${member.email}</div>

      <span>이름</span>
      <div>${member.name}</div>

      <span>비밀번호</span>
      <div>${member.password}</div>

      <div class="btn-group">
        <div class="right-align">
          <a href="/member/update/${member.email}">수정</a>
          <a href="/member/delete?id=${member.email}">삭제</a>
          <a href="/delete-me">탈퇴하기</a>
        </div>
      </div>
    </div>
    <jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>