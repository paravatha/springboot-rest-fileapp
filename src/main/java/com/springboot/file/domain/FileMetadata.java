
package com.springboot.file.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * File meta data domain class
 * @author Prasad Paravatha
 */
@Entity
public class FileMetadata implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	private String fileName;
	private String fileType;
	private Long sizeInBytes;
	private String dateUploaded;
	@JsonIgnore
	private String filePath;
	@JsonIgnore
	private Date date;
	
	public FileMetadata() {
	}
	
	public FileMetadata(String fileName, String fileType, Long size, String dateUploaded) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.sizeInBytes = size;
		this.dateUploaded = dateUploaded;
	}
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

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
	 * @return the size
	 */
	public Long getSizeInBytes() {
		return sizeInBytes;
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
	 * @param size the size to set
	 */
	public void setSizeInBytes(Long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}
	/**
	 * @return the dateUploaded
	 */
	public String getDateUploaded() {
		return dateUploaded;
	}

	/**
	 * @param dateUploaded the dateUploaded to set
	 */
	public void setDateUploaded(String dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
    /**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
    public String toString() {
        return String.format(
                "FileMetadata[id=%s, fileName='%s', fileType='%s, dateUploaded='%s, filePath='%s, sizeInBytes=%s']",
                id, fileName, fileType, dateUploaded, filePath, sizeInBytes.toString() );
    }	
}
