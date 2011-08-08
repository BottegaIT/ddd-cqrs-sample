<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<table>
	<thead>
		<tr>
			<td>shipmentId</td>
			<td>orderId</td>
			<td>status</td>
			<td></td>
			<td></td>
		</tr>
	</thead>
	<c:forEach items="${shipments}" var="shipment">
		<tr>
			<td><c:out value="${shipment.shipmentId}" />
			</td>
			<td><c:out value="${shipment.orderId}" />
			</td>
			<td><c:out value="${shipment.status}" />
			</td>
			<td>
				<form action="send" method="POST">
					<input type="hidden" name="shipmentId"
						value="${shipment.shipmentId}" /> <input type="submit"
						value="send" />
				</form></td>
			<td>
				<form action="deliver" method="POST">
					<input type="hidden" name="shipmentId"
						value="${shipment.shipmentId}" /> <input type="submit"
						value="deliver" />
				</form></td>
		<tr>
	</c:forEach>
</table>
