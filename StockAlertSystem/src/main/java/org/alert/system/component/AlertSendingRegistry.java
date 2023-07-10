package org.alert.system.component;

import org.alert.system.constants.AlertCondition;

import java.util.Map;

public class AlertSendingRegistry {
    Map<AlertCondition, IAlertFilterStrategy> conditionAlertFilterStrategyMap;

    public AlertSendingRegistry(Map<AlertCondition, IAlertFilterStrategy> conditionAlertFilterStrategyMap) {
        this.conditionAlertFilterStrategyMap = conditionAlertFilterStrategyMap;
    }
    public IAlertFilterStrategy getFilterStrategy(AlertCondition condition){
        return conditionAlertFilterStrategyMap.get(condition);
    }
}
