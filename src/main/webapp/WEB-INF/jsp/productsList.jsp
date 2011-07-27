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
			}, $.noop());
		});
	});
</script>
</head>
<body>
	<c:forEach items="${products}" var="product">
		<c:out value="${product.displayedName}" />
		<input name="addProductButton" type="button" value="add"
			productId="${product.productId}" />
		<br />
	</c:forEach>
</body>
<br />
<form action="${pageContext.request.contextPath}/checkout" method="POST">
	<input type="submit" value="checkout" />
</form>
</html>
