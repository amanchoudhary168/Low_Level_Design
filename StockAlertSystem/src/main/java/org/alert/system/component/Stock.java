package org.alert.system.component;
import org.alert.system.observer.IChannel;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private String stockName;
    private String symbol;
    private double price;

    private List<IChannel> channel;

    public String getStockName() {
        return stockName;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public Stock(String stockName, String symbol, double price){
        this.stockName = stockName;
        this.symbol = symbol;
        this.price = price;
        channel = new ArrayList<>();
    }

    public void update(double price) {
        this.price = price;
        for(IChannel c : channel){
            c.update(this);
        }
    }

    public void addChannel(IChannel channel){
        this.channel.add(channel);
    }
    public void removeChannel(IChannel channel){
        this.channel.remove(channel);
    }
}
