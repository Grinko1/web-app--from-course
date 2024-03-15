package com.example.demo.client;

import com.example.demo.entity.Product;
import com.example.demo.payload.NewProductPayload;
import com.example.demo.payload.UpdateProductPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientProductsRestClient implements ProductRestClient {
    private final RestClient restClient;
    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    @Override
    public List<Product> findAllProducts(String filter) {
        return this.restClient
                .get()
                .uri("/catalog-api/products?filter={filter}", filter)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Product createProduct(String title, String details) {
     try {
         return this.restClient
                 .post()
                 .uri("catalog-api/products")
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(new NewProductPayload(title, details))
                 .retrieve()
                 .body(Product.class);
     }catch (HttpClientErrorException.BadRequest exception){
       ProblemDetail problemDetail =  exception.getResponseBodyAs(ProblemDetail.class);
       throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
     }
    }

    @Override
    public Optional<Product> findProduct(int id) {
      try {
          return Optional.ofNullable(this.restClient.get()
                  .uri("catalog-api/products/{id}", id)
                  .retrieve()
                  .body(Product.class)
          );
      }catch (HttpClientErrorException.NotFound exception){
          return Optional.empty();
      }
    }

    @Override
    public void updateProduct(int id, String title, String details) {
        try {
             this.restClient
                    .patch()
                    .uri("catalog-api/products/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateProductPayload(title, details))
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail =  exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
             this.restClient.delete()
                    .uri("catalog-api/products/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException.NotFound exception){
             throw new NoSuchElementException(exception);
        }
    }
}
