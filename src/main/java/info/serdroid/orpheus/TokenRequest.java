package info.serdroid.orpheus;

import java.util.Objects;

public class TokenRequest {
	private String grantType;
	private String clientId;
	private String redirectURI;
	private String code;
	private String codeVerifier;

	TokenRequest() {}

	public String getGrantType() {
		return grantType;
	}
	public String getClientId() {
		return clientId;
	}
	public String getRedirectURI() {
		return redirectURI;
	}
	public String getCode() {
		return code;
	}
	public String getCodeVerifier() {
		return codeVerifier;
	}

	public String toURIString() {
		StringBuilder buffer = new StringBuilder(128);
		addToURIString("?grant_type=", grantType, buffer);
		addToURIString("&client_id=", clientId, buffer);
		addToURIString("&redirect_uri=", redirectURI, buffer);
		addToURIString("&code=", code, buffer);
		addToURIString("&code_verifier=", codeVerifier, buffer);
		return buffer.toString();
	}

	private void addToURIString(String prefix, String field, StringBuilder buffer) {
		if (field != null) {
			buffer.append(prefix).append(field);
		}
	}

	void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	void setClientId(String clientId) {
		this.clientId = clientId;
	}
	void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}
	void setCode(String code) {
		this.code = code;
	}
	void setCodeChallenge(String codeChallenge) {
		this.codeVerifier = codeChallenge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((codeVerifier == null) ? 0 : codeVerifier.hashCode());
		result = prime * result + ((redirectURI == null) ? 0 : redirectURI.hashCode());
		result = prime * result + ((grantType == null) ? 0 : grantType.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		TokenRequest other = (TokenRequest) obj;
		return 
		Objects.equals(grantType, other.grantType) &&
		Objects.equals(clientId, other.clientId) &&
		Objects.equals(redirectURI, other.redirectURI) &&
		Objects.equals(code, other.code) &&
		Objects.equals(codeVerifier, other.codeVerifier)
		;
	}

	public static class Builder {
		private TokenRequest tokenRequest = new TokenRequest();

		public Builder setGrantType(String grantType) {
			tokenRequest.grantType = grantType;
			return this;
		}

		public Builder setClientId(String clientId) {
			tokenRequest.clientId = clientId;
			return this;
		}

		public Builder setRedirectURI(String redirectURI) {
			tokenRequest.redirectURI = redirectURI;
			return this;
		}

		public Builder setCode(String code) {
			tokenRequest.code = code;
			return this;
		}

		public Builder setCodeVerifier(String codeVerifier) {
			tokenRequest.codeVerifier = codeVerifier;
			return this;
		}

		private void check() {
			if (tokenRequest.grantType == null || tokenRequest.code == null) {
				throw new RuntimeException("Mandatory fields could not be null");
			}
			
			if ( ! "authorization_code".equals(tokenRequest.grantType) ) {
				throw new RuntimeException("grant_type value should be 'authorization_code'");
			}
			
		}

		public TokenRequest build() {
			check();
			return tokenRequest;
		}
	}
}
