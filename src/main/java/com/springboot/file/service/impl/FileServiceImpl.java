package com.springboot.file.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.file.domain.FileMetadata;
import com.springboot.file.domain.SearchCriteria;
import com.springboot.file.repository.FileRepository;
import com.springboot.file.service.FileService;
import com.springboot.file.util.FileUtil;

/**
 * Implementation for File service operations
 * @author Prasad Paravatha
 *
 */
@Service
public class FileServiceImpl implements FileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Resource
	FileRepository repository;

	@Override
	public FileMetadata saveFile(MultipartFile uploadedFile) throws Exception {
		
		FileMetadata fileMetadata = FileUtil.buildFileMetadata(uploadedFile);
		LOGGER.debug("Saving " +fileMetadata.getFileName());

		FileMetadata fileMetadataExists = repository.findByName(fileMetadata.getFileName());
		if (fileMetadataExists != null) {
			fileMetadata.setId(fileMetadataExists.getId());
		}
		String filePath = FileUtil.writeFileToLocalDirectory(uploadedFile, fileMetadata);
		fileMetadata.setFilePath(filePath);
		fileMetadata = repository.save(fileMetadata);
		return fileMetadata;
	}
	
	@Override
	public FileMetadata getFileMetaData(String fileName) throws Exception {
		LOGGER.debug("Getting FileMetaData for " + fileName);
		return repository.findByName(fileName);
	}

	@Override
	public org.springframework.core.io.Resource getFileToBeDownloaded(FileMetadata fileMetadata) throws Exception {
		LOGGER.debug("Downloading file " + fileMetadata.getFileName());
		
		Path path = Paths.get(fileMetadata.getFilePath());
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
	    return resource;
	}

	@Override
	public List<UUID> getFileIDs(SearchCriteria searchCriteria) throws Exception {
		LOGGER.debug("getFileIDs based on crireria ");
		
		Iterable<FileMetadata> fileIterable = null;
		List<UUID> fileIDs = new ArrayList<UUID>();
		if (searchCriteria == null  || FileUtil.isEmpty(searchCriteria)) {
			fileIterable = repository.findAll();
		} else if (searchCriteria != null && searchCriteria.getFileType() != null && searchCriteria.getSizeInBytes()!= null) {
			fileIterable = repository.findByTypeAndSize(searchCriteria.getFileType(), searchCriteria.getSizeInBytes());
		} else if (searchCriteria != null && searchCriteria.getFileType() != null) {
			fileIterable = repository.findByType(searchCriteria.getFileType());
		}else if (searchCriteria != null && searchCriteria.getSizeInBytes()!= null) {
			fileIterable = repository.findBySize(searchCriteria.getSizeInBytes());
		} else if (searchCriteria != null && searchCriteria.getFileName() != null) {
			FileMetadata fileMetaData = repository.findByName(searchCriteria.getFileName());
			if (fileMetaData != null)
				fileIDs.add(fileMetaData.getId());
		} 
		if(fileIterable != null)
			for (FileMetadata fileMetadata : fileIterable)
				fileIDs.add(fileMetadata.getId());
			
		return fileIDs;
	}
	
	@Override
	public Iterable<FileMetadata> getFiles() throws Exception {
		LOGGER.debug("Getting all FileMetadata" );
		
		return repository.findAll();
	}
	
	/**
	 * @return the repository
	 */
	public FileRepository getRepository() {
		return repository;
	}
	/**
	 * @param repository the repository to set
	 */
	public void setRepository(FileRepository repository) {
		this.repository = repository;
	}
}
