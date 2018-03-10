package uk.co.labfour.cloud2.services.user.oauth;

import uk.co.labfour.bjson.BJsonException;
import uk.co.labfour.bjson.BJsonObject;

public class TokenResponse {
    String accessToken = null;
    String tokenType = null;
    int expiresIn = 0;
    String refreshToken = null;


    public String getAccessToken() {
        return accessToken;
    }

    public TokenResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public TokenResponse setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public TokenResponse setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public TokenResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public BJsonObject build() {
        BJsonObject jResponse = new BJsonObject();
        try {
            jResponse.put(OAuth2Common.TOKEN_ACCESS_TOKEN_FIELD, accessToken);
            jResponse.put(OAuth2Common.TOKEN_TOKEN_TYPE_FIELD, tokenType);
            jResponse.put(OAuth2Common.TOKEN_EXPIRES_IN_FIELD, expiresIn);
            if (null != refreshToken && ! refreshToken.isEmpty()) {
                jResponse.put(OAuth2Common.TOKEN_REFRESH_TOKEN_FIELD, refreshToken);
            }
        } catch (BJsonException e) {
            e.printStackTrace();
        }

        return jResponse;
    }
}
