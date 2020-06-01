package es.codeurjc.shop.rest.product;

public class GetProductResponse {
    private long productId;
    private String name;
    private long cost;
    private long stock;

    public GetProductResponse() {
    }

    public GetProductResponse(long productId, String name, long cost, long stock) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
        this.stock = stock;
    }

    public long getProductId() {
        return productId;
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
