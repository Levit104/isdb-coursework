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

// TODO Проверка даты (меньше текущей)
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
        model.addAttribute("clientId", clientId);
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

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @GetMapping("/{applianceId}/edit")
    public String editForm(@PathVariable("clientId") int clientId,
                           @PathVariable("applianceId") int applianceId,
                           Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("appliance", appliancesService.findById(applianceId));
        model.addAttribute("applianceTypes", applianceTypesService.findAll());
        return "appliances/edit";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @PatchMapping("/{applianceId}")
    public String edit(@PathVariable("clientId") int clientId,
                       @PathVariable("applianceId") int applianceId,
                       @ModelAttribute("appliance") @Valid Appliance appliance,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            appliance.setId(applianceId); // FIXME почему здесь это надо, а у клиентов/мастеров нет???
            model.addAttribute("applianceTypes", applianceTypesService.findAll());
            return "appliances/edit";
        }
        appliancesService.updateById(applianceId, appliance, clientId);
        return "redirect:/clients/{clientId}/appliances/{applianceId}";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#clientId)")
    @DeleteMapping("/{applianceId}")
    public String delete(@PathVariable("clientId") int clientId,
                         @PathVariable("applianceId") int applianceId) {
        appliancesService.deleteById(applianceId);
        return "redirect:/clients/{clientId}/appliances";
    }
}
