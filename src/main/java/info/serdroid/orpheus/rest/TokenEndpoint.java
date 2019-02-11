package info.serdroid.orpheus.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.serdroid.orpheus.AuthorizationService;
import info.serdroid.orpheus.TokenRequest;
import info.serdroid.orpheus.TokenResponse;

@ApplicationScoped
@Path("token")
public class TokenEndpoint {
	private static final Logger logger = LoggerFactory.getLogger(TokenEndpoint.class);

	@Inject
	private AuthorizationService authorizationService;
	
	// request parameters
	/*
			grant_type=authorization_code  // - Required
			&code={Authorization Code}     // - Required
			&client_id={Client ID}		   // - Required
			&redirect_uri={Redirect URI}   // - Required if the authorization
			                               //   request included 'redirect_uri'.
			&code_verifier={Verifier}      // - Required if the authorization
			                               //   request included
			                               //   'code_challenge'.
	*/
	
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)    
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String accessToken(@FormParam("grant_type") String grant_type, 
			@FormParam("code") String code,
	    	@FormParam("client_id") String client_id,
			@FormParam("redirect_uri") String redirect_uri,
			@FormParam("code_verifier") String code_verifier ) {
    	// check input
    	TokenRequest tokenRequest = new TokenRequest.Builder().setGrantType(grant_type)
    	.setClientId(client_id)
    	.setCode(code)
    	.setCodeVerifier(code_verifier)
    	.setRedirectURI(redirect_uri).build();
    	authorizationService.validateTokenRequest(tokenRequest);

    	// generate & return token response
    	TokenResponse tokenResponse = authorizationService.generateTokenResponse();
    	logger.debug("TokenEndpoint : returning access token {}", tokenResponse.getAccessToken());
    	authorizationService.addAccessToken(tokenResponse);
    	return tokenResponse.getAccessToken();
    }
}
