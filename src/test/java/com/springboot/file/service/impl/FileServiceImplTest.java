package com.springboot.file.service.impl;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springboot.file.domain.FileMetadata;
import com.springboot.file.repository.FileRepository;

/**
 * Unit test for FileService
 * @author Prasad Paravatha
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class FileServiceImplTest {
	@InjectMocks
	private FileServiceImpl fileService;
	
	@Mock
	FileRepository repository;
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);		
	}


	@Test
	public void testSaveFile() throws Exception {
		MockMultipartFile uploadedFile = new MockMultipartFile("file", "file.txt", "plain/text", "Sample Text".getBytes());
		FileMetadata fileMetadata = Mockito.mock(FileMetadata.class);
		when(fileService.saveFile(uploadedFile)).thenReturn(fileMetadata);
		
		fileService.saveFile(uploadedFile);
		Assert.assertNotNull(fileMetadata);
	}
	
	@Test
	public void testGetFileMetaData() throws Exception {
		FileMetadata fileMetadata = Mockito.mock(FileMetadata.class);
		String fileName = "file.txt";
		when(fileService.getFileMetaData(fileName)).thenReturn(fileMetadata);
		Assert.assertNotNull(fileMetadata);
	}
	
	@Test
	public void testGetFileIDs() throws Exception {
		List<UUID> fileIDs = new ArrayList<UUID>();
		fileIDs.add(UUID.randomUUID());
		fileIDs.add(UUID.randomUUID());
		when(fileService.getFileIDs(null)).thenReturn(fileIDs);
		Assert.assertNotNull(fileIDs);
	}
	
	
	@Test
	public void testGetFileToBeDownloaded() throws Exception {
		//Setup 
		File file = folder.newFile("file.txt");
		writeToFile("Sample Text", file.getAbsolutePath());
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setFilePath(file.getAbsolutePath());
		
		//Call the service method and verify result
		ByteArrayResource resource = (ByteArrayResource) fileService.getFileToBeDownloaded(fileMetadata);
		Assert.assertNotNull(resource);
	}
	
	public static void writeToFile(String text, String targetFilePath) throws IOException {
	    Path targetPath = Paths.get(targetFilePath);
	    byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
	    Files.write(targetPath, bytes, StandardOpenOption.CREATE);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
