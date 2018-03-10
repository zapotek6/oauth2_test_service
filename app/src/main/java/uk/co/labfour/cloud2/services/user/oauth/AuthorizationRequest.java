package uk.co.labfour.cloud2.services.user.oauth;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public class AuthorizationRequest {
    String clientId = null;
    URI redirectUri = null;
    OAuth2Common.AuthorizationGrantType authorizationGrantType = null;
    Set<String> scopes =null;
    String state = null;

    public String getClientId() {
        return clientId;
    }

    public OAuth2Common.AuthorizationGrantType getAuthorizationGrantType() {
        return authorizationGrantType;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public URI getRedirectUri() {
        return redirectUri;
    }

    public String getState() {
        return state;
    }

    public boolean isValid() {
        if (    null == clientId ||
                null == redirectUri ||
                null == authorizationGrantType) {
            return false;
        } else {
            return true;
        }
    }

    public static AuthorizationRequest build(Map<String, String[]> parameters) {
        AuthorizationRequest authorizationRequest =new AuthorizationRequest();


        authorizationRequest.clientId = OAuth2Common.getClientId(parameters);

        authorizationRequest.redirectUri = OAuth2Common.getRedirecturi(parameters);

        authorizationRequest.authorizationGrantType = OAuth2Common.getResponseType(parameters);

        authorizationRequest.scopes = OAuth2Common.getScopes(parameters);

        authorizationRequest.state = OAuth2Common.getState(parameters);

        return authorizationRequest;
    }


}
