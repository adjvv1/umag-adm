package kz.umag.adm.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AppInfo {
    private String version;
}
