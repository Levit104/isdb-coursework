package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.services.ClientService;
import levit104.isdb.coursework.services.RepairmanService;
import levit104.isdb.coursework.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PersonValidator personValidator;
    private final ClientService clientService;
    private final RepairmanService repairmanService;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/registration-client")
    public String registrationFormClient(@ModelAttribute("client") Client client) {
        return "auth/registration-client";
    }

    @PostMapping("/registration-client")
    public String registrationClient(@ModelAttribute("client") @Valid Client client,
                                     BindingResult bindingResult) {
        personValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "auth/registration-client";
        clientService.save(client);
        return "redirect:/auth/login";
    }

    @GetMapping("/registration-repairman")
    public String registrationFormRepairman(@ModelAttribute("repairman") Repairman repairman) {
        return "auth/registration-repairman";
    }

    @PostMapping("/registration-repairman")
    public String registrationRepairman(@ModelAttribute("repairman") @Valid Repairman repairman,
                                        BindingResult bindingResult) {
        personValidator.validate(repairman, bindingResult);
        if (bindingResult.hasErrors())
            return "auth/registration-repairman";
        repairmanService.save(repairman);
        return "redirect:/auth/login";
    }
}
