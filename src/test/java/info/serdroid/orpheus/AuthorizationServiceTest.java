package info.serdroid.orpheus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AuthorizationServiceTest {

	@Test (expected = java.lang.RuntimeException.class)
	public void validateAccessTokenShouldThrowExceptionWhenTokenNotExist() {
		AuthorizationService authorizationService = new AuthorizationService();
		authorizationService.validateAccessToken("non");
		assertThat(authorizationService).isNull();
	}

	public void validateAccessTokenShouldNotThrowExceptionWhenTokenExist() {
		AuthorizationService authorizationService = new AuthorizationService();
		TokenResponse tokenResponse = new TokenResponse();
    	tokenResponse.setAccessToken("access");
    	tokenResponse.setTokenType("bearer");
		authorizationService.validateAccessToken("access");
		assertThat(authorizationService).isNotNull();
	}
}
