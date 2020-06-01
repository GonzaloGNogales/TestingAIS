package es.codeurjc.shop.rest.product;

public class CreateProductResponse {
    private long id;

    public CreateProductResponse() { }

    public CreateProductResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
