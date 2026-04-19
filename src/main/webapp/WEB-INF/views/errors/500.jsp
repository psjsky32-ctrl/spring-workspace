<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
    <jsp:param value="잘못된 접근" name="title"/>
</jsp:include>

    <h1>시스템 에러가 발생했습니다.</h1>
    <h3>잠시후 다시 시도해주세요.</h3>
    <div>시스템 관리자는 에러 내용을 확인하세요.</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>