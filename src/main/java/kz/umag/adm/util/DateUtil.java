package kz.umag.adm.util;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateUtil {

    public static final DateFormat UMAG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public static Date longToDate(Long date) {
        return date != null ? new Date(date) : null;
    }

}
