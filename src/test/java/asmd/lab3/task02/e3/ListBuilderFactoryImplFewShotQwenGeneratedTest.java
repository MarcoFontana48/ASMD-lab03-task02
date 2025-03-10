package asmd.lab3.task02.e3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListBuilderFactoryImplFewShotQwenGeneratedTest {
    
    private ListBuilderFactory factory;
    
    @BeforeEach
    public void initFactory() {
        this.factory = new ListBuilderFactoryImpl();
    }
    
    @Test
    public void testEmpty() {
        // Test for an empty list builder
        ListBuilder<Integer> empty = this.factory.<Integer>empty();
        assertEquals(List.of(), empty.build());
        
        // Test adding elements to an empty builder
        assertEquals(List.of(10, 20),
                empty.add(List.of(10, 20))
                        .build());
        
        // Test multiple add operations
        assertEquals(List.of(10, 20, 30),
                empty.add(List.of(10, 20))
                        .add(List.of(30))
                        .build());
        
        // Test concatenation with another builder
        assertEquals(List.of(10, 20, 30),
                empty.add(List.of(10, 20))
                        .concat(empty.add(List.of(30)))
                        .build());
        
        // Another concat example
        assertEquals(List.of(10, 20, 30),
                empty.add(List.of(10, 20))
                        .concat(empty.add(List.of(30)))
                        .build());
    }
    
    @Test
    public void testFromElement() {
        // Test creating a builder with a single element
        ListBuilder<Integer> one = this.factory.fromElement(1);
        assertEquals(List.of(1), one.build());
        
        // Test adding elements to the builder
        assertEquals(List.of(1, 2, 3, 4),
                one.add(List.of(2, 3, 4)).build());
        
        // Test concatenating with another builder
        assertEquals(List.of(1, 2, 1),
                one.concat(this.factory.fromElement(2))
                        .concat(one)
                        .build());
    }
    
    @Test
    public void testBasicFromList() {
        // Test creating a builder from an existing list
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertEquals(List.of(1, 2, 3), l.build());
        
        // Test concatenation of the builder with itself
        assertEquals(List.of(1, 2, 3, 1, 2, 3),
                l.concat(l).build());
        
        // Test replaceAll operation, replacing 1 with -1 and -2
        assertEquals(List.of(-1, -2, 2, 3, -1, -2, 2, 3),
                l.concat(l)
                        .replaceAll(1, this.factory.fromList(List.of(-1, -2)))
                        .build());
        
        // Test replaceAll with no match
        assertEquals(List.of(1, 2, 3, 1, 2, 3),
                l.concat(l)
                        .replaceAll(10, this.factory.fromList(List.of(-1, -2)))
                        .build());
    }
    
    @Test
    public void testReverseFromList() {
        // Test reversing a list
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertEquals(List.of(1, 2, 3), l.build());
        
        // Test reversing the list
        assertEquals(List.of(3, 2, 1), l.reverse().build());
        
        // Test reversing twice, should return original order
        assertEquals(List.of(1, 2, 3), l.reverse().reverse().build());
        
        // Test concatenating a reversed list with the original
        assertEquals(List.of(1, 2, 3, 3, 2, 1),
                l.reverse().reverse().concat(l.reverse()).build());
    }
    
    @Test
    public void testJoin() {
        // Test join functionality with multiple list builders
        assertEquals(List.of("(", "1", "2", "3", "4", ")"),
                this.factory.join("(", ")",
                        List.of(this.factory.fromElement("1"),
                                this.factory.fromElement("2"),
                                this.factory.fromList(List.of("3", "4")))).build());
    }
    
    @Test
    public void testReplaceAllWithEmptyList() {
        // Test replaceAll with an empty list
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3, 4));
        ListBuilder<Integer> replaced = l.replaceAll(1, this.factory.empty());
        assertEquals(List.of(2, 3, 4), replaced.build());
    }
    
    @Test
    public void testAddEmptyList() {
        // Test adding an empty list should not affect the result
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertEquals(List.of(1, 2, 3), l.add(List.of()).build());
    }
    
    @Test
    public void testAddNull() {
        // Test adding null elements should not work (to check robustness)
        assertThrows(NullPointerException.class, () -> {
            this.factory.fromList(List.of(1, 2, 3)).add(null);
        });
    }
    
    @Test
    public void testConcatWithEmpty() {
        // Test concatenating with an empty builder
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertEquals(List.of(1, 2, 3), l.concat(this.factory.empty()).build());
    }
    
    @Test
    public void testReverseEmptyList() {
        // Test reversing an empty list
        ListBuilder<Integer> empty = this.factory.empty();
        assertEquals(List.of(), empty.reverse().build());
    }
    
    @Test
    public void testReverseSingleElementList() {
        // Test reversing a single element list
        ListBuilder<Integer> single = this.factory.fromElement(42);
        assertEquals(List.of(42), single.reverse().build());
    }
    
    @Test
    public void testJoinWithEmptyLists() {
        // Test join with empty list builders
        assertEquals(List.of("start", "end"),
                this.factory.join("start", "end", List.of(this.factory.empty(), this.factory.empty())).build());
    }
    
    @Test
    public void testJoinWithNullElement() {
        // Test join with null element (expect NullPointerException)
        assertThrows(NullPointerException.class, () -> {
            this.factory.join(null, "end", List.of(this.factory.fromElement("1"))).build();
        });
    }
}
