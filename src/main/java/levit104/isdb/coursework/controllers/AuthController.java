package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.services.DaysService;
import levit104.isdb.coursework.services.RepairmenService;
import levit104.isdb.coursework.validation.PersonValidator;
import levit104.isdb.coursework.validation.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final ScheduleValidator scheduleValidator;
    private final ClientsService clientsService;
    private final RepairmenService repairmenService;
    private final DaysService daysService;

    @Autowired
    public AuthController(PersonValidator personValidator,
                          ScheduleValidator scheduleValidator,
                          ClientsService clientsService,
                          RepairmenService repairmenService,
                          DaysService daysService) {
        this.personValidator = personValidator;
        this.scheduleValidator = scheduleValidator;
        this.clientsService = clientsService;
        this.repairmenService = repairmenService;
        this.daysService = daysService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "auth/registration";
    }

    @GetMapping("/registration-client")
    public String registrationPageClient(@ModelAttribute("client") Client client) {
        return "auth/registration-client";
    }

    @PostMapping("/registration-client")
    public String registrationActionClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        personValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "auth/registration-client";
        clientsService.save(client);
        return "redirect:/auth/login";
    }

    @GetMapping("/registration-repairman")
    public String registrationPageRepairman(@ModelAttribute("repairman") Repairman repairman, Model model) {
        model.addAttribute("days", daysService.findAll());
        return "auth/registration-repairman";
    }

    @PostMapping("/registration-repairman")
    public String registrationActionRepairman(Model model,
                                              @RequestParam(value = "selectedDays", required = false) List<Integer> selectedDaysIds,
                                              @ModelAttribute("repairman") @Valid Repairman repairman,
                                              BindingResult bindingResult) {
        personValidator.validate(repairman, bindingResult);
        scheduleValidator.validate(selectedDaysIds, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("days", daysService.findAll());
            return "auth/registration-repairman";
        }
        repairmenService.save(repairman, selectedDaysIds);
        return "redirect:/auth/login";
    }
}
