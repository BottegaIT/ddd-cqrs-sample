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
