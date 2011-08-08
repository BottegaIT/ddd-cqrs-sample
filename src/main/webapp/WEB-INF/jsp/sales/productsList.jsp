<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="productsContainer">
	<h1>products available</h1>
	<table>
		<thead>
			<tr>
				<td></td>
				<td>product name</td>
				<td>price</td>
				<td></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="product">
				<tr>
					<td><img src="asdasd" /></td>
					<td><c:out value="${product.displayedName}" /></td>
					<td><c:out value="${product.price}" /></td>
					<td><input name="addProductButton" type="button" value="add"
						productId="${product.productId}" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="basketContainer">
	<h1>your basket</h1>
	<div class="basketContent" style="display: none;">
		<ul id="basketItemsList">
		</ul>

		<div class="basketControls">
			<div>
				<form action="checkout" method="POST">
					<input type="submit" value="proceed to checkout" />
				</form>
			</div>
			<div>
				<button onclick="clearBasket()">clear basket</button>
			</div>
		</div>
	</div>
	<div class="basketNoContent" style="display: none;">
		<h2>your basket is empty</h2>
	</div>
</div>
