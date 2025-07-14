package examples.format;

import io.github.loputevs.printtable.formatter.CellValueFormatter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TypeFormatterTest {
    private final CellValueFormatter stringifier = new CellValueFormatter();
    
    @Test void localDateTime() {
        test(LocalDateTime.of(LocalDate.of(2024, 9, 28), LocalTime.of(18, 30, 45)), "2024-09-28 18:30:45");
    }
    
    @Test void localDate() {
        test(LocalDate.of(2024, 9, 28), "2024-09-28");
    }
    
    @Test void localTime() {
        test(LocalTime.of(18, 30, 45), "18:30:45");
    }
    
    @Test void date() {
        Calendar calendar = new GregorianCalendar(2024, Calendar.SEPTEMBER, 28, 18, 30, 45);
        test(calendar.getTime(), "2024-09-28 18:30:45");
    }
    
    @Test void calendar() {
        Calendar calendar = new GregorianCalendar(2024, Calendar.SEPTEMBER, 28, 18, 30, 45);
        test(calendar, "2024-09-28 18:30:45");
    }
    
    @Test void String() {
        test("   aaabbbccc   ", "\"   aaabbbccc   \"");
    }
    
    @Test void double_float_DigDecimal() {
        val expect = "2,024.0900";
        test(2024.09D, expect);
        test(2024.09F, expect);
        test(new BigDecimal("2024.09"), expect);
    }
    
    @Test void List_Array() {
        test(Arrays.asList(123, 456, 789), "[123, 456, 789]");
        test(new int[]{123, 456, 789}, "[123, 456, 789]");
    }
    
    /**
     * Text impl note:<br>
     * {@link HashMap} and other {@link Map} impls (exclude {@link LinkedHashMap}) hasn't element order, it means that map created like this <br>
     * {@code Map.of("aaa", 111, "bbb", 222, "ccc", 333)} <br>
     * can be stringify like this <br>
     * "{aaa=111, ccc=333, bbb=222}"
     */
    @Test void Map() {
        test(mapOf("aaa", 111, "bbb", 222, "ccc", 333), "{aaa=111, ccc=333, bbb=222}");
    }
    
    @Test @SneakyThrows void reflection_method() {
        test(Collections.class.getMethod("sort", List.class), "public static <T extends java.lang.Comparable<? super T>> void java.util.Collections.sort(java.util.List<T>)");
    }
    
    @Test void lambda() {
        Consumer<String> refConsumer = System.out::println;
        test(refConsumer, "(Consumer<T>) examples.format.TypeFormatterTest$$Lambda/");
        
        Function<String, Boolean> refFunction = "aaabbbccc"::contains;
        test(refFunction, "(Function<T,R>) examples.format.TypeFormatterTest$$Lambda/");
        
        Predicate<String> refPredicate = "aaabbbccc"::contains;
        test(refPredicate, "(Predicate<T>) examples.format.TypeFormatterTest$$Lambda/");
        
        Supplier<String> refSupplier = () -> "aaabbbccc";
        test(refSupplier, "(Supplier<T>) examples.format.TypeFormatterTest$$Lambda/");
    }
    
    
    @Test void Object() {
        test(new POJO("POJO Name"), "POJO Name");
    }
    
    @Test void CustomObject() {
        stringifier.getConfig().customStringifier(POJO.class, pojo -> pojo.name.substring(0, 5));
        test(new POJO("POJO Name"), "POJO");
        stringifier.getConfig().removeStringifier(POJO.class);
    }
    
    
    /* PRIVATE */
    
    private void test(Object obj, String expected) {
        val rsl = stringifier.apply(obj);
        Assertions.assertTrue(rsl.contains(expected), () -> System.lineSeparator() +
                "expect: \"" + expected + '"' + System.lineSeparator() +
                "actual: \"" + rsl + '"' + System.lineSeparator()
        );
    }
    
    private <K, V> Map<K, V> mapOf(K key1, V value1, K key2, V value2, K key3, V value3) {
        Map<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        return map;
    }
    
    @RequiredArgsConstructor
    private static class POJO {
        private final String name;
        
        @Override public String toString() {
            return name;
        }
    }
    
    
    /* EXP */
    
    
}
