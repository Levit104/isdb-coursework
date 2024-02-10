package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.validation.ClientValidator;
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
    private final ClientValidator clientValidator;
    private final ClientsService clientsService;

    @Autowired
    public AuthController(ClientValidator clientValidator, ClientsService clientsService) {
        this.clientValidator = clientValidator;
        this.clientsService = clientsService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("client") Client client) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("client") @Valid Client client,
                                      BindingResult bindingResult) {
        System.out.println("ABOBA2");
        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "/auth/registration";
        clientsService.register(client);
        return "redirect:/auth/login";
    }
}
