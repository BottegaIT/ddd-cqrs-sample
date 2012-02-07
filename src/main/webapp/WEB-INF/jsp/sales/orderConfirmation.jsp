<%--

    Copyright 2011-2012 the original author or authors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="ordersConfirmationContent">
	<h1>order confirmation</h1>
	<div class="orderedItemsContainer">
		<h2>ordered items</h2>
		<table class="ordersTable">
			<thead>
				<tr>
					<td class="productsColumn">product</td>
					<td class="quantityColumn">quantity</td>
					<td class="costColumn">price</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${order.orderedProducts}" var="product">
					<tr>
						<td class="productsColumn"><c:out value="${product.name}" />
						</td>
						<td class="quantityColumn"><c:out value="${product.quantity}" />
						</td>
						<td class="costColumn"><c:out
								value="${product.effectiveCost}" /></td>
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
		<div>
			Order status: <span class="orderConfirmationStatus">${order.orderStatus}</span>
		</div>
		<div class="orderConfirmationControls">
			<div class="inline">are you sure that you want to confirm this
				order?</div>
			<form id="orderConfirmationForm" action="submit" method="POST"
				class="inline">
				<input type="hidden" name="orderId" value="${order.orderId}" /> <input
					type="submit" value="Confirm" />
			</form>
		</div>
	</div>
</div>