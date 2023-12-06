package kz.umag.adm.middleware.umag;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import kz.umag.adm.dto.AuthCredentials;
import kz.umag.adm.exception.AuthException;
import kz.umag.adm.type.AuthHeader;

import static kz.umag.adm.util.AuthUtil.getAuthCredentials;

public class UmagRestApiRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            AuthCredentials credentials = getAuthCredentials();
            requestTemplate.header(AuthHeader.USER_ID.toString(), String.valueOf(credentials.getUserId()));
            requestTemplate.header(AuthHeader.STORE_ID.toString(), String.valueOf(credentials.getStoreId()));
            requestTemplate.header(AuthHeader.COMPANY_ID.toString(), String.valueOf(credentials.getCompanyId()));
        } catch (AuthException e) {
            /* Do nothing */
        }
    }

}
