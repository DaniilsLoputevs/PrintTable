SPECIFICATION

- Data types stringifies

Для отображения объектов необходимо их трансформировать в строку(format). 
Эта страница описывает стандартные трансформации, что применяются по умолчанию для указанных типов. 

This table describe default stringify if local stringifies not provided

| Data type                             | Object                                                              | Result                                                                                                               | Format                                         | Comment                                                                                                             |
|---------------------------------------|---------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| String                                | `"   aaabbbccc   "`                                                 | `"   aaabbbccc   "`                                                                                                  | "string value"                                 | default toString() wrapped by double quotes to clearly show empty characters at the beginning and end of the string |
| decimal (double & float & DigDecimal) | `2024.09D` & `2024.09F` & `new BigDecimal("2024.09")`               | `2,024.0900`                                                                                                         | #,###.0000                                     | https://www.baeldung.com/java-decimalformat                                                                         |
| LocalDateTime                         | `LocalDateTime.of(LocalDate.of(2024, 9, 28), LocalTime.of(18, 30))` | `2024-09-28 18:30`                                                                                                   | yyyy-MM-dd HH:mm:ss                            | More easy to read that default toString()                                                                           |
| LocalDate                             | `LocalDate.of(2024, 9, 28)`                                         | `2024-09-28`                                                                                                         | yyyy-MM-dd                                     | ISO-8601 (default toString())                                                                                       |
| LocalTime                             | `LocalTime.of(18, 30)`                                              | `18:30`                                                                                                              | HH:mm:ss                                       | ISO-8601 (default toString())                                                                                       |
| List & Array                          | `List.of(123, 456, 789)` & `int[] {123, 456, 789}`                  | `[123, 456, 789]`                                                                                                    | List.toString() & Arrays.toString()            | default toString()                                                                                                  |
| Map                                   | `Map.of("aaa", 111, "bbb", 222, "ccc", 333)`                        | `{aaa=111, bbb=222, ccc=333}`                                                                                        | Map.toString()                                 | default toString()                                                                                                  |
| java.lang.reflect.Method (Reflection) | `java.util.Collections#sort(list)`                                  | `public static <T extends java.lang.Comparable<? super T>> void java.util.Collections.sort(java.util.List<T>)`       | Full method declaration signature              | Show full method signature                                                                                          |
| (lambda & method reference)           | `Function<String, Boolean> ref = "aaabbbccc"::contains`             | `Function<java.lang.String, java.lang.Boolean> examples.format.TypeFormatterTest$$Lambda/0x000002a0810aa440` | Functional interface + class path + class name | resolve Functional interface + class path where lambda was declared + lambda generated name                         |
|                                       |                                                                     |                                                                                                                      |                                                |                                                                                                                     |

Примечание к таблице:
* Для всех указанных типов, stringifies работает так же с подтипами указанных Типов. (Работает с иерархии).
* Если 


Вы можете указать stringify для других классов используя API 
// TODO : пример кода stringify для кастомного класса
```java

```
Такие stringify будут работать только с указанными типами (НЕ Работает с иерархии). 
Идея этого API, простым способом локально переопределить toString() только для требуемых классов, 
в случая когда нет возможности сделать реальный override toString(), например для классов из внешних библиотек.

~~Если ни объект в runtime не совпадает ни с одним вариантом, то будет использован Object.toString(). ~~



* TODO : пример кода stringify для кастомного класса
* TODO : global & local configs example
* collection custom stringify
* collection apply cascade custom stringify 
