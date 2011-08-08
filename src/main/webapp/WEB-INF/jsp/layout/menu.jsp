<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="border: 1px solid black; margin: auto;">
	jump to module:
	<a href="${pageContext.request.contextPath}/sales/products/list">products</a>
	<a href="${pageContext.request.contextPath}/sales/orders/list">orders</a>
	<a href="${pageContext.request.contextPath}/shipping/shipment/list">shipping</a>
</div>
