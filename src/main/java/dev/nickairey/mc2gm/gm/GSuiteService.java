package dev.nickairey.mc2gm.gm;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Member;
import com.google.api.services.admin.directory.model.Members;

import dev.nickairey.mc2gm.Differencer.DiffType;
import dev.nickairey.mc2gm.Differencer.StringDiff;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class GSuiteService {

	private static final Logger log = LoggerFactory.getLogger(GSuiteService.class);
	private static final String logName = log.getName();

	@Autowired
	private Directory service;
	
	@Autowired
	private String groupKey;

    public Flux<String> getGroupMembers() {

    	Flux<String> gsAddressesFlux = 
    		Flux.create(emitter -> {

				boolean finished = false;
				String nextPageToken = null;
	
				while (!finished) {
					try {
						Members res = service.members()
							.list(groupKey)
							.setPageToken(nextPageToken)
							.execute();
	
						for (Member m: res.getMembers()) {
//							log.info(m.toPrettyString());
							emitter.next(m.getEmail());
						}
	
				        nextPageToken = res.getNextPageToken();
				        finished = nextPageToken == null;
			
				        log.info("next page token: "+nextPageToken);
			
					} catch (IOException e) {
						emitter.error(e);
						finished = true;
					}
				}
	
				emitter.complete();
	
			}, FluxSink.OverflowStrategy.BUFFER);
    	
    	return gsAddressesFlux.log(logName);
    }

    
    public void updateGroupMembers(Flux<StringDiff> differences) {
    	
    	differences.doOnNext(e -> {
    		try {
    			if (DiffType.AOnly.equals(e.diffType)) {
    				
    				log.info("inserting: >"+e.value+"<");
    				
    				Member n = service.members()
    					.insert(groupKey, 
	    				    new Member()
	    					  .setEmail(e.value)
	    					  .setRole("MEMBER")
    					)
    					.execute();
    				
    				log.info("inserted: "+n.toPrettyString());
    			}
    			else if (DiffType.BOnly.equals(e.diffType)) {
    				log.info("deleting:  >"+e.value+"<");
    				
    				service.members()
    				  .delete(groupKey, e.value)
    				  .execute();
    				
    				log.info("deleted:   >"+e.value+"<");
    			}
    		} catch (Exception ex) {
    			log.error(ex.getMessage());
    		}
    	}).subscribe();
    }
}