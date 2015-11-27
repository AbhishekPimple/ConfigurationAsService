<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="welcome" tagdir="/WEB-INF/tags"%>
<%@include file="springtabinclude.jsp"%>
<welcome:header />

<h3 align="left">Welcome ${loggedInUser}</h3>

<a class="logout" href="logout">Log Out</a>

<div id="example">
	<div class="demo-section k-content">
		<div id="tabstrip">
			<ul>
				<li class="k-state-active">My Profile</li>
				<li>Server</li>
				<li>Config Files</li>
				<li>Project</li>
				<li>Workbench</li>
			</ul>
			<div>
				<span class="myprofiletab">&nbsp;</span>
				<div class="myprofile">
					<form id="profile_form" action="getfile" method="GET" >
						<ul class="fieldlist">
							<li>
								<label for="name">File ID</label> 
								<input id="fileid" name="fileid" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<input type="button" id="getfile" class="k-button k-primary" value="Get File">
							</li>
						</ul>
					</form>
				</div>
			</div>
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
								<label for="name">Server Name*</label> 
								<input id="servername" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="description">Server Description</label>
								<textarea id="serverdescription" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<label for="hostnameip">Hostname/IP address*</label> 
								<input id="hostnameip" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="username">Username*</label> 
								<input id="username" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="password">Password*</label> 
								<input id="password" type="password" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="servertype">Server Type</label> 
								<input id="servertype" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="logfilepath">Server Log File Path</label> 
								<input id="logfilepath" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="restartCommand">Restart Command</label> 
								<input id="restartCommand" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>	
								<input type="button" id="createserverbutton" class="k-button k-primary" value="Add Server">
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div>
				<span class="filestab">&nbsp;</span>
				<div class="files">
					
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
								<label for="filepath">File Path*</label>
								<textarea id="filepath" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<input type="submit" id="addfilebutton" class="k-button k-primary" value="Add File">
							</li>
						</ul>
					
				</div>
			</div>
			<div>
				<span class="projecttab">&nbsp;</span>
				<div class="project">
					
						<ul class="fieldlist">
							<li>
								<label for="selectworkbench">Select Workbench</label> 
								<input id="selectworkbench" value="1" style="width: 100%;"/>
							</li>
							<li>
								<label for="name">Project Name*</label> 
								<input id="projectname" type="text" class="k-textbox" style="width: 100%;"/>
							</li>
							<li>
								<label for="description">Project Description</label>
								<textarea id="projectdescription" class="k-textbox" style="width: 100%;" > </textarea>
							</li>
							<li>
								<input type="button" id="createprojectbutton" class="k-button k-primary" value="Create Project">
							</li>
						</ul>
					
				</div>
			</div>
			<div>
				<span class="workbenchtab">&nbsp;</span>
				<div class="workbench">
					
						<ul class="fieldlist">
							<li>
								<label for="name">Workbench Name*</label> 
								<input id="workbenchname" type="text" class="k-textbox" style="width: 100%;" />
							</li>
							<li>
								<label for="description">Workbench Description</label>
								<textarea id="workbenchdescription" class="k-textbox" style="width: 100%;"></textarea>
							</li>
							<li>
								<input type="button" id="createworkbenchbutton" class="k-button k-primary" value="Create Workbench">
							</li>
						</ul>
					
				</div>
			</div>
		</div>
	</div>

</div>



<welcome:footer />

