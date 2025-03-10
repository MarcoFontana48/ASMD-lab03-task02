import java.util.*;
import java.util.function.*;

public class TimetableFactoryImplZeroShotSmollmGeneratedTest implements Timetable as.Timetable {
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
