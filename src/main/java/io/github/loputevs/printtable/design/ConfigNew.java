package io.github.loputevs.printtable.design;

//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.Function;

public interface ConfigNew {
    static ConfigNew newDefault() {
        // TODO: 19.11.2024 iml
        throw new RuntimeException();
    }
    
    /** Default */
    default ConfigNew tableName() {
        return this.tableName(currentTableSize -> "PrintTable (table size: " + currentTableSize + ')');
    }
    
    ;
    
    /**
     * Accept
     *
     * @param tableName parameter - currentTableSize
     *                  return - currentTableName
     */
    ConfigNew tableName(Function<Integer, String> tableName);
    
//    ConfigNew tableCellValueFormatter(TableCellValueFormatter.TableCellValueFormatterBuilder cellValueFormatterBuilder);
    ConfigNew tableCellValueFormatter(TableCellValueFormatter cellValueFormatter);
    
}
