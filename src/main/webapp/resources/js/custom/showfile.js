var myeditor;
$(document).ready(function() {
	/* console.log($("#filecontent").text()) */
	console.log("In showfile.js");
	

	var servers = window.servers;
	//console.log("In Show file", servers);
	//var serverData = JSON.parse(servers);
/*	
	$("#selectserver").kendoDropDownList({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : servers,
		indexselectserver : 0,
		change : onChangeServer
	});*/
	
/*	servers=[{
		name:"Prasad PC",
		id:"1"	
	},{
	name:"Parag PC",
	id:"2"
	}
	];*/
	
	$.each(servers, function(index, item){
		//console.log("item "+item.name);
		//console.log("item "+item.id);
		var toAppend = '<input type="checkbox" class="server" name="servers" id="'+item.id+'" >'+item.name+'&nbsp&nbsp';
		//console.log("to append :"+toAppend);
		$("#serverlist").append(toAppend);
	});
	
	
	
	
	$("#checkAll").change(function () {
	    $("input:checkbox").prop('checked', $(this).prop("checked"));
	    var checkBoxes = $('div .server');
	    if(checkBoxes.filter(':checked').length > 1){
	    	$('#saveandrestartbutton').attr('disabled',true);
	    }else{
	    	$('#saveandrestartbutton').attr('disabled',false);
	    }
	    
	});
	
	var checkBoxes = $('div .server');
	checkBoxes.change(function () {
	    $('#saveandrestartbutton').prop('disabled', checkBoxes.filter(':checked').length > 1);
	});
	$('div .server').change();
	
	
	
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
	
	
	$("#deletefilebutton").click(function() {
			var filename = document.title;
			var splitname = filename.split("_");
			var fileId = splitname[0];
			var jsonString = {
					fileId: fileId
				};
			if (confirm('Are you sure you want to delete this file?')){
				console.log(jsonString);
				$.ajax({
					headers: { 
				    	'Content-Type': 'application/json' 
				    },
					url : 'deletefile',
					type : 'POST',
					dataType : "json",
					data : JSON.stringify(jsonString),
					success : function(data) {
						alert("File has been deleted");
						window.close();
					},
					error : function(e) {
						console.log(e);
					}
				});
			}
				
		

	});
	

	$("#savebutton").click(function() {
		var filename = document.title;
		
		var filecontent = myeditor.getValue();
		//console.log(filecontent);
		
		var selectedservers = [];
		$('#serverlist input:checked').each(function() {
			selectedservers.push($(this).attr('id'));
		});
		
		console.log("selected: "+selectedservers);
		//var serverId = $("#selectserver").val();
		var serverIds = selectedservers;
		if(serverIds.length == 0){
			alert("Please select at least one server");
		}else{
			var jsonString = {
					name : filename,
					content : filecontent,
					serverIds: serverIds,
					isRestart: "false"
				};

				console.log(jsonString);
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
		}
		
		
		

	});
	
	
	
	$("#saveandrestartbutton").click(function() {
		var filename = document.title;
		var filecontent = myeditor.getValue();
		//console.log(filecontent);
		var selectedservers = [];
		$('#serverlist input:checked').each(function() {
			selectedservers.push($(this).attr('id'));
		});
		
		console.log("selected: "+selectedservers);
		//var serverId = $("#selectserver").val();
		var serverIds = selectedservers;
		

		var jsonString = {
			name : filename,
			content : filecontent,
			serverIds: serverIds,
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
				//console.log("File is not modified. So saving file");
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
								alert("File Successfully Saved and server has been restarted");
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