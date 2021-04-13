/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud;

import java.net.URI;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author mirek
 */
public class MirekgabUriBuilder {

    @Autowired
    private MyProperties myProperties;

    public URI buildUri(String restPath, Map<String, ?> parameters) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(myProperties.getRestScheme())
                .host(myProperties.getRestHost())
                .port(myProperties.getRestPort())
                .path(myProperties.getRestEndpoint())
                .path(restPath)
                .buildAndExpand(parameters);
        return uriComponents.toUri();
    }

    public URI buildUri(String restPath) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(myProperties.getRestScheme())
                .host(myProperties.getRestHost())
                .port(myProperties.getRestPort())
                .path(myProperties.getRestEndpoint())
                .path(restPath)
                .build();
        return uriComponents.toUri();
    }
}
