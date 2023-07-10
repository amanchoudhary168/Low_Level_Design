package org.alert.system.component;

import org.alert.system.model.Alert;

public class EqualToFilterStrategy implements IAlertFilterStrategy {
    public boolean isValidAlert(Alert alert, Stock s) {
        double price = alert.getPrice();
        return price == s.getPrice();
    }
}
