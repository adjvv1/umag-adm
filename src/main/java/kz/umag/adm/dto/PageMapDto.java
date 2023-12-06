package kz.umag.adm.dto;

import kz.umag.adm.exception.CustomRuntimeException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class PageMapDto<K, V> {

    private final Map<K, V> content = new HashMap<>();
    private int totalElements;


    public PageMapDto(Map<K, V> content1) {
        if (content1 == null) {
            throw new CustomRuntimeException("Content must not be null");
        }
        this.content.putAll(content1);
    }
}
