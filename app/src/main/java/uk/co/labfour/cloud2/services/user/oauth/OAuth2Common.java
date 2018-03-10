package uk.co.labfour.cloud2.services.user.oauth;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OAuth2Common {
    public static final String HTTP_CLIENT_ID_PARAMETER = "client_id";
    public static final String HTTP_CLIENT_SECRET_PARAMETER = "client_secret";
    public static final String HTTP_REDIRECT_URI_PARAMETER = "redirect_uri";
    public static final String HTTP_RESPONSE_TYPE_PARAMETER = "response_type";
    public static final String HTTP_SCOPE_PARAMETER = "scope";
    public static final String HTTP_SCOPE_PARAMETER_SEPARATOR = " ";
    public static final String HTTP_STATE_PARAMETER = "state";
    public static final String HTTP_CODE_PARAMETER = "code";
    public static final String HTTP_GRANT_TYPE_PARAMETER = "grant_type";

    public static final String RESPONSE_TYPE_CODE = "code";     // authorization_code
    public static final String RESPONSE_TYPE_TOKEN = "token";   // implicit
    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    public static final String TOKEN_ACCESS_TOKEN_FIELD = "access_token";
    public static final String TOKEN_TOKEN_TYPE_FIELD = "token_type";
    public static final String TOKEN_EXPIRES_IN_FIELD = "expires_in";
    public static final String TOKEN_REFRESH_TOKEN_FIELD = "refresh_token";

    public enum AuthorizationGrantType { UNDEFINED, CODE, IMPLICIT }

    public enum AccessTokenGrantType { UNDEFINED, AUTHORIZATION_CODE, PASSWORD, CLIENT_CREDENTIALS, REFRESH_TOKEN }


    private static String getParam(Map<String, String[]> params, String paramName) {
        if (params.containsKey(paramName)) {
            return params.get(paramName)[0];
        }
        return "";
    }

    public static String getClientId(Map<String, String[]> params) {
        return getParam(params, HTTP_CLIENT_ID_PARAMETER);
    }

    public static String getClientSecret(Map<String, String[]> params) {
        return getParam(params, HTTP_CLIENT_SECRET_PARAMETER);
    }

    public static URI getRedirecturi(Map<String, String[]> params) {
        try {
            return new URI(getParam(params, HTTP_REDIRECT_URI_PARAMETER));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AccessTokenGrantType getGrantType(Map<String, String[]> params) {
        String grantType = getParam(params, HTTP_GRANT_TYPE_PARAMETER);

        if (grantType.equals(GRANT_TYPE_AUTHORIZATION_CODE)) {
            return AccessTokenGrantType.AUTHORIZATION_CODE;
        } else if (grantType.equals(OAuth2Common.GRANT_TYPE_PASSWORD)) {
            return AccessTokenGrantType.PASSWORD;
        } else if (grantType.equals(OAuth2Common.GRANT_TYPE_CLIENT_CREDENTIALS)) {
            return AccessTokenGrantType.CLIENT_CREDENTIALS;
        } else if (grantType.equals(OAuth2Common.GRANT_TYPE_REFRESH_TOKEN)) {
            return AccessTokenGrantType.REFRESH_TOKEN;
        }

        return AccessTokenGrantType.UNDEFINED;
    }

    public static AuthorizationGrantType getResponseType(Map<String, String[]> params) {
        String grantType = getParam(params, HTTP_RESPONSE_TYPE_PARAMETER);

        if (grantType.equals(RESPONSE_TYPE_CODE)) {
            return AuthorizationGrantType.CODE;
        } else if (grantType.equals(OAuth2Common.RESPONSE_TYPE_TOKEN)) {
            return AuthorizationGrantType.IMPLICIT;
        }

        return AuthorizationGrantType.UNDEFINED;
    }

    public static Set<String> getScopes(Map<String, String[]> params) {

        if (params.containsKey(OAuth2Common.HTTP_SCOPE_PARAMETER)) {
            Set<String> scopes = new HashSet<>();
            for (String scope: getParam(params, HTTP_SCOPE_PARAMETER).split(HTTP_SCOPE_PARAMETER_SEPARATOR)) {
                scopes.add(scope);
            }
            return scopes;
        }

        return null;
    }

    public static String getState(Map<String, String[]> params) {
        return getParam(params, HTTP_STATE_PARAMETER);
    }

    public static String getCode(Map<String, String[]> params) {
        return getParam(params, HTTP_CODE_PARAMETER);
    }

}
