package es.codeurjc.shop.rest.customer;

public class CreateCustomerRequest {
    private String name;
    private long credit;

    public CreateCustomerRequest() { }

    public CreateCustomerRequest(String name, long credit) {
        this.name = name;
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public long getCredit() {
        return credit;
    }
}
