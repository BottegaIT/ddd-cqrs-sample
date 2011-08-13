<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="results" required="true" rtexprvalue="true"
	type="pl.com.bottega.erp.sales.presentation.PaginatedResult"%>
<div class="paginationContainer">
	<c:if test="${results.pagesCount > 0}">
		<div class="paginationPrompt">jump to page:</div>
		<ul class="pagination">
			<c:forEach var="pageNumber" begin="1" end="${results.pagesCount}">
				<li class="${pageNumber == results.pageNumber?"currentPage":""}">${pageNumber}</li>
			</c:forEach>
		</ul>
	</c:if>
</div>