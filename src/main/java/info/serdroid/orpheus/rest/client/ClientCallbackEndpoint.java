package info.serdroid.orpheus.rest.client;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.serdroid.orpheus.rest.ResourceResponse;
import info.serdroid.orpheus.rest.SignupEndpoint;

@ApplicationScoped
@Path("callback")
public class ClientCallbackEndpoint {
	private static final Logger logger = LoggerFactory.getLogger(ClientCallbackEndpoint.class);

    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)    
    public Response authorize(@QueryParam("code") String code, 
			@QueryParam("state") String state ) {
    	// make a token request with authorization code
    	// request parameters
    	/*
    			grant_type=authorization_code  // - Required
    			&code={Authorization Code}     // - Required
    			&redirect_uri={Redirect URI}   // - Required if the authorization
    			                               //   request included 'redirect_uri'.
    			&code_verifier={Verifier}      // - Required if the authorization
    			                               //   request included
    			                               //   'code_challenge'.
    	*/
        
    	Client client = ClientBuilder.newClient();
        MultivaluedMap<String, String> formParams = new MultivaluedHashMap<>();
        formParams.add("grant_type", "authorization_code");
        formParams.add("code", code);
//        formParams.add("client_id", ""); // ??? is client id required
        formParams.add("redirect_uri", "http://localhost:8081/orpheus/rest/callback");
        Response response = client.target("http://localhost:8080/orpheus/rest/token").request().post(Entity.form(formParams));
        
        String accessToken = response.readEntity(String.class);
    	// token response
    	/*
		{
		  "access_token": "{Access Token}",    // - Always included
		  "token_type": "{Token Type}",        // - Always included
		  "expires_in": {Lifetime In Seconds}, // - Optional
		  "refresh_token": "{Refresh Token}",  // - Optional
		  "scope": "{Scopes}"                  // - Mandatory if the granted
		                                       //   scopes differ from the
		                                       //   requested ones.
		}    	
    	*/
    	// make a resource query request from resource server
        String userId = "user-123";
    	client = ClientBuilder.newClient();
        formParams = new MultivaluedHashMap<>();
        formParams.add("access_token", accessToken);
        formParams.add("userid", userId);
        logger.debug("requesting resource with token = {}", accessToken);
        response = client.target("http://localhost:8080/orpheus/rest/resource").request().post(Entity.form(formParams));
    	ResourceResponse resourceResponse = response.readEntity(ResourceResponse.class);
    	// redirect to main.html with obtained resource data
    	URI location;
		try {
	    	String params = "?userid=" + resourceResponse.getUserId() + "&username=" + URLEncoder.encode(resourceResponse.getUserName(), "utf-8");
			location = new URI("/orpheus/main.html" + params);
			ResponseBuilder respBuilder = Response.status(Status.FOUND).location(location);
	    	return respBuilder.build();
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().build();
    }

}
