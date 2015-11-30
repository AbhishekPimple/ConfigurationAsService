package com.cas.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cas.dao.FileDao;
import com.cas.service.FileService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class FileServiceImpl implements FileService {
	FileDao fileDao;


	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public List<String> getFile(int fileId) {
		 
		List<String> fileContent = new ArrayList<String>();
		Map<String, String> fileData = new HashMap<String, String>();
		String propertyHome = System.getenv("PROPERTY_HOME");
		String scriptHome = System.getenv("SCRIPT_HOME");

		String baseFileName = null;
		try {
			fileData = fileDao.getFileData(fileId);
			baseFileName = fileId + "_" + fileData.get("filename");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		/*
		 * sh launchExpect.sh parag ubuntu.local root pull /home/parag/abc.txt
		 * /home/prasad/CAS/
		 */
		Timestamp filetimestamp;

		try {

			String cmd = "sh " + scriptHome + "launchExpect.sh" + " " +scriptHome+" "+fileData.get("username") + " "
					+ fileData.get("hostname") + " " + fileData.get("password") + " pull" + " "
					+ fileData.get("configfilepath") + " " + propertyHome;

			System.out.println("Get Command is :"+cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();

			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader r2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			while (r.ready()) {
				String readLine = r.readLine();
				//System.out.println("success:");
				System.out.println(readLine);
				if (readLine.startsWith("Modify: ")) {
					
					
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date parsedDate = dateFormat.parse(readLine.substring(8, 27));
						filetimestamp = new java.sql.Timestamp(parsedDate.getTime());
						fileDao.insertFileTimeStamp(filetimestamp, fileId);

					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			while (r2.ready()) {
				System.out.println(r2.readLine());
			}
			

		} catch (Throwable t) {
			t.printStackTrace();
		}

		File oldFile = new File(propertyHome + fileData.get("filename"));
		File newFile = new File(propertyHome + baseFileName);

		oldFile.renameTo(newFile);

		fileContent.add(baseFileName);
		Scanner s = null;

		try {
			/* File myfile = new File(filename.toURI()); */
			s = new Scanner(newFile);
			while (s.hasNextLine()) {
				fileContent.add(s.nextLine());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}

		}

		return fileContent;

	}

	public boolean saveFile(String name, String content, String serverId, String isRestart) {
		 
		String propertyHome = System.getenv("PROPERTY_HOME");
		String scriptHome = System.getenv("SCRIPT_HOME");
		content = content.replaceAll("<br><br>", "\n");
		content = content.replaceAll("<div>", "");
		content = content.replaceAll("</div>", "");
		content = content.replaceAll("<br>", "\n");
		
		try{
			String filename = propertyHome + name;
			int splitIndex = name.indexOf("_");
			int fileId = Integer.parseInt(name.substring(0, splitIndex));
			String oldFileName = name.substring(splitIndex + 1, name.length());
			File thisFile = new File(filename);
			File oldFile = new File(propertyHome + oldFileName);
			thisFile.renameTo(oldFile);

			try {

				FileWriter fileWriter = new FileWriter(oldFile, false);
				fileWriter.write(content);
				fileWriter.close();

				// Scp file to required server
				/*
				 * Map<String, String> serverData = fileDao.getServerData(name,
				 * Integer.parseInt(serverId));
				 */
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException io) {
				io.printStackTrace();
			}
				Map<String, String> serverData = fileDao.getServerData(fileId, Integer.parseInt(serverId));
				String remotePath = serverData.get("remotefilepath");
				remotePath = remotePath.substring(0, remotePath.lastIndexOf("/") + 1);
				String cmd = "sh " + scriptHome + "launchExpect.sh" + " " + scriptHome+" "+serverData.get("username") + " "
						+ serverData.get("hostname") + " " + serverData.get("password") + " push" + " " + propertyHome
						+ oldFileName + " " + remotePath;

				Process p = Runtime.getRuntime().exec(cmd);
				p.waitFor();

				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader r2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				while (r.ready()) {
					System.out.println("Success");
					System.out.println(r.readLine());
				}
				while (r2.ready()) {
					System.out.println("Error!");
					System.out.println(r2.readLine());
				}
				
				if(isRestart.equals("true")){
					String restartCommand = "sh " + scriptHome + "launchExpect.sh" + " " + scriptHome+" "+serverData.get("username") + " "
							+ serverData.get("hostname") + " " + serverData.get("password") + " restart " + serverData.get("restartcommand");
					
					System.out.println("Final Restart Command is :"+restartCommand);
					Process processRestart = Runtime.getRuntime().exec(restartCommand);//new ProcessBuilder(restartCommand, serverData.get("restartcommand")).start();//Runtime.getRuntime().exec(new String[]{restartCommand, serverData.get("restartcommand")});
					processRestart.waitFor();

	
					BufferedReader iStream = new BufferedReader(new InputStreamReader(processRestart.getInputStream()));
					BufferedReader eStream = new BufferedReader(new InputStreamReader(processRestart.getErrorStream()));
	
					while (iStream.ready()) {
						System.out.println("success");
						System.out.println(iStream.readLine());
					}
					while (eStream.ready()) {
						System.out.println("Error");
						System.out.println(eStream.readLine());
					}
				
				}
				return false;

		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;

	}

	private boolean checkifModified(String substring, int fileId) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date parsedDate = dateFormat.parse(substring);
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			Timestamp firstTimeStamp = fileDao.getRetrievedTimestamp(fileId);

			if (timestamp.after(firstTimeStamp)) {
				return true;
			}

		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkModified(String name, String content, String serverId) {
		String propertyHome = System.getenv("PROPERTY_HOME");
		String scriptHome = System.getenv("SCRIPT_HOME");
		String filename = propertyHome + name;
		int splitIndex = name.indexOf("_");
		int fileId = Integer.parseInt(name.substring(0, splitIndex));
		String oldFileName = name.substring(splitIndex + 1, name.length());

		
		boolean isModified = false;
		Map<String, String> serverData = fileDao.getServerData(fileId, Integer.parseInt(serverId));

		try {
			String remotePath = serverData.get("remotefilepath");
			remotePath = remotePath.substring(0, remotePath.lastIndexOf("/") + 1);

			String remotePathCheck = serverData.get("remotefilepath");

			String checkCommand = "sh " + scriptHome + "launchExpect.sh" + " " + scriptHome+" "+serverData.get("username") + " "
					+ serverData.get("hostname") + " " + serverData.get("password") + " getModTime" + " "
					+ remotePathCheck;
			
			System.out.println("Modified time script command is :"+checkCommand);

			Process checkProc = Runtime.getRuntime().exec(checkCommand);
			checkProc.waitFor();

			BufferedReader iStream = new BufferedReader(new InputStreamReader(checkProc.getInputStream()));
			BufferedReader eStream = new BufferedReader(new InputStreamReader(checkProc.getErrorStream()));
			
			while (iStream.ready()) {
				String readLine = iStream.readLine();
				if (readLine.startsWith("Modify: ")) {

					isModified = checkifModified(readLine.substring(8, 27), fileId);

				}

			}
			while (eStream.ready()) {
					System.out.println(eStream.readLine());
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return isModified;
	}

	public com.cas.model.File addFile(com.cas.model.File file) {
		 
		return fileDao.addFile(file);
	}

}
