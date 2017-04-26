package it.mapsgroup.gzoom.persistence.common;

import java.util.Date;
import java.util.Stack;

/**
 * @author Andrea Fossi.
 */
public class TxStampHolder {
    private ThreadLocal<Stack<TxStamp>> THREAD_LOCAL = new ThreadLocal<>();

    public Date push() {
        return push(new Date());
    }

    public Date push(Date stamp) {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(new Stack<>());
        }
        THREAD_LOCAL.get().push(new TxStamp(stamp));
        return stamp;
    }

    public Date pop() {
        if (THREAD_LOCAL.get() != null) {
            return THREAD_LOCAL.get().pop().getStamp();
        } else return null;
    }

    public Date getStamp() {
        Stack<TxStamp> stack = THREAD_LOCAL.get();
        if (stack != null && stack.size() > 0) {
            return new Date(stack.get(stack.size() - 1).getStamp().getTime());
        } else return null;
    }

    public void reset() {
        THREAD_LOCAL.remove();
    }

    private static class TxStamp {
        private final Date stamp;

        public TxStamp(Date stamp) {
            this.stamp = stamp;
        }

        public Date getStamp() {
            return stamp;
        }
    }
}
