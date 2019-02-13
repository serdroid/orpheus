package info.serdroid.orpheus.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.serdroid.orpheus.AuthorizationService;

@ApplicationScoped
@Path("resource")
public class ResourceEndpoint {
	private static final Logger logger = LoggerFactory.getLogger(ResourceEndpoint.class);
	
	@Inject
	private AuthorizationService authorizationService;
	
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)    
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public ResourceResponse accessToken(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,
	    	@FormParam("client_id") String client_id,
			@FormParam("userid") String userid) {
    	// check input
    	if(authorizationHeader == null) {
    		throw new RuntimeException("should return 401");
    	}
    	String access_token = authorizationHeader.substring(7);
    	logger.debug("requesting resource with access token {} ", access_token);
    	authorizationService.validateAccessToken(access_token);
    	ResourceResponse response = new ResourceResponse();
    	response.setUserId(userid);
    	response.setUserName("ali veli");
    	response.setEmail("ali.veli@example-resource.com");
    	return response;
    }
}
