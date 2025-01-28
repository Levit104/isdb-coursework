package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.ApplianceService;
import levit104.isdb.coursework.services.ClientService;
import levit104.isdb.coursework.validation.ApplianceValidator;
import levit104.isdb.coursework.validation.ErrorMessages;
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
    private final ApplianceService applianceService;
    private final ClientService clientService;

    @GetMapping
    public String showAll(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("appliances", applianceService.getAllByOwnerId(personDetails.getId()));
        return "appliances/index";
    }

    @GetMapping("/new")
    public String addForm(@ModelAttribute("appliance") Appliance appliance, Model model) {
        model.addAttribute("applianceTypes", applianceService.getAllTypes());
        return "appliances/new";
    }

    @PostMapping
    public String add(@AuthenticationPrincipal PersonDetails personDetails,
                      @ModelAttribute("appliance") @Valid Appliance appliance,
                      BindingResult bindingResult,
                      Model model) {
        appliance.setOwner(clientService.getById(personDetails.getId()));
        applianceValidator.validate(appliance, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", applianceService.getAllTypes());
            return "appliances/new";
        }

        applianceService.save(appliance);
        return "redirect:/appliances";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("appliance", applianceService.getById(id));
        model.addAttribute("applianceTypes", applianceService.getAllTypes());
        return "appliances/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@AuthenticationPrincipal PersonDetails personDetails,
                       @PathVariable("id") Integer id,
                       @ModelAttribute("appliance") @Valid Appliance appliance,
                       BindingResult bindingResult,
                       Model model) {
        appliance.setId(id);
        appliance.setOwner(clientService.getById(personDetails.getId()));
        applianceValidator.validate(appliance, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", applianceService.getAllTypes());
            return "appliances/edit";
        }

        applianceService.save(appliance);
        return "redirect:/appliances";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id,
                         @AuthenticationPrincipal PersonDetails personDetails,
                         Model model) {
        boolean inOrder = applianceService.deleteById(id);

        if (inOrder) {
            model.addAttribute("appliances", applianceService.getAllByOwnerId(personDetails.getId()));
            model.addAttribute("inOrder", ErrorMessages.APPLIANCE_IN_ORDER);
            model.addAttribute("inOrderId", id);
            return "appliances/index";
        }

        return "redirect:/appliances";
    }
}
