package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.services.DaysService;
import levit104.isdb.coursework.services.RepairmenService;
import levit104.isdb.coursework.validation.PersonValidator;
import levit104.isdb.coursework.validation.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/repairmen")
public class RepairmenController {
    private final PersonValidator personValidator;
    private final ScheduleValidator scheduleValidator;
    private final RepairmenService repairmenService;
    private final DaysService daysService;

    @Autowired
    public RepairmenController(PersonValidator personValidator,
                               ScheduleValidator scheduleValidator,
                               RepairmenService repairmenService,
                               DaysService daysService) {
        this.personValidator = personValidator;
        this.scheduleValidator = scheduleValidator;
        this.repairmenService = repairmenService;
        this.daysService = daysService;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("repairmen", repairmenService.findAll());
        return "repairmen/index";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("repairman", repairmenService.findById(id));
        return "repairmen/id";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("repairman", repairmenService.findById(id));
        model.addAttribute("days", daysService.findAll());
        model.addAttribute("scheduleIds", daysService.getDaysIds(id));
        return "repairmen/edit";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       Model model,
                       @RequestParam(value = "selectedDays", required = false) List<Integer> selectedDaysIds,
                       @ModelAttribute("repairman") @Valid Repairman repairman,
                       BindingResult bindingResult) {
        personValidator.validate(repairman, bindingResult);
        scheduleValidator.validate(selectedDaysIds, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("days", daysService.findAll());
            model.addAttribute("scheduleIds", daysService.getDaysIds(id));
            return "repairmen/edit";
        }
        repairmenService.updateById(id, repairman, selectedDaysIds);
        return "redirect:/repairmen/{id}";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        repairmenService.deleteById(id);
        return "redirect:/repairmen";
    }
}
