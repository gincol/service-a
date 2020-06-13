package es.vn.sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private RestTemplate myRestTemplate;
    
    @Value("${service-b.url}")
    String url;

	public String helloDirect() {
		return myRestTemplate.getForEntity(url, String.class).getBody();
	}
    
}   
