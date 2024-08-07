package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.dto.TimesheetPageDTO;
import ru.gb.service.TimesheetService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/timesheets")
@RequiredArgsConstructor
public class TimesheetPageController {

  private final TimesheetService service;

  @GetMapping
  public String getAllTimesheets(Model model) {
    List<TimesheetPageDTO> timesheets = service.findAll();
    model.addAttribute("timesheets", timesheets);
    return "timesheets-page.html";
  }

  // GET /home/timesheets/{id}
  @GetMapping("/{id}")
  public String getTimesheetPage(@PathVariable Long id, Model model) {
    Optional<TimesheetPageDTO> timesheet = service.findById(id);
    if (timesheet.isEmpty()) {
      throw new NoSuchElementException("There is no timesheet with id #" + id);
    }

    model.addAttribute("timesheet", timesheet.get());
    return "timesheet-page.html";
  }

}
