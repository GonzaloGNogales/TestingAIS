package es.codeurjc.shop.rest.purchase;

public class GetPurchaseResponse {
    private long purchaseId;
    private long customerId;
    private long productId;

    public GetPurchaseResponse() {
    }

    public GetPurchaseResponse(long purchaseId, long customerId, long productId) {
        this.purchaseId = purchaseId;
        this.customerId = customerId;
        this.productId = productId;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getProductId() {
        return productId;
    }

}
