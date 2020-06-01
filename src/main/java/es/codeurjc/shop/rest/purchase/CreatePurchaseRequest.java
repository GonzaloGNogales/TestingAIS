package es.codeurjc.shop.rest.purchase;

public class CreatePurchaseRequest {
    private long customerId;
    private long productId;

    public CreatePurchaseRequest() { }

    public CreatePurchaseRequest(long customerId, long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getProductId() {
        return productId;
    }
}
