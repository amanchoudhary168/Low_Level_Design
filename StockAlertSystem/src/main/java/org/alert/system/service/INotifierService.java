package org.alert.system.service;

import org.alert.system.model.User;

public interface INotifierService {
    public void notify(User user, String msg);
}
