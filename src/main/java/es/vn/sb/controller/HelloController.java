package es.vn.sb.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> hello() {
		logger.info("START hello():");
		return new ResponseEntity<String>("HELLO from service-a - " + Utils.getPodName(), HttpStatus.OK);
	}

	@RequestMapping(path = "/direct", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> helloDirect(@RequestHeader("User-Agent") String userAgent, @RequestHeader(value = "sprint", required = false) String sprint) {
		logger.info("START helloDirect():");
        
		if (Constants.ERROR == 0) {
			return new ResponseEntity<String>(
					"HELLO from service-a - " + Utils.getPodName() + "\n" + helloService.helloDirect(),
					HttpStatus.OK);
		}

		if (Utils.getRandomInt() == 1) {
			return new ResponseEntity<String>("ERROR from service-a - " + Utils.getPodName(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(
					"HELLO from service-a - " + Utils.getPodName() + "\n" + helloService.helloDirect(),
					HttpStatus.OK);
		}
	}

	@RequestMapping(path = "/error/{error}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public HttpEntity<String> helloError(@PathVariable int error) {
		logger.info("START helloError():");
		Constants.ERROR = error;
		return new ResponseEntity<String>("ERROR value from service-a: " + error + " - " + Utils.getPodName(),
				HttpStatus.OK);
	}

}
