package org.alert.system.service;

import org.alert.system.model.Alert;

import java.util.Queue;

public interface IAlertService {
    public long createAlert(Alert alert);
    public void deleteAlert(Alert alert);
    public long  updateAlert(double price);
    public Queue<Alert> getAlerts(String symbol);
}
