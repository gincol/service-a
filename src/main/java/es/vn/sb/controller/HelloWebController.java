package es.vn.sb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.vn.sb.utils.Utils;

@Controller
public class HelloWebController {

	private static final Logger logger = LoggerFactory.getLogger(HelloWebController.class);
	
	@Value("${spring.application.name}")
	private String appName;

	@Value("${spring.application.version}")
	private String appVersion;
	
	@RequestMapping(path = "/headers", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello(
			@RequestHeader(value = "sprint", required = false, defaultValue = "0") String sprint, Model model) {
		logger.info("START hello():");
		model.addAttribute("message", String.format("HELLO from '%s' in sprint: '%s', version: '%s' and pod: '%s'", appName, sprint,
				appVersion, Utils.getPodName()));
		return "index";
	}

}
