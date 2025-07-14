package examples.format;

import io.github.loputevs.printtable.formatter.CellValueFormatter;
import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class StringifyTest {
    
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println();
//        Predicate<String> objectMethodReference  = "String"::contains;
        Function<String, Boolean> objectMethodReference = "String"::contains;
        System.out.println(objectMethodReference.toString());
        System.out.println(objectMethodReference.getClass().isAnonymousClass());
        System.out.println(objectMethodReference.getClass().getCanonicalName());
        System.out.println(objectMethodReference.getClass().getSimpleName());
        System.out.println("--" + objectMethodReference.getClass().getTypeName());
        System.out.println("--" + objectMethodReference.getClass().getName());
        System.out.println(objectMethodReference.getClass().toGenericString());
        System.out.println(Arrays.toString(objectMethodReference.getClass().getInterfaces()));
        System.out.println(objectMethodReference.getClass().getInterfaces()[0].toGenericString());
//        System.out.println(objectMethodReference.getClass().getInterfaces()[0].descriptorString());
        System.out.println(objectMethodReference.getClass().isLocalClass());
        
        Function<String, Integer> a = (String s) -> {return 123;};
        System.out.println();
        System.out.println(a.toString());
        System.out.println(a.getClass().isAnonymousClass());
        System.out.println(a.getClass().getCanonicalName());
        System.out.println();

//        List<String> list = new ArrayList<String>();
//        Class<?> clazz = list.getClass();
//        Class<?> clazz = list.getClass();
//        Method method = clazz.getMethod("add", Object.class);
//        Method method = clazz.getDeclaredMethod("contains", Object.class);
//        Method method = Collections.class.getMethod("copy", List.class, List.class);
        Method method = Collections.class.getMethod("sort", List.class);
        System.out.println(method.toString());
        System.out.println(method.toGenericString());
        
        int[][] arr1 = {{1, 2}, {3, 4}};
        val arrClass = arr1.getClass();
//        System.out.println(arrClass.getSimpleName());
//        System.out.println(arrClass.getTypeName());
//        System.out.println(arrClass.getCanonicalName());
        int[] array = {123, 456, 789};
        System.out.println("array");
        System.out.println(Arrays.toString(array));
        
        val list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        System.out.println("list");
        System.out.println(list);
        
        val map = new HashMap<String, Integer>();
        map.put("aaa", 111);
        map.put("bbb", 222);
        System.out.println("map");
        System.out.println(map);


//        new BigDecimal().getClass().getDeclaredMethod().toGenericString()
//        SimpleDateFormat
//        LocalDateTime.of(LocalDate.of(2024, 9, 28).toString(), LocalTime.of(18, 30)).toString();
        
        val formatterMy = new CellValueFormatter();
        Function<String, String> lambdaString = s -> "true";
        System.out.println(formatterMy.apply(objectMethodReference));
    }
}
