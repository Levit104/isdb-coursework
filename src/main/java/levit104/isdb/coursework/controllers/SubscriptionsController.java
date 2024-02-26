package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.SubscriptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {
    private final SubscriptionsService subscriptionsService;

    @GetMapping
    public String showActive(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("activeSubscriptions", subscriptionsService.findAllByClientId(personDetails.getId()));
        return "subscriptions/index";
    }

    @GetMapping("/new")
    public String showPlans(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
//        model.addAttribute("subscriptionPlans", subscriptionsService.findAllPlans());
        model.addAttribute("subscriptionPlans", subscriptionsService.getAvailablePlans(personDetails.getId()));
        return "subscriptions/new";
    }

    @PostMapping
    public String subscribe(@AuthenticationPrincipal PersonDetails personDetails,
                            @RequestParam("planId") Integer planId,
                            @RequestParam("duration") Integer duration) {
        subscriptionsService.subscribe(duration, personDetails.getId(), planId);
        return "redirect:/subscriptions";
    }
}
