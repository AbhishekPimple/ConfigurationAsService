<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>
<welcome:header />

<h3 align="left">Welcome ${loggedInUser}</h3>


<div id="example">
	<div class="demo-section k-content">
		<div id="tabstrip">
			<ul>
				<li class="k-state-active">Server</li>
				<li>Config Files</li>
				<li>Project</li>
				<li>Workbench</li>
			</ul>
			<div>
				<span class="servertab">&nbsp;</span>
				<div class="server">
					<form id="server_form" action="server" method="POST">
						<ul class="fieldlist">
							<li>
								<label for="selectproject">Select Project</label> 
								<input id="selectproject" value="1" style="width: 100%;" />
							</li>
							<li>
								<label for="name">Server Name</label> 
								<input id="servername" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="description">Server Description</label>
								<textarea id="serverdescription" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<label for="hostnameip">Hostname/IP address</label> 
								<input id="hostnameip" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="username">Username</label> 
								<input id="username" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="password">Password</label> 
								<input id="password" type="password" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="servertype">Server Type</label> 
								<input id="servertype" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="keypair">Key Pair</label> 
								<input id="keypair" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="restartCommand">Restart Command</label> 
								<input id="restartCommand" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<input type="button" class="k-button k-primary" value="Test Connection">
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div>
				<span class="filestab">&nbsp;</span>
				<div class="files">
					<form id="files_form" action="configfile" method="POST">
						<ul class="fieldlist">
							<li>
								<label for="selectservers">Select Servers</label> 
								<div id="servercheckboxes">
									
								</div>
							</li>
							<li>
								<label for="name">File Name</label> 
								<input id="filename" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="description">File Description</label>
								<textarea id="filedescription" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<label for="filepath">File Path</label>
								<textarea id="filepath" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<input type="submit" class="k-button k-primary" value="Test File">
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div>
				<span class="projecttab">&nbsp;</span>
				<div class="project">
					<form id="project_form" action="project" method="POST">
						<ul class="fieldlist">
							<li>
								<label for="selectworkbench">Select Workbench</label> 
								<input id="selectworkbench" value="1" style="width: 100%;" />
							</li>
							<li>
								<label for="name">Project Name</label> 
								<input id="projectname" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="description">Project Description</label>
								<textarea id="projectdescription" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<input type="submit" class="k-button k-primary" value="Create Project">
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div>
				<span class="workbenchtab">&nbsp;</span>
				<div class="workbench">
					<form id="workbench_form" action="workbench" method="POST">
						<ul class="fieldlist">
							<li>
								<label for="name">Workbench Name</label> 
								<input id="name" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="description">Workbench Description</label>
								<textarea id="description" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<input type="submit" class="k-button k-primary" value="Create Workbench">
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</div>


	<script>
                $(document).ready(function() {
                	var workbenches = [
                                { text: "DEV", value: "1" },
                                { text: "Staging", value: "2" },
                                { text: "Production", value: "3" }
                            ];
                    
                	var projects = [
                                       { text: "SOLR", value: "1" },
                                       { text: "File Sharing", value: "2" },
                                       { text: "CAS", value: "3" }
                                   ];
                	
                	var servers = [
                	  {
                	    name: "Server 1",
                	    id: "1",
                	    checked: "true"
                	  },
                	  {
                  	    name: "Server 2",
                  	    id: "2",
                  	    checked: "false"
                  	  },
                	  {
                  	    name: "Server 3",
                  	    id: "3",
                  	    checked: "true"
                  	  }
                	];
                	$("#tabstrip").kendoTabStrip({
                        animation:  {
                            open: {
                                effects: "fadeIn"
                            }
                        }
                    });
                    
                    $("#selectworkbench").kendoDropDownList({
                        dataTextField: "text",
                        dataValueField: "value",
                        dataSource: workbenches,
                        indexselectworkbench: 0,
                        change: onChangeWorkbench
                    });
                    
                    $("#selectproject").kendoDropDownList({
                        dataTextField: "text",
                        dataValueField: "value",
                        dataSource: projects,
                        index: 0,
                        change: onChangeProject
                    });
                    
                    function onChangeWorkbench() {
                        var value = $("#selectworkbench").val();
                    };
                    function onChangeProject() {
                        var value = $("#selectproject").val();
                    };
                    
                    
                    $.each(servers, function () {
                        $("#servercheckboxes").append($("<p>").text(this.name).prepend(
                            $("<input>").attr('type', 'checkbox').val(this.id)
                               .prop('checked', this.checked)
                        ));
                    });
                });
            </script>
</div>



<welcome:footer />

