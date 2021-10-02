package com.nayan;

import java.io.File;

import javax.annotation.PreDestroy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.util.FileSystemUtils;

import com.nayan.helper.DisplayManager;
import com.nayan.helper.SystemProps;

@SpringBootApplication
public class TransferLocallyApplication {

	public static void main(String[] args) {
		
		SpringApplicationBuilder builder=new SpringApplicationBuilder(TransferLocallyApplication.class);
		builder.headless(false);
		builder.run(args);
		new SystemProps();
		Thread displayManager=new Thread(new DisplayManager());
		displayManager.start();
	}
	
	//shutdown hook
	@PreDestroy
	public void shutdown()
	{
		File file=new File(SystemProps.getPath());
		FileSystemUtils.deleteRecursively(file);
	}

}
