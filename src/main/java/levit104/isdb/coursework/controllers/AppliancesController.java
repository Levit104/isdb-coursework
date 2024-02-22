package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.services.ApplianceTypesService;
import levit104.isdb.coursework.services.AppliancesService;
import levit104.isdb.coursework.util.SecurityUtils;
import levit104.isdb.coursework.validation.ApplianceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// TODO
//  Проверка, что это правильный пользователь
@Controller
@RequestMapping("/appliances")
@RequiredArgsConstructor
public class AppliancesController {
    private final ApplianceValidator applianceValidator;
    private final AppliancesService appliancesService;
    private final ApplianceTypesService applianceTypesService;

    @ModelAttribute("client")
    public Client authenticatedClient() {
        return (Client) SecurityUtils.getAuthenticatedPerson();
    }

    @GetMapping
    public String showAll(Model model) {
        Integer ownerId = authenticatedClient().getId();
        model.addAttribute("appliances", appliancesService.findAllByOwnerId(ownerId));
        return "appliances/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("appliance", appliancesService.findById(id));
        return "appliances/id";
    }

    @GetMapping("/new")
    public String addForm(@ModelAttribute("appliance") Appliance appliance, Model model) {
        model.addAttribute("applianceTypes", applianceTypesService.findAll());
        return "appliances/new";
    }

    @PostMapping
    public String add(@ModelAttribute("appliance") @Valid Appliance appliance,
                      BindingResult bindingResult,
                      Model model) {
        Client client = authenticatedClient();
        appliance.setOwner(client);
        applianceValidator.validate(appliance, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", applianceTypesService.findAll());
            return "appliances/new";
        }

        appliancesService.save(appliance);
        return "redirect:/appliances";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("appliance", appliancesService.findById(id));
        model.addAttribute("applianceTypes", applianceTypesService.findAll());
        return "appliances/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") Integer id,
                       @ModelAttribute("appliance") @Valid Appliance appliance,
                       BindingResult bindingResult,
                       Model model) {
        Client client = authenticatedClient();
        appliance.setOwner(client);
        applianceValidator.validate(appliance, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", applianceTypesService.findAll());
            return "appliances/edit";
        }

        appliancesService.updateById(id, appliance);
        return "redirect:/appliances/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        appliancesService.deleteById(id);
        return "redirect:/appliances";
    }
}
