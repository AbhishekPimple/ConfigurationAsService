<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>${filename}</title>

<script src="<c:url value='/resources/js/jquery.min.js' />"></script>
<link href="<c:url value='/resources/styles/showfilecustom.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/styles/codemirror.css'/>" rel="stylesheet" />

<!-- <link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/codemirror/CodeMirror/master/lib/codemirror.css"> -->
<!-- <script type="text/javascript" src="https://cdn.rawgit.com/codemirror/CodeMirror/master/lib/codemirror.js"></script> -->

<script src="<c:url value='/resources/js/custom/codemirror.js' />"></script>
<script type="text/javascript" src="https://cdn.rawgit.com/codemirror/CodeMirror/master/mode/xml/xml.js"></script>
<script src="<c:url value='/resources/js/custom/showfile.js' />"></script>

<script src="<c:url value='/resources/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/shared/js/prettify.js'/>"></script>
<link href="<c:url value='/resources/styles/kendo.material.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/styles/kendo.common-material.min.css'/>"
	rel="stylesheet" />
</head>

<body style="font-size: 90%">
		<textarea id="filecontent">
			
		</textarea>
	<script type="text/javascript">
		
		var filecontents = '${filecontents}';
		console.log("File content is:"+filecontents);
		myeditor = CodeMirror.fromTextArea(document.getElementById("filecontent"), {
			mode: "xml",
	        lineNumbers: true,
	    });
		
		myeditor.setValue(filecontents);
		
	
	</script>

	
	<%-- <div id="filecontent" contenteditable="true">
		<c:if test="${not empty filecontent}">
			<c:forEach var="listValue" items="${filecontent}">${listValue}<br>
			</c:forEach>
		</c:if>
	</div> --%>
	<div id='loader' class="loaderdiv">
		<img src="resources/images/ajax-loader.gif"/>
	</div>
	<div id="savefile">
		<label style="color: white" for="selectserver"><b>Push this file to server</b></label><br>
		<input type="checkbox" id="checkAll"/> <label style="color:white"> Select all</label><br>
		<div id="serverlist">
			
		</div>
		<!--  <input id="selectserver" value="1" style="width: 100%;" /> --> <br> 
			<input type="button" class="k-button k-primary" id="savebutton" value="Push File" /> 
			<input type="button" class="k-button k-primary" id="saveandrestartbutton" value="Push File and Restart Server" />
			<input type="button" style="background-color: red" class="k-button k-primary" id="deletefilebutton" value="Delete this file" />


	</div>
</body>

</html>