package asmd.lab3.task02.e2;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimeSheetFactoryImplFewShowQwenGeneratedTest {
    
    @Test
    public void testWithMultipleHoursPerPair() {
        List<String> activities = List.of("act1", "act2");
        List<String> days = List.of("day1", "day2");
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        hours.put(new Pair<>("act1", "day1"), 1);
        hours.put(new Pair<>("act1", "day2"), 2); // act1 has multiple hours in day1 and day2
        hours.put(new Pair<>("act2", "day3"), 1);
        
        TimeSheet sheet = new TimeSheet(activities, days, hours);
        
        assertEquals(Map.of("act1", 3, "act2", 1), sheet.sumsPerActivity());
        assertEquals(Map.of("day1", 1, "day2", 2, "day3", 0), sheet.sumsPerDay());
    }
    
    @Test
    public void testWithZeroHoursForAPair() {
        List<String> activities = List.of("act1", "act2");
        List<String> days = List.of("day1", "day2");
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        hours.put(new Pair<>("act1", "day1"), 1);
        hours.put(new Pair<>("act1", "day2"), 0); // act1 has zero hours in day2
        hours.put(new Pair<>("act2", "day3"), 1);
        
        TimeSheet sheet = new TimeSheet(activities, days, hours);
        
        assertEquals(Map.of("act1", 1, "act2", 1), sheet.sumsPerActivity());
        assertEquals(Map.of("day1", 0, "day2", 1, "day3", 0), sheet.sumsPerDay()); // day2 has zero hours
    }
    
    @Test
    public void testWithDuplicateActivityDayPairs() {
        List<String> activities = List.of("act1", "act2");
        List<String> days = List.of("day1", "day2");
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        hours.put(new Pair<>("act1", "day1"), 1);
        hours.put(new Pair<>("act1", "day2"), 1); // act1's hours overlap in day1 and day2
        hours.put(new Pair<>("act2", "day3"), 1);
        
        TimeSheet sheet = new TimeSheet(activities, days, hours);
        
        assertEquals(Map.of("act1", 2, "act2", 1), sheet.sumsPerActivity());
        assertEquals(Map.of("day1", 0, "day2", 2, "day3", 0), sheet.sumsPerDay()); // day1 and day2 each contribute 1 hour
    }
    
    @Test
    public void testWithNullInput() {
        List<String> activities = null;
        List<String> days = List.of("day1", "day2");
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        
        try {
            TimeSheet sheet = new TimeSheet(activities, days, hours);
            fail("Expected IllegalArgumentException for null activities list.");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }
        
        activities = List.of("act1", "act2");
        days = null;
        
        try {
            TimeSheet sheet = new TimeSheet(activities, days, hours);
            fail("Expected NullPointerException for null days list.");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected behavior
        }
    }
    
    @Test
    public void testWithEmptyInput() {
        List<String> activities = List.of();
        List<String> days = List.of("day1", "day2");
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        
        try {
            TimeSheet sheet = new TimeSheet(activities, days, hours);
            fail("Expected IllegalArgumentException for empty activities list.");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }
        
        activities = List.of("act1", "act2");
        days = List.of();
        
        try {
            TimeSheet sheet = new TimeSheet(activities, days, hours);
            fail("Expected NullPointerException for empty days list.");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected behavior
        }
    }
    
    @Test
    public void testWithSingleActivityDayPair() {
        List<String> activities = List.of("act1");
        List<String> days = List.of("day1");
        Map<Pair<String, String>, Integer> hours = new HashMap<>();
        hours.put(new Pair<>("act1", "day1"), 2); // act1 has multiple hours in day1
        
        TimeSheet sheet = new TimeSheet(activities, days, hours);
        
        assertEquals(Map.of("act1", 2), sheet.sumsPerActivity());
        assertEquals(Map.of("day1", 2), sheet.sumsPerDay()); // all activity-day pairs contribute to this one
    }
}
