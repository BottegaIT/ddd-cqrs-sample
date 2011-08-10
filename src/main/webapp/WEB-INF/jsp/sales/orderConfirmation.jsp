<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>order confirmation</h1>
<div>
	<h2>ordered items</h2>
	<table border="0" cellpadding="1" cellspacing="1">
		<thead>
			<tr>
				<td>product</td>
				<td>quantity</td>
				<td>cost</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${order.orderedProducts}" var="product">
				<tr>
					<td><c:out value="${product.name}" />
					</td>
					<td><c:out value="${product.quantity}" />
					</td>
					<td><c:out value="${product.effectiveCost}" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2" style="text-align: right; padding-right: 10px;">total cost:</td>
				<td><c:out value="${order.totalCost}" /></td>
			</tr>
		</tfoot>
	</table>

	are you sure that you want to confirm this order?
	<form action="submit" method="POST">
		<input type="hidden" name="orderId" value="${order.orderId}" /> <input
			type="submit" value="Confirm" />
	</form>
</div>
