package com.springboot.file.repository;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.file.domain.FileMetadata;
/**
 * In memory repository with custom queries
 * @author Prasad Paravatha
 */
@Repository
public interface FileRepository extends CrudRepository<FileMetadata, Long>{
	
	@Query("select file from FileMetadata file where file.fileName = ?1")
	public FileMetadata findByName(String fileName);
	@Query("select file from FileMetadata file where file.fileType = ?1")
	public Iterable<FileMetadata> findByType(String fileType);	
	@Query("select file from FileMetadata file where file.sizeInBytes >= ?1")
	public Iterable<FileMetadata> findBySize(long sizeInBytes);	
	@Query("select file from FileMetadata file where file.fileType = ?1 and file.sizeInBytes >= ?2")
	public Iterable<FileMetadata> findByTypeAndSize(String fileType, long sizeInBytes);		
	@Query("select file from FileMetadata file where file.date >= ?1 and file.date <= ?2")
	public Iterable<FileMetadata> findFilesByTimePeriod(Date fromDate, Date toDate);

}
