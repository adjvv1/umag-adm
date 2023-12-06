package kz.umag.adm.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import feign.Feign;
import feign.Target;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import kz.umag.adm.middleware.umag.UmagRestApiErrorDecoder;
import kz.umag.adm.middleware.umag.UmagRestApiRequestInterceptor;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import static kz.umag.adm.util.DateUtil.longToDate;
import static kz.umag.adm.util.StringUtil.dateToStringUmag;

@UtilityClass
public class UmagApiUtil {

    public static <T> T createUmagApiClient(Class<T> apiClientClass,
                                            String serviceUrl,
                                            String remoteModuleName) {

        Target<T> target = new Target.HardCodedTarget<>(apiClientClass, serviceUrl);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(
                        Date.class,
                        (JsonSerializer<Date>) (date, typeOfSrc, context) -> new JsonPrimitive(dateToStringUmag(date))
                )
                .registerTypeAdapter(
                        Date.class,
                        (JsonDeserializer<Date>) (json, typeOfT, context) -> longToDate(json.getAsLong())
                )
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder(gson))
                .decoder(new GsonDecoder(gson))
                .errorDecoder(new UmagRestApiErrorDecoder(remoteModuleName))
                .requestInterceptor(new UmagRestApiRequestInterceptor())
                .logger(new Slf4jLogger(apiClientClass))
                .logLevel(feign.Logger.Level.FULL)
                .target(target);
    }


    private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString());
        }
    }

}
