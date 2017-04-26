package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.ArrayList;
import java.util.List;

public class NotificationGroupEx extends NotificationGroup {
    private List<UserLoginPersistent> users;
    private List<NotificationEvent> events;

    public NotificationGroupEx() {
        this.users = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public List<NotificationEvent> getEvents() {
        return events;
    }

    public void setEvents(List<NotificationEvent> events) {
        this.events = events;
    }

    public List<UserLoginPersistent> getUsers() {
        return users;
    }

    public void setUsers(List<UserLoginPersistent> users) {
        this.users = users;
    }
}