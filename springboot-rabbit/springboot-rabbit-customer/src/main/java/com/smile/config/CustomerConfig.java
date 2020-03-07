package com.smile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @作者 liutao
 * @时间 2020/3/5 23:56
 * @描述
 */
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class CustomerConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;
    private boolean publisherConfirms;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public boolean isPublisherConfirms() {
        return publisherConfirms;
    }

    public void setPublisherConfirms(boolean publisherConfirms) {
        this.publisherConfirms = publisherConfirms;
    }




}
