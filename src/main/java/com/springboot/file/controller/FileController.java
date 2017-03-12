package com.springboot.file.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.file.domain.FileMetadata;
import com.springboot.file.domain.SearchCriteria;
import com.springboot.file.service.impl.FileServiceImpl;

/**
 * Main Controller class
 * @author Prasad Paravatha 
 */
@RestController
@RequestMapping("/fileapp")
public class FileController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	FileServiceImpl fileService;

    /**
     * Uploads the file
     * 
     * URL: /fileapp/fileupload [POST]
     * @param uploadedFile The file to be uploaded  
     * @return uploadStatus Status of the upload
     */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public @ResponseBody String fileUpload(@RequestParam("file") MultipartFile uploadedFile) throws Exception {
		String uploadStatus = "";
		if (null == uploadedFile || uploadedFile.isEmpty()) {
			throw new IllegalArgumentException("Please select the file to upload");			
		} else {
			uploadStatus = uploadedFile.getOriginalFilename() + " is uploaded successfully";
			fileService.saveFile(uploadedFile);
		}
		LOGGER.debug(uploadStatus);
		return uploadStatus;
	}
	
    /**
     * Returns the file meta data
     * 
     * URL: /fileapp/{fileName} [GET]
     * @param fileName The name of the file you want to get meta data
     * @return fileMetadata The meta data of the added file
     */
	@RequestMapping(value = "/{fileName}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody FileMetadata getFileMetaData(@PathVariable String fileName)
			throws Exception {
		FileMetadata fileMetadata = fileService.getFileMetaData(fileName);
		if(fileMetadata != null) {
			LOGGER.debug(fileMetadata.toString());		
			return fileMetadata;
		} else {
			throw new IllegalArgumentException(fileName + " does not Exist");
		}
	}
	
    /**
     * Returns the list of file IDs
     * 
     * URL: /fileapp/fileIDs [POST]
     * @param searchCriteria The Search Criteria to filter file IDs
     * @return fileIDs The list of file IDs
     */
	@RequestMapping(value = "/fileIDs", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Object getFileIDs(@RequestBody(required=false) SearchCriteria searchCriteria) throws Exception {
		List<UUID> fileIDs = fileService.getFileIDs(searchCriteria);
		if(fileIDs != null) {
			LOGGER.debug(fileIDs.toString());		
			return fileIDs;
		} else {
			throw new IllegalArgumentException("No files exist in the repository");			
		}
	}
	
    /**
     * Returns the list of file meta data
     * 
     * URL: /fileapp/filelist [GET]
     * @return fileMetaDatas The list of FileMetadata objects
     */	
	@RequestMapping(value = "/filelist", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Iterable<FileMetadata> getFileList() throws Exception {
		LOGGER.debug("in getFileList()");	
		return fileService.getFiles();
	}
	
    /**
     * Returns the file to be downloaded
     * 
     * URL: /filedownload/{fileName} [GET]
     * @param fileName The file to be downloaded
     * @param response HttpServletResponse used to return the file 
     */		
	@RequestMapping(value = "/filedownload/{fileName}", method = RequestMethod.GET)
	public void fileDownload(@PathVariable String fileName, HttpServletResponse response) throws Exception {
		FileMetadata fileMetadata = fileService.getFileMetaData(fileName);
		if(fileMetadata != null) {
	        response.setContentType(fileMetadata.getFileType());
	        response.setHeader("Content-disposition", "attachment; filename=" + fileName);        
			LOGGER.debug(fileMetadata.toString());	
	        IOUtils.copy(fileService.getFileToBeDownloaded(fileMetadata).getInputStream(), response.getOutputStream());
		} else {
			throw new IllegalArgumentException(fileName + " does not Exist");
		}
	}

	/**
	 * @return the fileService
	 */
	public FileServiceImpl getFileService() {
		return fileService;
	}

	/**
	 * @param fileService the fileService to set
	 */
	public void setFileService(FileServiceImpl fileService) {
		this.fileService = fileService;
	}
}
