package kz.umag.adm.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.umag.adm.dto.AuthCredentials;
import kz.umag.adm.type.AuthHeader;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static kz.umag.adm.util.AuthUtil.setAuthCredentials;

public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                          AuthenticationManager authenticationManager) {

        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        // Extract from request
        List<String> permissions = Collections.emptyList();
        String permsHeader = request.getHeader(AuthHeader.PERMISSIONS.toString());
        if (StringUtils.isNotBlank(permsHeader)) {
            try {
                permissions = (List<String>) new ObjectMapper().readValue(permsHeader, List.class);
            } catch (JsonProcessingException e) { /* Do nothing */ }
        }
        String userId = request.getHeader(AuthHeader.USER_ID.toString());
        String storeId = request.getHeader(AuthHeader.STORE_ID.toString());
        String storeGroupId = request.getHeader(AuthHeader.STORE_GROUP_ID.toString());
        String companyId = request.getHeader(AuthHeader.COMPANY_ID.toString());
        AuthCredentials credentials = null;
        if (userId != null) {
            credentials = new AuthCredentials()
                    .setUserId(stringToInteger(userId))
                    .setUserId(stringToInteger(userId))
                    .setStoreId(stringToInteger(storeId))
                    .setStoreGroupId(stringToInteger(storeGroupId))
                    .setCompanyId(stringToInteger(companyId))
                    .setPermissions(permissions);
        }
        // Create a token object ot pass to Authentication Provider
        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(null, credentials);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        setAuthCredentials((AuthCredentials) authResult.getCredentials(), new HashSet<>(authResult.getAuthorities()));
        chain.doFilter(request, response);
    }

    private static Integer stringToInteger(String value) {
        Integer result = null;
        if (StringUtils.isNotBlank(value)) {
            try {
                result = Integer.valueOf(value);
            } catch (NumberFormatException ignore) { /* Do nothing */ }
        }
        return result;
    }

}
