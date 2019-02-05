package uk.co.labfour.cloud2.services.user.oauth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class OAuth2RequestHandler {


    private HttpHeaders prepareHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setCacheControl("");

        return httpHeaders;
    }

    public ResponseEntity<?> doAuthorize(HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = AuthorizationRequest.parse(request.getParameterMap());
        HttpHeaders httpHeaders = new HttpHeaders();

        if (authorizationRequest.isValid()) {
            System.out.println("request valid");

            AuthorizationResponse authorizationResponse = new AuthorizationResponse(authorizationRequest);
            authorizationResponse.setCode("0987654321");
            httpHeaders.setLocation(authorizationResponse.buildRedirectUri());
            return new ResponseEntity<>("", httpHeaders, HttpStatus.FOUND);

        } else {
            System.out.println("invalid request");
        }


        return new ResponseEntity<>("accepted", httpHeaders, HttpStatus.ACCEPTED);

    }
}
