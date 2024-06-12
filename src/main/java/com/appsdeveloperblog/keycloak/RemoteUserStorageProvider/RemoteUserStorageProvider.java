package com.appsdeveloperblog.keycloak.RemoteUserStorageProvider;
import com.appsdeveloperblog.keycloak.UserAdapter;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

public class RemoteUserStorageProvider implements UserLookupProvider, CredentialInputValidator, UserStorageProvider {

    private KeycloakSession keycloakSession;
    private ComponentModel componentModel;
    private UserApiServiceImpl userApiService;
    public RemoteUserStorageProvider(KeycloakSession keycloakSession, ComponentModel componentModel,
                                     UserApiServiceImpl userApiService){
        this.keycloakSession=keycloakSession;
        this.componentModel=componentModel;
        this.userApiService=userApiService;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        StorageId storageId = new StorageId(id);
        String email = storageId.getExternalId();
        return getUserByUsername(realm, email);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        UserModel returnValue = null;

        User user = userApiService.getUserByUserName(username);

        if(user!=null) {
            returnValue = new UserAdapter(keycloakSession, realm, componentModel, user);
        }

        return returnValue;
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        return getUserByUsername(realm, email);
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return credentialType.equals(PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        VerifyPasswordResponse verifyPasswordResponse = userApiService.verifyUserPassword(user.getUsername(),
                credentialInput.getChallengeResponse());

        if(verifyPasswordResponse == null) {
            return false;
        }


        return verifyPasswordResponse.getResult();
    }
}
