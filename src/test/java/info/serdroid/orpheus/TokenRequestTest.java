package info.serdroid.orpheus;

import org.junit.Test;

import info.serdroid.orpheus.TokenRequest.Builder;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenRequestTest {

	@Test (expected = java.lang.RuntimeException.class)
	public void grantTypeShouldNotBeNull() {
		Builder builder = new TokenRequest.Builder();
		TokenRequest builded = builder.setCode("1234567890").build();
		TokenRequest expected = new TokenRequest();
		expected.setCode("1234567890");
		assertThat(builded).isEqualTo(expected);
	}

	@Test (expected = java.lang.RuntimeException.class)
	public void codeShouldNotBeNull() {
		Builder builder = new TokenRequest.Builder();
		TokenRequest builded = builder.setGrantType("invalid").build();
		TokenRequest expected = new TokenRequest();
		expected.setGrantType("invalid");
		assertThat(builded).isEqualTo(expected);
	}

	@Test
	public void mandatoryFieldsShouldBeSet() {
		Builder builder = new TokenRequest.Builder();
		TokenRequest builded = builder.setCode("1234567890").setGrantType("authorization_code").build();
		TokenRequest expected = new TokenRequest();
		expected.setCode("1234567890");
		expected.setGrantType("authorization_code");
		assertThat(builded).isEqualTo(expected);
	}

	@Test (expected = java.lang.RuntimeException.class)
	public void responseTypeShouldBeCode() {
		Builder builder = new TokenRequest.Builder();
		TokenRequest builded = builder.setCode("1234567890").setGrantType("uthorization_code").build();
		TokenRequest expected = new TokenRequest();
		expected.setCode("1234567890");
		expected.setGrantType("authorization_code");
		assertThat(builded).isEqualTo(expected);
	}

	@Test
	public void optionalFieldShouldBeGetAsExpected() {
		Builder builder = new TokenRequest.Builder();
		TokenRequest builded = builder.setCode("1234567890").setGrantType("authorization_code")
				.setClientId("c123").build();
		TokenRequest expected = new TokenRequest();
		expected.setCode("1234567890");
		expected.setGrantType("authorization_code");
		expected.setClientId("c123");
		assertThat(builded).isEqualTo(expected);
	}

	@Test
	public void settingNullShouldNotChange() {
		Builder builder = new TokenRequest.Builder();
		TokenRequest builded = builder.setCode("1234567890").setGrantType("authorization_code")
				.setClientId(null).build();
		TokenRequest expected = new TokenRequest();
		expected.setCode("1234567890");
		expected.setGrantType("authorization_code");
		assertThat(builded).isEqualTo(expected);
	}

}
