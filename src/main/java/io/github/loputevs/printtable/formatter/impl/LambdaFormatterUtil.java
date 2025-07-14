package io.github.loputevs.printtable.formatter.impl;

import java.util.function.*;

public final class LambdaFormatterUtil {
    
    public static String parseLambdaDeclarationPrefix(Class<?> lambdaInterface) {
        // Simple interfaces
        if (lambdaInterface == Function.class) return "(Function<T,R>)";
        if (lambdaInterface == Consumer.class) return "(Consumer<T>)";
        if (lambdaInterface == Predicate.class) return "(Predicate<T>)";
        if (lambdaInterface == Supplier.class) return "(Supplier<T>)";
        
        // Operators
        if (lambdaInterface == UnaryOperator.class) return "(UnaryOperator<T>)";
        if (lambdaInterface == BinaryOperator.class) return "(BinaryOperator<T>)";
        
        // Two arguments
        if (lambdaInterface == BiConsumer.class) return "(BiConsumer<T,U>)";
        if (lambdaInterface == BiFunction.class) return "(BiFunction<T,U,R>)";
        if (lambdaInterface == BiPredicate.class) return "(BiPredicate<T,U>)";
        
        // Primitive functions
        if (lambdaInterface == ToDoubleFunction.class) return "(ToDoubleFunction<T>)";
        if (lambdaInterface == ToIntFunction.class) return "(ToIntFunction<T>)";
        if (lambdaInterface == ToLongFunction.class) return "(ToLongFunction<T>)";
        if (lambdaInterface == DoubleFunction.class) return "(DoubleFunction<R>)";
        if (lambdaInterface == IntFunction.class) return "(IntFunction<R>)";
        if (lambdaInterface == LongFunction.class) return "(LongFunction<R>)";
        
        // Specific mapper for types
        if (lambdaInterface == DoubleToIntFunction.class) return "(DoubleToIntFunction)";
        if (lambdaInterface == DoubleToLongFunction.class) return "(DoubleToLongFunction)";
        if (lambdaInterface == IntToDoubleFunction.class) return "(IntToDoubleFunction)";
        if (lambdaInterface == IntToLongFunction.class) return "(IntToLongFunction)";
        if (lambdaInterface == LongToDoubleFunction.class) return "(LongToDoubleFunction)";
        if (lambdaInterface == LongToIntFunction.class) return "(LongToIntFunction)";
        
        // Primitive mapper
        if (lambdaInterface == DoubleConsumer.class) return "(DoubleConsumer)";
        if (lambdaInterface == IntConsumer.class) return "(IntConsumer)";
        if (lambdaInterface == LongConsumer.class) return "(LongConsumer)";
        
        // Object + primitive arguments Consumer
        if (lambdaInterface == ObjDoubleConsumer.class) return "(ObjDoubleConsumer<T>)";
        if (lambdaInterface == ObjIntConsumer.class) return "(ObjIntConsumer<T>)";
        if (lambdaInterface == ObjLongConsumer.class) return "(ObjLongConsumer<T>)";
        
        return "(unknown lambda type)";
    }
    
}
