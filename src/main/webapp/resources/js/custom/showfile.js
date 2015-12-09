var myeditor;
$(document).ready(function() {
	/* console.log($("#filecontent").text()) */
	
	

	var servers = window.servers;
	console.log("In Show file", servers);
	//var serverData = JSON.parse(servers);
	
	$("#selectserver").kendoDropDownList({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : servers,
		indexselectserver : 0,
		change : onChangeServer
	});
	
	function onChangeServer() {
		var value = $("#selectserver").val();
	}
	
	
	
/*	$.ajax({
		url: "getfile",
		type: 'POST',
		async: false,
		dataType: "json",
		success: function (result) {
			console.log("Got result: "+result);
			
			var editor = CodeMirror.fromTextArea(document.getElementById("filecontent"), {
		        value: '${filecontent}',
				mode: "xml",
		        lineNumbers: true,
		    });
		},
		error: function (e) {
			//console.log(e);
			alert("error"+e);
		}
	});*/
	

	$('div[contenteditable]').keydown(function(e) {
	    // trap the return key being pressed
	    if (e.keyCode === 13) {
	      // insert 2 br tags (if only one br tag is inserted the cursor won't go to the next line)
	      document.execCommand('insertHTML', false, '<br><br>');
	      // prevent the default behaviour of return key pressed
	      return false;
	    }
	  });

	$("#savebutton").click(function() {
		var filename = document.title;
		/*var regex = /<br\s*[\/]?>/gi;*/
		//console.log("before", $("#filecontent").html());
		//var filecontent = $("#filecontent").val();
		var filecontent = myeditor.getValue();
		console.log(filecontent);
		var serverId = $("#selectserver").val();
		

		var jsonString = {
			name : filename,
			content : filecontent,
			serverId: serverId,
			isRestart: "false"
		};

		
		$.ajax({
			headers: { 
		    	'Content-Type': 'application/json' 
		    },
			url : 'checkmodify',
			type : 'POST',
			dataType : "json",
			data : JSON.stringify(jsonString),
			success : function(data) {
				console.log("File is not modified. So saving file");
				$.ajax({
					headers: { 
				    	'Content-Type': 'application/json' 
				    },
					url : 'savefile',
					type : 'POST',
					dataType : "json",
					data : JSON.stringify(jsonString),
					success : function(data) {
						console.log("Savefile :", data);
						alert("File Successfully Saved");
						window.close();
					},
					error : function(e) {
						console.log(e);
						alert("error during save file");
					}
				});
			},
			error : function(e) {
				console.log(e);
				if(e.responseText == "modified"){
					console.log("File has been modified. Ask for user preference.");
					if (confirm('File has been modifield. Do you still want to save it anyway?')){
						$.ajax({
							headers: { 
						    	'Content-Type': 'application/json' 
						    },
							url : 'savefile',
							type : 'POST',
							dataType : "json",
							data : JSON.stringify(jsonString),
							success : function(data) {
								console.log("Savefile :", data);
								alert("File Successfully Saved");
								window.close();
							},
							error : function(e) {
								if(e.responseText == "modified"){
									
								}
								console.log(e);
								alert("error during saving after user confirmation");
							}
						});
					}
				}else{
					alert("error during check modification");
				}
				
				
			}
		});
		
		console.log(jsonString);
		
		

	});
	
	
	
	$("#saveandrestartbutton").click(function() {
		var filename = document.title;
		/*var regex = /<br\s*[\/]?>/gi;*/
		console.log("before", $("#filecontent").html());
		var filecontent = $("#filecontent").html();
		console.log(filecontent);
		var serverId = $("#selectserver").val();
		

		var jsonString = {
			name : filename,
			content : filecontent,
			serverId: serverId,
			isRestart: "true",
		};

		
		$.ajax({
			headers: { 
		    	'Content-Type': 'application/json' 
		    },
			url : 'checkmodify',
			type : 'POST',
			dataType : "json",
			data : JSON.stringify(jsonString),
			success : function(data) {
				console.log("File is not modified. So saving file");
				$.ajax({
					headers: { 
				    	'Content-Type': 'application/json' 
				    },
					url : 'savefile',
					type : 'POST',
					dataType : "json",
					data : JSON.stringify(jsonString),
					success : function(data) {
						console.log("Savefile :", data);
						alert("File Successfully Saved");
						window.close();
					},
					error : function(e) {
						console.log(e);
						alert("error during save file");
					}
				});
			},
			error : function(e) {
				console.log(e);
				if(e.responseText == "modified"){
					console.log("File has been modified. Ask for user preference.");
					if (confirm('File has been modifield. Do you still want to save it anyway?')){
						$.ajax({
							headers: { 
						    	'Content-Type': 'application/json' 
						    },
							url : 'savefile',
							type : 'POST',
							dataType : "json",
							data : JSON.stringify(jsonString),
							success : function(data) {
								console.log("Savefile :", data);
								alert("File Successfully Saved");
								window.close();
							},
							error : function(e) {
								if(e.responseText == "modified"){
									
								}
								console.log(e);
								alert("error during saving after user confirmation");
							}
						});
					}
				}else{
					alert("error during check modification");
				}
				
				
			}
		});
		
		console.log(jsonString);
		
		

	});
});