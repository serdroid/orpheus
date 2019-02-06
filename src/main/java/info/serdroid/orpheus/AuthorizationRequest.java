package info.serdroid.orpheus;

import java.util.Objects;

public class AuthorizationRequest {
	private String responseType;
	private String clientId;
	private String redirectURI;
	private String scope;
	private String state;
	private String codeChallenge;
	private String codeChallengeMethod;

	AuthorizationRequest() {}

	public String getResponseType() {
		return responseType;
	}
	public String getClientId() {
		return clientId;
	}
	public String getRedirectURI() {
		return redirectURI;
	}
	public String getScope() {
		return scope;
	}
	public String getState() {
		return state;
	}
	public String getCodeChallenge() {
		return codeChallenge;
	}
	public String getCodeChallengeMethod() {
		return codeChallengeMethod;
	}

	public String toURIString() {
		StringBuilder buffer = new StringBuilder(128);
		addToURIString("?response_type=", responseType, buffer);
		addToURIString("&client_id=", clientId, buffer);
		addToURIString("&redirect_uri=", redirectURI, buffer);
		addToURIString("&scope=", scope, buffer);
		addToURIString("&state=", state, buffer);
		addToURIString("&code_challenge=", codeChallenge, buffer);
		addToURIString("&code_challenge_method=", codeChallengeMethod, buffer);
		return buffer.toString();
	}

	private void addToURIString(String prefix, String field, StringBuilder buffer) {
		if (field != null) {
			buffer.append(prefix).append(field);
		}
	}

	void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	void setClientId(String clientId) {
		this.clientId = clientId;
	}
	void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}
	void setScope(String scope) {
		this.scope = scope;
	}
	void setState(String state) {
		this.state = state;
	}
	void setCodeChallenge(String codeChallenge) {
		this.codeChallenge = codeChallenge;
	}
	void setCodeChallengeMethod(String codeChallengeMethod) {
		this.codeChallengeMethod = codeChallengeMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((codeChallenge == null) ? 0 : codeChallenge.hashCode());
		result = prime * result + ((codeChallengeMethod == null) ? 0 : codeChallengeMethod.hashCode());
		result = prime * result + ((redirectURI == null) ? 0 : redirectURI.hashCode());
		result = prime * result + ((responseType == null) ? 0 : responseType.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorizationRequest other = (AuthorizationRequest) obj;
		return 
		Objects.equals(responseType, other.responseType) &&
		Objects.equals(clientId, other.clientId) &&
		Objects.equals(redirectURI, other.redirectURI) &&
		Objects.equals(scope, other.scope) &&
		Objects.equals(state, other.state) &&
		Objects.equals(codeChallenge, other.codeChallenge) &&
		Objects.equals(codeChallengeMethod, other.codeChallengeMethod);
	}

	public static class Builder {
		private AuthorizationRequest authorizationRequest = new AuthorizationRequest();

		public Builder setResponseType(String responseType) {
			authorizationRequest.responseType = responseType;
			return this;
		}

		public Builder setClientId(String clientId) {
			authorizationRequest.clientId = clientId;
			return this;
		}

		public Builder setRedirectURI(String redirectURI) {
			authorizationRequest.redirectURI = redirectURI;
			return this;
		}

		public Builder setScope(String scope) {
			authorizationRequest.scope = scope;
			return this;
		}

		public Builder setState(String state) {
			authorizationRequest.state = state;
			return this;
		}

		public Builder setCodeChallenge(String codeChallenge) {
			authorizationRequest.codeChallenge = codeChallenge;
			return this;
		}

		public Builder setCodeChallengeMethod(String codeChallengeMethod) {
			authorizationRequest.codeChallengeMethod = codeChallengeMethod;
			return this;
		}

		private void check() {
			if (authorizationRequest.responseType == null || authorizationRequest.clientId == null) {
				throw new RuntimeException("Mandatory fields could not be null");
			}
			
			if ( ! "code".equals(authorizationRequest.responseType) ) {
				throw new RuntimeException("response_type value should be 'code'");
			}
			
		}

		public AuthorizationRequest build() {
			check();
			return authorizationRequest;
		}
	}
}
