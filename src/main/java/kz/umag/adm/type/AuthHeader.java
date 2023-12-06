package kz.umag.adm.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthHeader {
    USER_ID("__auth-user-id"),
    STORE_ID("__auth-store-id"),
    STORE_GROUP_ID("__auth-store-group-id"),
    COMPANY_ID("__auth-company-id"),
    POS_ID("__auth-pos-id"),
    CASHBOX_ID("__auth-cashbox-id"),
    TOKEN("__auth-token"),
    PERMISSIONS("__auth-permissions");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
