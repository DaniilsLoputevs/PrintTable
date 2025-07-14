# PrintTable

## Составные части
- Source: element array, subscribe
- Column: getter, typeFormatter, Modifier
  - value typeFormatter
- Fetcher: print, appender

- Table
  - IndexColumn
  - TableName (options)
    - count table elements
  - Source
    - array|list elements
    - subscribe to element provider 
  - Modifier
    - sort
    - filter
    - drop if
- Column
  - value getter
  - Modifier
    - align: [LEFT, RIGHT]
    - width
    - value typeFormatter
    - value colour processor
- Fetcher
  - print
  - appendTo

Features:
- multi times usages
- ? concurrent
- terminate OR subscribe Stream
- default data typeFormatter
- 


Pipeline
- collect sources
- get values for cells (Rows x Columns) + ?cache with index
- format values (data typeFormatter, colour, + custom processors)
- fetch to appender





Modifier
- table (sort, filter, drop, cache(hashCode, CustomIndex,)
- column (minWidth, CellColor,

append (Stream(peek), Iterable

make multi time usable + cache - for react & UI with multi thread

SourceContept

subscribe To Stream















