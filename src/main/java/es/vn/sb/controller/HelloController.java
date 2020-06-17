package es.vn.sb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.vn.sb.service.HelloService;
import es.vn.sb.utils.Constants;
import es.vn.sb.utils.Utils;

@RestController
@RequestMapping("/hello")
public class HelloController {

	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	HelloService helloService;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${spring.application.version}")
	private String appVersion;
	
	@Value("#{systemEnvironment['VERSION']}")
	String serviceVersion;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> hello() {
		logger.info("START hello():");
		return new ResponseEntity<String>(String.format("HELLO from '%s' in pod '%s', pomversion '%s' and serviceversion '%s'\n", appName, Utils.getPodName(), appVersion, serviceVersion),
				HttpStatus.OK);
	}

	@RequestMapping(path = "/version", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> version(
			@RequestHeader(value = "sprint", required = false, defaultValue = "0") String sprint) {
		logger.info("START hello(): sprint: " + sprint);

		return new ResponseEntity<String>(
				String.format("HELLO from '%s' in sprint: '%s', version: '%s' in pod: '%s', pomversion '%s' and serviceversion '%s'", appName, sprint,
						appVersion, Utils.getPodName(), appVersion, serviceVersion),
				HttpStatus.OK);
	}

	@RequestMapping(path = "/direct", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> helloDirect() {
		logger.info("START helloDirect():");
		
		if (Constants.ERROR == 0) {
			return new ResponseEntity<String>(String.format("HELLO from '%s', version '%s' in pod '%s', pomversion '%s' and serviceversion '%s'\n%s", appName,
					appVersion, Utils.getPodName(), appVersion, serviceVersion, helloService.helloDirect()),
					HttpStatus.OK);
		}

		if (Utils.getRandomInt() == 1) {
			return new ResponseEntity<String>(String.format("HELLO from '%s', version '%s' in pod '%s', pomversion '%s' and serviceversion '%s'", appName,
					appVersion, Utils.getPodName(), appVersion, serviceVersion),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(String.format("HELLO from '%s', version '%s' in pod '%s', pomversion '%s' and serviceversion '%s'\n'%s'", appName,
					appVersion, Utils.getPodName(), appVersion, serviceVersion, helloService.helloDirect()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(path = "/error", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> error() {
		logger.info("START error():");
		
		if (Constants.ERROR == 0) {
			return new ResponseEntity<String>(String.format("ERROR value from '%s', version '%s' in pod '%s' and error '%d', pomversion '%s' and serviceversion '%s'", appName,
					appVersion, Utils.getPodName(), Constants.ERROR, appVersion, serviceVersion), HttpStatus.OK);
		} 
		return new ResponseEntity<String>(String.format("ERROR value from '%s', version '%s' in pod '%s' and error '%d', pomversion '%s' and serviceversion '%s'", appName,
				appVersion, Utils.getPodName(), Constants.ERROR, appVersion, serviceVersion),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(path = "/error/{error}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> helloError(@PathVariable int error) {
		logger.info("START helloError():");
		Constants.ERROR = error;
		
		return new ResponseEntity<String>(String.format("ERROR value from '%s', version '%s' in pod: '%s', error '%d', pomversion '%s' and serviceversion '%s'", appName,
				appVersion, Utils.getPodName(), error, appVersion, serviceVersion),
				HttpStatus.OK);
	}

}
