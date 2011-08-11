<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="ordersListContent">
	<h1>list of all orders</h1>
	<div class="ordersList">
		<h2>orders created in the system</h2>
		<table class="ordersTable">
			<thead>
				<tr>
					<td class="submitDateColumn">submitted</td>
					<td class="statusColumn">status</td>
					<td class="totalCostColumn">price</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders}" var="order">
					<tr>
						<td class="submitDateColumn"><c:out
								value="${order.submitDate}" />
						</td>
						<td class="statusColumn"><c:out value="${order.status}" />
						</td>
						<td class="totalCostColumn"><c:out value="${order.totalCost}" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
