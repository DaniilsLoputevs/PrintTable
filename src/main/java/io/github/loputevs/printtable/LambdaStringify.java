package io.github.loputevs.printtable;

public class LambdaStringify {
//    public static class LambdaFormatterImpl implements TypeFormatter<Class<?>> {
//                @Override public String apply(Class<?> lambdaImplClass) {
//                    val lambdaClassPathAndName = lambdaImplClass.getTypeName();
//                    val lambdaInterface = lambdaImplClass.getInterfaces()[0]; /* lambda always has 1 interface OR it's not a lambda =( */
//                    if (lambdaInterface == Function.class) return "Function" + getActualGenericArguments(lambdaImplClass);
//                    if (lambdaInterface == Consumer.class) return "Consumer" + getActualGenericArguments(lambdaImplClass);
//                    if (lambdaInterface == Predicate.class) return "Predicate" + getActualGenericArguments(lambdaImplClass);
//                    if (lambdaInterface == Supplier.class) return "Supplier" + getActualGenericArguments(lambdaImplClass);
//                    throw new RuntimeException("Unhandled lambdaInterface: " + lambdaInterface);
//                }
//            }
//
//            private static String getActualGenericArguments(Class<?> lambdaImplClass) {
//                Type superClass = lambdaImplClass.getGenericInterfaces()[0]; // Извлекаем Functional interface
//                ParameterizedType parameterizedType = (ParameterizedType) superClass; // TODO: 08.10.2024 fix this
//                val genericArguments = parameterizedType.getActualTypeArguments();
//                return Arrays.stream(genericArguments).map(Type::getTypeName).collect(Collectors.joining(",", "<", ">"));
//            }
    
    // ==============

//    public static void main(String[] args) {
//        // Анонимный класс Function, где явно указаны типы String и Integer
//        Function<String, Integer> function = new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return s.length();
//            }
//        };
//
//        // Извлечение реальных типов из Function
//        extractGenericTypes(function);
//        System.out.println(CellValueFormatter.ConfigNew.Default.FUNCTIONAL_INTERFACE.apply(function.getClass()));
//    }
//
//    public static <T, R> void extractGenericTypes(Function<T, R> function) {
//
//        Type superClass = function.getClass().getGenericInterfaces()[0];  // Извлекаем интерфейс Function
//
//        if (superClass instanceof ParameterizedType) {
//            ParameterizedType parameterizedType = (ParameterizedType) superClass;
//            Type[] typeArguments = parameterizedType.getActualTypeArguments();
//
//            // Извлекаем типы аргументов и возвращаемого значения
//            System.out.println("Input Type: " + typeArguments[0]);    // Выведет java.lang.String
//            System.out.println("Return Type: " + typeArguments[1]);   // Выведет java.lang.Integer
//        }
//    }

}
