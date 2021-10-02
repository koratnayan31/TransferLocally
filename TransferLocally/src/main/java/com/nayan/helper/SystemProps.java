package com.nayan.helper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class SystemProps {

	private static String path;
	private static String os;
	private static int osId;
	private static InetAddress ip;

	private static void configurePath() {
		Runtime cmd = Runtime.getRuntime();
		System.out.println("Creating temporary directory...");
		try {
			switch (osId) {
			case 0:
			case 2:
			case 3:
				cmd.exec("mkdir /tmp/TransferLocallay");
				path = "/tmp/TransferLocallay";
				break;
			case 1:
				String systemDrive = System.getenv("SystemDrive");
				String command = "cmd /c mkdir " + systemDrive + "\\temp\\TransferLocally";
				cmd.exec(command);
				path = systemDrive + "\\temp\\TransferLocally";
				break;

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Temporary Directory created at " + path);
	}

	private static void configureIPDetail(boolean giveInfoToConsole) {
		try {
			ip = InetAddress.getLocalHost();
			if (giveInfoToConsole) {
				System.out.println(ip.getHostAddress());
				System.out.println("Transfer server started at: " + ip.getHostAddress() + ":8080");
				System.out.println("Type above address in your any browser to Intialize Transfering file");
			}
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
	}

	public static String getPath() {
		return path;
	}

	public static String getLocalIP() {
		configureIPDetail(false);
		return ip.getHostAddress();
	}

	// static block to check OS
	{
		System.out.println("Detecting Your Operating System");
		os = System.getProperty("os.name").toLowerCase();
		System.out.println("System Detected: " + os);
		if (os.contains("mac"))
			osId = 0;
		else if (os.contains("windows"))
			osId = 1;
		else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0)
			osId = 2;
		else
			osId = 3;
		configurePath();
		configureIPDetail(true);
	}

}
