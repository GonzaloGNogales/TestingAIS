package es.codeurjc.shop.rest.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.shop.domain.product.Product;
import es.codeurjc.shop.domain.product.ProductNotFoundException;
import es.codeurjc.shop.domain.product.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    List<GetProductResponse> listProducts() {
        return productService.getProducts()
            .stream()
            .map(c -> new GetProductResponse(c.getId(), c.getName(), c.getCost(), c.getStock()))
            .collect(Collectors.toList());
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponse newProduct(@RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(
            createProductRequest.getName(),
            createProductRequest.getCost(),
            createProductRequest.getStock()
        );
        return new CreateProductResponse(product.getId());
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable long id) {
        try {
            Product product = productService.getProduct(id);
            GetProductResponse getProductResponse = new GetProductResponse(product.getId(), product.getName(), product.getCost(), product.getStock());
            return new ResponseEntity<>(getProductResponse, HttpStatus.OK);
        } catch (ProductNotFoundException productNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetProductResponse> updateItem(@RequestBody CreateProductRequest updatedItem, @PathVariable long id) {
        try {
            Product product = productService.updateProduct(id, updatedItem.getName(), updatedItem.getCost(), updatedItem.getStock());
            GetProductResponse getProductResponse = new GetProductResponse(product.getId(), product.getName(), product.getCost(), product.getStock());
            return new ResponseEntity<>(getProductResponse, HttpStatus.CREATED);
        } catch (ProductNotFoundException productNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
