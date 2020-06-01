package es.codeurjc.shop.domain.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(String name, long cost, long stock) {
        Product product = new Product(name, cost, stock);
        return productRepository.save(product);
    }

    public Product getProduct(long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public long getProductCost(long id) throws ProductNotFoundException {
        return getProduct(id).getCost();
    }

    public Product updateProduct(long id, String name, long cost, long stock) throws ProductNotFoundException {
        Product product = getProduct(id);
        product.setName(name);
        product.setCost(cost);
        product.setStock(stock);
        productRepository.save(product);
        return product;
    }

    public void withdrawProduct(long id) throws ProductNotFoundException, ProductStockWithdrawExceededException {
        getProduct(id).withdraw();
    }

    public void returnItem(long id) throws ProductNotFoundException {
        getProduct(id).returnItem();
    }

}
