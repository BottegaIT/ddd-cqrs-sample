<%@ page
	import="pl.com.bottega.erp.sales.presentation.ProductSearchCriteria.ProductSearchOrder"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<div class="productsListContent">
	<h1>products available</h1>
	<div class="productsContent">
		<p>Choose from our wide range of quality products</p>
		<div>
			<h3>show only products</h3>
			<form>
				containing text: <input name="containsText" type="text" /> </br>
				up to price: <input name="maxPrice" type="text" /> EUR</br>
				<input type="submit" value="filter" />
			</form>
		</div>

		<p>${products.totalItemsCount} products found</p>
		<table id="productsTable" class="dataTable" sortedby="${sortBy}"
			ascending="${ascending}">
			<thead>
				<tr>
					<td class="imageColumn"></td>
					<td class="productNameColumn sortable"
						sortby="<%=ProductSearchOrder.NAME%>">product name</td>
					<td class="priceColumn sortable"
						sortby="<%=ProductSearchOrder.PRICE%>">price</td>
					<td class="buttonsColumn"></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${products.items}" var="product">
					<tr>
						<td class="imageColumn"><img class="productImage"
							src="<c:url value="/static/img/"/>" />
						</td>
						<td class="productNameColumn"><c:out
								value="${product.displayedName}" />
						</td>
						<td class="priceColumn"><c:out value="${product.price}" />
						</td>
						<td class="buttonsColumn"><input name="addProductButton"
							type="button" value="add" productId="${product.productId}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<custom:pagination results="${products}" />
	</div>
</div>

<div class="basketContainer">
	<div class="highlight">
		<h1>your basket</h1>
	</div>

	<div class="basketContent" style="display: none;">
		<ul id="basketItemsList">
		</ul>

		<div class="basketControls">
			<div>
				<form action="checkout" method="POST">
					<input type="submit" value="checkout" />
				</form>
			</div>
			<div>
				<button onclick="clearBasket()">clear basket</button>
			</div>
		</div>
	</div>
	<div class="basketNoContent" style="display: none;">
		<p class="notification">your basket is empty</p>
	</div>
</div>
