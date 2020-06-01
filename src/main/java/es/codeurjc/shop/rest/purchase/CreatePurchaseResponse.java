package es.codeurjc.shop.rest.purchase;

public class CreatePurchaseResponse {
    private long id;

    public CreatePurchaseResponse() { }

    public CreatePurchaseResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
