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
		// TODO Auto-generated method stub
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

		try {

			String cmd = "sh " + scriptHome + "launchExpect.sh" + " " + fileData.get("username") + " "
					+ fileData.get("hostname") + " " + fileData.get("password") + " pull" + " "
					+ fileData.get("configfilepath") + " " + propertyHome;

			System.out.println("final pull command is " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();

			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader r2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			System.out.println("Output stream: " + p.getOutputStream().toString());
			System.out.println("Exit Value is : " + p.exitValue());

			while (r.ready()) {
				System.out.println(r.readLine());
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
		/* System.out.println(fileContent); */

		return fileContent;

	}

	public boolean saveFile(String name, String content, String serverId) {
		// TODO Auto-generated method stub
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
				String cmd = "sh " + scriptHome + "launchExpect.sh" + " " + serverData.get("username") + " "
						+ serverData.get("hostname") + " " + serverData.get("password") + " push" + " " + propertyHome
						+ oldFileName + " " + remotePath;

				System.out.println("final push command is " + cmd);
				Process p = Runtime.getRuntime().exec(cmd);
				p.waitFor();

				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader r2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				System.out.println("Output stream: " + p.getOutputStream().toString());
				System.out.println("Exit Value is : " + p.exitValue());

				while (r.ready()) {

					System.out.println("Success!!!!!!!");
					System.out.println(r.readLine());
				}
				while (r2.ready()) {

					System.out.println("Error !!!!!!");
					System.out.println(r2.readLine());
				}
				System.out.println("File Saved");
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
			// TODO Auto-generated catch block
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

			String checkCommand = "sh " + scriptHome + "launchExpect.sh" + " " + serverData.get("username") + " "
					+ serverData.get("hostname") + " " + serverData.get("password") + " getModTime" + " "
					+ remotePathCheck;

			System.out.println("final get mod time command is " + checkCommand);
			Process checkProc = Runtime.getRuntime().exec(checkCommand);
			checkProc.waitFor();

			BufferedReader iStream = new BufferedReader(new InputStreamReader(checkProc.getInputStream()));
			BufferedReader eStream = new BufferedReader(new InputStreamReader(checkProc.getErrorStream()));
			System.out.println("Output stream: " + checkProc.getOutputStream().toString());
			System.out.println("Exit Value is : " + checkProc.exitValue());
			
			while (iStream.ready()) {
				String readLine = iStream.readLine();
				if (readLine.startsWith("Modify: ")) {

					isModified = checkifModified(readLine.substring(8, 27), fileId);

				}
				System.out.println(readLine);
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
		// TODO Auto-generated method stub
		return fileDao.addFile(file);
	}

}
