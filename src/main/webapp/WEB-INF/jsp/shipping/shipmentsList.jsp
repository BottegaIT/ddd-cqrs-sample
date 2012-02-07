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
<div class="shipmentsListContent">
	<h1>shipments</h1>
	<div class="shipmentsContainer">
		<h2>list of all created shipments</h2>
		<table class="shipmentsTable">
			<thead>
				<tr>
					<td class="shipmentIdColumn">shipmentId</td>
					<td class="orderIdColumn">orderId</td>
					<td class="statusColumn">status</td>
					<td class="buttonsColumn"></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${shipments}" var="shipment">
					<tr>
						<td class="shipmentIdColumn"><c:out value="${shipment.shipmentId}" /></td>
						<td class="orderIdColumn"><c:out value="${shipment.orderId}" /></td>
						<td class="statusColumn"><c:out value="${shipment.status}" /></td>
						<td class="buttonsColumn">
							<form action="send" method="POST" class="inline">
								<input type="hidden" name="shipmentId"
									value="${shipment.shipmentId}" /> <input type="submit"
									value="send" />
							</form>
							<form action="deliver" method="POST" class="inline">
								<input type="hidden" name="shipmentId"
									value="${shipment.shipmentId}" /> <input type="submit"
									value="deliver" />
							</form>
						</td>
					<tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
