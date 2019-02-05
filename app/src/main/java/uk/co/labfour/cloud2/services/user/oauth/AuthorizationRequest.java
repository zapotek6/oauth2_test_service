package uk.co.labfour.cloud2.services.user.oauth;

import uk.co.labfour.error.BEarer;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public class AuthorizationRequest {
    ResponseType responseType;

    String clientId;
    String clientSecret;

    URI redirectUri;

    Set<String> scopes;
    String state;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public ResponseType getResponseType() {
        return responseType;
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

    private boolean valid() {
        if (null != clientId &&
            null != responseType) {
            return true;
        } else {
            return false;
        }
    }

    public static BEarer<AuthorizationRequest> parse(Map<String, String[]> parameters) {
        AuthorizationRequest authorizationRequest =new AuthorizationRequest();

        authorizationRequest.clientId = OAuth2Common.getClientId(parameters);

        authorizationRequest.clientSecret = OAuth2Common.getClientSecret(parameters);

        authorizationRequest.redirectUri = OAuth2Common.getRedirecturi(parameters);

        BEarer<ResponseType> getResponseTypeOp = OAuth2Common.getResponseType(parameters);
        if (getResponseTypeOp.isOk()) {
            authorizationRequest.responseType = OAuth2Common.getResponseType(parameters).get();
        } else {
            authorizationRequest.responseType = null;
        }

        authorizationRequest.scopes = OAuth2Common.getScopes(parameters);

        authorizationRequest.state = OAuth2Common.getState(parameters);

        if (authorizationRequest.valid()) {
            return new BEarer<AuthorizationRequest>()
                    .setSuccess()
                    .set(authorizationRequest);
        } else {
            return BEarer.createGenericError(AuthorizationRequest.class.getSimpleName(), "Invalid request");
        }
    }

}
