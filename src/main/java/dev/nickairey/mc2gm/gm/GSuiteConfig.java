package dev.nickairey.mc2gm.gm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("!test")
@PropertySource(value = "classpath:gs-credentials.yml")
public class GSuiteConfig {

	@Value("${credentialsFile}")
	public String credentialsFileName;
	
	@Value("${groupKey}")
	public String groupKey;
    
	@Bean
	InputStreamReader credentials() throws Exception {
		FileInputStream credentialsInputStream = new FileInputStream(credentialsFileName);
        return new InputStreamReader(credentialsInputStream);
	}
	
	@Bean
	File credentialsDirectory() {
		File credentialsFile = new File(credentialsFileName);
		String credentialsFileDir = credentialsFile.getParent();
		return new java.io.File(credentialsFileDir);
	};
	
	@Bean
	String groupKey() {
		return groupKey;
	}
}
