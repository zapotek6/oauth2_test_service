package uk.co.labfour.cloud2.services.user.oauth;

import java.util.Set;

public class AuthorizationCode {
    private String code;
    private Set<String> scopes;

    public String getCode() {
        return code;
    }

    public AuthorizationCode setCode(String code) {
        this.code = code;
        return this;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public AuthorizationCode setScopes(Set<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    public AuthorizationCode addScope(String scope) {
        this.scopes.add(scope);
        return this;
    }
}
