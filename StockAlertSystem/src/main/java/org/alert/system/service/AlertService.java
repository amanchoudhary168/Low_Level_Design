package org.alert.system.service;

import org.alert.system.model.Alert;
import org.alert.system.repo.AlertRepo;

import java.util.Queue;

public class AlertService implements IAlertService{

    AlertRepo alertRepo;

    private static long counter = 0;
    @Override
    public long createAlert(Alert alert) {
        alert.setId(++counter);
       return  alertRepo.addAlert(alert);

    }

    @Override
    public void deleteAlert(Alert alert) {
        alertRepo.deleteAlert(alert);
    }

    @Override
    public long updateAlert(double price) {
        return 0;
    }

    @Override
    public Queue<Alert> getAlerts(String symbol) {
        return alertRepo.getAlerts(symbol);
    }

}
