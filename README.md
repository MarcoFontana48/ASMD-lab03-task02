# Task 02 - Test generation

## Zero shot - e1

### Qwen 2.5:3b

Q:

```text
considering this class in java, i need you to generate tests that cover all methods, edge cases and all possible outcomes "package asmd.lab3.e1.task01;

import java.util.*;
import java.util.stream.*;

import java.util.function.*;

public class TimetableFactoryImpl implements TimetableFactory {

    public static <T> Set<T> addToSet(Set<T> s, T t){
        return concatSet(s, Set.of(t));
    }

    public static <T> Set<T> concatSet(Set<T> s, Set<T> s2){
        return Stream.concat(s.stream(), s2.stream()).collect(Collectors.toSet());
    }

    // An implementation of a Timetable as an immutable class with activities, days, and an association of activities+days to hours
    // Since it is immutable, we use a record, which automatically gives fields, getters, constructors, hashcode, equals, toString.
    private static record TimetableData(Set<String> activities, Set<String> days, BiFunction<String, String, Integer> data) implements Timetable {

        @Override
        public int getSingleData(String activity, String day) {
            return data.apply(activity, day);
        }

        @Override
        public Timetable addHour(String activity, String day) {
            return new TimetableData(
                addToSet(activities, activity), 
                addToSet(days, day), 
                (a,d) -> data.apply(a,d) + (activity.equals(a) && day.equals(d) ? 1 : 0)
            );
        }

        private int statistics(BiPredicate<String, String> predicate) {
            return activities.stream()
                    .flatMap(a -> days.stream()
                            .filter(d -> predicate.test(a,d))
                            .map(d -> this.getSingleData(a, d)))
                    .collect(Collectors.summingInt(i -> i));
        }

        @Override
        public int sums(Set<String> activies, Set<String> days) {
            return statistics((a,d) -> activies.contains(a) && days.contains(d));
        }
    }

    @Override
    public Timetable empty() {
        return new TimetableData(Set.of(), Set.of(), (a,d) -> 0);
    }

    @Override
    public Timetable single(String activity, String day) {
        return empty().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        return new TimetableData(
            concatSet(table1.activities(), table2.activities()),
            concatSet(table1.days(), table2.days()),
            (a,d) -> table1.getSingleData(a, d) + table2.getSingleData(a, d)
        );
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        return new TimetableData(
            table.activities(),
            table.days(),
            (a, d) -> Math.min(table.getSingleData(a, d), bounds.apply(a, d)));
    }
    
}
"
```

A:
```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TimetableFactoryImplTest {

    private TimetableFactoryImpl timetableFactory;

    @BeforeEach
    public void setUp() {
        timetableFactory = new TimetableFactoryImpl();
    }

    @Test
    public void testAddToSet() {
        Set<String> activities = new HashSet<>(Set.of("Math", "Science"));
        Set<String> result = TimetableFactoryImpl.addToSet(activities, "English");
        
        assertTrue(result.contains("Math"));
        assertTrue(result.contains("Science"));
        assertTrue(result.contains("English"));
        assertEquals(3, result.size());
    }

    @Test
    public void testConcatSet() {
        Set<String> set1 = new HashSet<>(Set.of("Math", "Science"));
        Set<String> set2 = new HashSet<>(Set.of("English", "History"));
        
        Set<String> result = TimetableFactoryImpl.concatSet(set1, set2);
        
        assertTrue(result.contains("Math"));
        assertTrue(result.contains("Science"));
        assertTrue(result.contains("English"));
        assertTrue(result.contains("History"));
        assertEquals(4, result.size());
    }

    @Test
    public void testEmptyTimetable() {
        Timetable emptyTimetable = timetableFactory.empty();
        
        assertEquals(0, emptyTimetable.activities().size());
        assertEquals(0, emptyTimetable.days().size());
        assertEquals(0, emptyTimetable.sums(Set.of(), Set.of()));
    }

    @Test
    public void testSingleTimetable() {
        Timetable singleTimetable = timetableFactory.single("Math", "Monday");
        
        assertEquals(1, singleTimetable.activities().size());
        assertTrue(singleTimetable.activities().contains("Math"));
        assertEquals(1, singleTimetable.days().size());
        assertTrue(singleTimetable.days().contains("Monday"));
        assertEquals(1, singleTimetable.getSingleData("Math", "Monday"));
    }

    @Test
    public void testAddHourToTimetable() {
        Timetable timetable = timetableFactory.single("Math", "Monday");
        Timetable updatedTimetable = timetable.addHour("Math", "Monday");
        
        assertEquals(1, updatedTimetable.activities().size());
        assertTrue(updatedTimetable.activities().contains("Math"));
        assertEquals(1, updatedTimetable.days().size());
        assertTrue(updatedTimetable.days().contains("Monday"));
        assertEquals(2, updatedTimetable.getSingleData("Math", "Monday"));
    }

    @Test
    public void testJoinTimetables() {
        Timetable table1 = timetableFactory.single("Math", "Monday");
        Timetable table2 = timetableFactory.single("English", "Tuesday");
        
        Timetable joinedTable = timetableFactory.join(table1, table2);
        
        assertEquals(2, joinedTable.activities().size());
        assertTrue(joinedTable.activities().contains("Math"));
        assertTrue(joinedTable.activities().contains("English"));
        assertEquals(2, joinedTable.days().size());
        assertTrue(joinedTable.days().contains("Monday"));
        assertTrue(joinedTable.days().contains("Tuesday"));
        assertEquals(1, joinedTable.getSingleData("Math", "Monday"));
        assertEquals(1, joinedTable.getSingleData("English", "Tuesday"));
    }

    @Test
    public void testCutTimetable() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday");
        Timetable cutTable = timetableFactory.cut(table, (a, d) -> 1);
        
        assertEquals(1, cutTable.getSingleData("Math", "Monday"));
    }

    @Test
    public void testSums() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of("Math");
        Set<String> days = Set.of("Monday", "Tuesday");
        
        int sum = table.sums(activities, days);
        
        assertEquals(3, sum);  // 2 hours on Monday + 1 hour on Tuesday
    }

    @Test
    public void testSumsWithEmptyActivities() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of();  // Empty set of activities
        Set<String> days = Set.of("Monday", "Tuesday");
        
        int sum = table.sums(activities, days);
        
        assertEquals(0, sum);  // No activities to sum
    }

    @Test
    public void testSumsWithEmptyDays() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of("Math");
        Set<String> days = Set.of();  // Empty set of days
        
        int sum = table.sums(activities, days);
        
        assertEquals(0, sum);  // No days to sum
    }

    @Test
    public void testEdgeCases() {
        Timetable emptyTimetable = timetableFactory.empty();
        assertEquals(0, emptyTimetable.getSingleData("Math", "Monday"));
        assertThrows(NullPointerException.class, () -> emptyTimetable.getSingleData(null, null));
    }
}
```

The generated tests cover 100% of the code, including edge cases and standard scenarios.
Also using those tests i noticed that no exceptions are thrown when the method getSingleData of the empty timetable
is called with null parameters, that's something that should be taken into account in the implementation of the class
that i was able to discover only by generating those tests.

However, it does not use 'assertAll' meaning that if one of the assertions fails, the test will stop executing and not
check the rest of the assertions.

A followup question was asked to add 'assertAll' to the tests:

Q:
```text
Can you use 'assertAll' method only where there are multiple assertions? So that the test will be executed fully even in case of failures
```

A:
```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TimetableFactoryImplTest {

    private TimetableFactoryImpl timetableFactory;

    @BeforeEach
    public void setUp() {
        timetableFactory = new TimetableFactoryImpl();
    }

    @Test
    public void testAddToSet() {
        Set<String> activities = new HashSet<>(Set.of("Math", "Science"));
        Set<String> result = TimetableFactoryImpl.addToSet(activities, "English");
        
        assertAll("Add to set",
            () -> assertTrue(result.contains("Math")),
            () -> assertTrue(result.contains("Science")),
            () -> assertTrue(result.contains("English")),
            () -> assertEquals(3, result.size())
        );
    }

    @Test
    public void testConcatSet() {
        Set<String> set1 = new HashSet<>(Set.of("Math", "Science"));
        Set<String> set2 = new HashSet<>(Set.of("English", "History"));
        
        Set<String> result = TimetableFactoryImpl.concatSet(set1, set2);
        
        assertAll("Concatenate sets",
            () -> assertTrue(result.contains("Math")),
            () -> assertTrue(result.contains("Science")),
            () -> assertTrue(result.contains("English")),
            () -> assertTrue(result.contains("History")),
            () -> assertEquals(4, result.size())
        );
    }

    @Test
    public void testEmptyTimetable() {
        Timetable emptyTimetable = timetableFactory.empty();
        
        assertAll("Empty timetable",
            () -> assertEquals(0, emptyTimetable.activities().size()),
            () -> assertEquals(0, emptyTimetable.days().size()),
            () -> assertEquals(0, emptyTimetable.sums(Set.of(), Set.of()))
        );
    }

    @Test
    public void testSingleTimetable() {
        Timetable singleTimetable = timetableFactory.single("Math", "Monday");
        
        assertAll("Single timetable",
            () -> assertEquals(1, singleTimetable.activities().size()),
            () -> assertTrue(singleTimetable.activities().contains("Math")),
            () -> assertEquals(1, singleTimetable.days().size()),
            () -> assertTrue(singleTimetable.days().contains("Monday")),
            () -> assertEquals(1, singleTimetable.getSingleData("Math", "Monday"))
        );
    }

    @Test
    public void testAddHourToTimetable() {
        Timetable timetable = timetableFactory.single("Math", "Monday");
        Timetable updatedTimetable = timetable.addHour("Math", "Monday");
        
        assertAll("Add hour to timetable",
            () -> assertEquals(1, updatedTimetable.activities().size()),
            () -> assertTrue(updatedTimetable.activities().contains("Math")),
            () -> assertEquals(1, updatedTimetable.days().size()),
            () -> assertTrue(updatedTimetable.days().contains("Monday")),
            () -> assertEquals(2, updatedTimetable.getSingleData("Math", "Monday"))
        );
    }

    @Test
    public void testJoinTimetables() {
        Timetable table1 = timetableFactory.single("Math", "Monday");
        Timetable table2 = timetableFactory.single("English", "Tuesday");
        
        Timetable joinedTable = timetableFactory.join(table1, table2);
        
        assertAll("Join timetables",
            () -> assertEquals(2, joinedTable.activities().size()),
            () -> assertTrue(joinedTable.activities().contains("Math")),
            () -> assertTrue(joinedTable.activities().contains("English")),
            () -> assertEquals(2, joinedTable.days().size()),
            () -> assertTrue(joinedTable.days().contains("Monday")),
            () -> assertTrue(joinedTable.days().contains("Tuesday")),
            () -> assertEquals(1, joinedTable.getSingleData("Math", "Monday")),
            () -> assertEquals(1, joinedTable.getSingleData("English", "Tuesday"))
        );
    }

    @Test
    public void testCutTimetable() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday");
        Timetable cutTable = timetableFactory.cut(table, (a, d) -> 1);
        
        assertEquals(1, cutTable.getSingleData("Math", "Monday"));
    }

    @Test
    public void testSums() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of("Math");
        Set<String> days = Set.of("Monday", "Tuesday");
        
        int sum = table.sums(activities, days);
        
        assertEquals(3, sum);  // 2 hours on Monday + 1 hour on Tuesday
    }

    @Test
    public void testSumsWithEmptyActivities() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of();  // Empty set of activities
        Set<String> days = Set.of("Monday", "Tuesday");
        
        int sum = table.sums(activities, days);
        
        assertEquals(0, sum);  // No activities to sum
    }

    @Test
    public void testSumsWithEmptyDays() {
        Timetable table = timetableFactory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of("Math");
        Set<String> days = Set.of();  // Empty set of days
        
        int sum = table.sums(activities, days);
        
        assertEquals(0, sum);  // No days to sum
    }

    @Test
    public void testEdgeCases() {
        Timetable emptyTimetable = timetableFactory.empty();
        
        assertAll("Edge cases with empty timetable",
            () -> assertEquals(0, emptyTimetable.getSingleData("Math", "Monday")),
            () -> assertThrows(NullPointerException.class, () -> emptyTimetable.getSingleData(null, null))
        );
    }
}
```

Now the tests that have multiple assertions use `assertAll` to ensure that all assertions are executed, even if one of
them fails. This way, you can see all the failures at once instead of stopping at the first failure.

No other changes were necessary to run tests, as they already covered all methods, edge cases, and possible outcomes.

## Zero shot - e2

### Qwen 2.5:3b

A:

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TimeSheetFactoryImplTest {

    private TimeSheetFactoryImpl timeSheetFactory;

    @BeforeEach
    public void setUp() {
        timeSheetFactory = new TimeSheetFactoryImpl();
    }

    @Test
    public void testFlat() {
        TimeSheet timeSheet = timeSheetFactory.flat(3, 3, 5);
        
        assertAll("Flat TimeSheet",
            () -> assertEquals(3, timeSheet.sumsPerActivity().size()),
            () -> assertEquals(3, timeSheet.sumsPerDay().size()),
            () -> assertEquals(5, timeSheet.getSingleData("act1", "day1")),
            () -> assertEquals(5, timeSheet.getSingleData("act2", "day2")),
            () -> assertEquals(5, timeSheet.getSingleData("act3", "day3"))
        );
    }

    @Test
    public void testOfRawData() {
        List<Pair<Integer, Integer>> data = List.of(
            new Pair<>(0, 0), new Pair<>(1, 1), new Pair<>(2, 2)
        );
        TimeSheet timeSheet = timeSheetFactory.ofRawData(3, 3, data);
        
        assertAll("Raw Data TimeSheet",
            () -> assertEquals(1, timeSheet.getSingleData("act1", "day1")),
            () -> assertEquals(1, timeSheet.getSingleData("act2", "day2")),
            () -> assertEquals(0, timeSheet.getSingleData("act3", "day1"))
        );
    }

    @Test
    public void testOfListsOfLists() {
        List<String> activities = List.of("act1", "act2");
        List<String> days = List.of("day1", "day2");
        List<List<Integer>> data = List.of(
            List.of(1, 2),  // hours for act1 on day1 and day2
            List.of(3, 4)   // hours for act2 on day1 and day2
        );
        
        TimeSheet timeSheet = timeSheetFactory.ofListsOfLists(activities, days, data);
        
        assertAll("Lists of Lists TimeSheet",
            () -> assertEquals(1, timeSheet.getSingleData("act1", "day1")),
            () -> assertEquals(2, timeSheet.getSingleData("act1", "day2")),
            () -> assertEquals(3, timeSheet.getSingleData("act2", "day1")),
            () -> assertEquals(4, timeSheet.getSingleData("act2", "day2"))
        );
    }

    @Test
    public void testOfPartialMap() {
        Map<Pair<String, String>, Integer> data = new HashMap<>();
        data.put(new Pair<>("act1", "day1"), 1);
        data.put(new Pair<>("act2", "day2"), 2);
        
        List<String> activities = List.of("act1", "act2");
        List<String> days = List.of("day1", "day2");
        
        TimeSheet timeSheet = timeSheetFactory.ofPartialMap(activities, days, data);
        
        assertAll("Partial Map TimeSheet",
            () -> assertEquals(1, timeSheet.getSingleData("act1", "day1")),
            () -> assertEquals(2, timeSheet.getSingleData("act2", "day2")),
            () -> assertEquals(0, timeSheet.getSingleData("act1", "day2")),
            () -> assertEquals(0, timeSheet.getSingleData("act2", "day1"))
        );
    }

    @Test
    public void testSumsPerActivity() {
        List<Pair<Integer, Integer>> data = List.of(
            new Pair<>(0, 0), new Pair<>(1, 1), new Pair<>(2, 2)
        );
        TimeSheet timeSheet = timeSheetFactory.ofRawData(3, 3, data);
        
        Map<String, Integer> sums = timeSheet.sumsPerActivity();
        
        assertAll("Sums Per Activity",
            () -> assertEquals(3, sums.size()),
            () -> assertEquals(1, sums.get("act1")),
            () -> assertEquals(1, sums.get("act2")),
            () -> assertEquals(0, sums.get("act3"))
        );
    }

    @Test
    public void testSumsPerDay() {
        List<Pair<Integer, Integer>> data = List.of(
            new Pair<>(0, 0), new Pair<>(1, 1), new Pair<>(2, 2)
        );
        TimeSheet timeSheet = timeSheetFactory.ofRawData(3, 3, data);
        
        Map<String, Integer> sums = timeSheet.sumsPerDay();
        
        assertAll("Sums Per Day",
            () -> assertEquals(3, sums.size()),
            () -> assertEquals(1, sums.get("day1")),
            () -> assertEquals(1, sums.get("day2")),
            () -> assertEquals(0, sums.get("day3"))
        );
    }

    @Test
    public void testEmptyData() {
        TimeSheet emptyTimeSheet = timeSheetFactory.flat(0, 0, 0);
        
        assertAll("Empty TimeSheet",
            () -> assertEquals(0, emptyTimeSheet.sumsPerActivity().size()),
            () -> assertEquals(0, emptyTimeSheet.sumsPerDay().size()),
            () -> assertEquals(0, emptyTimeSheet.getSingleData("act1", "day1"))
        );
    }

    @Test
    public void testOutOfBoundData() {
        List<Pair<Integer, Integer>> data = List.of(
            new Pair<>(0, 0), new Pair<>(1, 1)
        );
        TimeSheet timeSheet = timeSheetFactory.ofRawData(3, 3, data);
        
        assertAll("Out of Bound Data",
            () -> assertEquals(0, timeSheet.getSingleData("act1", "day3")),
            () -> assertEquals(0, timeSheet.getSingleData("act3", "day1")),
            () -> assertEquals(0, timeSheet.getSingleData("act2", "day2"))
        );
    }

    @Test
    public void testLargeInput() {
        TimeSheet timeSheet = timeSheetFactory.flat(1000, 1000, 5);
        
        assertAll("Large Input TimeSheet",
            () -> assertEquals(1000, timeSheet.sumsPerActivity().size()),
            () -> assertEquals(1000, timeSheet.sumsPerDay().size()),
            () -> assertEquals(5, timeSheet.getSingleData("act500", "day500"))
        );
    }
}
```

The generated tests cover 100% of the code, including edge cases and standard scenarios.
Also, it uses the 'assertAll' method so that the test will be executed fully even in case of failures.

No other changes were necessary to run tests, as they already covered all methods, edge cases, and possible outcomes.

## Zero shot - e3

### Qwen 2.5:3b

Q:
```text
considering this class in java, i need you to generate tests that cover all methods, edge cases and all possible outcomes "package asmd.lab3.task02.e3;

import java.util.*;
import java.util.stream.*;
import java.util.function.Function;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    // a utility function, essentially a flatMap for builders
    private <R,T> ListBuilder<R> flatMap(ListBuilder<T> builder, Function<T, ListBuilder<R>> fun) {
        return new ListBuilderImpl<>(builder.build().stream().flatMap(t -> fun.apply(t).build().stream()));
    }

    // A builder can just be an immutable wrapper of a list!
    private class ListBuilderImpl<T> implements ListBuilder<T> {
        private final List<T> list;

        public ListBuilderImpl(Stream<T> stream){
            this.list = stream.toList();
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            return new ListBuilderImpl<>(Stream.concat(this.list.stream(), list.stream()));
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            return add(lb.build());
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            return flatMap(this, tt -> tt.equals(t) ? lb : fromElement(tt));
        }

        @Override
        public ListBuilder<T> reverse() {
            var l2 = new ArrayList<>(list);
            Collections.reverse(l2);
            return new ListBuilderImpl<>(l2.stream());
        }

        @Override
        public List<T> build() {
            return this.list;
        }

    }

    @Override
    public <T> ListBuilder<T> empty() {
        return new ListBuilderImpl<>(Stream.empty());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        return new ListBuilderImpl<>(Stream.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list.stream());
    }


    @Override
    public <T> ListBuilder<T> join(T start,  T stop, List<ListBuilder<T>> list) {
        return this
            .fromElement(start)
            .add(list.stream().flatMap(lb -> lb.build().stream()).toList())
            .add(List.of(stop));
    }
}
"
```

A:
```java
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
```

The generated tests cover 100% of the code, including edge cases and standard scenarios.
No more changes were necessary to run tests.

## Few shot - e1

### Qwen 2.5:3b

Q:
```text
considering this class in java, i need you to generate tests using JUnit5 that cover all methods, edge cases and all possible outcomes "package asmd.lab3.e1.task01;

import java.util.*;
import java.util.stream.*;

import java.util.function.*;

public class TimetableFactoryImpl implements TimetableFactory {

    public static <T> Set<T> addToSet(Set<T> s, T t){
        return concatSet(s, Set.of(t));
    }

    public static <T> Set<T> concatSet(Set<T> s, Set<T> s2){
        return Stream.concat(s.stream(), s2.stream()).collect(Collectors.toSet());
    }

    // An implementation of a Timetable as an immutable class with activities, days, and an association of activities+days to hours
    // Since it is immutable, we use a record, which automatically gives fields, getters, constructors, hashcode, equals, toString.
    private static record TimetableData(Set<String> activities, Set<String> days, BiFunction<String, String, Integer> data) implements Timetable {

        @Override
        public int getSingleData(String activity, String day) {
            return data.apply(activity, day);
        }

        @Override
        public Timetable addHour(String activity, String day) {
            return new TimetableData(
                addToSet(activities, activity), 
                addToSet(days, day), 
                (a,d) -> data.apply(a,d) + (activity.equals(a) && day.equals(d) ? 1 : 0)
            );
        }

        private int statistics(BiPredicate<String, String> predicate) {
            return activities.stream()
                    .flatMap(a -> days.stream()
                            .filter(d -> predicate.test(a,d))
                            .map(d -> this.getSingleData(a, d)))
                    .collect(Collectors.summingInt(i -> i));
        }

        @Override
        public int sums(Set<String> activies, Set<String> days) {
            return statistics((a,d) -> activies.contains(a) && days.contains(d));
        }
    }

    @Override
    public Timetable empty() {
        return new TimetableData(Set.of(), Set.of(), (a,d) -> 0);
    }

    @Override
    public Timetable single(String activity, String day) {
        return empty().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        return new TimetableData(
            concatSet(table1.activities(), table2.activities()),
            concatSet(table1.days(), table2.days()),
            (a,d) -> table1.getSingleData(a, d) + table2.getSingleData(a, d)
        );
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        return new TimetableData(
            table.activities(),
            table.days(),
            (a, d) -> Math.min(table.getSingleData(a, d), bounds.apply(a, d)));
    }
    
}
"

This is a test class that you can use as reference to generate a new class for tests: "package a02a.sol1;

import static org.junit.Assert.*;

import java.util.*;

public class Test {
	private ListBuilderFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new ListBuilderFactoryImpl();
	}

	@org.junit.Test
	public void testEmpty() {
		// empty() rappresenta il builder di una lista vuota
		ListBuilder<Integer> empty = this.factory.<Integer>empty();
		assertEquals(List.of(), empty.build());
		// se gli aggiungo 10 e 20 diventa il builder di una lista (10, 20)
		assertEquals(List.of(10, 20),
				empty.add(List.of(10, 20))
						.build());
		// posso fare due add consecutive, concatenando le chiamate
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.add(List.of(30))
						.build());
		// con la concat ottengo un builder che rappresenta la concatenazione delle liste
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.concat(empty.add(List.of(30)))
						.build());
		// altro esempio con la concat
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.concat(empty.add(List.of(30)))
						.build());
	}

	@org.junit.Test
	public void testFromElement() {
		// fromElement() rappresenta il builder di una lista con un elemento
		ListBuilder<Integer> one = this.factory.fromElement(1);
		// add e concat funzionano come ci si aspetta
		assertEquals(List.of(1), one.build());
		assertEquals(List.of(1, 2, 3, 4),
				one.add(List.of(2, 3, 4)).build());
		assertEquals(List.of(1, 2, 1),
				one.concat(this.factory.fromElement(2))
						.concat(one)
						.build());
	}

	@org.junit.Test
	public void testBasicFromList() {
		// fromList() rappresenta il builder di una lista con n elementi
		ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
		assertEquals(List.of(1, 2, 3), l.build());
		// concat funzionano come ci si aspetta
		assertEquals(List.of(1, 2, 3, 1, 2, 3),
				l.concat(l).build());
		// replaceAll qui rimpiazza gli "1" con coppie "-1, -2"
		assertEquals(List.of(-1, -2, 2, 3, -1, -2, 2, 3),
				l.concat(l)
						.replaceAll(1, this.factory.fromList(List.of(-1, -2)))
						.build());
		// se non c'è match, la replaceAll non fa nulla
		assertEquals(List.of(1, 2, 3, 1, 2, 3),
				l.concat(l)
						.replaceAll(10, this.factory.fromList(List.of(-1, -2)))
						.build());
	}

	@org.junit.Test
	public void testReverseFromList() {
		ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
		assertEquals(List.of(1, 2, 3), l.build());
		// la reverse fa ottenere un builder che rappresenta la lista invertita
		assertEquals(List.of(3, 2, 1), l.reverse().build());
		assertEquals(List.of(1, 2, 3), l.reverse().reverse().build());
		assertEquals(List.of(1, 2, 3, 3, 2, 1),
				l.reverse().reverse().concat(l.reverse()).build());
	}

	@org.junit.Test
	public void testJoin() {
  		// la join è usabile per concatenare più builder, con un elemento iniziale e uno finale
		assertEquals(List.of("(", "1", "2", "3", "4",")"),
				this.factory.join("(", ")", 
						List.of(this.factory.fromElement("1"), 
								this.factory.fromElement("2"),
								this.factory.fromList(List.of("3","4")))).build());
	}

}"
```

A:

```java
package asmd.lab3.task02.e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableFactoryImplFewShotQwenGeneratedTest {
    
    private TimetableFactoryImpl timetableFactory;
    
    @BeforeEach
    public void setUp() {
        timetableFactory = new TimetableFactoryImpl();
    }
    
    @Test
    public void testEmpty() {
        Timetable timetable = timetableFactory.empty();
        assertEquals(0, timetable.sums(Set.of(), Set.of()));
        assertEquals(0, timetable.sums(Set.of("Activity1"), Set.of("Monday")));
    }
    
    @Test
    public void testSingle() {
        Timetable timetable = timetableFactory.single("Activity1", "Monday");
        assertEquals(1, timetable.sums(Set.of("Activity1"), Set.of("Monday")));
        assertEquals(0, timetable.sums(Set.of("Activity1"), Set.of("Tuesday")));
    }
    
    @Test
    public void testJoin() {
        Timetable timetable1 = timetableFactory.single("Activity1", "Monday");
        Timetable timetable2 = timetableFactory.single("Activity2", "Tuesday");
        Timetable joinedTimetable = timetableFactory.join(timetable1, timetable2);
        
        // Checking sums for both activities and days
        assertEquals(1, joinedTimetable.sums(Set.of("Activity1"), Set.of("Monday")));
        assertEquals(1, joinedTimetable.sums(Set.of("Activity2"), Set.of("Tuesday")));
        assertEquals(0, joinedTimetable.sums(Set.of("Activity1"), Set.of("Tuesday")));
        assertEquals(0, joinedTimetable.sums(Set.of("Activity2"), Set.of("Monday")));
    }
    
    @Test
    public void testCut() {
        Timetable timetable = timetableFactory.single("Activity1", "Monday");
        BiFunction<String, String, Integer> bounds = (activity, day) -> activity.equals("Activity1") && day.equals("Monday") ? 2 : 0;
        Timetable cutTimetable = timetableFactory.cut(timetable, bounds);
        
        // After cutting, the value for "Activity1" on "Monday" should be limited to 2
        assertEquals(2, cutTimetable.sums(Set.of("Activity1"), Set.of("Monday")));
        assertEquals(0, cutTimetable.sums(Set.of("Activity1"), Set.of("Tuesday")));
    }
    
    @Test
    public void testAddToSet() {
        Set<String> activities = Set.of("Activity1");
        Set<String> result = TimetableFactoryImpl.addToSet(activities, "Activity2");
        
        assertTrue(result.contains("Activity1"));
        assertTrue(result.contains("Activity2"));
        assertEquals(2, result.size());
    }
    
    @Test
    public void testConcatSet() {
        Set<String> activities1 = Set.of("Activity1", "Activity2");
        Set<String> activities2 = Set.of("Activity3");
        
        Set<String> result = TimetableFactoryImpl.concatSet(activities1, activities2);
        
        assertTrue(result.contains("Activity1"));
        assertTrue(result.contains("Activity2"));
        assertTrue(result.contains("Activity3"));
        assertEquals(3, result.size());
    }
    
    @Test
    public void testEdgeCases() {
        // Test with empty sets for activities and days
        Timetable timetable = timetableFactory.empty();
        assertEquals(0, timetable.sums(Set.of("NonexistentActivity"), Set.of("NonexistentDay")));
        
        // Test with null values (if any) - This should ideally be handled in the implementation, but test anyway
        assertThrows(NullPointerException.class, () -> timetableFactory.single(null, "Monday"));
        assertThrows(NullPointerException.class, () -> timetableFactory.single("Activity1", null));
        
        // Test the cut operation with extreme boundary conditions
        BiFunction<String, String, Integer> extremeBounds = (activity, day) -> 100;
        Timetable extremeCutTimetable = timetableFactory.cut(timetable, extremeBounds);
        assertEquals(100, extremeCutTimetable.sums(Set.of("Activity1"), Set.of("Monday")));
    }
}
```

The generated tests cover 100% of the code, including edge cases and standard scenarios.

A better implementation would have been to use the `assertAll` method to group multiple assertions together, so that all
assertions are executed even if one of them fails.

However a couple of tests have a wrong expected value, for example the test testCut and testEdgeCases that should
have the expected value of 1 instead of 2 and 0 instead of 100 respectively.

So a small human intervention was needed to fix the expected values of those tests.

## Few shot - e2

### Qwen 2.5:3b

Q:
```text
considering this class in java, i need you to generate tests using JUnit5 that cover all methods, edge cases and all possible outcomes "package asmd.lab3.task02.e2;

import java.util.*;
import java.util.stream.*;

import java.util.function.*;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    // An implementation of a Timetable as an immutable class with activities, days, and an association of activities+days to hours
    // Since it is immutable, we use a record, which automatically gives fields, getters, constructors, hashcode, equals, toString.
    private static record TimeSheetData(List<String> activities, List<String> days, BiFunction<String, String, Integer> fun) implements TimeSheet {

        @Override
        public Map<String, Integer> sumsPerActivity() {
            return activities.stream()
                    .map(act -> new Pair<>(act, days.stream()
                            .map(day -> fun.apply(act,day))
                            .collect(Collectors.summingInt(i->i))))
                    .collect(Collectors.toMap(Pair::get1, Pair::get2));
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            return days.stream()
                    .map(day -> new Pair<>(day,activities.stream()
                            .map(act -> fun.apply(act,day))
                            .collect(Collectors.summingInt(i->i))))
                    .collect(Collectors.toMap(Pair::get1, Pair::get2));
        }

        @Override
        public int getSingleData(String activity, String day) {
            return activities.contains(activity) && days.contains(day) ? fun.apply(activity,day) : 0;
        }
    }

    private static List<String> createActivities(int numActivities){
        return Stream.iterate(1, i -> i + 1).map(i -> "act"+i).limit(numActivities).collect(Collectors.toList());
    }

    private static List<String> createDays(int numDays){
        return Stream.iterate(1, i -> i + 1).map(i -> "day"+i).limit(numDays).collect(Collectors.toList());
    }


    @Override
    public TimeSheet flat(int numActivities, int numDays, int hours) {
        var activities = createActivities(numActivities);
        var days = createDays(numDays);
        return new TimeSheetData(
            activities,
            days, 
            (a,d) -> hours);
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        var activities = createActivities(numActivities);
        var days = createDays(numDays);
        return new TimeSheetData(
            activities,
            days, 
            (a,d) -> (int)data.stream().filter(p -> p.get1().equals(activities.indexOf(a)) && p.get2().equals(days.indexOf(d))).count());
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeSheetData(
            List.copyOf(activities),
            List.copyOf(days), 
            (a,d) -> data.get(activities.indexOf(a)).get(days.indexOf(d)));
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return new TimeSheetData(
            List.copyOf(activities),
            List.copyOf(days), 
            (a,d) -> data.getOrDefault(new Pair<>(a,d),0));
    }
}
"

This is a test class that you can use as reference to generate a new class for tests: "public class Test {

	private TimeSheetFactory factory;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new TimeSheetFactoryImpl();
	}
	
	@org.junit.Test
	public void testFlat() {
		// un time sheet con 3 attività su 5 giorni, con un'ora al giorno su ciascuna attività su ciascun giorno
		TimeSheet sheet = this.factory.flat(3, 5, 1);
		assertEquals(List.of("act1", "act2", "act3"), sheet.activities());
		assertEquals(List.of("day1", "day2", "day3", "day4", "day5"), sheet.days());
		assertEquals(1, sheet.getSingleData("act1", "day2"));
		assertEquals(1, sheet.getSingleData("act2", "day3"));
		assertEquals(0, sheet.getSingleData("act22", "day30")); // attività/giorni non presenti, ritorno 0
		assertEquals(Map.of("act1",5, "act2",5,"act3",5), sheet.sumsPerActivity()); // 5 ore per ogni attività
		assertEquals(Map.of("day1",3, "day2",3,"day3",3, "day4",3, "day5",3), sheet.sumsPerDay()); // 3 ore su ogni giorno
	}

	@org.junit.Test
	public void testOfListsOfLists() {
		// un timesheet con 2 attività su 3 giorni, con nomi forniti
		TimeSheet sheet = this.factory.ofListsOfLists(
			List.of("a1","a2"), 
			List.of("d1", "d2", "d3"),
			List.of(
				List.of(1,2,3), // attività a1: 1,2,3 ore nei 3 giorni d1, d2, d3, ordinatamente
				List.of(0,0,1)  // attività a2: 0,0,1 ore nei 3 giorni d1, d2, d3, ordinatamente
			));
		assertEquals(List.of("a1", "a2"), sheet.activities());
		assertEquals(List.of("d1", "d2", "d3"), sheet.days());
		assertEquals(2, sheet.getSingleData("a1", "d2"));
		assertEquals(3, sheet.getSingleData("a1", "d3"));
		assertEquals(0, sheet.getSingleData("a2", "d2"));
		assertEquals(1, sheet.getSingleData("a2", "d3"));
		assertEquals(Map.of("a1",6, "a2",1), sheet.sumsPerActivity()); // ore per attività
		assertEquals(Map.of("d1",1, "d2",2,"d3",4), sheet.sumsPerDay()); // ore per giorno
	}

	@org.junit.Test
	public void testOfRawData() {
		// un timesheet con 2 attività su 3 giorni, con nomi standard
		TimeSheet sheet = this.factory.ofRawData(2, 3, List.of(
			new Pair<>(0,0), // un'ora su act1 e day1
			new Pair<>(0,1), // un'ora su act1 e day2
			new Pair<>(0,1), // un'ora su act1 e day2 (diventano due in tutto)
			new Pair<>(0,2), // un'ora su act1 e day3
			new Pair<>(0,2), // un'ora su act1 e day3 (diventano due in tutto)
			new Pair<>(0,2), // un'ora su act1 e day3 (diventano tre in tutto)
			new Pair<>(1,2)));// un'ora su act2 e day3
		assertEquals(List.of("act1", "act2"), sheet.activities());
		assertEquals(List.of("day1", "day2", "day3"), sheet.days());
		assertEquals(2, sheet.getSingleData("act1", "day2"));
		assertEquals(3, sheet.getSingleData("act1", "day3"));
		assertEquals(0, sheet.getSingleData("act2", "day2"));
		assertEquals(1, sheet.getSingleData("act2", "day3"));
		assertEquals(Map.of("act1",6, "act2",1), sheet.sumsPerActivity());
		assertEquals(Map.of("day1",1, "day2",2,"day3",4), sheet.sumsPerDay());
	}

	@org.junit.Test
	public void testOfMap() {
		// un timesheet con 2 attività su 3 giorni, con nomi forniti
		TimeSheet sheet = this.factory.ofPartialMap(
			List.of("act1","act2"), 
			List.of("day1", "day2", "day3"),
			Map.of( // mappa (attività, giorno) -> n_ore
				new Pair<>("act1","day1"),1,  
				new Pair<>("act1","day2"),2,
				new Pair<>("act1","day3"),3,
				new Pair<>("act2","day3"),1));
		assertEquals(List.of("act1", "act2"), sheet.activities());
		assertEquals(List.of("day1", "day2", "day3"), sheet.days());
		assertEquals(2, sheet.getSingleData("act1", "day2"));
		assertEquals(3, sheet.getSingleData("act1", "day3"));
		assertEquals(0, sheet.getSingleData("act2", "day2"));
		assertEquals(1, sheet.getSingleData("act2", "day3"));
		assertEquals(Map.of("act1",6, "act2",1), sheet.sumsPerActivity());
		assertEquals(Map.of("day1",1, "day2",2,"day3",4), sheet.sumsPerDay());
	}
}
"
```

A:

```java
package asmd.lab3.task02.e2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimeSheetFactoryImplFewShowQwenGeneratedTest {
    
    private TimeSheetFactoryImpl factory;
    
    @BeforeEach
    public void initFactory() {
        this.factory = new TimeSheetFactoryImpl();
    }
    
    @Test
    public void testFlat() {
        // Create a timesheet with 3 activities and 5 days, each activity has 1 hour per day
        TimeSheet sheet = this.factory.flat(3, 5, 1);
        
        // Verifying activities and days
        assertEquals(List.of("act1", "act2", "act3"), sheet.activities());
        assertEquals(List.of("day1", "day2", "day3", "day4", "day5"), sheet.days());
        
        // Verifying single data retrieval
        assertEquals(1, sheet.getSingleData("act1", "day2"));
        assertEquals(1, sheet.getSingleData("act2", "day3"));
        assertEquals(0, sheet.getSingleData("act22", "day30")); // Non-existent activity/day
        
        // Verifying sums per activity
        assertEquals(Map.of("act1", 5, "act2", 5, "act3", 5), sheet.sumsPerActivity()); // 5 hours per activity
        
        // Verifying sums per day
        assertEquals(Map.of("day1", 3, "day2", 3, "day3", 3, "day4", 3, "day5", 3), sheet.sumsPerDay()); // 3 hours per day
    }
    
    @Test
    public void testOfListsOfLists() {
        // Create a timesheet with 2 activities over 3 days, using provided names and data
        TimeSheet sheet = this.factory.ofListsOfLists(
                List.of("a1", "a2"),
                List.of("d1", "d2", "d3"),
                List.of(
                        List.of(1, 2, 3), // Activity a1: 1, 2, 3 hours for days d1, d2, d3
                        List.of(0, 0, 1)  // Activity a2: 0, 0, 1 hours for days d1, d2, d3
                ));
        
        // Verifying activities and days
        assertEquals(List.of("a1", "a2"), sheet.activities());
        assertEquals(List.of("d1", "d2", "d3"), sheet.days());
        
        // Verifying single data retrieval
        assertEquals(2, sheet.getSingleData("a1", "d2"));
        assertEquals(3, sheet.getSingleData("a1", "d3"));
        assertEquals(0, sheet.getSingleData("a2", "d2"));
        assertEquals(1, sheet.getSingleData("a2", "d3"));
        
        // Verifying sums per activity
        assertEquals(Map.of("a1", 6, "a2", 1), sheet.sumsPerActivity());
        
        // Verifying sums per day
        assertEquals(Map.of("d1", 1, "d2", 2, "d3", 4), sheet.sumsPerDay());
    }
    
    @Test
    public void testOfRawData() {
        // Create a timesheet with 2 activities over 3 days, using raw data
        TimeSheet sheet = this.factory.ofRawData(2, 3, List.of(
                new Pair<>(0, 0), // 1 hour for act1 and day1
                new Pair<>(0, 1), // 1 hour for act1 and day2
                new Pair<>(0, 1), // 1 more hour for act1 and day2
                new Pair<>(0, 2), // 1 hour for act1 and day3
                new Pair<>(0, 2), // 1 more hour for act1 and day3
                new Pair<>(0, 2), // 1 more hour for act1 and day3
                new Pair<>(1, 2)) // 1 hour for act2 and day3
        );
        
        // Verifying activities and days
        assertEquals(List.of("act1", "act2"), sheet.activities());
        assertEquals(List.of("day1", "day2", "day3"), sheet.days());
        
        // Verifying single data retrieval
        assertEquals(2, sheet.getSingleData("act1", "day2"));
        assertEquals(3, sheet.getSingleData("act1", "day3"));
        assertEquals(0, sheet.getSingleData("act2", "day2"));
        assertEquals(1, sheet.getSingleData("act2", "day3"));
        
        // Verifying sums per activity
        assertEquals(Map.of("act1", 6, "act2", 1), sheet.sumsPerActivity());
        
        // Verifying sums per day
        assertEquals(Map.of("day1", 1, "day2", 2, "day3", 4), sheet.sumsPerDay());
    }
    
    @Test
    public void testOfPartialMap() {
        // Create a timesheet with 2 activities over 3 days, using a map of data
        TimeSheet sheet = this.factory.ofPartialMap(
                List.of("act1", "act2"),
                List.of("day1", "day2", "day3"),
                Map.of( // Map (activity, day) -> hours
                        new Pair<>("act1", "day1"), 1,
                        new Pair<>("act1", "day2"), 2,
                        new Pair<>("act1", "day3"), 3,
                        new Pair<>("act2", "day3"), 1));
        
        // Verifying activities and days
        assertEquals(List.of("act1", "act2"), sheet.activities());
        assertEquals(List.of("day1", "day2", "day3"), sheet.days());
        
        // Verifying single data retrieval
        assertEquals(2, sheet.getSingleData("act1", "day2"));
        assertEquals(3, sheet.getSingleData("act1", "day3"));
        assertEquals(0, sheet.getSingleData("act2", "day2"));
        assertEquals(1, sheet.getSingleData("act2", "day3"));
        
        // Verifying sums per activity
        assertEquals(Map.of("act1", 6, "act2", 1), sheet.sumsPerActivity());
        
        // Verifying sums per day
        assertEquals(Map.of("day1", 1, "day2", 2, "day3", 4), sheet.sumsPerDay());
    }
    
    @Test
    public void testEdgeCases() {
        // Test case with 0 activities and 0 days in a flat timesheet
        TimeSheet emptySheet = this.factory.flat(0, 0, 0);
        assertTrue(emptySheet.sumsPerActivity().isEmpty());
        assertTrue(emptySheet.sumsPerDay().isEmpty());
        
        // Test case with 0 days in a flat timesheet (but with activities)
        TimeSheet noDaysSheet = this.factory.flat(3, 0, 5);
        assertEquals(0, noDaysSheet.sumsPerDay().size());
        assertEquals(3, noDaysSheet.sumsPerActivity().size());
        assertEquals(0, noDaysSheet.sumsPerActivity().get("act1"));
        assertEquals(0, noDaysSheet.sumsPerActivity().get("act2"));
        assertEquals(0, noDaysSheet.sumsPerActivity().get("act3"));
        
        // Test case with 0 activities in a flat timesheet (but with days)
        TimeSheet noActivitiesSheet = this.factory.flat(0, 3, 5);
        assertEquals(3, noActivitiesSheet.sumsPerDay().size());
        assertEquals(0, noActivitiesSheet.sumsPerDay().get("day1"));
        assertEquals(0, noActivitiesSheet.sumsPerDay().get("day2"));
        assertEquals(0, noActivitiesSheet.sumsPerDay().get("day3"));
    }
    
    @Test
    public void testInvalidData() {
        // Test case with partial map missing some (activity, day) pairs
        List<String> activities = List.of("act1", "act2");
        List<String> days = List.of("day1", "day2", "day3");
        Map<Pair<String, String>, Integer> invalidData = new HashMap<>();
        invalidData.put(new Pair<>("act1", "day1"), 5); // Only one pair provided
        
        TimeSheet sheet = this.factory.ofPartialMap(activities, days, invalidData);
        
        // Verifying sums for missing pairs
        assertEquals(5, sheet.getSingleData("act1", "day1"));
        assertEquals(0, sheet.getSingleData("act1", "day2"));
        assertEquals(0, sheet.getSingleData("act1", "day3"));
        assertEquals(0, sheet.getSingleData("act2", "day1"));
        assertEquals(0, sheet.getSingleData("act2", "day2"));
        assertEquals(0, sheet.getSingleData("act2", "day3"));
    }
}
```

The generated tests cover 100% of the code, including edge cases and standard scenarios.

A better implementation would have been to use the `assertAll` method to group multiple assertions together, so that all
assertions are executed even if one of them fails.

No more changes were necessary to run tests.

## Few shot - e3

### Qwen 2.5:3b

Q:
```text
considering this class in java, i need you to generate tests using JUnit5 that cover all methods, edge cases and all possible outcomes "package asmd.lab3.task02.e3;

import java.util.*;
import java.util.stream.*;
import java.util.function.Function;

public class ListBuilderFactoryImpl implements ListBuilderFactory {

    // a utility function, essentially a flatMap for builders
    private <R,T> ListBuilder<R> flatMap(ListBuilder<T> builder, Function<T, ListBuilder<R>> fun) {
        return new ListBuilderImpl<>(builder.build().stream().flatMap(t -> fun.apply(t).build().stream()));
    }

    // A builder can just be an immutable wrapper of a list!
    private class ListBuilderImpl<T> implements ListBuilder<T> {
        private final List<T> list;

        public ListBuilderImpl(Stream<T> stream){
            this.list = stream.toList();
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            return new ListBuilderImpl<>(Stream.concat(this.list.stream(), list.stream()));
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {
            return add(lb.build());
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            return flatMap(this, tt -> tt.equals(t) ? lb : fromElement(tt));
        }

        @Override
        public ListBuilder<T> reverse() {
            var l2 = new ArrayList<>(list);
            Collections.reverse(l2);
            return new ListBuilderImpl<>(l2.stream());
        }

        @Override
        public List<T> build() {
            return this.list;
        }

    }

    @Override
    public <T> ListBuilder<T> empty() {
        return new ListBuilderImpl<>(Stream.empty());
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        return new ListBuilderImpl<>(Stream.of(t));
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        return new ListBuilderImpl<>(list.stream());
    }


    @Override
    public <T> ListBuilder<T> join(T start,  T stop, List<ListBuilder<T>> list) {
        return this
            .fromElement(start)
            .add(list.stream().flatMap(lb -> lb.build().stream()).toList())
            .add(List.of(stop));
    }
}
"

This is a test class that you can use as reference to generate a new class for tests: "package asmd.lab3.task02.e3;

import static org.junit.Assert.*;

import java.util.*;

public class Test {

	private ListBuilderFactory factory;

	@org.junit.Before
	public void initFactory() {
		this.factory = new ListBuilderFactoryImpl();
	}

	@org.junit.Test
	public void testEmpty() {
		// empty() rappresenta il builder di una lista vuota
		ListBuilder<Integer> empty = this.factory.<Integer>empty();
		assertEquals(List.of(), empty.build());
		// se gli aggiungo 10 e 20 diventa il builder di una lista (10, 20)
		assertEquals(List.of(10, 20),
				empty.add(List.of(10, 20))
						.build());
		// posso fare due add consecutive, concatenando le chiamate
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.add(List.of(30))
						.build());
		// con la concat ottengo un builder che rappresenta la concatenazione delle liste
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.concat(empty.add(List.of(30)))
						.build());
		// altro esempio con la concat
		assertEquals(List.of(10, 20, 30),
				empty.add(List.of(10, 20))
						.concat(empty.add(List.of(30)))
						.build());
	}

	@org.junit.Test
	public void testFromElement() {
		// fromElement() rappresenta il builder di una lista con un elemento
		ListBuilder<Integer> one = this.factory.fromElement(1);
		// add e concat funzionano come ci si aspetta
		assertEquals(List.of(1), one.build());
		assertEquals(List.of(1, 2, 3, 4),
				one.add(List.of(2, 3, 4)).build());
		assertEquals(List.of(1, 2, 1),
				one.concat(this.factory.fromElement(2))
						.concat(one)
						.build());
	}

	@org.junit.Test
	public void testBasicFromList() {
		// fromList() rappresenta il builder di una lista con n elementi
		ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
		assertEquals(List.of(1, 2, 3), l.build());
		// concat funzionano come ci si aspetta
		assertEquals(List.of(1, 2, 3, 1, 2, 3),
				l.concat(l).build());
		// replaceAll qui rimpiazza gli "1" con coppie "-1, -2"
		assertEquals(List.of(-1, -2, 2, 3, -1, -2, 2, 3),
				l.concat(l)
						.replaceAll(1, this.factory.fromList(List.of(-1, -2)))
						.build());
		// se non c'è match, la replaceAll non fa nulla
		assertEquals(List.of(1, 2, 3, 1, 2, 3),
				l.concat(l)
						.replaceAll(10, this.factory.fromList(List.of(-1, -2)))
						.build());
	}

	@org.junit.Test
	public void testReverseFromList() {
		ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
		assertEquals(List.of(1, 2, 3), l.build());
		// la reverse fa ottenere un builder che rappresenta la lista invertita
		assertEquals(List.of(3, 2, 1), l.reverse().build());
		assertEquals(List.of(1, 2, 3), l.reverse().reverse().build());
		assertEquals(List.of(1, 2, 3, 3, 2, 1),
				l.reverse().reverse().concat(l.reverse()).build());
	}

	@org.junit.Test
	public void testJoin() {
  		// la join è usabile per concatenare più builder, con un elemento iniziale e uno finale
		assertEquals(List.of("(", "1", "2", "3", "4",")"),
				this.factory.join("(", ")",
						List.of(this.factory.fromElement("1"),
								this.factory.fromElement("2"),
								this.factory.fromList(List.of("3","4")))).build());
	}

}"
```

A:

```java
package asmd.lab3.task02.e3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListBuilderFactoryImplTest {

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
```

The generated tests cover 100% of the code, including edge cases and standard scenarios.

A better implementation would have been to use the `assertAll` method to group multiple assertions together, so that all
assertions are executed even if one of them fails.

However a small human intervention was required to fix the expected values of the tests testJoinWithNullElement because
the expected value was a NullPointerException but the implementation does not throw it and produces a result anyway.

The test before the edit was this one:
```java
@Test
public void testJoinWithNullElement() {
    // Test join with null element (expect NullPointerException)
    assertThrows(NullPointerException.class, () -> {
        this.factory.join(null, "end", List.of(this.factory.fromElement("1"))).build();
    });
}
```

and after the edit it became:
```java
@Test
public void testJoinWithNullElement() {
    assertEquals(
            Arrays.asList(null, "1", "end"),
            this.factory.join(null, "end", List.of(this.factory.fromElement("1"))).build()
    );
}
```
