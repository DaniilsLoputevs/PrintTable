package io.github.loputevs.printtable.design;

import io.github.loputevs.printtable.formatter.CompositeTypeFormatter;
import lombok.Builder;

import java.util.Objects;

@Builder public class TableCellValueFormatter implements CompositeTypeFormatter {
    private final PrimitiveFormatter primitiveFormatter;
    private final CustomTypeFormatter customTypeFormatter;
    
    public static TableCellValueFormatter newDefault() {
        return TableCellValueFormatter.builder()
                .primitiveFormatter(PrimitiveFormatter.newDefault())
                .build();
    }
    
    @Override public String format(Class<?> valueClazz, Object value) {
        
        String result = primitiveFormatter.format(valueClazz, value);
        if (!LETS_TRY_NEXT_FORMATER.equals(result)) return result;
        
        result = customTypeFormatter.format(valueClazz, value);
        if (!LETS_TRY_NEXT_FORMATER.equals(result)) return result;
        
        return Objects.toString(value);
    }
}
