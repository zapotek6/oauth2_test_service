package uk.co.labfour.cloud2.services.user.oauth;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class AuthorizationResponse {
    String code = null;
    URI redirectUri = null;
    String state = null;

    public AuthorizationResponse(AuthorizationRequest request) {
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

    public String getState() {
        return state;
    }

    public AuthorizationResponse setState(String state) {
        this.state = state;
        return this;
    }

    public URI buildLocation() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(redirectUri);
        uriComponentsBuilder.queryParam(OAuth2Common.HTTP_CODE_PARAMETER, code);
        if (null != state && ! state.isEmpty()) {
            uriComponentsBuilder.queryParam(OAuth2Common.HTTP_STATE_PARAMETER, state);
        }

        return uriComponentsBuilder.build().toUri();
    }

}
