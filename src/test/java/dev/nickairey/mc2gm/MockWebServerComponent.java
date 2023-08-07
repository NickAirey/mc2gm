package dev.nickairey.mc2gm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import okhttp3.mockwebserver.MockWebServer;

@Configuration
@Profile("test")
public class MockWebServerComponent {
	
	private MockWebServer mockWebServer = new MockWebServer();
	
	@Bean(initMethod = "start", destroyMethod = "shutdown")
	MockWebServer mockWebServer() {
		return mockWebServer;
	}
}
