<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${items}" var="item">
	<li>
		<c:out value="${item}" />
	</li>
</c:forEach>
