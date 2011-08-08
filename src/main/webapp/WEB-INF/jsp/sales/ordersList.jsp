<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:forEach items="${orders}" var="order">
		submit date:
		<c:out value="${order.submitDate}" />
		, status:
		<c:out value="${order.status}" />
		, total cost:
		<c:out value="${order.totalCost}" />
	<a href="${pageContext.request.contextPath}/order/${order.orderId}">[details]</a>
	<br />
</c:forEach>
