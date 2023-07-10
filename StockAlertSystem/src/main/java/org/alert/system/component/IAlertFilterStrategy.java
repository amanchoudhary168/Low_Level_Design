package org.alert.system.component;

import org.alert.system.model.Alert;

public interface IAlertFilterStrategy {
    public boolean isValidAlert(Alert alert, Stock s );
}
