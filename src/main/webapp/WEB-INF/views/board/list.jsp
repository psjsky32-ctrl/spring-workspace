<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!-- /templates/header.jsp import -->
<jsp:include page="/WEB-INF/views/templates/header.jsp">
	<jsp:param value="게시글 목록" name="title" />
    <jsp:param value="<script type='text/javascript' 
        src='/js/board.js'></script>" name="scripts"/>
</jsp:include>
<div class="grid list">
	<h1>게시글 목록</h1>
	<div>총 ${searchCount}개의 게시글이 검색되었습니다.</div>
	<!-- HTML 주석: 브라우저 개발자 도구에서 노출되는 주석. -->
	<%-- JSP 주석: 브라우저 개발자 도구에서 노출되지 않는 주석. --%>
	<%--
      		<% for (BoardVO board: searchResult) { %>
          <div><%=board.getId()%></div>
      		<% } %>
      		--%>
	<table class="grid">
		<colgroup>
			<col width="200" />
			<col width="*" />
			<col width="150" />
			<col width="80" />
			<col width="180" />
			<col width="180" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>이름</th>
				<th>조회수</th>
				<th>등록일</th>
				<th>수정일</th>
			</tr>
		</thead>
		<tbody>
			<!-- searchResult가 존재하지 않으면, "검색된 데이터가 없습니다"를 보여주고 -->
			<!-- searchResult가 존재하면, 반복하여 데이터를 보여주고 -->
			<c:choose>
				<c:when test="${not empty searchResult}">
					<c:forEach items="${searchResult}" var="board">
						<tr>
							<td>${board.id}</td>
							<td><a href="/view/${board.id}">${board.subject}</a></td>
							<td>${board.membersVO.name}</td>
							<td>${board.viewCnt}</td>
							<td>${board.crtDt}</td>
							<td>${board.mdfyDt}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="6">검색된 데이터가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<div class="btn-group">
		<div class="right-align">
			<c:if test="${not empty sessionScope.__LOGIN_DATA__}">
				<a href="/write">새로운 게시글 작성</a>
			</c:if>
		</div>
	</div>
	
	<dlv class="search-box">
	   <select id="list-size">
	       <option value="10" ${pagenation.listSize eq "10" ? "selected": ""}>10개씩</option>
	       <option value="20" ${pagenation.listSize eq "20" ? "selected": ""}>20개씩</option>
	       <option value="50" ${pagenation.listSize eq "50" ? "selected": ""}>50개씩</option>
	       <option value="100" ${pagenation.listSize eq "100" ? "selected": ""}>100개씩</option>
	   </select>
	   <div>
	       <select id="search-type">
	           <option value="email" ${pagenation.searchType eq "email" ? "selected" : "" }>Email로 검색</option>
	           <option value="name" ${pagenation.searchType eq "name" ? "selected" : "" }>작성자 이름으로 검색</option>
	           <option value="subject" ${pagenation.searchType eq "subject" ? "selected" : "" }>제목으로 검색</option>
	           <option value="content" ${pagenation.searchType eq "content" ? "selected" : "" }>내용으로 검색</option>
	           <option value="subject content" ${pagenation.searchType eq "subject content" ? "selected" : "" }>제목 + 내용으로 검색</option>
	       </select>
	       <input type="text" id="search-keyword" placeholder="검색어를 입력하세요." value="${pagenation.searchKeyword }"/>
	       <button type="button" class="search-button">검색!</button>
	   </div>
	</dlv>
	
	<c:if test="${pagenation.pageCount > 0}">
	<ul class="page-navigator">
	   <c:if test="${pagenation.hasPrevPageGroup}">
	       <li>
	           <a data-page-no = "0" href="javascript:void(-1)">처음</a>
	       </li>
	       <li>
	           <a data-page-no = "${pagenation.prevPageGroupStartPageNo}" href="javascript:void(-1)">이전</a>
	       </li>
	   </c:if>
	   <c:forEach begin="${pagenation.groupStartPageNo}" 
	              end="${pagenation.groupEndPageNo}" 
	              step="1" 
	              var="page">
	       <li class="${page eq pagenation.pageNo ? 'active': '' }">
	           <a data-page-no ="${page}"href = "javascript:void(-1)">${page + 1}</a>
	       </li>
	   </c:forEach>
	   <c:if test= "${pagenation.hasNesxtPageGroup}">
	       <li>
	           <a data-page-no = "${pagenation.nextpagegroupStartPageNo}" href="javascript:void(-1)">다음</a>
	       </li>
	       <li>
	           <a data-page-no = "${pagenation.pageCount - 1}" href="javascript:void(-1)">마지막</a>
	       </li>
	   </c:if>
	</ul>
	</c:if>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>