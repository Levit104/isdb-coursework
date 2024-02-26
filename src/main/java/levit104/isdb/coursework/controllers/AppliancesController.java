package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.AppliancesService;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.validation.ApplianceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appliances")
@RequiredArgsConstructor
public class AppliancesController {
    private final ApplianceValidator applianceValidator;
    private final AppliancesService appliancesService;
    private final ClientsService clientsService;

    @GetMapping
    public String showAll(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("appliances", appliancesService.findAllByOwnerId(personDetails.getId()));
        return "appliances/index";
    }

    @GetMapping("/new")
    public String addForm(@ModelAttribute("appliance") Appliance appliance, Model model) {
        model.addAttribute("applianceTypes", appliancesService.findAllTypes());
        return "appliances/new";
    }

    @PostMapping
    public String add(@AuthenticationPrincipal PersonDetails personDetails,
                      @ModelAttribute("appliance") @Valid Appliance appliance,
                      BindingResult bindingResult,
                      Model model) {
        appliance.setOwner(clientsService.findById(personDetails.getId()));
        applianceValidator.validate(appliance, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", appliancesService.findAllTypes());
            return "appliances/new";
        }

        appliancesService.save(appliance);
        return "redirect:/appliances";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("appliance", appliancesService.findById(id));
        model.addAttribute("applianceTypes", appliancesService.findAllTypes());
        return "appliances/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@AuthenticationPrincipal PersonDetails personDetails,
                       @PathVariable("id") Integer id,
                       @ModelAttribute("appliance") @Valid Appliance appliance,
                       BindingResult bindingResult,
                       Model model) {
        appliance.setOwner(clientsService.findById(personDetails.getId()));
        applianceValidator.validate(appliance, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", appliancesService.findAllTypes());
            return "appliances/edit";
        }

        appliancesService.updateById(id, appliance);
        return "redirect:/appliances";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        appliancesService.deleteById(id);
        return "redirect:/appliances";
    }
}
