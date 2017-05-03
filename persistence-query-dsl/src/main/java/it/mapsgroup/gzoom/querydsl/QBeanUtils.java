package it.mapsgroup.gzoom.querydsl;

import com.querydsl.core.types.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class QBeanUtils {
    public static Expression<?>[] merge(Expression<?>[] a, Expression<?>... b) {
        List<Expression<?>> _a = Arrays.asList(a);
        List<Expression<?>> _b = Arrays.asList(b);
        List<Expression<?>> c = new ArrayList<>(a.length + b.length);
        c.addAll(_a);
        c.addAll(_b);
        return c.toArray(new Expression<?>[0]);
    }
}
