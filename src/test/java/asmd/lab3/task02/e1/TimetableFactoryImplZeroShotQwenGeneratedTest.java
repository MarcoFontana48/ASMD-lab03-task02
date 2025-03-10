package asmd.lab3.task02.e1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static asmd.lab3.task02.e1.TimetableFactoryImpl.addToSet;
import static asmd.lab3.task02.e1.TimetableFactoryImpl.concatSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableFactoryImplZeroShotQwenGeneratedTest {
    private final TimetableFactory factory = new TimetableFactoryImpl();
    
    @Test
    public void testEmpty() {
        Timetable timetable = factory.empty();
        assertEquals(0, timetable.activities().size());
        assertEquals(0, timetable.days().size());
    }
    
    @Test
    public void testSingle() {
        String act = "Activity";
        String day = "Day";
        Timetable singleTimetable = factory.single(act, day);
        
        // Check if it contains only this activity and day combination
        assertEquals(1, singleTimetable.activities().size());
        assertEquals(singleTimetable.days().size(), 1);
        assertEquals(singleTimetable.getSingleData(act, day), 1);
    }
    
    @Test
    public void testJoin() {
        String act1 = "Activity1";
        String day1 = "Day1";
        
        // Create two Timetables
        Timetable table1 = factory.single(act1, day1);
        Timetable table2 = factory.empty();
        
        // Combine them and check the resulting data function.
        Timetable combinedTimetable = factory.join(table1, table2);
        
        assertEquals(1, combinedTimetable.activities().size());
        assertEquals(combinedTimetable.days().size(), 1);
    }
    
    @Test
    public void testCut() {
        String act = "Activity";
        String day = "Day";
        
        // Create a timetable and use it to create bounds for cutting
        Timetable table = factory.single(act, day);
        BiFunction<String, String, Integer> cutBounds = (a, d) -> 10;
        
        Timetable cutTimetable = factory.cut(table, cutBounds);
        
        assertEquals(1, cutTimetable.activities().size());
    }
    
    @Test
    public void testHelperMethods() {
        Set<String> activities = new HashSet<>();
        Set<String> days = new HashSet<>();
        
        // Add two items to each set.
        addToSet(activities, "Item1");
        addToSet(days, "Item2");
        
        // Check if both helper methods work as expected and return a combined set.
        Set<String> expectedActivities = new HashSet<>(Arrays.asList("Item1"));
        Set<String> expectedDays = new HashSet<>(Arrays.asList("Item2"));
        
        assertEquals(expectedActivities, concatSet(activities, Set.of("Item1")));
        assertEquals(expectedDays, concatSet(days, Set.of("Item2")));
        
        // Ensure that adding an existing element doesn't change the result.
        activities.add("Item1");
        days.add("Item2");
        
        assertEquals(expectedActivities, concatSet(activities, expectedActivities));
    }
    
    @Test
    public void testSums() {
        Timetable table = factory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of("Math");
        Set<String> days = Set.of("Monday", "Tuesday");
        
        int sum = table.sums(activities, days);
        
        assertEquals(3, sum);  // 2 hours on Monday + 1 hour on Tuesday
    }
    
    @Test
    public void testSumsWithEmptyActivities() {
        Timetable table = factory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of();  // Empty set of activities
        Set<String> days = Set.of("Monday", "Tuesday");
        
        int sum = table.sums(activities, days);
        
        assertEquals(0, sum);  // No activities to sum
    }
    
    @Test
    public void testSumsWithEmptyDays() {
        Timetable table = factory.single("Math", "Monday").addHour("Math", "Monday").addHour("Math", "Tuesday");
        
        Set<String> activities = Set.of("Math");
        Set<String> days = Set.of();  // Empty set of days
        
        int sum = table.sums(activities, days);
        
        assertEquals(0, sum);  // No days to sum
    }
}