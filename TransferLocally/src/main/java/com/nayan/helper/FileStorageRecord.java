package com.nayan.helper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class FileStorageRecord {

	private static int counter=1;
	private static Map<Integer, String> fileRecord=new HashMap<>();
	
	synchronized public static int initFileEntry(String fileName)
	{ 
		fileRecord.put(counter,fileName);
		return counter++;
	}
	
	public static void failureFallback(int id)
	{
		fileRecord.remove(id);
	}
	
	public static boolean containsFile(String filename)
	{
		return fileRecord.containsValue(filename);
	}
	
	public static boolean containsFile(int id)
	{
		return fileRecord.containsKey(id);
	}
	
	public static String getFileName(int id)
	{
		return fileRecord.get(id);
	}
	
	
	public static Map<Integer,String> getFileMap()
	{
		return new HashMap<>(fileRecord);
	}
	
	public static String getFilePath(int id)
	{
		if(containsFile(id))
		{
			String fileName=getFileName(id);
			fileName="File_".concat(""+id).concat(fileName.substring(fileName.lastIndexOf('.')));
			String fullPath=SystemProps.getPath();
			return fullPath.concat(File.separator).concat(fileName);
			
		}else
		{
			return null;
		}
	}
	
}
