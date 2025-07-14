package io.github.loputevs.printtable.design;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;


public interface Column<ROW_DATA, COLUMN_DATA> {
    
    static <ROW_DATA, COLUMN_DATA> Column<ROW_DATA, COLUMN_DATA> of(
            String name, Function<ROW_DATA, COLUMN_DATA> getter) {
        // TODO: 19.11.2024 impl
        return null;
    }
    
    /**
     * All values in this column must be colored with {@param color}.
     *
     * @see Column#color(Color, BiPredicate)
     */
    Column<ROW_DATA, COLUMN_DATA> color(Color color);
    
    /** @see Column#color(Color, BiPredicate) */
    Column<ROW_DATA, COLUMN_DATA> color(Color color, Predicate<COLUMN_DATA> colorizeCellValue);
    
    /**
     * Устанавливает проверку нужно ли покрасить это cell value by {@param color}
     * Если проверок несколько, выигрывает та, что была установлена последний.
     * @implNote TODO - Можно идти с конца - когда более Predicate
     */
    Column<ROW_DATA, COLUMN_DATA> color(Color color, BiPredicate<ROW_DATA, COLUMN_DATA> colorizeCellValue);
    
    Column<ROW_DATA, COLUMN_DATA> align(Align align);
    
    Column<ROW_DATA, COLUMN_DATA> width(Width width);
    
    Column<ROW_DATA, COLUMN_DATA> formatter(Width width);
    
    enum Align {LEFT, RIGHT}
    
    /**
     * TODO - dropBothProportionallyIfMoreThat
     */
    class Width {
        public static Width dropLeftIfMoreThat(int chars) {return null;}
        
        public static Width dropRightIfMoreThat(int chars) {return null;}
        
        public static final Width AUTOFIT = new Width();
    }
    enum Color {
        BLUE, RED, YELLOW, GREEN, ORANGE;
    }
}


class ColumnImpl<ROW_DATA, COLUMN_DATA> {
    private String columnName;
    private Function<ROW_DATA, COLUMN_DATA> getter;
    
    public static <ROW_DATA, COLUMN_DATA> Column<ROW_DATA, COLUMN_DATA> of(
            String name, Function<ROW_DATA, COLUMN_DATA> getter) {
        // TODO: 19.11.2024 impl
        return null;
    }
    
    /**
     * TODO - doc
     */
    public Column<ROW_DATA, COLUMN_DATA> color(Column.Color color) {
        // TODO: 19.11.2024 impl
        return null;
    }
}
