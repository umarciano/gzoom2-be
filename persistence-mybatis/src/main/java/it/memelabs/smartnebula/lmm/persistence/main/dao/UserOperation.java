package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.UserLogin;

/**
 * @author Fabio G. Strozzi
 */
public class UserOperation<T> {
    private UserLogin user;
    private T filter;

    public UserOperation(UserLogin user, T filter) {
        this.user = user;
        this.filter = filter;
    }

    public UserLogin getUser() {
        return user;
    }

    public T getFilter() {
        return filter;
    }
}
