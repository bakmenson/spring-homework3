package ru.gb.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gb.model.Project;
import ru.gb.model.Timesheet;
import ru.gb.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service // то же самое, что и Component
public class TimesheetService {

    private final TimesheetRepository repository;
    private final ProjectService projectService;

    public TimesheetService(TimesheetRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    public Optional<Timesheet> getById(Long id) {
        return repository.getById(id);
    }

    public List<Timesheet> getAll() {
        return repository.getAll();
    }

    public Timesheet create(Timesheet timesheet) {
        Long projectId = timesheet.getProjectId();

        if (projectId == null) {
            throw new NullPointerException("There is no project id.");
        }

        Optional<Project> project = projectService.getById(timesheet.getProjectId());

        if (project.isPresent()) {
            timesheet.setCreatedAt(LocalDate.now());
            return repository.create(timesheet);
        }

        throw new NoSuchElementException("There is no project with id " + projectId + ".");
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
