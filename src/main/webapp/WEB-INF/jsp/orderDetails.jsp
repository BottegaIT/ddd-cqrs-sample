<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
</head>
<body>
	<br /> submit date:
	<c:out value="${order.submitDate}" />
	<br /> status:
	<c:out value="${order.status}" />
	<br /> ordered items:
	<c:forEach items="${order.orderedProducts}" var="product">
		<c:out value="${product.name}" />
		(x 
		<c:out value="${product.quantity}" />
		), cost:
		<c:out value="${product.effectiveCost}" />
		<br />
	</c:forEach>
	total cost:
	<c:out value="${order.totalCost}" />
	<form
		action="${pageContext.request.contextPath}/order/${order.orderId}/submit"
		method="POST">
		<input type="submit" value="submit" />
	</form>


	<form action="${pageContext.request.contextPath}/shipment/create"
		method="POST">
		<input type="hidden" name="orderId" value="${order.orderId}" /> <input
			type="submit" value="ship this order" />
	</form>
	<form action="${pageContext.request.contextPath}/shipment/receive"
		method="POST">
		shipment id: <input type="text" name="shipmentId"> <input
			type="submit" value="receive" />
	</form>


</body>
</html>
