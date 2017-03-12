package com.springboot.file.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import com.springboot.file.util.FileConstants;

/**
 * Config class to delete the files at the startup
 * @author Prasad Paravatha
 *
 */
@Component
public class AppListnerConfig implements ApplicationListener<ApplicationReadyEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppListnerConfig.class);

	/**
	 * This event is executed as late as conceivably possible to indicate that
	 * the application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		LOGGER.debug("--- springboot-rest-fileapp is ready ---");
		FileSystemUtils.deleteRecursively(new File(FileConstants.DIRECTORY));
	}
}