package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.services.RepairmenService;
import levit104.isdb.coursework.validation.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final ClientsService clientsService;
    private final RepairmenService repairmenService;

    @Autowired
    public AuthController(PersonValidator personValidator, ClientsService clientsService, RepairmenService repairmenService) {
        this.personValidator = personValidator;
        this.clientsService = clientsService;
        this.repairmenService = repairmenService;
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
        clientsService.register(client);
        return "redirect:/auth/login";
    }

    @GetMapping("/registration-repairman")
    public String registrationPageRepairman(@ModelAttribute("repairman") Repairman repairman) {
        return "auth/registration-repairman";
    }

    @PostMapping("/registration-repairman")
    public String registrationActionRepairman(@ModelAttribute("repairman") @Valid Repairman repairman, BindingResult bindingResult) {
        personValidator.validate(repairman, bindingResult);
        if (bindingResult.hasErrors())
            return "auth/registration-repairman";
        repairmenService.register(repairman);
        return "redirect:/auth/login";
    }
}
