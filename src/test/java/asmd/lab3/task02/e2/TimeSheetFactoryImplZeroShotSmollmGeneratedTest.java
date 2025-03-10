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