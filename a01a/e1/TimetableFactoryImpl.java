package e1;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class TimetableFactoryImpl implements TimetableFactory {

    @Override
    public Timetable empty() {
        return new TimetableImpl();
    }

    @Override
    public Timetable single(String activity, String day) {
        return empty().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        Timetable result = empty();
    
        // Union of activities and days from both tables
        Set<String> allActivities = new HashSet<>(table1.activities());
        allActivities.addAll(table2.activities());
    
        Set<String> allDays = new HashSet<>(table1.days());
        allDays.addAll(table2.days());
    
        // Iterate through all activities and days
        for (String activity : allActivities) {
            for (String day : allDays) {
                int hours = table1.getSingleData(activity, day) + table2.getSingleData(activity, day);
    
                // Add the total hours for this activity and day to the result
                for (int i = 0; i < hours; i++) {
                    result = result.addHour(activity, day);
                }
            }
        }
    
        return result;
    }
    

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        // Reset hours to 0 but keep activities and days
        TimetableImpl result = ((TimetableImpl) table).resetHours();
    
        // Add bounded hours back to the result
        for (String activity : table.activities()) {
            for (String day : table.days()) {
                int allowedHours = bounds.apply(activity, day);
                int currentHours = Math.min(table.getSingleData(activity, day), allowedHours);
    
                for (int i = 0; i < currentHours; i++) {
                    result = (TimetableImpl) result.addHour(activity, day);
                }
            }
        }
    
        return result;
    }
    

    // Inner class implementing the Timetable interface
    private class TimetableImpl implements Timetable {
        private final Map<String, Map<String, Integer>> data = new HashMap<>();

        @Override
        public Timetable addHour(String activity, String day) {
            Map<String, Map<String, Integer>> newData = new HashMap<>(this.data);
            newData.putIfAbsent(activity, new HashMap<>());
            newData.get(activity).put(day, newData.get(activity).getOrDefault(day, 0) + 1);
            TimetableImpl newTimetable = new TimetableImpl();
            newTimetable.data.putAll(newData);
            return newTimetable;
        }

        @Override
        public Set<String> activities() {
            return data.keySet();
        }

        @Override
        public Set<String> days() {
            return data.values().stream()
                       .flatMap(map -> map.keySet().stream())
                       .collect(Collectors.toSet());
        }

        @Override
        public int getSingleData(String activity, String day) {
            return data.getOrDefault(activity, Map.of()).getOrDefault(day, 0);
        }

        @Override
        public int sums(Set<String> activities, Set<String> days) {
            return activities.stream()
                             .filter(data::containsKey)
                             .flatMap(activity -> data.get(activity).entrySet().stream())
                             .filter(entry -> days.contains(entry.getKey()))
                             .mapToInt(Map.Entry::getValue)
                             .sum();

        }

        public TimetableImpl resetHours() {
            Map<String, Map<String, Integer>> newData = new HashMap<>();
            for (String activity : data.keySet()) {
                newData.put(activity, new HashMap<>());
                for (String day : data.get(activity).keySet()) {
                    newData.get(activity).put(day, 0); // Reset hours to 0
                }
            }
            TimetableImpl newTimetable = new TimetableImpl();
            newTimetable.data.putAll(newData);
            return newTimetable;
        }
    }
}
