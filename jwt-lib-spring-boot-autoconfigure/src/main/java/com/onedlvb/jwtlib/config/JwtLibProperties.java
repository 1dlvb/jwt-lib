package com.onedlvb.jwtlib.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Class for handling properties for annotation
 * @author Matushkin Anton
 */
@Data
@ConfigurationProperties(prefix = "jwt-lib-spring-boot-starter")
public class JwtLibProperties {

    private String secret;
    private String algorithm;

}
