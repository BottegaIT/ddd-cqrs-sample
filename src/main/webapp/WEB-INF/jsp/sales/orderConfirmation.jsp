<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>order confirmation</h1>
<div>
	<h2>ordered items</h2>
	<table class="ordersTable">
		<thead>
			<tr>
				<td class="productsColumn">product</td>
				<td class="quantityColumn">quantity</td>
				<td class="costColumn">cost</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${order.orderedProducts}" var="product">
				<tr>
					<td class="productsColumn"><c:out value="${product.name}" /></td>
					<td class="quantityColumn"><c:out value="${product.quantity}" /></td>
					<td class="costColumn"><c:out value="${product.effectiveCost}" /></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2" style="text-align: right; padding-right: 10px;">total
					cost:</td>
				<td><c:out value="${order.totalCost}" />
				</td>
			</tr>
		</tfoot>
	</table>
	<div class="orderConfirmationControls">
		<div class="inline">are you sure that you want to confirm this
			order?</div>
		<form action="submit" method="POST" class="inline">
			<input type="hidden" name="orderId" value="${order.orderId}" /> <input
				type="submit" value="Confirm" />
		</form>
	</div>
</div>
