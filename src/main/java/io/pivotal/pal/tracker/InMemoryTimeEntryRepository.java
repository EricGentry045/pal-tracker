package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry> timeEntries = new HashMap<>();
    private long currentTimeEntry = 1;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(currentTimeEntry);
        timeEntries.put(currentTimeEntry, timeEntry);
        currentTimeEntry++;
        return timeEntry;
    }

    public TimeEntry find(long timeEntryId) {
        return timeEntries.get(timeEntryId);
    }

    public List<TimeEntry> list() {
        return timeEntries.values().stream().collect(Collectors.toList());
    }

    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        boolean isNewEntry = find(timeEntryId) == null;
        timeEntry.setId(timeEntryId);
        timeEntries.put(timeEntryId, timeEntry);
        if (isNewEntry) {
            return null;
        }
        return timeEntry;
    }

    public void delete(long timeEntryId){
        timeEntries.remove(timeEntryId);
    }
}
