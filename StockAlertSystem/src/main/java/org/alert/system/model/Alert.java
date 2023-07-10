package org.alert.system.model;

import org.alert.system.component.Stock;
import org.alert.system.constants.AlertCondition;

public class Alert {
    long id;
    Stock stock;
    double price;
    AlertCondition condition;
    User user;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setCondition(AlertCondition condition) {
        this.condition = condition;
    }

    public Stock getStock() {
        return stock;
    }

    public AlertCondition getCondition() {
        return condition;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
