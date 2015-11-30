package com.cas.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cas.dao.TreeViewDao;

public class TreeViewDaoImpl implements TreeViewDao{
	DataSource dataSource;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JSONObject populate(String emailId) throws SQLException {

		int userID = 0;
		String workbenchID = null;
		String workbenchName = null;
		PreparedStatement pstmt;
		ResultSet resultSet;
		List<String> workbenchIDList = new ArrayList<String>();

		Map<String,Map<String,String>> workbenchMap = new HashMap(); 

		String user = "Select user_id from user where user_email_id = ?";
		String workbench = "Select * from workbench where user_id = ?";
		String project = "Select * from project where workbench_id = ?";
		String server = "Select * from server where project_id = ?";
		String config = "Select * from config where server_id = ?";
		pstmt = dataSource.getConnection().prepareStatement(user);
		pstmt.setString(1, emailId);
		resultSet = pstmt.executeQuery();
		while(resultSet.next()){
			userID =  resultSet.getInt("user_id");
		}

		//get list of workbenches 
		JSONObject profile = new JSONObject();
		String workbenchDesc;
		JSONArray workbenches;
		pstmt = dataSource.getConnection().prepareStatement(workbench);
		pstmt.setString(1, String.valueOf(userID));
		resultSet = pstmt.executeQuery();
		workbenches = new JSONArray();
		JSONObject jsontemp ;
		while(resultSet.next()){
			jsontemp = new JSONObject();
			workbenchID = String.valueOf(resultSet.getInt("workbench_id"));
			workbenchName = resultSet.getString("workbench_name");
			workbenchDesc = resultSet.getString("workbench_desc");
			workbenchIDList.add(workbenchID);
			jsontemp.put("workbenchid",workbenchID );
			jsontemp.put("workbenchDesc", workbenchDesc);
			jsontemp.put("id", workbenchName);
			jsontemp.put("items", new JSONArray());
			workbenches.put(jsontemp);
		}

		profile.put("Profile", workbenches);

		//get project from workbenchesID
		Map<String,String> tempProjectMap;
		JSONObject jsonProjectTemp;
		String projectID,projectName,projectDesc;
		JSONArray objecttemp = null;
		pstmt = dataSource.getConnection().prepareStatement(project);
		for (ListIterator<String> iter = workbenchIDList.listIterator(); iter.hasNext(); ) {
			String tempWorkbenchID = iter.next();
			pstmt.setString(1, tempWorkbenchID);
			resultSet = pstmt.executeQuery();
			tempProjectMap = new HashMap();
			objecttemp = profile.getJSONArray("Profile");
			int location = -1;
			JSONArray jsonProjectTempArray = new JSONArray();

			for(int i = 0 ;i<objecttemp.length();i++){
				JSONObject tempjs = (JSONObject) objecttemp.get(i);
				if(tempjs.get("workbenchid").equals(tempWorkbenchID)){
					//if(tempjs.get("WorkbenchID").equals(tempWorkbenchID)){
					location = i;
				}
			}
			while(resultSet.next()){
				jsonProjectTemp = new JSONObject();
				projectID = String.valueOf(resultSet.getInt("project_id"));
				projectName = resultSet.getString("project_name");
				projectDesc = resultSet.getString("project_desc");
				tempProjectMap.put(projectID, projectName);
				jsonProjectTemp.put("ProjectID", projectID);
				jsonProjectTemp.put("id",projectName);
				jsonProjectTemp.put("projectDesc",projectDesc);
				jsonProjectTemp.put("items", new JSONArray());
				jsonProjectTempArray.put(jsonProjectTemp);
			}
			if(location>-1){
				JSONObject tempjs = (JSONObject) objecttemp.get(location);
				tempjs.put("items", jsonProjectTempArray);
				objecttemp.put(location, tempjs);
			}
			workbenchMap.put(tempWorkbenchID, tempProjectMap);
		}

		profile.put("Profile", objecttemp);
		System.out.println(profile.toString());

		//get 	server from project

		Map<String,Map<String,String>> projectMap = new HashMap();
		Map<String,String> tempServerMap;
		String serverID,serverName,serverUsername,serverIP,serverLogPath,serverType,serverStatus,serverDesc,serverRestartCmd;
		JSONObject jsonServerTemp;
		JSONArray jsonServerTempArray;

		pstmt = dataSource.getConnection().prepareStatement(server);
		for (Map.Entry<String,Map<String,String>> iter : workbenchMap.entrySet() ) {
			for(Map.Entry<String, String> iter1: iter.getValue().entrySet()){
				String tempProjectID = iter1.getKey();
				pstmt.setString(1, tempProjectID);
				resultSet = pstmt.executeQuery();
				tempServerMap = new HashMap();
				jsonServerTempArray = new JSONArray();
				int locationi=-1,locationj=-1;
				for(int i =0;i<objecttemp.length();i++){
					JSONObject tempjs = (JSONObject) objecttemp.get(i);
					JSONArray tempjsArr = tempjs.getJSONArray("items");

					for(int j=0;j<tempjsArr.length();j++){
						JSONObject tempProjectLevel = (JSONObject) tempjsArr.get(j);
						if(tempProjectLevel.get("ProjectID").equals(tempProjectID)){
							locationj = j;
							locationi = i;
						}
					}
				}

				System.out.println("after loop :location i and location j " + locationi +" "+ locationj);
				while(resultSet.next()){
					jsonServerTemp = new JSONObject();
					serverID = String.valueOf(resultSet.getInt("server_id"));
					serverName = resultSet.getString("server_name");
					serverUsername = resultSet.getString("server_username");
					serverIP = resultSet.getString("server_ipaddress");
					serverLogPath = resultSet.getString("server_logfile_path");
					serverType = resultSet.getString("server_type");
					serverStatus = resultSet.getString("server_status");
					serverDesc = resultSet.getString("server_desc");
					serverRestartCmd = resultSet.getString("server_restart_cmd");
					jsonServerTemp.put("ServerID", serverID);
					//jsonServerTemp.put("ServerName", serverName);
					jsonServerTemp.put("id", serverName);
					jsonServerTemp.put("serverUsername", serverUsername);
					jsonServerTemp.put("serverIP", serverIP);
					jsonServerTemp.put("serverLogPath", serverLogPath);
					jsonServerTemp.put("serverType", serverType);
					jsonServerTemp.put("serverStatus", serverStatus);
					jsonServerTemp.put("serverDesc", serverDesc);
					jsonServerTemp.put("serverRestartCmd", serverRestartCmd);
					tempServerMap.put(serverID, serverName);
					jsonServerTempArray.put(jsonServerTemp);
				}	
				System.out.println("location i and location j " + locationi +" "+ locationj);
				if(locationi>-1){

					JSONObject tempjs = (JSONObject) objecttemp.get(locationi);
					//JSONArray tempjsArr = tempjs.getJSONArray("Projects");
					JSONArray tempjsArr = tempjs.getJSONArray("items");
					JSONObject tempProjectLevel =null;
					if(locationj>-1){
						tempProjectLevel = (JSONObject) tempjsArr.get(locationj);
						tempProjectLevel.put("items", jsonServerTempArray);
						//tempProjectLevel.put("Servers", jsonServerTempArray);
						tempjsArr.put(locationj, tempProjectLevel);
					}
					tempjs.put("items", tempjsArr);
					//tempjs.put("Projects", tempjsArr);
					objecttemp.put(locationi,tempjs);

				}
				projectMap.put(tempProjectID, tempServerMap);
			}
		}


		//get config from server
		Map<String,Map<String,String>> serverMap = new HashMap();
		Map<String,JSONObject> serverMapJson = new HashMap();
		Map<String,String> tempConfigMap;
		JSONArray jsonConfigTempArray;
		JSONObject jsonConfigTemp;
		String configID,configName;
		pstmt = dataSource.getConnection().prepareStatement(config);
		for (Map.Entry<String,Map<String,String>> iter : projectMap.entrySet() ) {
			String tempServerID = iter.getKey();
			pstmt.setString(1, tempServerID);
			resultSet = pstmt.executeQuery();
			tempServerMap = new HashMap();

			jsonConfigTempArray = new JSONArray();

			int locationi=-1,locationj=-1,locationk=-1;
			for(int i =0;i<objecttemp.length();i++){
				JSONObject tempjs = (JSONObject) objecttemp.get(i);
				//JSONArray tempjsArr = tempjs.getJSONArray("Projects");
				JSONArray tempjsArr = tempjs.getJSONArray("items");
				for(int j=0;j<tempjsArr.length();j++){
					JSONObject tempProjectLevel = (JSONObject) tempjsArr.get(j);
					//JSONArray tempJsonArrayServer = tempProjectLevel.getJSONArray("Servers");
					JSONArray tempJsonArrayServer = tempProjectLevel.getJSONArray("items");
					for( int k=0;k<tempJsonArrayServer.length();k++){
						JSONObject tempServerLevel = (JSONObject) tempJsonArrayServer.get(k);
						if(tempServerLevel.get("ServerID").equals(tempServerID)){
							locationj = j;
							locationi = i;
							locationk = k;
						}
					}
				}
			}

			while(resultSet.next()){
				jsonConfigTemp= new JSONObject();
				configID = String.valueOf(resultSet.getInt("config_id"));
				configName = resultSet.getString("config_name");
				jsonConfigTemp.put("ConfigID",configID);
				//jsonConfigTemp.put("ConfigName", configName);
				jsonConfigTemp.put("id", configName);
				jsonConfigTempArray.put(jsonConfigTemp);

			}	
			System.out.println("location i and location j and location k  " + locationi +" "+ locationj + " " + locationk);
			JSONObject tempjs = null;
			JSONArray tempjsArr = null;
			if(locationi>-1){
				tempjs = (JSONObject) objecttemp.get(locationi);
				//JSONArray tempjsArr = tempjs.getJSONArray("Projects");
				tempjsArr = tempjs.getJSONArray("items");
				JSONObject tempProjectLevel = null;
				JSONArray tempjsArrServer = null;
				JSONObject tempServerLevel = null;
				if(locationj>-1){
					tempProjectLevel = (JSONObject) tempjsArr.get(locationj);
					//JSONArray tempjsArrServer = tempProjectLevel.getJSONArray("Servers");
					tempjsArrServer = tempProjectLevel.getJSONArray("items");
					if(locationk>-1){
						tempServerLevel = (JSONObject) tempjsArrServer.get(locationk);
						//tempServerLevel.put("Configs", jsonConfigTempArray);
						tempServerLevel.put("items", jsonConfigTempArray);
						tempjsArrServer.put(locationk,tempServerLevel);			
					}
					tempProjectLevel.put("items", tempjsArrServer);
					//tempProjectLevel.put("Servers", tempjsArrServer);
					tempjsArr.put(locationj, tempProjectLevel);
				}
				tempjs.put("items", tempjsArr);
				//tempjs.put("Projects", tempjsArr);
				objecttemp.put(locationi,tempjs);
			}
			serverMap.put(tempServerID, tempServerMap);
		}

		profile.put("Profile", objecttemp);

		JSONObject UserProfile = new JSONObject();
		UserProfile.put("UserProfile", new JSONArray() );
		JSONArray Root = new JSONArray();
		JSONObject uid = new JSONObject();
		uid.put("userid", emailId);
		uid.put("Profile", objecttemp);
		Root.put(uid);
		UserProfile.put("UserProfile", Root);
		System.out.println(UserProfile.toString());
		return UserProfile;
	}
}