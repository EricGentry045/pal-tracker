package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepo;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               MeterRegistry meterRegistry) {
        this.timeEntriesRepo = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntryToCreate);
        actionCounter.increment();
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry = timeEntriesRepo.find(timeEntryId);

        if (timeEntry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = timeEntriesRepo.update(timeEntryId, timeEntry);

        if (updatedTimeEntry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {
        timeEntriesRepo.delete(timeEntryId);
        actionCounter.increment();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
