package info.serdroid.orpheus.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import info.serdroid.orpheus.AuthorizationRequest;
import info.serdroid.orpheus.AuthorizationService;
import info.serdroid.orpheus.RandomGenerator;

@ApplicationScoped
@Path("authorize")
public class AuthorizationEndpoint {

	@Inject
	private AuthorizationService authorizationService;
	
	// response_type
	// client_id
	// redirect_uri
	// scope
	// state
	// code_challenge
	// code_challenge_method

    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)    
    public Response authorize(@QueryParam("response_type") String response_type, 
	    	@QueryParam("client_id") String client_id,
			@QueryParam("redirect_uri") String redirect_uri,
			@QueryParam("scope") String scope,
			@QueryParam("state") String state,
			@QueryParam("code_challenge") String code_challenge,
			@QueryParam("code_challenge_method") String code_challenge_method ) {
    	AuthorizationRequest authorizationRequest = new AuthorizationRequest.Builder()
    			.setResponseType(response_type)
    			.setClientId(client_id)
    			.setRedirectURI(redirect_uri)
    			.setScope(scope)
    			.setState(state)
    			.setCodeChallenge(code_challenge)
    			.setCodeChallengeMethod(code_challenge_method)
    			.build();
    	String authorizationCode = RandomGenerator.generateRandomString();
    	authorizationService.addAuthorizationRequest(authorizationCode, authorizationRequest);
    	URI location;
		try {
			System.out.println("AuthorizationEndpoint : redirecting to login.html");
			location = new URI("../login.html?code=" + authorizationCode);
			ResponseBuilder respBuilder = Response.status(Status.FOUND).location(location);
	    	return respBuilder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().build();
    }

}
