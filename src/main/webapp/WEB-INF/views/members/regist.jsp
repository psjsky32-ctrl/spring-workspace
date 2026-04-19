<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="FORM" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <jsp:include page="/WEB-INF/views/templates/header.jsp">
        <jsp:param value="게시글작성" name="title"/>
        <jsp:param value="<script type='text/javascript' 
        src='/js/members.js'></script>" name="scripts"/>
    </jsp:include>
    <h1>회원가입</h1>
    <FORM:form modelAttribute="registVO" method="post" action="/regist">

      <div class="grid write">
        <label for="email">이메일</label>
        <div class="input-div">
          <input
            type="text"
            id="email"
            name="email"
            placeholder="이메일을 입력하세요"
            value="${inputData.email}${errorData.email}"
          />
        <c:if test="${not empty errorMessage}">
            <div class="validation-error">${errorMessage}</div>
        </c:if>
          <FORM:errors path="email" cssClass="validation-error" element="div" />
        </div>
        <label for="name">이름</label>
        <div class="input-div">
          <input
            type="text"
            id="name"
            name="name"
            placeholder="이름을 입력하세요"
            value="${inputData.name}"
          />
          <FORM:errors path="name" cssClass="validation-error" element="div" />
        </div>
        <label for="password">비밀번호</label>
        <div class="input-div">
          <input
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호 입력하세요"
          />
          <FORM:errors
            path="password"
            cssClass="validation-error"
            element="div"
          />
        </div>
        <!-- 비밀번호 한 번 입력하기 ==> 비밀번호를 확인하는 기능 -->
        <label for="confirm-password">비밀번호 확인</label>
        <div class="input-div">
          <input
            type="password"
            id="confirm-password"
            name="confirm-password"
          />
        </div>
        <label for="show-password">비밀번호 확인하기</label>
        <input type="checkbox" id="show-password" />

        <!-- name이 없다는것은 서버로 전송하지 않을거다 -->

        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="저장" />
          </div>
        </div>
      </div>
    </FORM:form>
    <jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>