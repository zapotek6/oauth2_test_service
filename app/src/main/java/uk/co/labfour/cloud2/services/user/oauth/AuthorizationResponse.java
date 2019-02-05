package uk.co.labfour.cloud2.services.user.oauth;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;
import uk.co.labfour.error.BEarer;

import java.net.URI;

public class AuthorizationResponse {
    String code;
    String token;
    ResponseType responseType;
    URI redirectUri;
    String state;
    String error;

    public AuthorizationResponse(AuthorizationRequest request) {
        responseType = request.responseType;
        state = request.getState();
        redirectUri = request.getRedirectUri();
    }

    public String getCode() {
        return code;
    }

    public AuthorizationResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public AuthorizationResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getState() {
        return state;
    }

    public AuthorizationResponse setState(String state) {
        this.state = state;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public AuthorizationResponse setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public URI getRedirectUri() {
        return redirectUri;
    }

    public AuthorizationResponse setRedirectUri(URI redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public String getError() {
        return error;
    }

    public AuthorizationResponse setError(String error) {
        this.error = error;
        return this;
    }

    public BEarer<URI> buildRedirectUri() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(redirectUri);

        switch (responseType) {
            case AUTHORIZATION_CODE:
                uriComponentsBuilder.queryParam(OAuth2Common.HTTP_CODE_PARAMETER, code);
                break;
            /*case TOKEN:
                uriComponentsBuilder.queryParam(OAuth2Common.HTTP_, token);
                break;*/
            default:
                return BEarer.createGenericError(this, "Invalid responseType " + responseType.toString())
                        .setCode(HttpStatus.BAD_REQUEST.value());

        }

        if (null != state && ! state.isEmpty()) {
            uriComponentsBuilder.queryParam(OAuth2Common.HTTP_STATE_PARAMETER, state);
        }

        return new BEarer<URI>()
                .setSuccess()
                .set(uriComponentsBuilder.build().toUri());
    }

}
