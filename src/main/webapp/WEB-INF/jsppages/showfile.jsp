<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>${filename}</title>

<script src="<c:url value='/resources/js/jquery.min.js' />"></script>
<link href="<c:url value='/resources/styles/custom.css'/>" rel="stylesheet" />
<script src="<c:url value='/resources/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/shared/js/prettify.js'/>"></script>
<link href="<c:url value='/resources/styles/kendo.material.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/styles/kendo.common-material.min.css'/>" rel="stylesheet" />
</head>

<body style="font-size: 70%">
	<div id="filecontent" contenteditable="true">
		<c:if test="${not empty filecontent}">

			<c:forEach var="listValue" items="${filecontent}">
					${listValue} <br/>
			</c:forEach>

	</c:if>
	</div>
	<div id="savefile">
		<input type="button" class="k-button k-primary" id="savebutton" value="Save File"/>
	</div>
</body>


<script>
		$(document).ready(function() {
			/* console.log($("#filecontent").text()) */
			
			$("#savebutton").click(function(){
				var filename = document.title;
				var filecontent = $("#filecontent").text();
				
				var jsonString = {
						name: filename,
						content: filecontent
				};
				
				console.log(jsonString);
				$.ajax({
					headers: { 
				        'Accept': 'application/json',
				        'Content-Type': 'application/json' 
				    },
				    url: 'savefile',
				    type: 'POST',
				    dataType: "json",
				    data: JSON.stringify(jsonString),
				    success: function (data) {
				        alert("File Successfully Saved");
				    },
				    error: function () {
				        alert("error");
				    }
				});
				
			});
		});
		
		
	
</script>
</html>