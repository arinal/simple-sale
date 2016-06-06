package com.salestock.saleass.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

import com.salestock.SaleApp;
import com.salestock.common.RestClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.salestock.saleass.core.product.Product;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SaleApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ProductBasicIntegTests {

    String baseUrl = "http://localhost:{port}/api/product/";

    @Test
    public void get_nonexistent_product_should_get_404() {
        ResponseEntity response = restClient.getForEntity(baseUrl + 404, Object.class, port);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void get_product_1_should_return_momogi() {
        assertGetProductByIdMatchName(baseUrl + 1, "Momogi");
    }

    @Test
    public void get_product_2_should_return_pepsi() {
        assertGetProductByIdMatchName(baseUrl + 2, "Pepsi");
    }

    @Test
    public void get_products_by_searchkey_M() {
        String url = baseUrl + "?page={page}&size={size}&searchKey={key}";
        List<Product> products = RestSearcher.searchProduct(restClient, url, "M", port);
        assertThat(products.get(0).getName(), equalTo("Momogi"));
    }

    @Test
    public void post_a_new_product_return_201_and_valid_new_location() {
        Product product = new Product();
        product.setName("Product anew");
        ResponseEntity response = restClient.postForEntity(baseUrl, product, Object.class, port);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

        String url = response.getHeaders().getLocation().toString();
        assertGetProductByIdMatchName(url, "Product anew");
    }

    @Test
    public void post_product_with_non_zero_id_should_return_bad_request() {
        restClient = new TestRestTemplate();
        Product id1Product = new Product();
        id1Product.setId(1);
        ResponseEntity<String> response = restClient.postForEntity(baseUrl, id1Product, String.class, port);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void put_product_3_with_new_name_should_change_the_name() {
        ResponseEntity<Product> response = restClient.getForEntity(baseUrl + 3, Product.class, port);
        Product oldProduct = response.getBody();

        Product updateProduct = new Product();
        updateProduct.setId(3);
        updateProduct.setName(oldProduct.getName() + " Updated");
        restClient.put(baseUrl, updateProduct, port);

        assertGetProductByIdMatchName(baseUrl + 3, oldProduct.getName() + " Updated");
    }

    @Test
    public void delete_product_4_should_remove_the_entity() {
        restClient.delete(baseUrl + 4, port);
        ResponseEntity response = restClient.getForEntity(baseUrl + 4, Object.class, port);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    private void assertGetProductByIdMatchName(String url, String productName) {
        ResponseEntity<Product> response = restClient.getForEntity(url, Product.class, port);
        assertThat(response.getBody().getName(), equalTo(productName));
    }

    public ProductBasicIntegTests() {
        restClient = RestClientUtils.createTestRestTemplate();
    }

    private TestRestTemplate restClient;

    @Value("${local.server.port}")
    private int port;
}
