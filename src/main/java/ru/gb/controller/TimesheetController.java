package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.exception.ResourceNotFoundException;
import ru.gb.model.Timesheet;
import ru.gb.service.TimesheetService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    // GET - получить - не содержит тела
    // POST - create
    // PUT - изменение
    // PATCH - изменение
    // DELETE - удаление

    // @GetMapping("/timesheets/{id}") // получить конкретную запись по идентификатору
    // @DeleteMapping("/timesheets/{id}") // удалить конкретную запись по идентификатору
    // @PutMapping("/timesheets/{id}") // обновить конкретную запись по идентификатору

    private final TimesheetService service;

    // /timesheets/{id}
    @GetMapping("/{id}") // получить все
    public ResponseEntity<Timesheet> get(@PathVariable Long id) {
        Optional<Timesheet> ts = service.findById(id);

        return ts.map(timesheet -> ResponseEntity.status(HttpStatus.OK).body(timesheet))
                .orElseThrow(() -> new ResourceNotFoundException("There is no timesheet with id #" + id));
    }

    @GetMapping // получить все
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping // создание нового ресурса
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        try {
            timesheet = service.create(timesheet);
        } catch (NullPointerException | NoSuchElementException e) {
            System.err.println("Error while creating timesheet. " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.delete(id);
            // 204 No Content
            return ResponseEntity.noContent().build();
        }

        throw new ResourceNotFoundException("There is no timesheet with id #" + id);
    }

    @GetMapping(params = "createdAfter")
    public ResponseEntity<List<Timesheet>> getCreatedAfter(@RequestParam LocalDate createdAfter) {
        return ResponseEntity.ok(service.getCreatedAfter(createdAfter));
    }

    @GetMapping(params = "createdBefore")
    public ResponseEntity<List<Timesheet>> getCreatedBefore(@RequestParam LocalDate createdBefore) {
        return ResponseEntity.ok(service.getCreatedBefore(createdBefore));
    }

}
