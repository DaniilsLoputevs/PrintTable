package io.github.loputevs.printtable.formatter;

import java.util.function.Function;


@FunctionalInterface public interface CompositeTypeFormatter extends TypeFormatter<Object> {
    
    default String apply(Object value) {
        return format(value.getClass(), value);
    }
    
    String format(Class<?> valueClazz, Object value);
    
}
