package com.neilarg.currencybot.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Value("${proxy.enabled}")
    private Boolean proxyEnabled;

    @Value("${proxy.login}")
    private String proxyLogin;

    @Value("${proxy.password}")
    private String proxyPassword;



    @Bean
    public void authenticateProxy() {
        if (proxyEnabled) {
            System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(proxyLogin,
                            proxyPassword.toCharArray());
                }
            });
        }
    }

}
