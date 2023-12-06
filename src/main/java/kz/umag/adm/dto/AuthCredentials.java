package kz.umag.adm.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Contains current authorization data
 */
@Data
@Accessors(chain = true)
public class AuthCredentials {
    private Integer userId;
    private Integer storeId;
    private Integer storeGroupId;
    private Integer companyId;
    private List<String> permissions;
}
