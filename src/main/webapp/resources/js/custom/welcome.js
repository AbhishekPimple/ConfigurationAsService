


$(document).ready(
		function() {

			$(document).bind("contextmenu", function(e) {
				return false;
			});
			
			var treeViewdata;
			$.ajax({
				url: "loadProfile",
				type: 'POST',
				async: false,
				dataType: "json",
				success: function (result) {
					console.log("nexus",result);
					treeViewdata = result;

				},
				error: function (e) {
					console.log(e);
					alert("error"+e);
				}
			});

			console.log("After ajax call, treeview is ", treeViewdata);

			var testArray = new Array();
			var testObject = new Object();
			testObject.id=treeViewdata.UserProfile[0].userid;
			testObject.items = treeViewdata.UserProfile[0].Profile;
			//testObject.Items = treeViewdata.GetMenu[0].MenuGroup;
			testArray.push(testObject);

			var inlineDefault = new kendo.data.HierarchicalDataSource({
				data : testArray,
				schema : {
					model : {
						id : "id",
						children : "items"
					}
				}
			});

			var tree = $("#treeview-left").kendoTreeView({
				dataSource : inlineDefault,
				dataTextField : "id",
				select : function(event) {
					console.log("here");
					var parent=null,parentparent=null;
					var child = tree.dataItem(event.node);
					console.log("child",child);
					
					if(!(child.parentNode() == undefined)){
						parent = child.parentNode();
						parentparent = parent.parentNode();
					}
					console.log("child", child);
					console.log("parent", parent);
					console.log("parentparent", parentparent);
					alert("check");
					alert("node: " + JSON.stringify(child)+ "\nparent: "+ JSON.stringify(parent)+ "\nparentparent: "+ JSON.stringify(parentparent) /* + " parent:" +JSON.stringify(tree.dataItem(event.node)).parent()*/);
				}
			}).data("kendoTreeView");
			/*var jsondata = result.Profile;
			console.log("nexus",jsondata);*/

			/*var selectedNode = tree.select();
			alert( selectedNode);*/

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

				console.log(jsonString);
				$.ajax({
					url: "project",
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json',
					data: jsonString,
					beforeSend: function() {
						console.log("Before", jsonString);  
					},
					success: function (result) {
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


			/*{
					"GetMenu" : [ {
						"OutletCode" : "BOL",
						"MenuGroup" : [

						               {
						            	   "ParentId" : 1,
						            	   "ItemName" : "BEER",
						            	   "Items" : [ {
						            		   "ItemId" : 239,
						            		   "ParentId" : 1,
						            		   "ItemName" : "HEINEKEN PINT BEER",
						            		   "Price" : "35.000"
						            	   }, {
						            		   "ItemId" : 241,
						            		   "ParentId" : 1,
						            		   "ItemName" : "HEINEKEN BOTLLE",
						            		   "Price" : "35.000"
						            	   } ]
						               }, {
						            	   "ParentId" : 2,
						            	   "ItemName" : "BREEZERS",
						            	   "Items" : [ {
						            		   "ItemId" : 110,
						            		   "ParentId" : 2,
						            		   "ItemName" : "BACARDI BREEZER",
						            		   "Price" : "35.000"
						            	   } ]
						               } ],
					} ]
			};*/



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