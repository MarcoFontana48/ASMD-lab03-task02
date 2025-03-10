import asmd.lab3.task02.e3.ListBuilder;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListBuilderFactoryImplTest {
    
    private final Function<ListBuilder<Integer>, ListBuilder<Integer>> addList = (builder) -> new ListBuilderImpl<>(Arrays.asList(1,
            2).stream().flatMap(i -> builder.build().stream()).toList());
    
    private final ListBuilder<Integer> fromElementTestList = new ListBuilderImpl<>(Arrays.asList(5).stream());
    
    @org.junit.jupiter.api.Test
    void testEmpty() {
        ListBuilder<Integer> empty = ListBuilderFactoryImpl.empty();
        assertEquals(Arrays.asList(), empty.build());
    }
    
    @org.junit.jupiter.api.Test
    void testFromElement() {
        ListBuilder<Integer> singleElement = fromElementTestList;
        ListBuilder<Integer> expected = new ListBuilderImpl<>(Arrays.asList(5));
        assertTrue(singleElement.addAll(fromElementTestList).build().equals(expected.build()));
    }
    
    @org.junit.jupiter.api.Test
    void testFromList() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        ListBuilder<Integer> expected = new ListBuilderImpl<>(list.stream());
        assertEquals(expected, fromList(list));
    }
    
    @org.junit.jupiter.api.Test
    void testJoin() {
        List<ListBuilder<Integer>> subLists = Arrays.asList(
                ListBuilderFactoryImpl.empty(),
                addList(fromElementTestList),
                ListBuilderFactoryImpl.empty()
        );
        
        ListBuilder<Integer> expected = new ListBuilderImpl<>(Arrays.asList(5).stream());
        assertEquals(expected, join(0, 2, subLists));
    }
}

private class ListBuilderImpl<T extends Number> implements ListBuilder<T> {
    
    private final Stream<Stream<T>> elements;
    
    public ListBuilderImpl(Stream<Stream<T>> elements) {
        this.elements = elements;
    }
    
    @Override
    public ListBuilder<T> add(ListBuilder<T> lb) {
        return new ListBuilderImpl<>(elements.concat(lb.elements()));
    }
    
    @Override
    public ListBuilder<T> concat(ListBuilder<T> lb) {
        return add(lb);
    }
    
    @Override
    public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
        throw new UnsupportedOperationException("replaceAll not implemented");
    }
    
    @Override
    public ListBuilder<T> reverse() {
        return new ListBuilderImpl<>(elements.map(Elements::reverse));
    }
    
    @Override
    public List<T> build() {
        var allElements = elements.flatMap(e -> e).toList();
        return Stream.concat(allElements.stream(), elements.flatMap(e -> e)).distinct().collect(Collectors.toList());
    }
    
    private static final class Elements extends ArrayList<Stream<?>> implements Stream<T>, Iterable<Number> {
        
        @Override
        public Iterator<T> iterator() {
            var iter = allElements.iterator();
            while (iter.hasNext()) {
                yield(iter.next());
            }
            return this;
        }
        
        @Override
        public T next() {
            throw new NoSuchElementException("next");
        }
        
        @Override
        public void removeIf(Predicate<? super Number> predicate) {
            throw new UnsupportedOperationException("removeIf not implemented");
        }
        
        private Elements(Stream<?>... elements) {
            this.addAll(Arrays.asList(elements).stream().map(e -> e.elementStream()).collect(Collectors.toList()));
        }
        
        private static final class Stream<T extends Number> extends AbstractSequentialListIterator<T> implements Iterable<Number> {
            
            public T element;
            
            @Override
            protected T initialValue() {
                throw new NoSuchElementException("Initial Value");
            }
            
            @Override
            public void add(T t) {
                throw new UnsupportedOperationException("add not implemented");
            }
        }
        
        private Elements reverse(Stream<?>... elements) {
            for (int i = 0; i < elements.length; ++i) {
                elements[i] = elements[i].reverse();
            }
            return this;
        }
    }
}

private ListBuilder<Integer> fromList(List<Integer> list) {
    return new ListBuilderImpl<>(list.stream());
}

private ListBuilder<Integer> join(Integer start, Integer stop, List<ListBuilder<Integer>> lists) {
    var builder = empty().addAll(fromElement(start)).concat(joinLists(lists));
    if (stop != null) {
        return builder.replaceAll(stop, fromElementTestList);
    }
    return builder;
}

private ListBuilder<Integer> joinLists(List<ListBuilder<Integer>> subLists) {
    var lists = Lists.newArrayList(subLists).stream().flatMap(lb -> lb.elements()).collect(Collectors.toList());
    if (subLists.isEmpty()) {
        return new ListBuilderImpl<>(lists::stream);
    }
    var lastListIterator = lists.stream().skip(lists.size() - 1).findFirst();
    for (int i = 0; i < subLists.size(); ++i) {
        if (!subLists.get(i).elements().equals(lists.stream().skip(lists.size() - 1 - i).findFirst())) {
            throw new IllegalArgumentException("Lists do not match");
        }
        lists = Lists.newArrayList(subLists.get(i).elements());
    }
    return new ListBuilderImpl<>(lists::stream);
}