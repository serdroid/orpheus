package info.serdroid.orpheus;

import org.junit.Test;

import info.serdroid.orpheus.AuthorizationRequest;
import info.serdroid.orpheus.AuthorizationRequest.Builder;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationRequestTest {

	@Test (expected = java.lang.RuntimeException.class)
	public void responseTypeShouldNotBeNull() {
		Builder builder = new AuthorizationRequest.Builder();
		AuthorizationRequest builded = builder.setClientId("1234567890").build();
		AuthorizationRequest expected = new AuthorizationRequest();
		expected.setClientId("1234567890");
		assertThat(builded).isEqualTo(expected);
	}

	@Test (expected = java.lang.RuntimeException.class)
	public void clientIdShouldNotBeNull() {
		Builder builder = new AuthorizationRequest.Builder();
		AuthorizationRequest builded = builder.setResponseType("code").build();
		AuthorizationRequest expected = new AuthorizationRequest();
		expected.setResponseType("code");
		assertThat(builded).isEqualTo(expected);
	}

	@Test
	public void mandatoryFieldsShouldBeSet() {
		Builder builder = new AuthorizationRequest.Builder();
		AuthorizationRequest builded = builder.setResponseType("code").setClientId("1234567890").build();
		AuthorizationRequest expected = new AuthorizationRequest();
		expected.setResponseType("code");
		expected.setClientId("1234567890");
		assertThat(builded).isEqualTo(expected);
	}

	@Test (expected = java.lang.RuntimeException.class)
	public void responseTypeShouldBeCode() {
		Builder builder = new AuthorizationRequest.Builder();
		AuthorizationRequest builded = builder.setResponseType("ode").setClientId("1234567890").build();
		AuthorizationRequest expected = new AuthorizationRequest();
		expected.setResponseType("ode");
		expected.setClientId("1234567890");
		assertThat(builded).isEqualTo(expected);
	}

	@Test
	public void optionalFieldShouldBeGetAsExpected() {
		Builder builder = new AuthorizationRequest.Builder();
		AuthorizationRequest builded = builder.setResponseType("code").setClientId("1234567890")
				.setRedirectURI("../login.html")
				.build();
		AuthorizationRequest expected = new AuthorizationRequest();
		expected.setResponseType("code");
		expected.setClientId("1234567890");
		expected.setRedirectURI("../login.html");
		assertThat(builded).isEqualTo(expected);
	}

	@Test
	public void settingNullShouldNotChange() {
		Builder builder = new AuthorizationRequest.Builder();
		AuthorizationRequest builded = builder.setResponseType("code").setClientId("1234567890")
				.setRedirectURI(null)
				.build();
		AuthorizationRequest expected = new AuthorizationRequest();
		expected.setResponseType("code");
		expected.setClientId("1234567890");
		assertThat(builded).isEqualTo(expected);
	}

	@Test
	public void setSomeOptionalParametersShouldReturnSetParametersURIString() {
		Builder builder = new AuthorizationRequest.Builder();
		AuthorizationRequest builded = builder.setResponseType("code").setClientId("1234567890")
				.setRedirectURI("www.example.com")
				.setScope(null)
				.setState("current")
				.build();
		AuthorizationRequest expected = new AuthorizationRequest();
		expected.setResponseType("code");
		expected.setClientId("1234567890");
		expected.setRedirectURI("www.example.com");
		expected.setState("current");
		assertThat(builded).isEqualTo(expected);
		assertThat(builded.toURIString()).isEqualTo("?response_type=code&client_id=1234567890&redirect_uri=www.example.com&state=current");
	}

}
