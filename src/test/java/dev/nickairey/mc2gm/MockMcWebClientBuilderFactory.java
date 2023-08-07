package dev.nickairey.mc2gm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockWebServer;

@Configuration
@Profile("test")
public class MockMcWebClientBuilderFactory {
	
	@Autowired
	MockWebServer mockWebServer;
	
	@Bean
	public WebClient.Builder webClientBuilder() {
		
		WebClient.Builder builder = WebClient
				.builder()
				.baseUrl("http://localhost:"+mockWebServer.getPort());
		
		return builder;
	};
}
