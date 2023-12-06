package kz.umag.adm.util;

import lombok.experimental.UtilityClass;

/**
 * The utility class for controllers
 */
@UtilityClass
public final class ControllerConstants {
    public static final String API_FRAMEWORK_URL = "/rest";
    public static final String MODULE_SHORT_NAME_URL = "/adm";

    public static final String BASE_ADMIN_URL = API_FRAMEWORK_URL + "/admin" + MODULE_SHORT_NAME_URL;
    public static final String BASE_CABINET_URL = API_FRAMEWORK_URL + "/cabinet" + MODULE_SHORT_NAME_URL;
    public static final String BASE_INTERNAL_URL = API_FRAMEWORK_URL + "/internal" + MODULE_SHORT_NAME_URL;
    public static final String BASE_EXTERNAL_URL = API_FRAMEWORK_URL + "/external" + MODULE_SHORT_NAME_URL;

    public static final String COMMON_VIEW_PATH = "/view/{id}";
    public static final String COMMON_LIST_PATH = "/list";
    public static final String COMMON_CREATE_PATH = "/create";
    public static final String COMMON_UPDATE_PATH = "/update/{id}";
    public static final String COMMON_DELETE_PATH = "/delete/{id}";
}
