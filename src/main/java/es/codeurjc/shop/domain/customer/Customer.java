package es.codeurjc.shop.domain.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {

    private @Id @GeneratedValue long id;
    private String name;
    private long credit;

    Customer() {}

    Customer(String name, long credit) {
        setName(name);
        setCredit(credit);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public long getCredit() {
        return credit;
    }

    public void reserveCredit(long purchaseTotal) throws CustomerCreditLimitExceededException {
        if (credit >= purchaseTotal) {
            credit -= purchaseTotal;
        } else
            throw new CustomerCreditLimitExceededException();
    }

}