package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.dto.EmployeePageDTO;
import ru.gb.dto.ProjectPageDTO;
import ru.gb.service.ProjectService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectPageController {

    private final ProjectService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getProjects(Model model) {
        model.addAttribute("projects", service.findAll());
        return "projects-page";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        Optional<ProjectPageDTO> project = service.findById(id);
        Set<EmployeePageDTO> employees = service.findProjectEmployees(id);

        if (project.isPresent()) {
            model.addAttribute("project", project.get());
            model.addAttribute("employees", employees);
            return "project-page";
        }

        throw new NoSuchElementException("There is no project with id #" + id);
    }

}