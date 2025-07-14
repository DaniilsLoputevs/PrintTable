package examples.design;

import io.github.loputevs.printtable.PrintTable;
import io.github.loputevs.printtable.Source;
import io.github.loputevs.printtable.design.*;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.Objects;

/**
 * - Builder VS newDefault - что лучше?
 */
public class Design {
    @Getter static class User{ String name;}
    void example() {
        val stringSource = Source.ofClass(String.class);
        PrintTableNew.of(new ArrayList<String>())
//                .config(ConfigNew.newDefault())
                .config(ConfigNew.newDefault()
//                        .tableName()
                        .tableName(tableSize -> "Table of String #" + tableSize)
//                      .tableCellValueFormatter(TableCellValueFormatter.newDefault())
                        .tableCellValueFormatter(TableCellValueFormatter.builder()
//                              .primitiveFormatter(PrimitiveFormatter.newDefault())
                                .primitiveFormatter(PrimitiveFormatter.builder()
                                        .decimalFormatter(Objects::toString).build())
                                .customTypeFormatter(CustomTypeFormatter.newDefault()
                                        .addFormatter(RuntimeException.class, Throwable::getMessage))
                                .build()
                        )

                )
                .source(stringSource)
                .columnElementIndex()
                .column(Column.of("content", String::toString))
                .column(Column.of("length", String::length)
                        .color(Column.Color.BLUE, (s, integer) -> true )
                        .width(Column.Width.AUTOFIT)
                        .formatter()
                )
                
                .filter(it -> true)
                .sort(String::compareTo)
                
                .toResult()
//                .toIterable()
//                .toConsumer()
//                .toAppendable()
//                .toPrint()
//                .toText()
        ;

    }

    /**
     * Создать из Iterable(Collection, Stream, )
     * subscribe to reactive flow (push data to Source)
     * - align
     * - - Policy [autoFit, fixed chars]
     * - - side [LEFT, RIGHT, ?CENTER]
     * - -
     */
    public static void main(String[] args) {
        PrintTableNew
                .of(iterable)
//                .ofClassAutoGenerateColumns(Class<*>)

                .config(Config
                                .tableName()
//                            .tableSize()  .tableSize(size -> String)

                                .formatter((clazz)) // todo think about DSL
                                .formatter(CellValueFormatter.Config.newDefaultBuilder()) /* static import */
                                .tableCellValueFormatter(TableCellValueFormatter.newDefault())
                                .tableCellValueFormatter(TableCellValueFormatter.newDefault()
                                        .custom)
                )

                .source(Source.name())

                .columnElementIndex()
                .column(Column.valueGetter().name()
                        .color(Color)
                        .color(Color, (cellValue) -> Boolean)
                        .color(Color, (cellValue, Row) -> Boolean)
                        .align(LEFT || RIGHT)
                        .width(AUTOFIT || fixedSizePolicy(dropLeft, dropRight, dropBothProportionally))
                        .formatter(TypeFormatterBuilder.decimal()) /* static import */
                        .formatter(TypeFormatterBuilder.localDate()) /* static import */
                        .formatter(TypeFormatterBuilder.localTime()) /* static import */
                        .formatter(TypeFormatterBuilder.localDateTime()) /* static import */
                )

                .filter(Predicate)
                .sort(Comparator)
//                .drop(Predicate)


                .toResult() :TableProcesResult
                .toIterable()
                .toConsumer()
                .toAppendable(java.lang.Appendable)
                .toPrint()
                .toText()
    }

}
