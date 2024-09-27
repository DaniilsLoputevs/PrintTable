package examples;

import common.Task;
import io.github.loputevs.printtable.PrintTable;
import io.github.loputevs.printtable.PrintTable.CellColour;
import lombok.val;
import lombok.var;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.loputevs.printtable.PrintTable.AlignSide.LEFT;
import static io.github.loputevs.printtable.PrintTable.AlignSide.RIGHT;
import static io.github.loputevs.printtable.PrintTable.Colour.*;
import static io.github.loputevs.printtable.PrintTable.DefaultFormatterBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DONE:
 * TODO : colours & fix problem with (line size + formatter) ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : PrintTable.ofStream() && .append() && print() as Terminal operations ^^^^^^^^^^^^^^^^^^^^^
 * TODO : .columnRowCount(String name, String pattern) ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : append to table name: element count (do it automatically) ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : formatting for Numbers ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : formatting for DateTime ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : formatting for collections - show only size of ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : output to (Appendable || String || Consumer<String>) ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : columnOptions.alignSide ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : columnOptions ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : more documentation ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : buildEachLine (append Iterable || Stream) ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : count row for insert to SB into start with TableName ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * TODO : compile on Java9 (java8 with replace var -> actual variable type) ^^^^^^^^^^^^^^^^^^^^^^^^
 * </p>
 * FUTURE:
 * TODO : sort & filter operations - for append data -----------------------------------------------------------------------
 * TODO : all terminal operation start with to...() [toConsumer(), toPrint(), toAppendable]
 * TODO : serialize & deserialize by existing config (toString() & fromString() ) ------------------------------------------
 * TODO : impl - base method appendCellValueTo(StringBuilder sb)
 * TODO : getTableData & receive data for declare column value (excel formula? )
 * TODO : ColumnOptions (ColOpt) || Modifier -> Kotlin Compose Modifier (global factory) + cellColor(color) & cellColor(color, predicate<CellValue>) & cellColor(color, BiPredicate<Row, CellValue>)
 * TODO : ??? replace ArrayList content -> Iterator content
 * TODO : ??? rename getValue -> cellValueGetter
 * TODO : ??? rename generic names
 * TODO : optional table size + custom size table part
 * TODO : separate toString() & build() - toString used to Debug -_-
 * TODO : more tests & docs
 * TODO : split interface with doc from impl
 * TODO : спека на размер колонок
 * TODO : todos
 */
public class PrintTableTest {
    private static final String LS = System.lineSeparator();
    private static final Task
            ONE = new Task(10, "aaa", Task.Priority.LOW, LocalDateTime.MIN, 111.14, null, new String[]{"v1"}),
            TWO = new Task(20, "bbb", Task.Priority.MIDDLE, LocalDateTime.parse("2023-04-18T16:46:49.912727800", DateTimeFormatter.ISO_LOCAL_DATE_TIME), 1720.50, null, new String[]{"v2"}),
            THREE = new Task(30, "ccc", Task.Priority.HIGH, LocalDateTime.MAX, 2496.98, null, new String[]{"v1", "v2"});
    
    public static final List<Task> TASKS = Arrays.asList(
            ONE, TWO, THREE,
            new Task(40, "ddd", Task.Priority.LOW, null, 2000.50, Arrays.asList(ONE, TWO, THREE), null)
    );
    
    @Test
    void print_autoFit_name_elemIndex() {
        var oldSysOut = System.out;
        var myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        
        PrintTable.of(TASKS).name("Test data")
                .columnElemIndex()
                .column("ID", Task::getId)
                .column("NAME", Task::getName)
                .column("PRIORITY", Task::getPriority)
                .column("DEADLINE", Task::getDeadline)
                .column("PRICE", Task::getPrice)
                .column("RELATED", Task::getRelatedTask)
                .column("TAGS", Task::getTags)
                .print();
        
        
        var expected = new StringJoiner(LS)
                .add("Test data (table size: 4)")
                .add("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("| # | ID | NAME | PRIORITY | DEADLINE                            | PRICE   | RELATED                                                                                                                                                                                                                                                                                     | TAGS     |")
                .add("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("| 0 | 10 | aaa  | LOW      | -999999999-01-01T00:00              | 111.14  | null                                                                                                                                                                                                                                                                                        | [v1]     |")
                .add("| 1 | 20 | bbb  | MIDDLE   | 2023-04-18T16:46:49.912727800       | 1720.5  | null                                                                                                                                                                                                                                                                                        | [v2]     |")
                .add("| 2 | 30 | ccc  | HIGH     | +999999999-12-31T23:59:59.999999999 | 2496.98 | null                                                                                                                                                                                                                                                                                        | [v1, v2] |")
                .add("| 3 | 40 | ddd  | LOW      | null                                | 2000.5  | [Task[id=10, name='aaa', priority=LOW, deadline=-999999999-01-01T00:00, price=111.14], Task[id=20, name='bbb', priority=MIDDLE, deadline=2023-04-18T16:46:49.912727800, price=1720.5], Task[id=30, name='ccc', priority=HIGH, deadline=+999999999-12-31T23:59:59.999999999, price=2496.98]] | null     |")
                .add("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("").add("") // TODO: 27.09.2024 - странно, почему-то тут 2 LS в конце в actual
                .toString();
        System.setOut(oldSysOut);
        assertEquals(expected, myOut.toString());
    }
    
    @Test
    void toAppender_noName_noElemIndex_align_minWidth() {
        var rsl = new StringBuilder();
        PrintTable.of(TASKS)
                .column("ID", Task::getId)
                .column("NAME", Task::getName)
                .column("PRIORITY", Task::getPriority)
                .column("DEADLINE", Task::getDeadline, null, op -> op.align(LEFT).minWidth(40))
                .column("PRICE", Task::getPrice)
                .column("RELATED", Task::getRelatedTask, null, op -> op.align(RIGHT))
                .column("TAGS", Task::getTags)
                
                .toAppendable(rsl);
        
        var expected = new StringJoiner(LS)
                .add("")
                .add("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("| ID | NAME | PRIORITY | DEADLINE                                 | PRICE   |                                                                                                                                                                                                                                                                                    RELATED | TAGS     |")
                .add("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("| 10 | aaa  | LOW      | -999999999-01-01T00:00                   | 111.14  |                                                                                                                                                                                                                                                                                       null | [v1]     |")
                .add("| 20 | bbb  | MIDDLE   | 2023-04-18T16:46:49.912727800            | 1720.5  |                                                                                                                                                                                                                                                                                       null | [v2]     |")
                .add("| 30 | ccc  | HIGH     | +999999999-12-31T23:59:59.999999999      | 2496.98 |                                                                                                                                                                                                                                                                                       null | [v1, v2] |")
                .add("| 40 | ddd  | LOW      | null                                     | 2000.5  | [Task[id=10, name='aaa', priority=LOW, deadline=-999999999-01-01T00:00, price=111.14], Task[id=20, name='bbb', priority=MIDDLE, deadline=2023-04-18T16:46:49.912727800, price=1720.5], Task[id=30, name='ccc', priority=HIGH, deadline=+999999999-12-31T23:59:59.999999999, price=2496.98]] | null     |")
                .add("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("")
                .toString();
        
        assertEquals(expected, rsl.toString());
    }
    
    /**
     * @implNote test have specific, output contains invisible chars.
     * In console, you should see OK table, but in expected value we must include this symbols and so, it's brake pretty view in test case.<br>
     * Normal view present below + colourised some cell value
     *
     * <pre>
     * +--------------------------------------------------------------------------------------------------------------------------------------------------------------+
     * | ID | NAME | DEADLINE(Default)         | DEADLINE(Date)   | DEADLINE(Time) | PRICE     | PRICE(Money) | RELATED    | RELATED(Count) | TAGS                    |
     * +--------------------------------------------------------------------------------------------------------------------------------------------------------------+
     * | 20 | bbb  | 2023-04-18 16:46:49       | 2023-04-18       | 16:46          | 1720,5000 | 1720,50      | ()         | 0              | ****secret is hided**** |
     * | 30 | ccc  | +999999999-12-31 23:59:59 | +999999999-12-31 | 23:59          | 2496,9800 | 2496,98      | ()         | 0              | ****secret is hided**** |
     * | 40 | ddd  | null                      | null             | null           | 2000,5000 | 2000,50      | (10,20,30) | 3              | ****secret is hided**** |
     * +--------------------------------------------------------------------------------------------------------------------------------------------------------------+
     * </pre>
     */
    @Test
    void toAppender_ofAppendDataLater_colours_formatters() {
        var rsl = new StringBuilder();
        var priceColours = Arrays.asList(
                new CellColour<Task>(RED, task -> task.getPrice() >= 2000),
                new CellColour<Task>(GREEN, task -> task.getPrice() <= 2000)
        );
        Function<Collection<Task>, String> relatedTasksJoiningId =
                it -> Optional.ofNullable(it).orElse(Collections.emptyList()).stream()
                        .map(Task::getId).map(String::valueOf).collect(Collectors.joining(",", "(", ")"));
        
        var table = PrintTable.<Task>ofAppendDataLater()
                .column("ID", Task::getId)
                .column("NAME", Task::getName, null, op -> op.cellColours(YELLOW, task -> task.getRelatedTask() == null))
                .column("DEADLINE(Default)", Task::getDeadline, localDateTime())
                .column("DEADLINE(Date)", Task::getDeadline, localDateTime("yyyy-MM-dd"))
                .column("DEADLINE(Time)", Task::getDeadline, localDateTime("HH:mm"))
                .column("PRICE", Task::getPrice, decimal(), op -> op.cellColours(priceColours))
                .column("PRICE(Money)", Task::getPrice, decimal("####.00"))
                .column("RELATED", Task::getRelatedTask, relatedTasksJoiningId)
                .column("RELATED(Count)", Task::getRelatedTask, iterableSize())
                .column("TAGS", Task::getTags, tags -> "****secret is hided****");
        TASKS.stream().filter(it -> it.getId() > 10).forEach(table::append);
        table.toAppendable(rsl);
        table.print();
        
        String expected = new StringJoiner(LS)
                .add("")
                .add("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("| ID | NAME | DEADLINE(Default)         | DEADLINE(Date)   | DEADLINE(Time) | PRICE     | PRICE(Money) | RELATED    | RELATED(Count) | TAGS                    |")
                .add("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("| 20 | " + "\u001B[33mbbb\u001B[0m" + "  | 2023-04-18 16:46:49       | 2023-04-18       | 16:46          | " + "\u001B[32m1720,5000\u001B[0m" + " | 1720,50      | ()         | 0              | ****secret is hided**** |")
                .add("| 30 | " + "\u001B[33mccc\u001B[0m" + "  | +999999999-12-31 23:59:59 | +999999999-12-31 | 23:59          | " + "\u001B[31m2496,9800\u001B[0m" + " | 2496,98      | ()         | 0              | ****secret is hided**** |")
                .add("| 40 | ddd  | null                      | null             | null           | " + "\u001B[31m2000,5000\u001B[0m" + " | 2000,50      | (10,20,30) | 3              | ****secret is hided**** |")
                .add("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+")
                .add("")
                .toString();
        
        assertEquals(expected, rsl.toString());
    }
    
    @Test
    void mixOf_of_and_ofAppendDataLater() {
        var rsl = new StringBuilder();
        var table = PrintTable.<Task>of(Collections.emptyList())
                .name("Test data")
                .columnElemIndex()
                .column("ID", Task::getId)
                .column("NAME", Task::getName);
        TASKS.stream().filter(it -> it.getId() >= 30).forEach(table::append);
        table.toAppendable(rsl);
        
        val expected = new StringJoiner(LS)
                .add("Test data (table size: 2)")
                .add("+---------------+")
                .add("| # | ID | NAME |")
                .add("+---------------+")
                .add("| 0 | 30 | ccc  |")
                .add("| 1 | 40 | ddd  |")
                .add("+---------------+")
                .add("")
                .toString();
        
        assertEquals(expected, rsl.toString());
    }
    
}
