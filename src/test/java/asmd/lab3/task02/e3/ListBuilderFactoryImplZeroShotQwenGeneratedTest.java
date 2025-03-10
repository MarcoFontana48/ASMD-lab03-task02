package asmd.lab3.task02.e3;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class ListBuilderFactoryImplZeroShotQwenGeneratedTest {
    
    private ListBuilderFactoryImpl listBuilderFactory;
    
    @BeforeEach
    public void setUp() {
        listBuilderFactory = new ListBuilderFactoryImpl();
    }
    
    @Test
    public void testEmpty() {
        ListBuilder<Integer> emptyBuilder = listBuilderFactory.empty();
        List<Integer> list = emptyBuilder.build();
        
        assertTrue(list.isEmpty(), "The list should be empty");
    }
    
    @Test
    public void testFromElement() {
        ListBuilder<String> singleElementBuilder = listBuilderFactory.fromElement("element");
        List<String> list = singleElementBuilder.build();
        
        assertEquals(1, list.size(), "The list should contain one element");
        assertEquals("element", list.get(0), "The element should be 'element'");
    }
    
    @Test
    public void testFromList() {
        List<String> inputList = List.of("a", "b", "c");
        ListBuilder<String> listBuilder = listBuilderFactory.fromList(inputList);
        List<String> list = listBuilder.build();
        
        assertEquals(inputList, list, "The list should match the input list");
    }
    
    @Test
    public void testAdd() {
        ListBuilder<Integer> builder1 = listBuilderFactory.fromElement(1);
        ListBuilder<Integer> builder2 = listBuilderFactory.fromList(List.of(2, 3));
        
        ListBuilder<Integer> result = builder1.add(List.of(4, 5));
        List<Integer> list = result.build();
        
        assertEquals(List.of(1, 4, 5), list, "The list should contain [1, 4, 5]");
    }
    
    @Test
    public void testConcat() {
        ListBuilder<Integer> builder1 = listBuilderFactory.fromElement(1);
        ListBuilder<Integer> builder2 = listBuilderFactory.fromList(List.of(2, 3));
        
        ListBuilder<Integer> result = builder1.concat(builder2);
        List<Integer> list = result.build();
        
        assertEquals(List.of(1, 2, 3), list, "The concatenated list should contain [1, 2, 3]");
    }
    
    @Test
    public void testReplaceAll() {
        ListBuilder<String> builder = listBuilderFactory.fromList(List.of("apple", "banana", "pear", "apple", "orange"));
        ListBuilder<String> replacementBuilder = listBuilderFactory.fromList(List.of("fruit", "food"));
        
        ListBuilder<String> result = builder.replaceAll("apple", replacementBuilder);
        List<String> list = result.build();
        
        assertEquals(List.of("fruit", "food", "banana", "pear", "fruit", "food", "orange"), list, "All occurrences of 'apple' should be replaced");
    }
    
    @Test
    public void testReplaceAllWithNoMatch() {
        ListBuilder<String> builder = listBuilderFactory.fromList(List.of("apple", "banana"));
        ListBuilder<String> replacementBuilder = listBuilderFactory.fromList(List.of("fruit", "food"));
        
        ListBuilder<String> result = builder.replaceAll("grape", replacementBuilder);
        List<String> list = result.build();
        
        assertEquals(List.of("apple", "banana"), list, "No replacement should occur if the element does not exist");
    }
    
    @Test
    public void testReverse() {
        ListBuilder<Integer> builder = listBuilderFactory.fromList(List.of(1, 2, 3));
        ListBuilder<Integer> reversedBuilder = builder.reverse();
        List<Integer> reversedList = reversedBuilder.build();
        
        assertEquals(List.of(3, 2, 1), reversedList, "The list should be reversed");
    }
    
    @Test
    public void testReverseEmpty() {
        ListBuilder<Integer> builder = listBuilderFactory.empty();
        ListBuilder<Integer> reversedBuilder = builder.reverse();
        List<Integer> reversedList = reversedBuilder.build();
        
        assertTrue(reversedList.isEmpty(), "The reversed empty list should still be empty");
    }
    
    @Test
    public void testJoin() {
        ListBuilder<Integer> builder1 = listBuilderFactory.fromElement(1);
        ListBuilder<Integer> builder2 = listBuilderFactory.fromElement(2);
        ListBuilder<Integer> builder3 = listBuilderFactory.fromElement(3);
        
        ListBuilder<Integer> result = listBuilderFactory.join(0, 4, List.of(builder1, builder2, builder3));
        List<Integer> list = result.build();
        
        assertEquals(List.of(0, 1, 2, 3, 4), list, "The joined list should contain [0, 1, 2, 3, 4]");
    }
    
    @Test
    public void testJoinWithEmptyList() {
        ListBuilder<Integer> builder1 = listBuilderFactory.fromElement(1);
        
        ListBuilder<Integer> result = listBuilderFactory.join(0, 4, List.of());
        List<Integer> list = result.build();
        
        assertEquals(List.of(0, 4), list, "The joined list with an empty list should be [0, 4]");
    }
}
