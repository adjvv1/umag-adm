package kz.umag.adm.middleware;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class HttpSecurityDsl extends AbstractHttpConfigurer<HttpSecurityDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        AuthenticationProcessingFilter filter = getFilter(authenticationManager);
        http.addFilterBefore(filter, AnonymousAuthenticationFilter.class);
    }

    public static HttpSecurityDsl httpSecurityDsl() {
        return new HttpSecurityDsl();
    }

    private static AuthenticationProcessingFilter getFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationProcessingFilter(new AntPathRequestMatcher("/**"), authenticationManager);
    }
}
