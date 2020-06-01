package es.codeurjc.shop.domain.purchase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Purchase {

    private @Id @GeneratedValue long id;
    private long customerId;
    private long productId;

    Purchase() {}

    Purchase(long customerId, long productId) {
        setCustomerId(customerId);
        setProductId(productId);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

}
