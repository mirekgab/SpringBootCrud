/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author mirek
 */
@Configuration
@ConfigurationProperties(prefix="mirekgab")
public class MyProperties {
    private String restScheme;
    private String restHost;
    private Integer restPort;

    public String getRestHost() {
        return restHost;
    }

    public void setRestHost(String restHost) {
        this.restHost = restHost;
    }

    public Integer getRestPort() {
        return restPort;
    }

    public void setRestPort(Integer restPort) {
        this.restPort = restPort;
    }

    public String getRestScheme() {
        return restScheme;
    }

    public void setRestScheme(String restScheme) {
        this.restScheme = restScheme;
    }
}
