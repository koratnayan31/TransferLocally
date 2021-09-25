package com.nayan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nayan.helper.SystemProps;

@SpringBootApplication
public class TransferLocallyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferLocallyApplication.class, args);
		new SystemProps();
	}

}
