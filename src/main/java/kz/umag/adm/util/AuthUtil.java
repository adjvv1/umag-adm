package kz.umag.adm.util;

import kz.umag.adm.dto.AuthCredentials;
import kz.umag.adm.exception.AuthException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Set;

@Slf4j
@UtilityClass
public final class AuthUtil {

    public static AuthCredentials getAuthCredentials() throws AuthException {
        var securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() != null) {
            return (AuthCredentials) authentication.getCredentials();
        }
        throw new AuthException("No auth credentials");
    }

    public static void setAuthCredentials(AuthCredentials credentials) {
        setAuthCredentials(credentials, Collections.emptySet());
    }

    public static void setAuthCredentials(AuthCredentials credentials,
                                          Set<GrantedAuthority> grantedAuthorities) {

        var token = new UsernamePasswordAuthenticationToken(null, credentials, grantedAuthorities);
        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(token);
    }

}
