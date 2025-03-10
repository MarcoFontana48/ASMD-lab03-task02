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
