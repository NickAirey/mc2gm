package dev.nickairey.mc2gm;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class MockGSuiteConfig {

	@Bean
	InputStreamReader credentials() throws Exception {
		return new InputStreamReader(InputStream.nullInputStream());
	}

	@Bean
	File credentialsDirectory() {
		return new File("");
	};
}