package dev.nickairey.mc2gm.mc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("!test")
@PropertySource(value = "classpath:mc-credentials.yml")
public class MCWebClientBuilderFactory {
	
	@Value("${baseURL}")
	public String baseURL;
	 
	@Value("${authHdr}")
	public String authHdr;
	
	@Bean
	public WebClient.Builder webClientBuilder() {
		
		WebClient.Builder builder = WebClient
				.builder()
				.baseUrl(baseURL)
				.defaultHeader(HttpHeaders.AUTHORIZATION, authHdr);
		
		return builder;
	};
}
