package info.serdroid.orpheus.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.serdroid.orpheus.AuthorizationRequest;
import info.serdroid.orpheus.AuthorizationService;
import info.serdroid.orpheus.RandomGenerator;

@ApplicationScoped
@Path("signup")
public class SignupEndpoint {
	private static final Logger logger = LoggerFactory.getLogger(SignupEndpoint.class);

	@Inject
	private AuthorizationService authorizationService;

	
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)    
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response signup(@FormParam("userid") String userid, 
			@FormParam("password") String password,
			@FormParam("code") String code ) {
    	// TODO authenticate user
    	AuthorizationRequest authorizationRequest = authorizationService.getAndRemoveAuthorizationRequest(code);
    	if( authorizationRequest == null) {
    		// TODO redirect to login page ( or do something else ??? )
    		return Response.serverError().build();
    	}
    	// generate new authorization code
    	String authorizationCode = RandomGenerator.generateRandomString();
    	authorizationService.addAuthorizationRequest(authorizationCode, authorizationRequest);

    	// redirect to redirect_uri with code & state ( if exists )
    	URI location;
		try {
			logger.debug("SignupEndpoint : redirecting to {}", authorizationRequest.getRedirectURI());
			location = new URI(authorizationRequest.getRedirectURI() + "?code=" + authorizationCode + 
					"&state=" + authorizationRequest.getState());
			ResponseBuilder respBuilder = Response.status(Status.FOUND).location(location);
	    	respBuilder.cacheControl(CacheControl.valueOf("no-store"));
	    	respBuilder.header("Pragma", "no-cache");
	    	return respBuilder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().build();
    }
}
