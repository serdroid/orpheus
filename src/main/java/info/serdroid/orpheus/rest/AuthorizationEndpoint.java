package info.serdroid.orpheus.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("authorize")
public class AuthorizationEndpoint {

	// response_type
	// client_id
	// redirect_uri
	// scope
	// state
	// code_challenge
	// code_challenge_method

    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response authorize() {
    	URI location;
		try {
			location = new URI("../login.html?response_type=code&state=before_authenticate");
			ResponseBuilder respBuilder = Response.status(Status.FOUND).location(location);
	    	return respBuilder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().build();
    }

	
}
