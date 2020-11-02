package us.mn.state.health.hrd.bodyart.security;

import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();

    AccessToken getAccessToken();
}
