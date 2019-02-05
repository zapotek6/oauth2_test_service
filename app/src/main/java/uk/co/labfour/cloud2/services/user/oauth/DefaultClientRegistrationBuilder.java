package uk.co.labfour.cloud2.services.user.oauth;

import uk.co.labfour.error.BEarer;

public class DefaultClientRegistrationBuilder {

    public static BEarer applyDefaultConfidentialSettings(ClientRegistration clientRegistration) {

        if (null != clientRegistration) {
            clientRegistration.setType(ClientType.CONFIDENTIAL);
            clientRegistration.setAuthenticationType(ClientAuthenticationType.PASSWORD);
            clientRegistration.addResponseTypeAllowed(ResponseType.AUTHORIZATION_CODE);
            clientRegistration.addAuthorizationGrantTypesAllowed(AuthorizationGrantType.AUTHORIZATION_CODE);
            clientRegistration.addAuthorizationGrantTypesAllowed(AuthorizationGrantType.REFRESH_TOKEN);

        } else {
            return BEarer.createGenericError(DefaultClientRegistrationBuilder.class.getSimpleName(), "client is null");
        }

        return BEarer.createSuccess();
    }
}
