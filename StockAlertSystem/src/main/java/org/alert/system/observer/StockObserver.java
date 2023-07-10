package org.alert.system.observer;
import org.alert.system.model.Alert;
import org.alert.system.component.AlertSendingRegistry;
import org.alert.system.component.Stock;
import org.alert.system.service.AlertService;
import org.alert.system.service.INotifierService;

import java.util.ArrayList;
import java.util.Queue;

public class StockObserver implements IChannel {
    AlertService alertService;
    INotifierService notifierService;
    AlertSendingRegistry alertSendingRegistry;


    @Override
    public void update(Stock s) {
        Queue<Alert>alertQueue = alertService.getAlerts(s.getSymbol());
        if(alertQueue!=null){
            ArrayList<Alert> alertList = new ArrayList<>();
            while(!alertQueue.isEmpty()){
                Alert alert = alertQueue.poll();
                if(alertSendingRegistry.getFilterStrategy(alert.getCondition()).isValidAlert(alert,s))
                    notifierService.notify(alert.getUser(),String.format("%s price is %f",s.getStockName(),s.getPrice()));
                else
                    alertList.add(alert);
            }
            alertQueue.addAll(alertList);
        }
    }

}
