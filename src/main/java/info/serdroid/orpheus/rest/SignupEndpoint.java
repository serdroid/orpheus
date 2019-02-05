package info.serdroid.orpheus.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import info.serdroid.orpheus.RandomGenerator;

@ApplicationScoped
@Path("signup")
public class SignupEndpoint {

	
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)    
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response signup(@FormParam("userid") String userid, 
			@FormParam("password") String password,
			@FormParam("redirect_uri") String redirect_uri,
			@FormParam("state") String state ) {
    	// authenticate user
    	// generate authorization code
    	String code = RandomGenerator.generateRandomString();
    	// redirect to redirect_uri with code & state ( if exists )
    	URI location;
		try {
			System.out.println("SignupEndpoint : redirecting to " + redirect_uri);
			location = new URI(redirect_uri + "?code=" + code + "&state=" + state);
			ResponseBuilder respBuilder = Response.status(Status.FOUND).location(location);
	    	return respBuilder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().build();
    }
}
