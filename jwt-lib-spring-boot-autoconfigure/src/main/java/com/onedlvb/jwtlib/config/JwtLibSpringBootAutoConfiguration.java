package com.onedlvb.jwtlib.config;

import com.onedlvb.jwtlib.filter.JWTAuthenticationFilter;
import com.onedlvb.jwtlib.util.JWTUtil;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class for autoconfiguring Spring Starter
 * <ul>
 *     <li>Set jwt-lib-spring-boot-starter.secret=...</li>
 *     <li>Set jwt-lib-spring-boot-starter.algorithm=...</li>
 * </ul>
 * Configure these variables in application.properties file.
 * @author Matushkin Anton
 */
@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(JwtLibProperties.class)
public class JwtLibSpringBootAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public JWTUtil jwtUtil(JwtLibProperties jwtLibProperties) {
        return new JWTUtil(jwtLibProperties);
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(JWTUtil jwtUtil) {
        return new JWTAuthenticationFilter(jwtUtil);
    }

}
