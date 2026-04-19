<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
    <jsp:param value="잘못된 접근" name="title"/>
</jsp:include>

    <h1>${errorMessage}</h1>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>