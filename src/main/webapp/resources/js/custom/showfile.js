$(document).ready(function() {
	/* console.log($("#filecontent").text()) */
	var servers = [ {
		text : "Server 1",
		value : "6"
	}, {
		text : "Server 2",
		value : "2"
	}, {
		text : "Server 3",
		value : "3"
	} ];
	
	$("#selectserver").kendoDropDownList({
		dataTextField : "text",
		dataValueField : "value",
		dataSource : servers,
		indexselectserver : 0,
		change : onChangeServer
	});
	
	function onChangeServer() {
		var value = $("#selectserver").val();
	}
	

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
		console.log("before", $("#filecontent").html());
		var filecontent = $("#filecontent").html();
		console.log(filecontent);
		var serverId = $("#selectserver").val();
		

		var jsonString = {
			name : filename,
			content : filecontent,
			serverId: serverId
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