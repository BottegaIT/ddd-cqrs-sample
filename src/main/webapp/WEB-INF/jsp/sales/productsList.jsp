<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(":input[name=addProductButton]").click(function() {
			$.post("addProduct", {
				productId : $(this).attr("productId")
			}, function(data) {
				handleBasketItems(data);
			});
		});
		$.get("basketItems", function(data) {
			handleBasketItems(data);
		});
	});

	function clearBasket() {
		$.post("clearBasket", function() {
			$("#basketItemsList").empty();
		});
	}

	function handleBasketItems(basketItemsHtml) {
		$("#basketItemsList").html(basketItemsHtml);
	}
</script>
</head>
<body>
	<jsp:include page="../includes/menu.jsp" />

	<div style="width: 1000px; margin: 0 auto; border: 2 solid #E1E1E1; padding: 20px 20px 150px;">
		<div id="productsContainer" style="float: left">
			<h1>products available</h1>
			<c:forEach items="${products}" var="product">
				<c:out value="${product.displayedName}" />
				<input name="addProductButton" type="button" value="add"
					productId="${product.productId}" />
				<br />
			</c:forEach>
		</div>

		<div id="basketContainer" style="float: right; width: 300px;">
			<h1 style="background-color: #0000FF;">your basket</h1>
			<ul id="basketItemsList">
			</ul>

			<div>
				<form action="checkout" method="POST">
					<input type="submit" value="proceed to checkout" />
				</form>
				<button onclick="clearBasket()">clear basket</button>
			</div>
		</div>
		<div style="clear: both;" />


	</div>
</body>
</html>
