package info.serdroid.orpheus;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import info.serdroid.orpheus.rest.TokenResponse;

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
		
	}
	
	public void addAccessToken(TokenResponse token) {
		accessTokens.put(token.getAccessToken(), token);
	}

	public TokenResponse getAccessToken(String accessToken) {
		return accessTokens.get(accessToken);
	}
}
