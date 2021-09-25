package com.nayan.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploader {
	
	public boolean uploadFile(MultipartFile file)
	{
		if(file.isEmpty())
			return false;
		
		
		String path=SystemProps.getPath();
		int id=0;
		System.out.println("Upload of "+file.getOriginalFilename()+" has been started");
		try {
			
			id=FileStorageRecord.initFileEntry(file.getOriginalFilename());
			
			Files.copy(file.getInputStream(),Paths.get(path+File.separator+"File_"+id+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))),StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			System.err.println("Error IOException while uploading file");
			
			//file entry is done but upload unsuccessful so remove file entry
			if(id!=0)
				FileStorageRecord.failureFallback(id);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
