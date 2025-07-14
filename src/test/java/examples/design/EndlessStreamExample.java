//package examples.design;
//
//import io.github.loputevs.printtable.PrintTable;
//import io.github.loputevs.printtable.Source;
//
//import java.util.stream.Stream;
//
//public class EndlessStreamExample {
//    public Stream<String > logStreamThenItWillBeTerminated(Stream<String> stream) {
////        Stream<String> stream = Stream.of("aaa", "bbb", "ccc");
//        Source<String> source = Source.ofMutable();
//        stream = source.subscribeToStream(stream); // new Stream<String> instance
//        PrintTable<String> printTable = PrintTable.of(source).columnElementIndex();
//        stream = stream.onClose(printTable::print); // new Stream<String> instance
//        return stream;
//    }
//}
