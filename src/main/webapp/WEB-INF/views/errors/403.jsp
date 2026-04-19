<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
    <jsp:param value="찾을 수 없는 페이지" name="title"/>
</jsp:include>

    <h1>${errorMessage}</h1>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>