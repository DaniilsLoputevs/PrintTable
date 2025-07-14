package io.github.loputevs.printtable.design;

import io.github.loputevs.printtable.formatter.CompositeTypeFormatter;
import io.github.loputevs.printtable.formatter.TypeFormatter;

import java.util.HashMap;
import java.util.Map;

public class CustomTypeFormatter implements CompositeTypeFormatter {
    private final Map<Class<?>, TypeFormatter<?>> customFormatters = new HashMap<>();
    
    public static CustomTypeFormatter newDefault() {
        return new CustomTypeFormatter();
    }
    
    public <TYPE> CustomTypeFormatter addFormatter(Class<TYPE> clazz, TypeFormatter<TYPE> formatter) {
        customFormatters.put(clazz, formatter);
        return this;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"}) // Suppress raw generic
    @Override public String format(Class<?> valueClazz, Object value) {
        TypeFormatter formatter = customFormatters.get(valueClazz);
        if (formatter == null) return LETS_TRY_NEXT_FORMATER;
        return (String) formatter.apply(value);
    }
    
}
