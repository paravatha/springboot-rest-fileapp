package com.springboot.file.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.file.domain.FileMetadata;
import com.springboot.file.util.FileUtil;

/**
 * Unit test class for In Memory database
 * @author Prasad Paravatha
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileRepositoryTest {

	@Autowired
	private FileRepository fileRepository;
	private FileMetadata fileMetadata;
	private String fileName;
	private String fileType;
	
	@Before
    public void setUp() {
		fileName = "Test.jpg";
		fileType = "image/jpeg";
		fileMetadata = new FileMetadata();
		fileMetadata.setId(UUID.randomUUID());
		fileMetadata.setFileName(fileName);
		fileMetadata.setFileType(fileType);
		fileMetadata.setSizeInBytes(2000L);
		Calendar minuteBefore = Calendar.getInstance();
		minuteBefore.add(Calendar.MINUTE, -1);
		fileMetadata.setDate(minuteBefore.getTime());
	}
	/**
	 * Tests the save method .
	 */
	@Test
    public void saveTest() {
		fileMetadata = fileRepository.save(fileMetadata);
		Assert.assertNotNull(fileMetadata); 
		Assert.assertNotNull(fileMetadata.getId()); 
    }
	
	/**
	 * Tests the FindByName method .
	 */
	@Test
    public void findByNameTest() {
		FileMetadata fileMetadataFind = fileRepository.findByName(fileName);
        Assert.assertEquals(fileMetadataFind.getFileName(), fileName);
    }
	
	/**
	 * Tests the findByType method
	 */
	@Test
    public void findByTypeTest() {
		Iterable<FileMetadata> fileMetadataFind =  fileRepository.findByType(fileType);
        Assert.assertEquals(fileMetadataFind.iterator().next().getFileType(), fileType);
    }	
	
	/**
	 * Tests the findBySize method
	 */
	@Test
    public void findBySizeTest() {
		Iterable<FileMetadata> fileMetadataFind =  fileRepository.findBySize(1900L);
		Long count = fileMetadataFind.spliterator().getExactSizeIfKnown();
        Assert.assertEquals(count, new Long(1L));
    }
	
	/**
	 * Tests the findByTypeAndSize method
	 */
	@Test
    public void findByTypeAndSizeTest() {
		Iterable<FileMetadata> fileMetadataFind =  fileRepository.findByTypeAndSize(fileType, 1900L);
		Long count = fileMetadataFind.spliterator().getExactSizeIfKnown();
        Assert.assertEquals(count, new Long(1L));
    }
	
	
	
	/**
	 * Tests the filesByTimePeriod method
	 */
	@Test
    public void findFilesByTimePeriodTest() {
		Date fromDate = FileUtil.getFromDate("600000");
		Date toDate = new Date();
		Iterable<FileMetadata> fileMetadataFind =  fileRepository.findFilesByTimePeriod(fromDate, toDate);
		Long count = fileMetadataFind.spliterator().getExactSizeIfKnown();
        Assert.assertEquals(count, new Long(1L));
    }	
}
