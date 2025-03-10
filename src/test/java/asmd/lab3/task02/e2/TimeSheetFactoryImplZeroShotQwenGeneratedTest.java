package asmd.lab3.task02.e2;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class TimeSheetFactoryImplZeroShotQwenGeneratedTest {
    
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
    public void testEmptyData() {
        TimeSheet emptyTimeSheet = timeSheetFactory.flat(0, 0, 0);
        
        assertAll("Empty TimeSheet",
                () -> assertEquals(0, emptyTimeSheet.sumsPerActivity().size()),
                () -> assertEquals(0, emptyTimeSheet.sumsPerDay().size()),
                () -> assertEquals(0, emptyTimeSheet.getSingleData("act1", "day1"))
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
