package info.serdroid.orpheus.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.impl.gradle.Gradle;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import info.serdroid.orpheus.URLQueryParser;

@RunWith(Arquillian.class)
public class EndpointTest {

	@ArquillianResource
	private URL contextPath;

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class);
		Archive<?>[] archives = Gradle.resolver().forProjectDirectory(".")
				.importCompileAndRuntime()
				.resolve()
				.asList(JavaArchive.class).toArray(new Archive[0]);
		war.addPackages(true, "info.serdroid.orpheus")
				.addAsWebInfResource("beans-test.xml", "beans.xml")
				.addAsLibraries(archives);
		System.out.println(war.toString(true));
		return war;
	}

	@Ignore
	@Test
	public void authorizeShouldReturnRedirectToLoginHtml() {
		Client client = ClientBuilder.newClient();
		Response response = client.target(contextPath + "rest/authorize?response_type=code&client_id=123&redirect_uri=http://localhost:8080/orpheus/rest/callback&state=login")
				.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
				.request().get();
		assertThat(response.getStatus()).isEqualTo(302);
		String location = response.getHeaderString("Location");
		assertThat(location).startsWith(contextPath + "login.html?code=");
	}

	@Test
	public void authorizationToResourceRequestCycle() throws MalformedURLException, UnsupportedEncodingException {
		// authorize request
		Client client = ClientBuilder.newClient();
		String redirectUri = contextPath + "rest/callback";
		String userId = "user-123";
		Response response = client.target(contextPath + "rest/authorize?response_type=code&client_id=123&state=login&redirect_uri=" + redirectUri)
				.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
				.request().get();
		assertThat(response.getStatus()).isEqualTo(302);
		String location = response.getHeaderString("Location");
		assertThat(location).startsWith(contextPath + "login.html?code=");
		
		// sign-in request request with temporary authorization code
		String authCode = location.substring( location.indexOf("=") + 1);
        MultivaluedMap<String, String> formParams = new MultivaluedHashMap<>();
        System.out.println("authCode = " + authCode);
        formParams.add("code", authCode);
        formParams.add("userid", userId);
        formParams.add("password", "password");
		response = client.target(contextPath + "rest/signup")
				.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
				.request().post(Entity.form(formParams));
		assertThat(response.getStatus()).isEqualTo(302);
		String pragma = response.getHeaderString("pragma");
		assertThat(pragma).isEqualTo("no-cache");
		String cacheControl = response.getHeaderString("Cache-Control");
		assertThat(cacheControl).isEqualTo("no-store");
		location = response.getHeaderString("Location");
		System.out.println("location=" + location);
		// token request request with authorization code

		Map<String, List<String>> queryParameters = URLQueryParser.getQueryParameters(location);
        formParams = new MultivaluedHashMap<>();
        formParams.add("grant_type", "authorization_code");
        formParams.add("code", queryParameters.get("code").get(0));
        formParams.add("client_id", "123");
        formParams.add("redirect_uri", redirectUri);
		response = client.target(contextPath + "rest/token")
				.request().post(Entity.form(formParams));

		// resource request with access token
		String accessToken = response.readEntity(String.class);
        formParams = new MultivaluedHashMap<>();
        formParams.add("client_id", "123");
        formParams.add("userid", userId);
		response = client.target(contextPath + "rest/resource")
				.request()
				.header("Authorization", "Bearer " + accessToken)
				.post(Entity.form(formParams));
		
		ResourceResponse resourceResponse = response.readEntity(ResourceResponse.class);
		assertThat(resourceResponse.getUserName()).isEqualTo("ali veli");
}

}
