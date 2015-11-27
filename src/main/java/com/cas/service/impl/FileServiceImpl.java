package com.cas.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cas.dao.FileDao;
import com.cas.service.FileService;

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

		String baseFileName = null;
		try {
			fileData = fileDao.getFileData(fileId);
			baseFileName = fileId+"_"+fileData.get("filename");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		/*   sh launchExpect.sh parag ubuntu.local root pull /home/parag/abc.txt /home/prasad/CAS/   */
		
		
		
		Map<String, String> serverData = fileDao.getServerData(fileId);
	
		String filename = propertyHome+baseFileName;

		fileContent.add(baseFileName);
		Scanner s = null;

		try {
			File myfile = new File(filename);
			/*File myfile = new File(filename.toURI());*/
			s = new Scanner(myfile);
			while (s.hasNextLine()) {
				fileContent.add(s.nextLine());
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (URISyntaxException e) {

		}*/ finally {
			if (s != null) {
				s.close();
			}

		}
/*		System.out.println(fileContent);*/
		
		return fileContent;

	}

	public void saveFile(String name, String content, String serverId) {
		// TODO Auto-generated method stub
		String propertyHome = System.getenv("PROPERTY_HOME");
		content = content.replaceAll("<br><br>", "\n");
		content = content.replaceAll("<div>", "");
		content = content.replaceAll("</div>", "");
		content = content.replaceAll("<br>", "\n");
		System.out.println("filecontent is :"+content);
		String filename = propertyHome+name; 
		try {
			File thisFile = new File(filename);
			FileWriter fileWriter = new FileWriter(thisFile, false);
			fileWriter.write(content);
			fileWriter.close();
			System.out.println("File Saved");
			
			
			//Scp file to required server
			/*Map<String, String> serverData = fileDao.getServerData(name, Integer.parseInt(serverId));*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
	}

}
