package com.guocz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author guocz
 * @date 2022/8/31 9:52
 */
@ConfigurationProperties(prefix = "curator")
@Data
@Component
public class WrapperZK {

    private int retryCount;

    private int elapsedTimeMs;

    private String connectString;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;

}
