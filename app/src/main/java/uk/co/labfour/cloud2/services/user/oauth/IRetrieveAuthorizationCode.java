package uk.co.labfour.cloud2.services.user.oauth;

import uk.co.labfour.error.BEarer;

import java.util.Set;

public interface IRetrieveAuthorizationCode {

    BEarer<AuthorizationCode> retrieveAuthorizationCode(String clientId, Set<String> scopes);
}
