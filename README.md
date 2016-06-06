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
Open up another terminal to fire HTTP GET:
```
curl -i -X GET http://192.168.99.100:8080/api/product/1 
```

If everything is correct, you will see detailed information about product id 1.

## Architecture
This application follows onion layered principles where business logic reside in bottom-most
of the layer. 
- Business logic layer, everything inside `com.salestock.saleass.core` package.
- Application layer, everything inside `com.salestock.saleass.ui.rest` package.
- Common layer, inside `com.salestock.common` package. This layer doesn't have
  any relationship with our application domain and can be reused in another
  application.
  

