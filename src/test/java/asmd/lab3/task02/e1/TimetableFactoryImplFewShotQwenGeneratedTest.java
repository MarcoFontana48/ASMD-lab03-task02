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
        assertEquals(1, cutTimetable.sums(Set.of("Activity1"), Set.of("Monday")));
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
        assertEquals(0, extremeCutTimetable.sums(Set.of("Activity1"), Set.of("Monday")));
    }
}