package dev.nickairey.mc2gm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import dev.nickairey.mc2gm.mc.MCService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest
@ActiveProfiles("test")
public class MCServiceTests {

	@Autowired
	private MCService mcService;	
	
	@Autowired
	private MockWebServer mockWebServer; 

	
	@Test
	public void test1() {
		
		String response = "{ \"members\": [ { \"email_address\": \"abc@de.fg\" } , { \"email_address\": \"def@gh.ij\" } ], \"total_items\":2 }";
		
		mockWebServer.enqueue(new MockResponse()
			.setBody(response)
			.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			.setResponseCode(200)
		);
		
		List<String> mcAddresses = new ArrayList<String>();
		
		mcService.getAddresses()
			.log()
			.subscribe(mcAddresses::add);

		assertThat(mcAddresses).hasSize(2);
	}
}
