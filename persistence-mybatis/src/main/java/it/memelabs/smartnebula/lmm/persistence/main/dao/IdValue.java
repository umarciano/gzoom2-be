package it.memelabs.smartnebula.lmm.persistence.main.dao;

/**
 * @author Fabio G. Strozzi
 */
public class IdValue<T> {
    private final long id;
    private final T value;

    public IdValue(long id, T value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public T isValue() {
        return value;
    }
}
