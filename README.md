# Task 02 - Test generation
In order to make the tests, i have used 2 different models:
- smollm 360m
- qwen 2.5:3b

## Zero shot - e1
I have asked the 2 models the same question:

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

### Smollm 360m

A:
```java
import java.util.*;
import java.util.function.*;

public class TimetableFactoryImpl implements Timetable as.Timetable {
    private static final int NUM_ACTIVITIES = 10; // number of activities

    private static final int NUM_DAYS = 24 * NUM_ACTIVITIES; // number of days in a week

    private static final int NUM_Hours = NUM_DAYS * NUM_ACTIVITIES; // total number of hours per day

    private static class TimetableData {
        private Set<String> activities, days;

        public TimetableData(Set<String> activities, Set<String> days) {
            this.activities = activities;
            this.days = days;
        }

        private static class BiFunction {
            private String data, day;

            public BiFunction(String activity, String day) {
                data = data + day;
                day = day + 1;
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.data = data;
                this.day = day;
            }
        }

        private static class BiPredicate {
            private String activity, day;

            public BiPredicate() {
                this.activity = "activities";
                this.day = 0;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.data = data;
                this.day = day;
            }
        }

        private static class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        private static class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        private static class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        private static class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        private static class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        private static class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }

        class BiPredicateBi as.Timetable {
            private int id, data, day;

            public BiPredicateBi() {
                id = 0;
                data = "activities";
                day = 1;
            }

            public void test(Data<String, String> data, Data<String, String> day) {
                if (data.equals("activities")) {
                    return true;
                } else if (day == 1) {
                    return false;
                } else {
                    return day == 0;
                }
            }

            public void apply(Data<String, String> data, Data<String, String> day) {
                this.id = id;
                this.data = data;
                this.day = day;
            }
        }
    </class>
</template>
```

The generated code is not a test class and repeats itself many times, producing always the same code, plus it ends
with HTML tags.

This is unusable and has to be rewritten entirely from scratch.

### Qwen 2.5:3b

A:
```java
@Test
public void testEmpty() {
    Timetable timetable = factory.empty();
    assertEquals(0, timetable.activities().size());
    assertEquals(0, timetable.days().size());
    assertEquals(0, timetable.data.apply("a", "b"));
}

@Test
public void testSingle() {
    String act = "Activity";
    String day = "Day";
    Timetable singleTimetable = factory.single(act, day);

    // Check if it contains only this activity and day combination
    assertEquals(1, singleTimetable.activities().size());
    assertEquals(singleTimetable.days().size(), 1);
    assertEquals(singleTimetable.data.apply("a", "b"), 0);
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
    assertEquals(combinedTimetable.data.apply(act1, day1), 1);
}

@Test
public void testCut() {
    String act = "Activity";
    String day = "Day";

    // Create a timetable and use it to create bounds for cutting
    Timetable table = factory.single(act, day);
    BiFunction<String, String, Integer> cutBounds = (a,d) -> 10;

    Timetable cutTimetable = factory.cut(table, cutBounds);

    assertEquals(1, cutTimetable.data.apply(act, day));
    // Check if the other activity and days have their values capped at 9.
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

    assertEquals(expectedActivities, concatSet(activities, Set.of("Item3")));
    assertEquals(expectedDays, concatSet(days, Set.of("Item4")));

    // Ensure that adding an existing element doesn't change the result.
    activities.add("Item1");
    days.add("Item2");

    assertEquals(expectedActivities, concatSet(activities, expectedActivities));
}
```

the provided code does not cover all the methods, and presents some errors:
- it is using a field 'factory' that was not created
- it is using a field 'data' that does not exist

in order to fix those problems it is required to create the missing field, and remove the unnecessary code that uses
the data field

The edited code looks like this:

```java
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
}
```

however this still does not cover all the methods:
- the methods 'sums' and 'statistics' are not covered

So another small human intervention is required to also cover this missing part:

```java
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
```

Now it covers all the methods but does not consider edge cases, also, it does not use the 'assertAll' method to cover 
all the tests. So it would require some more effort to add those changes.

## Zero shot - e2
I have asked 2 models the same question:

Q: 
```text
considering this class in java, i need you to generate tests that cover all methods, edge cases and all possible outcomes "package asmd.lab3.task02.e2;

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
```

### Smollm 360m

A:
```java
import java.util.function.BiFunction;
import java.util.stream.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeSheetFactoryImpl implements TimeSheetFactory {
    private static final String TIME_SHEET_ID = "time-sheet";

    public TimeSheetFactory() {
        // An implementation of a Timetable as an immutable class with activities, days, and an association of activities+days to hours
        private class TimerTable implements TimerTable {
            private static final int NUM_ACTIONS = 10;
            private static final int NUM_DAYS = 5;

            public TimerTable(int numActions) {
                this.NUM_ACTIONS = numActions
            }

            @Override
            public Map<String, Integer> sumsPerActivity() {
                return new Map<>();
                for (int i = 0; i < NUM_ACTIONS; ++i) {
                    int activityId = i % NUM_ACTIONS;
                    int daysInActivity = i / NUM_ACTIONS;
                    int sumOfActivities = 0;
                    for (int day = 1; day <= daysInActivity; ++day) {
                        int activityId = day % NUM_DAYS;
                        int hours = day / NUM_DAYS;

                        if (activityId == 0) {
                            int remainingHours = 0;
                            int totalHours = 0;
                            for (int dayToRun = 1; dayToRun <= daysInActivity; ++dayToRun) {
                                int remainingHours += hours;
                            }
                            int remainingHours %= NUM_DAYS;
                            int remainingDays = remainingHours - daysInActivity;

                            if (remainingHours == 0) {
                                int remainingHours = 0;
                            } else {
                                int remainingHours += remainingDays;
                            }

                            int sumOfActivities = activityId + remainingHours;
                            int totalHours += hours;
                            int sumOfEvents = activityId + remainingHours - daysInActivity;
                            int totalEvents = remainingHours - daysToRun;

                            if (sumOfEvents == 0) {
                                int sumOfEvents = 0;
                            } else {
                                int sumOfEvents += remainingHours;
                            }

                            int remainingDays = remainingHours % NUM_DAYS;
                            int remainingHours -= remainingDays;
                            int remainingHours += remainingDays - daysToRun;

                            int totalHours += hours + remainingHours;
                            int totalEvents += remainingHours + remainingDays;

                            int sumOfActivities = activityId + remainingHours;
                            int sumOfEvents = activityId + remainingHours - daysInActivity;
                            int sumOfEvents += remainingHours + remainingDays;

                            int sumOfEvents += remainingHours + remainingDays;
                            int sumOfEvents += remainingHours + remainingDays;

                            int sumOfActivities += remainingHours + remainingDays;
                            int sumOfEvents += remainingHours + remainingDays;

                            if (remainingHours == 0) {
                                int sumOfEvents = 0;
                            } else {
                                int sumOfEvents += remainingHours;
                            }

                        }

                        int totalHoursInDay = hours * NUM_DAYS;
                        int totalEventsInDay = days * NUM_DAYS;

                        int sumOfActivities += totalHoursInDay + totalEvents;
                        int sumOfEvents += totalHoursInDay + totalEvents;

                        return new TimerTable(sumOfActivities, sumOfEvents);
                    }
                }
            }
        }
    }
}
```

The generated test class is not a test class and cannot be used as one, it has to be rewritten from scratch.

### Qwen 2.5:3b

A:
```java
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
    Map<String, Integer> result = factory.newTimeSheet().sumsPerActivity();

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
    Map<String, Integer> result = factory.newTimeSheet().sumsPerDay();

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
    Map<String, Integer> sumsPerActivity = factory.newTimeSheet().sumsPerActivity();

    assertEquals(sumsPerActivity.get(expectedPairAct1Day1.toString()), resultAct1Day1);
    assertEquals(sumsPerActivity.get(expectedPairAct2Day2.toString()), resultAct2Day2);

    Pair<String, String> expectedPairNotPresent = new Pair<>("act3", "day3");

    assertEquals(factory.newTimeSheet().getSingleData("act3", "day3"), 0);
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
    Map<String, Integer> resultSumsActivity = factory.newTimeSheet().sumsPerActivity();
    Map<String, Integer> resultSumsDay = factory.newTimeSheet().sumsPerDay();

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
```

The generated code has an error that have to be fixed:
- the 'factory' variable in each test calls an unexisting 'newTimeSheet()' method.
This will require to rewrite part of each test:

```java
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
```

I have rewritten each test to achieve the same result as before but calling the right methods.

However, the generated code does not cover all the methods and edge cases, and also does not use 'assertAll' method
that would be required to each test with multiple assertions.

So it would require some more effort to add those changes.

The generated code is too far from the expected result, and it is not immediately usable.

## Zero shot - e3
I have asked 2 models the same question:

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

### Smollm 360m

A:
```java
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.Map<T, List<T>>;
import java.util.stream.List<T>;

public class ListBuilderFactoryImpl implements ListBuilderFactory {
    private static final String TYPE_LIST = "list";

    public ListBuilderFactory ImplementsListBuilder(Type type) {
        return new ListBuilderFactoryImpl(type);
    }

    public ListBuilderFactory ImplementsListBuilder(Type type, List<T> list) {
        return new ListBuilderFactoryImpl(type, list);
    }

    private static class ListBuilderFactoryImpl implements ListBuilderFactory {

        private static final String TYPE_LIST = "list";
        private static final List<T> list = new ArrayList();

        public ListBuilderFactory ImplementsListBuilder(Type type, List<T> list) {
            return new ListBuilderFactoryImpl(type, list);
        }
    }

    private static class ListBuilderFactoryImpl implements ListBuilderFactory {
        private static final String TYPE_LIST = "list";
        private static final List<T> list = new ArrayList();

        public ListBuilderFactory ImplementsListBuilder(Type type, List<T> list) {
            return new ListBuilderFactoryImpl(type, list);
        }
    }

    private static class ListBuilderFactoryImpl implements ListBuilderFactory {
        private static final String TYPE_LIST = "list";
        private static final List<T> list = new ArrayList();

        public ListBuilderFactory ImplementsListBuilder(Type type, List<T> list) {
            return new ListBuilderFactoryImpl(type, list);
        }
    }

    private static class ListBuilderFactoryImpl implements ListBuilderFactory {
        private static final String TYPE_LIST = "list";
        private static final List<T> list = new ArrayList();

        public ListBuilderFactory ImplementsListBuilder(Type type, List<T> list) {
            return new ListBuilderFactoryImpl(type, list);
        }
    }
}
```

The generated test class is not a test class and it also presents many errors up to a point where it is completely 
unusable and has to be rewritten manually from scratch.

### qwen 2.5:3b

A:
```java
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ListBuilderFactoryImplTest {

    private final Function<ListBuilder<Integer>, ListBuilder<Integer>> addList = (builder) -> new ListBuilderImpl<>(Arrays.asList(1,
2).stream().flatMap(i -> builder.build().stream()).toList());

    private final ListBuilder<Integer> fromElementTestList = new ListBuilderImpl<>(Arrays.asList(5).stream());

    @org.junit.jupiter.api.Test
    void testEmpty() {
        ListBuilder<Integer> empty = ListBuilderFactoryImpl.empty();
        assertEquals(Arrays.asList(), empty.build());
    }

    @org.junit.jupiter.api.Test
    void testFromElement() {
        ListBuilder<Integer> singleElement = fromElementTestList;
        ListBuilder<Integer> expected = new ListBuilderImpl<>(Arrays.asList(5));
        assertTrue(singleElement.addAll(fromElementTestList).build().equals(expected.build()));
    }

    @org.junit.jupiter.api.Test
    void testFromList() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        ListBuilder<Integer> expected = new ListBuilderImpl<>(list.stream());
        assertEquals(expected, fromList(list));
    }

    @org.junit.jupiter.api.Test
    void testJoin() {
        List<ListBuilder<Integer>> subLists = Arrays.asList(
            ListBuilderFactoryImpl.empty(),
            addList(fromElementTestList),
            ListBuilderFactoryImpl.empty()
        );

        ListBuilder<Integer> expected = new ListBuilderImpl<>(Arrays.asList(5).stream());
        assertEquals(expected, join(0, 2, subLists));
    }
}

private class ListBuilderImpl<T extends Number> implements ListBuilder<T> {

    private final Stream<Stream<T>> elements;

    public ListBuilderImpl(Stream<Stream<T>> elements) {
        this.elements = elements;
    }

    @Override
    public ListBuilder<T> add(ListBuilder<T> lb) {
        return new ListBuilderImpl<>(elements.concat(lb.elements()));
    }

    @Override
    public ListBuilder<T> concat(ListBuilder<T> lb) {
        return add(lb);
    }

    @Override
    public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
        throw new UnsupportedOperationException("replaceAll not implemented");
    }

    @Override
    public ListBuilder<T> reverse() {
        return new ListBuilderImpl<>(elements.map(Elements::reverse));
    }

    @Override
    public List<T> build() {
        var allElements = elements.flatMap(e -> e).toList();
        return Stream.concat(allElements.stream(), elements.flatMap(e -> e)).distinct().collect(Collectors.toList());
    }

    private static final class Elements extends ArrayList<Stream<?>> implements Stream<T>, Iterable<Number> {

        @Override
        public Iterator<T> iterator() {
            var iter = allElements.iterator();
            while (iter.hasNext()) {
                yield(iter.next());
            }
            return this;
        }

        @Override
        public T next() {
            throw new NoSuchElementException("next");
        }

        @Override
        public void removeIf(Predicate<? super Number> predicate) {
            throw new UnsupportedOperationException("removeIf not implemented");
        }

        private Elements(Stream<?>... elements) {
            this.addAll(Arrays.asList(elements).stream().map(e -> e.elementStream()).collect(Collectors.toList()));
        }

        private static final class Stream<T extends Number> extends AbstractSequentialListIterator<T> implements Iterable<Number> {

            public T element;

            @Override
            protected T initialValue() {
                throw new NoSuchElementException("Initial Value");
            }

            @Override
            public void add(T t) {
                throw new UnsupportedOperationException("add not implemented");
            }
        }

        private Elements reverse(Stream<?>... elements) {
            for (int i = 0; i < elements.length; ++i) {
                elements[i] = elements[i].reverse();
            }
            return this;
        }
    }
}

private ListBuilder<Integer> fromList(List<Integer> list) {
    return new ListBuilderImpl<>(list.stream());
}

private ListBuilder<Integer> join(Integer start, Integer stop, List<ListBuilder<Integer>> lists) {
    var builder = empty().addAll(fromElement(start)).concat(joinLists(lists));
    if (stop != null) {
        return builder.replaceAll(stop, fromElementTestList);
    }
    return builder;
}

private ListBuilder<Integer> joinLists(List<ListBuilder<Integer>> subLists) {
    var lists = Lists.newArrayList(subLists).stream().flatMap(lb -> lb.elements()).collect(Collectors.toList());
    if (subLists.isEmpty()) {
        return new ListBuilderImpl<>(lists::stream);
    }
    var lastListIterator = lists.stream().skip(lists.size() - 1).findFirst();
    for (int i = 0; i < subLists.size(); ++i) {
        if (!subLists.get(i).elements().equals(lists.stream().skip(lists.size() - 1 - i).findFirst())) {
            throw new IllegalArgumentException("Lists do not match");
        }
        lists = Lists.newArrayList(subLists.get(i).elements());
    }
    return new ListBuilderImpl<>(lists::stream);
}
```

The generated code is full of errors up to a point where it is pointless to try to fix it and it's better to rewrite it
completely from scratch.

The reasons are:
- it creates new classes that are not needed (the prompt was about tests, not other classes
- the generated code presents multiple errors and cannot be compiled
- the generated tests are basic tests that can be considered as a starting point but they are not complete and do not
  cover all the methods, edge cases and all possible outcomes.

## Few shot - e1
I have asked 2 models the same question:

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

### Smollm 360m

A:
```java
import org.junit.Test;
import org.junit.runner.UnitTestResults;
import org.junit. runners.JUnit4Runner;
import org.junit.runner.runner.Runnermethods;
import org.junit.runner.runner.runner.Runnermethods;
import org.junit.runner.runner.runner.runner.Runner;
import org.junit.runner.runner.runner.runner.runner.RunnerTestResult;

public class TestJoin {
    private Runnermethods runner;

    public static void testJoin(Runnermethods runner) {
        // Create a new test case
        Runnermethods.new()
                .setName("join")
                .setClass("test_join", "TestJoin");

        // Call the test method
        TestJoin.testJoin(runner);
    }

    public static void main(String[] args) {
        // Create a new class to represent the joiner
        Class<?=Runnermethods.class> joiner = new Class<?=Runnermethods.class>();

        // Call the test method
        TestJoin.testJoin(runner);
    }
}
```

The provided test class is not complete and does not cover all methods, edge cases, and possible outcomes.
It's a basic starting point, not at all a complete test suite and it also has a couple of errors like:

```java
Runnermethods.new()
        .setName("join")
        .setClass("test_join", "TestJoin");
```

Where the 'new' keyword is used as method.
So huge human intervention is needed to fix the test class to the point where the generated test class is useless and
has to be rewritten from scratch.

### qwen 2.5:3b

A:
```java
package asmd.lab3.e1.task01;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTimetableFactoryImpl {

    private TimetableFactory factory;

    @BeforeEach
    public void initFactory() {
        factory = new TimetableFactoryImpl();
    }

    // Test for empty()
    @Test
    public void testEmpty() {
        Timetable result = factory.empty();
        assertEquals(0, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for single()
    @Test
    public void testSingle() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;
        Timetable result = factory.single("activity", "day");
        assertEquals(1, result.sums(Collections.singleton("activity"), Collections.singleton("day")));
    }

    // Test for join()
    @Test
    public void testJoin() {
        BiFunction<String, String, Integer> data1 = (a,d) -> 1;
        BiFunction<String, String, Integer> data2 = (a,d) -> 2;
        Timetable table1 = factory.single("activity", "day");
        Timetable table2 = factory.single("other_activity", "other_day");

        Timetable result = factory.join(table1, table2);
        assertEquals(1, result.sums(Collections.singleton("activity"), Collections.singleton("day")));
        assertEquals(2, result.sums(Collections.singleton("other_activity"), Collections.singleton("other_day")));
    }

    // Test for reverseFromList()
    @Test
    public void testReverseFromList() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;
        ListBuilder<Integer> listBuilder = mock(ListBuilder.class);

        factory.fromList(List.of(1)).reverse().build();
        factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).reverse().build();

        // Assuming reverseFromList is implemented, we can test with these calls
    }

    // Test for fromElement()
    @Test
    public void testFromElement() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromElement("element");
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for fromList()
    @Test
    public void testFromList() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(1)).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for reverseFromList()
    @Test
    public void testReverseFromList() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).reverse().build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for reverseFromList()
    @Test
    public void testReverseFromListWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).reverse().build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAll() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(1, 2)).replaceAll(1, factory.single("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for reverseFromList()
    @Test
    public void testReverseFromListWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).reverse().build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for fromElement()
    @Test
    public void testFromElementWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromElement("element").build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for fromList()
    @Test
    public void testFromListWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for reverseFromList()
    @Test
    public void testReverseFromListWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).reverse().build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for reverseFromList()
    @Test
    public void testReverseFromListWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).reverse().build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for reverseFromList()
    @Test
    public void testReverseFromListWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).concat(factory.fromList(List.of(3))).reverse().build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }

    // Test for replaceAll()
    @Test
    public void testReplaceAllWithData() {
        BiFunction<String, String, Integer> data = (a,d) -> 1;

        Timetable result = factory.fromList(List.of(2)).replaceAll(factory.fromElement("element")).build();
        assertEquals(1, result.sums(Collections.singleton("any_activity"), Collections.singleton("any_day")));
    }
    
    //...
```

The response was stuck in a loop and I had to stop it manually.

The generated test class presents multiple errors, it tries for example to call a method 'fromList' that does not 
exist. So it is unusable and human intervention is needed to fix all tests generated.

It did not consider edge cases like null inputs, so it would require some work to add tests.


## Few shot - e2
I have asked 2 models the same question:

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

### Smollm 360m

A:
```python
def get_average_duration(data):
    if not isinstance(data, list) or len(data) == 0:
        raise ValueError("Input must be a list of dictionaries.")

    data = [dict(d) for d in data]
    average_duration = sum(d.get("duration", 0) for d in data) / len(data)
    return f"Average duration is {average_duration:.2f} seconds."
```

Since the first reply was in python instead of java, i asked the same question a second time, to which it replied again
in python:

A:
```python
import json

def get_data(data):
    if isinstance(data, dict):
        return {key: data[key] for key in data.keys()}
    elif isinstance(data, list):
        return [get_data(item) for item in data]
    else:
        raise TypeError("Input must be a dictionary or a list.")
```

Since it keeps generating a python responses I haven't asked it to this model again.

### qwen 2.5:3b

A:
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestOfPartialMap {

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
```

The provided code presents a single small error in each test method: the TimeSheet interface is not a class, so it cannot be 
created using 'new' keyword. Also replacing it with an implementation of that interface does not solve the issue since
the constructor required is different than the one implemented in the class.

So a small human intervention is needed to fix the test class, but overall it is a good starting point that also considers
some edge cases like null and empty inputs to methods.

## Few shot - e3
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

### Smollm 360m

A:
```python
import unittest
from unittest.mock import patch

class TestJoin(unittest.TestCase):
    @patch('os.path.join')
    def test_join(self, mock_join):
        mock_join.side_effect = lambda x: f"({x})"
        self.assertEqual(join("1", "2", "3"), "1")
        self.assertEqual(join("4", "5", "6"), "7")

class TestJoinWithReversal(unittest.TestCase):
    @patch('os.path.join')
    def test_join_reversed(self, mock_join):
        mock_join.side_effect = lambda x: f"({x})"
        self.assertEqual(join("1", "2", "3"), "1")
        self.assertEqual(join("4", "5", "6"), "7")

class TestJoinWithReversalAndCombiner(unittest.TestCase):
    @patch('os.path.join')
    def test_join_combiner(self, mock_join):
        mock_join.side_effect = lambda x: f"({x})"
        self.assertEqual(join("1", "2", "3"), "1")
        self.assertEqual(join("4", "5", "6"), "7")

class TestJoinAndCombiner(unittest.TestCase):
    @patch('os.path.join')
    def test_join_and_combiner(self, mock_join):
        mock_join.side_effect = lambda x: f"({x})"
        self.assertEqual(join("1", "2", "3"), "1")
        self.assertEqual(join("4", "5", "6"), "7")

class TestJoinAndCombinerWithReversal(unittest.TestCase):
    @patch('os.path.join')
    def test_join_and_combiner(self, mock_join):
        mock_join.side_effect = lambda x: f"({x})"
        self.assertEqual(join("1", "2", "3"), "1")
        self.assertEqual(join("4", "5", "6"), "7")

if __name__ == '__main__':
    unittest.main()
```

Since the response is once again in python I rebooted the model and asked it again the same question, but another 
response in python was received:

A:
```python
import unittest
from unittest.mock import patch

class TestTestClass(unittest.TestCase):
    def test_empty(self):
        factory = TestFactory()
        self.assertEqual(factory.fromElement("1"), "1")
        self.assertEqual(factory.fromElement(2), "2")
        self.assertEqual(factory.fromList([3, 4]), [3, 4])

    def test_add(self):
        factory = TestFactory()
        self.assertEqual(factory.fromElement(5).build(), "10")
        self.assertEqual(factory.fromElement(6).build(), "20")
        self.assertEqual(factory.fromList([7, 8]).build(), [14, 22])

    def test_concat(self):
        factory = TestFactory()
        self.assertEqual(factory.fromElement("1").build(), "1")
        self.assertEqual(factory.fromElement(2).build(), "2")
        self.assertEqual(factory.fromList([3, 4]).build(), [6, 9])

    def test_join(self):
        factory = TestFactory()
        self.assertEqual(factory.fromElement("1").join(), "1")
        self.assertEqual(factory.fromElement(2).join(), "2")
        self.assertEqual(factory.fromList([3, 4]).join(), ["3", "4"])

if __name__ == "__main__":
    unittest.main()
```

Since it keeps generating python responses I haven't asked it to this model again.

### qwen 2.5:3b

A:
```java
package asmd.lab3.task02.e3;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.*;

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
        ListBuilder<Integer> expectedWithElements = this.factory.fromElement(10).add(this.factory.fromElement(20));
        assertNotEquals(List.of(), expectedWithElements.build());
        assertEquals(List.of(10, 20), expectedWithElements.build());

        // posso fare due add consecutive, concatenando le chiamate
        ListBuilder<Integer> expectedAddAdd = this.factory.empty().add(this.factory.fromElement(1)).add(this.factory.fromElement(2));
        assertNotEquals(List.of(), expectedAddAdd.build());
        assertEquals(List.of(1, 2), expectedAddAdd.build());

        // con la concat ottengo un builder che rappresenta la concatenazione delle liste
        ListBuilder<Integer> expectedConcat = this.factory.empty().concat(this.factory.fromElement(1)).concat(this.factory.fromElement(2));
        assertNotEquals(List.of(), expectedConcat.build());
        assertEquals(List.of(1, 2), expectedConcat.build());

        // altro esempio con la concat
        ListBuilder<Integer> expectedConcatConcat = this.factory.empty().concat(this.factory.fromElement(1)).add(this.factory.fromElement(2));
        assertNotEquals(List.of(), expectedConcatConcat.build());
        assertEquals(List.of(1, 2), expectedConcatConcat.build());

    }

    @org.junit.Test
    public void testFromElement() {
        // fromElement() rappresenta il builder di una lista con un elemento
        ListBuilder<Integer> one = this.factory.fromElement(1);
        assertNotEquals(List.of(), one.build());
        assertEquals(List.of(1), one.build());

        ListBuilder<String> stringOne = this.factory.fromElement("1");
        assertNotEquals(List.of(), stringOne.build());
        assertEquals(List.of("1"), stringOne.build());

        // concat con un singolo elemento
        ListBuilder<Integer> expectedConcatWithSingleElement = this.factory.empty().concat(one);
        assertNotEquals(List.of(), expectedConcatWithSingleElement.build());
        assertEquals(List.of(1), expectedConcatWithSingleElement.build());
    }

    @org.junit.Test
    public void testBasicFromList() {
        // fromList() rappresenta il builder di una lista con n elementi
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertNotEquals(List.of(), l.build());
        assertEquals(List.of(1, 2, 3), l.build());

        // concat funzionano come ci si aspetta
        ListBuilder<Integer> expectedConcat = this.factory.empty().concat(l).concat(this.factory.fromElement(4)).concat(l);
        assertNotEquals(List.of(), expectedConcat.build());
        assertEquals(List.of(1, 2, 3, 4, 1, 2, 3), expectedConcat.build());

        // replaceAll qui rimpiazza gli "1" con coppie "-1, -2"
        ListBuilder<Integer> expectedReplaceAll = this.factory.empty().concat(l).replaceAll(1, this.factory.fromList(List.of(-1, -2))).concat(l);
        assertNotEquals(List.of(), expectedReplaceAll.build());
        assertEquals(List.of(-1, -2, 2, 3, -1, -2, 2, 3), expectedReplaceAll.build());

        // se non c'è match, la replaceAll non fa nulla
        ListBuilder<Integer> expectedNoReplaceAll = this.factory.empty().concat(l).replaceAll(10, this.factory.fromList(List.of(-1, -2))).concat(l);
        assertNotEquals(List.of(), expectedNoReplaceAll.build());
        assertEquals(List.of(1, 2, 3, 1, 2, 3), expectedNoReplaceAll.build());

    }

    @org.junit.Test
    public void testReverseFromList() {
        // la reverse fa ottenere un builder che rappresenta la lista invertita
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertNotEquals(List.of(), l.build());
        assertEquals(List.of(1, 2, 3), l.build());

        // reverse fa ottenere una nuova lista invertita
        ListBuilder<Integer> reversedExpected = this.factory.empty().concat(l).reverse();
        assertNotEquals(List.of(), reversedExpected.build());
        assertEquals(List.of(3, 2, 1), reversedExpected.build());

        // reverse funziona correttamente su due volte
        ListBuilder<Integer> doubleReverseExpected = this.factory.empty().concat(l).reverse().reverse();
        assertNotEquals(List.of(), doubleReverseExpected.build());
        assertEquals(List.of(1, 2, 3, 3, 2, 1), doubleReverseExpected.build());

    }

    @org.junit.Test
    public void testJoin() {
        // la join è usabile per concatenare più builder, con un elemento iniziale y uno finale
        ListBuilder<String> expectedJoin = this.factory.join("(", ")");
                                              .concat(this.factory.fromElement("1"))
                                              .concat(this.factory.fromElement("2"))
                                              .concat(this.factory.fromList(List.of("3", "4")));
        assertNotEquals(List.of(), expectedJoin.build());
        assertEquals(List.of("(", "1", "2", "3", "4", ")"), expectedJoin.build());

        // Test with a different list of elements
        ListBuilder<String> anotherExpectedJoin = this.factory.join("(", ")");
                                              .concat(this.factory.fromList(List.of("5", "6")))
                                              .concat(this.factory.fromList(List.of("7", "8")));
        assertNotEquals(List.of(), anotherExpectedJoin.build());
        assertEquals(List.of("(", "5", "6", "7", "8", ")"), anotherExpectedJoin.build());

    }
}
```

The generated code presents small errors like calling a 'concat' method that does not actually exist but the overall
generated tests definitely improved compared to zero shot prompts

The generated tests do not cover many edge cases that should have been considered but it's an overall good starting point.

A small human intervention is still required to fix the generated code.

## Conclusions
The generated code is not perfect and requires some human intervention to be usable, but it is a good starting point.
Using the Few Shot approach, the generated code is more usable and requires less human intervention compared to 
the Zero Shot approach that in some cases generated completely wrong responses not even in Java and not even about tests.