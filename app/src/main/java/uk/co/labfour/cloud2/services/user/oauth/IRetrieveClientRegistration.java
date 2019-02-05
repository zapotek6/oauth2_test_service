package uk.co.labfour.cloud2.services.user.oauth;

import uk.co.labfour.error.BEarer;

public interface IRetrieveClientRegistration {

    BEarer<ClientRegistration> getClientRegistration(String clientId);

}
