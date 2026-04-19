<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="FORM" uri="http://www.springframework.org/tags/form"%>
    <jsp:include page="/WEB-INF/views/templates/header.jsp">
        <jsp:param value="게시글작성" name="title"/>
        <jsp:param value="<script type='text/javascript' src='/js/board.js'></script>" name="scripts"/>
    </jsp:include>
    <h1>게시글 작성</h1>
    <!-- form:form modelAttibute ==> form 태그 내부의 input, textarea, select 등을 
                                     컨트롤러로 보내기 위한 아이디 
                                 ==> 보편적으로 변수의 이름(엔드포인트의) 작성한다 -->
    <FORM:form
      modelAttribute="writeVO"
      method="post"
      action="/write"
      enctype="multipart/form-data"
    >
      <div class="grid write">
        <label for="subject">제목</label>
        <div class="input-div">
          <input
            type="text"
            id="subject"
            name="subject"
            placeholder="제목을 입력하세요"
            value="${inputData.subject}"
          />
          <FORM:errors
            path="subject"
            cssClass="validation-error"
            element="div"
          />
        </div>

        <label for="attach-files">첨부파일</label>
        <div id="attach-files" class="attach-files">
          <input type="file" name="attachFile" />
          <button type="button" class="add-file">+</button>
        </div>

        <label for="content">내용</label>
        <textarea id="content" name="content" placeholder="내용을 입력하세요">
        ${inputData.content}</textarea
        >
        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="저장" />
          </div>
        </div>
      </div>
    </FORM:form>
    <jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>
