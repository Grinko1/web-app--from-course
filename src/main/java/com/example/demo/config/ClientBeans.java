package com.example.demo.config;

import com.example.demo.client.RestClientProductsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public RestClientProductsRestClient productsRestClient(@Value("${selmag.services.catalog.uri:http://localhost:8081}") String catalogBaseUri,
                                                           @Value("${selmag.services.catalog.username:user}") String username,
                                                           @Value("${selmag.services.catalog.password:password}") String password) {
        return new RestClientProductsRestClient(
                RestClient
                        .builder()
                        .baseUrl(catalogBaseUri)
                        .requestInterceptor(new BasicAuthenticationInterceptor(username, password))
                        .build());
    }
}
