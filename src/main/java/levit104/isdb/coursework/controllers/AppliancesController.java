package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.ApplianceType;
import levit104.isdb.coursework.services.AppliancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/*
TODO:
    1. Проверка даты (меньше текущей)
    2. Типы приборов только для админа ???
    3. Удаление прибора
    4. Проверка уникальности типа
 */

@Controller
@RequestMapping("/clients/{clientId}/appliances")
public class AppliancesController {
    private final AppliancesService appliancesService;

    @Autowired
    public AppliancesController(AppliancesService appliancesService) {
        this.appliancesService = appliancesService;
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @GetMapping
    public String showAllAppliances(@PathVariable("clientId") int clientId,
                                    Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("appliances", appliancesService.findAllByOwnerId(clientId));
        return "clients/appliances/index";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @GetMapping("/{applianceId}")
    public String showOneAppliance(@PathVariable("clientId") int clientId,
                                   @PathVariable("applianceId") int applianceId,
                                   Model model) {
        model.addAttribute("appliance", appliancesService.findApplianceById(applianceId));
        return "clients/appliances/id";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @GetMapping("/new")
    public String addApplianceForm(@PathVariable("clientId") int clientId,
                                   @ModelAttribute("appliance") Appliance appliance,
                                   Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("applianceTypes", appliancesService.findAllTypes());
        return "clients/appliances/new";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @PostMapping
    public String addAppliance(@PathVariable("clientId") int clientId,
                               @ModelAttribute("appliance") @Valid Appliance appliance,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", appliancesService.findAllTypes());
            return "clients/appliances/new";
        }
        appliancesService.saveAppliance(appliance, clientId);
        return "redirect:/clients/{clientId}/appliances";
    }

    @GetMapping("/types")
    public String showAllAppliancesTypes(@PathVariable("clientId") int clientId,
                                         Model model) {
        model.addAttribute("applianceTypes", appliancesService.findAllTypes());
        return "clients/appliances/types/index";
    }

    @GetMapping("/types/new")
    public String addApplianceTypeForm(@PathVariable("clientId") int clientId,
                                       @ModelAttribute("applianceType") ApplianceType applianceType,
                                       Model model) {
        model.addAttribute("clientId", clientId);
        return "clients/appliances/types/new";
    }

    @PostMapping("/types")
    public String addApplianceType(@PathVariable int clientId,
                                   @ModelAttribute("applianceType") @Valid ApplianceType applianceType,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "clients/appliances/types/new";
        appliancesService.saveApplianceType(applianceType);
        return "redirect:/clients/{clientId}/appliances/new";
    }

}
