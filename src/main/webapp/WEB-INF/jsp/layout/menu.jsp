<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="menuContainer">
	<div>jump to module:</div>
	<div class="menuItem">
		<a href="${pageContext.request.contextPath}/sales/products/list">products</a>
	</div>
	<div class="menuItem">
		<a href="${pageContext.request.contextPath}/sales/orders/list">orders</a>
	</div>
	<div class="menuItem">
		<a href="${pageContext.request.contextPath}/shipping/shipment/list">shipping</a>
	</div>
</div>