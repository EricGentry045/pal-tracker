package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeEntries")
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntriesRepo = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntryToCreate);

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry = timeEntriesRepo.find(timeEntryId);

        if (timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = timeEntriesRepo.update(timeEntryId, timeEntry);

        if (updatedTimeEntry != null) {
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {
        timeEntriesRepo.delete(timeEntryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
