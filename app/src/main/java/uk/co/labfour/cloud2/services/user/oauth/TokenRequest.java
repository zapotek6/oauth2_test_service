package uk.co.labfour.cloud2.services.user.oauth;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public class TokenRequest {
    String clientId = null;
    String clientSecret = null;
    URI redirectUri = null;
    OAuth2Common.AccessTokenGrantType grantType = null;
    Set<String> scopes =null;
    String code = null;

    public boolean isValid() {
        if (    null == clientId ||
                null == redirectUri ||
                null == grantType ||
                null == code) {
            return false;
        } else {
            return true;
        }
    }


    public static TokenRequest build(Map<String, String[]> parameters) {
        TokenRequest tokenRequest =new TokenRequest();

        tokenRequest.clientId = OAuth2Common.getClientId(parameters);

        tokenRequest.clientSecret = OAuth2Common.getClientSecret(parameters);

        tokenRequest.redirectUri = OAuth2Common.getRedirecturi(parameters);

        tokenRequest.grantType = OAuth2Common.getGrantType(parameters);

        tokenRequest.scopes = OAuth2Common.getScopes(parameters);

        tokenRequest.code = OAuth2Common.getCode(parameters);

        return tokenRequest;
    }
}
