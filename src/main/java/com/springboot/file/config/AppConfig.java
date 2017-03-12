package com.springboot.file.config;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.springboot.file.domain.FileMetadata;
import com.springboot.file.repository.FileRepository;
import com.springboot.file.service.impl.EmailServiceImpl;
import com.springboot.file.util.FileUtil;

/**
 * Config adapter to customize request and response
 * @author Prasad Paravatha 
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig extends WebMvcConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	@Resource
	FileRepository repository;

	@Value("${email.send.frequency}")
	private String frequency;

	@Autowired
	private EmailServiceImpl emailService;

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
					configurer.ignoreUnknownPathExtensions(false)
							  .ignoreAcceptHeader(false)
							  .favorPathExtension(false)
							  .favorParameter(true)
							  .defaultContentType(MediaType.APPLICATION_JSON);
	}

	
	@Scheduled(initialDelayString = "${email.send.frequency}", fixedRateString = "${email.send.frequency}")
	public void pollerToSendEmail() throws Exception {
		Date fromDate = FileUtil.getFromDate(frequency);
		Date toDate = new Date();

		Iterable<FileMetadata> fileIterable = repository.findFilesByTimePeriod(fromDate, toDate);
		Long count = fileIterable.spliterator().getExactSizeIfKnown();
		if (count > 0) {
			String minMessage = Long.valueOf(frequency)/60000 + " minutes";
			LOGGER.debug("Sending email for the files uploaded/modfied in the past " + minMessage);
			String message = "FYI : " + count.toString() + " files have been uploaded/modfied in the past " + minMessage;
			emailService.sendEmail(message);
		}
	}

}
