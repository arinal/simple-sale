package com.salestock.saleass.rest;

import com.salestock.common.RestClientUtils;
import com.salestock.saleass.core.product.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestHelper {
    public static List<Product> searchProduct(RestTemplate restClient, String url, String searchKey, int port) {
        ResponseEntity<PagedResources<Product>> responseEntity = restClient.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedResources<Product>>() { },
                port, 0, 100, searchKey);
        PagedResources<Product> resources = responseEntity.getBody();
        return new ArrayList<>(resources.getContent());
    }

    public static List<Product> searchProduct(String url, String searchKey, int port) {
        return searchProduct(RestClientUtils.createTestRestTemplate(), url, searchKey, port);
    }
}
