package com.nayan.controller;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nayan.helper.FileStorageRecord;
import com.nayan.helper.FileUploader;
import com.nayan.helper.Message;
import com.nayan.helper.SystemProps;

@Controller
public class HomeController {
	
	@Autowired
	FileUploader uploader;
	
	@RequestMapping("/")
	public String home(Model m)
	{
		m.addAttribute("title", "Transfer Locally");
		return "home";
	}
	
	@RequestMapping("/transfer")
	public String transfer(Model m)
	{
		m.addAttribute("files", FileStorageRecord.getFileMap());
		return "transfer";
	}
	
	
	@PostMapping("/transfer/upload")
	public String upload(@RequestParam("fileUpload") MultipartFile file,HttpSession session)
	{
		boolean isUploaded=uploader.uploadFile(file);
		Message msg=new Message();
		if(isUploaded)
		{
			msg.setContent("File Transfered to Server Successfully and Ready to Download");
			msg.setType("alert-success");
		}else
		{
			msg.setContent("Something went wrong while tranfering file.Try again or check whether file is not empty");
			msg.setType("alert-danger");
		}
		session.setAttribute("message", msg);
		return "redirect:/transfer";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id,HttpSession session)
	{
		boolean deleted=false;
		if(FileStorageRecord.containsFile(id))
		{
			deleted=FileSystemUtils.deleteRecursively(new File(FileStorageRecord.getFilePath(id)));
			if(deleted)
			{
				FileStorageRecord.failureFallback(id);
				session.setAttribute("message",new Message("File deleted successfully","alert-success"));
			}else
			{
				session.setAttribute("message",new Message("Something went wrong. File can not be deleted","alert-danger"));
			}
		}
		else
		{
			session.setAttribute("message",new Message("Requested file can not be deleted as file does not exist","alert-danger"));
		}
		
		return "redirect:/transfer";
	}
}
