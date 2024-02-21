package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("clients", clientsService.findAll());
        return "clients/index";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("client", clientsService.findById(id));
        return "clients/id";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("client", clientsService.findById(id));
        return "clients/edit";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") Integer id,
                       @ModelAttribute("client") @Valid Client client,
                       BindingResult bindingResult) {
        personValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "clients/edit";
        clientsService.updateById(id, client);
        return "redirect:/clients/{id}";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        clientsService.deleteById(id);
        return "redirect:/clients";
    }
}
