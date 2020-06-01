package es.codeurjc.shop.rest.product;

public class CreateProductRequest {
    private String name;
    private long cost;
    private long stock;

    public CreateProductRequest() { }

    public CreateProductRequest(String name, long cost, long stock) {
        this.name = name;
        this.cost = cost;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public long getCost() {
        return cost;
    }

    public long getStock() {
        return stock;
    }
}
