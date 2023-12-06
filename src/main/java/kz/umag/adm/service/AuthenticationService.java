package kz.umag.adm.service;

import kz.umag.adm.exception.AuthException;
import org.springframework.stereotype.Service;

import static kz.umag.adm.util.AuthUtil.getAuthCredentials;

@Service
public class AuthenticationService {

    public Integer getCurrentCompanyId() throws AuthException {
        return getAuthCredentials().getCompanyId();
    }

    public Integer getCurrentStoreId() throws AuthException {
        return getAuthCredentials().getStoreId();
    }

}
