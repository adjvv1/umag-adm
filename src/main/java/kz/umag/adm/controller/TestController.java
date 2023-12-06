package kz.umag.adm.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.umag.adm.dto.AppInfo;
import kz.umag.adm.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestController.BASE_URL)
public class TestController {

    public static final String BASE_URL = ControllerConstants.API_FRAMEWORK_URL
            + ControllerConstants.MODULE_SHORT_NAME_URL
            + "/test";

    @Value("${umag-adm.version}")
    private String version;

    @Operation(summary = "Service provides information about the application")
    @GetMapping
    public AppInfo getAppInfo() {
        return new AppInfo()
                .setVersion(version);
    }
}
