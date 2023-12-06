package kz.umag.adm.util;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.util.Date;

import static kz.umag.adm.util.DateUtil.UMAG_DATE_FORMAT;

@UtilityClass
public class StringUtil {

    public static String dateToString(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static String dateToStringUmag(Date date) {
        return dateToString(date, UMAG_DATE_FORMAT);
    }

}
