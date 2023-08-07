package dev.nickairey.mc2gm;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;


public class StreamTests {


	@Test
	public void testLowerCase() {
		
		Flux.just("ABC", "Def")
		.map(String::toLowerCase)
		.log()
		.subscribe();
		
	}
	
}
