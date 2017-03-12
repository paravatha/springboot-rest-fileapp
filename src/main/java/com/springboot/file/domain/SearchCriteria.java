package com.springboot.file.domain;

import java.io.Serializable;
/**
 * Search criteria domain class
 * @author Prasad Paravatha
 */
public class SearchCriteria implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileType;
	private Long sizeInBytes;
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the sizeInBytes
	 */
	public Long getSizeInBytes() {
		return sizeInBytes;
	}
	/**
	 * @param sizeInBytes the sizeInBytes to set
	 */
	public void setSizeInBytes(Long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}
	
	@Override
    public String toString() {
        return String.format(
                "SearchCriteria[fileName='%s', fileType='%s, sizeInBytes=%s']",
                 fileName, fileType,  sizeInBytes.toString() );
    }
}
