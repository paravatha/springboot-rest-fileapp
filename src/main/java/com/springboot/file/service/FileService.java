package com.springboot.file.service;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.file.domain.FileMetadata;
import com.springboot.file.domain.SearchCriteria;
/**
 * Interface for File service operations
 * @author Prasad Paravatha
 */
public interface FileService {
	
	/*
	 * This method saves the file 
	 * @param uploadedFile
	 */
	public FileMetadata saveFile(MultipartFile uploadedFile) throws Exception;
	
	/*
	 * This gets file meta data from the database 
	 * @param fileName
	 * @return FileMetadata
	 */
	public FileMetadata getFileMetaData(String fileName) throws Exception;

	/*
	 * This gets all file IDs from the database based on the criteria
	 * @param SearchCriteria
	 * @return List<UUID>
	 */
	public List<UUID> getFileIDs(SearchCriteria searchCriteria) throws Exception;
	
	/*
	 * This gets file to be downloaded
	 * @param fileName
	 * @return Resource
	 */
	public Resource getFileToBeDownloaded(FileMetadata fileMetadata) throws Exception;
	
	/*
	 * This gets the list of file meta data objects
	 * @return FileMetadata list
	 */
	public Iterable<FileMetadata> getFiles() throws Exception;
}
