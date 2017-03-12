package com.springboot.file.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * @author Prasad Paravatha
 *
 */
public final class FileConstants {
	
	public static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	public static final String DIRECTORY = "filesystem";
	public static final String EMAIL_SUBJECT = "Update on your File system";

}
