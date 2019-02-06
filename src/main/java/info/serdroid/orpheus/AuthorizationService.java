package info.serdroid.orpheus;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorizationService {
	Map<String, AuthorizationRequest> authRequests;
	
	public AuthorizationService() {
		authRequests = new HashMap<>();
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
}
