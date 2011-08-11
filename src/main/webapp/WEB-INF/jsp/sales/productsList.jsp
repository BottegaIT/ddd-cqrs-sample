<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="productsListContent">
	<h1>products available</h1>
	<div class="productsContent">
		<p>Choose from our wide range of quality products</p>
		<table id="productsTable">
			<thead>
				<tr>
					<td class="imageColumn"></td>
					<td class="productNameColumn">product name</td>
					<td class="priceColumn">price</td>
					<td class="buttonsColumn"></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${products}" var="product">
					<tr>
						<td class="imageColumn"><img class="productImage"
							src="asdasd" /></td>
						<td class="productNameColumn"><c:out
								value="${product.displayedName}" /></td>
						<td class="priceColumn"><c:out value="${product.price}" /></td>
						<td class="buttonsColumn"><input name="addProductButton"
							type="button" value="add" productId="${product.productId}" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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
