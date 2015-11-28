$(document).ready(
		function() {

			$("#getfile").click(function() {
				window.open("getfile", null, null, null);
			});

			function projectvalidateFields() {
				//var x = document.forms["myForm"]["fname"].value;
				var projectName = $("#projectname").val();
				if (projectName == null || projectName == "") {
					alert("Please fill all mandatory fields.");
					return false;
				}
				else
					return true;
			}
			$("#createprojectbutton").click(function() {
				if(projectvalidateFields()){

					var workbencId = $("#selectworkbench").val();
					var projectName = $("#projectname").val();
					var projectDesc = $("#projectdescription").val();

					var jsonString = {
							projectName: projectName,
							projectDesc: projectDesc,
							workbenchId: workbencId
					};

					console.log(JSON.stringify(jsonString));
					$.ajax({
						url: "project",
						type: 'POST',
						dataType: 'json',
						headers: { 
							'Content-Type': 'application/json' 
						},
						data: JSON.stringify(jsonString),
						beforeSend: function() {
							console.log("Before", JSON.stringify(jsonString));  
						},
						success: function (result) {
							alert("Project has been created successfully");
							console.log("data", result);
						},
						error: function () {
							alert("error");
						}
					});

				}
			});

			function workbenchvalidateFields() {
				//var x = document.forms["myForm"]["fname"].value;
				var workbenchName = $("#workbenchname").val();
				if (workbenchName == null || workbenchName == "") {
					alert("Please fill all mandatory fields.");
					return false;
				}
				else
					return true;
			}

			$("#createworkbenchbutton").click(function() {

				if(workbenchvalidateFields()){
					//var workbencId = $("#selectworkbench").val();
					var workbenchName = $("#workbenchname").val();
					var workbenchDesc = $("#workbenchdescription").val();

					var jsonString = {
							workbenchName: workbenchName,
							workbenchDesc: workbenchDesc
							//workbenchId: workbencId
					};

					console.log(JSON.stringify(jsonString));
					$.ajax({
						url: "workbench",
						type: 'POST',
						dataType: 'json',
						headers: { 
							'Content-Type': 'application/json' 
						},
						data: JSON.stringify(jsonString),
						beforeSend: function() {
							console.log("Before", JSON.stringify(jsonString));  
						},
						success: function (result) {
							alert("Workbench has been created successfully.");
							console.log("data", result);
						},
						error: function () {
							alert("error");
						}
					});

				}

			});

			function servervalidateFields() {

				var serverName = $("#servername").val();
				var hostIP = $("#hostnameip").val();
				var username = $("#username").val();
				var password = $("#password").val();

				if (serverName == null || serverName == "" || hostIP == null || hostIP == "" || username == null || username == "" || password == null || password == "") {
					alert("Please fill all mandatory fields.");
					return false;
				}
				else
					return true;
			}

			$("#createserverbutton").click(function() {
				if(servervalidateFields()) {
					var projectId = $("#selectproject").val();
					var serverName = $("#servername").val();
					var serverDesc = $("#serverdescription").val();
					var hostIP = $("#hostnameip").val();
					var username = $("#username").val();
					var password = $("#password").val();
					var serverType = $("#servertype").val();
					var logFilePath = $("#logfilepath").val();
					var restartCmd = $("#restartCommand").val();

					var jsonString = {
							serverName: serverName,
							serverDesc: serverDesc,
							hostIP: hostIP,
							username: username,
							password: password,
							serverType: serverType,
							logFilePath: logFilePath,
							restartCmd: restartCmd,
							projectId: projectId
					};

					console.log(JSON.stringify(jsonString));
					$.ajax({
						url: "server",
						type: 'POST',
						dataType: 'json',
						headers: { 
							'Content-Type': 'application/json' 
						},
						data: JSON.stringify(jsonString),
						beforeSend: function() {
							console.log("Before", JSON.stringify(jsonString));  
						},
						success: function (result) {
							alert("Server has been created successfully");
							console.log("data", result);
						},
						error: function () {
							alert("error");
						}
					});
				}

			});

			function filevalidateFields() {

				var filePath = $("#filepath").val();
				if (filePath == null || filePath == "") {
					alert("Please fill all mandatory fields.");
					return false;
				}
				else
					return true;
			}

			$("#addfilebutton").click(function() {
				if(filevalidateFields()) {

					var fileName = $("#filename").val();
					var fileDesc = $("#filedescription").val();
					var filePath = $("#filepath").val();
					var serverId = [];
					$('#servercheckboxes input:checked').each(function() {
						serverId.push($(this).attr('id'));
					});

					var jsonString = {
							fileName: fileName,
							fileDesc: fileDesc,
							filePath: filePath,
							serverId: serverId
					};

					console.log(JSON.stringify(jsonString));
					$.ajax({
						url: "addfile",
						type: 'POST',
						dataType: 'json',
						headers: { 
							'Content-Type': 'application/json' 
						},
						data: JSON.stringify(jsonString),
						beforeSend: function() {
							console.log("Before", JSON.stringify(jsonString));  
						},
						success: function (result) {
							alert("File has been addd successfully");
							console.log("data", result);
						},
						error: function () {
							alert("error");
						}
					});
				}

			});


			var workbenches = [ {
				text : "DEV",
				value : "1"
			}, {
				text : "Staging",
				value : "2"
			}, {
				text : "Production",
				value : "3"
			} ];

			var projects = [ {
				text : "SOLR",
				value : "1"
			}, {
				text : "File Sharing",
				value : "2"
			}, {
				text : "CAS",
				value : "3"
			} ];

			var servers = [ {
				name : "Server 1",
				id : "1",
				checked : "true"
			}, {
				name : "Server 2",
				id : "2",
				checked : "false"
			}, {
				name : "Server 3",
				id : "3",
				checked : "true"
			} ];
			$("#tabstrip").kendoTabStrip({
				animation : {
					open : {
						effects : "fadeIn"
					}
				}
			});

			$("#selectworkbench").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : workbenches,
				indexselectworkbench : 0,
				change : onChangeWorkbench
			});

			$("#selectproject").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : projects,
				index : 0,
				change : onChangeProject
			});

			function onChangeWorkbench() {
				var value = $("#selectworkbench").val();
			}
			;
			function onChangeProject() {
				var value = $("#selectproject").val();
			}
			;

			$.each(servers, function() {
				$("#servercheckboxes")
				.append(
						$("<p>").text(this.name).prepend(
								$("<input>").attr({'type':'checkbox','id':this.id})
								.val(this.id).prop('checked',
										this.checked)));
			});
		});