package it.mapsgroup.gzoom.security;

import it.mapsgroup.gzoom.querydsl.dto.UserLogin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Principal utilities.
 *
 * @author Fabio G. Strozzi
 */
public class Principals {

    /**
     * Retrieves the current principal.
     *
     * @return the current principal.
     */
    public static UserLogin principal() {
        return (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Retrieves the current principal username.
     *
     * @return the current principal username.
     */
    public static String username() {
        UserLogin user = principal();
        return user != null ? user.getUsername() : null;
    }
}
