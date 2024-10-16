package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientsController {
    private final PersonValidator personValidator;
    private final ClientsService clientsService;

    @GetMapping("/profile")
    public String show(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("client", clientsService.findById(personDetails.getId()));
        return "clients/profile";
    }

    @GetMapping("/profile/edit")
    public String editForm(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("client", clientsService.findById(personDetails.getId()));
        return "clients/edit";
    }

    @PatchMapping("/profile")
    public String edit(@AuthenticationPrincipal PersonDetails personDetails,
                       @ModelAttribute("client") @Valid Client client,
                       BindingResult bindingResult) {
        client.setId(personDetails.getId());
        personValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "clients/edit";
        clientsService.save(client);
        return "redirect:/clients/profile";
    }

    @DeleteMapping("/profile")
    public String delete(@AuthenticationPrincipal PersonDetails personDetails) {
        clientsService.deleteById(personDetails.getId());
        return "redirect:/logout";
    }
}
