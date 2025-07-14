package examples.format;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;
import java.util.function.Function;

public class StringifyTest2 {
    
    static void localMethod(String s) {
    
    }
    
    public static void main(String[] args) {
        class MyConsumerWithGenericArgument implements Consumer<String > {
            @Override public void accept(String s) {
                System.out.println(s);
            }
        }
        class MyConsumerWithGenericParameter<T> implements Consumer<T> {
            @Override public void accept(T s) {
                System.out.println(s);
            }
        }

//            Consumer<String> refConsumer = (s) -> System.out.println(s);           // java.lang.Object
//            Consumer<String> refConsumer = System.out::println;                    // java.lang.Object
//            Consumer<String> refConsumer = new MyConsumerWithGenericArgument();    // java.lang.String
//            Consumer<String> refConsumer = new MyConsumerWithGenericParameter();   // T
//            Consumer<String> refConsumer = new Consumer<String>() {                // java.lang.String
//                @Override public void accept(String s) {
//                    System.out.println(s);
//                }
//            };
        
        // Получаем generic тип
//            Type genericType = getGenericType(refConsumer.getClass());

//            System.out.println("Generic type: " + genericType.getTypeName()); // Ожидается java.lang.String
    }
    
    // Метод для получения generic-типа
    public static Type getGenericType(Class<?> clazz) {
        Type[] interfaces = clazz.getGenericInterfaces();
        
        for (Type iface : interfaces) {
            if (iface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) iface;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                
                if (typeArguments.length > 0) {
                    return typeArguments[0];
                }
            }
        }
        
        return Object.class; // Если generic-тип не найден
    }
    
    
    
    @Test void lambdaExp() {
        Function<String , Integer> lambda = (s) -> 1;
        
        val str = "asd";
        Class<?> clazz1 = str.getClass();
        Class<?> clazz2 = str.getClass().getClass();
        System.out.println(str);
        System.out.println(str.getClass() == String.class);
        System.out.println(clazz1 == clazz2);

//        Consumer<String> refConsumer = System.out::println;
//        test(refConsumer, "(Consumer<T>) examples.format.TypeFormatterTest$$Lambda/");
//
//        Function<String, Boolean> refFunction = "aaabbbccc"::contains;
//        test(refFunction, "(Function<T,R>) examples.format.TypeFormatterTest$$Lambda/");
//
//        Predicate<String> refPredicate = "aaabbbccc"::contains;
//        test(refPredicate, "(Predicate<T>) examples.format.TypeFormatterTest$$Lambda/");
//
//        Supplier<String> refSupplier = () -> "aaabbbccc";
//        test(refSupplier, "(Supplier<T>) examples.format.TypeFormatterTest$$Lambda/");
    }
}
