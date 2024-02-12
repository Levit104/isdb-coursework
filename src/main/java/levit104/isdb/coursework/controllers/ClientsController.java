package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.validation.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static levit104.isdb.coursework.security.SecurityUtils.getAuthenticatedPerson;

@Controller
@RequestMapping("/clients")
public class ClientsController {
    private final PersonValidator personValidator;
    private final ClientsService clientsService;

    @Autowired
    public ClientsController(PersonValidator personValidator, ClientsService clientsService) {
        this.personValidator = personValidator;
        this.clientsService = clientsService;
    }

    @GetMapping
    public String showAll(Model model) {
        System.out.println("ABOBA_SHOW_ALL");
        model.addAttribute("clients", clientsService.findAll());
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        System.out.println("ABOBA_SHOW_ONE, id= " + id);
        model.addAttribute("client", clientsService.findById(id));
        return "clients/id";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") int id, Model model) {
        System.out.println("ABOBA_EDIT_FORM, id= " + id);
        model.addAttribute("client", clientsService.findById(id));
        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("client") @Valid Client client,
                       BindingResult bindingResult) {
        System.out.println("ABOBA_EDIT, id= " + id);
        personValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "clients/edit";
        clientsService.updateById(id, client);
        return "redirect:/clients/{id}";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        System.out.println("ABOBA_DELETE, id= " + id);
        clientsService.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/log-info")
    public String logInfo() {
        Client client = (Client) getAuthenticatedPerson();
        System.out.println(client);
        return "redirect:/clients/" + client.getId();
    }
}
