package com.salestock.saleass.rest;

import com.salestock.SaleApp;
import com.salestock.saleass.core.sale.Sale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SaleApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SaleBasicIntegTests {

    @Test
    public void get_nonexistent_sale_should_get_404() {
        ResponseEntity response = restClient.getForEntity(baseUrl + 404, Object.class, port);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void get_sale_1_should_return_S01() {
        assertGetByIdMatchWithCode(baseUrl + 1, "S01");
    }

    @Test
    public void get_sale_2_should_return_S02() {
        assertGetByIdMatchWithCode(baseUrl + 2, "S02");
    }

    @Test
    public void post_a_new_sale_return_201_and_valid_new_location() {
        Sale sale = new Sale();
        sale.setTime(LocalDateTime.of(2000, 1, 1, 0, 0));
        ResponseEntity response = restClient.postForEntity(baseUrl, sale, Object.class, port);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

        String url = response.getHeaders().getLocation().toString();
        ResponseEntity<Sale> saleResponse = restClient.getForEntity(url, Sale.class);
        assertThat(sale.getTime(), equalTo(saleResponse.getBody().getTime()));
    }

    @Test
    public void post_sale_with_non_zero_id_should_return_bad_request() {
        Sale id1Sale = new Sale();
        id1Sale.setId(1);
        ResponseEntity<String> response = restClient.postForEntity(baseUrl, id1Sale, String.class, port);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void put_sale_3_with_new_time_should_change_the_time() {
        ResponseEntity<Sale> response = restClient.getForEntity(baseUrl + 3, Sale.class, port);
        Sale oldSale = response.getBody();

        Sale updateSale = new Sale();
        updateSale.setId(3);
        updateSale.setTime(oldSale.getTime().minusYears(10));
        restClient.put(baseUrl, updateSale, port);

        response = restClient.getForEntity(baseUrl + 3, Sale.class, port);
        assertThat(oldSale.getTime(), not(equalTo(response.getBody().getTime())));
    }

    @Test
    public void delete_sale_4_should_remove_the_entity() {
        restClient.delete(baseUrl + 4, port);
        ResponseEntity response = restClient.getForEntity(baseUrl + 4, Object.class, port);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    void assertGetByIdMatchWithCode(String url, String saleCode) {
        ResponseEntity<Sale> response = restClient.getForEntity(url, Sale.class, port);
        assertThat(response.getBody().getCode(), equalTo(saleCode));
    }

    String baseUrl = "http://localhost:{port}/api/sale/";
    TestRestTemplate restClient = new TestRestTemplate();

    @Value("${local.server.port}")
    private int port;
}
