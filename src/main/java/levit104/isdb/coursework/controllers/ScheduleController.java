package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.services.DaysService;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/repairmen/{repairmanId}/schedule")
@RequiredArgsConstructor
// TODO PreAuthorize
public class ScheduleController {
    private final DaysService daysService;

    @GetMapping
    public String show(@PathVariable("repairmanId") Integer repairmanId, Model model) {
        model.addAttribute("schedule", daysService.findAllByRepairmanId(repairmanId));
        return "schedule/index";
    }

    @GetMapping("/edit")
    public String editForm(@PathVariable("repairmanId") Integer repairmanId, Model model) {
        model.addAttribute("repairmanId", repairmanId);
        model.addAttribute("days", daysService.findAll());
        model.addAttribute("scheduleIds", daysService.getDaysIds(repairmanId));
        return "schedule/edit";
    }

    @PostMapping
    public String edit(@PathVariable("repairmanId") Integer repairmanId,
                       @RequestParam(value = "selectedDaysIds", required = false) List<Integer> selectedDaysIds,
                       Model model) {
        if (selectedDaysIds == null) {
            model.addAttribute("scheduleError", ErrorMessages.EMPTY_SCHEDULE);
            model.addAttribute("days", daysService.findAll());
            // model.addAttribute("scheduleIds", daysService.getDaysIds(repairmanId));
            return "schedule/edit";
        }
        daysService.saveSchedule(repairmanId, selectedDaysIds);
        return "redirect:/repairmen/{repairmanId}/schedule";
    }
}
