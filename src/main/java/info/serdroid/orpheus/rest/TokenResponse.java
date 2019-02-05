package info.serdroid.orpheus.rest;

public class TokenResponse {
	private String accessToken;
	private String tokenType;
	private int expires;
	private String refreshToken;
	private String scope;
	public String getAccessToken() {
		return accessToken;
	}
	void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	String getTokenType() {
		return tokenType;
	}
	void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	int getExpires() {
		return expires;
	}
	void setExpires(int expires) {
		this.expires = expires;
	}
	String getRefreshToken() {
		return refreshToken;
	}
	void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	String getScope() {
		return scope;
	}
	void setScope(String scope) {
		this.scope = scope;
	}
	
}
