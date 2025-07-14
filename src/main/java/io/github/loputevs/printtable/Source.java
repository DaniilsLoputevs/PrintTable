package io.github.loputevs.printtable;

import io.github.loputevs.printtable.design.PrintTableNew;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Source of row data for {@link PrintTableNew} used as data block that has own place in result table
 *
 * @param <ROW_DATA> //
 */
public interface Source<ROW_DATA> extends Iterable<ROW_DATA> {
    
    static <ROW_DATA> Source<ROW_DATA> ofClass(Class<ROW_DATA> clazz) {return new SourceImpl<ROW_DATA>();}
    
    static <ROW_DATA> Source<ROW_DATA> of(Iterable<ROW_DATA> elements) {return new SourceImpl<>(elements);}
    
    static <ROW_DATA> Source<ROW_DATA> of(Collection<ROW_DATA> elements) {return new SourceImpl<>(elements);}
    
    static <ROW_DATA> Source<ROW_DATA> of(ROW_DATA[] elements) {return new SourceImpl<>(elements);}
    
    /**
     * Terminate {@link Stream} and create a {@link PrintTableNew} of stream content as table content.
     * <p>
     * DON'T USE IT FOR ENDLESS STREAM!!! <br>
     * For Endless {@link Stream} use code like this
     * <p>
     * Example 1 <pre><code>stream.peek(this::submit)</code></pre><br>
     * Example 2 <pre><code>
     *         public Stream<String > logStreamThenItWillBeTerminated(Stream<String> stream) {
     * //        Stream<String> stream = Stream.of("aaa", "bbb", "ccc");
     *         Source<String> source = Source.ofMutable();
     *         stream = source.subscribeToStream(stream); // new Stream<String> instance
     *         PrintTable<String> printTable = PrintTable.of(source).columnElementIndex();
     *         stream = stream.onClose(printTable::print); // new Stream<String> instance
     *         return stream;
     *     }
     * </code></pre>
     */
    static <E> Source<E> ofTerminateStream(Stream<E> elements) {return new SourceImpl<>(elements.collect(Collectors.toList()));}
    
    
    // TODO: 28.09.2024 doc & examples!
    void addRowData(ROW_DATA rowData);
    
    /**
     * @return new instance of Stream! You must use this instance in stream chain!
     */
    default Stream<ROW_DATA> subscribeToStream(Stream<ROW_DATA> stream) {
        return stream.peek(this::addRowData);
    }
    
    /**
     * Reusing the method adds {@param filter} to the sort chain after
     * the last {@link Predicate} set.
     * <p>
     * {@param filter} will be called after the previous {@link Predicate}.
     */
    Source<ROW_DATA> filter(Predicate<ROW_DATA> predicate);
    
    /**
     * Repeated use of the method adds {@param comparator} to the sort chain after
     * the last {@link Comparator} set.
     * <p>
     * {@param comparator} will only be used if the previous
     * {@link Comparator} returned that both elements were equal.
     */
    Source<ROW_DATA> sort(Comparator<ROW_DATA> comparator);
    
    /* Iterator<ROW_DATA> iterator() */
    
    /**
     * @return stream of all content.
     */
    Stream<ROW_DATA> stream();
    
}

class SourceImpl<ROW_DATA> implements Source<ROW_DATA> {
    private final List<ROW_DATA> collection = new ArrayList<>();
    private Predicate<ROW_DATA> filter;
    private Comparator<ROW_DATA> sort;
    
    public SourceImpl() {}
    
    public SourceImpl(Iterable<ROW_DATA> collection) {collection.forEach(this.collection::add);}
    
    public SourceImpl(Collection<ROW_DATA> collection) {this.collection.addAll(collection);}
    
    public SourceImpl(ROW_DATA[] array) {this.collection.addAll(Arrays.asList(array));}
    
    
    @Override public void addRowData(ROW_DATA rowData) {collection.add(rowData);}
    
    @Override public Source<ROW_DATA> filter(Predicate<ROW_DATA> predicate) {
        if (filter == null) filter = predicate;
        else filter = filter.and(predicate);
        return this;
    }
    
    @Override public Source<ROW_DATA> sort(Comparator<ROW_DATA> comparator) {
        if (sort == null) sort = comparator;
        else sort.thenComparing(comparator);
        return this;
    }
    
    @Override public Iterator<ROW_DATA> iterator() {return collection.iterator();}
    
    /**
     * @implNote It makes no difference whether you Sort or Filter first,
     * the resulting data set and its order will be the same in both cases.
     */
    @Override public Stream<ROW_DATA> stream() {return collection.stream();}
}

//class StreamSubscribeSource<ROW_DATA> implements Source<ROW_DATA> {
//    private final List<ROW_DATA> subscribeAccumulator = new ArrayList<>();
//
//    public StreamSubscribeSource(Stream<ROW_DATA> stream) {
//        stream.peek(subscribeAccumulator::add);
//    }
//}
