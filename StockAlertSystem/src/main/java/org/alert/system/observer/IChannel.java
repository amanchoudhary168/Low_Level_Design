package org.alert.system.observer;

import org.alert.system.component.Stock;

public interface IChannel {
    public void update(Stock s);

}
