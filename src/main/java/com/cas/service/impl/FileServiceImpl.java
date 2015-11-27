package com.cas.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

		// find file name from fileId
		String baseFileName = "tempfile.log";
		
		URL filename = this.getClass().getClassLoader().getResource("temp/"+baseFileName); // Currently
																							// hard
																							// coding
																							// it
		System.out.println();

		fileContent.add(baseFileName);
		Scanner s = null;

		try {
			s = new Scanner(new File(filename.toURI()));
			while (s.hasNextLine()) {
				fileContent.add(s.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {

		} finally {
			if (s != null) {
				s.close();
			}

		}
/*		System.out.println(fileContent);*/
		
		return fileContent;

	}

	public void saveFile(int fileId) {
		// TODO Auto-generated method stub
		
		//Compute filename from fileId
		
		//Find which servers to send this file to.
		
		//Execute required script.
		
		
	}

	public com.cas.model.File addFile(com.cas.model.File file) {
		// TODO Auto-generated method stub
		return fileDao.addFile(file);
	}

}
