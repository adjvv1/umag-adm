package kz.umag.adm.middleware;

import kz.umag.adm.dto.AuthCredentials;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Custom logic to validate the credentials should be here
        var grantedAuthorities = new HashSet<GrantedAuthority>();
        AuthCredentials credentials = (AuthCredentials) authentication.getCredentials();
        if (credentials != null) {
            var perms = credentials.getPermissions();
            if (perms != null && !perms.isEmpty()) {
                grantedAuthorities = perms.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toCollection(HashSet::new));
            }
        }
        return new PreAuthenticatedAuthenticationToken(null, credentials, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.equals(authentication);
    }

}
