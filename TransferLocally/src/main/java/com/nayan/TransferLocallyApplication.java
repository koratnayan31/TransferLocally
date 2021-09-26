package com.nayan;

import java.io.File;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.FileSystemUtils;

import com.nayan.helper.SystemProps;

@SpringBootApplication
public class TransferLocallyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferLocallyApplication.class, args);
		new SystemProps();
	}
	
	//shutdown hook
	@PreDestroy
	public void shutdown()
	{
		File file=new File(SystemProps.getPath());
		FileSystemUtils.deleteRecursively(file);
	}

}
