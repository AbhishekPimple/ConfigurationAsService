<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@include file="springtabinclude.jsp"%>
<welcome:welcomeheader />

<h3 style="color: #3f51b5" align="left">Welcome ${loggedInUser}</h3>

<a class="logout" href="logout">Log Out</a>

<div style="float: left; width: 20%;">
	<h3 style="color: #3f51b5" align="left">My Profile</h3>
	<div id="treeview-left" style="color: #3f51b5"></div>
</div>

<div style="float: left; width: 80%;">
	<div id="example">
		<div class="demo-section k-content">
			<div id="tabstrip">
				<ul>
					<li id="tserver">Server</li>
					<li id="tfile">Config Files</li>
					<li id="tproject">Project</li>
					<li id="tworkbench" class="k-state-active">Workbench</li>
				</ul>
				
		

				<div class="whichtabs">
					<span class="servertab">&nbsp;</span>
					<div class="server" id="myserver">
						<form id="server_form" action="server" method="POST">
							<ul class="fieldlist">
								<li><label for="selectproject">Select Project</label> <input
									id="selectproject" value="1" style="width: 100%;" /></li>
								<li><label for="name">Server Name*</label> <input
									id="servername" type="text" class="k-textbox"
									style="width: 100%;" /></li>
								<li><label for="description">Server Description</label> <textarea
										id="serverdescription" class="k-textbox" style="width: 100%;"></textarea>
								</li>
								<li><label for="hostnameip">Hostname/IP address*</label> <input
									id="hostnameip" type="text" class="k-textbox"
									style="width: 100%;" /></li>
								<li><label for="username">Username*</label> <input
									id="username" type="text" class="k-textbox"
									style="width: 100%;" /></li>
								<li><label for="password">Password*</label> <input
									id="password" type="password" class="k-textbox"
									style="width: 100%;" /></li>
								<li><label for="servertype">Server Type</label> <input
									id="servertype" type="text" class="k-textbox"
									style="width: 100%;" /></li>
								<li><label for="logfilepath">Server Log File Path</label> <input
									id="logfilepath" type="text" class="k-textbox"
									style="width: 100%;" /></li>
								<li><label for="restartCommand">Restart Command</label> <input
									id="restartCommand" type="text" class="k-textbox"
									style="width: 100%;" /></li>
								<li><input type="button" id="createserverbutton"
									class="k-button k-primary" value="Add Server"></li>
								<li><input type="button" id="updateserverbutton"
									class="k-button k-primary" value="Update Server"></li>
								<li><input type="button" id="deleteserverbutton" style="background-color: red"
									class="k-button k-primary" value="Delete Server"></li>
							</ul>
						</form>
					</div>
				</div>
				<div class="whichtabs">
					<span class="filestab">&nbsp;</span>
					<div class="files">

						<ul class="fieldlist">
							<li><label for="selectservers">Select Servers</label>
								<div id="servercheckboxes"></div></li>
							<li><label for="name">File Name</label> <input id="filename"
								type="text" class="k-textbox" style="width: 100%;" /></li>
							<li><label for="description">File Description</label> <textarea
									id="filedescription" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li><label for="filepath">File Path*</label> <textarea
									id="filepath" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li><input type="submit" id="addfilebutton"
								class="k-button k-primary" value="Add File"></li>
						</ul>

					</div>
				</div>
				<div class="whichtabs">
					<span class="projecttab">&nbsp;</span>
					<div class="project">

						<ul class="fieldlist">
							<li><label for="selectworkbench">Select Workbench</label> <input
								id="selectworkbench" value="1" style="width: 100%;" /></li>
							<li><label for="name">Project Name*</label> <input
								id="projectname" type="text" class="k-textbox"
								style="width: 100%;" /></li>
							<li><label for="description">Project Description</label> <textarea
									id="projectdescription" class="k-textbox" style="width: 100%;"> </textarea>
							</li>
							<li><input type="button" id="createprojectbutton"
								class="k-button k-primary" value="Create Project"></li>
							<li><input type="button" id="updateprojectbutton"
								class="k-button k-primary" value="Update Project"></li>
							<li><input type="button" id="deleteprojectbutton" style="background-color: red"
								class="k-button k-primary" value="Delete Project"></li>
						</ul>

					</div>
				</div>
				<div class="whichtabs">
					<span class="workbenchtab">&nbsp;</span>
					<div class="workbench">

						<ul class="fieldlist">
							<li><label for="name">Workbench Name*</label> <input
								id="workbenchname" type="text" class="k-textbox"
								style="width: 100%;" /></li>
							<li><label for="description">Workbench Description</label> <textarea
									id="workbenchdescription" class="k-textbox"
									style="width: 100%;"></textarea></li>
							<li><input type="button" id="createworkbenchbutton"
								class="k-button k-primary" value="Create Workbench"></li>
							<li><input type="button" id="updateworkbenchbutton"
								class="k-button k-primary" value="Update Workbench"></li>
							<li><input type="button" id="deleteworkbenchbutton" style="background-color: red"
								class="k-button k-primary" value="Delete Workbench"></li>
						</ul>

					</div>
				</div>
			</div>
		</div>

	</div>
</div>


	<welcome:footer />