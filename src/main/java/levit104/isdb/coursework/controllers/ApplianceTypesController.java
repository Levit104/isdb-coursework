package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.ApplianceType;
import levit104.isdb.coursework.services.ApplianceTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appliance-types")
public class ApplianceTypesController {
    private final ApplianceTypesService applianceTypesService;

    @Autowired
    public ApplianceTypesController(ApplianceTypesService applianceTypesService) {
        this.applianceTypesService = applianceTypesService;
    }

    @GetMapping
    public String showAllAppliancesTypes(Model model) {
        model.addAttribute("applianceTypes", applianceTypesService.findAll());
        return "appliance-types/index";
    }

    @GetMapping("/new")
    public String addApplianceTypeForm(@ModelAttribute("applianceType") ApplianceType applianceType) {
        return "appliance-types/new";
    }

    // TODO unique
    @PostMapping
    public String addApplianceType(@ModelAttribute("applianceType") @Valid ApplianceType applianceType,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "appliance-types/new";
        applianceTypesService.save(applianceType);
        return "redirect:/appliance-types";
    }
}
