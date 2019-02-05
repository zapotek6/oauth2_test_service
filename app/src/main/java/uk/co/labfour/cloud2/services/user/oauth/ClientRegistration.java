package uk.co.labfour.cloud2.services.user.oauth;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class ClientRegistration {
    private String id;
    private String secret;
    private ClientAuthenticationType authenticationType;
    private URI redirectionUri;
    private Set<ResponseType> responseTypesAllowed = new HashSet<ResponseType>();
    private Set<AuthorizationGrantType> authorizationGrantTypesAllowed = new HashSet<AuthorizationGrantType>();
    private ClientType type;
    private ClientProfile profile;

    public String getId() {
        return id;
    }

    public ClientRegistration setId(String id) {
        this.id = id;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public ClientRegistration setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public ClientAuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public ClientRegistration setAuthenticationType(ClientAuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public URI getRedirectionUri() {
        return redirectionUri;
    }

    public ClientRegistration setRedirectionUri(URI redirectionUri) {
        this.redirectionUri = redirectionUri;
        return this;
    }

    public Set<ResponseType> getResponseTypesAllowed() {
        return responseTypesAllowed;
    }

    public ClientRegistration setResponseTypesAllowed(Set<ResponseType> responseTypesAllowed) {
        this.responseTypesAllowed = responseTypesAllowed;
        return this;
    }

    public ClientRegistration addResponseTypeAllowed(ResponseType responseType) {
        this.responseTypesAllowed.add(responseType);
        return this;
    }

    public Set<AuthorizationGrantType> getAuthorizationGrantTypesAllowed() {
        return authorizationGrantTypesAllowed;
    }

    public ClientRegistration setAuthorizationGrantTypesAllowed(Set<AuthorizationGrantType> authorizationGrantTypesAllowed) {
        this.authorizationGrantTypesAllowed = authorizationGrantTypesAllowed;
        return this;
    }

    public ClientRegistration addAuthorizationGrantTypesAllowed(AuthorizationGrantType authorizationGrantType) {
        this.authorizationGrantTypesAllowed.add(authorizationGrantType);
        return this;
    }

    public ClientType getType() {
        return type;
    }

    public ClientRegistration setType(ClientType type) {
        this.type = type;
        return this;
    }

    public ClientProfile getProfile() {
        return profile;
    }

    public ClientRegistration setProfile(ClientProfile profile) {
        this.profile = profile;
        return this;
    }
}
