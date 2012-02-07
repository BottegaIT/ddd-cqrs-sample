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
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>DDD CQRS Sample</title>
<!--[if IE]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<link rel="stylesheet" href="<c:url value="/static/css/layout/layout.css" />" type="text/css"
	media="all" />
<tiles:useAttribute name="additionalCss" classname="java.util.List" ignore="true" />
<c:forEach var="style" items="${additionalCss}">
	<link rel="stylesheet" href="<c:url value="/static/css/${style}" />" type="text/css" media="all" />
</c:forEach>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<tiles:useAttribute name="additionalJs" classname="java.util.List" ignore="true" />
<c:forEach var="script" items="${additionalJs}">
	<script type="text/javascript" src="<c:url value="/static/js/${script}" />"></script>
</c:forEach>
</head>
<body>
	<div class="logo">DDD CQRS Sample</div>

	<div class="siteBody">
		<tiles:insertAttribute name="menu" />

		<tiles:insertAttribute name="content" />
	</div>
</body>
</html>
