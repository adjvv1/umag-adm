package kz.umag.adm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Contains common information about appeared internal exception
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExceptionInfoDto implements Serializable {
    /**
     * The code of http (or another!) status
     */
    private Integer status;
    /**
     * The detailed message about causes of exception throwing
     */
    private String message;
}
