package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.services.ApplianceTypesService;
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
    3. Удаление прибора
    4. Проверка уникальности типа
 */

@Controller
@RequestMapping("/clients/{clientId}/appliances")
public class AppliancesController {
    private final AppliancesService appliancesService;
    private final ApplianceTypesService applianceTypesService;

    @Autowired
    public AppliancesController(AppliancesService appliancesService, ApplianceTypesService applianceTypesService) {
        this.appliancesService = appliancesService;
        this.applianceTypesService = applianceTypesService;
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @GetMapping
    public String showAll(@PathVariable("clientId") int clientId,
                          Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("appliances", appliancesService.findAllByOwnerId(clientId));
        return "appliances/index";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @GetMapping("/{applianceId}")
    public String show(@PathVariable("clientId") int clientId,
                       @PathVariable("applianceId") int applianceId,
                       Model model) {
        model.addAttribute("appliance", appliancesService.findById(applianceId));
        return "appliances/id";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @GetMapping("/new")
    public String addForm(@PathVariable("clientId") int clientId,
                          @ModelAttribute("appliance") Appliance appliance,
                          Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("applianceTypes", applianceTypesService.findAll());
        return "appliances/new";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @PostMapping
    public String add(@PathVariable("clientId") int clientId,
                      @ModelAttribute("appliance") @Valid Appliance appliance,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("applianceTypes", applianceTypesService.findAll());
            return "appliances/new";
        }
        appliancesService.save(appliance, clientId);
        return "redirect:/clients/{clientId}/appliances";
    }
}
