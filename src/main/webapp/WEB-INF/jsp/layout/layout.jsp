<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/static/css/layout/layout.css" />"
	type="text/css" media="all" />
<tiles:useAttribute name="additionalCss" classname="java.util.List" ignore="true"/>
<c:forEach var="style" items="${additionalCss}">
	<link rel="stylesheet" href="<c:url value="/static/css/${style}" />" type="text/css" media="all" />
</c:forEach>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<tiles:useAttribute name="additionalJs" classname="java.util.List" ignore="true"/>
<c:forEach var="script" items="${additionalJs}">
	<script type="text/javascript" src="<c:url value="/static/js/${script}" />" ></script>
</c:forEach>
</head>
<body>
	<tiles:insertAttribute name="menu" />
	<div class="siteContent">
		<tiles:insertAttribute name="content" />
	</div>
</body>
</html>
