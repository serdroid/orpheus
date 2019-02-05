package info.serdroid.orpheus.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("resource")
public class ResourceEndpoint {

    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)    
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public ResourceResponse accessToken(@FormParam("access_token") String access_token, 
	    	@FormParam("client_id") String client_id,
			@FormParam("userid") String userid) {
    	// check input
    	ResourceResponse response = new ResourceResponse();
    	response.setUserId(userid);
    	response.setUserName("ali veli");
    	response.setEmail("ali.veli@example-resource.com");
    	return response;
    }
}