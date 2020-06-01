package es.codeurjc.shop.domain.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    private @Id @GeneratedValue long id;
    private String name;
    private long cost;
    private long stock;

    Product() {}

    Product(String name, long cost, long stock) {
        setName(name);
        setCost(cost);
        setStock(stock);
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

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getCost() {
        return cost;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getStock() {
        return stock;
    }

    public void withdraw() throws ProductStockWithdrawExceededException {
        if (stock > 0) {
            stock--;
        } else {
            throw new ProductStockWithdrawExceededException();
        }
    }

    public void returnItem() {
        stock++;
    }

}
