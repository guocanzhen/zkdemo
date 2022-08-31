package com.guocz.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guocz
 * @date 2022/8/31 9:54
 */
@Configuration
public class CuratorConfig {

    @Autowired
    private WrapperZK wrapperZK;

    /**
     * 这里的start就是创建完对象放到容器后，需要调用他的start方法
     * @return
     */
    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.newClient(
                wrapperZK.getConnectString(),
                wrapperZK.getSessionTimeoutMs(),
                wrapperZK.getConnectionTimeoutMs(),
                new RetryNTimes(wrapperZK.getRetryCount(), wrapperZK.getElapsedTimeMs()));
    }
}
