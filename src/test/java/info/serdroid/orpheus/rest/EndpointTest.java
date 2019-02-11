package info.serdroid.orpheus.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
import org.junit.Test;
import org.junit.runner.RunWith;

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

}
