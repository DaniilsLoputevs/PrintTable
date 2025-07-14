package io.github.loputevs.printtable.formatter.impl;

import io.github.loputevs.printtable.formatter.TypeFormatter;
import lombok.val;

/**
 * Java compiler generate synthetic classes for lambda & method reference syntax.
 * <pre><code>
 *      Consumer<String> lambdaObject = (s) -> System.out.println(s);
 *      Consumer<String> lambdaObject = System.out::println;
 * </code></pre>
 * For this classes possible to extract formal generic parameter but we must use {@link Class} private methods
 * This is very bad way and I think it's not required for this moment.
 * <p>
 * Here I use a hardcoded list of possible lambda interfaces with declared formal generic parameters.
 *
 * @see LambdaFormatterUtil
 */
public class LambdaFormatterImpl implements TypeFormatter<Class<?>> {
    
    @Override public String apply(Class<?> lambdaImplClass) {
        val lambdaInterface = lambdaImplClass.getInterfaces()[0]; /* lambda always has 1 interface OR it's not a lambda =( */
        val lambdaPrefix = LambdaFormatterUtil.parseLambdaDeclarationPrefix(lambdaInterface);
        val lambdaClassPathAndName = lambdaImplClass.getTypeName();
        return lambdaPrefix + " " + lambdaClassPathAndName;
    }
    
}
