package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.services.RepairmenService;
import levit104.isdb.coursework.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
        model.addAttribute("repairmen", repairmenService.findAll());
        return "repairmen/index";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("repairman", repairmenService.findById(id));
        return "repairmen/id";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("repairman", repairmenService.findById(id));
        return "repairmen/edit";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") Integer id,
                       @ModelAttribute("repairman") @Valid Repairman repairman,
                       BindingResult bindingResult) {
        personValidator.validate(repairman, bindingResult);
        if (bindingResult.hasErrors())
            return "repairmen/edit";
        repairmenService.updateById(id, repairman);
        return "redirect:/repairmen/{id}";
    }

    @PreAuthorize("@peopleService.hasCorrectId(#id)")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        repairmenService.deleteById(id);
        return "redirect:/repairmen";
    }
}
