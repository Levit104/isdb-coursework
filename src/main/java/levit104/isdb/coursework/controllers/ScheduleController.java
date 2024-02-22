package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.services.DaysService;
import levit104.isdb.coursework.util.SecurityUtils;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO Проверка, что это правильный пользователь
@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final DaysService daysService;

    @ModelAttribute("repairmanId")
    public Integer authenticatedRepairmanId() {
        return SecurityUtils.getAuthenticatedPerson().getId();
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute("schedule", daysService.findAllByRepairmanId(authenticatedRepairmanId()));
        return "schedule/index";
    }

    @GetMapping("/edit")
    public String editForm(Model model) {
        model.addAttribute("days", daysService.findAll());
        model.addAttribute("scheduleIds", daysService.getDaysIds(authenticatedRepairmanId()));
        return "schedule/edit";
    }

    @PutMapping
    public String edit(@RequestParam(value = "selectedDaysIds", required = false) List<Integer> selectedDaysIds,
                       Model model) {
        if (selectedDaysIds == null) {
            model.addAttribute("scheduleError", ErrorMessages.EMPTY_SCHEDULE);
            model.addAttribute("days", daysService.findAll());
//            model.addAttribute("scheduleIds", daysService.getDaysIds(repairmanId));
            return "schedule/edit";
        }
        daysService.saveSchedule(authenticatedRepairmanId(), selectedDaysIds);
        return "redirect:/schedule";
    }
}
