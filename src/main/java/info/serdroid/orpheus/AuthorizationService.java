package info.serdroid.orpheus;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorizationService {
	Map<String, AuthorizationRequest> authRequests;
	Map<String, TokenResponse> accessTokens;
	
	public AuthorizationService() {
		authRequests = new HashMap<>();
		accessTokens = new HashMap<>();
	}

	public void addAuthorizationRequest(String code, AuthorizationRequest authorizationRequest) {
		authRequests.put(code, authorizationRequest);
	}

	public AuthorizationRequest getAuthorizationRequest(String code) {
		return authRequests.get(code);
	}

	public AuthorizationRequest getAndRemoveAuthorizationRequest(String code) {
		return authRequests.remove(code);
	}
	
	public void validateTokenRequest(TokenRequest tokenRequest) {
		AuthorizationRequest authorizationRequest = getAndRemoveAuthorizationRequest(tokenRequest.getCode());
		if (authorizationRequest == null) {
			throw new RuntimeException("wrong authorization code");
		}
		if ( ! authorizationRequest.getClientId().equals(tokenRequest.getClientId()) ) {
			throw new RuntimeException("wrong client id");
		}
		if ( ! authorizationRequest.getRedirectURI().equals(tokenRequest.getRedirectURI()) ) {
			throw new RuntimeException("wrong redirect uri");
		}
	}
	
	public TokenResponse generateTokenResponse() { 
		String access = RandomGenerator.generateRandomString();
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(access);
		tokenResponse.setTokenType("bearer");
		return tokenResponse;
	}
	
	public void addAccessToken(TokenResponse token) {
		accessTokens.put(token.getAccessToken(), token);
	}

	public TokenResponse getAccessToken(String accessToken) {
		return accessTokens.get(accessToken);
	}

	public void validateAccessToken(String accessToken) {
		TokenResponse tokenResponse = accessTokens.get(accessToken);
		if ( null == tokenResponse ) {
			throw new RuntimeException("Invalid Access Token");
		}
	}

}
