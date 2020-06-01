package es.codeurjc.shop.rest.customer;

public class GetCustomerResponse {
    private long customerId;
    private String name;
    private long credit;

    public GetCustomerResponse() {
    }

    public GetCustomerResponse(long customerId, String name, long credit) {
        this.customerId = customerId;
        this.name = name;
        this.credit = credit;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public long getCredit() {
        return credit;
    }

}
