# Simple Sale
Simple REST API with domain driven approach. Required features: list items, add, remove item.

## Getting Started
Assuming you already have JDK8+ and JAVA_HOME setup, just run:

```
git clone https://github.com/arinal/simple-sale.git && cd simple-sale
./gradlew bootrun
curl -i -X GET http://localhost:8080/api/product/1
```

Or you can just use docker instead:
```
./gradlew buildDocker
docker run -p 8080:8080 saleass
```
Or maybe you wanna try to separate app and db? Try docker compse:
```
docker-compose up
```
Eitherway, if you use docker, you have to open up another terminal to fire HTTP GET:
```
curl -i -X GET http://192.168.99.100:8080/api/product/1 
```

If everything is correct, you will see detailed information about product id 1.
```
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
X-Application-Context: saleass:prod:80
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 06 Jun 2016 04:44:59 GMT

{"id":1,"code":"P01","name":"Momogi","unitPrice":500.00,"transient":false,"_links":{"self":{"href":"http://192.168.99.100:8080/api/product/1"}}} 
```

## Architecture
This application follows onion layered principles where business logic reside in bottom-most
of the layer. 
- Business logic layer, everything inside `com.salestock.saleass.core` package.
- Application layer, everything inside `com.salestock.saleass.ui.rest` package.
- Common layer, inside `com.salestock.common` package. This layer doesn't have
  any relationship with our application domain and can be reused in another
  application.
  
As a starter, to see what does this application can do in general, have a look
at the main test case in [`ShoppingHappyPathTest.java`](https://github.com/arinal/simple-sale/blob/master/src/test/java/com/salestock/saleass/rest/ShoppingHappyPathTest.java).

As you may have seen, this application doesn't have explicit language about cart. Buying items
is implemented by `Sale` domain to represent transaction. Cart management, like adding or removing
item to current cart, should be implemented on client side. Hence, why it is called `simple-sale`.
