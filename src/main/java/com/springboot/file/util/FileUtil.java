package com.springboot.file.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.file.domain.FileMetadata;
import com.springboot.file.domain.SearchCriteria;

/**
 * Utility class with re-usable methods
 * @author Prasad Paravatha
 */
public class FileUtil {
	
	public static FileMetadata buildFileMetadata(MultipartFile uploadedFile) {
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setId(UUID.randomUUID());
		fileMetadata.setFileName(uploadedFile.getOriginalFilename());
		fileMetadata.setFileType(uploadedFile.getContentType());	
		fileMetadata.setSizeInBytes(uploadedFile.getSize());

		Date currentDate = new Date();
		String dateUploaded = FileConstants.SIMPLE_DATE_FORMAT.format(currentDate);
		fileMetadata.setDateUploaded(dateUploaded);
		fileMetadata.setDate(currentDate);
		return fileMetadata;
	}
	
    public static String writeFileToLocalDirectory(MultipartFile uploadedFile, FileMetadata fileMetadata) throws IOException {

        String tempDirPath = createDirectory(fileMetadata);
        File file = new File(tempDirPath, uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(uploadedFile.getBytes());    
        fileOutputStream.close();
        return file.getPath();
    }

    private static String createDirectory(FileMetadata fileMetadata) {
        String path = getDirectoryPath(fileMetadata);
        createDirectory(path);
        return path;
    }

    private static String getDirectoryPath(FileMetadata fileMetadata) {
       return getDirectoryPath(fileMetadata.getId().toString());
    }
    
    private static String getDirectoryPath(String idPath) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FileConstants.DIRECTORY).append(File.separator).append(idPath);
        String path = stringBuilder.toString();
        return path;
    }

    private static void createDirectory(String path) {
        File file = new File(path);
        file.mkdirs();
    }

	public static boolean isEmpty(SearchCriteria searchCriteria) {
		if ((searchCriteria != null && searchCriteria.getFileName() == null 
				&& searchCriteria.getFileType() == null) && searchCriteria.getSizeInBytes() == null)
			return true;
		else 
			return false;
	}
	
	public static Date getFromDate(String frequency) {
		Calendar now = Calendar.getInstance();
		int freq = Integer.valueOf(frequency);
		now.add(Calendar.MILLISECOND, -freq);
		Date fromDate = now.getTime();
		return fromDate;		
	}
}
