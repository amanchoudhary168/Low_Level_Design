package org.alert.system.repo;

import org.alert.system.model.Alert;

import java.util.Map;
import java.util.PriorityQueue;

public class AlertRepo {
    Map<String, PriorityQueue<Alert>> observerMap;

    public long addAlert(Alert alert){
        PriorityQueue<Alert> pq = observerMap.getOrDefault(alert.getStock().getSymbol(),new PriorityQueue<>());
        pq.add(alert);
        observerMap.put(alert.getStock().getSymbol(),pq);
        return alert.getId();
    }

    public PriorityQueue<Alert> getAlerts(String symbol){
        return observerMap.get(symbol);
    }

    public void deleteAlert(Alert alert){
        PriorityQueue<Alert> queue = observerMap.get(alert.getStock().getSymbol());
        queue.remove(alert);
    }
}
