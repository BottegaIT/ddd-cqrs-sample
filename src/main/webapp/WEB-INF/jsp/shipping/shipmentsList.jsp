<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#sendShipment").click(sendShipment);
		$("#deliverShipment").click(deliverShipment);
	});

	function sendShipment() {
		$.post("send", {
			shipmentId : $(this).attr("shipmentId")
		}, function(data) {
		});
	}

	function deliverShipment() {
		$.post("deliver", {
			shipmentId : $(this).attr("shipmentId")
		}, function(data) {
		});
	}
</script>
</head>
<body>
	<jsp:include page="../includes/menu.jsp" />
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
				<td><c:out value="${shipment.shipmentId}" /></td>
				<td><c:out value="${shipment.orderId}" /></td>
				<td><c:out value="${shipment.status}" /></td>
				<td>
					<form action="send" method="POST">
						<input type="hidden" name="shipmentId" value="${shipment.shipmentId}" />
						<input type="submit" value="send"/>
					</form>
				</td>
				<td>
					<form action="deliver" method="POST">
						<input type="hidden" name="shipmentId" value="${shipment.shipmentId}" />
						<input type="submit" value="deliver"/>
					</form>
				</td>
			<tr>
		</c:forEach>
	</table>
</body>
</html>
