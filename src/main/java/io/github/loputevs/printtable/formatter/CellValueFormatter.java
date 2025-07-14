package io.github.loputevs.printtable.formatter;

import io.github.loputevs.printtable.formatter.impl.ArrayFormatterImpl;
import io.github.loputevs.printtable.formatter.impl.LambdaFormatterImpl;
import lombok.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CellValueFormatter  implements TypeFormatter<Object> {
    protected Config config = Config.newDefaultBuilder().build();
    
    /**
     * TODO :
     * TODO : java.sql.Data
     * TODO : java.util.Calendar
     * TODO :
     *          ZonedDateTime — дата и время с часовым поясом.
     *          OffsetDateTime — дата и время с фиксированным смещением от UTC.
     *          OffsetTime — время с фиксированным смещением от UTC.
     *          Instant — момент времени в UTC (эпоха Unix).
     *          Period — временной интервал в виде годов, месяцев и дней.
     *          Duration — временной интервал в секундах и наносекундах.
     */
    @Override public String apply(Object cellValue) {
        val clazz = cellValue.getClass();
        if (clazz == String.class) return config.getString().apply((String) cellValue);
        if (isDecimalType(cellValue, clazz)) return config.getDecimal().apply((Number) cellValue);
        
        if (cellValue instanceof LocalDateTime) return config.getLocalDateTime().apply((LocalDateTime) cellValue);
        if (cellValue instanceof LocalDate) return config.getLocalDate().apply((LocalDate) cellValue);
        if (cellValue instanceof LocalTime) return config.getLocalTime().apply((LocalTime) cellValue);
        if (cellValue instanceof Date) return config.getDate().apply((Date) cellValue);
        if (cellValue instanceof Calendar) return config.getCalendar().apply(((Calendar) cellValue));
        
        if (cellValue instanceof List) return config.getList().apply((List<?>) cellValue);
        if (clazz.isArray()) return config.array.apply(cellValue);
        if (cellValue instanceof Map) return config.getMap().apply((Map<?, ?>) cellValue);
        
        if (cellValue instanceof Method) return config.getReflectionMethod().apply((Method) cellValue);
        if (isLambdaOrMethodReference(clazz)) return config.functionalInterface.apply(clazz);
        if (cellValue == Class.class) return config.clazz.apply((Class<?>) cellValue);
        
        if (config.customStringifies.containsKey(clazz)) return config.customStringifies.get(clazz).apply(cellValue);
        return config.objectToString.apply(cellValue);
    }
    
    private boolean isLambdaOrMethodReference(Class<?> clazz) {
        return clazz.isSynthetic() && clazz.getName().contains("$$Lambda");
    }
    
    private boolean isDecimalType(Object object, Class<?> clazz) {
        return object instanceof BigDecimal || clazz == Double.class || clazz == Float.class;
    }
    
    
    @Builder
    @Getter
    @Setter
    public static class Config {
        private TypeFormatter<String> string;
        private TypeFormatter<Number> decimal;
        
        private TypeFormatter<LocalDateTime> localDateTime;
        private TypeFormatter<LocalDate> localDate;
        private TypeFormatter<LocalTime> localTime;
        private TypeFormatter<Date> date;
        private TypeFormatter<Calendar> calendar;
        
        private TypeFormatter<List<?>> list;
        private TypeFormatter<Object> array;
        private TypeFormatter<Map<?, ?>> map;
        
        private TypeFormatter<Method> reflectionMethod;
        private TypeFormatter<Class<?>> functionalInterface;
        private TypeFormatter<Class<?>> clazz;
        
        private Map<Class<?>, TypeFormatter<Object>> customStringifies;
        private TypeFormatter<Object> objectToString;
        
        /**
         * Designed to use if flow style like Stream API.
         *
         * @param clazz
         * @param <CELL_VALUE_TYPE>
         * @return
         */
        @SuppressWarnings("unchecked") // I know that I do ^_^
        public <CELL_VALUE_TYPE> Config customStringifier(Class<CELL_VALUE_TYPE> clazz, TypeFormatter<CELL_VALUE_TYPE> typeFormatter) {
            customStringifies.put(clazz, (TypeFormatter<Object>) typeFormatter);
            return this;
        }
        
        public Config removeStringifier(Class<?> clazz) {
            customStringifies.remove(clazz);
            return this;
        }
        
        public static ConfigBuilder newDefaultBuilder() {
            return Config.builder()
                    .string(Default.STRING)
                    .decimal(Default.DECIMAL)
                    
                    .localDateTime(Default.LOCAL_DATE_TIME)
                    .localDate(Default.LOCAL_DATE)
                    .localTime(Default.LOCAL_TIME)
                    .date(Default.DATE)
                    .calendar(Default.CALENDAR)
                    
                    .list(Default.LIST)
                    .array(Default.ARRAY)
                    .map(Default.MAP)
                    
                    .reflectionMethod(Default.REFLECTION_METHOD)
                    .functionalInterface(Default.FUNCTIONAL_INTERFACE)
                    .clazz(Class::toGenericString)
                    
                    .customStringifies(new HashMap<>())
                    .objectToString(Objects::toString);
        }
        
        
        public static class Default {
        
            public static final DecimalFormat DECIMAL_PATTERN = new DecimalFormat("#,###.0000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            public static final DateTimeFormatter LOCAL_DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            public static final DateTimeFormatter LOCAL_DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            public static final DateTimeFormatter LOCAL_TIME_PATTERN = DateTimeFormatter.ofPattern("HH:mm:ss");
            public static final SimpleDateFormat DATE_PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            public static final SimpleDateFormat CALENDAR_PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            /**
             * Example
             * value:  "   aaabbbccc   "
             * result: "   aaabbbccc   "
             */
            public static final TypeFormatter<String> STRING = str -> '"' + str + '"';
            public static final TypeFormatter<Number> DECIMAL = DECIMAL_PATTERN::format;
            
            public static final TypeFormatter<LocalDateTime> LOCAL_DATE_TIME = LOCAL_DATE_TIME_PATTERN::format;
            public static final TypeFormatter<LocalDate> LOCAL_DATE = LOCAL_DATE_PATTERN::format;
            public static final TypeFormatter<LocalTime> LOCAL_TIME = LOCAL_TIME_PATTERN::format;
            public static final TypeFormatter<Date> DATE = DATE_PATTERN::format;
            public static final TypeFormatter<Calendar> CALENDAR = calendar -> DATE_PATTERN.format(calendar.getTime());
            
            public static final TypeFormatter<List<?>> LIST = Object::toString;
            public static final TypeFormatter<Object> ARRAY = new ArrayFormatterImpl();
            public static final TypeFormatter<Map<?, ?>> MAP = java.util.Map::toString;
            
            public static final TypeFormatter<Method> REFLECTION_METHOD = Method::toGenericString;
            public static final TypeFormatter<Class<?>> FUNCTIONAL_INTERFACE = new LambdaFormatterImpl();
            
        }
        
    }
    
}
