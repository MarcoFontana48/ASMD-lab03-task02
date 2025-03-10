package asmd.lab3.task02.e2;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TimeSheetFactoryImplZeroShotQwenGeneratedTest {
    @Test
    public void testSumsPerActivity() {
        // Example data
        List<String> activities = Arrays.asList("act1", "act2");
        List<String> days = Arrays.asList("day1", "day2");
        
        Map<Pair<String, String>, Integer> expectedData = new HashMap<>();
        Pair<String, String> act1Day1Pair = new Pair<>("act1", "day1");
        Pair<String, String> act1Day2Pair = new Pair<>("act1", "day2");
        Pair<String, String> act2Day1Pair = new Pair<>("act2", "day1");
        
        expectedData.put(act1Day1Pair, 10);
        expectedData.put(act1Day2Pair, 5);
        expectedData.put(act2Day1Pair, 8);
        
        TimeSheetFactoryImpl factory = new TimeSheetFactoryImpl();
        TimeSheet timeSheet = factory.ofPartialMap(activities, days, expectedData);
        Map<String, Integer> result = timeSheet.sumsPerActivity();
        
        assertEquals(expectedData.size(), result.size());
        for (Map.Entry<Pair<String, String>, Integer> entry : expectedData.entrySet()) {
            Pair<String, String> key = entry.getKey();
            Integer value = entry.getValue();
            assertContains(result.get(key.toString()), value);
        }
    }
    
    @Test
    public void testSumsPerDay() {
        // Example data
        List<String> activities = Arrays.asList("act1", "act2");
        List<String> days = Arrays.asList("day1", "day2");
        
        Map<Pair<String, String>, Integer> expectedData = new HashMap<>();
        Pair<String, String> act1Day1Pair = new Pair<>("act1", "day1");
        Pair<String, String> act1Day2Pair = new Pair<>("act1", "day2");
        Pair<String, String> act2Day1Pair = new Pair<>("act2", "day1");
        
        expectedData.put(act1Day1Pair, 8);
        expectedData.put(act1Day2Pair, 10);
        expectedData.put(act2Day1Pair, 5);
        
        TimeSheetFactoryImpl factory = new TimeSheetFactoryImpl();
        TimeSheet timeSheet = factory.ofPartialMap(activities, days, expectedData);
        Map<String, Integer> result = timeSheet.sumsPerActivity();
        
        assertEquals(expectedData.size(), result.size());
        for (Map.Entry<Pair<String, String>, Integer> entry : expectedData.entrySet()) {
            Pair<String, String> key = entry.getKey();
            Integer value = entry.getValue();
            assertContains(result.get(key.toString()), value);
        }
    }
    
    @Test
    public void testGetSingleData() {
        // Example data
        List<String> activities = Arrays.asList("act1", "act2");
        List<String> days = Arrays.asList("day1", "day2");
        
        Pair<String, String> expectedPairAct1Day1 = new Pair<>("act1", "day1");
        Pair<String, String> expectedPairAct2Day2 = new Pair<>("act2", "day2");
        
        Integer resultAct1Day1 = 5;
        Integer resultAct2Day2 = 8;
        
        TimeSheetFactoryImpl factory = new TimeSheetFactoryImpl();
        Map<String, Integer> sumsPerActivity = factory.ofPartialMap(activities, days, new HashMap<>()).sumsPerActivity();
        
        assertEquals(sumsPerActivity.get(expectedPairAct1Day1.toString()), resultAct1Day1);
        assertEquals(sumsPerActivity.get(expectedPairAct2Day2.toString()), resultAct2Day2);
        assertEquals(factory.ofPartialMap(activities, days, new HashMap<>()).getSingleData("act3", "day3"), 0);
    }
    
    @Test
    public void testFlat() {
        List<String> activities = Arrays.asList("act1", "act2");
        List<String> days = Arrays.asList("day1", "day2");
        
        Map<Pair<String, String>, Integer> expectedData = new HashMap<>();
        Pair<String, String> act1Day1Pair = new Pair<>("act1", "day1");
        Pair<String, String> act1Day2Pair = new Pair<>("act1", "day2");
        Pair<String, String> act2Day1Pair = new Pair<>("act2", "day1");
        
        expectedData.put(act1Day1Pair, 10);
        expectedData.put(act1Day2Pair, 5);
        expectedData.put(act2Day1Pair, 8);
        
        TimeSheetFactoryImpl factory = new TimeSheetFactoryImpl();
        TimeSheet timeSheet = factory.ofPartialMap(activities, days, expectedData);
        Map<String, Integer> resultSumsActivity = timeSheet.sumsPerActivity();
        Map<String, Integer> resultSumsDay = timeSheet.sumsPerDay();
        
        assertEquals(expectedData.size(), resultSumsActivity.size());
        for (Map.Entry<Pair<String, String>, Integer> entry : expectedData.entrySet()) {
            Pair<String, String> key = entry.getKey();
            Integer value = entry.getValue();
            assertContains(resultSumsActivity.get(key.toString()), value);
        }
        
        assertEquals(expectedData.size(), resultSumsDay.size());
        for (Map.Entry<Pair<String, String>, Integer> entry : expectedData.entrySet()) {
            Pair<String, String> key = entry.getKey();
            Integer value = entry.getValue();
            assertContains(resultSumsDay.get(key.toString()), value);
        }
    }
    
    private void assertContains(Integer result, Integer expected) {
        assertTrue(result != null);
        assertEquals(expected.intValue(), result.intValue());
    }
}
