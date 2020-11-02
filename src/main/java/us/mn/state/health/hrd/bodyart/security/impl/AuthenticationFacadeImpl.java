package us.mn.state.health.hrd.bodyart.security.impl;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import us.mn.state.health.hrd.bodyart.security.AuthenticationFacade;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public AccessToken getAccessToken() {
        if (getAuthentication().getPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal principal = (KeycloakPrincipal) getAuthentication().getPrincipal();
            return principal.getKeycloakSecurityContext().getToken();
        }
        return null;
    }
}
