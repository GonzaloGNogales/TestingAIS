package es.codeurjc.shop.rest.customer;

public class CreateCustomerResponse {
    private long id;

    public CreateCustomerResponse() { }

    public CreateCustomerResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
