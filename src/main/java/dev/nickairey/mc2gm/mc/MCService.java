package dev.nickairey.mc2gm.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.nickairey.mc2gm.mc.MCResponse.EmailInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class MCService {

	private static final Logger log = LoggerFactory.getLogger(MCService.class);
	private static final String logName = log.getName();
	
	@Autowired
	WebClient.Builder builder;
	
	public Flux<String> getAddresses() {
		
		Flux<String> mcAddressesFlux = 
			Flux.create(emitter -> {
				
				boolean finished = false;
				final int page_size = 100;				
				int retreived = 0;
				
				while (!finished) {
				
					final int offset = retreived;
					MCResponse response = builder
						.build()
						.get()
						.uri(uriBuilder -> uriBuilder
							.queryParam("status", "subscribed")
							.queryParam("fields", "members.email_address,total_items")
							.queryParam("offset", offset)
							.queryParam("count", page_size)
							.build()
						)
						.accept(MediaType.APPLICATION_JSON)
						.retrieve()
						.bodyToMono(MCResponse.class)
						.block();
					
					for (EmailInfo e : response.members) {
						emitter.next(e.email_address);
						++retreived;
					}
					
					finished = (retreived >= response.total_items);
				}
				
				emitter.complete();
				
			}, FluxSink.OverflowStrategy.BUFFER);
		
		return mcAddressesFlux.log(logName);
	}
}
