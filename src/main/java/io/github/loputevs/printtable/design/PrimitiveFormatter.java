package io.github.loputevs.printtable.design;

import io.github.loputevs.printtable.formatter.CellValueFormatter;
import io.github.loputevs.printtable.formatter.CompositeTypeFormatter;
import io.github.loputevs.printtable.formatter.TypeFormatter;
import lombok.Builder;

import java.math.BigDecimal;

@Builder public class PrimitiveFormatter implements CompositeTypeFormatter {
    private final TypeFormatter<String> stringFormatter;
    private final TypeFormatter<Number> decimalFormatter;
    
    public static PrimitiveFormatter newDefault() {
        return PrimitiveFormatter.builder()
                .stringFormatter(CellValueFormatter.Config.Default.STRING)
                .decimalFormatter(CellValueFormatter.Config.Default.DECIMAL)
                .build();
    }
    
    @Override public String format(Class<?> valueClazz, Object value) {
        if (valueClazz == String.class) return stringFormatter.apply((String) value);
        if (isDecimalType(value, valueClazz)) return decimalFormatter.apply((Number) value);
        return LETS_TRY_NEXT_FORMATER;
    }
    
    private boolean isDecimalType(Object object, Class<?> clazz) {
        return object instanceof BigDecimal || clazz == Double.class || clazz == Float.class;
    }
}
