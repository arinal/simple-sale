package com.salestock.saleass.rest;

import com.salestock.SaleApp;
import com.salestock.saleass.core.product.Product;
import com.salestock.saleass.core.sale.Sale;
import com.salestock.saleass.core.sale.SaleLineItem;
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

import java.util.List;

import static com.salestock.common.core.IterableCommon.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SaleApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SaleIntegTests {
    @Test
    public void buying_2_momogi_1_pepsi_and_2_momogi_again() {
        String saleUrl = "http://localhost:{port}/api/sale/";

        String productBaseUrl = "http://localhost:{port}/api/product/";
        String searchProductUrl = productBaseUrl + "?page={page}&size={size}&searchKey={key}";

        TestRestTemplate restClient = new TestRestTemplate();

        // First, generate a new sale. Firing GET to sale URL with id 0, the server will generate
        // new sale with its code initialized. Note that GET is being used, so nothing changed in
        // server yet.
        ResponseEntity<Sale> saleResponse = restClient.getForEntity(saleUrl + 0, Sale.class, port);
        Sale sale = saleResponse.getBody();
        // Note that we're in the context of client, which oftentimes don't have
        // a well encapsulated object in business logic. Mainly, client is only
        // constructing a JSON that will be sent to the server. Mimicking this
        // dumb client behavior, we're accessing Sale without addLineItem method,
        // but through direct ArrayList instead.
        List<SaleLineItem> lineItems = (List<SaleLineItem>)sale.getLineItems();

        // The user search for product to add in the sale, probably by typing
        // 'M' into the autocomplete drop-down-list. This will trigger a GET request
        // to product controller.
        List<Product> products = RestSearcher.searchProduct(searchProductUrl, "M", port);
        Product product1 = products.get(0); // user select first match, a nice Momogi snack
        lineItems.add(new SaleLineItem(product1, 2)); // make it 2 momogi please

        // Now, for another product, user type 'Pe'
        products = RestSearcher.searchProduct(searchProductUrl, "Pe", port);
        Product product2 = products.get(0); // select first match, a Pepsi
        lineItems.add(new SaleLineItem(product2, 1)); // just 1 is enough

        // Add Momogi again to sale. Have we used addLineItem, this will surely
        // be grouped automatically, but we're accessing directly to the list.
        lineItems.add(new SaleLineItem(product1, 2));

        // User press the Submit button after making payment. Yes, our system hasn't yet
        // handle payment now. The server will:
        // - Group the line items
        // - Generate the sale code again. First sale's code generation is just for
        //     assistance, so we have a clue what the code would be.
        // - Persist the sale transaction
        // - Return HTTP created
        ResponseEntity postSaleResponse = restClient.postForEntity(saleUrl, sale, Object.class, port);
        assertThat(postSaleResponse.getStatusCode(), equalTo(HttpStatus.CREATED));

        // gather the URL of newly saved transaction
        String newSaleLocation = postSaleResponse.getHeaders().getLocation().toString();

        // get the new sale and assert whether it is correctly grouped
        sale = restClient.getForEntity(newSaleLocation, Sale.class).getBody();
        assertThat(stream(sale.getLineItems()).count(), equalTo(2L));
        assertThat(sale.getLineItem(product1).get().getQuantity(), equalTo(4));

        // make sure the total is 500 (Momogi) x 4 + 5000 (Pepsi) = 7000
        assertThat(sale.getTotal().intValue(), equalTo(7000));
    }

    @Value("${local.server.port}")
    private int port;
}
