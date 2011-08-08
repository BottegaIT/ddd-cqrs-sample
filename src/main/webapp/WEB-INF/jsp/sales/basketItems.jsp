<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${basketItems}" var="item">
	<li>
		<c:out value="${item.productName}" /> 
		<c:if test="${item.count>1 }">
			(x<c:out value="${item.count}" />)
		</c:if>
	</li>
</c:forEach>
