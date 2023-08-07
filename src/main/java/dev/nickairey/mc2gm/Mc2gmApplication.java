package dev.nickairey.mc2gm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import dev.nickairey.mc2gm.gm.GSuiteService;
import dev.nickairey.mc2gm.mc.MCService;


@SpringBootApplication
public class Mc2gmApplication {
	
	private static final Logger log = LoggerFactory.getLogger(Mc2gmApplication.class);
	
	@Autowired
	private MCService mcService;
	
	@Autowired
	private GSuiteService gsService;

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(Mc2gmApplication.class, args)));
	}
	
	@Bean
	@Profile("!test")
	public CommandLineRunner commandLineRunner() {
		return args -> {
			log.info("starting");
			
			List<String> mcAddresses = new ArrayList<String>();
			List<String> gmAddresses = new ArrayList<String>();
			
			mcService
				.getAddresses()
				.map(String::toLowerCase)
				.subscribe(mcAddresses::add);
			
			gsService
				.getGroupMembers()
				.map(String::toLowerCase)
				.subscribe(gmAddresses::add);
	
			gsService.updateGroupMembers(Differencer.getDifferences(mcAddresses, gmAddresses));
			
			log.info("finished");
		};
	}
}