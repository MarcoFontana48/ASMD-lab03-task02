package asmd.lab3.task02.e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TimetableFactoryImplZeroShotQwenGeneratedTest {
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