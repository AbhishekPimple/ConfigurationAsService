$(document).ready(
		function() {

			$("#getfile").click(function() {
				window.open("getfile", null, null, null);
			});

			$("#createprojectbutton").click(function() {
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
				    	alert("Project has been created successfullu");
				        console.log("data", result);
				    },
				    error: function () {
				        alert("error");
				    }
				});
				
				
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
										$("<input>").attr('type', 'checkbox')
												.val(this.id).prop('checked',
														this.checked)));
			});
		});