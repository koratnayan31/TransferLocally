package com.nayan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nayan.helper.FileStorageRecord;

@Controller
public class DownloadController {

	@GetMapping("/download/{id}")
	public ResponseEntity<InputStreamResource> download(@PathVariable("id") Integer id) {
		HttpHeaders header=new HttpHeaders();
		
		String path=FileStorageRecord.getFilePath(id);
		if(path==null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			File file=new File(path);
			long length=file.length();
			header.setContentLength(length);
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentDispositionFormData("attachment",FileStorageRecord.getFileName(id));
			InputStreamResource isr=null;
			try {
			isr=new InputStreamResource(new FileInputStream(file));
			}catch(FileNotFoundException ex)
			{
				System.out.println("Exception:"+path);
				ex.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<InputStreamResource>(isr,header,HttpStatus.OK);
		}
		
	}

}
