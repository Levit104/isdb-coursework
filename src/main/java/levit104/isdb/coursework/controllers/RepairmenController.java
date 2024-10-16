package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.RepairmenService;
import levit104.isdb.coursework.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/repairmen")
@RequiredArgsConstructor
public class RepairmenController {
    private final PersonValidator personValidator;
    private final RepairmenService repairmenService;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("repairmen", repairmenService.getAllWithNonEmptySchedule());
        return "repairmen/index";
    }

    @GetMapping("/profile")
    public String show(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("repairman", repairmenService.findById(personDetails.getId()));
        return "repairmen/profile";
    }

    @GetMapping("/profile/edit")
    public String editForm(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("repairman", repairmenService.findById(personDetails.getId()));
        return "repairmen/edit";
    }

    @PatchMapping("/profile")
    public String edit(@AuthenticationPrincipal PersonDetails personDetails,
                       @ModelAttribute("repairman") @Valid Repairman repairman,
                       BindingResult bindingResult) {
        repairman.setId(personDetails.getId());
        personValidator.validate(repairman, bindingResult);
        if (bindingResult.hasErrors())
            return "repairmen/edit";
        repairmenService.save(repairman);
        return "redirect:/repairmen/profile";
    }

    @DeleteMapping("/profile")
    public String delete(@AuthenticationPrincipal PersonDetails personDetails) {
        repairmenService.deleteById(personDetails.getId());
        return "redirect:/logout";
    }
}
