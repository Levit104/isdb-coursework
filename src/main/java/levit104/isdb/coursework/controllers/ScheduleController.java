package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.DaysService;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final DaysService daysService;

    @GetMapping
    public String show(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("schedule", daysService.findAllByRepairmanId(personDetails.getId()));
        return "schedule/index";
    }

    @GetMapping("/edit")
    public String editForm(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("days", daysService.findAll());
        model.addAttribute("scheduleIds", daysService.getDaysIds(personDetails.getId()));
        return "schedule/edit";
    }

    @PutMapping
    public String edit(@AuthenticationPrincipal PersonDetails personDetails,
                       @RequestParam(value = "selectedDaysIds", required = false) List<Integer> selectedDaysIds,
                       Model model) {
        if (selectedDaysIds == null) {
            model.addAttribute("scheduleError", ErrorMessages.EMPTY_SCHEDULE);
            model.addAttribute("days", daysService.findAll());
//            model.addAttribute("scheduleIds", daysService.getDaysIds(repairmanId));
            return "schedule/edit";
        }
        daysService.saveSchedule(personDetails.getId(), selectedDaysIds);
        return "redirect:/schedule";
    }
}
