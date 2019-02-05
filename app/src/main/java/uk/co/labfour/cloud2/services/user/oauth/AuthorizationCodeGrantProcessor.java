package uk.co.labfour.cloud2.services.user.oauth;

import uk.co.labfour.error.BEarer;

public class AuthorizationCodeGrantProcessor {
    private final IRetrieveClientRegistration clientRegistrationRetriever;

    public AuthorizationCodeGrantProcessor(IRetrieveClientRegistration clientRegistrationRetriever) {
        this.clientRegistrationRetriever = clientRegistrationRetriever;
    }


    private boolean isResponseTypeAllowed(ClientRegistration clientRegistration, AuthorizationRequest authorizationRequest) {

        return clientRegistration
                .getResponseTypesAllowed()
                .contains(authorizationRequest.getResponseType());
    }

    private boolean redirectionUriValid(ClientRegistration clientRegistration, AuthorizationRequest authorizationRequest) {
        return null != clientRegistration.getRedirectionUri() &&
                !clientRegistration.getRedirectionUri().toASCIIString().isEmpty() &&
                authorizationRequest.getRedirectUri().equals(clientRegistration.getRedirectionUri());
    }

    public BEarer validate(AuthorizationRequest authorizationRequest) {


        BEarer<ClientRegistration> getClientRegistrationOp = clientRegistrationRetriever.getClientRegistration(authorizationRequest.clientId);

        // Client ID exists?
        if (getClientRegistrationOp.isOk()) {

            ClientRegistration clientRegistration = getClientRegistrationOp.get();
            // Requested response type is allowed?
            if (isResponseTypeAllowed(clientRegistration, authorizationRequest) &&
                    authorizationRequest.getResponseType() == ResponseType.AUTHORIZATION_CODE) {

                return BEarer.createSuccess();

            } else {
                return BEarer.createGenericError(this, "Invalid Response Type " + authorizationRequest.getResponseType());

            }

        } else {
            return BEarer.createGenericError(this, "Invalid Client ID " + authorizationRequest.getClientId());
        }


    }

}
